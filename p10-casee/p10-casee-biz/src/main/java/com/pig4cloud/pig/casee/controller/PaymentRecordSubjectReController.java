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
import com.pig4cloud.pig.casee.entity.PaymentRecordSubjectRe;
import com.pig4cloud.pig.casee.service.PaymentRecordSubjectReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 回款记录关联主体信息表
 *
 * @author Mjh
 * @date 2022-02-17 17:52:08
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/paymentrecordsubjectre" )
@Api(value = "paymentrecordsubjectre", tags = "回款记录关联主体信息表管理")
public class PaymentRecordSubjectReController {

    private final PaymentRecordSubjectReService paymentRecordSubjectReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param paymentRecordSubjectRe 回款记录关联主体信息表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_paymentrecordsubjectre_get')" )
    public R getPaymentRecordSubjectRePage(Page page, PaymentRecordSubjectRe paymentRecordSubjectRe) {
        return R.ok(paymentRecordSubjectReService.page(page, Wrappers.query(paymentRecordSubjectRe)));
    }


    /**
     * 通过id查询回款记录关联主体信息表
     * @param paymentRecordSubjectId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{paymentRecordSubjectId}" )
    @PreAuthorize("@pms.hasPermission('casee_paymentrecordsubjectre_get')" )
    public R getById(@PathVariable("paymentRecordSubjectId" ) Integer paymentRecordSubjectId) {
        return R.ok(paymentRecordSubjectReService.getById(paymentRecordSubjectId));
    }

    /**
     * 新增回款记录关联主体信息表
     * @param paymentRecordSubjectRe 回款记录关联主体信息表
     * @return R
     */
    @ApiOperation(value = "新增回款记录关联主体信息表", notes = "新增回款记录关联主体信息表")
    @SysLog("新增回款记录关联主体信息表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_paymentrecordsubjectre_add')" )
    public R save(@RequestBody PaymentRecordSubjectRe paymentRecordSubjectRe) {
        return R.ok(paymentRecordSubjectReService.save(paymentRecordSubjectRe));
    }

    /**
     * 修改回款记录关联主体信息表
     * @param paymentRecordSubjectRe 回款记录关联主体信息表
     * @return R
     */
    @ApiOperation(value = "修改回款记录关联主体信息表", notes = "修改回款记录关联主体信息表")
    @SysLog("修改回款记录关联主体信息表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_paymentrecordsubjectre_edit')" )
    public R updateById(@RequestBody PaymentRecordSubjectRe paymentRecordSubjectRe) {
        return R.ok(paymentRecordSubjectReService.updateById(paymentRecordSubjectRe));
    }

    /**
     * 通过id删除回款记录关联主体信息表
     * @param paymentRecordSubjectId id
     * @return R
     */
    @ApiOperation(value = "通过id删除回款记录关联主体信息表", notes = "通过id删除回款记录关联主体信息表")
    @SysLog("通过id删除回款记录关联主体信息表" )
    @DeleteMapping("/{paymentRecordSubjectId}" )
    @PreAuthorize("@pms.hasPermission('casee_paymentrecordsubjectre_del')" )
    public R removeById(@PathVariable Integer paymentRecordSubjectId) {
        return R.ok(paymentRecordSubjectReService.removeById(paymentRecordSubjectId));
    }

}
