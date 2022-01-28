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
import com.pig4cloud.pig.casee.entity.AssetsBankLoanRe;
import com.pig4cloud.pig.casee.service.AssetsBankLoanReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 财产关联银行借贷表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/assetsbankloanre" )
@Api(value = "assetsbankloanre", tags = "财产关联银行借贷表管理")
public class AssetsBankLoanReController {

    private final AssetsBankLoanReService assetsBankLoanReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param assetsBankLoanRe 财产关联银行借贷表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getAssetsBankLoanRePage(Page page, AssetsBankLoanRe assetsBankLoanRe) {
        return R.ok(assetsBankLoanReService.page(page, Wrappers.query(assetsBankLoanRe)));
    }


    /**
     * 通过id查询财产关联银行借贷表
     * @param assetsBankLoanId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{assetsBankLoanId}" )
    public R getById(@PathVariable("assetsBankLoanId" ) Integer assetsBankLoanId) {
        return R.ok(assetsBankLoanReService.getById(assetsBankLoanId));
    }

    /**
     * 新增财产关联银行借贷表
     * @param assetsBankLoanRe 财产关联银行借贷表
     * @return R
     */
    @ApiOperation(value = "新增财产关联银行借贷表", notes = "新增财产关联银行借贷表")
    @SysLog("新增财产关联银行借贷表" )
    @PostMapping
    public R save(@RequestBody AssetsBankLoanRe assetsBankLoanRe) {
        return R.ok(assetsBankLoanReService.save(assetsBankLoanRe));
    }

    /**
     * 修改财产关联银行借贷表
     * @param assetsBankLoanRe 财产关联银行借贷表
     * @return R
     */
    @ApiOperation(value = "修改财产关联银行借贷表", notes = "修改财产关联银行借贷表")
    @SysLog("修改财产关联银行借贷表" )
    @PutMapping
    public R updateById(@RequestBody AssetsBankLoanRe assetsBankLoanRe) {
        return R.ok(assetsBankLoanReService.updateById(assetsBankLoanRe));
    }

    /**
     * 通过id删除财产关联银行借贷表
     * @param assetsBankLoanId id
     * @return R
     */
    @ApiOperation(value = "通过id删除财产关联银行借贷表", notes = "通过id删除财产关联银行借贷表")
    @SysLog("通过id删除财产关联银行借贷表" )
    @DeleteMapping("/{assetsBankLoanId}" )
    public R removeById(@PathVariable Integer assetsBankLoanId) {
        return R.ok(assetsBankLoanReService.removeById(assetsBankLoanId));
    }

}
