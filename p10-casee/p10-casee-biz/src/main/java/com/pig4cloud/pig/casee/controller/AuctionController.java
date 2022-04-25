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
import com.pig4cloud.pig.casee.entity.paifuentity.Auction;
import com.pig4cloud.pig.casee.service.AuctionService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 拍卖表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auction" )
@Api(value = "auction", tags = "拍卖表管理")
public class AuctionController {

    private final  AuctionService auctionService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param auction 拍卖表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_auction_get')" )
    public R getAuctionPage(Page page, Auction auction) {
        return R.ok(auctionService.page(page, Wrappers.query(auction)));
    }


    /**
     * 通过id查询拍卖表
     * @param auctionId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{auctionId}" )
    @PreAuthorize("@pms.hasPermission('casee_auction_get')" )
    public R getById(@PathVariable("auctionId" ) Integer auctionId) {
        return R.ok(auctionService.getById(auctionId));
    }

    /**
     * 新增拍卖表
     * @param auction 拍卖表
     * @return R
     */
    @ApiOperation(value = "新增拍卖表", notes = "新增拍卖表")
    @SysLog("新增拍卖表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_auction_add')" )
    public R save(@RequestBody Auction auction) {
        return R.ok(auctionService.save(auction));
    }

    /**
     * 修改拍卖表
     * @param auction 拍卖表
     * @return R
     */
    @ApiOperation(value = "修改拍卖表", notes = "修改拍卖表")
    @SysLog("修改拍卖表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_auction_edit')" )
    public R updateById(@RequestBody Auction auction) {
        return R.ok(auctionService.updateById(auction));
    }

    /**
     * 通过id删除拍卖表
     * @param auctionId id
     * @return R
     */
    @ApiOperation(value = "通过id删除拍卖表", notes = "通过id删除拍卖表")
    @SysLog("通过id删除拍卖表" )
    @DeleteMapping("/{auctionId}" )
    @PreAuthorize("@pms.hasPermission('casee_auction_del')" )
    public R removeById(@PathVariable Integer auctionId) {
        return R.ok(auctionService.removeById(auctionId));
    }

}
