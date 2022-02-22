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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.BehaviorSaveDTO;
import com.pig4cloud.pig.casee.entity.Behavior;
import com.pig4cloud.pig.casee.entity.liquientity.BehaviorLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.BehaviorLiquiDetail;
import com.pig4cloud.pig.casee.mapper.BehaviorMapper;
import com.pig4cloud.pig.casee.service.BehaviorLiquiService;
import com.pig4cloud.pig.casee.service.BehaviorService;
import com.pig4cloud.pig.casee.vo.BehaviorOrProjectOrCasee;
import com.pig4cloud.pig.casee.vo.BehaviorOrProjectPageVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 行为表
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

	/**
	 * 根据主体id分页查询行为数据
	 * @param page
	 * @param subjectId
	 * @return
	 */
	@Override
	public IPage<BehaviorOrProjectPageVO> queryPageBehaviorOrProject(Page page, Integer subjectId) {
		return this.baseMapper.queryPageBehaviorOrProject(page, subjectId);
	}

	/**
	 * 根据行为id查询行为信息、项目信息、案件信息
	 * @param behaviorId
	 * @return
	 */
	@Override
	public BehaviorOrProjectOrCasee queryById(Integer behaviorId) {
		return this.baseMapper.queryById(behaviorId);
	}

	@Override
	public Integer saveBehavior(BehaviorSaveDTO behaviorSaveDTO){
		Integer save = 0;
		if(behaviorSaveDTO.getLimitType()==102){
			BehaviorLiqui behaviorLiqui = new BehaviorLiqui();
			BeanCopyUtil.copyBean(behaviorSaveDTO,behaviorLiqui);
			BehaviorLiquiDetail behaviorLiquiDetail = new BehaviorLiquiDetail();
			BeanCopyUtil.copyBean(behaviorSaveDTO,behaviorLiquiDetail);
			behaviorLiqui.setBehaviorLiquiDetail(behaviorLiquiDetail);
			save = behaviorLiquiService.saveBehaviorLiqui(behaviorLiqui);
		}else {
			Behavior behavior = new Behavior();
			save = this.baseMapper.insert(behavior);
		}
		return save;
	}
}
