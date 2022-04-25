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
import com.pig4cloud.pig.casee.entity.paifuentity.AuctionDeal;
import com.pig4cloud.pig.casee.service.AuctionDealService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 拍卖成交表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:57
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auctiondeal" )
@Api(value = "auctiondeal", tags = "拍卖成交表管理")
public class AuctionDealController {

    private final  AuctionDealService auctionDealService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param auctionDeal 拍卖成交表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_auctiondeal_get')" )
    public R getAuctionDealPage(Page page, AuctionDeal auctionDeal) {
        return R.ok(auctionDealService.page(page, Wrappers.query(auctionDeal)));
    }


    /**
     * 通过id查询拍卖成交表
     * @param auctionDeal id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{auctionDeal}" )
    @PreAuthorize("@pms.hasPermission('casee_auctiondeal_get')" )
    public R getById(@PathVariable("auctionDeal" ) Integer auctionDeal) {
        return R.ok(auctionDealService.getById(auctionDeal));
    }

    /**
     * 新增拍卖成交表
     * @param auctionDeal 拍卖成交表
     * @return R
     */
    @ApiOperation(value = "新增拍卖成交表", notes = "新增拍卖成交表")
    @SysLog("新增拍卖成交表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_auctiondeal_add')" )
    public R save(@RequestBody AuctionDeal auctionDeal) {
        return R.ok(auctionDealService.save(auctionDeal));
    }

    /**
     * 修改拍卖成交表
     * @param auctionDeal 拍卖成交表
     * @return R
     */
    @ApiOperation(value = "修改拍卖成交表", notes = "修改拍卖成交表")
    @SysLog("修改拍卖成交表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_auctiondeal_edit')" )
    public R updateById(@RequestBody AuctionDeal auctionDeal) {
        return R.ok(auctionDealService.updateById(auctionDeal));
    }

    /**
     * 通过id删除拍卖成交表
     * @param auctionDeal id
     * @return R
     */
    @ApiOperation(value = "通过id删除拍卖成交表", notes = "通过id删除拍卖成交表")
    @SysLog("通过id删除拍卖成交表" )
    @DeleteMapping("/{auctionDeal}" )
    @PreAuthorize("@pms.hasPermission('casee_auctiondeal_del')" )
    public R removeById(@PathVariable Integer auctionDeal) {
        return R.ok(auctionDealService.removeById(auctionDeal));
    }

}
