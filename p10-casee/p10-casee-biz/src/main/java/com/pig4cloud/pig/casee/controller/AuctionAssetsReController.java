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
import com.pig4cloud.pig.casee.entity.AuctionAssetsRe;
import com.pig4cloud.pig.casee.service.AuctionAssetsReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 拍卖与项目案件财产关联表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auctionassetsre" )
@Api(value = "auctionassetsre", tags = "拍卖与项目案件财产关联表管理")
public class AuctionAssetsReController {

    private final  AuctionAssetsReService auctionAssetsReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param auctionAssetsRe 拍卖与项目案件财产关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_auctionassetsre_get')" )
    public R getAuctionAssetsRePage(Page page, AuctionAssetsRe auctionAssetsRe) {
        return R.ok(auctionAssetsReService.page(page, Wrappers.query(auctionAssetsRe)));
    }


    /**
     * 通过id查询拍卖与项目案件财产关联表
     * @param auctionAssetsRe id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{auctionAssetsRe}" )
    @PreAuthorize("@pms.hasPermission('casee_auctionassetsre_get')" )
    public R getById(@PathVariable("auctionAssetsRe" ) Integer auctionAssetsRe) {
        return R.ok(auctionAssetsReService.getById(auctionAssetsRe));
    }

    /**
     * 新增拍卖与项目案件财产关联表
     * @param auctionAssetsRe 拍卖与项目案件财产关联表
     * @return R
     */
    @ApiOperation(value = "新增拍卖与项目案件财产关联表", notes = "新增拍卖与项目案件财产关联表")
    @SysLog("新增拍卖与项目案件财产关联表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_auctionassetsre_add')" )
    public R save(@RequestBody AuctionAssetsRe auctionAssetsRe) {
        return R.ok(auctionAssetsReService.save(auctionAssetsRe));
    }

    /**
     * 修改拍卖与项目案件财产关联表
     * @param auctionAssetsRe 拍卖与项目案件财产关联表
     * @return R
     */
    @ApiOperation(value = "修改拍卖与项目案件财产关联表", notes = "修改拍卖与项目案件财产关联表")
    @SysLog("修改拍卖与项目案件财产关联表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_auctionassetsre_edit')" )
    public R updateById(@RequestBody AuctionAssetsRe auctionAssetsRe) {
        return R.ok(auctionAssetsReService.updateById(auctionAssetsRe));
    }

    /**
     * 通过id删除拍卖与项目案件财产关联表
     * @param auctionAssetsRe id
     * @return R
     */
    @ApiOperation(value = "通过id删除拍卖与项目案件财产关联表", notes = "通过id删除拍卖与项目案件财产关联表")
    @SysLog("通过id删除拍卖与项目案件财产关联表" )
    @DeleteMapping("/{auctionAssetsRe}" )
    @PreAuthorize("@pms.hasPermission('casee_auctionassetsre_del')" )
    public R removeById(@PathVariable Integer auctionAssetsRe) {
        return R.ok(auctionAssetsReService.removeById(auctionAssetsRe));
    }

}
