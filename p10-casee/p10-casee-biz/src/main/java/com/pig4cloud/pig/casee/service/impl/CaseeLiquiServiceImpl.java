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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES.LiQui_SSES_SSESCPJG_SSESCPJG;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSQT.LiQui_SSQT_SSQTCPJG_SSQTCPJG;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS.LiQui_SSYS_SSYSCPJG_SSYSCPJG;
import com.pig4cloud.pig.casee.mapper.CaseeLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.*;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


/**
 * ?????????
 *
 * @author yy
 * @date 2022-01-10 14:51:59
 */
@Service
public class CaseeLiquiServiceImpl extends ServiceImpl<CaseeLiquiMapper, Casee> implements CaseeLiquiService {
	@Autowired
	private ProjectCaseeReLiquiService projectCaseeReLiquiService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private TargetService targetService;
	@Autowired
	private AssetsReService assetsReService;
	@Autowired
	private CaseeSubjectReService caseeSubjectReService;
	@Autowired
	private RemoteSubjectService subjectService;
	@Autowired
	private CaseeLawyerReService caseeLawyerReService;
	@Autowired
	private ProjectStatusService projectStatusService;
	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;
	@Autowired
	private TaskNodeService taskNodeService;
	@Autowired
	private ExpenseRecordService expenseRecordService;
	@Autowired
	private ExpenseRecordSubjectReService expenseRecordSubjectReService;
	@Autowired
	private AssetsReLiquiService assetsReLiquiService;
	@Autowired
	private PaymentRecordService paymentRecordService;

	@Override
	@Transactional
	public Integer modifyCaseeStatusById(CaseeLiquiModifyStatusDTO caseeLiquiModifyStatusDTO) {
		Integer modify = 0;
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		caseeLiqui.setCaseeId(caseeLiquiModifyStatusDTO.getCaseeId());
		caseeLiqui.setStatus(caseeLiquiModifyStatusDTO.getStatus());
		if(caseeLiquiModifyStatusDTO.getStatus()!=0 && caseeLiquiModifyStatusDTO.getStatus()!=1){
			caseeLiqui.setCloseTime(caseeLiquiModifyStatusDTO.getChangeTime());
		}
		modify = this.baseMapper.updateById(caseeLiqui);

		Integer status = caseeLiquiModifyStatusDTO.getStatus();
		String statusName = null;
		if (status == 1){
			statusName = "??????";
		}else if (status == 2) {
			statusName = "??????";
		} else if (status == 3) {
			statusName = "??????";
		} else if (status == 4) {
			statusName = "??????";
		}

		// ?????????????????????????????????
		ProjectStatusSaveDTO projectStatusSaveDTO = new ProjectStatusSaveDTO();
		projectStatusSaveDTO.setType(2);
		projectStatusSaveDTO.setStatusVal(status);
		projectStatusSaveDTO.setStatusName(statusName);
		BeanCopyUtil.copyBean(caseeLiquiModifyStatusDTO,projectStatusSaveDTO);
		projectStatusService.saveStatusRecord(projectStatusSaveDTO);
		return modify;
	}

	@Override
	@Transactional
	public Integer addCaseeLiqui(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception {
		// ??????????????????
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		BeanCopyUtil.copyBean(caseeLiquiAddDTO, caseeLiqui);
		this.baseMapper.insert(caseeLiqui);
		// ???????????????????????????
		ProjectCaseeRe projectCaseeRe = new ProjectCaseeRe();
		BeanCopyUtil.copyBean(caseeLiquiAddDTO, projectCaseeRe);
		projectCaseeRe.setCaseeId(caseeLiqui.getCaseeId());
		projectCaseeReLiquiService.save(projectCaseeRe);
		// ????????????????????????
		List<CaseeSubjectRe> caseeSubjectReList = new ArrayList<>();
		// ???????????????/??????/?????????/????????????????????????
		caseeLiquiAddDTO.getApplicantList().stream().forEach(item -> {
			CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
			caseeSubjectRe.setCaseeId(caseeLiqui.getCaseeId());
			caseeSubjectRe.setSubjectId(item.getSubjectId());
			caseeSubjectRe.setType(item.getType());
			caseeSubjectRe.setCaseePersonnelType(0);
			caseeSubjectReList.add(caseeSubjectRe);
		});
		// ????????????/????????????/?????????????????????
		caseeLiquiAddDTO.getExecutedList().stream().forEach(item -> {
			CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
			caseeSubjectRe.setCaseeId(caseeLiqui.getCaseeId());
			caseeSubjectRe.setSubjectId(item.getSubjectId());
			caseeSubjectRe.setType(item.getType());
			caseeSubjectRe.setCaseePersonnelType(1);
			caseeSubjectReList.add(caseeSubjectRe);
		});
		caseeSubjectReService.saveBatch(caseeSubjectReList);

		// ?????????????????????????????????
		CaseeLiquiModifyStatusDTO caseeLiquiModifyStatusDTO = new CaseeLiquiModifyStatusDTO();
		caseeLiquiModifyStatusDTO.setStatus(1);
		caseeLiquiModifyStatusDTO.setProjectId(caseeLiquiAddDTO.getProjectId());
		caseeLiquiModifyStatusDTO.setCaseeId(caseeLiqui.getCaseeId());
		caseeLiquiModifyStatusDTO.setChangeTime(caseeLiquiAddDTO.getStartTime());
		this.modifyCaseeStatusById(caseeLiquiModifyStatusDTO);

		//????????????????????????????????????
		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(caseeLiquiAddDTO.getProjectId());
		TargetAddDTO targetAddDTO = new TargetAddDTO();
		targetAddDTO.setCaseeId(caseeLiqui.getCaseeId());
		targetAddDTO.setProcedureNature(caseeLiqui.getCaseeType());
		targetAddDTO.setOutlesId(projectLiqui.getOutlesId());
		targetAddDTO.setProjectId(caseeLiquiAddDTO.getProjectId());
		targetAddDTO.setGoalType(10001);
		targetService.saveTargetAddDTO(targetAddDTO);

		// ????????????????????????
		if (Objects.nonNull(caseeLiquiAddDTO.getLawyerName())) {
			CaseeLawyerRe caseeLawyerRe = new CaseeLawyerRe();
			caseeLawyerRe.setActualName(caseeLiquiAddDTO.getLawyerName());
			caseeLawyerRe.setCaseeId(caseeLiqui.getCaseeId());
			caseeLawyerReService.save(caseeLawyerRe);
		}

		//???????????????????????????????????????
		if (caseeLiquiAddDTO.getJudicialExpenses() != null) {
			ExpenseRecord expenseRecord = new ExpenseRecord();

			switch (caseeLiquiAddDTO.getCaseeType()) {
				case 1010:
					expenseRecord.setCostType(10002);
					break;
				case 2010:
					expenseRecord.setCostType(10002);
					break;
				case 2020:
					expenseRecord.setCostType(10003);
					break;
				case 2021:
					expenseRecord.setCostType(10004);
					break;
				case 2030:
					expenseRecord.setCostType(10009);
					break;
				case 3010:
					expenseRecord.setCostType(10005);
					break;
			}
			expenseRecord.setProjectId(caseeLiquiAddDTO.getProjectId());
			expenseRecord.setCaseeId(caseeLiqui.getCaseeId());
			expenseRecord.setCompanyCode(projectLiqui.getCompanyCode());
			expenseRecord.setCaseeNumber(caseeLiquiAddDTO.getCaseeNumber());
			expenseRecord.setCostIncurredTime(caseeLiquiAddDTO.getStartTime());
			expenseRecord.setCostAmount(caseeLiquiAddDTO.getJudicialExpenses());
			expenseRecord.setSubjectName(caseeLiquiAddDTO.getExecutedName());
			expenseRecord.setStatus(0);
			expenseRecordService.save(expenseRecord);

			//?????????????????????
			projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(caseeLiquiAddDTO.getJudicialExpenses()));
			projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
			projectLiquiService.updateById(projectLiqui);


			if (caseeLiquiAddDTO.getCaseeType().equals(1010) || caseeLiquiAddDTO.getCaseeType().equals(2010) || caseeLiquiAddDTO.getCaseeType().equals(2020) || caseeLiquiAddDTO.getCaseeType().equals(2030) || caseeLiquiAddDTO.getCaseeType().equals(3010)) {
				//?????????????????????
				List<CaseeSubjectReDTO> executedList = caseeLiquiAddDTO.getExecutedList();
				for (CaseeSubjectReDTO caseeSubjectReDTO : executedList) {
					ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
					expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
					expenseRecordSubjectRe.setSubjectId(caseeSubjectReDTO.getSubjectId());
					expenseRecordSubjectReService.save(expenseRecordSubjectRe);
				}
			} else if (caseeLiquiAddDTO.getCaseeType().equals(2021)) {//????????????????????????????????????
				//????????????????????????
				CaseeLiqui caseeParentId = this.getCaseeParentId(caseeLiquiAddDTO.getProjectId(), 2020);
				//?????????????????????????????????
				List<SubjectOptionVO> subjectOptionVOList = caseeSubjectReService.getByCaseeId(caseeParentId.getCaseeId(), null, 1);

				ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
				for (SubjectOptionVO subjectOptionVO : subjectOptionVOList) {
					expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
					expenseRecordSubjectRe.setSubjectId(subjectOptionVO.getSubjectId());
					expenseRecordSubjectReService.save(expenseRecordSubjectRe);
				}
			}
		}
		return caseeLiqui.getCaseeId();
	}

	@Override
	@Transactional
	public Integer addPreservationCasee(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception {
		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		saveAssetsReService(caseeLiquiAddDTO.getProjectId(), caseeId, caseeLiquiAddDTO.getCaseeType());
		return caseeId;
	}

	@Override
	@Transactional
	public Integer addLawsuitsCasee(CaseeLawsuitsDTO caseeLawsuitsDTO) throws Exception {
		CaseeLiquiAddDTO caseeLiquiAddDTO = new CaseeLiquiAddDTO();
		BeanCopyUtil.copyBean(caseeLawsuitsDTO, caseeLiquiAddDTO);
		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		return caseeId;
	}

	@Override
	@Transactional
	public Integer saveSecondInstanceCasee(CaseeSecondInstanceDTO caseeSecondInstanceDTO) throws Exception {
		CaseeLiquiAddDTO caseeLiquiAddDTO = new CaseeLiquiAddDTO();
		BeanCopyUtil.copyBean(caseeSecondInstanceDTO, caseeLiquiAddDTO);
		if (caseeSecondInstanceDTO.getSubjectList().size() > 0) {
			List<CaseeSubjectReDTO> applicantList = new ArrayList<>();
			caseeSecondInstanceDTO.getSubjectList().stream().forEach(item -> {
				Integer subjectId = 0;
				if (Objects.nonNull(item.getSubjectId())) {
					subjectId = item.getSubjectId();
				} else {
					R subject = subjectService.saveOrUpdateById(item, SecurityConstants.FROM);
					subjectId = Integer.valueOf(subject.getData().toString());
				}
				CaseeSubjectReDTO caseeSubjectReDTO = new CaseeSubjectReDTO();
				caseeSubjectReDTO.setSubjectId(subjectId);
				// ??????????????????4=?????????
				caseeSubjectReDTO.setType(4);
				applicantList.add(caseeSubjectReDTO);
			});
			caseeLiquiAddDTO.setApplicantList(applicantList);
		}

		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		return caseeId;
	}

	@Override
	@Transactional
	public Integer addExecutionCasee(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception {
		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		// ??????????????????
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		caseeLiqui.setCaseeId(caseeId);
		// 30101=??????
		caseeLiqui.setCategory(30101);
		this.baseMapper.updateById(caseeLiqui);

		// ??????????????????????????????????????????
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().eq(AssetsRe::getProjectId, caseeLiquiAddDTO.getProjectId());
		List<AssetsRe> assetsRes = assetsReService.list(queryWrapper);
		Project project = projectLiquiService.getById(caseeLiquiAddDTO.getProjectId());

		// ??????????????????????????????
		if (assetsRes.size() > 0) {
			List<AssetsRe> assetsReList = new ArrayList<>();
			assetsRes.stream().forEach(item -> {
				if (item.getCaseeId() == null) {//????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????
					//??????????????????
					TargetAddDTO targetAddDTO = new TargetAddDTO();
					targetAddDTO.setCaseeId(caseeId);
					targetAddDTO.setProcedureNature(4040);
					targetAddDTO.setOutlesId(project.getOutlesId());
					targetAddDTO.setProjectId(caseeLiquiAddDTO.getProjectId());
					targetAddDTO.setGoalId(item.getAssetsId());
					targetAddDTO.setGoalType(20001);
					try {
						targetService.saveTargetAddDTO(targetAddDTO);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {//???????????????????????????????????????????????????????????????????????????????????????????????????????????????????????????id??????????????????id
					Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getProjectId,caseeLiquiAddDTO.getProjectId()).eq(Target::getCaseeId, item.getCreateCaseeId()).eq(Target::getGoalId, item.getAssetsId()).eq(Target::getGoalType, 20001));
					target.setCaseeId(caseeId);
					targetService.updateById(target);
					//??????????????????id?????????????????????????????????????????????????????????????????????????????????????????????????????????
					List<TaskNode> list = taskNodeService.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, project.getProjectId()).eq(TaskNode::getCaseeId, item.getCreateCaseeId()).eq(TaskNode::getTargetId, target.getTargetId()).eq(TaskNode::getNodeAttributes, 400));
					for (TaskNode taskNode : list) {
						taskNode.setCaseeId(caseeId);
					}
					taskNodeService.updateBatchById(list);
				}
				AssetsRe assetsRe = new AssetsRe();
				assetsRe.setAssetsReId(item.getAssetsReId());
				assetsRe.setCaseeId(caseeId);
				// ????????????????????????id??????????????????id??????
				if (Objects.isNull(item.getCreateCaseeId())) {
					assetsRe.setCreateCaseeId(caseeId);
				}
				assetsReList.add(assetsRe);
			});
			assetsReService.updateBatchById(assetsReList);
		}
		return caseeId;
	}

	@Override
	@Transactional
	public Integer addReinstatementCasee(CaseeReinstatementDTO caseeReinstatementDTO) throws Exception {
		CaseeLiquiAddDTO caseeLiquiAddDTO = new CaseeLiquiAddDTO();
		BeanCopyUtil.copyBean(caseeReinstatementDTO, caseeLiquiAddDTO);
		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		// ??????????????????
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		caseeLiqui.setCaseeId(caseeId);
		// 30311=??????
		caseeLiqui.setCategory(30311);
		this.baseMapper.updateById(caseeLiqui);

		// ??????????????????????????????????????????
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().eq(AssetsRe::getProjectId, caseeLiquiAddDTO.getProjectId());
		queryWrapper.lambda().ne(AssetsRe::getStatus, 500);
		List<AssetsRe> assetsRes = assetsReService.list(queryWrapper);

		for (AssetsRe assetsRe : assetsRes) {
			Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getProjectId,caseeReinstatementDTO.getProjectId()).eq(Target::getCaseeId, assetsRe.getCaseeId()).eq(Target::getGoalId, assetsRe.getAssetsId()).eq(Target::getGoalType, 20001));
			target.setCaseeId(caseeId);
			targetService.updateById(target);
			//??????????????????id?????????????????????????????????????????????????????????????????????????????????????????????????????????
			List<TaskNode> list = taskNodeService.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, caseeReinstatementDTO.getProjectId()).eq(TaskNode::getCaseeId, assetsRe.getCaseeId()).eq(TaskNode::getTargetId, target.getTargetId()).eq(TaskNode::getNodeAttributes, 400));
			for (TaskNode taskNode : list) {
				taskNode.setCaseeId(caseeId);
			}
			taskNodeService.updateBatchById(list);
		}
		assetsReLiquiService.updateAssetsRe(caseeReinstatementDTO.getProjectId(),caseeId);
		return caseeId;
	}

	@Transactional
	public void saveAssetsReService(Integer projectId, Integer caseeId, Integer caseeType) {
		// ??????????????????????????????????????????
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().eq(AssetsRe::getProjectId, projectId);
		queryWrapper.lambda().eq(AssetsRe::getAssetsSource, 1);
		queryWrapper.lambda().isNull(AssetsRe::getCaseeId);
		List<AssetsRe> assetsRes = assetsReService.list(queryWrapper);

		Project project = projectLiquiService.getById(projectId);
		// ????????????????????????
		if (assetsRes.size() > 0) {
			List<AssetsRe> assetsReList = new ArrayList<>();
			assetsRes.stream().forEach(item -> {
				AssetsRe assetsRe = new AssetsRe();
				assetsRe.setAssetsReId(item.getAssetsReId());
				assetsRe.setCaseeId(caseeId);
				assetsRe.setCreateCaseeId(caseeId);
				assetsReList.add(assetsRe);
				//????????????????????????????????????
				TargetAddDTO targetAddDTO = new TargetAddDTO();
				targetAddDTO.setCaseeId(caseeId);
				targetAddDTO.setProcedureNature(4040);
				targetAddDTO.setOutlesId(project.getOutlesId());
				targetAddDTO.setProjectId(projectId);
				targetAddDTO.setGoalId(item.getAssetsId());
				targetAddDTO.setGoalType(20001);
				try {
					targetService.saveTargetAddDTO(targetAddDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}

			});
			assetsReService.updateBatchById(assetsReList);
		}
	}

	@Override
	public List<CaseeListVO> queryByIdList(Integer caseeId, List<Integer> caseeType) {
		return this.baseMapper.selectByIdList(caseeId, caseeType);
	}

	@Override
	public CaseeLiqui getCaseeParentId(Integer projectId, Integer caseeType) {
		return this.baseMapper.getCaseeParentId(projectId, caseeType);
	}

	@Override
	public CaseeLiqui queryByStatusList(Integer projectId, Integer status) {
		return this.baseMapper.selectByStatusList(projectId, status);
	}

	@Override
	public IPage<CaseeLiquiPageVO> queryPage(Page page, CaseeLiquiPageDTO caseeLiquiPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPage(page, caseeLiquiPageDTO, insOutlesDTO);
	}

	/**
	 * ????????????id??????????????????
	 *
	 * @param caseeId
	 * @return
	 */
	@Override
	public CaseeLiquiDetailsVO queryByCaseeId(Integer caseeId) {
		CaseeLiquiDetailsVO caseeLiquiDetailsVO = this.baseMapper.queryByCaseeId(caseeId);
		CaseeOrSubjectDTO caseeOrSubjectDTO = new CaseeOrSubjectDTO();
		caseeOrSubjectDTO.setCaseeId(caseeId);
		caseeOrSubjectDTO.setCaseePersonnelType(1);
		caseeLiquiDetailsVO.setCaseeOrSubjectVOList(this.baseMapper.selectCaseeOrSubject(caseeOrSubjectDTO));
		caseeLiquiDetailsVO.setCaseeOrAssetsVOList(this.assetsReService.selectCaseeOrAssets(caseeId));
		return caseeLiquiDetailsVO;
	}

	@Override
	public List<Subject> queryCaseeSubjectList(CaseeSubjectDTO caseeSubjectDTO) {
		return this.baseMapper.selectCaseeSubject(caseeSubjectDTO);
	}

	@Override
	public List<SubjectAssetsBehaviorListVO> queryAssetsBehavior(Integer caseeId, Integer caseePersonnelType) {
		return this.baseMapper.selectAssetsBehavior(caseeId, caseePersonnelType);
	}

	@Override
	public IPage<CaseeLiquiPageVO> queryAssetNotAddedPage(Page page, CaseeLiquiPageDTO caseeLiquiPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectAssetNotAddedPage(page, caseeLiquiPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<CaseeLiquiFlowChartPageVO> queryFlowChartPage(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectFlowChartPage(page, caseeLiquiFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public CaseeLiquiDetailsVO queryByCaseeIdDetails(Integer caseeId) {
		return this.baseMapper.queryByCaseeId(caseeId);
	}

	@Override
	public void litigationCaseeClose() {
		// ???????????????????????????????????????????????????
		List<CaseeLiqui> caseeList = this.baseMapper.selectJudgmentTakesEffect();
		List<Casee> casees = new ArrayList<>();
		for (CaseeLiqui caseeLiqui : caseeList) {
			// ???????????????
			LocalDate effectiveDate =  caseeLiqui.getCaseeLiquiDetail().getEffectiveDate();
			Casee casee = new Casee();
			casee.setCaseeId(caseeLiqui.getCaseeId());
			casee.setStatus(3);
			casee.setCloseTime(effectiveDate);
			casees.add(casee);

			ProjectCaseeRe projectCaseeRe = projectCaseeReLiquiService.getOne(new LambdaQueryWrapper<ProjectCaseeRe>().eq(ProjectCaseeRe::getCaseeId, casee.getCaseeId()));

			//??????????????????????????????????????????????????????????????????????????????????????????????????????
			if (caseeLiqui.getCaseeLiquiDetail().getFirstTrialRefereeResult()!=null&&caseeLiqui.getCaseeLiquiDetail().getSecondTrialRefereeResult()==null){
				//??????????????????????????????????????????
				TaskNode taskNode = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, projectCaseeRe.getProjectId()).eq(TaskNode::getCaseeId, projectCaseeRe.getCaseeId()).eq(TaskNode::getNodeKey, "liQui_SSYS_SSYSCPJG_SSYSCPJG"));
				LiQui_SSYS_SSYSCPJG_SSYSCPJG liQui_ssys_ssyscpjg_ssyscpjg = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSYS_SSYSCPJG_SSYSCPJG.class);

				RefereeResultTakeEffectDTO refereeResultTakeEffectDTO=new RefereeResultTakeEffectDTO();
				refereeResultTakeEffectDTO.setProjectId(projectCaseeRe.getProjectId());
				refereeResultTakeEffectDTO.setCaseeId(projectCaseeRe.getCaseeId());
				BeanUtils.copyProperties(liQui_ssys_ssyscpjg_ssyscpjg,refereeResultTakeEffectDTO);

				//???????????????????????????????????????????????????????????????
				expenseRecordService.refereeResultTakeEffectUpdateExpenseRecord(refereeResultTakeEffectDTO);

				//???????????????????????????????????????????????????????????????????????????
			}else if (caseeLiqui.getCaseeLiquiDetail().getSecondTrialRefereeResult()!=null){
				//??????????????????????????????????????????
				TaskNode taskNode = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, projectCaseeRe.getProjectId()).eq(TaskNode::getCaseeId, projectCaseeRe.getCaseeId()).eq(TaskNode::getNodeKey, "liQui_SSES_SSESCPJG_SSESCPJG"));
				LiQui_SSES_SSESCPJG_SSESCPJG liQui_sses_ssescpjg_ssescpjg = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSES_SSESCPJG_SSESCPJG.class);

				RefereeResultTakeEffectDTO refereeResultTakeEffectDTO=new RefereeResultTakeEffectDTO();
				refereeResultTakeEffectDTO.setProjectId(projectCaseeRe.getProjectId());
				refereeResultTakeEffectDTO.setCaseeId(projectCaseeRe.getCaseeId());
				BeanUtils.copyProperties(liQui_sses_ssescpjg_ssescpjg,refereeResultTakeEffectDTO);

				//???????????????????????????????????????????????????????????????
				expenseRecordService.refereeResultTakeEffectUpdateExpenseRecord(refereeResultTakeEffectDTO);

				//???????????????????????????????????????????????????????????????????????????
			}else if (caseeLiqui.getCaseeLiquiDetail().getOtherRefereeResult()!=null){
				//??????????????????????????????????????????
				TaskNode taskNode = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, projectCaseeRe.getProjectId()).eq(TaskNode::getCaseeId, projectCaseeRe.getCaseeId()).eq(TaskNode::getNodeKey, "liQui_SSQT_SSQTCPJG_SSQTCPJG"));
				LiQui_SSQT_SSQTCPJG_SSQTCPJG liQui_ssqt_ssqtcpjg_ssqtcpjg = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSQT_SSQTCPJG_SSQTCPJG.class);

				RefereeResultTakeEffectDTO refereeResultTakeEffectDTO=new RefereeResultTakeEffectDTO();
				refereeResultTakeEffectDTO.setProjectId(projectCaseeRe.getProjectId());
				refereeResultTakeEffectDTO.setCaseeId(projectCaseeRe.getCaseeId());
				BeanUtils.copyProperties(liQui_ssqt_ssqtcpjg_ssqtcpjg,refereeResultTakeEffectDTO);

				//???????????????????????????????????????????????????????????????
				expenseRecordService.refereeResultTakeEffectUpdateExpenseRecord(refereeResultTakeEffectDTO);
			}
		}
		this.updateBatchById(casees);
	}

	@Override
	public IPage<CaseeLiquiFlowChartPageVO> queryLitigationFirstInstanceAppealExpired(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectLitigationFirstInstanceAppealExpired(page, caseeLiquiFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<CaseeLiquiFlowChartPageVO> queryAddReinstatementCase(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectAddReinstatementCase(page, caseeLiquiFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<CaseeLiquiFlowChartPageVO> queryCourtPayment(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectCourtPayment(page, caseeLiquiFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<CaseeLiquiFlowChartPageVO> queryPaymentCompleted(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPaymentCompleted(page, caseeLiquiFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<CaseeLiquiFlowChartPageVO> queryNotAddBehavior(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectNotAddBehavior(page, caseeLiquiFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<CaseeLiquiFlowChartPageVO> caseeSubjectNotAddAssets(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.caseeSubjectNotAddAssets(page, caseeLiquiFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<CaseeLiquiFlowChartPageVO> queryPropertyPreservationCompleted(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPropertyPreservationCompleted(page, caseeLiquiFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public Long queryCompareTheNumberOfCasesCount() {
		return this.baseMapper.queryCompareTheNumberOfCasesCount(jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * ????????????????????????????????????
	 *
	 * @param differenceList
	 * @return
	 */
	@Override
	public Map<String, BigDecimal> getCaseeMap(Integer polylineActive, List<String> differenceList) {
		return this.baseMapper.getCaseeMap(polylineActive, differenceList, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * ?????????????????????????????????????????????
	 *
	 * @param casee
	 * @return
	 */
	@Override
	public CaseeLiqui getCaseeLiqui(@RequestParam("casee") Casee casee) {
		return this.baseMapper.getCaseeLiqui(casee);
	}

	@Override
	public	CaseeLiquiPageVO getCaseeDetails(Integer caseeId){
		return this.baseMapper.selectCaseeDetails(caseeId);
	}

	@Override
	public IPage<QueryCaseeLiquiPageVO> queryCaseeLiquiPage(Page page, QueryCaseeLiquiPageDTO queryCaseeLiquiPageDTO) {
		return this.baseMapper.queryCaseeLiquiPage(page, queryCaseeLiquiPageDTO, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	@Override
	@Transactional
	public Integer modifyByCaseeId(CaseeLiquiModifyDTO caseeLiquiModifyDTO){
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		BeanCopyUtil.copyBean(caseeLiquiModifyDTO,caseeLiqui);

		if(Objects.nonNull(caseeLiquiModifyDTO.getLawyerName())){
			UpdateWrapper<CaseeLawyerRe> updateWrapper = new UpdateWrapper<>();
			updateWrapper.lambda().eq(CaseeLawyerRe::getCaseeId,caseeLiquiModifyDTO.getCaseeId());
			updateWrapper.lambda().set(CaseeLawyerRe::getActualName,caseeLiquiModifyDTO.getLawyerName());
			caseeLawyerReService.update(updateWrapper);
		}

		// ????????????????????????????????????
		UpdateWrapper<ExpenseRecord> expenseRecordUpdateWrapper = new UpdateWrapper<>();
		expenseRecordUpdateWrapper.lambda().eq(ExpenseRecord::getCaseeId,caseeLiquiModifyDTO.getCaseeId());
		expenseRecordUpdateWrapper.lambda().set(ExpenseRecord::getCaseeNumber,caseeLiquiModifyDTO.getCaseeNumber());
		expenseRecordService.update(expenseRecordUpdateWrapper);

		// ??????????????????????????????
		UpdateWrapper<PaymentRecord> paymentRecordUpdateWrapper = new UpdateWrapper<>();
		paymentRecordUpdateWrapper.lambda().eq(PaymentRecord::getCaseeId,caseeLiquiModifyDTO.getCaseeId());
		paymentRecordUpdateWrapper.lambda().set(PaymentRecord::getCaseeNumber,caseeLiquiModifyDTO.getCaseeNumber());
		paymentRecordService.update(paymentRecordUpdateWrapper);

		return this.baseMapper.updateById(caseeLiqui);
	}
}
