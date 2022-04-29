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
import com.pig4cloud.pig.casee.entity.LeadTheWay;
import com.pig4cloud.pig.casee.service.LeadTheWayService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 引领看样表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/leadtheway" )
@Api(value = "leadtheway", tags = "引领看样表管理")
public class LeadTheWayController {

    private final  LeadTheWayService leadTheWayService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param leadTheWay 引领看样表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_leadtheway_get')" )
    public R getLeadTheWayPage(Page page, LeadTheWay leadTheWay) {
        return R.ok(leadTheWayService.page(page, Wrappers.query(leadTheWay)));
    }


    /**
     * 通过id查询引领看样表
     * @param leadTheWayId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{leadTheWayId}" )
    @PreAuthorize("@pms.hasPermission('casee_leadtheway_get')" )
    public R getById(@PathVariable("leadTheWayId" ) Integer leadTheWayId) {
        return R.ok(leadTheWayService.getById(leadTheWayId));
    }

    /**
     * 新增引领看样表
     * @param leadTheWay 引领看样表
     * @return R
     */
    @ApiOperation(value = "新增引领看样表", notes = "新增引领看样表")
    @SysLog("新增引领看样表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_leadtheway_add')" )
    public R save(@RequestBody LeadTheWay leadTheWay) {
        return R.ok(leadTheWayService.save(leadTheWay));
    }

    /**
     * 修改引领看样表
     * @param leadTheWay 引领看样表
     * @return R
     */
    @ApiOperation(value = "修改引领看样表", notes = "修改引领看样表")
    @SysLog("修改引领看样表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_leadtheway_edit')" )
    public R updateById(@RequestBody LeadTheWay leadTheWay) {
        return R.ok(leadTheWayService.updateById(leadTheWay));
    }

    /**
     * 通过id删除引领看样表
     * @param leadTheWayId id
     * @return R
     */
    @ApiOperation(value = "通过id删除引领看样表", notes = "通过id删除引领看样表")
    @SysLog("通过id删除引领看样表" )
    @DeleteMapping("/{leadTheWayId}" )
    @PreAuthorize("@pms.hasPermission('casee_leadtheway_del')" )
    public R removeById(@PathVariable Integer leadTheWayId) {
        return R.ok(leadTheWayService.removeById(leadTheWayId));
    }

}
