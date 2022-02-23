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
import com.pig4cloud.pig.admin.api.dto.OutlesTemplateReDTO;
import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.api.entity.OutlesTemplateRe;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.admin.api.entity.UserOutlesStaffRe;
import com.pig4cloud.pig.admin.mapper.OutlesTemplateReMapper;
import com.pig4cloud.pig.admin.service.OutlesService;
import com.pig4cloud.pig.admin.service.OutlesTemplateReService;
import com.pig4cloud.pig.admin.service.UserOutlesStaffReService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 网点任务模板关联表
 *
 * @author Mjh
 * @date 2021-12-17 11:31:08
 */
@Service
public class OutlesTemplateReServiceImpl extends ServiceImpl<OutlesTemplateReMapper, OutlesTemplateRe> implements OutlesTemplateReService {
	@Autowired
	private OutlesService outlesService;

	@Autowired
	private UserOutlesStaffReService userOutlesStaffReService;

	@Override
	@Transactional
	public boolean configureOutlesTemplate(OutlesTemplateReDTO outlesTemplateReDTO) {

//		// 数据库数据全部清空后需要修改
//
//		//修改用户网点表权限，将新的网点负责人权限改为管理，旧网点负责人改为普通
//
//		//1.根据网点加管理权限查询网点负责人集合
//		List<UserOutlesStaffRe> list = userOutlesStaffReService.list(new LambdaQueryWrapper<UserOutlesStaffRe>()
//				.eq(UserOutlesStaffRe::getOutlesId, outlesTemplateReDTO.getOutlesId()).eq(UserOutlesStaffRe::getRoleType, 1));
//
//		//2.将旧网点负责人权限修改为普通
//
//		for (UserOutlesStaffRe userOutlesStaffRe : list) {
//			this.baseMapper.updateByOutlesOrRoleType(userOutlesStaffRe, null, 0);
//		}
//
//		//3.将新网点负责人权限修改为管理
//		UserOutlesStaffRe userOutlesStaffRe = userOutlesStaffReService.getOne(new LambdaQueryWrapper<UserOutlesStaffRe>()
//				.eq(UserOutlesStaffRe::getOutlesId, outlesTemplateReDTO.getOutlesId()).eq(UserOutlesStaffRe::getUserId, outlesTemplateReDTO.getUserId()));
//
//		this.baseMapper.updateByOutlesOrRoleType(userOutlesStaffRe, outlesTemplateReDTO.getUserId(),1);

		//修改网点简称
		Outles outles=new Outles();
		outles.setOutlesId(outlesTemplateReDTO.getOutlesId());
		outles.setAlias(outlesTemplateReDTO.getAlias());
//		outles.setUserId(outlesTemplateReDTO.getUserId());
		outlesService.updateById(outles);

		//清除当前网点模板关联信息
		this.remove(new LambdaQueryWrapper<OutlesTemplateRe>().eq(OutlesTemplateRe::getOutlesId,outlesTemplateReDTO.getOutlesId()));

		List<OutlesTemplateRe> outlesTemplateReList = outlesTemplateReDTO.getTemplateIds().stream().map(templateId -> {
			OutlesTemplateRe outlesTemplateRe = new OutlesTemplateRe();
			outlesTemplateRe.setTemplateId(templateId);
			outlesTemplateRe.setOutlesId(outlesTemplateReDTO.getOutlesId());
			return outlesTemplateRe;
		}).collect(Collectors.toList());
		//添加提交上来的网点模板关联信息
		return this.saveBatch(outlesTemplateReList);
	}

	@Override
	public List<OutlesTemplateRe> getByOutlesId(Integer outlesId) {
		return this.baseMapper.getByOutlesId(outlesId);
	}

	@Override
	public TaskNodeTemplate queryTemplateByTemplateNature(Integer templateNature,Integer outlesId) {
		return this.baseMapper.queryTemplateByTemplateNature(templateNature,outlesId);
	}
}
