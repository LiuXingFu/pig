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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.PaymentRecordDTO;
import com.pig4cloud.pig.casee.dto.PaymentRecordPageDTO;
import com.pig4cloud.pig.casee.dto.count.CountMoneyBackMonthlyRankDTO;
import com.pig4cloud.pig.casee.dto.paifu.PaymentRecordSaveDTO;
import com.pig4cloud.pig.casee.entity.PaymentRecord;
import com.pig4cloud.pig.casee.service.PaymentRecordService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 回款详细记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:52:51
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/paymentrecord" )
@Api(value = "paymentrecord", tags = "回款详细记录表管理")
public class PaymentRecordController {

    private final PaymentRecordService paymentRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param paymentRecordPageDTO 回款详细记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPaymentRecordPage(Page page, PaymentRecordPageDTO paymentRecordPageDTO) {
        return R.ok(paymentRecordService.getPaymentRecordPage(page, paymentRecordPageDTO));
    }

	/**
	 * 通过项目id查询未分配记录
	 * @param paymentRecordPageDTO
	 * @return R
	 */
	@ApiOperation(value = "通过id查询回款详细记录分页记录", notes = "通过id查询回款详细记录分页记录")
	@GetMapping("/getByUnassignedPaymentRecordPage" )
	public R getByUnassignedPaymentRecordPage(Page page,PaymentRecordPageDTO paymentRecordPageDTO) {
		return R.ok(paymentRecordService.getByUnassignedPaymentRecordPage(page, paymentRecordPageDTO));
	}


    /**
     * 通过id查询回款详细记录表
     * @param paymentRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{paymentRecordId}" )
    public R getById(@PathVariable("paymentRecordId" ) Integer paymentRecordId) {
        return R.ok(paymentRecordService.getById(paymentRecordId));
    }

	/**
	 * 通过id查询回款详细记录分页记录
	 * @param paymentRecordId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询回款详细记录分页记录", notes = "通过id查询回款详细记录分页记录")
	@GetMapping("/getByPaymentRecordPage" )
	public R getByPaymentRecordPage(Page page, Integer paymentRecordId) {
		return R.ok(paymentRecordService.page(page,new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getFatherId, paymentRecordId)));
	}

	/**
	 * 通过项目id查询回款详细记录表
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "通过项目id查询回款详细记录表", notes = "通过项目id查询回款详细记录表")
	@GetMapping("/getProjectIdByPaymentRecord" )
	public R getProjectIdByPaymentRecord(Page page, String projectId) {
		return R.ok(paymentRecordService.page(page,new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getProjectId, projectId).isNotNull(PaymentRecord::getFatherId)));
	}

	/**
	 * 查询法院到款记录以及到款总额
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "查询法院到款记录以及到款总额", notes = "查询法院到款记录以及到款总额")
	@GetMapping("/getCourtPaymentPage" )
	public R getCourtPaymentPage(Page page,String projectId) {
		return R.ok(paymentRecordService.getCourtPaymentPage(page, projectId));
	}

	/**
	 * 根据项目id查询法院到款未领款信息
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "根据项目id查询法院到款未领款信息", notes = "根据项目id查询法院到款未领款信息")
	@GetMapping("/getCourtPaymentUnpaid/{projectId}" )
	public R getCourtPaymentUnpaid(@PathVariable("projectId" )Integer projectId) {
		return R.ok(paymentRecordService.getCourtPaymentUnpaid(projectId));
	}


	/**
	 * 领款
	 * @param paymentRecordDTO 领款
	 * @return R
	 */
	@ApiOperation(value = "领款", notes = "领款")
	@SysLog("领款" )
	@PostMapping("/collection")
	public R collection(@RequestBody PaymentRecordDTO paymentRecordDTO) {
		return R.ok(paymentRecordService.collection(paymentRecordDTO));
	}

    /**
     * 新增回款详细记录表
     * @param paymentRecord 回款详细记录表
     * @return R
     */
    @ApiOperation(value = "新增回款详细记录表", notes = "新增回款详细记录表")
    @SysLog("新增回款详细记录表" )
    @PostMapping
    public R save(@RequestBody PaymentRecord paymentRecord) {
        return R.ok(paymentRecordService.save(paymentRecord));
    }

	/**
	 * 新增回款详细记录及关联主体信息
	 * @param paymentRecordDTO
	 * @return R
	 */
	@ApiOperation(value = "新增回款详细记录及关联主体信息", notes = "新增回款详细记录及关联主体信息")
	@SysLog("新增回款详细记录及关联主体信息" )
	@PostMapping("/savePaymentRecord")
	public R savePaymentRecord(@RequestBody PaymentRecordDTO paymentRecordDTO) {
		return R.ok(paymentRecordService.savePaymentRecord(paymentRecordDTO));
	}

    /**
     * 修改回款详细记录表
     * @param paymentRecord 回款详细记录表
     * @return R
     */
    @ApiOperation(value = "修改回款详细记录表", notes = "修改回款详细记录表")
    @SysLog("修改回款详细记录表" )
    @PutMapping
    public R updateById(@RequestBody PaymentRecord paymentRecord) {
        return R.ok(paymentRecordService.updateById(paymentRecord));
    }

    /**
     * 通过id删除回款详细记录表
     * @param paymentRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id删除回款详细记录表", notes = "通过id删除回款详细记录表")
    @SysLog("通过id删除回款详细记录表" )
    @DeleteMapping("/{paymentRecordId}" )
    public R removeById(@PathVariable Integer paymentRecordId) {
        return R.ok(paymentRecordService.removeById(paymentRecordId));
    }

	/**
	 * 查询回款额排名
	 * @param page
	 * @param countMoneyBackMonthlyRankDTO
	 * @return
	 */
	@ApiOperation(value = "根据项目id查询法院到款未领款信息", notes = "根据项目id查询法院到款未领款信息")
	@GetMapping("/queryMoneyBackMonthlyRankList")
    public R queryMoneyBackMonthlyRankList(Page page,  CountMoneyBackMonthlyRankDTO countMoneyBackMonthlyRankDTO) {
    	return R.ok(paymentRecordService.queryMoneyBackMonthlyRankList(page, countMoneyBackMonthlyRankDTO));
	}

	/**
	 * 根据项目id查询拍辅回款详情
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "根据项目id查询拍辅回款详情", notes = "根据项目id查询拍辅回款详情")
	@GetMapping("/queryPaifuPaymentRecordList/{projectId}" )
	public R queryPaifuPaymentRecordList(@PathVariable Integer projectId) {
		return R.ok(paymentRecordService.queryPaifuPaymentRecordList(projectId));
	}

	/**
	 * 保存拍辅回款记录
	 * @param paymentRecordSaveDTO
	 * @return R
	 */
	@ApiOperation(value = "保存拍辅回款记录", notes = "保存拍辅回款记录")
	@SysLog("保存拍辅回款记录" )
	@PostMapping("/savePaifuPaymentRecord")
	public R savePaifuPaymentRecord(@RequestBody PaymentRecordSaveDTO paymentRecordSaveDTO) {
		return R.ok(paymentRecordService.savePaifuPaymentRecord(paymentRecordSaveDTO));
	}

}
