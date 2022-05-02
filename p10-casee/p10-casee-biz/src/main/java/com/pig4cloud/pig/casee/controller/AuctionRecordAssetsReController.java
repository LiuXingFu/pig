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
import com.pig4cloud.pig.casee.entity.AuctionRecordAssetsRe;
import com.pig4cloud.pig.casee.service.AuctionRecordAssetsReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 拍卖记录财产关联表
 *
 * @author pig code generator
 * @date 2022-05-02 11:45:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auctionrecordassetsre" )
@Api(value = "auctionrecordassetsre", tags = "拍卖记录财产关联表管理")
public class AuctionRecordAssetsReController {

    private final  AuctionRecordAssetsReService auctionRecordAssetsReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param auctionRecordAssetsRe 拍卖记录财产关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_auctionrecordassetsre_get')" )
    public R getAuctionRecordAssetsRePage(Page page, AuctionRecordAssetsRe auctionRecordAssetsRe) {
        return R.ok(auctionRecordAssetsReService.page(page, Wrappers.query(auctionRecordAssetsRe)));
    }


    /**
     * 通过id查询拍卖记录财产关联表
     * @param auctionRecordAssetsRe id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{auctionRecordAssetsRe}" )
    @PreAuthorize("@pms.hasPermission('casee_auctionrecordassetsre_get')" )
    public R getById(@PathVariable("auctionRecordAssetsRe" ) Integer auctionRecordAssetsRe) {
        return R.ok(auctionRecordAssetsReService.getById(auctionRecordAssetsRe));
    }

    /**
     * 新增拍卖记录财产关联表
     * @param auctionRecordAssetsRe 拍卖记录财产关联表
     * @return R
     */
    @ApiOperation(value = "新增拍卖记录财产关联表", notes = "新增拍卖记录财产关联表")
    @SysLog("新增拍卖记录财产关联表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_auctionrecordassetsre_add')" )
    public R save(@RequestBody AuctionRecordAssetsRe auctionRecordAssetsRe) {
        return R.ok(auctionRecordAssetsReService.save(auctionRecordAssetsRe));
    }

    /**
     * 修改拍卖记录财产关联表
     * @param auctionRecordAssetsRe 拍卖记录财产关联表
     * @return R
     */
    @ApiOperation(value = "修改拍卖记录财产关联表", notes = "修改拍卖记录财产关联表")
    @SysLog("修改拍卖记录财产关联表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_auctionrecordassetsre_edit')" )
    public R updateById(@RequestBody AuctionRecordAssetsRe auctionRecordAssetsRe) {
        return R.ok(auctionRecordAssetsReService.updateById(auctionRecordAssetsRe));
    }

    /**
     * 通过id删除拍卖记录财产关联表
     * @param auctionRecordAssetsRe id
     * @return R
     */
    @ApiOperation(value = "通过id删除拍卖记录财产关联表", notes = "通过id删除拍卖记录财产关联表")
    @SysLog("通过id删除拍卖记录财产关联表" )
    @DeleteMapping("/{auctionRecordAssetsRe}" )
    @PreAuthorize("@pms.hasPermission('casee_auctionrecordassetsre_del')" )
    public R removeById(@PathVariable Integer auctionRecordAssetsRe) {
        return R.ok(auctionRecordAssetsReService.removeById(auctionRecordAssetsRe));
    }

}
