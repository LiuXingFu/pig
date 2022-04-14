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
import com.pig4cloud.pig.casee.entity.MortgageAssetsRe;
import com.pig4cloud.pig.casee.service.MortgageAssetsReService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 抵押财产关联表
 *
 * @author Mjh
 * @date 2022-04-13 11:24:36
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/mortgageassetsre" )
@Api(value = "mortgageassetsre", tags = "抵押财产关联表管理")
public class MortgageAssetsReController {

    private final  MortgageAssetsReService mortgageAssetsReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param mortgageAssetsRe 抵押财产关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getMortgageAssetsRePage(Page page, MortgageAssetsRe mortgageAssetsRe) {
        return R.ok(mortgageAssetsReService.page(page, Wrappers.query(mortgageAssetsRe)));
    }


    /**
     * 通过id查询抵押财产关联表
     * @param mortgageAssetsRe id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{mortgageAssetsRe}" )
    public R getById(@PathVariable("mortgageAssetsRe" ) Integer mortgageAssetsRe) {
        return R.ok(mortgageAssetsReService.getById(mortgageAssetsRe));
    }

    /**
     * 新增抵押财产关联表
     * @param mortgageAssetsRe 抵押财产关联表
     * @return R
     */
    @ApiOperation(value = "新增抵押财产关联表", notes = "新增抵押财产关联表")
    @SysLog("新增抵押财产关联表" )
    @PostMapping
    public R save(@RequestBody MortgageAssetsRe mortgageAssetsRe) {
        return R.ok(mortgageAssetsReService.save(mortgageAssetsRe));
    }

    /**
     * 修改抵押财产关联表
     * @param mortgageAssetsRe 抵押财产关联表
     * @return R
     */
    @ApiOperation(value = "修改抵押财产关联表", notes = "修改抵押财产关联表")
    @SysLog("修改抵押财产关联表" )
    @PutMapping
    public R updateById(@RequestBody MortgageAssetsRe mortgageAssetsRe) {
        return R.ok(mortgageAssetsReService.updateById(mortgageAssetsRe));
    }

    /**
     * 通过id删除抵押财产关联表
     * @param mortgageAssetsRe id
     * @return R
     */
    @ApiOperation(value = "通过id删除抵押财产关联表", notes = "通过id删除抵押财产关联表")
    @SysLog("通过id删除抵押财产关联表" )
    @DeleteMapping("/{mortgageAssetsRe}" )
    public R removeById(@PathVariable Integer mortgageAssetsRe) {
        return R.ok(mortgageAssetsReService.removeById(mortgageAssetsRe));
    }

}
