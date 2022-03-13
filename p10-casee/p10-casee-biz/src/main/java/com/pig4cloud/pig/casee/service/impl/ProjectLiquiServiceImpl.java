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
import com.pig4cloud.pig.casee.dto.count.CountLineChartColumnarChartDTO;
import com.pig4cloud.pig.casee.dto.count.CountPolylineLineChartDTO;
import com.pig4cloud.pig.casee.dto.count.ExpirationReminderDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.assets.AssetsReLiqui;
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
import com.pig4cloud.pig.common.core.util.GetDifference;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
	private AssetsReLiquiService assetsReLiquiService;

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

	@Autowired
	private ReconciliatioMediationLiquiService reconciliatioMediationService;

	@Override
	public IPage<ProjectLiquiPageVO> queryPageLiqui(Page page, ProjectLiquiPageDTO projectLiquiPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPageLiqui(page, projectLiquiPageDTO, insOutlesDTO);
	}

	@Override
	@Transactional
	public Integer addProjectLiqui(ProjectLiquiAddDTO projectLiquiAddVO) {
		ProjectLiqui projectLiqui = new ProjectLiqui();
		BeanCopyUtil.copyBean(projectLiquiAddVO, projectLiqui);
		// 查询银行借贷和移送记录表
		TransferRecordBankLoanVO transferRecordBankLoanVO = transferRecordLiquiService.getTransferRecordBankLoan(projectLiquiAddVO.getTransferRecordId(), null);
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
		ExpenseRecord expenseRecord = new ExpenseRecord();
		expenseRecord.setProjectId(projectLiqui.getProjectId());
		expenseRecord.setCostType(30001);
		expenseRecord.setCostIncurredTime(projectLiqui.getTakeTime());
		expenseRecord.setCostAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount());
		expenseRecord.setStatus(0);
		expenseRecord.setCompanyCode(projectLiqui.getCompanyCode());
		expenseRecordService.save(expenseRecord);

		//添加费用记录关联主体信息
		ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
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
		queryWrapper.lambda().eq(SubjectBankLoanRe::getBankLoanId, transferRecordBankLoanVO.getSourceId());
		List<SubjectBankLoanRe> subjectBankLoanReList = subjectBankLoanReService.list(queryWrapper);
		List<ProjectSubjectRe> projectSubjectRes = new ArrayList<>();
		// 遍历银行借贷关联表copy到项目主体关联表中
		subjectBankLoanReList.stream().forEach(item -> {
			ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
			projectSubjectRe.setSubjectId(item.getSubjectId());
			projectSubjectRe.setProjectId(projectLiqui.getProjectId());
			projectSubjectRe.setType(item.getDebtType());
			projectSubjectRes.add(projectSubjectRe);
		});
		R<Subject> subject = remoteSubjectService.getByInsId(transferRecordBankLoanVO.getInsId(), SecurityConstants.FROM);
		ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
		projectSubjectRe.setSubjectId(subject.getData().getSubjectId());
		projectSubjectRe.setProjectId(projectLiqui.getProjectId());
		projectSubjectRe.setType(0);
		projectSubjectRes.add(projectSubjectRe);


		// 保存项目主体关联表
		projectSubjectReService.saveBatch(projectSubjectRes);

		// 抵押情况0=有
		if (transferRecordBankLoanVO.getMortgageSituation() == 0) {
			// 查询银行借贷抵押财产
			List<AssetsInformationVO> assetsInformationVOS = assetsBankLoanReMapper.getAssetsBankLoanRe(transferRecordBankLoanVO.getSourceId());
			List<AssetsReLiqui> assetsReLiquiList = new ArrayList<>();
			assetsInformationVOS.stream().forEach(item -> {
				AssetsReLiqui assetsReLiqui = new AssetsReLiqui();
				assetsReLiqui.setAssetsId(item.getAssetsId());
				assetsReLiqui.setSubjectId(item.getSubjectId());
				assetsReLiqui.setProjectId(projectLiqui.getProjectId());
				// 案件来源1=抵押财产
				assetsReLiqui.setAssetsSource(1);
				AssetsReCaseeDetail assetsReCaseeDetail = new AssetsReCaseeDetail();
				assetsReCaseeDetail.setMortgagee(0);
				assetsReCaseeDetail.setMortgageTime(item.getMortgageTime());
				assetsReCaseeDetail.setMortgageAmount(item.getMortgageAmount());
				assetsReLiqui.setAssetsReCaseeDetail(assetsReCaseeDetail);
				assetsReLiquiList.add(assetsReLiqui);
				assetsReLiquiService.save(assetsReLiqui);
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
	public ProjectLiquiDetailsVO getProjectDetails(Integer projectId) {
		// 获取项目基本信息及办理机构名称和网点名称
		ProjectLiquiDetailsVO projectLiquiDetailsVO = this.baseMapper.selectByProjectId(projectId);


		// 查询银行借贷和移送记录表
		TransferRecordBankLoanVO transferRecordBankLoanVO = transferRecordLiquiService.getTransferRecordBankLoan(null, projectId);
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
	public Integer modifyStatusById(ProjectModifyStatusDTO projectModifyStatusDTO) {
		ProjectLiqui projectLiqui = new ProjectLiqui();
		projectLiqui.setProjectId(projectModifyStatusDTO.getProjectId());
		projectLiqui.setStatus(projectModifyStatusDTO.getStatus());
		String projectName = null;

		if (projectModifyStatusDTO.getStatus().equals(1000)) {
			projectName = "项目在办";
		} else if (projectModifyStatusDTO.getStatus().equals(2000)) {
			projectName = "项目暂缓";
		} else if (projectModifyStatusDTO.getStatus().equals(3000)) {
			projectName = "项目和解";
		} else if (projectModifyStatusDTO.getStatus().equals(4000)) {
			projectName = "项目退出";
		}

		// 保存项目状态变更记录表
		ProjectStatus projectStatus = new ProjectStatus();
		projectStatus.setType(1);
		projectStatus.setSourceId(projectModifyStatusDTO.getProjectId());
		projectStatus.setStatusName(projectName);

		// 获取操作人用户名称
		R<UserVO> userVOR = userService.getUserById(securityUtilsService.getCacheUser().getId(), SecurityConstants.FROM);
		projectStatus.setUserName(userVOR.getData().getActualName());

		BeanCopyUtil.copyBean(projectModifyStatusDTO, projectStatus);
		projectStatusService.save(projectStatus);

		return this.baseMapper.updateById(projectLiqui);
	}

	@Override
	public ProjectLiqui getByProjectId(Integer projectId) {
		return this.baseMapper.getByProjectId(projectId);
	}

	@Override
	public IPage<ProjectLiquiOrBehaviorPageVO> queryPageProjectLiqui(Page page, Integer subjectId) {
		return this.baseMapper.queryPageProjectLiqui(page, subjectId);
	}

	@Override
	public List<ProjectSubjectVO> queryProjectSubjectList(ProjectSubjectDTO projectSubjectDTO) {
		return this.baseMapper.selectProjectSubject(projectSubjectDTO);
	}

	@Override
	public ProjectLiquiDealtVO queryDealt(Integer projectId) {
		ProjectLiquiDealtVO projectLiquiDealtVO = new ProjectLiquiDealtVO();
		// 查询项目基本详情
		ProjectLiquiDetailsVO projectLiquiDetailsVO = this.baseMapper.getByProjectId(projectId);
		projectLiquiDealtVO.setProjectLiqui(projectLiquiDetailsVO);
		projectLiquiDealtVO.setLitigation(projectLiquiDetailsVO.getLitigation());

		// 查询诉前阶段案件信息
		List<Integer> typeList = new ArrayList<>();
		typeList.add(1010);
		List<CaseeListVO> prePleadingList = caseeLiquiService.queryByIdList(projectId, typeList);
		projectLiquiDealtVO.setPrePleadingList(prePleadingList);

		// 查询诉讼阶段案件信息
		typeList = new ArrayList<>();
		typeList.add(2010);
		typeList.add(2020);
		typeList.add(2021);
		typeList.add(2030);
		List<CaseeListVO> litigationList = caseeLiquiService.queryByIdList(projectId, typeList);
		projectLiquiDealtVO.setLitigationList(litigationList);

		// 查询执行阶段案件信息
		typeList = new ArrayList<>();
		typeList.add(3010);
		typeList.add(3031);
		List<CaseeListVO> executeList = caseeLiquiService.queryByIdList(projectId, typeList);
		projectLiquiDealtVO.setExecuteList(executeList);

		Boolean addCaseeBtn = false;
		List<Integer> statusList = new ArrayList<>();
		statusList.add(0);
		statusList.add(1);
		CaseeLiqui caseeLiqui = caseeLiquiService.queryByStatusList(projectId, statusList);
		if (Objects.isNull(caseeLiqui)) {
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
	public List<SubjectAssetsBehaviorListVO> queryAssetsBehavior(Integer projectId, Integer caseePersonnelType) {
		return this.baseMapper.selectAssetsBehavior(projectId, caseePersonnelType);
	}

	@Override
	public IPage<ProjectLiquiPageVO> queryNotProcessedPage(Page page, ProjectNoProcessedDTO projectNoProcessedDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectNotProcessedPage(page, projectNoProcessedDTO, insOutlesDTO);
	}


	@Override
	public CountProjectStatisticsVO countProject() {
		CountProjectStatisticsVO projectStatisticsVO = new CountProjectStatisticsVO();

		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		//**********待接收统计********************************
		projectStatisticsVO.setPendingCount(getTransferRecordPage());


		ProjectNoProcessedDTO projectNoProcessedDTO = new ProjectNoProcessedDTO();
		IPage<ProjectLiquiPageVO> pageVOIPage = queryNotProcessedPage(page, projectNoProcessedDTO);
		projectStatisticsVO.setNotProcessedCount(pageVOIPage.getTotal());

		return projectStatisticsVO;
	}

	/**
	 * 查询待接收分页集合数据
	 *
	 * @return
	 */
	private Long getTransferRecordPage() {
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		TransferRecordDTO transferRecordDTO = new TransferRecordDTO();
		transferRecordDTO.setTransferType(0);
		transferRecordDTO.setStatus(0);

		IPage<TransferRecordBankLoanVO> transferRecordPage = transferRecordLiquiService.getTransferRecordPage(page, transferRecordDTO);
		return transferRecordPage.getTotal();
	}

	public Long queryCaseNodePage(String nodeKey, Integer caseeType) {
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO = new CaseeLiquiFlowChartPageDTO();
		caseeLiquiFlowChartPageDTO.setStatus(1);

		caseeLiquiFlowChartPageDTO.setNodeKey(nodeKey);
		caseeLiquiFlowChartPageDTO.setCaseeType(caseeType);
		IPage<CaseeLiquiFlowChartPageVO> caseeLiquiFlowChartPageVOIPage = caseeLiquiService.queryFlowChartPage(page, caseeLiquiFlowChartPageDTO);
		return caseeLiquiFlowChartPageVOIPage.getTotal();
	}

	public Long queryCaseeCount(Integer caseeType) {
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		CaseeLiquiPageDTO caseeLiquiPageDTO = new CaseeLiquiPageDTO();
		caseeLiquiPageDTO.setCaseeType(caseeType);
		caseeLiquiPageDTO.setStatus(1);
		IPage<CaseeLiquiPageVO> caseeLiquiPageVOIPage = caseeLiquiService.queryPage(page, caseeLiquiPageDTO);
		return caseeLiquiPageVOIPage.getTotal();
	}

	@Override
	public CountPreLitigationStageVO countPreLitigationStage() {
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
		IPage<CaseeLiquiPageVO> assetNotAddedList = caseeLiquiService.queryAssetNotAddedPage(page, caseeLiquiPageDTO);
		preLitigationStageVO.setAssetNotAddedCount(assetNotAddedList.getTotal());

		//**********查控保全未完成统计********************************
		preLitigationStageVO.setSeizedUndoneCount(queryAssetsNotSeizeAndFreeze(1010, 20200));

		//**********立案未送达统计********************************
		preLitigationStageVO.setStartCaseeDeliveredCount(queryCaseNodePage("liQui_SQ_SQLASDQK_SQLASDQK", 1010));

		//**********结案未送达统计********************************
		preLitigationStageVO.setCloseCaseeDeliveredCount(queryCaseNodePage("liQui_SQ_SQBQJGSDQK_SQBQJGSDQK", 1010));

		//**********保全完成未结案统计********************************
		CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO = new CaseeLiquiFlowChartPageDTO();
		caseeLiquiFlowChartPageDTO.setCaseeType(1010);
		IPage<CaseeLiquiFlowChartPageVO> notClosedCount = caseeLiquiService.queryPropertyPreservationCompleted(page, caseeLiquiFlowChartPageDTO);
		preLitigationStageVO.setNotClosedCount(notClosedCount.getTotal());


		return preLitigationStageVO;
	}

	@Override
	public CountLitigationVO countlitigation() {
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
		countLitigationVO.setLitigationFirstInstanceStandCaseUndelivered(queryCaseNodePage("liQui_SSYS_SSYSLASDQK_SSYSLASDQK", 2020));

		//**********送达未庭审********************************
		countLitigationVO.setLitigationFirstInstanceServiceNotCourtTrial(queryCaseNodePage("liQui_SSYS_SSYSTSXX_SSYSTSXX", 2020));

		//**********庭审未判决********************************
		countLitigationVO.setLitigationFirstInstanceCourtTrialNotJudgment(queryCaseNodePage("liQui_SSYS_SSYSCPJG_SSYSCPJG", 2020));

		//**********裁判文书未送达********************************
		countLitigationVO.setLitigationFirstInstanceJudgmentPaperworkNotService(queryCaseNodePage("liQui_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK", 2020));

		//**********上诉确认********************************
		countLitigationVO.setLitigationFirstInstanceAppealConfirmation(queryCaseNodePage("liQui_SSYS_SSYSYGSSQK_SSYSYGSSQK", 2020));

		//**********上诉到期未确认********************************
		CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageVO = new CaseeLiquiFlowChartPageDTO();
		caseeLiquiFlowChartPageVO.setStatus(1);
		caseeLiquiFlowChartPageVO.setNodeKey("liQui_SSYS_SSYSSSDQQR_SSYSSSDQQR");
		IPage<CaseeLiquiFlowChartPageVO> caseeLiquiFlowChartPageVOIPage = caseeLiquiService.queryLitigationFirstInstanceAppealExpired(page, caseeLiquiFlowChartPageVO);

		countLitigationVO.setLitigationFirstInstanceAppealExpired(caseeLiquiFlowChartPageVOIPage.getTotal());

		//***********一审节点统计end*************************************************

		//***********保全节点统计Start*************************************************

		//**********未添加财产统计********************************
		CaseeLiquiPageDTO caseeLiquiPageDTO = new CaseeLiquiPageDTO();
		caseeLiquiPageDTO.setCaseeType(2010);
		caseeLiquiPageDTO.setStatus(1);
		IPage<CaseeLiquiPageVO> litigationHoldServiceNotAddProperty = caseeLiquiService.queryAssetNotAddedPage(page, caseeLiquiPageDTO);
		countLitigationVO.setLitigationHoldServiceNotAddProperty(litigationHoldServiceNotAddProperty.getTotal());

		//**********立案未送达统计********************************
		countLitigationVO.setLitigationHoldStandCaseUndelivered(queryCaseNodePage("liQui_SSBQ_SSBQLASDQK_SSBQLASDQK", 2010));

		//**********结案未送达统计********************************
		countLitigationVO.setLitigationHoldKnotCaseUndelivered(queryCaseNodePage("liQui_SSBQ_SSBQBQJGSDQK_SSBQBQJGSDQK", 2010));

		//**********诉讼保全未完成统计********************************
		countLitigationVO.setLitigationHoldCheckControlPreservationUndone(queryAssetsNotSeizeAndFreeze(2010, 20200));

		//**********保全完成未结案统计********************************
		CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO = new CaseeLiquiFlowChartPageDTO();
		caseeLiquiFlowChartPageDTO.setCaseeType(2010);
		IPage<CaseeLiquiFlowChartPageVO> litigationHoldPreservationCompleteNotKnotCase = caseeLiquiService.queryPropertyPreservationCompleted(page, caseeLiquiFlowChartPageDTO);
		countLitigationVO.setLitigationHoldPreservationCompleteNotKnotCase(litigationHoldPreservationCompleteNotKnotCase.getTotal());

		//***********保全节点统计end*************************************************


		//***********二审节点统计Start*************************************************

		//**********立案未送达统计********************************
		countLitigationVO.setLitigationSecondInstanceStandCaseUndelivered(queryCaseNodePage("liQui_SSES_SSESLASDQK_SSESLASDQK", 2021));

		//**********送达未庭审********************************
		countLitigationVO.setLitigationSecondInstanceServiceNotCourtTrial(queryCaseNodePage("liQui_SSES_SSESTSXX_SSESTSXX", 2021));

		//**********庭审未判决********************************
		countLitigationVO.setLitigationSecondInstanceCourtTrialNotJudgment(queryCaseNodePage("liQui_SSES_SSESCPJG_SSESCPJG", 2021));

		//**********裁判文书未送达********************************
		countLitigationVO.setLitigationSecondInstanceJudgmentPaperworkNotService(queryCaseNodePage("liQui_SSES_SSESCPWSZZSDQK_SSESCPWSZZSDQK", 2021));

		//***********二审节点统计end*************************************************


		//***********其它案件节点统计Start*************************************************

		//**********立案未送达统计********************************
		countLitigationVO.setLitigationOthersStandCaseUndelivered(queryCaseNodePage("liQui_SSQT_SSQTLASDQK_SSQTLASDQK", 2030));

		//**********送达未庭审********************************
		countLitigationVO.setLitigationOthersServiceNotCourtTrial(queryCaseNodePage("liQui_SSQT_SSQTTSXX_SSQTTSXX", 2030));

		//**********庭审未判决********************************
		countLitigationVO.setLitigationOthersCourtTrialNotJudgment(queryCaseNodePage("liQui_SSQT_SSQTCPJG_SSQTCPJG", 2030));

		//**********裁判文书未送达********************************
		countLitigationVO.setLitigationOthersJudgmentNotService(queryCaseNodePage("liQui_SSQT_SSQTCPWSZZSDQK_SSQTCPWSZZSDQK", 2030));

		//**********送达未生效********************************
//		countLitigationVO.setLitigationOthersServiceNotActive(queryCaseNodePage("liQui_SSQT_SSQTCPWSZZSDQK_SSQTCPWSZZSDQK", 2030));

		//***********其它案件节点统计end*************************************************

		return countLitigationVO;
	}

	@Override
	public CountFulfillVO countFulfill() {
		CountFulfillVO countFulfillVO = new CountFulfillVO();
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		//**********履行未到期统计********************************
		ReconciliatioMediationDTO reconciliatioMediationDTO = new ReconciliatioMediationDTO();
		reconciliatioMediationDTO.setStatus(0);
		IPage<ReconciliatioMediationVO> fulfillFulfillNotExpired = reconciliatioMediationService.getReconciliatioMediationPage(page, reconciliatioMediationDTO);
		countFulfillVO.setFulfillFulfillNotExpired(fulfillFulfillNotExpired.getTotal());

		//**********达成履行协议未处理********************************
		countFulfillVO.setFulfillReachFulfillProtocolNotProcessed(fulfillFulfillNotExpired.getTotal());

		//**********首次执行待立案********************************
		ProjectNoProcessedDTO projectNoProcessedDTO = new ProjectNoProcessedDTO();
		IPage<ProjectLiquiPageVO> fulfillFirstExecutionPending = this.queryFulfillFirstExecutionPending(page,projectNoProcessedDTO);
		countFulfillVO.setFulfillFirstExecutionPending(fulfillFirstExecutionPending.getTotal());


		return countFulfillVO;
	}

	@Override
	public CountImplementVO countImplement() {
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
		countImplementVO.setChiefExecutiveStandCaseUndelivered(queryCaseNodePage("liQui_ZXSZ_ZXSZLASDQK_ZXSZLASDQK", 3010));

		//**********首执送达未查控********************************
		countImplementVO.setChiefExecutiveHeadExecuteServiceNotCheckControl(queryAssetsNotSeizeAndFreeze(3010, null));

		//***********首执案件节点统计end*************************************************


		//***********执恢案件节点统计Start*************************************************

		//**********恢复执行待立案********************************
		CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO = new CaseeLiquiFlowChartPageDTO();
		IPage<CaseeLiquiFlowChartPageVO> caseeLiquiFlowChartPageVOIPage = caseeLiquiService.queryAddReinstatementCase(page, caseeLiquiFlowChartPageDTO);
		countImplementVO.setReinstateResumeExecutionTreatFileACase(caseeLiquiFlowChartPageVOIPage.getTotal());

		//**********执恢立案未送达********************************
		countImplementVO.setReinstateStandCaseUndelivered(queryCaseNodePage("liQui_ZXZH_ZXZHLASDQK_ZXZHLASDQK", 3031));

		//**********执恢送达未查控********************************
		countImplementVO.setReinstateExecuteRestoreServiceUnchecked(queryAssetsNotSeizeAndFreeze(3031, null));

		//**********到款未拨付********************************
		IPage<CaseeLiquiFlowChartPageVO> courtPayment = caseeLiquiService.queryCourtPayment(page, caseeLiquiFlowChartPageDTO);
		countImplementVO.setPaymentNotPaid(courtPayment.getTotal());

		//**********结清未实际执结********************************
		IPage<CaseeLiquiFlowChartPageVO> paymentCompleted = caseeLiquiService.queryPaymentCompleted(page, caseeLiquiFlowChartPageDTO);
		countImplementVO.setSettleNotActualExecution(paymentCompleted.getTotal());

		//***********执恢案件节点统计end*************************************************


		return countImplementVO;
	}

	public Long queryBehaviorNodePage(String nodeKey, Integer type) {
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		BehaviorLiquiDebtorPageDTO caseeLiquiFlowChartPageDTO = new BehaviorLiquiDebtorPageDTO();

		caseeLiquiFlowChartPageDTO.setNodeKey(nodeKey);
		caseeLiquiFlowChartPageDTO.setType(type);
		IPage<CaseeLiquiDebtorPageVO> caseeLiquiDebtorPageVOIPage = behaviorLiquiService.queryDebtorPage(page, caseeLiquiFlowChartPageDTO);
		return caseeLiquiDebtorPageVOIPage.getTotal();
	}

	public Long queryAssetsNotSeizeAndFreeze(Integer caseeType, Integer type) {
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO = new AssetsReLiquiFlowChartPageDTO();
		assetsReLiquiFlowChartPageDTO.setCaseeType(caseeType);
		assetsReLiquiFlowChartPageDTO.setType(type);
		IPage<AssetsReLiquiFlowChartPageVO> assetsReLiquiFlowChartPageVOIPage = assetsReLiquiService.queryAssetsNotSeizeAndFreeze(page, assetsReLiquiFlowChartPageDTO);
		return assetsReLiquiFlowChartPageVOIPage.getTotal();
	}


	@Override
	public CountDebtorVO countDebtor() {
		CountDebtorVO countDebtorVO = new CountDebtorVO();
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		//**********行为未限制********************************
		CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO = new CaseeLiquiFlowChartPageDTO();
		caseeLiquiFlowChartPageDTO.setType(200);
		IPage<CaseeLiquiFlowChartPageVO> notAddBehavior = caseeLiquiService.queryNotAddBehavior(page, caseeLiquiFlowChartPageDTO);
		countDebtorVO.setBehaviorNotLimit(notAddBehavior.getTotal());

		//**********限制未送达********************************
		countDebtorVO.setLimitNotService(queryBehaviorNodePage("limit_XWXZ_XWXZSDQK_XWXZSDQK", 100));

		//**********制裁申请未实施********************************
		countDebtorVO.setSanctionApplyNotImplemented(queryBehaviorNodePage("beIllegal_XWWF_SSXWWF_SSXWWF", 200));

		//**********已结清限制未撤销********************************
		BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO = new BehaviorLiquiDebtorPageDTO();
		IPage<CaseeLiquiDebtorPageVO> alreadySettleLimitNotRevoked = behaviorLiquiService.behaviorPaymentCompleted(page, behaviorLiquiDebtorPageDTO);
		countDebtorVO.setAlreadySettleLimitNotRevoked(alreadySettleLimitNotRevoked.getTotal());

		//**********未添加财产********************************
		caseeLiquiFlowChartPageDTO = new CaseeLiquiFlowChartPageDTO();
		IPage<CaseeLiquiFlowChartPageVO> notAddProperty = caseeLiquiService.caseeSubjectNotAddAssets(page, caseeLiquiFlowChartPageDTO);
		countDebtorVO.setNotAddProperty(notAddProperty.getTotal());

		return countDebtorVO;
	}

	@Override
	public CountPropertySearchVO countPropertySearch() {
		CountPropertySearchVO countPropertySearchVO = new CountPropertySearchVO();
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		//**********应查控财产未查控********************************
		countPropertySearchVO.setShouldCheckControlUnchecked(queryAssetsNotSeizeAndFreeze(null, null));

		//**********有抵押轮封未商移********************************
		AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO = new AssetsReLiquiFlowChartPageDTO();
		IPage<AssetsReLiquiFlowChartPageVO> haveMortgageWheelSealNotTransferred = assetsReLiquiService.queryBusinessTransfer(page, assetsReLiquiFlowChartPageDTO);
		countPropertySearchVO.setHaveMortgageWheelSealNotTransferred(haveMortgageWheelSealNotTransferred.getTotal());

		//**********首冻资金未划扣********************************
		IPage<AssetsReLiquiFlowChartPageVO> firstFrozenFundsNotDebited = assetsReLiquiService.queryFundDeduction(page, assetsReLiquiFlowChartPageDTO);
		countPropertySearchVO.setFirstFrozenFundsNotDebited(firstFrozenFundsNotDebited.getTotal());

		//**********待拍财产未移交********************************
		IPage<AssetsReLiquiFlowChartPageVO> propertyToBeAuctionedNotHandedOver = assetsReLiquiService.queryPropertyToBeAuctioned(page, assetsReLiquiFlowChartPageDTO);
		countPropertySearchVO.setPropertyToBeAuctionedNotHandedOver(propertyToBeAuctionedNotHandedOver.getTotal());

		//**********处理未到款********************************
		IPage<AssetsReLiquiFlowChartPageVO> auctionTransactionNotProcessed = assetsReLiquiService.queryPropertyAuctionSuccess(page,assetsReLiquiFlowChartPageDTO);
		countPropertySearchVO.setHandleNotPayment(auctionTransactionNotProcessed.getTotal());

		return countPropertySearchVO;
	}

	public Long queryPropertyFlowChartPage(String nodeKey, List<Integer> assetsTypeList) {
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO = new AssetsReLiquiFlowChartPageDTO();
		assetsReLiquiFlowChartPageDTO.setNodeKey(nodeKey);
		assetsReLiquiFlowChartPageDTO.setAssetsTypeList(assetsTypeList);
		IPage<AssetsReLiquiFlowChartPageVO> assetsReLiquiFlowChartPageVOIPage = assetsReLiquiService.queryPropertyFlowChartPage(page, assetsReLiquiFlowChartPageDTO);
		return assetsReLiquiFlowChartPageVOIPage.getTotal();
	}

	@Override
	public CountAuctionPropertyVO countAuctionProperty() {
		CountAuctionPropertyVO countAuctionPropertyVO = new CountAuctionPropertyVO();
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);


		//**********动产未现勘********************************
		countAuctionPropertyVO.setChattelNotAvailable(queryPropertyFlowChartPage("entityZX_STZX_CCZXXK_CCZXXK", null));

		//**********不动产未现勘********************************
		countAuctionPropertyVO.setRealEstateNotSurveyed(queryPropertyFlowChartPage("entityZX_STZX_CCZXXK_CCZXXK", null));

		//**********不动产现勘未入户********************************
		List<Integer> assetsTypeList = new ArrayList<>();
		assetsTypeList.add(20201);
		assetsTypeList.add(20204);
		countAuctionPropertyVO.setRealEstateSurveyNotRegistered(queryPropertyFlowChartPage("entityZX_STZX_CCZXBDCXKRH_CCZXBDCXKRH", assetsTypeList));

		//**********拍卖价格依据未出具********************************
		countAuctionPropertyVO.setAuctionPriceBasisNotIssued(queryPropertyFlowChartPage("entityZX_STZX_CCZXJGYJ_CCZXJGYJ", null));

		//**********有依据未上拍********************************
		countAuctionPropertyVO.setThereIsEvidenceNotListed(queryPropertyFlowChartPage("entityZX_STZX_CCZXPMGG_CCZXPMGG", null));

		//**********公告期未拍卖********************************
		AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO = new AssetsReLiquiFlowChartPageDTO();
		IPage<AssetsReLiquiFlowChartPageVO> announcementPeriodNotAuctioned = assetsReLiquiService.queryPropertyAuctionAnnouncementPeriod(page, assetsReLiquiFlowChartPageDTO);
		countAuctionPropertyVO.setAnnouncementPeriodNotAuctioned(announcementPeriodNotAuctioned.getTotal());

		//**********拍卖到期无结果********************************
		IPage<AssetsReLiquiFlowChartPageVO> auctionExpiresWithoutResults = assetsReLiquiService.queryPropertyAuctionDue(page,assetsReLiquiFlowChartPageDTO);
		countAuctionPropertyVO.setAuctionExpiresWithoutResults(auctionExpiresWithoutResults.getTotal());

		//**********拍卖成交未处理********************************
		IPage<AssetsReLiquiFlowChartPageVO> auctionTransactionNotProcessed = assetsReLiquiService.queryPropertyAuctionSuccess(page,assetsReLiquiFlowChartPageDTO);
		countAuctionPropertyVO.setAuctionTransactionNotProcessed(auctionTransactionNotProcessed.getTotal());

		//**********拍卖不成交未处理********************************
		IPage<AssetsReLiquiFlowChartPageVO> auctionTransactionFailedNotProcessed = assetsReLiquiService.queryPropertyAuctionFailed(page,assetsReLiquiFlowChartPageDTO);
		countAuctionPropertyVO.setAuctionTransactionFailedNotProcessed(auctionTransactionFailedNotProcessed.getTotal());

		//**********拍卖异常未撤销********************************
		IPage<AssetsReLiquiFlowChartPageVO> auctionExceptionNotCancelled = assetsReLiquiService.queryPropertyAuctionAbnormal(page,assetsReLiquiFlowChartPageDTO);
		countAuctionPropertyVO.setAuctionExceptionNotCancelled(auctionExceptionNotCancelled.getTotal());

		//**********到款/抵偿未裁定********************************
		IPage<AssetsReLiquiFlowChartPageVO> arrivalCompensationNotAdjudicated = assetsReLiquiService.queryDispositionRuling(page,assetsReLiquiFlowChartPageDTO);
		countAuctionPropertyVO.setArrivalCompensationNotAdjudicated(arrivalCompensationNotAdjudicated.getTotal());

		//**********裁定未送达********************************
		countAuctionPropertyVO.setRulingNotService(queryPropertyFlowChartPage("entityZX_STZX_CCZXDCCDSDQK_CCZXDCCDSDQK", null));

		//**********送达未腾退********************************
		countAuctionPropertyVO.setDeliveredButNotVacated(queryPropertyFlowChartPage("entityZX_STZX_CCZXTTCG_CCZXTTCG", null));


		return countAuctionPropertyVO;
	}

	/**
	 * 首页项目、事项统计接口
	 *
	 * @return
	 */
	@Override
	public CountProjectMattersVO countProjectMatters() {
		CountProjectMattersVO countProjectMattersVO = new CountProjectMattersVO();

		//设置分页条数
		Page page = new Page();
		page.setSize(1);
		page.setCurrent(1);
		page.setSize(10);

		//查询项目待接收
		countProjectMattersVO.setPendingCount(getTransferRecordPage());

		//查询项目在办
		ProjectLiquiPageDTO projectInProgress = new ProjectLiquiPageDTO();

		projectInProgress.setStatus(1000);

		IPage<ProjectLiquiPageVO> projectInProgressIPage = this.queryPageLiqui(page, projectInProgress);

		countProjectMattersVO.setProjectInProgressCount(projectInProgressIPage.getTotal());

		//查询项目暂缓

		ProjectLiquiPageDTO projectSuspend = new ProjectLiquiPageDTO();

		projectSuspend.setStatus(2000);

		IPage<ProjectLiquiPageVO> projectSuspendIPage = this.queryPageLiqui(page, projectSuspend);

		countProjectMattersVO.setProjectSuspendCount(projectSuspendIPage.getTotal());

		IPage<ExpirationReminderVO> expirationReminderVOIPage = this.queryStatisticsReminder(page, new ExpirationReminderDTO());

		//却查询提醒事项
		countProjectMattersVO.setRemindMatterCount(expirationReminderVOIPage.getTotal());

		return countProjectMattersVO;
	}

	/**
	 * 年比较去年相关数量如：项目、回款额、案件数和财产数
	 *
	 * @return
	 */
	@Override
	public CountCompareQuantityVO countCompareQuantity() {
		CountCompareQuantityVO countCompareQuantityVO = new CountCompareQuantityVO();

		//查询较去年项目数
		countCompareQuantityVO.setCompareTheNumberOfItemsCount(this.baseMapper.queryCompareTheNumberOfItemsCount(jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_")));

		//查询较去年回款额
		countCompareQuantityVO.setCompareMoneyBackAmountCount(this.paymentRecordService.queryCompareMoneyBackAmountCount());

		//查询较去年案件数
		countCompareQuantityVO.setCompareTheNumberOfCasesCount(this.caseeLiquiService.queryCompareTheNumberOfCasesCount());

		//查询较去年财产数
		countCompareQuantityVO.setComparePropertyNumbersCount(this.assetsReLiquiService.queryComparePropertyNumbersCount());

		return countCompareQuantityVO;
	}

	/**
	 * 本月回款额月排名、回款总额、财产类型数量、财产总数量和提醒事项集合
	 *
	 * @return
	 */
	@Override
	public CountMoneyBackMonthlyRankVO countMoneyBackMonthlyRank() {
		CountMoneyBackMonthlyRankVO countMoneyBackMonthlyRankVO = new CountMoneyBackMonthlyRankVO();

		//本月总回款额
		countMoneyBackMonthlyRankVO.setTotalRepayments(this.paymentRecordService.getTotalRepayments());

		//财产分类统计集合
		countMoneyBackMonthlyRankVO.setPropertyCategoryTotalList(this.assetsReLiquiService.queryPropertyCategoryTotalList());

		//财产总数量
		countMoneyBackMonthlyRankVO.setTotalProperty(this.assetsReLiquiService.queryTotalProperty());

		return countMoneyBackMonthlyRankVO;
	}

	/**
	 * 查询项目案件折线图
	 * @param countPolylineLineChartDTO
	 * @return
	 */
	@Override
	public CountPolylineLineChartVO countPolylineLineChart(CountPolylineLineChartDTO countPolylineLineChartDTO) {

		CountPolylineLineChartVO countPolylineLineChartVO = new CountPolylineLineChartVO();

		//判断折线年月份类型 0年 1月
		if (countPolylineLineChartDTO.getPolylineActive().equals(Integer.valueOf("0"))) {
			//创建查询日期集合
			List<String> yearDifference;
			//起始年份与结束年份不为空
			if((Objects.nonNull(countPolylineLineChartDTO.getPolylineYearStar()) && !countPolylineLineChartDTO.getPolylineYearStar().equals("")) && (Objects.nonNull(countPolylineLineChartDTO.getPolylineYearEnd()) && !countPolylineLineChartDTO.getPolylineYearEnd().equals(""))) {
//				起始年份与结束年份相等 取起始年份存入时间集合
				if (countPolylineLineChartDTO.getPolylineYearStar().equals(countPolylineLineChartDTO.getPolylineYearEnd())) {
					yearDifference = new ArrayList<>();
					yearDifference.add(countPolylineLineChartDTO.getPolylineYearStar());
					//起始年份与结束年份不相等 将间隔年份全部查出
				} else {
					yearDifference = GetDifference.getYearDifference(countPolylineLineChartDTO.getPolylineYearStar(), countPolylineLineChartDTO.getPolylineYearEnd());
				}
			//起始年份与结束年份为空 查询当前年份-9年的全部年份
			} else {
				Calendar c1 = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String polylineYearEnd = sdf.format(date);
				c1.setTime(date);
				c1.add(Calendar.YEAR, -9);
				Date time = c1.getTime();
				String polylineYearStar = sdf.format(time);
				yearDifference = GetDifference.getYearDifference(polylineYearStar, polylineYearEnd);
			}
			//查询相应数据并处理查询数据
			setCountPolylineLineChartVO(countPolylineLineChartDTO.getPolylineActive(), countPolylineLineChartVO, yearDifference);

		} else {
			//创建查询日期集合
			List<String> monthDifference;
			//起始月份与结束月份不为空
			if((Objects.nonNull(countPolylineLineChartDTO.getPolylineMonthStar()) && !countPolylineLineChartDTO.getPolylineMonthStar().equals("")) && (Objects.nonNull(countPolylineLineChartDTO.getPolylineMonthEnd()) && !countPolylineLineChartDTO.getPolylineMonthEnd().equals(""))) {
				//起始月份与结束月份相等 取起始月份存入时间集合
				if (countPolylineLineChartDTO.getPolylineMonthStar().equals(countPolylineLineChartDTO.getPolylineMonthEnd())) {
					monthDifference = new ArrayList<>();
					monthDifference.add(countPolylineLineChartDTO.getPolylineMonthStar());
				//起始月份与结束月份不相等 将间隔月份全部查出
				} else {
					monthDifference = GetDifference.getMonthDifference(countPolylineLineChartDTO.getPolylineMonthStar(), countPolylineLineChartDTO.getPolylineMonthEnd());
				}
			//起始月份与结束月份为空 查询当前月份-11的全部月份
			} else {
				Calendar c1 = Calendar.getInstance();
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				String polylineMonthEnd = sdf.format(now);
				c1.setTime(now);
				c1.add(Calendar.MONTH, -11);
				Date time = c1.getTime();
				String polylineMonthStar = sdf.format(time);
				monthDifference = GetDifference.getMonthDifference(polylineMonthStar, polylineMonthEnd);
			}

			//查询相应数据并处理查询数据
			setCountPolylineLineChartVO(countPolylineLineChartDTO.getPolylineActive(), countPolylineLineChartVO, monthDifference);
		}

		return countPolylineLineChartVO;
	}

	/**
	 * 根据日期集合查询项目与案件数量
	 * @param polylineActive
	 * @param countPolylineLineChartVO
	 * @param monthDifference
	 */
	private void setCountPolylineLineChartVO(Integer polylineActive, CountPolylineLineChartVO countPolylineLineChartVO, List<String> monthDifference) {
		//根据日期集合查询项目
		Map<String, Long> projectMap = this.baseMapper.getProjectMap(polylineActive, monthDifference, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));

		//根据日期集合查询案件
		Map<String, Long> caseeMap = this.caseeLiquiService.getCaseeMap(polylineActive, monthDifference);

		List<String> timelineList = new ArrayList<>();

		List<Long> projectList = new ArrayList<>();

		List<Long> caseeList = new ArrayList<>();

		//循环将数据存入VO相对应的List中
		for (String key : projectMap.keySet()) {
			timelineList.add(key);
			projectList.add(projectMap.get(key));
		}

		for (String key : caseeMap.keySet()) {
			caseeList.add(caseeMap.get(key));
		}

		countPolylineLineChartVO.setTimelineList(timelineList);

		countPolylineLineChartVO.setProjectList(projectList);

		countPolylineLineChartVO.setCaseeList(caseeList);
	}

	@Override
	public 	IPage<ProjectLiquiPageVO> queryFulfillFirstExecutionPending(Page page, ProjectNoProcessedDTO projectNoProcessedDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectFulfillFirstExecutionPending(page, projectNoProcessedDTO, insOutlesDTO);
	}

	/**
	 * 回款额折线柱状图
	 * @param countLineChartColumnarChartDTO
	 * @return
	 */
	@Override
	public CountLineChartColumnarChartVO countLineChartColumnarChart(CountLineChartColumnarChartDTO countLineChartColumnarChartDTO) {
		CountLineChartColumnarChartVO countLineChartColumnarChartVO = new CountLineChartColumnarChartVO();

		//判断折线年月份类型 0年 1月
		if (countLineChartColumnarChartDTO.getPolylineColumnActive().equals(Integer.valueOf("0"))) {
			//创建查询日期集合
			List<String> yearDifference;
			//起始年份与结束年份不为空
			if((Objects.nonNull(countLineChartColumnarChartDTO.getPolylineColumnYearStar()) && !countLineChartColumnarChartDTO.getPolylineColumnYearStar().equals("")) && (Objects.nonNull(countLineChartColumnarChartDTO.getPolylineColumnYearEnd()) && !countLineChartColumnarChartDTO.getPolylineColumnYearEnd().equals(""))) {
				//起始年份与结束年份相等 取起始年份存入时间集合
				if (countLineChartColumnarChartDTO.getPolylineColumnYearStar().equals(countLineChartColumnarChartDTO.getPolylineColumnYearEnd())) {
					yearDifference = new ArrayList<>();
					yearDifference.add(countLineChartColumnarChartDTO.getPolylineColumnYearStar());
					//起始月份与结束月份不相等 将间隔月份全部查出
				} else {
					yearDifference = GetDifference.getYearDifference(countLineChartColumnarChartDTO.getPolylineColumnYearStar(), countLineChartColumnarChartDTO.getPolylineColumnYearEnd());
				}
				//起始年份与结束年份为空 查询当前年份-9年的全部年份
			} else {
				Calendar c1 = Calendar.getInstance();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
				Date date = new Date();
				String polylineColumnYearEnd = sdf.format(date);
				c1.setTime(date);
				c1.add(Calendar.YEAR, -9);
				Date time = c1.getTime();
				String polylineColumnYearStar = sdf.format(time);
				yearDifference = GetDifference.getYearDifference(polylineColumnYearStar, polylineColumnYearEnd);
			}

			//查询相应数据并处理查询数据
			setCountLineChartColumnarChartVO(countLineChartColumnarChartDTO.getPolylineColumnActive(), countLineChartColumnarChartVO, yearDifference);

		} else {
			//创建查询日期集合
			List<String> monthDifference;
			//起始月份与结束月份不为空
			if((Objects.nonNull(countLineChartColumnarChartDTO.getPolylineColumnMonthStar()) && !countLineChartColumnarChartDTO.getPolylineColumnMonthStar().equals("")) && (Objects.nonNull(countLineChartColumnarChartDTO.getPolylineColumnMonthEnd()) && !countLineChartColumnarChartDTO.getPolylineColumnMonthEnd().equals(""))) {
				//起始月份与结束月份相等 取起始月份存入时间集合
				if (countLineChartColumnarChartDTO.getPolylineColumnMonthStar().equals(countLineChartColumnarChartDTO.getPolylineColumnMonthEnd())) {
					monthDifference = new ArrayList<>();
					monthDifference.add(countLineChartColumnarChartDTO.getPolylineColumnMonthStar());
					//起始月份与结束月份不相等 将间隔月份全部查出
				} else {
					monthDifference = GetDifference.getMonthDifference(countLineChartColumnarChartDTO.getPolylineColumnMonthStar(), countLineChartColumnarChartDTO.getPolylineColumnMonthEnd());
				}
				//起始月份与结束月份为空 查询当前月份-11的全部月份
			} else {
				Calendar c1 = Calendar.getInstance();
				Date now = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
				String polylineColumnMonthEnd = sdf.format(now);
				c1.setTime(now);
				c1.add(Calendar.MONTH, -11);
				Date time = c1.getTime();
				String polylineColumnMonthStar = sdf.format(time);
				monthDifference = GetDifference.getMonthDifference(polylineColumnMonthStar, polylineColumnMonthEnd);
			}

			//查询相应数据并处理查询数据
			setCountLineChartColumnarChartVO(countLineChartColumnarChartDTO.getPolylineColumnActive(), countLineChartColumnarChartVO, monthDifference);
		}

		return countLineChartColumnarChartVO;
	}

	/**
	 * 根据日期集合查询回款额
	 * @param polylineColumnActive
	 * @param countLineChartColumnarChartVO
	 * @param difference
	 */
	private void setCountLineChartColumnarChartVO(Integer polylineColumnActive, CountLineChartColumnarChartVO countLineChartColumnarChartVO, List<String> difference) {
		List<String> lineColumnarTimelineList = new ArrayList<>();

		List<BigDecimal> moneyBackAmountLit = new ArrayList<>();

		//查询回款
		Map<String, BigDecimal> paymentRecordMap = this.paymentRecordService.getPaymentRecordMap(polylineColumnActive, difference);

		//循环将数据存入VO相对应的List中
		for (String key : paymentRecordMap.keySet()) {
			lineColumnarTimelineList.add(key);
			moneyBackAmountLit.add(paymentRecordMap.get(key));
		}

		countLineChartColumnarChartVO.setLineColumnarTimelineList(lineColumnarTimelineList);

		countLineChartColumnarChartVO.setMoneyBackAmountLit(moneyBackAmountLit);
	}

	@Override
	public 	IPage<ExpirationReminderVO> queryStatisticsReminder(Page page, ExpirationReminderDTO expirationReminderDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectStatisticsReminder(page, expirationReminderDTO, insOutlesDTO);
	}


}
