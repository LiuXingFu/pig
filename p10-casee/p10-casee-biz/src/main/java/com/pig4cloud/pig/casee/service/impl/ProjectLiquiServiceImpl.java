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
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.ProjectLiquiDTO;
import com.pig4cloud.pig.casee.dto.ProjectQueryLiquiDTO;
import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.api.feign.RemoteInstitutionService;
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.admin.api.vo.InstitutionVO;
import com.pig4cloud.pig.admin.api.vo.UserVO;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.ProjectLiquiPageDTO;
import com.pig4cloud.pig.casee.dto.ProjectModifyStatusDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.detail.ProjectLiQuiDetail;
import com.pig4cloud.pig.casee.mapper.ProjectLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.ProjectLiquiVO;
import com.pig4cloud.pig.casee.dto.ProjectLiquiAddDTO;
import com.pig4cloud.pig.casee.vo.ProjectLiquiDetailsVO;
import com.pig4cloud.pig.casee.vo.ProjectLiquiPageVO;
import com.pig4cloud.pig.casee.vo.TransferRecordBankLoanVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.BeanUtils;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
	private TransferRecordService transferRecordService;

	@Autowired
	private ProjectOutlesDealReService projectOutlesDealReService;

	@Autowired
	private RemoteUserService userService;

	@Autowired
	private RemoteInstitutionService institutionService;

	@Autowired
	private ProjectStatusService projectStatusService;

	@Autowired
	private ProjectSubjectReService projectSubjectReService;

	@Autowired
	private SubjectBankLoanReService subjectBankLoanReService;

	@Autowired
	private SecurityUtilsService securityUtilsService;



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
		TransferRecordBankLoanVO transferRecordBankLoanVO = transferRecordService.getTransferRecordBankLoan(projectLiquiAddVO.getTransferRecordId());
		projectLiqui.setInsId(transferRecordBankLoanVO.getEntrustedInsId());
		projectLiqui.setOutlesId(transferRecordBankLoanVO.getEntrustedOutlesId());
		projectLiqui.setProjectType(100);
		ProjectLiQuiDetail projectLiQuiDetail = new ProjectLiQuiDetail();
		projectLiQuiDetail.setProjectAmount(transferRecordBankLoanVO.getHandoverAmount());
		projectLiQuiDetail.setRemainingPayment(BigDecimal.valueOf(0));
		projectLiqui.setProjectLiQuiDetail(projectLiQuiDetail);
		// 查询办理人名称
		R<UserVO> userVOR = userService.getUserById(projectLiquiAddVO.getUserId(), SecurityConstants.FROM);
		projectLiqui.setUserNickName(userVOR.getData().getActualName());
		// 查询委托机构名称
		R<InstitutionVO> institutionVOR = institutionService.getById(transferRecordBankLoanVO.getInsId(), SecurityConstants.FROM);
		projectLiqui.setProposersNames(institutionVOR.getData().getInsName());
		this.baseMapper.insert(projectLiqui);

		// 保存项目状态变更记录表
		ProjectStatus projectStatus = new ProjectStatus();
		projectStatus.setUserName("在办");
		projectStatus.setType(1);
		projectStatus.setSourceId(projectLiqui.getProjectId());
		projectStatusService.save(projectStatus);


		// 查询银行借贷主体关联表
		QueryWrapper<SubjectBankLoanRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SubjectBankLoanRe::getBankLoanId,transferRecordBankLoanVO.getBankLoanId());
		List<SubjectBankLoanRe> subjectBankLoanReList =  subjectBankLoanReService.list(queryWrapper);
		List<ProjectSubjectRe> projectSubjectRes = new ArrayList<>();
		// 遍历银行借贷关联表copy到项目主体关联表中
		subjectBankLoanReList.stream().forEach(item ->{
			ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
			BeanCopyUtil.copyBean(item,projectSubjectRe);
			projectSubjectRe.setProjectId(projectLiqui.getProjectId());
			projectSubjectRes.add(projectSubjectRe);
		});
		// 保存项目主体关联表
		projectSubjectReService.saveBatch(projectSubjectRes);

		// 保存项目委托关联表
		ProjectOutlesDealRe projectOutlesDealRe = new ProjectOutlesDealRe();
		projectOutlesDealRe.setInsId(transferRecordBankLoanVO.getInsId());
		projectOutlesDealRe.setOutlesId(transferRecordBankLoanVO.getOutlesId());
		projectOutlesDealRe.setUserId(0);
		projectOutlesDealRe.setType(1);
		projectOutlesDealRe.setProjectId(0);
		projectOutlesDealReService.save(projectOutlesDealRe);
		// 返回添加的项目id
		return projectLiqui.getProjectId();
	}

	@Override
	public ProjectLiquiDetailsVO getProjectDetails(Integer projectId){
		// 获取项目基本信息及办理机构名称和网点名称
		ProjectLiquiDetailsVO projectLiquiDetailsVO = this.baseMapper.selectByProjectId(projectId);


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


}
