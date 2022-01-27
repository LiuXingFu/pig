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
package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.api.entity.OutlesTemplateRe;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.mapper.TaskNodeTemplateMapper;
import com.pig4cloud.pig.admin.service.InstitutionService;
import com.pig4cloud.pig.admin.service.OutlesTemplateReService;
import com.pig4cloud.pig.admin.service.TaskNodeTemplateService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 流程节点模板表
 *
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
@Service
public class TaskNodeTemplateServiceImpl extends ServiceImpl<TaskNodeTemplateMapper, TaskNodeTemplate> implements TaskNodeTemplateService {
	@Autowired
	private InstitutionService institutionService;
	@Autowired
	private SecurityUtilsService securityUtilsService;
	@Autowired
	private OutlesTemplateReService outlesTemplateReService;
	@Override
	public Boolean removeByTemplateIdOrOutlesTemplateRe(Integer templateId) {
		outlesTemplateReService.remove(new LambdaQueryWrapper<OutlesTemplateRe>().eq(OutlesTemplateRe::getTemplateId,templateId));
		return this.removeById(templateId);
	}

	@Override
	public List<TaskNodeTemplate> queryTemplateType() {
		Institution institution = institutionService.getById(securityUtilsService.getCacheUser().getInsId());
		return this.list(new LambdaQueryWrapper<TaskNodeTemplate>().eq(TaskNodeTemplate::getTemplateType, institution.getInsType()).eq(TaskNodeTemplate::getDelFlag, 0));
	}
}
