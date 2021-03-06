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

package com.pig4cloud.pig.casee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.BehaviorLiquiDebtorPageDTO;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.entity.Behavior;
import com.pig4cloud.pig.casee.entity.liquientity.BehaviorLiqui;
import com.pig4cloud.pig.casee.vo.BehaviorDetailVO;
import com.pig4cloud.pig.casee.vo.BehaviorOrProjectPageVO;
import com.pig4cloud.pig.casee.vo.CaseeLiquiDebtorPageVO;

/**
 * 行为表
 *
 * @author yuanduo
 * @date 2022-02-14 15:51:27
 */
public interface BehaviorLiquiService extends IService<Behavior> {

	Integer saveBehaviorLiqui(BehaviorLiqui behaviorLiqui);

	/**
	 * 制裁申请未实施
	 * @param page
	 * @param behaviorLiquiDebtorPageDTO
	 * @return
	 */
	IPage<CaseeLiquiDebtorPageVO> selectSanctionApplyNotImplemented(Page page, BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO);

	/**
	 * 限制未送达
	 * @param page
	 * @param behaviorLiquiDebtorPageDTO
	 * @return
	 */
	IPage<CaseeLiquiDebtorPageVO> queryLimitNotService(Page page, BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO);

	/**
	 * 已结清限制未撤销
	 * @param page
	 * @param behaviorLiquiDebtorPageDTO
	 * @return
	 */
	IPage<CaseeLiquiDebtorPageVO> behaviorPaymentCompleted(Page page, BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO);

	/**
	 * 查询债务人行为列表
	 * @param page
	 * @param subjectId
	 * @return
	 */
	IPage<BehaviorOrProjectPageVO> queryBehaviorBySubjecrId(Page page, Integer subjectId);

	/**
	 * 查询行为详情
	 * @param behaviorId
	 * @return
	 */
	BehaviorDetailVO queryBehaviorDetail(Integer behaviorId);

}
