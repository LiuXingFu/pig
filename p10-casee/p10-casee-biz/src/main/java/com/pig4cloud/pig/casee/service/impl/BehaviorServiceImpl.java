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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.BehaviorSaveDTO;
import com.pig4cloud.pig.casee.dto.SaveBehaviorDTO;
import com.pig4cloud.pig.casee.dto.TargetAddDTO;
import com.pig4cloud.pig.casee.entity.Behavior;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.entity.liquientity.BehaviorLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.BehaviorLiquiDetail;
import com.pig4cloud.pig.casee.mapper.BehaviorMapper;
import com.pig4cloud.pig.casee.service.BehaviorLiquiService;
import com.pig4cloud.pig.casee.service.BehaviorService;
import com.pig4cloud.pig.casee.service.ProjectLiquiService;
import com.pig4cloud.pig.casee.service.TargetService;
import com.pig4cloud.pig.casee.vo.BehaviorOrProjectOrCasee;
import com.pig4cloud.pig.casee.vo.BehaviorOrProjectPageVO;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.KeyValue;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * ?????????
 *
 * @author yuanduo
 * @date 2022-02-14 15:51:27
 */
@Service
public class BehaviorServiceImpl extends ServiceImpl<BehaviorMapper, Behavior> implements BehaviorService {

	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	BehaviorLiquiService behaviorLiquiService;
	@Autowired
	JurisdictionUtilsService jurisdictionUtilsService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private TargetService targetService;

	/**
	 * ????????????id????????????????????????
	 *
	 * @param page
	 * @param subjectId
	 * @return
	 */
	@Override
	public IPage<BehaviorOrProjectPageVO> queryPageBehaviorOrProject(Page page, Integer subjectId) {
		return this.baseMapper.queryPageBehaviorOrProject(page, subjectId, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * ????????????id????????????????????????????????????????????????
	 *
	 * @param behaviorId
	 * @return
	 */
	@Override
	public BehaviorOrProjectOrCasee queryById(Integer behaviorId) {
		return this.baseMapper.queryById(behaviorId);
	}

	@Override
	public Integer saveBehavior(BehaviorSaveDTO behaviorSaveDTO) {
		Integer save = 0;
		Integer behaviorId = 0;
		if (behaviorSaveDTO.getLimitType() == 102) {
			BehaviorLiqui behaviorLiqui = new BehaviorLiqui();
			BeanCopyUtil.copyBean(behaviorSaveDTO, behaviorLiqui);
			BehaviorLiquiDetail behaviorLiquiDetail = new BehaviorLiquiDetail();
			BeanCopyUtil.copyBean(behaviorSaveDTO, behaviorLiquiDetail);
			behaviorLiqui.setBehaviorLiquiDetail(behaviorLiquiDetail);
			behaviorId = behaviorLiquiService.saveBehaviorLiqui(behaviorLiqui);
		} else {
			Behavior behavior = new Behavior();
			BeanCopyUtil.copyBean(behaviorSaveDTO, behavior);
			save = this.baseMapper.insert(behavior);
			behaviorId = behavior.getBehaviorId();
		}
		Project project = projectLiquiService.getById(behaviorSaveDTO.getProjectId());

		//????????????????????????????????????
		TargetAddDTO targetAddDTO = new TargetAddDTO();
		targetAddDTO.setCaseeId(behaviorSaveDTO.getCaseeId());
		targetAddDTO.setOutlesId(project.getOutlesId());
		targetAddDTO.setProjectId(behaviorSaveDTO.getProjectId());
		targetAddDTO.setGoalId(behaviorId);
		targetAddDTO.setGoalType(30001);
		if (behaviorSaveDTO.getType() == 100) {
			targetAddDTO.setProcedureNature(5050);
		} else {
			targetAddDTO.setProcedureNature(5051);
		}
		try {
			targetService.saveTargetAddDTO(targetAddDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return save;
	}

	@Override
	public IPage<BehaviorOrProjectPageVO> queryPageByCaseeId(Page page, Integer caseeId) {
		return this.baseMapper.queryPageByCaseeId(page, caseeId);
	}

	@Override
	public BehaviorLiqui getBehaviorLiqui(Behavior behavior) {
		return this.baseMapper.getBehaviorLiqui(behavior);
	}
}
