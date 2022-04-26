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
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.ProjectStatusSaveDTO;
import com.pig4cloud.pig.casee.dto.TargetAddDTO;
import com.pig4cloud.pig.casee.dto.paifu.*;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.paifuentity.AssetsRePaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ProjectPaifuDetail;
import com.pig4cloud.pig.casee.mapper.ProjectPaifuMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.paifu.ProjectPaifuDetailVO;
import com.pig4cloud.pig.casee.vo.paifu.ProjectPaifuPageVO;
import com.pig4cloud.pig.casee.vo.paifu.ProjectSubjectReListVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	private RemoteRelationshipAuthenticateService relationshipAuthenticateService;
	@Autowired
	private TargetService targetService;

	@Override
	@Transactional
	public Integer saveProjectCasee(ProjectPaifuSaveDTO projectPaifuSaveDTO){
		Integer courtId = relationshipAuthenticateService.getByAuthenticateId(projectPaifuSaveDTO.getCourtId(), SecurityConstants.FROM).getData();

		// 保存项目表
		ProjectPaifu projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectType(200);
		BeanCopyUtil.copyBean(projectPaifuSaveDTO,projectPaifu);
		Integer save = this.baseMapper.insert(projectPaifu);

		// 保存案件表
		Casee casee = new Casee();
		BeanCopyUtil.copyBean(projectPaifuSaveDTO,casee);
		casee.setCourtId(courtId);
		caseeService.save(casee);

		List<ProjectSubjectRe> projectSubjectRes = new ArrayList();
		List<CaseeSubjectRe> caseeSubjectRes = new ArrayList();
		String proposersNames = "";
		String subjectPersons = "";
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
		LiquiTransferRecord liquiTransferRecord =  liquiTransferRecordService.getById(projectPaifuReceiptDTO.getLiquiTransferRecordId());
		Casee casee = caseeService.getById(liquiTransferRecord.getCaseeId());
		// 保存项目表
		ProjectPaifu projectPaifu = new ProjectPaifu();
		BeanCopyUtil.copyBean(projectPaifuReceiptDTO,projectPaifu);
		projectPaifu.setProjectType(200);
		projectPaifu.setInsId(liquiTransferRecord.getEntrustedInsId());
		projectPaifu.setOutlesId(liquiTransferRecord.getEntrustedOutlesId());
		projectPaifu.setProposersNames(casee.getApplicantName());
		projectPaifu.setSubjectPersons(casee.getExecutedName());
		ProjectPaifuDetail projectPaifuDetail = new ProjectPaifuDetail();
		BeanCopyUtil.copyBean(projectPaifuReceiptDTO,projectPaifuDetail);
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


}
