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
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteRelationshipAuthenticateService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.dto.paifu.*;
import com.pig4cloud.pig.casee.dto.paifu.count.AssetsRePaifuFlowChartPageDTO;
import com.pig4cloud.pig.casee.dto.paifu.excel.ImportPaifu;
import com.pig4cloud.pig.casee.dto.paifu.excel.ImportPaifuDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.paifuentity.AssetsRePaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.AssetsRePaifuDetail;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ProjectPaifuDetail;
import com.pig4cloud.pig.casee.mapper.ProjectPaifuMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.utils.DownloadUtils;
import com.pig4cloud.pig.casee.vo.LiquiTransferRecordDetailsVO;
import com.pig4cloud.pig.casee.vo.SubjectOptionVO;
import com.pig4cloud.pig.casee.vo.paifu.*;
import com.pig4cloud.pig.casee.vo.paifu.count.AssetsRePaifuFlowChartPageVO;
import com.pig4cloud.pig.casee.vo.paifu.count.CountFlowChartVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.*;

/**
 * ???????????????
 */
@Service
public class ProjectPaifuServiceImpl extends ServiceImpl<ProjectPaifuMapper, Project> implements ProjectPaifuService {
	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;
	@Autowired
	private CaseeService caseeService;
	@Autowired
	private ProjectSubjectReService projectSubjectReService;
	@Autowired
	private CaseeSubjectReService caseeSubjectReService;
	@Autowired
	private ProjectStatusService projectStatusService;
	@Autowired
	private ProjectCaseeReService projectCaseeReService;
	@Autowired
	private RemoteSubjectService remoteSubjectService;
	@Autowired
	private LiquiTransferRecordService liquiTransferRecordService;
	@Autowired
	private AssetsLiquiTransferRecordReService assetsLiquiTransferRecordReService;
	@Autowired
	private ProjectOutlesDealReService projectOutlesDealReService;
	@Autowired
	private AssetsReService assetsReService;
	@Autowired
	private AssetsReSubjectService assetsReSubjectService;
	@Autowired
	private TargetService targetService;
	@Autowired
	private ExpenseRecordService expenseRecordService;
	@Autowired
	private PaymentRecordService paymentRecordService;
	@Autowired
	private AssetsService assetsService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private DownloadUtils downloadUtils;
	@Autowired
	private CustomerService customerService;

	@Override
	@Transactional
	public Integer saveProjectCasee(ProjectPaifuSaveDTO projectPaifuSaveDTO) {

		// ???????????????
		ProjectPaifu projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectType(200);
		BeanCopyUtil.copyBean(projectPaifuSaveDTO, projectPaifu);
		Integer save = this.baseMapper.insert(projectPaifu);

		// ???????????????
		Casee casee = new Casee();
		BeanCopyUtil.copyBean(projectPaifuSaveDTO, casee);
		caseeService.save(casee);

		List<ProjectSubjectRe> projectSubjectRes = new ArrayList();
		List<CaseeSubjectRe> caseeSubjectRes = new ArrayList();
		String proposersNames = "";
		String subjectPersons = "";
		List<CustomerSubjectDTO> customerSubjectDTOS = new ArrayList<>();
		// ??????????????????
		for (CaseeSubjectReListDTO item : projectPaifuSaveDTO.getApplicantList()) {
			Subject subject = new Subject();
			BeanCopyUtil.copyBean(item, subject);
			Integer subjectId = remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM).getData();
			ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
			projectSubjectRe.setSubjectId(subjectId);
			projectSubjectRe.setProjectId(projectPaifu.getProjectId());
			projectSubjectRe.setType(item.getCaseePersonnelType());
			projectSubjectRes.add(projectSubjectRe);

			CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
			caseeSubjectRe.setCaseeId(casee.getCaseeId());
			caseeSubjectRe.setSubjectId(subjectId);
			caseeSubjectRe.setType(item.getCaseePersonnelType());
			caseeSubjectRe.setCaseePersonnelType(item.getCaseePersonnelType());
			caseeSubjectRes.add(caseeSubjectRe);
			if (item.getCaseePersonnelType() == 0) {
				// ????????????
				CustomerSubjectDTO customerSubjectDTO = new CustomerSubjectDTO();
				customerSubjectDTO.setSubjectId(subjectId);
				customerSubjectDTO.setCustomerType(40000);
				customerSubjectDTO.setProjectId(projectPaifu.getProjectId());
				customerSubjectDTO.setCaseeId(casee.getCaseeId());
				customerSubjectDTO.setRecommenderId(projectPaifuSaveDTO.getUserId());
				customerSubjectDTO.setInsId(projectPaifuSaveDTO.getInsId());
				customerSubjectDTO.setOutlesId(projectPaifuSaveDTO.getOutlesId());
				customerSubjectDTOS.add(customerSubjectDTO);
				if (proposersNames.equals("")) {
					proposersNames = item.getName();
				} else {
					proposersNames = proposersNames + "," + item.getName();
				}
			} else {
				if (subjectPersons.equals("")) {
					subjectPersons = item.getName();
				} else {
					subjectPersons = subjectPersons + "," + item.getName();
				}
			}
		}
		projectSubjectReService.saveBatch(projectSubjectRes);
		caseeSubjectReService.saveBatch(caseeSubjectRes);
		customerService.saveCustomerBatch(customerSubjectDTOS);

		//??????????????????????????????????????????????????????
		ProjectPaifu updateProject = new ProjectPaifu();
		updateProject.setProjectId(projectPaifu.getProjectId());
		updateProject.setProposersNames(proposersNames);
		updateProject.setSubjectPersons(subjectPersons);
		this.baseMapper.updateById(updateProject);

		//????????????????????????????????????????????????????????????
		Casee updateCasee = new Casee();
		updateCasee.setCaseeId(casee.getCaseeId());
		updateCasee.setApplicantName(proposersNames);
		updateCasee.setExecutedName(subjectPersons);
		caseeService.updateById(updateCasee);

		// ???????????????????????????
		ProjectCaseeRe projectCaseeRe = new ProjectCaseeRe();
		projectCaseeRe.setProjectId(projectPaifu.getProjectId());
		projectCaseeRe.setCaseeId(casee.getCaseeId());
		BeanCopyUtil.copyBean(projectPaifuSaveDTO, projectCaseeRe);
		projectCaseeReService.save(projectCaseeRe);

		// ?????????????????????????????????
		ProjectStatusSaveDTO projectStatusSaveDTO = new ProjectStatusSaveDTO();
		projectStatusSaveDTO.setType(1);
		projectStatusSaveDTO.setStatusVal(1000);
		projectStatusSaveDTO.setStatusName("??????");
		projectStatusSaveDTO.setProjectId(projectPaifu.getProjectId());
		projectStatusSaveDTO.setChangeTime(projectPaifuSaveDTO.getTakeTime());
		projectStatusService.saveStatusRecord(projectStatusSaveDTO);

		return save;
	}

	@Override
	public IPage<ProjectPaifuPageVO> queryProjectCaseePage(Page page, ProjectPaifuPageDTO projectPaifuPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));

		projectPaifuPageDTO.setOrders(JsonUtils.jsonToList(projectPaifuPageDTO.getOrdersJson(), OrderItem.class));

		page.setOrders(projectPaifuPageDTO.getOrders());

		return this.baseMapper.selectPagePaifu(page, projectPaifuPageDTO, insOutlesDTO);
	}

	@Override
	public ProjectPaifuDetailVO queryProjectCaseeDetailList(Integer projectId) {
		ProjectPaifuDetailVO projectPaifuDetailVO = this.baseMapper.selectByProjectId(projectId);
		projectPaifuDetailVO.setApplicantList(this.baseMapper.selectProjectSubjectReList(projectId, 0));
		projectPaifuDetailVO.setExecutedList(this.baseMapper.selectProjectSubjectReList(projectId, 1));
//		projectPaifuDetailVO.setCaseeList(caseeService.queryByPaifuProjectId(projectId));
		return projectPaifuDetailVO;
	}

	@Override
	@Transactional
	public Integer addProjectSubjectRe(ProjectSubjectReSaveDTO projectSubjectReSaveDTO) {
		Project project = this.baseMapper.selectById(projectSubjectReSaveDTO.getProjectId());
		// ???????????????
		Subject subject = new Subject();
		BeanCopyUtil.copyBean(projectSubjectReSaveDTO, subject);
		Integer subjectId = remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM).getData();
		// ???????????????????????????
		ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
		projectSubjectRe.setSubjectId(subjectId);
		projectSubjectRe.setProjectId(projectSubjectReSaveDTO.getProjectId());
		projectSubjectRe.setType(projectSubjectReSaveDTO.getCaseePersonnelType());
		projectSubjectReService.save(projectSubjectRe);
		// ???????????????????????????
		CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
		caseeSubjectRe.setCaseeId(projectSubjectReSaveDTO.getCaseeId());
		caseeSubjectRe.setSubjectId(subjectId);
		caseeSubjectRe.setCaseePersonnelType(projectSubjectReSaveDTO.getCaseePersonnelType());
		caseeSubjectReService.save(caseeSubjectRe);
		String projectSubjectName = null;
		ProjectPaifu updateProject = new ProjectPaifu();
		Casee updateCasee = new Casee();
		if (projectSubjectReSaveDTO.getCaseePersonnelType() == 0) {
			projectSubjectName = project.getProposersNames() + "," + projectSubjectReSaveDTO.getName();
			updateProject.setProposersNames(projectSubjectName);
			updateCasee.setApplicantName(projectSubjectName);
			// ????????????
			CustomerSubjectDTO customerSubjectDTO = new CustomerSubjectDTO();
			customerSubjectDTO.setSubjectId(subjectId);
			customerSubjectDTO.setNatureType(30000);
			customerSubjectDTO.setProjectId(project.getProjectId());
			customerSubjectDTO.setCaseeId(projectSubjectReSaveDTO.getCaseeId());
			customerSubjectDTO.setRecommenderId(project.getUserId());
			customerSubjectDTO.setInsId(project.getInsId());
			customerSubjectDTO.setOutlesId(project.getOutlesId());
			customerService.saveCustomer(customerSubjectDTO);
		} else {
			projectSubjectName = project.getSubjectPersons() + "," + projectSubjectReSaveDTO.getName();
			updateProject.setSubjectPersons(projectSubjectName);
			updateCasee.setExecutedName(projectSubjectName);
		}
		//??????????????????????????????????????????????????????
		updateProject.setProjectId(projectSubjectReSaveDTO.getProjectId());
		this.baseMapper.updateById(updateProject);

		//????????????????????????????????????????????????????????????
		updateCasee.setCaseeId(projectSubjectReSaveDTO.getCaseeId());
		caseeService.updateById(updateCasee);
		return 1;
	}

	@Override
	public ProjectSubjectReListVO queryProjectSubjectRe(Integer projectId, String unifiedIdentity) {
		return this.baseMapper.selectProjectSubjectRe(projectId, unifiedIdentity);
	}

	@Override
	@Transactional
	public Integer modifyByProjectId(ProjectPaifuModifyDTO projectPaifuModifyDTO) {
		ProjectPaifu projectPaifu = new ProjectPaifu();
		BeanCopyUtil.copyBean(projectPaifuModifyDTO, projectPaifu);
		Integer modify = this.baseMapper.updateById(projectPaifu);

		Casee casee = new Casee();
		BeanCopyUtil.copyBean(projectPaifuModifyDTO, casee);
		caseeService.updateById(casee);
		return modify;
	}

	@Override
	@Transactional
	public Integer modifyProjectSubjectRe(ProjectSubjectReSaveDTO projectSubjectReSaveDTO) {
		// ??????????????????????????????
		UpdateWrapper<ProjectSubjectRe> projectSubjectRe = new UpdateWrapper<>();
		projectSubjectRe.lambda().eq(ProjectSubjectRe::getProjectId, projectSubjectReSaveDTO.getProjectId());
		projectSubjectRe.lambda().eq(ProjectSubjectRe::getSubjectId, projectSubjectReSaveDTO.getSubjectId());
		projectSubjectRe.lambda().set(ProjectSubjectRe::getType, projectSubjectReSaveDTO.getCaseePersonnelType());
		projectSubjectReService.update(projectSubjectRe);
		// ??????????????????????????????
		UpdateWrapper<CaseeSubjectRe> caseeSubject = new UpdateWrapper<>();
		caseeSubject.lambda().eq(CaseeSubjectRe::getCaseeId, projectSubjectReSaveDTO.getCaseeId());
		caseeSubject.lambda().eq(CaseeSubjectRe::getSubjectId, projectSubjectReSaveDTO.getSubjectId());
		caseeSubject.lambda().set(CaseeSubjectRe::getType, projectSubjectReSaveDTO.getCaseePersonnelType());
		caseeSubject.lambda().set(CaseeSubjectRe::getCaseePersonnelType, projectSubjectReSaveDTO.getCaseePersonnelType());
		caseeSubjectReService.update(caseeSubject);
		//??????????????????
		Subject subject = new Subject();
		BeanCopyUtil.copyBean(projectSubjectReSaveDTO, subject);
		remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM);

		List<ProjectSubjectReListVO> projectSubjectList = this.baseMapper.selectProjectSubjectReList(projectSubjectReSaveDTO.getProjectId(), -1);
		String applicantName = "";
		String executedName = "";
		for (ProjectSubjectReListVO projectSubjectReListVO : projectSubjectList) {
			String name = projectSubjectReListVO.getName();
			if (projectSubjectReListVO.getType() == 0) {
				if (applicantName.equals("")) {
					applicantName = name;
				} else {
					applicantName = applicantName + "," + name;
				}
			} else {
				if (executedName.equals("")) {
					executedName = name;
				} else {
					executedName = executedName + "," + name;
				}
			}
		}
		ProjectPaifu projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectId(projectSubjectReSaveDTO.getProjectId());
		projectPaifu.setSubjectPersons(executedName);
		projectPaifu.setProposersNames(applicantName);
		this.baseMapper.updateById(projectPaifu);

		Casee casee = new Casee();
		casee.setCaseeId(projectSubjectReSaveDTO.getCaseeId());
		casee.setApplicantName(applicantName);
		casee.setExecutedName(executedName);
		caseeService.updateById(casee);

		return 1;
	}

	@Override
	@Transactional
	public Integer removeProjectSubjectRe(ProjectSubjectReRemoveDTO projectSubjectReRemoveDTO) {
		Integer projectId = projectSubjectReRemoveDTO.getProjectId();
		QueryWrapper<ProjectSubjectRe> projectSubjectRe = new QueryWrapper<>();
		projectSubjectRe.lambda().eq(ProjectSubjectRe::getProjectId, projectId);
		projectSubjectRe.lambda().eq(ProjectSubjectRe::getSubjectId, projectSubjectReRemoveDTO.getSubjectId());
		projectSubjectReService.remove(projectSubjectRe);

		QueryWrapper<CaseeSubjectRe> caseeSubjectRe = new QueryWrapper<>();
		caseeSubjectRe.lambda().eq(CaseeSubjectRe::getCaseeId, projectSubjectReRemoveDTO.getCaseeId());
		caseeSubjectRe.lambda().eq(CaseeSubjectRe::getSubjectId, projectSubjectReRemoveDTO.getSubjectId());
		caseeSubjectReService.remove(caseeSubjectRe);

		Project project = this.baseMapper.selectById(projectId);
		String subjectName = "";
		if (projectSubjectReRemoveDTO.getCaseePersonnelType() == 0) {
			subjectName = project.getProposersNames();
		} else {
			subjectName = project.getSubjectPersons();
		}
		String[] subjectNameList = subjectName.split(",");
		String nameList = "";
		for (String name : subjectNameList) {
			if (!projectSubjectReRemoveDTO.getName().equals(name)) {
				if (nameList.equals("")) {
					nameList = name;
				} else {
					nameList = nameList + "," + name;
				}
			}
		}
		// ?????????????????????????????????????????????????????????
		ProjectPaifu projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectId(projectId);
		Casee casee = new Casee();
		casee.setCaseeId(projectSubjectReRemoveDTO.getCaseeId());

		if (projectSubjectReRemoveDTO.getCaseePersonnelType() == 0) {
			projectPaifu.setProposersNames(nameList);
			casee.setApplicantName(nameList);
		} else {
			projectPaifu.setSubjectPersons(nameList);
			casee.setExecutedName(nameList);
		}
		this.baseMapper.updateById(projectPaifu);
		caseeService.updateById(casee);
		return 1;
	}

	@Override
	@Transactional
	public Integer saveProjectReceipt(ProjectPaifuReceiptDTO projectPaifuReceiptDTO) {
		LiquiTransferRecordDetailsVO liquiTransferRecord = liquiTransferRecordService.getByLiquiTransferRecordId(projectPaifuReceiptDTO.getLiquiTransferRecordId());
		Casee casee = caseeService.getById(liquiTransferRecord.getCaseeId());

		ProjectPaifu projectPaifu = new ProjectPaifu();
		ProjectPaifuDetail projectPaifuDetail = new ProjectPaifuDetail();
		if (Objects.nonNull(projectPaifuReceiptDTO.getProjectId())) {
			projectPaifu = this.queryById(projectPaifuReceiptDTO.getProjectId());
			BeanCopyUtil.copyBean(projectPaifu.getProjectPaifuDetail(), projectPaifuDetail);
		} else {
			// ???????????????
			projectPaifu = new ProjectPaifu();
			BeanCopyUtil.copyBean(projectPaifuReceiptDTO, projectPaifu);
			projectPaifu.setProjectType(200);
			projectPaifu.setInsId(liquiTransferRecord.getEntrustedInsId());
			projectPaifu.setOutlesId(liquiTransferRecord.getEntrustedOutlesId());
			projectPaifu.setProposersNames(casee.getApplicantName());
			projectPaifu.setSubjectPersons(casee.getExecutedName());
			projectPaifuDetail.setApplicationSubmissionTime(liquiTransferRecord.getApplicationSubmissionTime());
			projectPaifuDetail.setAuctionApplicationFile(liquiTransferRecord.getAuctionApplicationFile());
		}
		projectPaifu.setProjectPaifuDetail(projectPaifuDetail);
		this.saveOrUpdate(projectPaifu);

		// ????????????????????????
		ProjectCaseeRe projectCaseeRe = new ProjectCaseeRe();
		projectCaseeRe.setProjectId(projectPaifu.getProjectId());
		projectCaseeRe.setCaseeId(liquiTransferRecord.getCaseeId());
		projectCaseeRe.setUserId(projectPaifuReceiptDTO.getUserId());
		projectCaseeRe.setActualName(projectPaifuReceiptDTO.getUserNickName());
		// ???????????????????????????????????????
		if (Objects.nonNull(projectPaifuReceiptDTO.getProjectId())) {
			QueryWrapper<ProjectCaseeRe> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(ProjectCaseeRe::getProjectId, projectPaifuReceiptDTO.getProjectId());
			queryWrapper.lambda().eq(ProjectCaseeRe::getCaseeId, liquiTransferRecord.getCaseeId());
			queryWrapper.lambda().eq(ProjectCaseeRe::getDelFlag, 0);
			ProjectCaseeRe projectCaseeReOne = projectCaseeReService.getOne(queryWrapper);
			if (Objects.isNull(projectCaseeReOne)) {
				projectCaseeReService.save(projectCaseeRe);
			}
		} else {
			// ???????????????????????????
			QueryWrapper<CaseeSubjectRe> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(CaseeSubjectRe::getCaseeId, liquiTransferRecord.getCaseeId());
			List<CaseeSubjectRe> caseeSubjectRes = caseeSubjectReService.list(queryWrapper);
			List<ProjectSubjectRe> projectSubjectRes = new ArrayList<>();
			for (CaseeSubjectRe caseeSubjectRe : caseeSubjectRes) {
				ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
				BeanCopyUtil.copyBean(caseeSubjectRe, projectSubjectRe);
				if (caseeSubjectRe.getCaseePersonnelType() == 1) {
					projectSubjectRe.setType(1);
				}
				projectSubjectRe.setProjectId(projectPaifu.getProjectId());
				projectSubjectRes.add(projectSubjectRe);
			}
			projectCaseeReService.save(projectCaseeRe);
			projectSubjectReService.saveBatch(projectSubjectRes);
		}
		// ????????????????????????
		QueryWrapper<AssetsLiquiTransferRecordRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsLiquiTransferRecordRe::getLiquiTransferRecordId, projectPaifuReceiptDTO.getLiquiTransferRecordId());
		List<AssetsLiquiTransferRecordRe> assetsLiquiTransferRecordRes = assetsLiquiTransferRecordReService.list(queryWrapper);

		List<AssetsReSubject> assetsReSubjectList = new ArrayList<>();
		// ????????????????????????
		for (AssetsLiquiTransferRecordRe assetsLiquiTransferRecordRe : assetsLiquiTransferRecordRes) {
			AssetsRe assetsRe = assetsReService.getById(assetsLiquiTransferRecordRe.getAssetsReId());
			// ????????????????????????????????????
			AssetsRePaifu paifuAssetsRe = new AssetsRePaifu();
			paifuAssetsRe.setProjectId(projectPaifu.getProjectId());
			paifuAssetsRe.setCaseeId(assetsRe.getCaseeId());
			paifuAssetsRe.setCreateCaseeId(assetsRe.getCreateCaseeId());
			paifuAssetsRe.setAssetsId(assetsRe.getAssetsId());
			paifuAssetsRe.setSubjectName(assetsRe.getSubjectName());
			paifuAssetsRe.setAssetsSource(assetsRe.getAssetsSource());
			paifuAssetsRe.setMortgageAssetsRecordsId(assetsRe.getMortgageAssetsRecordsId());
			paifuAssetsRe.setAssetsReDetail(assetsRe.getAssetsReDetail());
			assetsReService.save(paifuAssetsRe);

			//????????????????????????????????????
			TargetAddDTO targetAddDTO = new TargetAddDTO();
			targetAddDTO.setCaseeId(assetsRe.getCaseeId());
			targetAddDTO.setProcedureNature(6060);
			targetAddDTO.setOutlesId(projectPaifu.getOutlesId());
			targetAddDTO.setProjectId(projectPaifu.getProjectId());
			targetAddDTO.setGoalId(assetsRe.getAssetsId());
			targetAddDTO.setGoalType(20001);
			try {
				targetService.saveTargetAddDTO(targetAddDTO);
			} catch (Exception e) {
				e.printStackTrace();
			}

			// ????????????????????????
			QueryWrapper<AssetsReSubject> assetsReSubjectQueryWrapper = new QueryWrapper<>();
			assetsReSubjectQueryWrapper.lambda().eq(AssetsReSubject::getAssetsReId, assetsLiquiTransferRecordRe.getAssetsReId());
			List<AssetsReSubject> assetsReSubjects = assetsReSubjectService.list(assetsReSubjectQueryWrapper);
			// ????????????????????????????????????
			for (AssetsReSubject assetsReSubject : assetsReSubjects) {
				AssetsReSubject paifuAssetsReSubject = new AssetsReSubject();
				paifuAssetsReSubject.setAssetsReId(paifuAssetsRe.getAssetsReId());
				paifuAssetsReSubject.setSubjectId(assetsReSubject.getSubjectId());
				assetsReSubjectList.add(paifuAssetsReSubject);
			}
		}
		assetsReSubjectService.saveBatch(assetsReSubjectList);

		// ???????????????????????????
		ProjectOutlesDealRe projectOutlesDealRe = new ProjectOutlesDealRe();
		projectOutlesDealRe.setInsId(liquiTransferRecord.getEntrustInsId());
		projectOutlesDealRe.setOutlesId(liquiTransferRecord.getEntrustOutlesId());
		projectOutlesDealRe.setUserId(liquiTransferRecord.getCreateBy());
		projectOutlesDealRe.setType(1);
		projectOutlesDealRe.setProjectId(projectPaifu.getProjectId());
		projectOutlesDealReService.save(projectOutlesDealRe);

		// ?????????????????????????????????
		ProjectStatusSaveDTO projectStatusSaveDTO = new ProjectStatusSaveDTO();
		projectStatusSaveDTO.setType(1);
		projectStatusSaveDTO.setStatusVal(1000);
		projectStatusSaveDTO.setStatusName("??????");
		projectStatusSaveDTO.setProjectId(projectPaifu.getProjectId());
		projectStatusSaveDTO.setChangeTime(projectPaifuReceiptDTO.getTakeTime());
		projectStatusService.saveStatusRecord(projectStatusSaveDTO);
		return projectCaseeRe.getProjectId();
	}

	@Override
	public ProjectPaifuDetailVO queryProjectCaseeDetail(Integer projectId) {
		return this.baseMapper.selectByProjectId(projectId);
	}

	public Long queryPropertyFlowChartPage(String nodeKey, List<Integer> assetsTypeList) {
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);

		AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO = new AssetsRePaifuFlowChartPageDTO();
		assetsRePaifuFlowChartPageDTO.setNodeKey(nodeKey);
		assetsRePaifuFlowChartPageDTO.setAssetsTypeList(assetsTypeList);
		IPage<AssetsRePaifuFlowChartPageVO> assetsRePaifuFlowChartPageVOIPage = this.queryFlowChartPage(page, assetsRePaifuFlowChartPageDTO);
		return assetsRePaifuFlowChartPageVOIPage.getTotal();
	}

	@Override
	public CountFlowChartVO countProjectFlowChart() {
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);
		CountFlowChartVO countFlowChartVO = new CountFlowChartVO();

		// ???????????????
		LiquiTransferRecordPageDTO liquiTransferRecordPageDTO = new LiquiTransferRecordPageDTO();
		liquiTransferRecordPageDTO.setStatus(0);
		countFlowChartVO.setPendingCount(liquiTransferRecordService.queryLiquiTransferRecordPage(page, liquiTransferRecordPageDTO).getTotal());

		//???????????????
		List<Integer> chattelNotAvailable = new ArrayList<>();
		chattelNotAvailable.add(20202);
		countFlowChartVO.setChattelNotAvailable(queryPropertyFlowChartPage("paiFu_STCC_XK_XK", chattelNotAvailable));

		//??????????????????
		List<Integer> assetsTypeList = new ArrayList<>();
		assetsTypeList.add(20201);
		assetsTypeList.add(20204);
		countFlowChartVO.setRealEstateNotSurveyed(queryPropertyFlowChartPage("paiFu_STCC_XK_XK", assetsTypeList));

		//????????????????????????
		AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO = new AssetsRePaifuFlowChartPageDTO();
		IPage<AssetsRePaifuFlowChartPageVO> realEstateSurveyNotRegistered = this.queryRealEstateNotSurveyedPage(page, assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setRealEstateSurveyNotRegistered(realEstateSurveyNotRegistered.getTotal());

		//???????????????????????????
		countFlowChartVO.setAuctionPriceBasisNotIssued(queryPropertyFlowChartPage("paiFu_STCC_JGYJ_JGYJ", null));

		//??????????????????
		countFlowChartVO.setThereIsEvidenceNotListed(queryPropertyFlowChartPage("paiFu_STCC_PMGG_PMGG", null));

		//??????????????????
		IPage<AssetsRePaifuFlowChartPageVO> announcementPeriodNotAuctioned = this.queryAnnouncementPeriodNotAuctioned(page, assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setAnnouncementPeriodNotAuctioned(announcementPeriodNotAuctioned.getTotal());

		//?????????????????????
		IPage<AssetsRePaifuFlowChartPageVO> auctionExpiresWithoutResults = this.queryAuctionExpiresWithoutResults(page, assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setAuctionExpiresWithoutResults(auctionExpiresWithoutResults.getTotal());

		//?????????????????????
		IPage<AssetsRePaifuFlowChartPageVO> auctionTransactionNotProcessed = queryAuctionTransactionNotProcessed(page, assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setAuctionTransactionNotProcessed(auctionTransactionNotProcessed.getTotal());

		//????????????????????????
		IPage<AssetsRePaifuFlowChartPageVO> auctionTransactionFailedNotProcessed = queryAuctionTransactionFailedNotProcessed(page, assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setAuctionTransactionFailedNotProcessed(auctionTransactionFailedNotProcessed.getTotal());

		//?????????????????????
		IPage<AssetsRePaifuFlowChartPageVO> auctionExceptionNotCancelled = queryAuctionExceptionNotCancelled(page, assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setAuctionExceptionNotCancelled(auctionExceptionNotCancelled.getTotal());

		//??????/???????????????
		IPage<AssetsRePaifuFlowChartPageVO> arrivalCompensationNotAdjudicated = queryArrivalCompensationNotAdjudicated(page, assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setArrivalCompensationNotAdjudicated(arrivalCompensationNotAdjudicated.getTotal());

		//???????????????
		IPage<AssetsRePaifuFlowChartPageVO> rulingNotService = queryRulingNotService(page, assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setRulingNotService(rulingNotService.getTotal());

		//???????????????
		countFlowChartVO.setDeliveredButNotVacated(queryPropertyFlowChartPage("paiFu_STCC_TTCG_TTCG", null));

		return countFlowChartVO;
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryFlowChartPage(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryFlowChartPage(page, assetsRePaifuFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryRealEstateNotSurveyedPage(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryRealEstateNotSurveyedPage(page, assetsRePaifuFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryAnnouncementPeriodNotAuctioned(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryAnnouncementPeriodNotAuctioned(page, assetsRePaifuFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryAuctionExpiresWithoutResults(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryAuctionExpiresWithoutResults(page, assetsRePaifuFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryAuctionTransactionNotProcessed(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryAuctionTransactionNotProcessed(page, assetsRePaifuFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryAuctionExceptionNotCancelled(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryAuctionExceptionNotCancelled(page, assetsRePaifuFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryRulingNotService(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryRulingNotService(page, assetsRePaifuFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryAuctionTransactionFailedNotProcessed(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryAuctionTransactionFailedNotProcessed(page, assetsRePaifuFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryArrivalCompensationNotAdjudicated(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryArrivalCompensationNotAdjudicated(page, assetsRePaifuFlowChartPageDTO, insOutlesDTO);
	}

	@Override
	public ProjectPaifu queryById(Integer projectId) {
		Project project = this.baseMapper.selectById(projectId);
		ProjectPaifu projectPaifu = new ProjectPaifu();
		BeanCopyUtil.copyBean(project, projectPaifu);
		return projectPaifu;
	}

	@Override
	@Transactional
	public Integer updateProjectAmount(Integer projectId) {
		// ?????????????????????????????????
		BigDecimal projectAmount = expenseRecordService.totalAmountByProjectId(projectId);
		ProjectPaifu projectPaifu = this.queryById(projectId);
		ProjectPaifuDetail projectPaifuDetail = new ProjectPaifuDetail();
		if (projectPaifu.getProjectPaifuDetail() != null) {
			BeanCopyUtil.copyBean(projectPaifu.getProjectPaifuDetail(), projectPaifuDetail);
		}
		projectPaifuDetail.setProjectAmount(projectAmount);
		projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectId(projectId);
		projectPaifu.setProjectPaifuDetail(projectPaifuDetail);
		return this.baseMapper.updateById(projectPaifu);
	}

	@Override
	@Transactional
	public Integer updateRepaymentAmount(Integer projectId) {
		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setProjectId(projectId);
		paymentRecord.setStatus(1);
		// ?????????????????????
		BigDecimal repaymentAmount = paymentRecordService.sumCourtPayment(paymentRecord);
		ProjectPaifu projectPaifu = this.queryById(projectId);
		ProjectPaifuDetail projectPaifuDetail = new ProjectPaifuDetail();
		if (projectPaifu.getProjectPaifuDetail() != null) {
			BeanCopyUtil.copyBean(projectPaifu.getProjectPaifuDetail(), projectPaifuDetail);
		}
		projectPaifuDetail.setRepaymentAmount(repaymentAmount);
		projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectId(projectId);
		projectPaifu.setProjectPaifuDetail(projectPaifuDetail);
		return this.baseMapper.updateById(projectPaifu);
	}

	@Override
	@Transactional
	public Integer excelImport(ImportPaifuDTO importPaifuDTO) {
		// ????????????
		if (importPaifuDTO.getInProgressList() != null) {
			for (ImportPaifu importPaifu : importPaifuDTO.getInProgressList()) {
				//----------------????????????----------------------------
				Casee casee = saveOrUpdateCasee(importPaifu, 1, importPaifuDTO.getCourtId());
				//----------------????????????----------------------------
				Project project = saveOrUpdateProject(importPaifu, 1000, casee.getCaseeId(), importPaifuDTO.getInsId(), importPaifuDTO.getOutlesId(), importPaifuDTO.getUserId(), importPaifuDTO.getUserNickName());
				//----------------????????????----------------------------
				Assets assets = saveOrUpdateAssets(importPaifu);
				//----------------?????????????????????----------------------------
				saveOrUpdateAssetsRe(importPaifu, 100, project.getProjectId(), casee.getCaseeId(), assets.getAssetsId());
			}
		}
		// ????????????
		if (importPaifuDTO.getClosedList() != null) {
			for (ImportPaifu closedList : importPaifuDTO.getClosedList()) {
				//----------------????????????----------------------------
				Casee casee = saveOrUpdateCasee(closedList, 3, importPaifuDTO.getCourtId());
				//----------------????????????----------------------------
				Project project = saveOrUpdateProject(closedList, 4000, casee.getCaseeId(), importPaifuDTO.getInsId(), importPaifuDTO.getOutlesId(), importPaifuDTO.getUserId(), importPaifuDTO.getUserNickName());
				//----------------????????????----------------------------
				Assets assets = saveOrUpdateAssets(closedList);
				//----------------?????????????????????----------------------------
				saveOrUpdateAssetsRe(closedList, 500, project.getProjectId(), casee.getCaseeId(), assets.getAssetsId());
			}
		}
		return 1;
	}

	@Transactional
	public Casee saveOrUpdateCasee(ImportPaifu importPaifu, Integer caseeStatus, Integer courtId) {
		String caseeNumber = importPaifu.getCaseeNumber();
		//----------------????????????----------------------------
		QueryWrapper<Casee> caseeQueryWrapper = new QueryWrapper<>();
			caseeQueryWrapper.lambda().eq(Casee::getCaseeNumber, caseeNumber);
			caseeQueryWrapper.lambda().eq(Casee::getDelFlag, 0);
			caseeQueryWrapper.last("limit 1");
			Casee casee = caseeService.getOne(caseeQueryWrapper);
			String[] executedNames = splitSubject(importPaifu.getExecutedName());
			String[] applicantNames = splitSubject(importPaifu.getApplicantName());
			if (casee == null) {
			Casee saveCasee = new Casee();
			saveCasee.setCaseeNumber(caseeNumber);
			saveCasee.setStartTime(importPaifu.getStartTime());
			saveCasee.setJudgeName(importPaifu.getJudgeName());
			saveCasee.setExecutedName(importPaifu.getExecutedName());
			saveCasee.setApplicantName(importPaifu.getApplicantName());
			saveCasee.setCourtId(courtId);
			saveCasee.setStatus(caseeStatus);
			caseeService.save(saveCasee);
			casee = saveCasee;

			List<CaseeSubjectRe> caseeSubjectRes = new ArrayList();
			if (executedNames != null) {
				for (String executedName : executedNames) {
					if (executedName != null) {
						Integer subjectId = getSubjectId(executedName);
						if(subjectId>0){
							CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
							caseeSubjectRe.setCaseeId(casee.getCaseeId());
							caseeSubjectRe.setType(1);
							caseeSubjectRe.setCaseePersonnelType(1);
							caseeSubjectRe.setSubjectId(subjectId);
							caseeSubjectRes.add(caseeSubjectRe);
						}
					}
				}
			}
			if (applicantNames != null) {
				for (String applicantName : applicantNames) {
					if (applicantName != null) {
						Integer subjectId = getSubjectId(applicantName);
						if(subjectId>0){
							CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
							caseeSubjectRe.setCaseeId(casee.getCaseeId());
							caseeSubjectRe.setType(0);
							caseeSubjectRe.setCaseePersonnelType(0);
							caseeSubjectRe.setSubjectId(subjectId);
							caseeSubjectRes.add(caseeSubjectRe);
						}
					}
				}
			}

			caseeSubjectReService.saveBatch(caseeSubjectRes);
		} else {
			if (casee.getStatus() != 1) {
				Casee updateCasee = new Casee();
				updateCasee.setCaseeId(casee.getCaseeId());
				updateCasee.setStatus(caseeStatus);
				caseeService.updateById(updateCasee);
			}
		}
		return casee;
	}

	@Transactional
	public Project saveOrUpdateProject(ImportPaifu importPaifu, Integer projectStatus, Integer caseeId, Integer insId, Integer outlesId, Integer userId, String userNickName) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(insId);
		insOutlesDTO.setOutlesId(outlesId);
		// ???????????????????????????????????????id?????????????????????????????????
		Project project = projectService.getProjectIdByCaseeNumber(200, importPaifu.getCaseeNumber(), insOutlesDTO);
		if (project == null) {
			Project projectPaifu = new Project();
			String companyCode = importPaifu.getCompanyCode();
			// ????????????????????????
			String year = companyCode.substring(companyCode.indexOf("(") + 1, companyCode.indexOf(")"));
			String alias = companyCode.substring(companyCode.indexOf(")") + 1, companyCode.lastIndexOf("??????"));
			Integer word = getWord(year, alias);
			projectPaifu.setYear(year);
			projectPaifu.setAlias(alias);
			projectPaifu.setWord(word);
			String newCompanyCode = "???" + year + "???" + companyCode.substring(companyCode.indexOf(")") + 1, companyCode.length());
			projectPaifu.setCompanyCode(newCompanyCode);
			projectPaifu.setProposersNames(importPaifu.getApplicantName());
			projectPaifu.setSubjectPersons(importPaifu.getExecutedName());
			projectPaifu.setInsId(insId);
			projectPaifu.setOutlesId(outlesId);
			projectPaifu.setUserId(userId);
			projectPaifu.setUserNickName(userNickName);
			projectPaifu.setProjectType(200);
			projectPaifu.setStatus(projectStatus);
			projectPaifu.setTakeTime(importPaifu.getStartTime());
			this.baseMapper.insert(projectPaifu);
			project = projectPaifu;

			QueryWrapper<CaseeSubjectRe> caseeSubjectReQueryWrapper = new QueryWrapper<>();
			caseeSubjectReQueryWrapper.lambda().eq(CaseeSubjectRe::getCaseeId, caseeId);
			List<CaseeSubjectRe> caseeSubjectRes = caseeSubjectReService.list(caseeSubjectReQueryWrapper);
			List<ProjectSubjectRe> projectSubjectRes = new ArrayList();
			if (caseeSubjectRes != null) {
				for (CaseeSubjectRe caseeSubjectRe : caseeSubjectRes) {
					ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
					projectSubjectRe.setProjectId(projectPaifu.getProjectId());
					projectSubjectRe.setType(caseeSubjectRe.getCaseePersonnelType());
					projectSubjectRe.setSubjectId(caseeSubjectRe.getSubjectId());
					projectSubjectRes.add(projectSubjectRe);
				}
			}
			projectSubjectReService.saveBatch(projectSubjectRes);
			ProjectCaseeRe projectCaseeRe = new ProjectCaseeRe();
			projectCaseeRe.setProjectId(project.getProjectId());
			projectCaseeRe.setCaseeId(caseeId);
			projectCaseeRe.setUserId(userId);
			projectCaseeRe.setActualName(userNickName);
			projectCaseeReService.save(projectCaseeRe);
		} else {
			Project updateProject = new Project();
			updateProject.setProjectId(project.getProjectId());
			updateProject.setStatus(projectStatus);
			projectService.updateById(updateProject);
		}
		return project;
	}

	@Transactional
	public Assets saveOrUpdateAssets(ImportPaifu importPaifu) {
		String assetsName = importPaifu.getAssetsName();
		QueryWrapper<Assets> assetsQueryWrapper = new QueryWrapper<>();
		assetsQueryWrapper.lambda().eq(Assets::getAssetsName, assetsName);
		assetsQueryWrapper.last("limit 1");
		Assets assets = assetsService.getOne(assetsQueryWrapper);
		if (assets == null) {
			Assets saveAssets = new Assets();
			saveAssets.setAssetsName(importPaifu.getAssetsName());
			saveAssets.setAssetsType(importPaifu.getAssetsType());
			saveAssets.setOwner(importPaifu.getOwner());
			assetsService.save(saveAssets);
			assets = saveAssets;
		}
		return assets;
	}

	@Transactional
	public void saveOrUpdateAssetsRe(ImportPaifu importPaifu, Integer assetsReStatus, Integer projectId, Integer caseeId, Integer assetsId) {
		AssetsRe getAssetsRe = getAssetsRe(projectId, assetsId);
		String subjectName = null;
		if (getAssetsRe == null) {
			AssetsRePaifu assetsRePaifu = new AssetsRePaifu();
			assetsRePaifu.setProjectId(projectId);
			assetsRePaifu.setCaseeId(caseeId);
			assetsRePaifu.setCreateCaseeId(caseeId);
			assetsRePaifu.setAssetsId(assetsId);
			assetsRePaifu.setStatus(assetsReStatus);
			AssetsRePaifuDetail assetsRePaifuDetail = new AssetsRePaifuDetail();
			assetsRePaifu.setAssetsRePaifuDetail(assetsRePaifuDetail);
			assetsReService.save(assetsRePaifu);
			List<AssetsReSubject> assetsReSubjects = new ArrayList<>();

			List<SubjectOptionVO> subjectOptionVOS = caseeSubjectReService.querySubjectList(caseeId, 1);
			String[] assetsSubjectNames = splitSubject(importPaifu.getOwner());
			if (subjectOptionVOS != null) {
				for (SubjectOptionVO subjectOptionVO : subjectOptionVOS) {
					if (assetsSubjectNames != null) {
						for (String assetsSubject : assetsSubjectNames) {
							if (subjectOptionVO.getName().equals(assetsSubject)) {
								if (subjectName == null) {
									subjectName = assetsSubject;
								} else {
									subjectName = subjectName + "," + assetsSubject;
								}
								AssetsReSubject assetsReSubject = new AssetsReSubject();
								assetsReSubject.setSubjectId(subjectOptionVO.getSubjectId());
								assetsReSubject.setAssetsReId(assetsRePaifu.getAssetsReId());
								assetsReSubjects.add(assetsReSubject);
							}
						}
					}
				}
			}
			AssetsRe updateAssetsRe = new AssetsRe();
			updateAssetsRe.setAssetsReId(assetsRePaifu.getAssetsReId());
			updateAssetsRe.setSubjectName(subjectName);
			assetsReService.updateById(updateAssetsRe);
			assetsReSubjectService.saveBatch(assetsReSubjects);

			if (assetsReStatus == 100) {
				Project project = projectLiquiService.getById(projectId);
				//????????????????????????
				TargetAddDTO targetAddDTO = new TargetAddDTO();
				targetAddDTO.setCaseeId(caseeId);
				targetAddDTO.setProcedureNature(6060);
				targetAddDTO.setOutlesId(project.getOutlesId());
				targetAddDTO.setProjectId(projectId);
				targetAddDTO.setGoalId(assetsId);
				targetAddDTO.setGoalType(20001);
				try {
					targetService.saveTargetAddDTO(targetAddDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if (getAssetsRe.getStatus() != 100) {
				AssetsRe updateAssetsRe = new AssetsRe();
				updateAssetsRe.setAssetsId(getAssetsRe.getAssetsReId());
				updateAssetsRe.setStatus(assetsReStatus);
				assetsReService.updateById(updateAssetsRe);
			}
		}
	}

	public String[] splitSubject(String subjectNameList) {
		String[] subjectName = null;
		if (subjectNameList != null) {
			if (subjectNameList.indexOf("???") != -1) {
				subjectName = subjectNameList.split("???");
			} else {
				subjectName = new String[]{subjectNameList};
			}
		}
		return subjectName;
	}

	public Integer getWord(String year, String alias) {
		QueryWrapper<Project> projectQueryWrapper = new QueryWrapper<>();
		projectQueryWrapper.eq("project_type", 200).eq("year", year).eq("alias", alias).orderByDesc("word").last("limit 1");
		Project projectres = this.getOne(projectQueryWrapper);
		int word;
		if (projectres == null) {
			word = 1;
		} else {
			word = projectres.getWord() + 1;
		}
		return word;
	}

	@Transactional
	public Integer getSubjectId(String subjectName) {
		if(subjectName.equals("??????????????????")){
			return 0;
		}
		R<Subject> subject = remoteSubjectService.queryBySubjectName(subjectName, SecurityConstants.FROM);
		Integer subjectId = 0;
		if (subject.getData() == null) {
			Subject saveSubject = new Subject();
			saveSubject.setName(subjectName);
			R<Subject> subjectR = remoteSubjectService.saveSubject(saveSubject, SecurityConstants.FROM);
			subjectId = subjectR.getData().getSubjectId();
		} else {
			subjectId = subject.getData().getSubjectId();
		}
		return subjectId;
	}

	public AssetsRe getAssetsRe(Integer projectId, Integer assetsId) {
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getProjectId, projectId);
		queryWrapper.lambda().eq(AssetsRe::getAssetsId, assetsId);
		queryWrapper.last("limit 1");
		return assetsReService.getOne(queryWrapper);
	}

	@Override
	public void projectPaifuExport(HttpServletResponse response, ProjectPaifuPageDTO projectPaifuPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		List<String> yearList = this.baseMapper.getYearList(projectPaifuPageDTO, insOutlesDTO);
		ProjectPaifuExcelExportVO projectPaifuExcelExportVO = new ProjectPaifuExcelExportVO();
		List<ProjectPaifuInProgressVO> inProgressList = new ArrayList<>();
		for (String year : yearList) {
			projectPaifuPageDTO.setYear(year);
			projectPaifuPageDTO.setProjectStatus(1000);

			List<ProjectPaifuExportVO> projectPaifuExportVOS = this.baseMapper.projectPaifuExport(projectPaifuPageDTO, insOutlesDTO);
			if(projectPaifuExportVOS.size()!=0){
				ProjectPaifuInProgressVO projectPaifuInProgressVO = new ProjectPaifuInProgressVO();
				projectPaifuInProgressVO.setYear(year);
				projectPaifuInProgressVO.setExportVOS(projectPaifuExportVOS);
				inProgressList.add(projectPaifuInProgressVO);
			}
		}
		projectPaifuExcelExportVO.setInProgressList(inProgressList);
		projectPaifuPageDTO.setYear(null);
		projectPaifuPageDTO.setProjectStatus(4000);
		List<ProjectPaifuExportVO> caseClosed = this.baseMapper.projectPaifuExport(projectPaifuPageDTO, insOutlesDTO);
		projectPaifuExcelExportVO.setCaseClosed(caseClosed);

		downloadUtils.downloadPaifuLedger(response, projectPaifuExcelExportVO);
	}


}
