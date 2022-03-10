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
import com.pig4cloud.pig.casee.entity.ReconciliatioMediationSubjectRe;
import com.pig4cloud.pig.casee.service.ReconciliatioMediationSubjectReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 和解/调解主体关联表
 *
 * @author Mjh
 * @date 2022-03-09 19:48:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/reconciliatiomediationsubjectre" )
@Api(value = "reconciliatiomediationsubjectre", tags = "和解/调解主体关联表管理")
public class ReconciliatioMediationSubjectReController {

    private final ReconciliatioMediationSubjectReService reconciliatioMediationSubjectReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param reconciliatioMediationSubjectRe 和解/调解主体关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getReconciliatioMediationSubjectRePage(Page page, ReconciliatioMediationSubjectRe reconciliatioMediationSubjectRe) {
        return R.ok(reconciliatioMediationSubjectReService.page(page, Wrappers.query(reconciliatioMediationSubjectRe)));
    }


    /**
     * 通过id查询和解/调解主体关联表
     * @param reconciliatioMediationSubjectId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{reconciliatioMediationSubjectId}" )
    public R getById(@PathVariable("reconciliatioMediationSubjectId" ) Integer reconciliatioMediationSubjectId) {
        return R.ok(reconciliatioMediationSubjectReService.getById(reconciliatioMediationSubjectId));
    }

    /**
     * 新增和解/调解主体关联表
     * @param reconciliatioMediationSubjectRe 和解/调解主体关联表
     * @return R
     */
    @ApiOperation(value = "新增和解/调解主体关联表", notes = "新增和解/调解主体关联表")
    @SysLog("新增和解/调解主体关联表" )
    @PostMapping
    public R save(@RequestBody ReconciliatioMediationSubjectRe reconciliatioMediationSubjectRe) {
        return R.ok(reconciliatioMediationSubjectReService.save(reconciliatioMediationSubjectRe));
    }

    /**
     * 修改和解/调解主体关联表
     * @param reconciliatioMediationSubjectRe 和解/调解主体关联表
     * @return R
     */
    @ApiOperation(value = "修改和解/调解主体关联表", notes = "修改和解/调解主体关联表")
    @SysLog("修改和解/调解主体关联表" )
    @PutMapping
    public R updateById(@RequestBody ReconciliatioMediationSubjectRe reconciliatioMediationSubjectRe) {
        return R.ok(reconciliatioMediationSubjectReService.updateById(reconciliatioMediationSubjectRe));
    }

    /**
     * 通过id删除和解/调解主体关联表
     * @param reconciliatioMediationSubjectId id
     * @return R
     */
    @ApiOperation(value = "通过id删除和解/调解主体关联表", notes = "通过id删除和解/调解主体关联表")
    @SysLog("通过id删除和解/调解主体关联表" )
    @DeleteMapping("/{reconciliatioMediationSubjectId}" )
    public R removeById(@PathVariable Integer reconciliatioMediationSubjectId) {
        return R.ok(reconciliatioMediationSubjectReService.removeById(reconciliatioMediationSubjectId));
    }

}
