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
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.admin.api.vo.UserVO;
import com.pig4cloud.pig.casee.dto.ProjectStatusSaveDTO;
import com.pig4cloud.pig.casee.entity.ProjectStatus;
import com.pig4cloud.pig.casee.entity.biz.ProjectStatusBiz;
import com.pig4cloud.pig.casee.entity.biz.detail.ProjectStatusBizDetail;
import com.pig4cloud.pig.casee.mapper.ProjectStatusMapper;
import com.pig4cloud.pig.casee.service.ProjectStatusService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 项目/案件状态表
 *
 * @author ligt
 * @date 2022-01-18 15:21:05
 */
@Service
public class ProjectStatusServiceImpl extends ServiceImpl<ProjectStatusMapper, ProjectStatus> implements ProjectStatusService {
	@Autowired
	private SecurityUtilsService securityUtilsService;
	@Autowired
	private RemoteUserService userService;

	@Override
	public Integer saveStatusRecord(ProjectStatusSaveDTO projectStatusSaveDTO){
		ProjectStatusBiz projectStatusBiz = new ProjectStatusBiz();
		R<UserVO> userVOR = userService.getUserById(securityUtilsService.getCacheUser().getId(), SecurityConstants.FROM);
		projectStatusBiz.setUserName(userVOR.getData().getActualName());
		BeanCopyUtil.copyBean(projectStatusSaveDTO,projectStatusBiz);
		ProjectStatusBizDetail projectStatusBizDetail = new ProjectStatusBizDetail();
		BeanCopyUtil.copyBean(projectStatusSaveDTO,projectStatusBizDetail);
		projectStatusBiz.setProjectStatusBizDetail(projectStatusBizDetail);
		return this.baseMapper.insert(projectStatusBiz);
	}

}
