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
import com.pig4cloud.pig.casee.mapper.CaseeLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.*;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;


/**
 * 案件表
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
			statusName = "立案";
		}else if (status == 2) {
			statusName = "撤案";
		} else if (status == 3) {
			statusName = "结案";
		} else if (status == 4) {
			statusName = "终结";
		}

		// 保存项目状态变更记录表
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
		// 保存清收案件
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		BeanCopyUtil.copyBean(caseeLiquiAddDTO, caseeLiqui);
		this.baseMapper.insert(caseeLiqui);
		// 保存项目案件关联表
		ProjectCaseeRe projectCaseeRe = new ProjectCaseeRe();
		BeanCopyUtil.copyBean(caseeLiquiAddDTO, projectCaseeRe);
		projectCaseeRe.setCaseeId(caseeLiqui.getCaseeId());
		projectCaseeReLiquiService.save(projectCaseeRe);
		// 保存案件人员关联
		List<CaseeSubjectRe> caseeSubjectReList = new ArrayList<>();
		// 遍历申请人/原告/上诉人/申请执行人等集合
		caseeLiquiAddDTO.getApplicantList().stream().forEach(item -> {
			CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
			caseeSubjectRe.setCaseeId(caseeLiqui.getCaseeId());
			caseeSubjectRe.setSubjectId(item.getSubjectId());
			caseeSubjectRe.setType(item.getType());
			caseeSubjectRe.setCaseePersonnelType(0);
			caseeSubjectReList.add(caseeSubjectRe);
		});
		// 遍历被告/被执行人/被上述人等集合
		caseeLiquiAddDTO.getExecutedList().stream().forEach(item -> {
			CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
			caseeSubjectRe.setCaseeId(caseeLiqui.getCaseeId());
			caseeSubjectRe.setSubjectId(item.getSubjectId());
			caseeSubjectRe.setType(item.getType());
			caseeSubjectRe.setCaseePersonnelType(1);
			caseeSubjectReList.add(caseeSubjectRe);
		});
		caseeSubjectReService.saveBatch(caseeSubjectReList);

		// 保存项目状态变更记录表
		CaseeLiquiModifyStatusDTO caseeLiquiModifyStatusDTO = new CaseeLiquiModifyStatusDTO();
		caseeLiquiModifyStatusDTO.setStatus(1);
		caseeLiquiModifyStatusDTO.setProjectId(caseeLiquiAddDTO.getProjectId());
		caseeLiquiModifyStatusDTO.setCaseeId(caseeLiqui.getCaseeId());
		caseeLiquiModifyStatusDTO.setChangeTime(caseeLiquiAddDTO.getStartTime());
		this.modifyCaseeStatusById(caseeLiquiModifyStatusDTO);

		//添加任务数据以及程序信息
		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(caseeLiquiAddDTO.getProjectId());
		TargetAddDTO targetAddDTO = new TargetAddDTO();
		targetAddDTO.setCaseeId(caseeLiqui.getCaseeId());
		targetAddDTO.setProcedureNature(caseeLiqui.getCaseeType());
		targetAddDTO.setOutlesId(projectLiqui.getOutlesId());
		targetAddDTO.setProjectId(caseeLiquiAddDTO.getProjectId());
		targetAddDTO.setGoalType(10001);
		targetService.saveTargetAddDTO(targetAddDTO);

		//添加各各案件司法费产生记录
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

			//修改项目总金额
			projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(caseeLiquiAddDTO.getJudicialExpenses()));
			projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
			projectLiquiService.updateById(projectLiqui);


			if (caseeLiquiAddDTO.getCaseeType().equals(1010) || caseeLiquiAddDTO.getCaseeType().equals(2010) || caseeLiquiAddDTO.getCaseeType().equals(2020) || caseeLiquiAddDTO.getCaseeType().equals(2030) || caseeLiquiAddDTO.getCaseeType().equals(3010)) {
				//案件债务人信息
				List<CaseeSubjectReDTO> executedList = caseeLiquiAddDTO.getExecutedList();
				for (CaseeSubjectReDTO caseeSubjectReDTO : executedList) {
					ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
					expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
					expenseRecordSubjectRe.setSubjectId(caseeSubjectReDTO.getSubjectId());
					expenseRecordSubjectReService.save(expenseRecordSubjectRe);
				}
			} else if (caseeLiquiAddDTO.getCaseeType().equals(2021)) {//二审案件主体信息特殊处理
				//查询一审案件信息
				CaseeLiqui caseeParentId = this.getCaseeParentId(caseeLiquiAddDTO.getProjectId(), 2020);
				//查询一审案件债务人信息
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
		// 保存案件代理律师
		if (Objects.nonNull(caseeLawsuitsDTO.getLawyerName())) {
			CaseeLawyerRe caseeLawyerRe = new CaseeLawyerRe();
			caseeLawyerRe.setActualName(caseeLawsuitsDTO.getLawyerName());
			caseeLawyerRe.setCaseeId(caseeId);
			caseeLawyerReService.save(caseeLawyerRe);
		}
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
				// 案件人员关系4=案外人
				caseeSubjectReDTO.setType(4);
				applicantList.add(caseeSubjectReDTO);
			});
			caseeLiquiAddDTO.setApplicantList(applicantList);
		}

		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		// 保存案件代理律师
		if (Objects.nonNull(caseeSecondInstanceDTO.getLawyerName())) {
			CaseeLawyerRe caseeLawyerRe = new CaseeLawyerRe();
			caseeLawyerRe.setActualName(caseeSecondInstanceDTO.getLawyerName());
			caseeLawyerRe.setCaseeId(caseeId);
			caseeLawyerReService.save(caseeLawyerRe);
		}
		return caseeId;
	}

	@Override
	@Transactional
	public Integer addExecutionCasee(CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception {
		Integer caseeId = addCaseeLiqui(caseeLiquiAddDTO);
		// 更新案件类别
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		caseeLiqui.setCaseeId(caseeId);
		// 30101=首执
		caseeLiqui.setCategory(30101);
		this.baseMapper.updateById(caseeLiqui);

		// 查询所有财产，更新财产关联表
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().eq(AssetsRe::getProjectId, caseeLiquiAddDTO.getProjectId());
		List<AssetsRe> assetsRes = assetsReService.list(queryWrapper);
		Project project = projectLiquiService.getById(caseeLiquiAddDTO.getProjectId());

		// 遍历修改财产案件关联
		if (assetsRes.size() > 0) {
			List<AssetsRe> assetsReList = new ArrayList<>();
			assetsRes.stream().forEach(item -> {
				if (item.getCaseeId() == null) {//如果移交过来的财产没有在诉前或者诉讼阶段处理过，那么添加首执案件时需添加财产程序
					//添加财产程序
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
				} else {//如果移交过来的财产有在诉前或者诉讼阶段处理过，那么添加首执案件时需修改财产程序案件id以及节点案件id
					Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getProjectId,caseeLiquiAddDTO.getProjectId()).eq(Target::getCaseeId, item.getCreateCaseeId()).eq(Target::getGoalId, item.getAssetsId()).eq(Target::getGoalType, 20001));
					target.setCaseeId(caseeId);
					targetService.updateById(target);
					//修改节点案件id、不然页面加载节点会查询到诉讼或者诉前的财产程序导致在执行阶段无法显示
					List<TaskNode> list = taskNodeService.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, project.getProjectId()).eq(TaskNode::getCaseeId, item.getCreateCaseeId()).eq(TaskNode::getTargetId, target.getTargetId()).eq(TaskNode::getNodeAttributes, 400));
					for (TaskNode taskNode : list) {
						taskNode.setCaseeId(caseeId);
					}
					taskNodeService.updateBatchById(list);
				}
				AssetsRe assetsRe = new AssetsRe();
				assetsRe.setAssetsReId(item.getAssetsReId());
				assetsRe.setCaseeId(caseeId);
				// 假如没有创建案件id，将首执案件id保存
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
		// 更新案件类别
		CaseeLiqui caseeLiqui = new CaseeLiqui();
		caseeLiqui.setCaseeId(caseeId);
		// 30311=执恢
		caseeLiqui.setCategory(30311);
		this.baseMapper.updateById(caseeLiqui);

		// 查询所有财产，更新财产关联表
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().eq(AssetsRe::getProjectId, caseeLiquiAddDTO.getProjectId());
		queryWrapper.lambda().ne(AssetsRe::getStatus, 500);
		List<AssetsRe> assetsRes = assetsReService.list(queryWrapper);

		for (AssetsRe assetsRe : assetsRes) {
			Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getProjectId,caseeReinstatementDTO.getProjectId()).eq(Target::getCaseeId, assetsRe.getCaseeId()).eq(Target::getGoalId, assetsRe.getAssetsId()).eq(Target::getGoalType, 20001));
			target.setCaseeId(caseeId);
			targetService.updateById(target);
			//修改节点案件id、不然页面加载节点会查询到诉讼或者诉前的财产程序导致在执行阶段无法显示
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
		// 查询抵押财产，更新财产关联表
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().eq(AssetsRe::getProjectId, projectId);
		queryWrapper.lambda().eq(AssetsRe::getAssetsSource, 1);
		queryWrapper.lambda().isNull(AssetsRe::getCaseeId);
		List<AssetsRe> assetsRes = assetsReService.list(queryWrapper);

		Project project = projectLiquiService.getById(projectId);
		// 遍历保存财产关联
		if (assetsRes.size() > 0) {
			List<AssetsRe> assetsReList = new ArrayList<>();
			assetsRes.stream().forEach(item -> {
				AssetsRe assetsRe = new AssetsRe();
				assetsRe.setAssetsReId(item.getAssetsReId());
				assetsRe.setCaseeId(caseeId);
				assetsRe.setCreateCaseeId(caseeId);
				assetsReList.add(assetsRe);
				//添加任务数据以及程序信息
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
	 * 根据案件id查询案件信息
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
		// 查询一审、二审案件判决结果生效日期
		List<CaseeLiqui> caseeList = this.baseMapper.selectJudgmentTakesEffect();
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
	 * 根据日期集合查询案件数量
	 *
	 * @param differenceList
	 * @return
	 */
	@Override
	public Map<String, BigDecimal> getCaseeMap(Integer polylineActive, List<String> differenceList) {
		return this.baseMapper.getCaseeMap(polylineActive, differenceList, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * 根据特定条件查询案件与案件详情
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
		if (!queryCaseeLiquiPageDTO.getTotalType().equals(-1)) {
			List<Integer> list = new ArrayList<>();
			if (queryCaseeLiquiPageDTO.getTotalType().equals(0)) {
				list.add(1010);
				list.add(2010);
			} else if (queryCaseeLiquiPageDTO.getTotalType().equals(1)) {
				list.add(2020);
				list.add(2021);
				list.add(2030);
			} else {
				list.add(3010);
				list.add(3031);
			}
			queryCaseeLiquiPageDTO.setCaseeTypeList(list);
		}
		return this.baseMapper.queryCaseeLiquiPage(page, queryCaseeLiquiPageDTO);
	}
}
