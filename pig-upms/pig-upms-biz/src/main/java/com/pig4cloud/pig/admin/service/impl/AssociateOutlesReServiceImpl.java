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
	 * @param page
	 * @param associateOutlesRe
	 * @return
	 */
	@Override
	public IPage<AssociateOutlesRe> pageAssociateOutles(Page page, AssociateOutlesRe associateOutlesRe) {
		return this.baseMapper.pageAssociateOutles(page, associateOutlesRe);
	}

	/**
	 * 根据associateID查询授权列表
	 * @param page
	 * @param associateId
	 * @return
	 */
	@Override
	public IPage<Outles> getAuthorizationPage(Page page, Integer associateId) throws Exception {
		// 1.根据关联机构id查询关联机构信息
		InstitutionAssociate institutionAssociate = institutionAssociateService.getOne(new LambdaQueryWrapper<InstitutionAssociate>().eq(InstitutionAssociate::getAssociateId, associateId));
		// 2.判断关联信息是否为空
		if(Objects.nonNull(institutionAssociate)){
			// 2.1根据网点id与关联id查询
			List<Integer> outlesIds = this.list(new LambdaQueryWrapper<AssociateOutlesRe>().eq(AssociateOutlesRe::getAssociateId, associateId)).stream().map(AssociateOutlesRe::getOutlesId)
					.collect(Collectors.toList());
			LambdaQueryWrapper<Outles> query = new LambdaQueryWrapper<Outles>().eq(Outles::getInsId, institutionAssociate.getInsId());
			if(Objects.nonNull(outlesIds)){
				if(outlesIds.size() > 0){
					query.notIn(Outles::getOutlesId, outlesIds);
				}
			}
			IPage<Outles> pageList = outlesService.page(page, query);
			return pageList;
		} else {
			throw new Exception("合作机构不存在！");
		}
	}

	/**
	 * 新增机构授权网点
	 * @param associateOutlesDTO 机构关联网点表
	 * @return R
	 */
	@Override
	@Transactional
	public boolean saveAssociateOutles(AssociateOutlesReDTO associateOutlesDTO) throws Exception {
		if(Objects.nonNull(associateOutlesDTO)) {
			List<AssociateOutlesRe> list = new ArrayList<>();
			for (Integer outlesId : associateOutlesDTO.getOutless()) {
				AssociateOutlesRe associateOutlesRe = new AssociateOutlesRe();
				BeanUtils.copyProperties(associateOutlesDTO, associateOutlesRe);
				associateOutlesRe.setInsId(securityUtilsService.getCacheUser().getInsId());
				associateOutlesRe.setOutlesId(outlesId);
				associateOutlesRe.setAuthorizationTime(LocalDateTime.now());
				list.add(associateOutlesRe);
			}
			return this.saveBatch(list);
		} else {
			throw new Exception("参数异常！");
		}
	}

	/**
	 * 解除网点授权关系
	 * @param associateOutlesRe
	 * @return R
	 */
	@Override
	@Transactional
	public boolean dismissById(AssociateOutlesRe associateOutlesRe) {
		return this.remove(new LambdaQueryWrapper<AssociateOutlesRe>()
				.eq(AssociateOutlesRe::getAssociateId, associateOutlesRe.getAssociateId())
				.eq(AssociateOutlesRe::getOutlesId, associateOutlesRe.getOutlesId()));
	}
}
