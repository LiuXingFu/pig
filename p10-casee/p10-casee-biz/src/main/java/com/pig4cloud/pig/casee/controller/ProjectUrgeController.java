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
import com.pig4cloud.pig.casee.entity.ProjectUrge;
import com.pig4cloud.pig.casee.service.ProjectUrgeService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 项目督促表
 *
 * @author pig code generator
 * @date 2022-03-10 20:53:32
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projecturge" )
@Api(value = "projecturge", tags = "项目督促表管理")
public class ProjectUrgeController {

    private final ProjectUrgeService projectUrgeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param projectUrge 项目督促表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_projecturge_get')" )
    public R getProjectUrgePage(Page page, ProjectUrge projectUrge) {
        return R.ok(projectUrgeService.page(page, Wrappers.query(projectUrge)));
    }


    /**
     * 通过id查询项目督促表
     * @param projectUrgeId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{projectUrgeId}" )
    @PreAuthorize("@pms.hasPermission('casee_projecturge_get')" )
    public R getById(@PathVariable("projectUrgeId" ) Integer projectUrgeId) {
        return R.ok(projectUrgeService.getById(projectUrgeId));
    }

    /**
     * 新增项目督促表
     * @param projectUrge 项目督促表
     * @return R
     */
    @ApiOperation(value = "新增项目督促表", notes = "新增项目督促表")
    @SysLog("新增项目督促表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_projecturge_add')" )
    public R save(@RequestBody ProjectUrge projectUrge) {
        return R.ok(projectUrgeService.save(projectUrge));
    }

    /**
     * 修改项目督促表
     * @param projectUrge 项目督促表
     * @return R
     */
    @ApiOperation(value = "修改项目督促表", notes = "修改项目督促表")
    @SysLog("修改项目督促表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_projecturge_edit')" )
    public R updateById(@RequestBody ProjectUrge projectUrge) {
        return R.ok(projectUrgeService.updateById(projectUrge));
    }

    /**
     * 通过id删除项目督促表
     * @param projectUrgeId id
     * @return R
     */
    @ApiOperation(value = "通过id删除项目督促表", notes = "通过id删除项目督促表")
    @SysLog("通过id删除项目督促表" )
    @DeleteMapping("/{projectUrgeId}" )
    @PreAuthorize("@pms.hasPermission('casee_projecturge_del')" )
    public R removeById(@PathVariable Integer projectUrgeId) {
        return R.ok(projectUrgeService.removeById(projectUrgeId));
    }

}
