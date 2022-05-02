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
import com.pig4cloud.pig.casee.entity.SamplePreparationWork;
import com.pig4cloud.pig.casee.service.SamplePreparationWorkService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 看样准备工作表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/samplepreparationwork" )
@Api(value = "samplepreparationwork", tags = "看样准备工作表管理")
public class SamplePreparationWorkController {

    private final  SamplePreparationWorkService samplePreparationWorkService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param samplePreparationWork 看样准备工作表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationwork_get')" )
    public R getSamplePreparationWorkPage(Page page, SamplePreparationWork samplePreparationWork) {
        return R.ok(samplePreparationWorkService.page(page, Wrappers.query(samplePreparationWork)));
    }


    /**
     * 通过id查询看样准备工作表
     * @param samplePreparationWorkId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{samplePreparationWorkId}" )
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationwork_get')" )
    public R getById(@PathVariable("samplePreparationWorkId" ) Integer samplePreparationWorkId) {
        return R.ok(samplePreparationWorkService.getById(samplePreparationWorkId));
    }

    /**
     * 新增看样准备工作表
     * @param samplePreparationWork 看样准备工作表
     * @return R
     */
    @ApiOperation(value = "新增看样准备工作表", notes = "新增看样准备工作表")
    @SysLog("新增看样准备工作表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationwork_add')" )
    public R save(@RequestBody SamplePreparationWork samplePreparationWork) {
        return R.ok(samplePreparationWorkService.save(samplePreparationWork));
    }

    /**
     * 修改看样准备工作表
     * @param samplePreparationWork 看样准备工作表
     * @return R
     */
    @ApiOperation(value = "修改看样准备工作表", notes = "修改看样准备工作表")
    @SysLog("修改看样准备工作表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationwork_edit')" )
    public R updateById(@RequestBody SamplePreparationWork samplePreparationWork) {
        return R.ok(samplePreparationWorkService.updateById(samplePreparationWork));
    }

    /**
     * 通过id删除看样准备工作表
     * @param samplePreparationWorkId id
     * @return R
     */
    @ApiOperation(value = "通过id删除看样准备工作表", notes = "通过id删除看样准备工作表")
    @SysLog("通过id删除看样准备工作表" )
    @DeleteMapping("/{samplePreparationWorkId}" )
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationwork_del')" )
    public R removeById(@PathVariable Integer samplePreparationWorkId) {
        return R.ok(samplePreparationWorkService.removeById(samplePreparationWorkId));
    }

}
