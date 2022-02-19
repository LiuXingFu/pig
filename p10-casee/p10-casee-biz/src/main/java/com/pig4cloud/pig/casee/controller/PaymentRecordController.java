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
import com.pig4cloud.pig.casee.entity.PaymentRecord;
import com.pig4cloud.pig.casee.service.PaymentRecordService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


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
     * @param paymentRecord 回款详细记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getPaymentRecordPage(Page page, PaymentRecord paymentRecord) {
        return R.ok(paymentRecordService.getPaymentRecordPage(page, paymentRecord));
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
	 * 通过id查询回款详细记录表
	 * @param paymentRecordId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getByPaymentRecord/{paymentRecordId}" )
	public R getByPaymentRecord(@PathVariable("paymentRecordId" ) Integer paymentRecordId) {
		return R.ok(paymentRecordService.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getFatherId, paymentRecordId)));
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

}