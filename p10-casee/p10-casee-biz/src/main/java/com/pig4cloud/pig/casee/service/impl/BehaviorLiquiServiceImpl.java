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
import com.pig4cloud.pig.casee.dto.BehaviorLiquiDebtorPageDTO;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.entity.Behavior;
import com.pig4cloud.pig.casee.entity.liquientity.BehaviorLiqui;
import com.pig4cloud.pig.casee.mapper.BehaviorLiquiMapper;
import com.pig4cloud.pig.casee.service.BehaviorLiquiService;
import com.pig4cloud.pig.casee.vo.CaseeLiquiDebtorPageVO;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * 行为表
 *
 * @author yuanduo
 * @date 2022-02-14 15:51:27
 */
@Service
public class BehaviorLiquiServiceImpl extends ServiceImpl<BehaviorLiquiMapper, Behavior> implements BehaviorLiquiService {

	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Override
	public Integer saveBehaviorLiqui(BehaviorLiqui behaviorLiqui){
		Integer restrictedPeriod = behaviorLiqui.getBehaviorLiquiDetail().getRestrictedPeriod();
		BehaviorLiqui behavior = new BehaviorLiqui();
		if(restrictedPeriod>0){
			LocalDate limitEndTime = behaviorLiqui.getBehaviorDate().plusMonths(restrictedPeriod);
			behaviorLiqui.getBehaviorLiquiDetail().setLimitEndTime(limitEndTime);
		}
		BeanCopyUtil.copyBean(behaviorLiqui,behavior);
		this.baseMapper.insert(behavior);
		return behavior.getBehaviorId();
	}

	@Override
	public IPage<CaseeLiquiDebtorPageVO> queryDebtorPage(Page page, BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectDebtorPage(page,behaviorLiquiDebtorPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<CaseeLiquiDebtorPageVO> behaviorPaymentCompleted(Page page, BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.behaviorPaymentCompleted(page,behaviorLiquiDebtorPageDTO,insOutlesDTO);
	}
}
