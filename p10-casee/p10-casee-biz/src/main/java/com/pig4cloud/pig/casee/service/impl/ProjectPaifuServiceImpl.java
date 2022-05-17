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
 * 拍辅项目表
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
	public Integer saveProjectCasee(ProjectPaifuSaveDTO projectPaifuSaveDTO){

		// 保存项目表
		ProjectPaifu projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectType(200);
		BeanCopyUtil.copyBean(projectPaifuSaveDTO,projectPaifu);
		Integer save = this.baseMapper.insert(projectPaifu);

		// 保存案件表
		Casee casee = new Casee();
		BeanCopyUtil.copyBean(projectPaifuSaveDTO,casee);
		caseeService.save(casee);

		List<ProjectSubjectRe> projectSubjectRes = new ArrayList();
		List<CaseeSubjectRe> caseeSubjectRes = new ArrayList();
		String proposersNames = "";
		String subjectPersons = "";
		List<CustomerSubjectDTO> customerSubjectDTOS = new ArrayList<>();
		// 遍历人员集合
		for(CaseeSubjectReListDTO item : projectPaifuSaveDTO.getApplicantList()){
			Subject subject = new Subject();
			BeanCopyUtil.copyBean(item,subject);
			Integer subjectId = remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM).getData();
			ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
			projectSubjectRe.setSubjectId(subjectId);
			projectSubjectRe.setProjectId(projectPaifu.getProjectId());
			projectSubjectRe.setType(item.getCaseePersonnelType());
			projectSubjectRes.add(projectSubjectRe);

			CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
			caseeSubjectRe.setCaseeId(casee.getCaseeId());
			caseeSubjectRe.setSubjectId(subjectId);
			caseeSubjectRe.setCaseePersonnelType(item.getCaseePersonnelType());
			caseeSubjectRes.add(caseeSubjectRe);
			if(item.getCaseePersonnelType()==0){
				// 保存客户
				CustomerSubjectDTO customerSubjectDTO = new CustomerSubjectDTO();
				customerSubjectDTO.setSubjectId(subjectId);
				customerSubjectDTO.setNatureType(30000);
				customerSubjectDTO.setProjectId(projectPaifu.getProjectId());
				customerSubjectDTO.setCaseeId(casee.getCaseeId());
				customerSubjectDTO.setRecommenderId(projectPaifuSaveDTO.getUserId());
				customerSubjectDTO.setInsId(projectPaifuSaveDTO.getInsId());
				customerSubjectDTO.setOutlesId(projectPaifuSaveDTO.getOutlesId());
				customerSubjectDTOS.add(customerSubjectDTO);
				if(proposersNames.equals("")){
					proposersNames = item.getName();
				}else{
					proposersNames = proposersNames+","+item.getName();
				}
			}else{
				if(subjectPersons.equals("")){
					subjectPersons = item.getName();
				}else{
					subjectPersons = subjectPersons+","+item.getName();
				}
			}
		}
		projectSubjectReService.saveBatch(projectSubjectRes);
		caseeSubjectReService.saveBatch(caseeSubjectRes);
		customerService.saveCustomerBatch(customerSubjectDTOS);

		//更新项目所有委托名称和所有债务人名称
		ProjectPaifu updateProject = new ProjectPaifu();
		updateProject.setProjectId(projectPaifu.getProjectId());
		updateProject.setProposersNames(proposersNames);
		updateProject.setSubjectPersons(subjectPersons);
		this.baseMapper.updateById(updateProject);

		//更新案件所有申请人名称和所有被执行人名称
		Casee updateCasee = new Casee();
		updateCasee.setCaseeId(casee.getCaseeId());
		updateCasee.setApplicantName(proposersNames);
		updateCasee.setExecutedName(subjectPersons);
		caseeService.updateById(updateCasee);

		// 保存项目案件关联表
		ProjectCaseeRe projectCaseeRe = new ProjectCaseeRe();
		projectCaseeRe.setProjectId(projectPaifu.getProjectId());
		projectCaseeRe.setCaseeId(casee.getCaseeId());
		BeanCopyUtil.copyBean(projectPaifuSaveDTO,projectCaseeRe);
		projectCaseeReService.save(projectCaseeRe);

		// 保存项目状态变更记录表
		ProjectStatusSaveDTO projectStatusSaveDTO = new ProjectStatusSaveDTO();
		projectStatusSaveDTO.setType(1);
		projectStatusSaveDTO.setStatusVal(1000);
		projectStatusSaveDTO.setStatusName("在办");
		projectStatusSaveDTO.setProjectId(projectPaifu.getProjectId());
		projectStatusSaveDTO.setChangeTime(projectPaifuSaveDTO.getTakeTime());
		projectStatusService.saveStatusRecord(projectStatusSaveDTO);

		return save;
	}

	@Override
	public IPage<ProjectPaifuPageVO> queryProjectCaseePage(Page page, ProjectPaifuPageDTO projectPaifuPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPagePaifu(page, projectPaifuPageDTO, insOutlesDTO);
	}

	@Override
	public ProjectPaifuDetailVO queryProjectCaseeDetailList(Integer projectId){
		ProjectPaifuDetailVO projectPaifuDetailVO = this.baseMapper.selectByProjectId(projectId);
		projectPaifuDetailVO.setApplicantList(this.baseMapper.selectProjectSubjectReList(projectId,0));
		projectPaifuDetailVO.setExecutedList(this.baseMapper.selectProjectSubjectReList(projectId,1));
//		projectPaifuDetailVO.setCaseeList(caseeService.queryByPaifuProjectId(projectId));
		return projectPaifuDetailVO;
	}

	@Override
	@Transactional
	public Integer addProjectSubjectRe(ProjectSubjectReSaveDTO projectSubjectReSaveDTO){
		Project project = this.baseMapper.selectById(projectSubjectReSaveDTO.getProjectId());
		// 保存主体表
		Subject subject = new Subject();
		BeanCopyUtil.copyBean(projectSubjectReSaveDTO,subject);
		Integer subjectId = remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM).getData();
		// 保存项目主体关联表
		ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
		projectSubjectRe.setSubjectId(subjectId);
		projectSubjectRe.setProjectId(projectSubjectReSaveDTO.getProjectId());
		projectSubjectRe.setType(projectSubjectReSaveDTO.getCaseePersonnelType());
		projectSubjectReService.save(projectSubjectRe);
		// 保存案件主体关联表
		CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
		caseeSubjectRe.setCaseeId(projectSubjectReSaveDTO.getCaseeId());
		caseeSubjectRe.setSubjectId(subjectId);
		caseeSubjectRe.setCaseePersonnelType(projectSubjectReSaveDTO.getCaseePersonnelType());
		caseeSubjectReService.save(caseeSubjectRe);
		String projectSubjectName = null;
		ProjectPaifu updateProject = new ProjectPaifu();
		Casee updateCasee = new Casee();
		if(projectSubjectReSaveDTO.getCaseePersonnelType()==0){
			projectSubjectName = project.getProposersNames()+","+projectSubjectReSaveDTO.getName();
			updateProject.setProposersNames(projectSubjectName);
			updateCasee.setApplicantName(projectSubjectName);
			// 保存客户
			CustomerSubjectDTO customerSubjectDTO = new CustomerSubjectDTO();
			customerSubjectDTO.setSubjectId(subjectId);
			customerSubjectDTO.setNatureType(30000);
			customerSubjectDTO.setProjectId(project.getProjectId());
			customerSubjectDTO.setCaseeId(projectSubjectReSaveDTO.getCaseeId());
			customerSubjectDTO.setRecommenderId(project.getUserId());
			customerSubjectDTO.setInsId(project.getInsId());
			customerSubjectDTO.setOutlesId(project.getOutlesId());
			customerService.saveCustomer(customerSubjectDTO);
		}else{
			projectSubjectName = project.getSubjectPersons()+","+projectSubjectReSaveDTO.getName();
			updateProject.setSubjectPersons(projectSubjectName);
			updateCasee.setExecutedName(projectSubjectName);
		}
		//更新项目所有委托名称或所有债务人名称
		updateProject.setProjectId(projectSubjectReSaveDTO.getProjectId());
		this.baseMapper.updateById(updateProject);

		//更新案件所有申请人名称和所有被执行人名称
		updateCasee.setCaseeId(projectSubjectReSaveDTO.getCaseeId());
		caseeService.updateById(updateCasee);
		return 1;
	}

	@Override
	public 	ProjectSubjectReListVO queryProjectSubjectRe(Integer projectId,String unifiedIdentity){
		return this.baseMapper.selectProjectSubjectRe(projectId,unifiedIdentity);
	}

	@Override
	@Transactional
	public 	Integer modifyByProjectId(ProjectPaifuModifyDTO projectPaifuModifyDTO){
		ProjectPaifu projectPaifu = new ProjectPaifu();
		BeanCopyUtil.copyBean(projectPaifuModifyDTO,projectPaifu);
		Integer modify = this.baseMapper.updateById(projectPaifu);

		Casee casee = new Casee();
		BeanCopyUtil.copyBean(projectPaifuModifyDTO,casee);
		caseeService.updateById(casee);
		return modify;
	}

	@Override
	@Transactional
	public Integer modifyProjectSubjectRe(ProjectSubjectReSaveDTO projectSubjectReSaveDTO){
		// 更新项目主体人员类型
		UpdateWrapper<ProjectSubjectRe> projectSubjectRe = new UpdateWrapper<>();
		projectSubjectRe.lambda().eq(ProjectSubjectRe::getProjectId,projectSubjectReSaveDTO.getProjectId());
		projectSubjectRe.lambda().eq(ProjectSubjectRe::getSubjectId,projectSubjectReSaveDTO.getSubjectId());
		projectSubjectRe.lambda().set(ProjectSubjectRe::getType,projectSubjectReSaveDTO.getCaseePersonnelType());
		projectSubjectReService.update(projectSubjectRe);
		// 更新案件主体人员类型
		UpdateWrapper<CaseeSubjectRe> caseeSubject = new UpdateWrapper<>();
		caseeSubject.lambda().eq(CaseeSubjectRe::getCaseeId,projectSubjectReSaveDTO.getCaseeId());
		caseeSubject.lambda().eq(CaseeSubjectRe::getSubjectId,projectSubjectReSaveDTO.getSubjectId());
		caseeSubject.lambda().set(CaseeSubjectRe::getType,projectSubjectReSaveDTO.getCaseePersonnelType());
		caseeSubject.lambda().set(CaseeSubjectRe::getCaseePersonnelType,projectSubjectReSaveDTO.getCaseePersonnelType());
		caseeSubjectReService.update(caseeSubject);
		//更新主体信息
		Subject subject = new Subject();
		BeanCopyUtil.copyBean(projectSubjectReSaveDTO,subject);
		remoteSubjectService.saveOrUpdateById(subject,SecurityConstants.FROM);

		List<ProjectSubjectReListVO> projectSubjectList = this.baseMapper.selectProjectSubjectReList(projectSubjectReSaveDTO.getProjectId(),-1);
		String applicantName = "";
		String executedName = "";
		for(ProjectSubjectReListVO projectSubjectReListVO:projectSubjectList){
			String name = projectSubjectReListVO.getName();
			if(projectSubjectReListVO.getType()==0){
				if(applicantName.equals("")){
					applicantName = name;
				}else{
					applicantName = applicantName+","+name;
				}
			}else{
				if(executedName.equals("")){
					executedName = name;
				}else{
					executedName = executedName+","+name;
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
	public Integer removeProjectSubjectRe(ProjectSubjectReRemoveDTO projectSubjectReRemoveDTO){
		Integer projectId = projectSubjectReRemoveDTO.getProjectId();
		QueryWrapper<ProjectSubjectRe> projectSubjectRe = new QueryWrapper<>();
		projectSubjectRe.lambda().eq(ProjectSubjectRe::getProjectId,projectId);
		projectSubjectRe.lambda().eq(ProjectSubjectRe::getSubjectId,projectSubjectReRemoveDTO.getSubjectId());
		projectSubjectReService.remove(projectSubjectRe);

		QueryWrapper<CaseeSubjectRe> caseeSubjectRe = new QueryWrapper<>();
		caseeSubjectRe.lambda().eq(CaseeSubjectRe::getCaseeId,projectSubjectReRemoveDTO.getCaseeId());
		caseeSubjectRe.lambda().eq(CaseeSubjectRe::getSubjectId,projectSubjectReRemoveDTO.getSubjectId());
		caseeSubjectReService.remove(caseeSubjectRe);

		Project project = this.baseMapper.selectById(projectId);
		String subjectName = "";
		if(projectSubjectReRemoveDTO.getCaseePersonnelType()==0){
			subjectName = project.getProposersNames();
		}else{
			subjectName = project.getSubjectPersons();
		}
		String[] subjectNameList = subjectName.split(",");
		String nameList = "";
		for(String name : subjectNameList){
			if(!projectSubjectReRemoveDTO.getName().equals(name)){
				if(nameList.equals("")){
					nameList = name;
				}else{
					nameList = nameList+","+name;
				}
			}
		}
		// 更新项目和案件申请人、被执行人所有名称
		ProjectPaifu projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectId(projectId);
		Casee casee = new Casee();
		casee.setCaseeId(projectSubjectReRemoveDTO.getCaseeId());

		if(projectSubjectReRemoveDTO.getCaseePersonnelType()==0){
			projectPaifu.setProposersNames(nameList);
			casee.setApplicantName(nameList);
		}else{
			projectPaifu.setSubjectPersons(nameList);
			casee.setExecutedName(nameList);
		}
		this.baseMapper.updateById(projectPaifu);
		caseeService.updateById(casee);
		return 1;
	}

	@Override
	@Transactional
	public 	Integer saveProjectReceipt(ProjectPaifuReceiptDTO projectPaifuReceiptDTO){
		LiquiTransferRecordDetailsVO liquiTransferRecord = liquiTransferRecordService.getByLiquiTransferRecordId(projectPaifuReceiptDTO.getLiquiTransferRecordId());
		Casee casee = caseeService.getById(liquiTransferRecord.getCaseeId());

		ProjectPaifuDetail projectPaifuDetail = new ProjectPaifuDetail();
		if(Objects.nonNull(projectPaifuReceiptDTO.getProjectId())){
			ProjectPaifu projectPaifu = this.queryById(projectPaifuReceiptDTO.getProjectId());
			BeanCopyUtil.copyBean(projectPaifu.getProjectPaifuDetail(),projectPaifuDetail);
		}

		// 保存项目表
		ProjectPaifu projectPaifu = new ProjectPaifu();
		BeanCopyUtil.copyBean(projectPaifuReceiptDTO,projectPaifu);
		projectPaifu.setProjectType(200);
		projectPaifu.setInsId(liquiTransferRecord.getEntrustedInsId());
		projectPaifu.setOutlesId(liquiTransferRecord.getEntrustedOutlesId());
		projectPaifu.setProposersNames(casee.getApplicantName());
		projectPaifu.setSubjectPersons(casee.getExecutedName());
		projectPaifuDetail.setApplicationSubmissionTime(liquiTransferRecord.getApplicationSubmissionTime());
		projectPaifuDetail.setAuctionApplicationFile(liquiTransferRecord.getAuctionApplicationFile());
		projectPaifu.setProjectPaifuDetail(projectPaifuDetail);
		this.saveOrUpdate(projectPaifu);

		// 保存项目案件关联
		ProjectCaseeRe projectCaseeRe = new ProjectCaseeRe();
		projectCaseeRe.setProjectId(projectPaifu.getProjectId());
		projectCaseeRe.setCaseeId(liquiTransferRecord.getCaseeId());
		projectCaseeRe.setUserId(projectPaifuReceiptDTO.getUserId());
		projectCaseeRe.setActualName(projectPaifuReceiptDTO.getUserNickName());
		// 验证项目案件关联表是否存在
		if(Objects.nonNull(projectPaifuReceiptDTO.getProjectId())){
			QueryWrapper<ProjectCaseeRe> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(ProjectCaseeRe::getProjectId,projectPaifuReceiptDTO.getProjectId());
			queryWrapper.lambda().eq(ProjectCaseeRe::getCaseeId,liquiTransferRecord.getCaseeId());
			queryWrapper.lambda().eq(ProjectCaseeRe::getDelFlag,0);
			ProjectCaseeRe projectCaseeReOne = projectCaseeReService.getOne(queryWrapper);
			if(Objects.isNull(projectCaseeReOne)){
				projectCaseeReService.save(projectCaseeRe);
			}
		}else{
			// 保存项目主体关联表
			QueryWrapper<CaseeSubjectRe> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(CaseeSubjectRe::getCaseeId,liquiTransferRecord.getCaseeId());
			List<CaseeSubjectRe> caseeSubjectRes = caseeSubjectReService.list(queryWrapper);
			List<ProjectSubjectRe> projectSubjectRes = new ArrayList<>();
			for(CaseeSubjectRe caseeSubjectRe:caseeSubjectRes){
				ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
				BeanCopyUtil.copyBean(caseeSubjectRe,projectSubjectRe);
				if(caseeSubjectRe.getCaseePersonnelType()==1){
					projectSubjectRe.setType(1);
				}
				projectSubjectRe.setProjectId(projectPaifu.getProjectId());
				projectSubjectRes.add(projectSubjectRe);
			}
			projectCaseeReService.save(projectCaseeRe);
			projectSubjectReService.saveBatch(projectSubjectRes);
		}
		// 查询移交财产信息
		QueryWrapper<AssetsLiquiTransferRecordRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsLiquiTransferRecordRe::getLiquiTransferRecordId,projectPaifuReceiptDTO.getLiquiTransferRecordId());
		List<AssetsLiquiTransferRecordRe> assetsLiquiTransferRecordRes = assetsLiquiTransferRecordReService.list(queryWrapper);

		List<AssetsReSubject> assetsReSubjectList = new ArrayList<>();
		// 遍历移交财产集合
		for(AssetsLiquiTransferRecordRe assetsLiquiTransferRecordRe:assetsLiquiTransferRecordRes){
			AssetsRe assetsRe = assetsReService.getById(assetsLiquiTransferRecordRe.getAssetsReId());
			// 保存拍辅项目案件财产关联
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

			//添加任务数据以及程序信息
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

			// 查询财产主体关联
			QueryWrapper<AssetsReSubject> assetsReSubjectQueryWrapper = new QueryWrapper<>();
			assetsReSubjectQueryWrapper.lambda().eq(AssetsReSubject::getAssetsReId,assetsLiquiTransferRecordRe.getAssetsReId());
			List<AssetsReSubject> assetsReSubjects = assetsReSubjectService.list(assetsReSubjectQueryWrapper);
			// 遍历项目案件财产主体关联
			for(AssetsReSubject assetsReSubject : assetsReSubjects){
				AssetsReSubject paifuAssetsReSubject = new AssetsReSubject();
				paifuAssetsReSubject.setAssetsReId(paifuAssetsRe.getAssetsReId());
				paifuAssetsReSubject.setSubjectId(assetsReSubject.getSubjectId());
				assetsReSubjectList.add(paifuAssetsReSubject);
			}
		}
		assetsReSubjectService.saveBatch(assetsReSubjectList);

		// 保存项目委托关联表
		ProjectOutlesDealRe projectOutlesDealRe = new ProjectOutlesDealRe();
		projectOutlesDealRe.setInsId(liquiTransferRecord.getEntrustInsId());
		projectOutlesDealRe.setOutlesId(liquiTransferRecord.getEntrustOutlesId());
		projectOutlesDealRe.setUserId(liquiTransferRecord.getCreateBy());
		projectOutlesDealRe.setType(1);
		projectOutlesDealRe.setProjectId(projectPaifu.getProjectId());
		projectOutlesDealReService.save(projectOutlesDealRe);

		// 保存项目状态变更记录表
		ProjectStatusSaveDTO projectStatusSaveDTO = new ProjectStatusSaveDTO();
		projectStatusSaveDTO.setType(1);
		projectStatusSaveDTO.setStatusVal(1000);
		projectStatusSaveDTO.setStatusName("在办");
		projectStatusSaveDTO.setProjectId(projectPaifu.getProjectId());
		projectStatusSaveDTO.setChangeTime(projectPaifuReceiptDTO.getTakeTime());
		projectStatusService.saveStatusRecord(projectStatusSaveDTO);
		return projectCaseeRe.getProjectId();
	}

	@Override
	public	ProjectPaifuDetailVO queryProjectCaseeDetail(Integer projectId){
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
	public CountFlowChartVO countProjectFlowChart(){
		Page page = new Page();
		page.setCurrent(1);
		page.setSize(10);
		CountFlowChartVO countFlowChartVO = new CountFlowChartVO();

		// 待接收统计
		LiquiTransferRecordPageDTO liquiTransferRecordPageDTO = new LiquiTransferRecordPageDTO();
		liquiTransferRecordPageDTO.setStatus(0);
		countFlowChartVO.setPendingCount(liquiTransferRecordService.queryLiquiTransferRecordPage(page,liquiTransferRecordPageDTO).getTotal());

		//动产未现勘
		List<Integer> chattelNotAvailable = new ArrayList<>();
		chattelNotAvailable.add(20202);
		countFlowChartVO.setChattelNotAvailable(queryPropertyFlowChartPage("paiFu_STCC_XK_XK",chattelNotAvailable));

		//不动产未现勘
		List<Integer> assetsTypeList = new ArrayList<>();
		assetsTypeList.add(20201);
		assetsTypeList.add(20204);
		countFlowChartVO.setRealEstateNotSurveyed(queryPropertyFlowChartPage("paiFu_STCC_XK_XK",assetsTypeList));

		//不动产现勘未入户
		AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO = new AssetsRePaifuFlowChartPageDTO();
		IPage<AssetsRePaifuFlowChartPageVO> realEstateSurveyNotRegistered = this.queryRealEstateNotSurveyedPage(page,assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setRealEstateSurveyNotRegistered(realEstateSurveyNotRegistered.getTotal());

		//拍卖价格依据未出具
		countFlowChartVO.setAuctionPriceBasisNotIssued(queryPropertyFlowChartPage("paiFu_STCC_JGYJ_JGYJ",null));

		//有依据未上拍
		countFlowChartVO.setThereIsEvidenceNotListed(queryPropertyFlowChartPage("paiFu_STCC_PMGG_PMGG",null));

		//公告期未拍卖
		IPage<AssetsRePaifuFlowChartPageVO> announcementPeriodNotAuctioned = this.queryAnnouncementPeriodNotAuctioned(page,assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setAnnouncementPeriodNotAuctioned(announcementPeriodNotAuctioned.getTotal());

		//拍卖到期无结果
		IPage<AssetsRePaifuFlowChartPageVO> auctionExpiresWithoutResults = this.queryAuctionExpiresWithoutResults(page,assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setAuctionExpiresWithoutResults(auctionExpiresWithoutResults.getTotal());

		//拍卖成交未处理
		IPage<AssetsRePaifuFlowChartPageVO> auctionTransactionNotProcessed = queryAuctionTransactionNotProcessed(page,assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setAuctionTransactionNotProcessed(auctionTransactionNotProcessed.getTotal());

		//拍卖不成交未处理
		IPage<AssetsRePaifuFlowChartPageVO> auctionTransactionFailedNotProcessed = queryAuctionTransactionFailedNotProcessed(page,assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setAuctionTransactionFailedNotProcessed(auctionTransactionFailedNotProcessed.getTotal());

		//拍卖异常未撤销
		IPage<AssetsRePaifuFlowChartPageVO> auctionExceptionNotCancelled = queryAuctionExceptionNotCancelled(page,assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setAuctionExceptionNotCancelled(auctionExceptionNotCancelled.getTotal());

		//到款/抵偿未裁定
		IPage<AssetsRePaifuFlowChartPageVO> arrivalCompensationNotAdjudicated = queryArrivalCompensationNotAdjudicated(page,assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setArrivalCompensationNotAdjudicated(arrivalCompensationNotAdjudicated.getTotal());

		//裁定未送达
		IPage<AssetsRePaifuFlowChartPageVO> rulingNotService = queryRulingNotService(page,assetsRePaifuFlowChartPageDTO);
		countFlowChartVO.setRulingNotService(rulingNotService.getTotal());

		//送达未腾退
		countFlowChartVO.setDeliveredButNotVacated(queryPropertyFlowChartPage("paiFu_STCC_TTCG_TTCG",null));

		return countFlowChartVO;
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryFlowChartPage(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryFlowChartPage(page,assetsRePaifuFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryRealEstateNotSurveyedPage(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryRealEstateNotSurveyedPage(page,assetsRePaifuFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryAnnouncementPeriodNotAuctioned(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryAnnouncementPeriodNotAuctioned(page,assetsRePaifuFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryAuctionExpiresWithoutResults(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryAuctionExpiresWithoutResults(page,assetsRePaifuFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryAuctionTransactionNotProcessed(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryAuctionTransactionNotProcessed(page,assetsRePaifuFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryAuctionExceptionNotCancelled(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryAuctionExceptionNotCancelled(page,assetsRePaifuFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryRulingNotService(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryRulingNotService(page,assetsRePaifuFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryAuctionTransactionFailedNotProcessed(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryAuctionTransactionFailedNotProcessed(page,assetsRePaifuFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<AssetsRePaifuFlowChartPageVO> queryArrivalCompensationNotAdjudicated(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryArrivalCompensationNotAdjudicated(page,assetsRePaifuFlowChartPageDTO,insOutlesDTO);
	}

	@Override
	public ProjectPaifu queryById(Integer projectId){
		Project project = this.baseMapper.selectById(projectId);
		ProjectPaifu projectPaifu = new ProjectPaifu();
		BeanCopyUtil.copyBean(project,projectPaifu);
		return projectPaifu;
	}

	@Override
	@Transactional
	public Integer updateProjectAmount(Integer projectId){
		// 统计费用产生明细总金额
		BigDecimal projectAmount = expenseRecordService.totalAmountByProjectId(projectId);
		ProjectPaifu projectPaifu = this.queryById(projectId);
		ProjectPaifuDetail projectPaifuDetail = new ProjectPaifuDetail();
		if(projectPaifu.getProjectPaifuDetail()!=null){
			BeanCopyUtil.copyBean(projectPaifu.getProjectPaifuDetail(),projectPaifuDetail);
		}
		projectPaifuDetail.setProjectAmount(projectAmount);
		projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectId(projectId);
		projectPaifu.setProjectPaifuDetail(projectPaifuDetail);
		return this.baseMapper.updateById(projectPaifu);
	}

	@Override
	@Transactional
	public Integer updateRepaymentAmount(Integer projectId){
		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setProjectId(projectId);
		// 统计回款总金额
		BigDecimal repaymentAmount = paymentRecordService.sumCourtPayment(paymentRecord);
		ProjectPaifu projectPaifu = this.queryById(projectId);
		ProjectPaifuDetail projectPaifuDetail = new ProjectPaifuDetail();
		if(projectPaifu.getProjectPaifuDetail()!=null){
			BeanCopyUtil.copyBean(projectPaifu.getProjectPaifuDetail(),projectPaifuDetail);
		}
		projectPaifuDetail.setRepaymentAmount(repaymentAmount);
		projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectId(projectId);
		projectPaifu.setProjectPaifuDetail(projectPaifuDetail);
		return this.baseMapper.updateById(projectPaifu);
	}

	@Override
	@Transactional
	public Integer excelImport(ImportPaifuDTO importPaifuDTO){
		// 在办案件
		if(importPaifuDTO.getInProgressList()!=null){
			for(ImportPaifu importPaifu : importPaifuDTO.getInProgressList()){
				//----------------案件保存----------------------------
				Casee casee = saveOrUpdateCasee(importPaifu,1,importPaifuDTO.getCourtId());
				//----------------项目保存----------------------------
				Project project = saveOrUpdateProject(importPaifu,1000,casee.getCaseeId(),importPaifuDTO.getInsId(),importPaifuDTO.getOutlesId(),importPaifuDTO.getUserId(),importPaifuDTO.getUserNickName());
				//----------------财产保存----------------------------
				Assets assets = saveOrUpdateAssets(importPaifu);
				//----------------财产关联表保存----------------------------
				saveOrUpdateAssetsRe(importPaifu,100,project.getProjectId(),casee.getCaseeId(),assets.getAssetsId());
			}
		}
		// 结案案件
		if(importPaifuDTO.getClosedList()!=null){
			for(ImportPaifu closedList : importPaifuDTO.getClosedList()){
				//----------------案件保存----------------------------
				Casee casee = saveOrUpdateCasee(closedList,3,importPaifuDTO.getCourtId());
				//----------------项目保存----------------------------
				Project project = saveOrUpdateProject(closedList,4000,casee.getCaseeId(),importPaifuDTO.getInsId(),importPaifuDTO.getOutlesId(),importPaifuDTO.getUserId(),importPaifuDTO.getUserNickName());
				//----------------财产保存----------------------------
				Assets assets = saveOrUpdateAssets(closedList);
				//----------------财产关联表保存----------------------------
				saveOrUpdateAssetsRe(closedList,500,project.getProjectId(),casee.getCaseeId(),assets.getAssetsId());
			}
		}
		return 1;
	}

	@Transactional
	public Casee saveOrUpdateCasee(ImportPaifu importPaifu,Integer caseeStatus,Integer courtId){
		String caseeNumber = importPaifu.getCaseeNumber();
		//----------------案件验证----------------------------
		QueryWrapper<Casee> caseeQueryWrapper = new QueryWrapper<>();
		caseeQueryWrapper.lambda().eq(Casee::getCaseeNumber,caseeNumber);
		caseeQueryWrapper.lambda().eq(Casee::getDelFlag,0);
		caseeQueryWrapper.last("limit 1");
		Casee casee = caseeService.getOne(caseeQueryWrapper);
		String[] executedNames = splitSubject(importPaifu.getExecutedName());
		String[] applicantNames = splitSubject(importPaifu.getApplicantName());
		if(casee==null){
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
			if(executedNames!=null){
				for(String executedName : executedNames){
					if(executedName!=null){
						Integer subjectId = getSubjectId(executedName);
						CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
						caseeSubjectRe.setCaseeId(casee.getCaseeId());
						caseeSubjectRe.setType(1);
						caseeSubjectRe.setCaseePersonnelType(1);
						caseeSubjectRe.setSubjectId(subjectId);
						caseeSubjectRes.add(caseeSubjectRe);
					}
				}
			}
			if(applicantNames!=null){
				for(String applicantName : applicantNames){
					if(applicantName!=null){
						Integer subjectId = getSubjectId(applicantName);
						CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
						caseeSubjectRe.setCaseeId(casee.getCaseeId());
						caseeSubjectRe.setType(0);
						caseeSubjectRe.setCaseePersonnelType(0);
						caseeSubjectRe.setSubjectId(subjectId);
						caseeSubjectRes.add(caseeSubjectRe);
					}
				}
			}

			caseeSubjectReService.saveBatch(caseeSubjectRes);
		}else{
			if(casee.getStatus()!=1){
				Casee updateCasee = new Casee();
				updateCasee.setCaseeId(casee.getCaseeId());
				updateCasee.setStatus(caseeStatus);
				caseeService.updateById(updateCasee);
			}
		}
		return casee;
	}

	@Transactional
	public Project saveOrUpdateProject(ImportPaifu importPaifu,Integer projectStatus,Integer caseeId,Integer insId,Integer outlesId,Integer userId,String userNickName){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(insId);
		insOutlesDTO.setOutlesId(outlesId);
		// 查询案件案号是否有拍辅项目id，有则不添加项目和案件
		Project project = projectService.getProjectIdByCaseeNumber(200,importPaifu.getCaseeNumber(),insOutlesDTO);
		if(project==null){
			Project projectPaifu = new Project();
			String companyCode = importPaifu.getCompanyCode();
			// 解析公司业务案号
			String year = companyCode.substring(companyCode.indexOf("(")+1,companyCode.indexOf(")"));
			String alias = companyCode.substring(companyCode.indexOf(")")+1,companyCode.lastIndexOf("法拍"));
			Integer word = getWord(year,alias);
			projectPaifu.setYear(year);
			projectPaifu.setAlias(alias);
			projectPaifu.setWord(word);
			String newCompanyCode = "（"+year+"）"+companyCode.substring(companyCode.indexOf(")")+1,companyCode.length());
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
			caseeSubjectReQueryWrapper.lambda().eq(CaseeSubjectRe::getCaseeId,caseeId);
			List<CaseeSubjectRe> caseeSubjectRes = caseeSubjectReService.list(caseeSubjectReQueryWrapper);
			List<ProjectSubjectRe> projectSubjectRes = new ArrayList();
			if(caseeSubjectRes!=null){
				for(CaseeSubjectRe caseeSubjectRe : caseeSubjectRes){
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
		}else{
			Project updateProject = new Project();
			updateProject.setProjectId(project.getProjectId());
			updateProject.setStatus(projectStatus);
			projectService.updateById(updateProject);
		}
		return project;
	}

	@Transactional
	public Assets saveOrUpdateAssets(ImportPaifu importPaifu){
		String assetsName = importPaifu.getAssetsName();
		QueryWrapper<Assets> assetsQueryWrapper = new QueryWrapper<>();
		assetsQueryWrapper.lambda().eq(Assets::getAssetsName,assetsName);
		assetsQueryWrapper.last("limit 1");
		Assets assets = assetsService.getOne(assetsQueryWrapper);
		if(assets==null){
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
	public void saveOrUpdateAssetsRe(ImportPaifu importPaifu,Integer assetsReStatus,Integer projectId,Integer caseeId,Integer assetsId){
		AssetsRe getAssetsRe = getAssetsRe(projectId,assetsId);
		String subjectName = null;
		if(getAssetsRe==null){
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

			List<SubjectOptionVO> subjectOptionVOS = caseeSubjectReService.querySubjectList(caseeId,1);
			String[] assetsSubjectNames = splitSubject(importPaifu.getOwner());
			if(subjectOptionVOS!=null){
				for(SubjectOptionVO subjectOptionVO:subjectOptionVOS){
					if(assetsSubjectNames!=null){
						for(String assetsSubject:assetsSubjectNames){
							if(subjectOptionVO.getName().equals(assetsSubject)){
								if(subjectName==null){
									subjectName = assetsSubject;
								}else{
									subjectName = subjectName+","+assetsSubject;
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

			if (assetsReStatus==100){
				Project project = projectLiquiService.getById(projectId);
				//添加拍辅财产程序
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
		}else{
			if(getAssetsRe.getStatus()!=100){
				AssetsRe updateAssetsRe = new AssetsRe();
				updateAssetsRe.setAssetsId(getAssetsRe.getAssetsReId());
				updateAssetsRe.setStatus(assetsReStatus);
				assetsReService.updateById(updateAssetsRe);
			}
		}
	}

	public String[] splitSubject(String subjectNameList){
		String[] subjectName = null;
		if(subjectNameList!=null){
			if(subjectNameList.indexOf("、")!=-1){
				subjectName = subjectNameList.split("、");
			}else{
				subjectName = new String[]{subjectNameList};
			}
		}
		return subjectName;
	}

	public Integer getWord(String year,String alias){
		QueryWrapper<Project> projectQueryWrapper =new QueryWrapper<>();
		projectQueryWrapper.eq("project_type",200).eq("year",year).eq("alias", alias).orderByDesc("word").last("limit 1");
		Project projectres = this.getOne(projectQueryWrapper);
		int word;
		if(projectres==null){
			word=1;
		}else {
			word =projectres.getWord()+1;
		}
		return word;
	}

	@Transactional
	public Integer getSubjectId(String subjectName){
		R<Subject> subject = remoteSubjectService.queryBySubjectName(subjectName,SecurityConstants.FROM);
		Integer subjectId = 0;
		if(subject.getData()==null){
			Subject saveSubject = new Subject();
			saveSubject.setName(subjectName);
			R<Subject> subjectR = remoteSubjectService.saveSubject(saveSubject,SecurityConstants.FROM);
			subjectId = subjectR.getData().getSubjectId();
		}else{
			subjectId = subject.getData().getSubjectId();
		}
		return subjectId;
	}

	public AssetsRe getAssetsRe(Integer projectId,Integer assetsId){
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getProjectId,projectId);
		queryWrapper.lambda().eq(AssetsRe::getAssetsId,assetsId);
		queryWrapper.last("limit 1");
		return assetsReService.getOne(queryWrapper);
	}

	@Override
	public void projectPaifuExport(HttpServletResponse response, ProjectPaifuPageDTO projectPaifuPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		List<String> yearList = this.baseMapper.getYearList(projectPaifuPageDTO, insOutlesDTO);
		ProjectPaifuExcelExportVO projectPaifuExcelExportVO = new ProjectPaifuExcelExportVO();
		List<ProjectPaifuInProgressVO> inProgressList = new ArrayList<>();
		for (String year:yearList){

			projectPaifuPageDTO.setYear(year);
			projectPaifuPageDTO.setProjectStatus(1000);

			List<ProjectPaifuExportVO> projectPaifuExportVOS = this.baseMapper.projectPaifuExport(projectPaifuPageDTO, insOutlesDTO);

			ProjectPaifuInProgressVO projectPaifuInProgressVO = new ProjectPaifuInProgressVO();
			projectPaifuInProgressVO.setYear(year);
			projectPaifuInProgressVO.setExportVOS(projectPaifuExportVOS);
			inProgressList.add(projectPaifuInProgressVO);
		}
		projectPaifuExcelExportVO.setInProgressList(inProgressList);
		projectPaifuPageDTO.setProjectStatus(4000);
		List<ProjectPaifuExportVO> caseClosed = this.baseMapper.projectPaifuExport(projectPaifuPageDTO, insOutlesDTO);
		projectPaifuExcelExportVO.setCaseClosed(caseClosed);

		System.out.println("--------------------"+projectPaifuExcelExportVO);

		downloadUtils.downloadPaifuLedger(response,projectPaifuExcelExportVO);
	}



}
