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

package com.pig4cloud.pig.casee.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.BehaviorLiquiDebtorPageDTO;
import com.pig4cloud.pig.casee.service.BehaviorLiquiService;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 行为表
 *
 * @author yuanduo
 * @date 2022-02-14 15:51:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/behaviorLiqui" )
@Api(value = "behaviorLiqui", tags = "行为清收管理")
public class BehaviorLiquiController {

    private final BehaviorLiquiService behaviorLiquiService;

	/**
	 * 制裁申请未实施
	 *
	 * @param behaviorLiquiDebtorPageDTO
	 * @return
	 */
	@ApiOperation(value = "制裁申请未实施", notes = "制裁申请未实施")
	@GetMapping("/selectSanctionApplyNotImplemented")
	public R selectSanctionApplyNotImplemented(Page page, BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO) {
		return R.ok(this.behaviorLiquiService.selectSanctionApplyNotImplemented(page,behaviorLiquiDebtorPageDTO));
	}

	/**
	 * 限制未送达
	 *
	 * @param behaviorLiquiDebtorPageDTO
	 * @return
	 */
	@ApiOperation(value = "限制未送达", notes = "限制未送达")
	@GetMapping("/queryLimitNotService")
	public R queryLimitNotService(Page page, BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO) {
		return R.ok(this.behaviorLiquiService.queryLimitNotService(page,behaviorLiquiDebtorPageDTO));
	}

	/**
	 * 已结清限制未撤销
	 *
	 * @param behaviorLiquiDebtorPageDTO
	 * @return
	 */
	@ApiOperation(value = "结清未撤销", notes = "结清未撤销")
	@GetMapping("/behaviorPaymentCompleted")
	public R behaviorPaymentCompleted(Page page, BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO) {
		return R.ok(this.behaviorLiquiService.behaviorPaymentCompleted(page,behaviorLiquiDebtorPageDTO));
	}
	/**
	 * 查询债务人行为列表
	 *
	 * @param subjectId
	 * @return
	 */
	@ApiOperation(value = "查询债务人行为列表", notes = "查询债务人行为列表")
	@GetMapping("/queryBehaviorBySubjecrId")
	public R queryBehaviorBySubjecrId(Page page, Integer subjectId) {
		return R.ok(this.behaviorLiquiService.queryBehaviorBySubjecrId(page,subjectId));
	}

}
