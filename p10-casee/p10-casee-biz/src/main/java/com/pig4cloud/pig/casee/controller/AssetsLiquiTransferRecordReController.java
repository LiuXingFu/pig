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
import com.pig4cloud.pig.casee.entity.AssetsLiquiTransferRecordRe;
import com.pig4cloud.pig.casee.service.AssetsLiquiTransferRecordReService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 财产关联清收移交记录表
 *
 * @author Mjh
 * @date 2022-04-06 15:22:06
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/assetsliquitransferrecordre" )
@Api(value = "assetsliquitransferrecordre", tags = "财产关联清收移交记录表管理")
public class AssetsLiquiTransferRecordReController {

    private final  AssetsLiquiTransferRecordReService assetsLiquiTransferRecordReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param assetsLiquiTransferRecordRe 财产关联清收移交记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAssetsLiquiTransferRecordRePage(Page page, AssetsLiquiTransferRecordRe assetsLiquiTransferRecordRe) {
        return R.ok(assetsLiquiTransferRecordReService.page(page, Wrappers.query(assetsLiquiTransferRecordRe)));
    }


    /**
     * 通过id查询财产关联清收移交记录表
     * @param assetsClearTransferRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{assetsClearTransferRecordId}" )
    public R getById(@PathVariable("assetsClearTransferRecordId" ) Integer assetsClearTransferRecordId) {
        return R.ok(assetsLiquiTransferRecordReService.getById(assetsClearTransferRecordId));
    }

    /**
     * 新增财产关联清收移交记录表
     * @param assetsLiquiTransferRecordRe 财产关联清收移交记录表
     * @return R
     */
    @ApiOperation(value = "新增财产关联清收移交记录表", notes = "新增财产关联清收移交记录表")
    @SysLog("新增财产关联清收移交记录表" )
    @PostMapping
    public R save(@RequestBody AssetsLiquiTransferRecordRe assetsLiquiTransferRecordRe) {
        return R.ok(assetsLiquiTransferRecordReService.save(assetsLiquiTransferRecordRe));
    }

    /**
     * 修改财产关联清收移交记录表
     * @param assetsLiquiTransferRecordRe 财产关联清收移交记录表
     * @return R
     */
    @ApiOperation(value = "修改财产关联清收移交记录表", notes = "修改财产关联清收移交记录表")
    @SysLog("修改财产关联清收移交记录表" )
    @PutMapping
    public R updateById(@RequestBody AssetsLiquiTransferRecordRe assetsLiquiTransferRecordRe) {
        return R.ok(assetsLiquiTransferRecordReService.updateById(assetsLiquiTransferRecordRe));
    }

    /**
     * 通过id删除财产关联清收移交记录表
     * @param assetsClearTransferRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id删除财产关联清收移交记录表", notes = "通过id删除财产关联清收移交记录表")
    @SysLog("通过id删除财产关联清收移交记录表" )
    @DeleteMapping("/{assetsClearTransferRecordId}" )
    public R removeById(@PathVariable Integer assetsClearTransferRecordId) {
        return R.ok(assetsLiquiTransferRecordReService.removeById(assetsClearTransferRecordId));
    }

}
