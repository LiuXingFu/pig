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
import com.pig4cloud.pig.casee.entity.SamplePreparationWorkAssetsRe;
import com.pig4cloud.pig.casee.service.SamplePreparationWorkAssetsReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 看样准备工作关联财产表表
 *
 * @author Mjh
 * @date 2022-05-05 10:24:34
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/samplepreparationworkassetsre" )
@Api(value = "samplepreparationworkassetsre", tags = "看样准备工作关联财产表表管理")
public class SamplePreparationWorkAssetsReController {

    private final  SamplePreparationWorkAssetsReService samplePreparationWorkAssetsReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param samplePreparationWorkAssetsRe 看样准备工作关联财产表表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationworkassetsre_get')" )
    public R getSamplePreparationWorkAssetsRePage(Page page, SamplePreparationWorkAssetsRe samplePreparationWorkAssetsRe) {
        return R.ok(samplePreparationWorkAssetsReService.page(page, Wrappers.query(samplePreparationWorkAssetsRe)));
    }


    /**
     * 通过id查询看样准备工作关联财产表表
     * @param samplePreparationWorkAssetsReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{samplePreparationWorkAssetsReId}" )
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationworkassetsre_get')" )
    public R getById(@PathVariable("samplePreparationWorkAssetsReId" ) Integer samplePreparationWorkAssetsReId) {
        return R.ok(samplePreparationWorkAssetsReService.getById(samplePreparationWorkAssetsReId));
    }

    /**
     * 新增看样准备工作关联财产表表
     * @param samplePreparationWorkAssetsRe 看样准备工作关联财产表表
     * @return R
     */
    @ApiOperation(value = "新增看样准备工作关联财产表表", notes = "新增看样准备工作关联财产表表")
    @SysLog("新增看样准备工作关联财产表表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationworkassetsre_add')" )
    public R save(@RequestBody SamplePreparationWorkAssetsRe samplePreparationWorkAssetsRe) {
        return R.ok(samplePreparationWorkAssetsReService.save(samplePreparationWorkAssetsRe));
    }

    /**
     * 修改看样准备工作关联财产表表
     * @param samplePreparationWorkAssetsRe 看样准备工作关联财产表表
     * @return R
     */
    @ApiOperation(value = "修改看样准备工作关联财产表表", notes = "修改看样准备工作关联财产表表")
    @SysLog("修改看样准备工作关联财产表表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationworkassetsre_edit')" )
    public R updateById(@RequestBody SamplePreparationWorkAssetsRe samplePreparationWorkAssetsRe) {
        return R.ok(samplePreparationWorkAssetsReService.updateById(samplePreparationWorkAssetsRe));
    }

    /**
     * 通过id删除看样准备工作关联财产表表
     * @param samplePreparationWorkAssetsReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除看样准备工作关联财产表表", notes = "通过id删除看样准备工作关联财产表表")
    @SysLog("通过id删除看样准备工作关联财产表表" )
    @DeleteMapping("/{samplePreparationWorkAssetsReId}" )
    @PreAuthorize("@pms.hasPermission('casee_samplepreparationworkassetsre_del')" )
    public R removeById(@PathVariable Integer samplePreparationWorkAssetsReId) {
        return R.ok(samplePreparationWorkAssetsReService.removeById(samplePreparationWorkAssetsReId));
    }

}
