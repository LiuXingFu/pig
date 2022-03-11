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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.ProjectUrgeSaveDTO;
import com.pig4cloud.pig.casee.entity.ProjectUrge;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectUrgeLiqui;
import com.pig4cloud.pig.casee.mapper.ProjectUrgeLiquiMapper;
import com.pig4cloud.pig.casee.service.ProjectUrgeLiquiService;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目督促表
 *
 * @author pig code generator
 * @date 2022-03-10 20:53:32
 */
@Service
public class ProjectUrgeLiquiServiceImpl extends ServiceImpl<ProjectUrgeLiquiMapper, ProjectUrge> implements ProjectUrgeLiquiService {
	@Autowired
	private SecurityUtilsService securityUtilsService;

	@Override
	@Transactional
	public 	Integer saveProjectUrge(ProjectUrgeSaveDTO projectUrgeSaveDTO){
		ProjectUrgeLiqui projectUrgeLiqui = new ProjectUrgeLiqui();
		BeanCopyUtil.copyBean(projectUrgeSaveDTO,projectUrgeLiqui);
		projectUrgeLiqui.setActualName(securityUtilsService.getCacheUser().getUsername());
		return this.baseMapper.insert(projectUrgeLiqui);
	}
}
