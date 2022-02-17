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

package com.pig4cloud.pig.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.service.CaseeLawyerReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.entity.CaseeLawyerRe;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 案件律师关联表
 *
 * @author pig code generator
 * @date 2022-02-15 10:56:32
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/caseelawyerre" )
@Api(value = "caseelawyerre", tags = "案件律师关联表管理")
public class CaseeLawyerReController {

    private final CaseeLawyerReService caseeLawyerReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param caseeLawyerRe 案件律师关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('demo_caseelawyerre_get')" )
    public R getCaseeLawyerRePage(Page page, CaseeLawyerRe caseeLawyerRe) {
        return R.ok(caseeLawyerReService.page(page, Wrappers.query(caseeLawyerRe)));
    }


    /**
     * 通过id查询案件律师关联表
     * @param caseeLawyerId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{caseeLawyerId}" )
    @PreAuthorize("@pms.hasPermission('demo_caseelawyerre_get')" )
    public R getById(@PathVariable("caseeLawyerId" ) Integer caseeLawyerId) {
        return R.ok(caseeLawyerReService.getById(caseeLawyerId));
    }

    /**
     * 新增案件律师关联表
     * @param caseeLawyerRe 案件律师关联表
     * @return R
     */
    @ApiOperation(value = "新增案件律师关联表", notes = "新增案件律师关联表")
    @SysLog("新增案件律师关联表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('demo_caseelawyerre_add')" )
    public R save(@RequestBody CaseeLawyerRe caseeLawyerRe) {
        return R.ok(caseeLawyerReService.save(caseeLawyerRe));
    }

    /**
     * 修改案件律师关联表
     * @param caseeLawyerRe 案件律师关联表
     * @return R
     */
    @ApiOperation(value = "修改案件律师关联表", notes = "修改案件律师关联表")
    @SysLog("修改案件律师关联表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('demo_caseelawyerre_edit')" )
    public R updateById(@RequestBody CaseeLawyerRe caseeLawyerRe) {
        return R.ok(caseeLawyerReService.updateById(caseeLawyerRe));
    }

    /**
     * 通过id删除案件律师关联表
     * @param caseeLawyerId id
     * @return R
     */
    @ApiOperation(value = "通过id删除案件律师关联表", notes = "通过id删除案件律师关联表")
    @SysLog("通过id删除案件律师关联表" )
    @DeleteMapping("/{caseeLawyerId}" )
    @PreAuthorize("@pms.hasPermission('demo_caseelawyerre_del')" )
    public R removeById(@PathVariable Integer caseeLawyerId) {
        return R.ok(caseeLawyerReService.removeById(caseeLawyerId));
    }

}
