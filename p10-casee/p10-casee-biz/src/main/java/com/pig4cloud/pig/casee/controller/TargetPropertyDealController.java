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
import com.pig4cloud.pig.casee.entity.TargetPropertyDeal;
import com.pig4cloud.pig.casee.service.TargetPropertyDealService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 标的物品关联表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/targetpropertydeal" )
@Api(value = "targetpropertydeal", tags = "标的物品关联表管理")
public class TargetPropertyDealController {

    private final  TargetPropertyDealService targetPropertyDealService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param targetPropertyDeal 标的物品关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_targetpropertydeal_get')" )
    public R getTargetPropertyDealPage(Page page, TargetPropertyDeal targetPropertyDeal) {
        return R.ok(targetPropertyDealService.page(page, Wrappers.query(targetPropertyDeal)));
    }


    /**
     * 通过id查询标的物品关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('casee_targetpropertydeal_get')" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(targetPropertyDealService.getById(id));
    }

    /**
     * 新增标的物品关联表
     * @param targetPropertyDeal 标的物品关联表
     * @return R
     */
    @ApiOperation(value = "新增标的物品关联表", notes = "新增标的物品关联表")
    @SysLog("新增标的物品关联表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_targetpropertydeal_add')" )
    public R save(@RequestBody TargetPropertyDeal targetPropertyDeal) {
        return R.ok(targetPropertyDealService.save(targetPropertyDeal));
    }

    /**
     * 修改标的物品关联表
     * @param targetPropertyDeal 标的物品关联表
     * @return R
     */
    @ApiOperation(value = "修改标的物品关联表", notes = "修改标的物品关联表")
    @SysLog("修改标的物品关联表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_targetpropertydeal_edit')" )
    public R updateById(@RequestBody TargetPropertyDeal targetPropertyDeal) {
        return R.ok(targetPropertyDealService.updateById(targetPropertyDeal));
    }

    /**
     * 通过id删除标的物品关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除标的物品关联表", notes = "通过id删除标的物品关联表")
    @SysLog("通过id删除标的物品关联表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('casee_targetpropertydeal_del')" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(targetPropertyDealService.removeById(id));
    }

}
