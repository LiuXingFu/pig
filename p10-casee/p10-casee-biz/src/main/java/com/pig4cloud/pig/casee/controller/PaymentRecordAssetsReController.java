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
import com.pig4cloud.pig.casee.entity.PaymentRecordAssetsRe;
import com.pig4cloud.pig.casee.service.PaymentRecordAssetsReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 回款记录财产关联表
 *
 * @author pig code generator
 * @date 2022-05-05 21:23:53
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/paymentrecordassetsre" )
@Api(value = "paymentrecordassetsre", tags = "回款记录财产关联表管理")
public class PaymentRecordAssetsReController {

    private final  PaymentRecordAssetsReService paymentRecordAssetsReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param paymentRecordAssetsRe 回款记录财产关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_paymentrecordassetsre_get')" )
    public R getPaymentRecordAssetsRePage(Page page, PaymentRecordAssetsRe paymentRecordAssetsRe) {
        return R.ok(paymentRecordAssetsReService.page(page, Wrappers.query(paymentRecordAssetsRe)));
    }


    /**
     * 通过id查询回款记录财产关联表
     * @param paymentRecordAssetsReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{paymentRecordAssetsReId}" )
    @PreAuthorize("@pms.hasPermission('casee_paymentrecordassetsre_get')" )
    public R getById(@PathVariable("paymentRecordAssetsReId" ) Integer paymentRecordAssetsReId) {
        return R.ok(paymentRecordAssetsReService.getById(paymentRecordAssetsReId));
    }

    /**
     * 新增回款记录财产关联表
     * @param paymentRecordAssetsRe 回款记录财产关联表
     * @return R
     */
    @ApiOperation(value = "新增回款记录财产关联表", notes = "新增回款记录财产关联表")
    @SysLog("新增回款记录财产关联表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_paymentrecordassetsre_add')" )
    public R save(@RequestBody PaymentRecordAssetsRe paymentRecordAssetsRe) {
        return R.ok(paymentRecordAssetsReService.save(paymentRecordAssetsRe));
    }

    /**
     * 修改回款记录财产关联表
     * @param paymentRecordAssetsRe 回款记录财产关联表
     * @return R
     */
    @ApiOperation(value = "修改回款记录财产关联表", notes = "修改回款记录财产关联表")
    @SysLog("修改回款记录财产关联表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_paymentrecordassetsre_edit')" )
    public R updateById(@RequestBody PaymentRecordAssetsRe paymentRecordAssetsRe) {
        return R.ok(paymentRecordAssetsReService.updateById(paymentRecordAssetsRe));
    }

    /**
     * 通过id删除回款记录财产关联表
     * @param paymentRecordAssetsReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除回款记录财产关联表", notes = "通过id删除回款记录财产关联表")
    @SysLog("通过id删除回款记录财产关联表" )
    @DeleteMapping("/{paymentRecordAssetsReId}" )
    @PreAuthorize("@pms.hasPermission('casee_paymentrecordassetsre_del')" )
    public R removeById(@PathVariable Integer paymentRecordAssetsReId) {
        return R.ok(paymentRecordAssetsReService.removeById(paymentRecordAssetsReId));
    }

}
