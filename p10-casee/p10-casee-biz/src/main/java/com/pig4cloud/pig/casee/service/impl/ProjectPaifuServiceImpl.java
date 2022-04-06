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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.paifu.ProjectPaifuPageDTO;
import com.pig4cloud.pig.casee.dto.paifu.ProjectPaifuSaveDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.mapper.ProjectPaifuMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.paifu.ProjectPaifuPageVO;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 拍辅项目表
 */
@Service
public class ProjectPaifuServiceImpl extends ServiceImpl<ProjectPaifuMapper, Project> implements ProjectPaifuService {
	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;
	@Autowired
	private SecurityUtilsService securityUtilsService;
	@Autowired
	private CaseeService caseeService;
	@Autowired
	private ProjectSubjectReService projectSubjectReService;
	@Autowired
	private CaseeSubjectReService caseeSubjectReService;
	@Autowired
	private ProjectStatusService projectStatusService;


	@Override
	public Integer saveProjectCasee(ProjectPaifuSaveDTO projectPaifuSaveDTO){
		PigUser pigUser = securityUtilsService.getCacheUser();
		// 保存项目表
		ProjectPaifu projectPaifu = new ProjectPaifu();
		projectPaifu.setProjectType(200);
		BeanCopyUtil.copyBean(projectPaifuSaveDTO,projectPaifu);
		projectPaifu.setInsId(pigUser.getInsId());
		projectPaifu.setOutlesId(pigUser.getOutlesId());
		Integer save = this.baseMapper.insert(projectPaifu);

		// 保存案件表
		Casee casee = new Casee();
		BeanCopyUtil.copyBean(projectPaifuSaveDTO,casee);
		caseeService.save(casee);

		List<ProjectSubjectRe> projectSubjectRes = new ArrayList();
		List<CaseeSubjectRe> caseeSubjectRes = new ArrayList();
		// 遍历申请人集合
		projectPaifuSaveDTO.getApplicantList().stream().forEach(item->{
			ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
			projectSubjectRe.setSubjectId(item.getSubjectId());
			projectSubjectRe.setProjectId(projectPaifu.getProjectId());
			projectSubjectRe.setType(0);
			projectSubjectRes.add(projectSubjectRe);

			CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
			caseeSubjectRe.setCaseeId(casee.getCaseeId());
			caseeSubjectRe.setSubjectId(item.getSubjectId());
			caseeSubjectRe.setType(item.getType());
			caseeSubjectRe.setCaseePersonnelType(0);
			caseeSubjectRes.add(caseeSubjectRe);
		});
		// 遍历被执行人集合
		projectPaifuSaveDTO.getExecutedList().stream().forEach(item->{
			ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
			projectSubjectRe.setSubjectId(item.getSubjectId());
			projectSubjectRe.setProjectId(projectPaifu.getProjectId());
			projectSubjectRe.setType(1);
			projectSubjectRes.add(projectSubjectRe);

			CaseeSubjectRe caseeSubjectRe = new CaseeSubjectRe();
			caseeSubjectRe.setCaseeId(casee.getCaseeId());
			caseeSubjectRe.setSubjectId(item.getSubjectId());
			caseeSubjectRe.setType(item.getType());
			caseeSubjectRe.setCaseePersonnelType(1);
			caseeSubjectRes.add(caseeSubjectRe);
		});
		projectSubjectReService.saveBatch(projectSubjectRes);
		caseeSubjectReService.saveBatch(caseeSubjectRes);
		// 保存项目案件关联表
		ProjectCaseeRe projectCaseeRe = new ProjectCaseeRe();
		projectCaseeRe.setProjectId(projectPaifu.getProjectId());
		projectCaseeRe.setCaseeId(casee.getCaseeId());
		BeanCopyUtil.copyBean(projectPaifuSaveDTO,projectCaseeRe);

		// 保存项目状态变更记录表
		ProjectStatus projectStatus = new ProjectStatus();
		projectStatus.setStatusName("在办");
		projectStatus.setUserName(projectPaifuSaveDTO.getUserNickName());
		projectStatus.setType(1);
		projectStatus.setSourceId(projectPaifu.getProjectId());
		projectStatusService.save(projectStatus);

		return save;
	}

	@Override
	public IPage<ProjectPaifuPageVO> queryProjectCaseePage(Page page, ProjectPaifuPageDTO projectPaifuPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPagePaifu(page, projectPaifuPageDTO, insOutlesDTO);
	}

}
