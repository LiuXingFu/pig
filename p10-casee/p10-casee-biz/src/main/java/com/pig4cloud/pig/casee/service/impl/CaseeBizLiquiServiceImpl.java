package com.pig4cloud.pig.casee.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteMessageRecordService;
import com.pig4cloud.pig.admin.api.feign.RemoteOutlesService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.admin.api.vo.OutlesVO;
import com.pig4cloud.pig.casee.dto.CaseeBizLiquiDTO;
import com.pig4cloud.pig.casee.dto.CaseeOrTargetTaskFlowDTO;
import com.pig4cloud.pig.casee.dto.CaseeRepaymentAddDTO;
import com.pig4cloud.pig.casee.dto.TargetThingAddDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.detail.CaseeLiQuipLedge;
import com.pig4cloud.pig.casee.entity.detail.TargetThingLiQuiDateDetail;
import com.pig4cloud.pig.casee.mapper.CaseeBizLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.*;
import com.pig4cloud.pig.common.core.constant.CaseeOrTargetTaskFlowConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CaseeBizLiquiServiceImpl extends ServiceImpl<CaseeBizLiquiMapper, CaseeBizBase> implements CaseeBizLiquiService {
	@Autowired
	private CaseePersonnelService caseePersonnelService;
	@Autowired
	private CaseePersonnelReService caseePersonnelReService;
	@Autowired
	private CaseeOutlesDealReService caseeOutlesDealReService;
	@Autowired
	private RemoteSubjectService remoteSubjectService;
	@Autowired
	private CaseeBizService caseeBizService;
	@Autowired
	private RemoteMessageRecordService remoteMessageRecordService;
	@Autowired
	private SecurityUtilsService securityUtilsService;

	@Autowired
	private TargetBizLiquiService targetBizLiquiService;

	@Autowired
	private RemoteOutlesService remoteOutlesService;
	@Autowired
	private TaskNodeService taskNodeService;

	@Autowired
	private RemoteAddressService remoteAddressService;
	@Autowired
	private CaseePerformanceService caseePerformanceService;
	/**
	 * 查询清收列表
	 * @param page
	 * @param caseeBizLiquiDTO
	 * @return
	 */
	@Override
	public IPage<CaseeBizLiquiVO> queryLiquidateCaseePage(Page page, CaseeBizLiquiDTO caseeBizLiquiDTO){
		if(Objects.nonNull(caseeBizLiquiDTO.getCaseStatus())){
			String[] s = caseeBizLiquiDTO.getCaseStatus().split("_");
			if(s.length == 1){
				caseeBizLiquiDTO.setStatus(Integer.valueOf(s[0]));
				caseeBizLiquiDTO.setStatusConfirm(Integer.valueOf(s[0]));
			} else {
				caseeBizLiquiDTO.setStatus(Integer.valueOf(s[0]));
				caseeBizLiquiDTO.setStatusConfirm(Integer.valueOf(s[1]));
			}
		}
		return this.baseMapper.selectLiquidateCasee(page, caseeBizLiquiDTO);
	}
	/**
	 * 委托案件列表
	 * @param page
	 * @param caseeBizLiquiDTO
	 * @return
	 */
	@Override
	public IPage<CaseeBizLiquiVO> queryLiquidateCaseeEntrustPage(Page page, CaseeBizLiquiDTO caseeBizLiquiDTO){
		return this.baseMapper.selectLiquidateCaseeEntrust(page,caseeBizLiquiDTO);
	}

	@Override
	@Transactional
	public CaseeBizLiqui addCasee(CaseeBizLiquiDTO caseeBizLiquiDTO) throws Exception{
		QueryWrapper<CaseeBizBase> goodsCaseQueryWrapper=new QueryWrapper<>();
		goodsCaseQueryWrapper.eq("company_code",caseeBizLiquiDTO.getCompanyCode()).eq("del_flag","0");
		CaseeBizBase goodsCaseWord= caseeBizService.getOne(goodsCaseQueryWrapper);
		if(goodsCaseWord!=null){
			throw new Exception("业务案号重复请重新添加");
		}
		caseeBizLiquiDTO.getCaseeLiquiDetail().setRepaymentRate(0);

		CaseeBizLiqui caseeBizLiqui = new CaseeBizLiqui();
		String executedNames="";
		for (int i=0;i<caseeBizLiquiDTO.getExecutedList().size();i++){
			if(i==0){
				executedNames= caseeBizLiquiDTO.getExecutedList().get(i).getName();
			}else {
				executedNames=executedNames+","+ caseeBizLiquiDTO.getExecutedList().get(i).getName();
			}
		}
		caseeBizLiquiDTO.setExecutedPersons(executedNames);
		BeanUtils.copyProperties(caseeBizLiquiDTO,caseeBizLiqui);
		this.baseMapper.insert(caseeBizLiqui);
		//添加案件标的
		R<OutlesVO> outlesVO = remoteOutlesService.getById(caseeBizLiquiDTO.getCreateOutlesId(), SecurityConstants.FROM);
		ObjectMapper objectMapper = new ObjectMapper();
		List<TargetBizLiqui> targetBizLiquiList = new ArrayList<>();
		for (CaseeLiQuipLedge caseeLiQuipLedge:caseeBizLiquiDTO.getCaseeLiquiDetail().getPledgeList()){

			TargetThingAddDTO targetThingAddDTO=new TargetThingAddDTO();
			targetThingAddDTO.setCaseeId(caseeBizLiqui.getCaseeId());
			targetThingAddDTO.setTargetName(caseeLiQuipLedge.getTargetName());
//			targetThingAddDTO.setBusinessType(100);
//			targetThingAddDTO.setTargetCategory(2);
//			targetThingAddDTO.setTargetType(caseeLiQuipLedge.getTargetType());
			//明细信息
			TargetThingLiQuiDateDetail targetThingLiQuiDateDetail=new TargetThingLiQuiDateDetail();
			targetThingLiQuiDateDetail.setOwner(caseeLiQuipLedge.getOwner());
			targetThingLiQuiDateDetail.setExecutedUnifiedIdentity(caseeLiQuipLedge.getExecutedUnifiedIdentity());
//			targetThingAddDTO.setDescribes(caseeLiQuipLedge.getRemark());
			//添加地址
			Address address=new Address();
			address.setProvince(caseeLiQuipLedge.getProvince());
			address.setCity(caseeLiQuipLedge.getCity());
			address.setArea(caseeLiQuipLedge.getArea());
			address.setInformationAddress(caseeLiQuipLedge.getInformationAddress());
			address.setCode(caseeLiQuipLedge.getCode());
			R rest=remoteAddressService.saveAddress(address, SecurityConstants.FROM);
			if(rest!=null && rest.getData()!=null){
				address= objectMapper.convertValue( rest.getData(), Address.class);
//				targetThingAddDTO.setAddressId(address.getAddressId());
			}
			TargetBizLiqui targetBizLiqui = new TargetBizLiqui();
			BeanCopyUtil.copyBean(targetThingAddDTO,targetBizLiqui);
			targetBizLiqui.setTargetDetail(JsonUtils.objectToJson(targetThingLiQuiDateDetail));
			targetBizLiqui.setCreateOutlesId(caseeBizLiquiDTO.getCreateOutlesId());
			targetBizLiqui.setCreateInsId(caseeBizLiquiDTO.getCreateInsId());
			targetBizLiquiList.add(targetBizLiqui);

		}

		targetBizLiquiService.saveBatch(targetBizLiquiList);
		//循环标的物 添加任务
		for (TargetBizLiqui targetBizLiqui : targetBizLiquiList) {
			//根据模板id生成任务
//			targetBizLiquiService.configurationNodeTemplate(targetBizLiqui,outlesVO.getData().getTemplateId());
			//添加标的物损毁、灭失任务
//			targetBizLiquiService.addTargetThingTask(targetBizLiqui);
		}



		//添加委托机构和网点信息
		CaseeOutlesDealRe entrustCaseeOutlesDealRe =new CaseeOutlesDealRe();
		entrustCaseeOutlesDealRe.setInsId(caseeBizLiquiDTO.getEntrustInsId());
		entrustCaseeOutlesDealRe.setOutlesId(caseeBizLiquiDTO.getEntrustOutlesId());
		entrustCaseeOutlesDealRe.setType(1);//1-委托机构
		entrustCaseeOutlesDealRe.setCanDeal(0);//是否可办理（0-不可办理，1-可办理）
		caseeBizLiquiDTO.getOutlesList().add(entrustCaseeOutlesDealRe);
		//添加案件id
		for (CaseeOutlesDealRe caseeOutlesDealRe : caseeBizLiquiDTO.getOutlesList()) {
			caseeOutlesDealRe.setCaseeId(caseeBizLiqui.getCaseeId());
		}
		caseeOutlesDealReService.saveBatch(caseeBizLiquiDTO.getOutlesList());


		//添加申请人（委托机构）
		if (caseeBizLiquiDTO.getProposersNames() != null) {
			List<CaseePersonnel> CaseePersonnelList = new ArrayList<>();
			CaseePersonnel personnelProposers = new CaseePersonnel();
			personnelProposers.setName(caseeBizLiquiDTO.getProposersNames());
			CaseePersonnelList.add(personnelProposers);
			addCaseePersonnelRe(CaseePersonnelList, caseeBizLiqui.getCaseeId(), 0);
		}

		//添加被执行人
		if (caseeBizLiquiDTO.getExecutedList() != null && caseeBizLiquiDTO.getExecutedList().size() != 0) {
			addCaseePersonnelRe(caseeBizLiquiDTO.getExecutedList(), caseeBizLiqui.getCaseeId(), 1);
		}
		//添加担保人
		if (caseeBizLiquiDTO.getBondsmanList() != null && caseeBizLiquiDTO.getBondsmanList().size() != 0) {
			addCaseePersonnelRe(caseeBizLiquiDTO.getBondsmanList(), caseeBizLiqui.getCaseeId(), 2);
		}
		//1，创建processEngine对象
		ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
		//2，得到repositoryService实例
		RepositoryService repositoryService = processEngine.getRepositoryService();
		//查询流程部署信息
		Deployment deployment = repositoryService.createDeploymentQuery().deploymentName("案件标的审批流程").singleResult();

		TaskNode taskNode=new TaskNode();
		taskNode.setActDeploymentId(deployment.getId());
		taskNode.setNodeId(CaseeOrTargetTaskFlowConstants.CASEEID_OPEN+caseeBizLiqui.getCaseeId());
		taskNode.setNodeKey(CaseeOrTargetTaskFlowConstants.CASEENODEKEY_OPEN);
		taskNode.setNodeName("清收案件开启");
		taskNode.setNeedAudit(1);
		taskNode.setInsType(1200);
		taskNode.setNodeType(100);
		taskNode.setInsId(caseeBizLiqui.getCreateInsId());
		taskNode.setOutlesId(caseeBizLiqui.getCreateOutlesId());
		taskNode.setCaseeId(caseeBizLiqui.getCaseeId());
		taskNodeService.save(taskNode);

		taskNode.setNodeId(CaseeOrTargetTaskFlowConstants.CASEEID_CLOSE+caseeBizLiqui.getCaseeId());
		taskNode.setNodeKey(CaseeOrTargetTaskFlowConstants.CASEENODEKEY_CLOSE);
		taskNode.setNodeName("清收案件结案");
		taskNode.setNodeType(101);
		taskNodeService.save(taskNode);

		taskNode.setNodeId(CaseeOrTargetTaskFlowConstants.CASEEID_SUSPEND+caseeBizLiqui.getCaseeId());
		taskNode.setNodeKey(CaseeOrTargetTaskFlowConstants.CASEENODEKEY_SUSPEND);
		taskNode.setNodeName("清收案件中止");
		taskNode.setNodeType(102);
		taskNodeService.save(taskNode);

		taskNode.setNodeId(CaseeOrTargetTaskFlowConstants.CASEEID_POSTPONE+caseeBizLiqui.getCaseeId());
		taskNode.setNodeKey(CaseeOrTargetTaskFlowConstants.CASEENODEKEY_POSTPONE);
		taskNode.setNodeName("清收案件暂缓");
		taskNode.setNodeType(103);
		taskNodeService.save(taskNode);

		taskNode.setNodeId(CaseeOrTargetTaskFlowConstants.CASEEID_REPAYMENTDETAILS+caseeBizLiqui.getCaseeId());
		taskNode.setNodeKey(CaseeOrTargetTaskFlowConstants.CASEENODEKEY_REPAYMENTDETAILS);
		taskNode.setNodeName("清收案件还款详情");
		taskNode.setNodeType(104);
		taskNode.setNeedCost(1);
		taskNodeService.save(taskNode);

		//添加清收案件还款详情绩效信息
		CaseePerformance caseePerformance=new CaseePerformance();
		BeanCopyUtil.copyBean(taskNode,caseePerformance);
		caseePerformance.setPerfScore(5);
		BigDecimal perfAmount=new BigDecimal("20");
		caseePerformance.setPerfAmount(perfAmount);
		caseePerformanceService.save(caseePerformance);

		return caseeBizLiqui;
	}

	@Override
	@Transactional
	public CaseeBizLiqui updateCasee(CaseeBizLiquiDTO caseeDto) {
		CaseeBizLiqui caseeBizLiqui = new CaseeBizLiqui();
		BeanUtils.copyProperties(caseeDto, caseeBizLiqui);
		this.baseMapper.updateById(caseeBizLiqui);
//		//修改案件机构关联
//		LambdaQueryWrapper<CaseeOutlesDealRe> caseeOutlesDealReQueryWrapper = new LambdaQueryWrapper<>();
//		caseeOutlesDealReQueryWrapper.eq(CaseeOutlesDealRe::getDelFlag, 0).eq(CaseeOutlesDealRe::getCaseeId, caseeBizLiqui.getCaseeId());
//		caseeOutlesDealReService.remove(caseeOutlesDealReQueryWrapper);
//		if (caseeDto.getOutlesList() != null && caseeDto.getOutlesList().size() != 0) {
//			for (CaseeOutlesDealRe caseeOutlesDealRe : caseeDto.getOutlesList()) {
//				caseeOutlesDealRe.setCaseeId(caseeBizLiqui.getCaseeId());
//			}
//			caseeOutlesDealReService.saveBatch(caseeDto.getOutlesList());
//		}

		//添加申请人（机构）
		if (caseeDto.getProposersNames() != null) {
			List<CaseePersonnel> CaseePersonnelList = new ArrayList<>();
			CaseePersonnel personnelProposers = new CaseePersonnel();
			personnelProposers.setName(caseeDto.getProposersNames());
			CaseePersonnelList.add(personnelProposers);
			updateCaseePersonnelRe(CaseePersonnelList, caseeBizLiqui.getCaseeId(), 0);
		}

		//添加被执行人
		if (caseeDto.getExecutedList() != null && caseeDto.getExecutedList().size() != 0) {
			updateCaseePersonnelRe(caseeDto.getExecutedList(), caseeBizLiqui.getCaseeId(), 1);
		}
		//添加担保人
		if (caseeDto.getBondsmanList() != null && caseeDto.getBondsmanList().size() != 0) {
			updateCaseePersonnelRe(caseeDto.getBondsmanList(), caseeBizLiqui.getCaseeId(), 2);
		}


		return caseeBizLiqui;
	}
	@Transactional
	public void addCaseePersonnelRe(List<CaseePersonnel> caseePersonnel, int caseeId, int type){
		List<CaseePersonnelRe> caseePersonnelReList = new ArrayList<>();
		List<Subject> subjectList = new ArrayList<>();
		//新增案件人员
		caseePersonnelService.saveBatch(caseePersonnel);
		for (int i=0;i<caseePersonnel.size();i++) {
			//添加关联
			CaseePersonnelRe caseePersonnelRe=new CaseePersonnelRe();
			caseePersonnelRe.setCasePersonnelId(caseePersonnel.get(i).getCasePersonnelId());
			caseePersonnelRe.setCaseeId(caseeId);
			caseePersonnelRe.setType(type);
			caseePersonnelReList.add(caseePersonnelRe);
			//添加主体信息
			if(caseePersonnel.get(i).getUnifiedIdentity()!=null){
				Subject subject=new Subject();
				subject.setUnifiedIdentity(caseePersonnel.get(i).getUnifiedIdentity());
				subject.setPhone(caseePersonnel.get(i).getPhone());
				subject.setName(caseePersonnel.get(i).getName());
				subjectList.add(subject);
			}
		}
		remoteSubjectService.saveBatchSubject(subjectList, SecurityConstants.FROM);
		caseePersonnelReService.saveBatch(caseePersonnelReList);
	}
	@Transactional
	public void updateCaseePersonnelRe(List<CaseePersonnel> caseePersonnel, int caseeId, int type){
		List<CaseePersonnelRe> caseePersonnelReList = new ArrayList<>();
		List<Subject> subjectList = new ArrayList<>();
		//新增案件人员
		caseePersonnelService.saveBatch(caseePersonnel);
		//重新添加关联信息
		LambdaQueryWrapper<CaseePersonnelRe> caseeOutlesDealReQueryWrapper=new LambdaQueryWrapper<>();
		caseeOutlesDealReQueryWrapper.eq(CaseePersonnelRe::getDelFlag,0).eq(CaseePersonnelRe::getCaseeId,caseeId);
		caseePersonnelReService.remove(caseeOutlesDealReQueryWrapper);
		for (int i=0;i<caseePersonnel.size();i++) {
			//添加关联
			CaseePersonnelRe caseePersonnelRe=new CaseePersonnelRe();
			caseePersonnelRe.setCasePersonnelId(caseePersonnel.get(i).getCasePersonnelId());
			caseePersonnelRe.setCaseeId(caseeId);
			caseePersonnelRe.setType(type);
			caseePersonnelReList.add(caseePersonnelRe);
			//添加主体信息
			if(caseePersonnel.get(i).getUnifiedIdentity()!=null&&!caseePersonnel.get(i).getUnifiedIdentity().equals("")){
				Subject subject=new Subject();
				subject.setUnifiedIdentity(caseePersonnel.get(i).getUnifiedIdentity());
				subject.setPhone(caseePersonnel.get(i).getPhone());
				subject.setName(caseePersonnel.get(i).getName());
				subjectList.add(subject);
			}
		}

		remoteSubjectService.saveBatchSubject(subjectList, SecurityConstants.FROM);
		caseePersonnelReService.saveBatch(caseePersonnelReList);
	}

	@Override
	public com.pig4cloud.pig.casee.vo.CaseePersonnelVO queryCaseeAssistOutlets(Integer caseeId){
		com.pig4cloud.pig.casee.vo.CaseePersonnelVO caseePersonnelVO = new com.pig4cloud.pig.casee.vo.CaseePersonnelVO();
		// 查询协办网点列表
		List<CaseeOutlesDealReVo> outlesDealResList = caseeOutlesDealReService.queryCaseeOutlesDealRe(caseeId,null,null,2);
		// 查询受托详情
		CaseeDetailsVO entrustedDetails = this.baseMapper.selectEntrustedDetails(caseeId);
		// 查询委托详情
		CaseeDetailsVO commissionDetails = this.baseMapper.selectCommissionDetails(caseeId);

		caseePersonnelVO.setOutlesDealResList(outlesDealResList);
		caseePersonnelVO.setCommissionDetails(commissionDetails);
		caseePersonnelVO.setEntrustedDetails(entrustedDetails);
		return caseePersonnelVO;
	}

	@Override
	public com.pig4cloud.pig.casee.vo.CaseePersonnelVO queryCaseeDetails(Integer caseeId){

		com.pig4cloud.pig.casee.vo.CaseePersonnelVO caseePersonnelVO = new com.pig4cloud.pig.casee.vo.CaseePersonnelVO();
		// 查询被执行人列表
		List<CaseePersonnelTypeVO> executedPersonList = caseePersonnelService.queryByCaseeId(caseeId,1);
		// 查询担保人列表
		List<CaseePersonnelTypeVO> guarantorList = caseePersonnelService.queryByCaseeId(caseeId,2);
		caseePersonnelVO.setExecutedPersonList(executedPersonList);
		caseePersonnelVO.setGuarantorList(guarantorList);

		//查询案件信息
		caseePersonnelVO.setCaseeBizLiqui(this.baseMapper.selectCaseeBizLiquiDetail(caseeId));

		return caseePersonnelVO;
	}

	@Override
	public CaseeBizLiquiVO queryAuditCaseeDetails(Integer caseeId) {
		return this.baseMapper.queryAuditCaseeDetails(caseeId);
	}

	@Override
	public CaseeBizLiqui updateProperty(CaseeBizLiquiDTO caseeBizLiquiDTO){
		LambdaQueryWrapper<CaseeBizBase> caseeBizBaseLambdaQueryWrapper=new LambdaQueryWrapper<>();
		caseeBizBaseLambdaQueryWrapper.eq(CaseeBizBase::getCaseeId,caseeBizLiquiDTO.getCaseeId()).eq(CaseeBizBase::getDelFlag,"0");
		CaseeBizBase caseeBizBase=caseeBizService.getOne(caseeBizBaseLambdaQueryWrapper);
		caseeBizBase.setCaseeDetail(caseeBizLiquiDTO.getCaseeDetail());
		this.baseMapper.updateById(caseeBizBase);
		//转换为CaseeBizLiqui
		CaseeBizLiqui caseeBizLiqui=CaseeBizLiqui.genFromCaseeBizBase(caseeBizBase);
		return caseeBizLiqui;
	}

	@Transactional
	@Override
	public Boolean updateOutles(CaseeBizLiquiDTO caseeBizLiquiDTO){
		LambdaQueryWrapper<CaseeOutlesDealRe> caseeOutlesDealReQueryWrapper = new LambdaQueryWrapper<>();
		caseeOutlesDealReQueryWrapper.eq(CaseeOutlesDealRe::getDelFlag, 0).eq(CaseeOutlesDealRe::getCaseeId, caseeBizLiquiDTO.getCaseeId()).eq(CaseeOutlesDealRe::getType,"2");
		caseeOutlesDealReService.remove(caseeOutlesDealReQueryWrapper);
			for (CaseeOutlesDealRe caseeOutlesDealRe : caseeBizLiquiDTO.getOutlesList()) {
				caseeOutlesDealRe.setCaseeId(caseeBizLiquiDTO.getCaseeId());
			}

		return caseeOutlesDealReService.saveBatch(caseeBizLiquiDTO.getOutlesList());
	}

	@Override
	@Transactional
	public Integer modifyCaseStatus(Integer caseeId,Integer status,String explain){
		String statusName = null;
		TaskNode taskNode=null;
		if(status==1){
			statusName = "开启";
			taskNode = taskNodeService.getById(CaseeOrTargetTaskFlowConstants.CASEEID_OPEN + caseeId);
		}else if(status == 2){
			statusName = "暂缓";
			taskNode = taskNodeService.getById(CaseeOrTargetTaskFlowConstants.CASEEID_POSTPONE + caseeId);
		}else if(status == 3){
			statusName = "中止";
			taskNode = taskNodeService.getById(CaseeOrTargetTaskFlowConstants.CASEEID_SUSPEND + caseeId);
		}
		// 消息推送
		List<CaseeOutlesDealRe> caseeOutlesDealReList = caseeBizService.messagePush(caseeId, null, statusName, null, explain);

		//启动任务流设置审核人
		caseeBizService.caseeOrTagetFlowStart(taskNode,caseeOutlesDealReList,explain);

		return caseeBizService.modifyCaseStatus(caseeId,status);
	}

	@Override
	public Integer caseClosedOpen(Integer caseeId, LocalDateTime closeTime, String explain){
		//查询当前节点信息
		TaskNode taskNode = taskNodeService.getById(CaseeOrTargetTaskFlowConstants.CASEEID_CLOSE + caseeId);

		// 消息推送
		List<CaseeOutlesDealRe> caseeOutlesDealReList = caseeBizService.messagePush(caseeId, null, "结案", closeTime, explain);

		//启动任务流设置审核人
		caseeBizService.caseeOrTagetFlowStart(taskNode,caseeOutlesDealReList,explain);

		return caseeBizService.caseClosedOpen(caseeId,closeTime);
	}

	@Override
	public Integer confirmCaseStatus(CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO,Integer statusConfirm){
		if (securityUtilsService.getCacheUser().getOutlesId()==null){
			return -1;
		}
		String explain=caseeOrTargetTaskFlowDTO.getDescribed();

		if(explain!=null && !explain.equals("")){
			explain = "情况说明："+explain+"。";
		}else{
			explain = "";
		}
		String statusName = null;
		if(statusConfirm==2){
			statusName = "已确认";
		}else if(statusConfirm == 3){
			statusName = "已拒绝";
		}
		CaseeBizBase caseeBizBase = caseeBizService.getById(caseeOrTargetTaskFlowDTO.getCaseeId());
		if(Objects.nonNull(caseeBizBase)){
			PigUser pigUser = securityUtilsService.getCacheUser();
			// 公司业务案号
			String companyCode = caseeBizBase.getCompanyCode();
			// 消息标题
			String messageTitle = "案件状态确认通知";
			// 推送内容
			String messageContent = "办理的案号为"+companyCode+"，案件状态申请"+statusName+"。"+explain+"请知悉！";
			MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
			messageRecordDTO.setCreateBy(pigUser.getId());
			messageRecordDTO.setCreateTime(LocalDateTime.now());
			// 300=清收消息
			messageRecordDTO.setMessageType(300);
			messageRecordDTO.setMessageTitle(messageTitle);
			messageRecordDTO.setMessageContent(messageContent);
			messageRecordDTO.setReceiverInsId(caseeBizBase.getCreateInsId());
			messageRecordDTO.setReceiverOutlesId(caseeBizBase.getCreateOutlesId());
			messageRecordDTO.setReceiverUserId(caseeBizBase.getUserId());
			List<MessageRecordDTO>messageRecordDTOList=new ArrayList<>();
			messageRecordDTOList.add(messageRecordDTO);
			// 保存消息通知
			remoteMessageRecordService.batchSendMessageRecordOutPush(messageRecordDTOList,SecurityConstants.FROM);
		}
		return caseeBizService.confirmCaseStatus(caseeOrTargetTaskFlowDTO,statusConfirm);
	}
	@Override
	public Integer addRepaymentDetail(CaseeRepaymentAddDTO caseeRepaymentAddDTO) throws Exception{
		CaseeBizBase caseeBizBase=this.baseMapper.selectById(caseeRepaymentAddDTO.getCaseeId());
		if(caseeBizBase==null){
			throw new Exception("案件id为空 请联系管理员");
		}
		CaseeBizLiqui caseeBizLiqui= CaseeBizLiqui.genFromCaseeBizBase(caseeBizBase);
		caseeBizLiqui.initCaseeLiquiDetail();
		BeanUtils.copyProperties(caseeRepaymentAddDTO,caseeBizLiqui.getCaseeLiquiDetail());
		caseeBizLiqui.setCaseeDetail(JsonUtils.objectToJson(caseeBizLiqui.getCaseeLiquiDetail()));

		//查询还款详情任务信息
		TaskNode taskNode = taskNodeService.getById(CaseeOrTargetTaskFlowConstants.CASEEID_REPAYMENTDETAILS + caseeRepaymentAddDTO.getCaseeId());
		//启动任务流设置审核人
		caseeBizService.caseeOrTagetFlowStart(taskNode,null,null);
		return this.baseMapper.updateById(caseeBizLiqui);
	}


}
