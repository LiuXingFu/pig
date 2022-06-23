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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.entity.PaymentSourceRe;
import com.pig4cloud.pig.casee.service.PaymentSourceReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 回款来源表
 *
 * @author Mjh
 * @date 2022-06-23 17:07:54
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/paymentsourcere" )
@Api(value = "paymentsourcere", tags = "回款来源表管理")
public class PaymentSourceReController {

    private final  PaymentSourceReService paymentSourceReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param paymentSourceRe 回款来源表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_paymentsourcere_get')" )
    public R getPaymentSourceRePage(Page page, PaymentSourceRe paymentSourceRe) {
        return R.ok(paymentSourceReService.page(page, Wrappers.query(paymentSourceRe)));
    }


    /**
     * 通过id查询回款来源表
     * @param paymentSourceReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{paymentSourceReId}" )
    @PreAuthorize("@pms.hasPermission('casee_paymentsourcere_get')" )
    public R getById(@PathVariable("paymentSourceReId" ) Integer paymentSourceReId) {
        return R.ok(paymentSourceReService.getById(paymentSourceReId));
    }

    /**
     * 新增回款来源表
     * @param paymentSourceRe 回款来源表
     * @return R
     */
    @ApiOperation(value = "新增回款来源表", notes = "新增回款来源表")
    @SysLog("新增回款来源表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_paymentsourcere_add')" )
    public R save(@RequestBody PaymentSourceRe paymentSourceRe) {
        return R.ok(paymentSourceReService.save(paymentSourceRe));
    }

    /**
     * 修改回款来源表
     * @param paymentSourceRe 回款来源表
     * @return R
     */
    @ApiOperation(value = "修改回款来源表", notes = "修改回款来源表")
    @SysLog("修改回款来源表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_paymentsourcere_edit')" )
    public R updateById(@RequestBody PaymentSourceRe paymentSourceRe) {
        return R.ok(paymentSourceReService.updateById(paymentSourceRe));
    }

    /**
     * 通过id删除回款来源表
     * @param paymentSourceReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除回款来源表", notes = "通过id删除回款来源表")
    @SysLog("通过id删除回款来源表" )
    @DeleteMapping("/{paymentSourceReId}" )
    @PreAuthorize("@pms.hasPermission('casee_paymentsourcere_del')" )
    public R removeById(@PathVariable Integer paymentSourceReId) {
        return R.ok(paymentSourceReService.removeById(paymentSourceReId));
    }

}
