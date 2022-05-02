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
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.vo.AssociateOutlesRePageVO;
import com.pig4cloud.pig.admin.mapper.AssociateOutlesReMapper;
import com.pig4cloud.pig.admin.service.*;
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

	@Autowired
	RelationshipAuthenticateService relationshipAuthenticateService;

	@Autowired
	InsOutlesCourtReService insOutlesCourtReService;

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
	 * @return
	 * @patam outlesName
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
		//判断是否勾选网点如果 勾选继续执行
		if (Objects.nonNull(associateOutlesDTO.getOutlesIds()) && associateOutlesDTO.getOutlesIds().size() > 0) {
			List<AssociateOutlesRe> list = new ArrayList<>();
			List<InsOutlesCourtRe> insOutlesCourtReList = new ArrayList<>();

			//循环创建网点授权与机构网点法院关联实体并将他们存入集合中
			for (Integer outlesId : associateOutlesDTO.getOutlesIds()) {
				AssociateOutlesRe associateOutlesRe = new AssociateOutlesRe();
				associateOutlesRe.setInsAssociateId(associateOutlesDTO.getInsAssociateId());
				associateOutlesRe.setInsId(securityUtilsService.getCacheUser().getInsId());
				associateOutlesRe.setOutlesId(outlesId);
				associateOutlesRe.setAuthorizationTime(LocalDateTime.now());
				list.add(associateOutlesRe);

				//判断如果合作机构为拍辅机构 则继续执行 创建机构网点法院关联实体
				if (associateOutlesDTO.getInsType().equals(1100)) {
					//查询机构法院关联表
					RelationshipAuthenticate relationshipAuthenticate = relationshipAuthenticateService.getOne(new LambdaQueryWrapper<RelationshipAuthenticate>()
							.eq(RelationshipAuthenticate::getAuthenticateId, securityUtilsService.getCacheUser().getInsId()));
					//查询机构网点法院关联表
					InsOutlesCourtRe insOutlesCourtRe = this.insOutlesCourtReService.getOne(new LambdaQueryWrapper<InsOutlesCourtRe>()
							.eq(InsOutlesCourtRe::getCourtId, relationshipAuthenticate.getAuthenticateGoalId())
							.eq(InsOutlesCourtRe::getOutlesId, outlesId));
					//判断机构网点法院关联是否存在？不存在，将机构网点法院关联存入集合中
					if (Objects.isNull(insOutlesCourtRe)) {
						insOutlesCourtRe = new InsOutlesCourtRe();

						insOutlesCourtRe.setCourtId(relationshipAuthenticate.getAuthenticateGoalId());
						insOutlesCourtRe.setInsId(associateOutlesDTO.getInsAssociateId());
						insOutlesCourtRe.setOutlesId(outlesId);

						insOutlesCourtReList.add(insOutlesCourtRe);
					}
				}

			}

			//机构网点法院关联集合数据大于0，保存数据
			if (insOutlesCourtReList.size() > 0) {
				this.insOutlesCourtReService.saveBatch(insOutlesCourtReList);
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

	@Override
	public IPage<AssociateOutlesRePageVO> queryCooperateOutlesPage(Page page, AssociateOutlesRe associateOutlesRe) {
		associateOutlesRe.setInsId(securityUtilsService.getCacheUser().getInsId());
		return this.baseMapper.queryCooperateOutlesPage(page, associateOutlesRe);
	}


}
