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
import com.pig4cloud.pig.casee.entity.ReceiveConsultation;
import com.pig4cloud.pig.casee.service.ReceiveConsultationService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 接受咨询名单表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/receiveconsultation" )
@Api(value = "receiveconsultation", tags = "接受咨询名单表管理")
public class ReceiveConsultationController {

    private final  ReceiveConsultationService receiveConsultationService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param receiveConsultation 接受咨询名单表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_receiveconsultation_get')" )
    public R getReceiveConsultationPage(Page page, ReceiveConsultation receiveConsultation) {
        return R.ok(receiveConsultationService.page(page, Wrappers.query(receiveConsultation)));
    }


    /**
     * 通过id查询接受咨询名单表
     * @param receiveConsultationId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{receiveConsultationId}" )
    @PreAuthorize("@pms.hasPermission('casee_receiveconsultation_get')" )
    public R getById(@PathVariable("receiveConsultationId" ) Integer receiveConsultationId) {
        return R.ok(receiveConsultationService.getById(receiveConsultationId));
    }

    /**
     * 新增接受咨询名单表
     * @param receiveConsultation 接受咨询名单表
     * @return R
     */
    @ApiOperation(value = "新增接受咨询名单表", notes = "新增接受咨询名单表")
    @SysLog("新增接受咨询名单表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_receiveconsultation_add')" )
    public R save(@RequestBody ReceiveConsultation receiveConsultation) {
        return R.ok(receiveConsultationService.save(receiveConsultation));
    }

    /**
     * 修改接受咨询名单表
     * @param receiveConsultation 接受咨询名单表
     * @return R
     */
    @ApiOperation(value = "修改接受咨询名单表", notes = "修改接受咨询名单表")
    @SysLog("修改接受咨询名单表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_receiveconsultation_edit')" )
    public R updateById(@RequestBody ReceiveConsultation receiveConsultation) {
        return R.ok(receiveConsultationService.updateById(receiveConsultation));
    }

    /**
     * 通过id删除接受咨询名单表
     * @param receiveConsultationId id
     * @return R
     */
    @ApiOperation(value = "通过id删除接受咨询名单表", notes = "通过id删除接受咨询名单表")
    @SysLog("通过id删除接受咨询名单表" )
    @DeleteMapping("/{receiveConsultationId}" )
    @PreAuthorize("@pms.hasPermission('casee_receiveconsultation_del')" )
    public R removeById(@PathVariable Integer receiveConsultationId) {
        return R.ok(receiveConsultationService.removeById(receiveConsultationId));
    }

}
