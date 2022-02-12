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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.AssociateOutlesReDTO;
import com.pig4cloud.pig.admin.api.entity.AssociateOutlesRe;
import com.pig4cloud.pig.admin.api.entity.InstitutionAssociate;
import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.mapper.AssociateOutlesReMapper;
import com.pig4cloud.pig.admin.service.AssociateOutlesReService;
import com.pig4cloud.pig.admin.service.InstitutionAssociateService;
import com.pig4cloud.pig.admin.service.OutlesService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 机构关联网点表
 *
 * @author yuanduo
 * @date 2021-09-03 11:09:36
 */
@Service
public class AssociateOutlesReServiceImpl extends ServiceImpl<AssociateOutlesReMapper, AssociateOutlesRe> implements AssociateOutlesReService {

	@Autowired
	OutlesService outlesService;

	@Autowired
	InstitutionAssociateService institutionAssociateService;

	@Autowired
	SecurityUtilsService securityUtilsService;

	/**
	 * 分页查询已授权的网点信息
	 *
	 * @param page
	 * @param associateOutlesRe
	 * @return
	 */
	@Override
	public IPage<AssociateOutlesRe> pageAssociateOutles(Page page, AssociateOutlesRe associateOutlesRe) {
		associateOutlesRe.setInsId(securityUtilsService.getCacheUser().getInsId());
		return this.baseMapper.pageAssociateOutles(page, associateOutlesRe);
	}

	/**
	 * 根据associateID查询授权列表
	 *
	 * @param insAssociateId
	 * @patam outlesName
	 * @return
	 */
	@Override
	public List<Outles> getAuthorizationPage(Integer insAssociateId, String outlesName) throws Exception {
		// 2.1根据网点id与关联id查询
		List<Integer> outlesIds = this.list(new LambdaQueryWrapper<AssociateOutlesRe>()
				.eq(AssociateOutlesRe::getInsId, securityUtilsService.getCacheUser().getInsId())
				.eq(AssociateOutlesRe::getInsAssociateId, insAssociateId))
				.stream().map(AssociateOutlesRe::getOutlesId).collect(Collectors.toList());

		List<Outles> outlesList = outlesService.pageOutlesList(securityUtilsService.getCacheUser().getInsId(), outlesName, outlesIds);

		return outlesList;
	}

	/**
	 * 新增机构授权网点
	 *
	 * @param associateOutlesDTO 机构关联网点DTO
	 * @return R
	 */
	@Override
	@Transactional
	public boolean saveAssociateOutles(AssociateOutlesReDTO associateOutlesDTO) throws Exception {
		if (Objects.nonNull(associateOutlesDTO.getOutlesIds()) && associateOutlesDTO.getOutlesIds().size() > 0) {
			List<AssociateOutlesRe> list = new ArrayList<>();
			for (Integer outlesId : associateOutlesDTO.getOutlesIds()) {
				AssociateOutlesRe associateOutlesRe = new AssociateOutlesRe();
				associateOutlesRe.setInsAssociateId(associateOutlesDTO.getInsAssociateId());
				associateOutlesRe.setInsId(securityUtilsService.getCacheUser().getInsId());
				associateOutlesRe.setOutlesId(outlesId);
				associateOutlesRe.setAuthorizationTime(LocalDateTime.now());
				list.add(associateOutlesRe);
			}
			return this.saveBatch(list);
		} else {
			throw new Exception("请选择勾线网点！");
		}
	}

	/**
	 * 解除网点授权关系
	 *
	 * @param associateOutlesId
	 * @return R
	 */
	@Override
	@Transactional
	public boolean dismissById(Integer associateOutlesId) {
		return this.removeById(associateOutlesId);
	}
}
