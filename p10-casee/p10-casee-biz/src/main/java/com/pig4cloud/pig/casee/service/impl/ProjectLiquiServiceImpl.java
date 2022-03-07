/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.casee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.admin.api.vo.UserVO;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.assets.AssetsReCasee;
import com.pig4cloud.pig.casee.entity.assets.detail.AssetsReCaseeDetail;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.ProjectLiQuiDetail;
import com.pig4cloud.pig.casee.mapper.AssetsBankLoanReMapper;
import com.pig4cloud.pig.casee.mapper.ProjectLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.*;
import com.pig4cloud.pig.casee.vo.count.*;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 清收项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@Service
public class ProjectLiquiServiceImpl extends ServiceImpl<ProjectLiquiMapper, Project> implements ProjectLiquiService {

	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Autowired
	private TransferRecordLiquiService transferRecordLiquiService;

	@Autowired
	private ProjectOutlesDealReService projectOutlesDealReService;

	@Autowired
	private RemoteUserService userService;

	@Autowired
	private ProjectStatusService projectStatusService;

	@Autowired
	private ProjectSubjectReService projectSubjectReService;

	@Autowired
	private SubjectBankLoanReService subjectBankLoanReService;

	@Autowired
	private SecurityUtilsService securityUtilsService;

	@Autowired
	private AssetsReLiquiService assetsReCaseeService;

	@Autowired
	private AssetsBankLoanReMapper assetsBankLoanReMapper;

	@Autowired
	private CaseeLiquiService caseeLiquiService;

	@Autowired
	private RemoteSubjectService remoteSubjectService;

	@Autowired
	private ExpenseRecordService expenseRecordService;

	@Autowired
	private ExpenseRecordSubjectReService expenseRecordSubjectReService;

	@Autowired
	private PaymentRecordService paymentRecordService;

	@Autowired
	private BehaviorLiquiService behaviorLiquiService;

	@Override
	public IPage<ProjectLiquiPageVO> queryPageLiqui(Page page, ProjectLiquiPageDTO projectLiquiPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPageLiqui(page,projectLiquiPageDTO,insOutlesDTO);
	}

	@Override
	@Transactional
	public Integer addProjectLiqui(ProjectLiquiAddDTO projectLiquiAddVO){
		ProjectLiqui projectLiqui = new ProjectLiqui();
		BeanCopyUtil.copyBean(projectLiquiAddVO,projectLiqui);
		// 查询银行借贷和移送记录表
		TransferRecordBankLoanVO transferRecordBankLoanVO = transferRecordLiquiService.getTransferRecordBankLoan(projectLiquiAddVO.getTransferRecordId(),null);
		projectLiqui.setInsId(transferRecordBankLoanVO.getEntrustedInsId());
		projectLiqui.setOutlesId(transferRecordBankLoanVO.getEntrustedOutlesId());
		projectLiqui.setProjectType(100);
		ProjectLiQuiDetail projectLiQuiDetail = new ProjectLiQuiDetail();
		projectLiQuiDetail.setProjectAmount(transferRecordBankLoanVO.getTransferRecordLiquiDetail().getHandoverAmount());
		projectLiQuiDetail.setRepaymentAmount(BigDecimal.valueOf(0));
		projectLiqui.setProjectLiQuiDetail(projectLiQuiDetail);
		projectLiqui.setProposersNames(transferRecordBankLoanVO.getInsName());
		projectLiqui.setSubjectPersons(transferRecordBankLoanVO.getSubjectName());
		this.baseMapper.insert(projectLiqui);

		//查询银行借贷债务人信息
		List<Integer> subjectIdList = subjectBankLoanReService.selectSubjectId(transferRecordBankLoanVO.getSourceId());
		R<List<Subject>> result = remoteSubjectService.queryBySubjectIdList(subjectIdList, SecurityConstants.FROM);
		//添加费用产生记录
		ExpenseRecord expenseRecord=new ExpenseRecord();
		expenseRecord.setProjectId(projectLiqui.getProjectId());
		expenseRecord.setCostType(30001);
		expenseRecord.setCostIncurredTime(projectLiqui.getTakeTime());
		expenseRecord.setCostAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount());
		expenseRecord.setStatus(0);
		expenseRecord.setCompanyCode(projectLiqui.getCompanyCode());
		expenseRecordService.save(expenseRecord);

		//添加费用记录关联主体信息
		ExpenseRecordSubjectRe expenseRecordSubjectRe=new ExpenseRecordSubjectRe();
		for (Subject subject : result.getData()) {
			expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
			expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordSubjectReService.save(expenseRecordSubjectRe);
		}

		// 保存项目状态变更记录表
		ProjectStatus projectStatus = new ProjectStatus();
		projectStatus.setStatusName("在办");
		projectStatus.setUserName(projectLiquiAddVO.getUserNickName());
		projectStatus.setType(1);
		projectStatus.setSourceId(projectLiqui.getProjectId());
		projectStatusService.save(projectStatus);

		// 查询银行借贷主体关联表
		QueryWrapper<SubjectBankLoanRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SubjectBankLoanRe::getBankLoanId,transferRecordBankLoanVO.getSourceId());
		List<SubjectBankLoanRe> subjectBankLoanReList =  subjectBankLoanReService.list(queryWrapper);
		List<ProjectSubjectRe> projectSubjectRes = new ArrayList<>();
		// 遍历银行借贷关联表copy到项目主体关联表中
		subjectBankLoanReList.stream().forEach(item ->{
			ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
			projectSubjectRe.setSubjectId(item.getSubjectId());
			projectSubjectRe.setProjectId(projectLiqui.getProjectId());
			projectSubjectRe.setType(item.getDebtType());
			projectSubjectRes.add(projectSubjectRe);
		});
		R<Subject> subject = remoteSubjectService.getByInsId(transferRecordBankLoanVO.getInsId(),SecurityConstants.FROM);
		ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
		projectSubjectRe.setSubjectId(subject.getData().getSubjectId());
		projectSubjectRe.setProjectId(projectLiqui.getProjectId());
		projectSubjectRe.setType(0);
		projectSubjectRes.add(projectSubjectRe);


		// 保存项目主体关联表
		projectSubjectReService.saveBatch(projectSubjectRes);

		// 抵押情况0=有
		if(transferRecordBankLoanVO.getMortgageSituation()==0){
			// 查询银行借贷抵押财产
			List<AssetsInformationVO> assetsInformationVOS = assetsBankLoanReMapper.getAssetsBankLoanRe(transferRecordBankLoanVO.getSourceId());
			List<AssetsReCasee> assetsReCaseeList = new ArrayList<>();
			assetsInformationVOS.stream().forEach(item ->{
				AssetsReCasee assetsReCasee = new AssetsReCasee();
				assetsReCasee.setAssetsId(item.getAssetsId());
				assetsReCasee.setSubjectId(item.getSubjectId());
				assetsReCasee.setProjectId(projectLiqui.getProjectId());
				// 案件来源1=抵押财产
				assetsReCasee.setAssetsSource(1);
				AssetsReCaseeDetail assetsReCaseeDetail = new AssetsReCaseeDetail();
				assetsReCaseeDetail.setMortgagee(0);
				assetsReCasee.setAssetsReCaseeDetail(assetsReCaseeDetail);
				assetsReCaseeList.add(assetsReCasee);
				assetsReCaseeService.save(assetsReCasee);
			});
		}

		// 保存项目委托关联表
		ProjectOutlesDealRe projectOutlesDealRe = new ProjectOutlesDealRe();
		projectOutlesDealRe.setInsId(transferRecordBankLoanVO.getInsId());
		projectOutlesDealRe.setOutlesId(transferRecordBankLoanVO.getOutlesId());
		projectOutlesDealRe.setUserId(transferRecordBankLoanVO.getCreateBy());
		projectOutlesDealRe.setType(1);
		projectOutlesDealRe.setProjectId(projectLiqui.getProjectId());
		projectOutlesDealReService.save(projectOutlesDealRe);
		// 返回添加的项目id
		return projectLiqui.getProjectId();
	}

	@Override
	public ProjectLiquiDetailsVO getProjectDetails(Integer projectId){
		// 获取项目基本信息及办理机构名称和网点名称
		ProjectLiquiDetailsVO projectLiquiDetailsVO = this.baseMapper.selectByProjectId(projectId);


		// 查询银行借贷和移送记录表
		TransferRecordBankLoanVO transferRecordBankLoanVO = transferRecordLiquiService.getTransferRecordBankLoan(null,projectId);
		projectLiquiDetailsVO.setTransferRecordBankLoanVO(transferRecordBankLoanVO);

		ProjectSubjectDTO projectSubjectDTO = new ProjectSubjectDTO();
		projectSubjectDTO.setProjectId(projectId);
		// 债务人列表
		List<ProjectSubjectVO> projectSubjectVOList = this.baseMapper.selectProjectSubject(projectSubjectDTO);
		projectLiquiDetailsVO.setProjectSubjectVOList(projectSubjectVOList);

//		assetsBankLoanReMapper.getAssetsBankLoanRe(transferRecordBankLoanVO.getSourceId());

		return projectLiquiDetailsVO;
	}

	@Override
	@Transactional
	public Integer modifyStatusById(ProjectModifyStatusDTO projectModifyStatusDTO){
		ProjectLiqui projectLiqui = new ProjectLiqui();
		projectLiqui.setProjectId(projectModifyStatusDTO.getProjectId());
		projectLiqui.setStatus(projectModifyStatusDTO.getStatus());

		// 保存项目状态变更记录表
		ProjectStatus projectStatus = new ProjectStatus();
		projectStatus.setType(1);
		projectStatus.setSourceId(projectModifyStatusDTO.getProjectId());
		// 获取操作人用户名称
		R<UserVO> userVOR = userService.getUserById(securityUtilsService.getCacheUser().getId(),SecurityConstants.FROM);
		projectStatus.setUserName(userVOR.getData().getActualName());

		BeanCopyUtil.copyBean(projectModifyStatusDTO,projectStatus);
		projectStatusService.save(projectStatus);

		return this.baseMapper.updateById(projectLiqui);
	}

	@Override
	public ProjectLiqui getByProjectId(Integer projectId){
		return this.baseMapper.getByProjectId(projectId);
	}

	@Override
	public IPage<ProjectLiquiOrBehaviorPageVO> queryPageProjectLiqui(Page page, Integer subjectId) {
		return this.baseMapper.queryPageProjectLiqui(page, subjectId);
	}

	@Override
	public List<ProjectSubjectVO> queryProjectSubjectList(ProjectSubjectDTO projectSubjectDTO){
		return this.baseMapper.selectProjectSubject(projectSubjectDTO);
	}

	@Override
	public ProjectLiquiDealtVO queryDealt(Integer projectId){
		ProjectLiquiDealtVO projectLiquiDealtVO = new ProjectLiquiDealtVO();
		// 查询项目基本详情
		ProjectLiquiDetailsVO projectLiquiDetailsVO = this.baseMapper.getByProjectId(projectId);
		projectLiquiDealtVO.setProjectLiqui(projectLiquiDetailsVO);
		projectLiquiDealtVO.setLitigation(projectLiquiDetailsVO.getLitigation());

		// 查询诉前阶段案件信息
		List<Integer> typeList = new ArrayList<>();
		typeList.add(1010);
		List<CaseeListVO> prePleadingList = caseeLiquiService.queryByIdList(projectId,typeList);
		projectLiquiDealtVO.setPrePleadingList(prePleadingList);

		// 查询诉讼阶段案件信息
		typeList = new ArrayList<>();
		typeList.add(2010);
		typeList.add(2020);
		typeList.add(2021);
		typeList.add(2030);
		List<CaseeListVO> litigationList = caseeLiquiService.queryByIdList(projectId,typeList);
		projectLiquiDealtVO.setLitigationList(litigationList);

		// 查询执行阶段案件信息
		typeList = new ArrayList<>();
		typeList.add(3010);
		typeList.add(3031);
		List<CaseeListVO> executeList = caseeLiquiService.queryByIdList(projectId,typeList);
		projectLiquiDealtVO.setExecuteList(executeList);

		Boolean addCaseeBtn = false;
		List<Integer> statusList = new ArrayList<>();
		statusList.add(0);
		statusList.add(1);
		CaseeLiqui caseeLiqui = caseeLiquiService.queryByStatusList(projectId,statusList);
		if(Objects.isNull(caseeLiqui)){
			addCaseeBtn = true;
		}
		projectLiquiDealtVO.setAddCaseeBtn(addCaseeBtn);

		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setProjectId(projectId);
		paymentRecord.setStatus(0);
		paymentRecord.setPaymentType(200);
		BigDecimal sumCourtPayment = paymentRecordService.sumCourtPayment(paymentRecord);
		projectLiquiDealtVO.setSumCourtPayment(sumCourtPayment);

		return projectLiquiDealtVO;
	}

	@Override
	public List<SubjectAssetsBehaviorListVO> queryAssetsBehavior(Integer projectId,Integer caseePersonnelType){
		return this.baseMapper.selectAssetsBehavior(projectId,caseePersonnelType);
	}

	@Override
	public IPage<ProjectLiquiPageVO> queryNotProcessedPage(Page page, ProjectNoProcessedDTO projectNoProcessedDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectNotProcessedPage(page,projectNoProcessedDTO,insOutlesDTO);
	}


	@Override
	public CountProjectStatisticsVO countProject(){
		CountProjectStatisticsVO projectStatisticsVO = new CountProjectStatisticsVO();
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);
		TransferRecordDTO transferRecordDTO = new TransferRecordDTO();
		transferRecordDTO.setTransferType(0);
		transferRecordDTO.setStatus(0);

		//**********待接收统计********************************
		IPage<TransferRecordBankLoanVO> transferRecordPage = transferRecordLiquiService.getTransferRecordPage(page,transferRecordDTO);
		projectStatisticsVO.setPendingCount(transferRecordPage.getTotal());


		ProjectNoProcessedDTO projectNoProcessedDTO = new ProjectNoProcessedDTO();
		IPage<ProjectLiquiPageVO> pageVOIPage = queryNotProcessedPage(page,projectNoProcessedDTO);
		projectStatisticsVO.setNotProcessedCount(pageVOIPage.getTotal());

		return projectStatisticsVO;
	}

	public Long queryCaseNodePage(String nodeKey,Integer caseeType){
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO = new CaseeLiquiFlowChartPageDTO();
		caseeLiquiFlowChartPageDTO.setStatus(1);

		caseeLiquiFlowChartPageDTO.setNodeKey(nodeKey);
		caseeLiquiFlowChartPageDTO.setCaseeType(caseeType);
		IPage<CaseeLiquiFlowChartPageVO> caseeLiquiFlowChartPageVOIPage = caseeLiquiService.queryFlowChartPage(page,caseeLiquiFlowChartPageDTO);
		return caseeLiquiFlowChartPageVOIPage.getTotal();
	}

	public Long queryCaseeCount(Integer caseeType){
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		CaseeLiquiPageDTO caseeLiquiPageDTO = new CaseeLiquiPageDTO();
		caseeLiquiPageDTO.setCaseeType(caseeType);
		caseeLiquiPageDTO.setStatus(1);
		IPage<CaseeLiquiPageVO> caseeLiquiPageVOIPage = caseeLiquiService.queryPage(page,caseeLiquiPageDTO);
		return caseeLiquiPageVOIPage.getTotal();
	}

	@Override
	public CountPreLitigationStageVO countPreLitigationStage(){
		CountPreLitigationStageVO preLitigationStageVO = new CountPreLitigationStageVO();
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		//**********诉前保全案件统计********************************
		preLitigationStageVO.setPreservationCaseCount(queryCaseeCount(1010));


		//**********未添加财产统计********************************
		CaseeLiquiPageDTO caseeLiquiPageDTO = new CaseeLiquiPageDTO();
		caseeLiquiPageDTO.setCaseeType(1010);
		caseeLiquiPageDTO.setStatus(1);
		IPage<CaseeLiquiPageVO> assetNotAddedList = caseeLiquiService.queryAssetNotAddedPage(page,caseeLiquiPageDTO);
		preLitigationStageVO.setAssetNotAddedCount(assetNotAddedList.getTotal());

		//**********立案未送达统计********************************
		preLitigationStageVO.setStartCaseeDeliveredCount(queryCaseNodePage("liQui_SQ_SQLASDQK_SQLASDQK",1010));

		//**********结案未送达统计********************************
		preLitigationStageVO.setSeizedUndoneCount(queryCaseNodePage("liQui_SQ_SQBQJGSDQK_SQBQJGSDQK",1010));

		return preLitigationStageVO;
	}

	@Override
	public CountLitigationVO countlitigation(){
		CountLitigationVO countLitigationVO = new CountLitigationVO();
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		//**********一审案件统计********************************
		countLitigationVO.setLitigationFirstInstance(queryCaseeCount(2020));

		//**********保全案件统计********************************
		countLitigationVO.setLitigationHold(queryCaseeCount(2010));

		//**********二审案件统计********************************
		countLitigationVO.setLitigationSecondInstance(queryCaseeCount(2021));

		//**********其它案件统计********************************
		countLitigationVO.setLitigationOthers(queryCaseeCount(2030));


		//***********一审节点统计Start*************************************************

		//**********立案未送达统计********************************
		countLitigationVO.setLitigationFirstInstanceStandCaseUndelivered(queryCaseNodePage("liQui_SSYS_SSYSLASDQK_SSYSLASDQK",2020));

		//**********送达未庭审********************************
		countLitigationVO.setLitigationFirstInstanceServiceNotCourtTrial(queryCaseNodePage("liQui_SSYS_SSYSTSXX_SSYSTSXX",2020));

		//**********庭审未判决********************************
		countLitigationVO.setLitigationFirstInstanceCourtTrialNotJudgment(queryCaseNodePage("liQui_SSYS_SSYSCPJG_SSYSCPJG",2020));

		//**********裁判文书未送达********************************
		countLitigationVO.setLitigationFirstInstanceJudgmentPaperworkNotService(queryCaseNodePage("liQui_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK",2020));

		//**********上诉确认********************************
		countLitigationVO.setLitigationFirstInstanceAppealConfirmation(queryCaseNodePage("liQui_SSYS_SSYSYGSSQK_SSYSYGSSQK",2020));

		//**********上诉到期未确认********************************
		CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageVO = new CaseeLiquiFlowChartPageDTO();
		caseeLiquiFlowChartPageVO.setStatus(1);
		caseeLiquiFlowChartPageVO.setNodeKey("liQui_SSYS_SSYSSSDQQR_SSYSSSDQQR");
		IPage<CaseeLiquiFlowChartPageVO> caseeLiquiFlowChartPageVOIPage = caseeLiquiService.queryLitigationFirstInstanceAppealExpired(page,caseeLiquiFlowChartPageVO);

		countLitigationVO.setLitigationFirstInstanceAppealExpired(caseeLiquiFlowChartPageVOIPage.getTotal());

		//***********一审节点统计end*************************************************

		//***********保全节点统计Start*************************************************

		//**********未添加财产统计********************************
		CaseeLiquiPageDTO caseeLiquiPageDTO = new CaseeLiquiPageDTO();
		caseeLiquiPageDTO.setCaseeType(2010);
		caseeLiquiPageDTO.setStatus(1);
		IPage<CaseeLiquiPageVO> litigationHoldServiceNotAddProperty = caseeLiquiService.queryAssetNotAddedPage(page,caseeLiquiPageDTO);
		countLitigationVO.setLitigationHoldServiceNotAddProperty(litigationHoldServiceNotAddProperty.getTotal());

		//**********立案未送达统计********************************
		countLitigationVO.setLitigationHoldStandCaseUndelivered(queryCaseNodePage("liQui_SSBQ_SSBQLASDQK_SSBQLASDQK",2010));

		//**********结案未送达统计********************************
		countLitigationVO.setLitigationHoldKnotCaseUndelivered(queryCaseNodePage("liQui_SSBQ_SSBQBQJGSDQK_SSBQBQJGSDQK",2010));

		//***********保全节点统计end*************************************************


		//***********二审节点统计Start*************************************************

		//**********立案未送达统计********************************
		countLitigationVO.setLitigationSecondInstanceStandCaseUndelivered(queryCaseNodePage("liQui_SSES_SSESLASDQK_SSESLASDQK",2021));

		//**********送达未庭审********************************
		countLitigationVO.setLitigationSecondInstanceServiceNotCourtTrial(queryCaseNodePage("liQui_SSES_SSESTSXX_SSESTSXX",2021));

		//**********庭审未判决********************************
		countLitigationVO.setLitigationSecondInstanceCourtTrialNotJudgment(queryCaseNodePage("liQui_SSES_SSESCPJG_SSESCPJG",2021));

		//**********裁判文书未送达********************************
		countLitigationVO.setLitigationSecondInstanceJudgmentPaperworkNotService(queryCaseNodePage("liQui_SSES_SSESCPWSZZSDQK_SSESCPWSZZSDQK",2021));

		//***********二审节点统计end*************************************************


		//***********其它案件节点统计Start*************************************************

		//**********立案未送达统计********************************
		countLitigationVO.setLitigationOthersStandCaseUndelivered(queryCaseNodePage("liQui_SSQT_SSQTLASDQK_SSQTLASDQK",2030));

		//**********送达未庭审********************************
		countLitigationVO.setLitigationOthersServiceNotCourtTrial(queryCaseNodePage("liQui_SSQT_SSQTTSXX_SSQTTSXX",2030));

		//**********庭审未判决********************************
		countLitigationVO.setLitigationOthersCourtTrialNotJudgment(queryCaseNodePage("liQui_SSQT_SSQTCPJG_SSQTCPJG",2030));

		//**********裁判文书未送达********************************
		countLitigationVO.setLitigationOthersJudgmentNotService(queryCaseNodePage("liQui_SSQT_SSQTCPWSZZSDQK_SSQTCPWSZZSDQK",2030));

		//***********其它案件节点统计end*************************************************

		return countLitigationVO;
	}

	@Override
	public CountFulfillVO countFulfill(){
		CountFulfillVO countFulfillVO = new CountFulfillVO();

		return countFulfillVO;
	}

	@Override
	public CountImplementVO countImplement(){
		CountImplementVO countImplementVO = new CountImplementVO();
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		//**********首执案件统计********************************
		countImplementVO.setChiefExecutive(queryCaseeCount(3010));

		//**********执恢案件统计********************************
		countImplementVO.setReinstate(queryCaseeCount(3031));

		//***********首执案件节点统计Start*************************************************

		//**********首执立案未送达********************************
		countImplementVO.setChiefExecutiveStandCaseUndelivered(queryCaseNodePage("liQui_ZXSZ_ZXSZLASDQK_ZXSZLASDQK",3010));


		//***********首执案件节点统计end*************************************************


		//***********执恢案件节点统计Start*************************************************


		//**********恢复执行待立案********************************
		CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO = new CaseeLiquiFlowChartPageDTO();
		IPage<CaseeLiquiFlowChartPageVO> caseeLiquiFlowChartPageVOIPage = caseeLiquiService.queryAddReinstatementCase(page,caseeLiquiFlowChartPageDTO);
		countImplementVO.setReinstateResumeExecutionTreatFileACase(caseeLiquiFlowChartPageVOIPage.getTotal());

		//**********执恢立案未送达********************************
		countImplementVO.setReinstateStandCaseUndelivered(queryCaseNodePage("liQui_ZXZH_ZXZHLASDQK_ZXZHLASDQK",3031));

		//**********到款未拨付********************************
		IPage<CaseeLiquiFlowChartPageVO> courtPayment = caseeLiquiService.queryCourtPayment(page,caseeLiquiFlowChartPageDTO);
		countImplementVO.setPaymentNotPaid(courtPayment.getTotal());

		//**********结清未实际执结********************************
		IPage<CaseeLiquiFlowChartPageVO> paymentCompleted = caseeLiquiService.queryPaymentCompleted(page,caseeLiquiFlowChartPageDTO);
		countImplementVO.setSettleNotActualExecution(paymentCompleted.getTotal());

		//***********执恢案件节点统计end*************************************************


		return countImplementVO;
	}

	public Long queryBehaviorNodePage(String nodeKey,Integer type){
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		BehaviorLiquiDebtorPageDTO caseeLiquiFlowChartPageDTO = new BehaviorLiquiDebtorPageDTO();

		caseeLiquiFlowChartPageDTO.setNodeKey(nodeKey);
		caseeLiquiFlowChartPageDTO.setType(type);
		IPage<CaseeLiquiDebtorPageVO> caseeLiquiDebtorPageVOIPage = behaviorLiquiService.queryDebtorPage(page,caseeLiquiFlowChartPageDTO);
		return caseeLiquiDebtorPageVOIPage.getTotal();
	}


	@Override
	public CountDebtorVO countDebtor(){
		CountDebtorVO countDebtorVO = new CountDebtorVO();
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		//**********行为未限制********************************
		CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO = new CaseeLiquiFlowChartPageDTO();
		caseeLiquiFlowChartPageDTO.setType(200);
		IPage<CaseeLiquiFlowChartPageVO> notAddBehavior = caseeLiquiService.queryNotAddBehavior(page,caseeLiquiFlowChartPageDTO);
		countDebtorVO.setBehaviorNotLimit(notAddBehavior.getTotal());

		//**********限制未送达********************************
		countDebtorVO.setLimitNotService(queryBehaviorNodePage("limit_XWXZ_XWXZSDQK_XWXZSDQK",100));

		//**********行为违法未提交********************************
		caseeLiquiFlowChartPageDTO.setType(100);
		IPage<CaseeLiquiFlowChartPageVO> actIllegalNotSubmitted = caseeLiquiService.queryNotAddBehavior(page,caseeLiquiFlowChartPageDTO);
		countDebtorVO.setActIllegalNotSubmitted(actIllegalNotSubmitted.getTotal());

		//**********制裁申请未实施********************************
		countDebtorVO.setSanctionApplyNotImplemented(queryBehaviorNodePage("beIllegal_XWWF_SSXWWF_SSXWWF",200));

		//**********已结清限制未撤销********************************
		BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO = new BehaviorLiquiDebtorPageDTO();
		IPage<CaseeLiquiDebtorPageVO> alreadySettleLimitNotRevoked = behaviorLiquiService.behaviorPaymentCompleted(page,behaviorLiquiDebtorPageDTO);
		countDebtorVO.setAlreadySettleLimitNotRevoked(alreadySettleLimitNotRevoked.getTotal());

		//**********未添加财产********************************
		caseeLiquiFlowChartPageDTO = new CaseeLiquiFlowChartPageDTO();
		IPage<CaseeLiquiFlowChartPageVO> notAddProperty = caseeLiquiService.caseeSubjectNotAddAssets(page,caseeLiquiFlowChartPageDTO);
		countDebtorVO.setNotAddProperty(notAddProperty.getTotal());

		return countDebtorVO;
	}

	@Override
	public CountPropertySearchVO countPropertySearch(){
		CountPropertySearchVO countPropertySearchVO = new CountPropertySearchVO();

		return countPropertySearchVO;
	}

	@Override
	public CountAuctionPropertyVO countAuctionProperty(){
		CountAuctionPropertyVO countAuctionPropertyVO = new CountAuctionPropertyVO();

		return countAuctionPropertyVO;
	}


}
