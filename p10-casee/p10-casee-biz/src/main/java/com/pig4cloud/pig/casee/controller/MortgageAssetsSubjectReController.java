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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.entity.MortgageAssetsSubjectRe;
import com.pig4cloud.pig.casee.service.MortgageAssetsSubjectReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 财产关联债务人表
 *
 * @author Mjh
 * @date 2022-04-13 11:23:59
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/mortgageassetssubjectre" )
@Api(value = "mortgageassetssubjectre", tags = "财产关联债务人表管理")
public class MortgageAssetsSubjectReController {

    private final  MortgageAssetsSubjectReService mortgageAssetsSubjectReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param mortgageAssetsSubjectRe 财产关联债务人表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getMortgageAssetsSubjectRePage(Page page, MortgageAssetsSubjectRe mortgageAssetsSubjectRe) {
        return R.ok(mortgageAssetsSubjectReService.page(page, Wrappers.query(mortgageAssetsSubjectRe)));
    }


    /**
     * 通过id查询财产关联债务人表
     * @param mortgageAssetsSubjectReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{mortgageAssetsSubjectReId}" )
    public R getById(@PathVariable("mortgageAssetsSubjectReId" ) Integer mortgageAssetsSubjectReId) {
        return R.ok(mortgageAssetsSubjectReService.getById(mortgageAssetsSubjectReId));
    }

    /**
     * 新增财产关联债务人表
     * @param mortgageAssetsSubjectRe 财产关联债务人表
     * @return R
     */
    @ApiOperation(value = "新增财产关联债务人表", notes = "新增财产关联债务人表")
    @SysLog("新增财产关联债务人表" )
    @PostMapping
    public R save(@RequestBody MortgageAssetsSubjectRe mortgageAssetsSubjectRe) {
        return R.ok(mortgageAssetsSubjectReService.save(mortgageAssetsSubjectRe));
    }

    /**
     * 修改财产关联债务人表
     * @param mortgageAssetsSubjectRe 财产关联债务人表
     * @return R
     */
    @ApiOperation(value = "修改财产关联债务人表", notes = "修改财产关联债务人表")
    @SysLog("修改财产关联债务人表" )
    @PutMapping
    public R updateById(@RequestBody MortgageAssetsSubjectRe mortgageAssetsSubjectRe) {
        return R.ok(mortgageAssetsSubjectReService.updateById(mortgageAssetsSubjectRe));
    }

    /**
     * 通过id删除财产关联债务人表
     * @param mortgageAssetsSubjectReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除财产关联债务人表", notes = "通过id删除财产关联债务人表")
    @SysLog("通过id删除财产关联债务人表" )
    @DeleteMapping("/{mortgageAssetsSubjectReId}" )
    public R removeById(@PathVariable Integer mortgageAssetsSubjectReId) {
        return R.ok(mortgageAssetsSubjectReService.removeById(mortgageAssetsSubjectReId));
    }

	/**
	 * 根据抵押记录id查询集合
	 * @param mortgageAssetsReId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getByMortgageAssetsReId/{mortgageAssetsReId}" )
	public R getByMortgageAssetsReId(@PathVariable("mortgageAssetsReId" ) Integer mortgageAssetsReId) {
		QueryWrapper<MortgageAssetsSubjectRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(MortgageAssetsSubjectRe::getMortgageAssetsReId,mortgageAssetsReId);
		return R.ok(mortgageAssetsSubjectReService.list(queryWrapper));
	}

}
