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
import com.pig4cloud.pig.admin.api.entity.ClientUserRe;
import com.pig4cloud.pig.admin.service.ClientUserReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author yuanduo
 * @date 2021-12-08 16:21:04
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/clientuserre" )
@Api(value = "clientuserre", tags = "管理")
public class ClientUserReController {

    private final ClientUserReService clientUserReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param clientUserRe 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('admin_clientuserre_get')" )
    public R getClientUserRePage(Page page, ClientUserRe clientUserRe) {
        return R.ok(clientUserReService.page(page, Wrappers.query(clientUserRe)));
    }


    /**
     * 通过id查询
     * @param clientId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{clientId}" )
    @PreAuthorize("@pms.hasPermission('admin_clientuserre_get')" )
    public R getById(@PathVariable("clientId" ) String clientId) {
        return R.ok(clientUserReService.getById(clientId));
    }

    /**
     * 新增
     * @param clientUserRe 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_clientuserre_add')" )
    public R save(@RequestBody ClientUserRe clientUserRe) {
        return R.ok(clientUserReService.save(clientUserRe));
    }

    /**
     * 修改
     * @param clientUserRe 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_clientuserre_edit')" )
    public R updateById(@RequestBody ClientUserRe clientUserRe) {
        return R.ok(clientUserReService.updateById(clientUserRe));
    }

    /**
     * 通过id删除
     * @param clientId id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{clientId}" )
    @PreAuthorize("@pms.hasPermission('admin_clientuserre_del')" )
    public R removeById(@PathVariable String clientId) {
        return R.ok(clientUserReService.removeById(clientId));
    }

}
