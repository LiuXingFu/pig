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
import com.pig4cloud.pig.casee.entity.AuctionResults;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.service.AuctionResultsService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 拍卖结果表
 *
 * @author pig code generator
 * @date 2022-04-25 20:59:35
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auctionresults" )
@Api(value = "auctionresults", tags = "拍卖结果表管理")
public class AuctionResultsController {

    private final  AuctionResultsService auctionResultsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param auctionResults 拍卖结果表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_auctionresults_get')" )
    public R getAuctionResultsPage(Page page, AuctionResults auctionResults) {
        return R.ok(auctionResultsService.page(page, Wrappers.query(auctionResults)));
    }


    /**
     * 通过id查询拍卖结果表
     * @param auctionResultsId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{auctionResultsId}" )
    @PreAuthorize("@pms.hasPermission('casee_auctionresults_get')" )
    public R getById(@PathVariable("auctionResultsId" ) Integer auctionResultsId) {
        return R.ok(auctionResultsService.getById(auctionResultsId));
    }

    /**
     * 新增拍卖结果表
     * @param auctionResults 拍卖结果表
     * @return R
     */
    @ApiOperation(value = "新增拍卖结果表", notes = "新增拍卖结果表")
    @SysLog("新增拍卖结果表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_auctionresults_add')" )
    public R save(@RequestBody AuctionResults auctionResults) {
        return R.ok(auctionResultsService.save(auctionResults));
    }

    /**
     * 修改拍卖结果表
     * @param auctionResults 拍卖结果表
     * @return R
     */
    @ApiOperation(value = "修改拍卖结果表", notes = "修改拍卖结果表")
    @SysLog("修改拍卖结果表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_auctionresults_edit')" )
    public R updateById(@RequestBody AuctionResults auctionResults) {
        return R.ok(auctionResultsService.updateById(auctionResults));
    }

    /**
     * 通过id删除拍卖结果表
     * @param auctionResultsId id
     * @return R
     */
    @ApiOperation(value = "通过id删除拍卖结果表", notes = "通过id删除拍卖结果表")
    @SysLog("通过id删除拍卖结果表" )
    @DeleteMapping("/{auctionResultsId}" )
    @PreAuthorize("@pms.hasPermission('casee_auctionresults_del')" )
    public R removeById(@PathVariable Integer auctionResultsId) {
        return R.ok(auctionResultsService.removeById(auctionResultsId));
    }

}
