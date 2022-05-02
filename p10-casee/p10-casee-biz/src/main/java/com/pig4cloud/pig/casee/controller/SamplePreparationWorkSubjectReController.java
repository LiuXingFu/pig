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
import com.pig4cloud.pig.casee.entity.SamplePreparationWorkSubjectRe;
import com.pig4cloud.pig.casee.service.SamplePreparationWorkSubjectReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 看样人员关联看样准备工作表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/samplepreparationworksubjectre" )
@Api(value = "samplepreparationworksubjectre", tags = "看样人员关联看样准备工作表管理")
public class SamplePreparationWorkSubjectReController {

    private final  SamplePreparationWorkSubjectReService samplePreparationWorkSubjectReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param samplePreparationWorkSubjectRe 看样人员关联看样准备工作表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationworksubjectre_get')" )
    public R getSamplePreparationWorkSubjectRePage(Page page, SamplePreparationWorkSubjectRe samplePreparationWorkSubjectRe) {
        return R.ok(samplePreparationWorkSubjectReService.page(page, Wrappers.query(samplePreparationWorkSubjectRe)));
    }


    /**
     * 通过id查询看样人员关联看样准备工作表
     * @param samplePreparationWorkSubjectReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{samplePreparationWorkSubjectReId}" )
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationworksubjectre_get')" )
    public R getById(@PathVariable("samplePreparationWorkSubjectReId" ) Integer samplePreparationWorkSubjectReId) {
        return R.ok(samplePreparationWorkSubjectReService.getById(samplePreparationWorkSubjectReId));
    }

    /**
     * 新增看样人员关联看样准备工作表
     * @param samplePreparationWorkSubjectRe 看样人员关联看样准备工作表
     * @return R
     */
    @ApiOperation(value = "新增看样人员关联看样准备工作表", notes = "新增看样人员关联看样准备工作表")
    @SysLog("新增看样人员关联看样准备工作表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationworksubjectre_add')" )
    public R save(@RequestBody SamplePreparationWorkSubjectRe samplePreparationWorkSubjectRe) {
        return R.ok(samplePreparationWorkSubjectReService.save(samplePreparationWorkSubjectRe));
    }

    /**
     * 修改看样人员关联看样准备工作表
     * @param samplePreparationWorkSubjectRe 看样人员关联看样准备工作表
     * @return R
     */
    @ApiOperation(value = "修改看样人员关联看样准备工作表", notes = "修改看样人员关联看样准备工作表")
    @SysLog("修改看样人员关联看样准备工作表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationworksubjectre_edit')" )
    public R updateById(@RequestBody SamplePreparationWorkSubjectRe samplePreparationWorkSubjectRe) {
        return R.ok(samplePreparationWorkSubjectReService.updateById(samplePreparationWorkSubjectRe));
    }

    /**
     * 通过id删除看样人员关联看样准备工作表
     * @param samplePreparationWorkSubjectReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除看样人员关联看样准备工作表", notes = "通过id删除看样人员关联看样准备工作表")
    @SysLog("通过id删除看样人员关联看样准备工作表" )
    @DeleteMapping("/{samplePreparationWorkSubjectReId}" )
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationworksubjectre_del')" )
    public R removeById(@PathVariable Integer samplePreparationWorkSubjectReId) {
        return R.ok(samplePreparationWorkSubjectReService.removeById(samplePreparationWorkSubjectReId));
    }

}
