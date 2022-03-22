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
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.ProjectSubjectReModifyDTO;
import com.pig4cloud.pig.casee.entity.ProjectSubjectRe;
import com.pig4cloud.pig.casee.mapper.ProjectSubjectReMapper;
import com.pig4cloud.pig.casee.service.ProjectSubjectReService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 项目主体关联表
 *
 * @author ligt
 * @date 2022-01-11 14:52:12
 */
@Service
public class ProjectSubjectReServiceImpl extends ServiceImpl<ProjectSubjectReMapper, ProjectSubjectRe> implements ProjectSubjectReService {

	@Autowired
	private RemoteSubjectService remoteSubjectService;

	@Override
	@Transactional
	public Integer modifySubjectBySubjectReId(ProjectSubjectReModifyDTO projectSubjectReModifyDTO){
		ProjectSubjectRe projectSubjectRe = new ProjectSubjectRe();
		projectSubjectRe.setSubjectReId(projectSubjectReModifyDTO.getSubjectReId());
		projectSubjectRe.setType(projectSubjectReModifyDTO.getType());

		Subject subject = new Subject();
		BeanCopyUtil.copyBean(projectSubjectReModifyDTO,subject);
		remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM);

		return this.baseMapper.updateById(projectSubjectRe);
	}
}
