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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.entity.ProjectStatus;
import com.pig4cloud.pig.casee.mapper.ProjectStatusMapper;
import com.pig4cloud.pig.casee.service.ProjectStatusService;
import org.springframework.stereotype.Service;

/**
 * 项目/案件状态表
 *
 * @author ligt
 * @date 2022-01-18 15:21:05
 */
@Service
public class ProjectStatusServiceImpl extends ServiceImpl<ProjectStatusMapper, ProjectStatus> implements ProjectStatusService {
	/**
	 * 保存项目状态
	 * @param projectStatus
	 * @return
	 */
	public ProjectStatus saveProjectStatus(ProjectStatus projectStatus){
		LambdaQueryWrapper<ProjectStatus> lambdaQueryWrapper=new LambdaQueryWrapper<>();
		lambdaQueryWrapper.eq(ProjectStatus::getDelFlag,"0")
				.eq(ProjectStatus::getType,projectStatus.getType())
				.eq(ProjectStatus::getSourceId,projectStatus.getSourceId())
				.eq(ProjectStatus::getStatus,projectStatus.getStatus());
		ProjectStatus projectStatusQuery=this.getOne(lambdaQueryWrapper);
		if(projectStatusQuery!=null){
			projectStatus.setStatusId(projectStatusQuery.getStatusId());
		}
		this.saveOrUpdate(projectStatus);
		return projectStatus;
	}
}
