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
import com.pig4cloud.pig.admin.api.dto.*;
import com.pig4cloud.pig.admin.api.entity.InsOutlesCourtRe;
import com.pig4cloud.pig.admin.api.entity.RelationshipAuthenticate;
import com.pig4cloud.pig.admin.api.vo.InsOutlesCourtReVO;
import com.pig4cloud.pig.admin.api.vo.OrganizationQueryVO;
import com.pig4cloud.pig.admin.mapper.InsOutlesCourtReMapper;
import com.pig4cloud.pig.admin.service.InsOutlesCourtReService;
import com.pig4cloud.pig.admin.service.RelationshipAuthenticateService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 机构网点法院关联表
 *
 * @author yuanduo
 * @date 2022-04-28 19:56:42
 */
@Service
public class InsOutlesCourtReServiceImpl extends ServiceImpl<InsOutlesCourtReMapper, InsOutlesCourtRe> implements InsOutlesCourtReService {

	@Autowired
	RelationshipAuthenticateService relationshipAuthenticateService;

	@Autowired
	private SecurityUtilsService securityUtilsService;

	@Override
	public IPage<InsOutlesCourtReVO> queryInsOutlesCourtPage(Page page, InsOutlesCourtRePageDTO insOutlesCourtRePageDTO) {
		RelationshipAuthenticate relationshipAuthenticate = relationshipAuthenticateService.getOne(new LambdaQueryWrapper<RelationshipAuthenticate>()
				.eq(RelationshipAuthenticate::getAuthenticateId, insOutlesCourtRePageDTO.getCourtInsId()));

		insOutlesCourtRePageDTO.setCourtId(relationshipAuthenticate.getAuthenticateGoalId());

		return this.baseMapper.queryInsOutlesCourtPage(page, insOutlesCourtRePageDTO);
	}

	@Override
	public List<InsOutlesCourtReVO> queryInsOutlesCourtReByInsIdAndCourtId(Integer insId, Integer courtId) {
		return this.baseMapper.queryInsOutlesCourtReByInsIdAndCourtId(insId, courtId);
	}

	/**
	 * 将入驻网点id集合入驻到法院机构绑定的法院
	 * @param insOutlesCourtReAddOutlesIdsDTO
	 * @return
	 */
	@Override
	@Transactional
	public int addInsOutlesCourtReByOutlesIds(InsOutlesCourtReAddOutlesIdsDTO insOutlesCourtReAddOutlesIdsDTO) {

		int add = 0;

		RelationshipAuthenticate relationshipAuthenticate = relationshipAuthenticateService.getOne(new LambdaQueryWrapper<RelationshipAuthenticate>()
				.eq(RelationshipAuthenticate::getAuthenticateId, insOutlesCourtReAddOutlesIdsDTO.getCourtInsId()));

		List<InsOutlesCourtRe> list = new ArrayList<>();

		for (int i = 0; i < insOutlesCourtReAddOutlesIdsDTO.getOutlesIds().size(); i++) {
			Integer outlesId = insOutlesCourtReAddOutlesIdsDTO.getOutlesIds().get(i);
			InsOutlesCourtRe insOutlesCourtRe = new InsOutlesCourtRe();
			insOutlesCourtRe.setInsId(insOutlesCourtReAddOutlesIdsDTO.getInsId());
			insOutlesCourtRe.setOutlesId(outlesId);
			insOutlesCourtRe.setCourtId(relationshipAuthenticate.getAuthenticateGoalId());
			insOutlesCourtRe.setCourtInsId(insOutlesCourtReAddOutlesIdsDTO.getCourtInsId());
			list.add(insOutlesCourtRe);
		}

		this.saveBatch(list);

		return add += 1;
	}

	@Override
	public List<OrganizationQueryVO> getCourtList(InsOutlesCourtReSelectDTO insOutlesCourtReSelectDTO){
		return this.baseMapper.selectCourtList(insOutlesCourtReSelectDTO);
	}


	/**
	 * 将入驻法院id集合入驻到绑定的相关网点
	 * @param insOutlesCourtReAddCourtInsIdsDTO
	 * @return
	 */
	@Override
	public int addInsOutlesCourtReByCourtInsIds(InsOutlesCourtReAddCourtInsIdsDTO insOutlesCourtReAddCourtInsIdsDTO) {

		int add = 0;

		List<InsOutlesCourtRe> list = new ArrayList<>();

		for (int i = 0; i < insOutlesCourtReAddCourtInsIdsDTO.getCourtInsIds().size(); i++) {
			Integer courtInsId = insOutlesCourtReAddCourtInsIdsDTO.getCourtInsIds().get(i);
			RelationshipAuthenticate relationshipAuthenticate = relationshipAuthenticateService.getOne(new LambdaQueryWrapper<RelationshipAuthenticate>()
					.eq(RelationshipAuthenticate::getAuthenticateId, courtInsId));

			InsOutlesCourtRe insOutlesCourtRe = new InsOutlesCourtRe();
			insOutlesCourtRe.setInsId(insOutlesCourtReAddCourtInsIdsDTO.getInsId());
			insOutlesCourtRe.setOutlesId(insOutlesCourtReAddCourtInsIdsDTO.getOutlesId());
			insOutlesCourtRe.setCourtId(relationshipAuthenticate.getAuthenticateGoalId());
			insOutlesCourtRe.setCourtInsId(courtInsId);
			list.add(insOutlesCourtRe);
		}

		this.saveBatch(list);

		return add += 1;
	}

	/**
	 * 根据特定条件查询机构网点法院关联数据
	 * @param insOutlesCourtReQueryDTO
	 * @return
	 */
	@Override
	public List<InsOutlesCourtReVO> queryByInsOutlesCourtReQueryDTO(InsOutlesCourtReQueryDTO insOutlesCourtReQueryDTO) {
		return this.baseMapper.queryByInsOutlesCourtReQueryDTO(insOutlesCourtReQueryDTO);
	}
}
