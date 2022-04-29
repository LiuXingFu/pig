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
import com.pig4cloud.pig.casee.entity.LeadTheWayActualLookSamplerRe;
import com.pig4cloud.pig.casee.service.LeadTheWayActualLookSamplerReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 看样人员名单关联引领看样表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/leadthewayactuallooksamplerre" )
@Api(value = "leadthewayactuallooksamplerre", tags = "看样人员名单关联引领看样表管理")
public class LeadTheWayActualLookSamplerReController {

    private final  LeadTheWayActualLookSamplerReService leadTheWayActualLookSamplerReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param leadTheWayActualLookSamplerRe 看样人员名单关联引领看样表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_leadthewayactuallooksamplerre_get')" )
    public R getLeadTheWayActualLookSamplerRePage(Page page, LeadTheWayActualLookSamplerRe leadTheWayActualLookSamplerRe) {
        return R.ok(leadTheWayActualLookSamplerReService.page(page, Wrappers.query(leadTheWayActualLookSamplerRe)));
    }


    /**
     * 通过id查询看样人员名单关联引领看样表
     * @param leadTheWayActualLookSamplerReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{leadTheWayActualLookSamplerReId}" )
    @PreAuthorize("@pms.hasPermission('casee_leadthewayactuallooksamplerre_get')" )
    public R getById(@PathVariable("leadTheWayActualLookSamplerReId" ) Integer leadTheWayActualLookSamplerReId) {
        return R.ok(leadTheWayActualLookSamplerReService.getById(leadTheWayActualLookSamplerReId));
    }

    /**
     * 新增看样人员名单关联引领看样表
     * @param leadTheWayActualLookSamplerRe 看样人员名单关联引领看样表
     * @return R
     */
    @ApiOperation(value = "新增看样人员名单关联引领看样表", notes = "新增看样人员名单关联引领看样表")
    @SysLog("新增看样人员名单关联引领看样表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_leadthewayactuallooksamplerre_add')" )
    public R save(@RequestBody LeadTheWayActualLookSamplerRe leadTheWayActualLookSamplerRe) {
        return R.ok(leadTheWayActualLookSamplerReService.save(leadTheWayActualLookSamplerRe));
    }

    /**
     * 修改看样人员名单关联引领看样表
     * @param leadTheWayActualLookSamplerRe 看样人员名单关联引领看样表
     * @return R
     */
    @ApiOperation(value = "修改看样人员名单关联引领看样表", notes = "修改看样人员名单关联引领看样表")
    @SysLog("修改看样人员名单关联引领看样表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_leadthewayactuallooksamplerre_edit')" )
    public R updateById(@RequestBody LeadTheWayActualLookSamplerRe leadTheWayActualLookSamplerRe) {
        return R.ok(leadTheWayActualLookSamplerReService.updateById(leadTheWayActualLookSamplerRe));
    }

    /**
     * 通过id删除看样人员名单关联引领看样表
     * @param leadTheWayActualLookSamplerReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除看样人员名单关联引领看样表", notes = "通过id删除看样人员名单关联引领看样表")
    @SysLog("通过id删除看样人员名单关联引领看样表" )
    @DeleteMapping("/{leadTheWayActualLookSamplerReId}" )
    @PreAuthorize("@pms.hasPermission('casee_leadthewayactuallooksamplerre_del')" )
    public R removeById(@PathVariable Integer leadTheWayActualLookSamplerReId) {
        return R.ok(leadTheWayActualLookSamplerReService.removeById(leadTheWayActualLookSamplerReId));
    }

}
