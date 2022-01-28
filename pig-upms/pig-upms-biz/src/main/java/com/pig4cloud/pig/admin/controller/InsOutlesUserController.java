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
import com.pig4cloud.pig.admin.api.entity.InsOutlesUser;
import com.pig4cloud.pig.admin.service.InsOutlesUserService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 机构/网点用户关联表
 *
 * @author xls
 * @date 2022-01-27 19:30:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/insoutlesuser" )
@Api(value = "insoutlesuser", tags = "机构/网点用户关联表管理")
public class InsOutlesUserController {

    private final InsOutlesUserService insOutlesUserService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param insOutlesUser 机构/网点用户关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('admin_insoutlesuser_get')" )
    public R getInsOutlesUserPage(Page page, InsOutlesUser insOutlesUser) {
        return R.ok(insOutlesUserService.page(page, Wrappers.query(insOutlesUser)));
    }


    /**
     * 通过id查询机构/网点用户关联表
     * @param insOutlesUserId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{insOutlesUserId}" )
    @PreAuthorize("@pms.hasPermission('admin_insoutlesuser_get')" )
    public R getById(@PathVariable("insOutlesUserId" ) Integer insOutlesUserId) {
        return R.ok(insOutlesUserService.getById(insOutlesUserId));
    }

    /**
     * 新增机构/网点用户关联表
     * @param insOutlesUser 机构/网点用户关联表
     * @return R
     */
    @ApiOperation(value = "新增机构/网点用户关联表", notes = "新增机构/网点用户关联表")
    @SysLog("新增机构/网点用户关联表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_insoutlesuser_add')" )
    public R save(@RequestBody InsOutlesUser insOutlesUser) {
        return R.ok(insOutlesUserService.save(insOutlesUser));
    }

    /**
     * 修改机构/网点用户关联表
     * @param insOutlesUser 机构/网点用户关联表
     * @return R
     */
    @ApiOperation(value = "修改机构/网点用户关联表", notes = "修改机构/网点用户关联表")
    @SysLog("修改机构/网点用户关联表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_insoutlesuser_edit')" )
    public R updateById(@RequestBody InsOutlesUser insOutlesUser) {
        return R.ok(insOutlesUserService.updateById(insOutlesUser));
    }

    /**
     * 通过id删除机构/网点用户关联表
     * @param insOutlesUserId id
     * @return R
     */
    @ApiOperation(value = "通过id删除机构/网点用户关联表", notes = "通过id删除机构/网点用户关联表")
    @SysLog("通过id删除机构/网点用户关联表" )
    @DeleteMapping("/{insOutlesUserId}" )
    @PreAuthorize("@pms.hasPermission('admin_insoutlesuser_del')" )
    public R removeById(@PathVariable Integer insOutlesUserId) {
        return R.ok(insOutlesUserService.removeById(insOutlesUserId));
    }





}