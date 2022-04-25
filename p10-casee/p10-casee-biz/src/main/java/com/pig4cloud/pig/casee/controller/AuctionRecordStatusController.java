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
import com.pig4cloud.pig.casee.entity.paifuentity.AuctionRecordStatus;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.service.AuctionRecordStatusService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 拍卖记录状态表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auctionrecordstatus" )
@Api(value = "auctionrecordstatus", tags = "拍卖记录状态表管理")
public class AuctionRecordStatusController {

    private final  AuctionRecordStatusService auctionRecordStatusService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param auctionRecordStatus 拍卖记录状态表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_auctionrecordstatus_get')" )
    public R getAuctionRecordStatusPage(Page page, AuctionRecordStatus auctionRecordStatus) {
        return R.ok(auctionRecordStatusService.page(page, Wrappers.query(auctionRecordStatus)));
    }


    /**
     * 通过id查询拍卖记录状态表
     * @param auctionRecordStatus id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{auctionRecordStatus}" )
    @PreAuthorize("@pms.hasPermission('casee_auctionrecordstatus_get')" )
    public R getById(@PathVariable("auctionRecordStatus" ) Integer auctionRecordStatus) {
        return R.ok(auctionRecordStatusService.getById(auctionRecordStatus));
    }

    /**
     * 新增拍卖记录状态表
     * @param auctionRecordStatus 拍卖记录状态表
     * @return R
     */
    @ApiOperation(value = "新增拍卖记录状态表", notes = "新增拍卖记录状态表")
    @SysLog("新增拍卖记录状态表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_auctionrecordstatus_add')" )
    public R save(@RequestBody AuctionRecordStatus auctionRecordStatus) {
        return R.ok(auctionRecordStatusService.save(auctionRecordStatus));
    }

    /**
     * 修改拍卖记录状态表
     * @param auctionRecordStatus 拍卖记录状态表
     * @return R
     */
    @ApiOperation(value = "修改拍卖记录状态表", notes = "修改拍卖记录状态表")
    @SysLog("修改拍卖记录状态表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_auctionrecordstatus_edit')" )
    public R updateById(@RequestBody AuctionRecordStatus auctionRecordStatus) {
        return R.ok(auctionRecordStatusService.updateById(auctionRecordStatus));
    }

    /**
     * 通过id删除拍卖记录状态表
     * @param auctionRecordStatus id
     * @return R
     */
    @ApiOperation(value = "通过id删除拍卖记录状态表", notes = "通过id删除拍卖记录状态表")
    @SysLog("通过id删除拍卖记录状态表" )
    @DeleteMapping("/{auctionRecordStatus}" )
    @PreAuthorize("@pms.hasPermission('casee_auctionrecordstatus_del')" )
    public R removeById(@PathVariable Integer auctionRecordStatus) {
        return R.ok(auctionRecordStatusService.removeById(auctionRecordStatus));
    }

}
