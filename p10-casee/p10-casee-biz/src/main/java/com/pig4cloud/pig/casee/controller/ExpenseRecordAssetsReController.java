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
import com.pig4cloud.pig.casee.entity.ExpenseRecordAssetsRe;
import com.pig4cloud.pig.casee.service.ExpenseRecordAssetsReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 产生费用财产关联表
 *
 * @author pig code generator
 * @date 2022-05-03 21:15:30
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/expenserecordassetsre" )
@Api(value = "expenserecordassetsre", tags = "产生费用财产关联表管理")
public class ExpenseRecordAssetsReController {

    private final  ExpenseRecordAssetsReService expenseRecordAssetsReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param expenseRecordAssetsRe 产生费用财产关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_expenserecordassetsre_get')" )
    public R getExpenseRecordAssetsRePage(Page page, ExpenseRecordAssetsRe expenseRecordAssetsRe) {
        return R.ok(expenseRecordAssetsReService.page(page, Wrappers.query(expenseRecordAssetsRe)));
    }


    /**
     * 通过id查询产生费用财产关联表
     * @param expenseRecordAssetsReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{expenseRecordAssetsReId}" )
    @PreAuthorize("@pms.hasPermission('casee_expenserecordassetsre_get')" )
    public R getById(@PathVariable("expenseRecordAssetsReId" ) Integer expenseRecordAssetsReId) {
        return R.ok(expenseRecordAssetsReService.getById(expenseRecordAssetsReId));
    }

    /**
     * 新增产生费用财产关联表
     * @param expenseRecordAssetsRe 产生费用财产关联表
     * @return R
     */
    @ApiOperation(value = "新增产生费用财产关联表", notes = "新增产生费用财产关联表")
    @SysLog("新增产生费用财产关联表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_expenserecordassetsre_add')" )
    public R save(@RequestBody ExpenseRecordAssetsRe expenseRecordAssetsRe) {
        return R.ok(expenseRecordAssetsReService.save(expenseRecordAssetsRe));
    }

    /**
     * 修改产生费用财产关联表
     * @param expenseRecordAssetsRe 产生费用财产关联表
     * @return R
     */
    @ApiOperation(value = "修改产生费用财产关联表", notes = "修改产生费用财产关联表")
    @SysLog("修改产生费用财产关联表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_expenserecordassetsre_edit')" )
    public R updateById(@RequestBody ExpenseRecordAssetsRe expenseRecordAssetsRe) {
        return R.ok(expenseRecordAssetsReService.updateById(expenseRecordAssetsRe));
    }

    /**
     * 通过id删除产生费用财产关联表
     * @param expenseRecordAssetsReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除产生费用财产关联表", notes = "通过id删除产生费用财产关联表")
    @SysLog("通过id删除产生费用财产关联表" )
    @DeleteMapping("/{expenseRecordAssetsReId}" )
    @PreAuthorize("@pms.hasPermission('casee_expenserecordassetsre_del')" )
    public R removeById(@PathVariable Integer expenseRecordAssetsReId) {
        return R.ok(expenseRecordAssetsReService.removeById(expenseRecordAssetsReId));
    }

	/**
	 * 根据费用产生记录id查询财产关联信息
	 * @param expenseRecordId
	 * @return R
	 */
	@ApiOperation(value = "根据费用产生记录id查询财产关联信息", notes = "根据费用产生记录id查询财产关联信息")
	@GetMapping("/queryAssetsList/{expenseRecordId}" )
	public R queryAssetsList(@PathVariable Integer expenseRecordId) {
		return R.ok(expenseRecordAssetsReService.queryAssetsList(expenseRecordId));
	}

	/**
	 * 根据项目id和费用类型查询未还款的财产关联id
	 * @param projectId
	 * @return R
	 */
	@ApiOperation(value = "根据项目id和费用类型查询未还款的财产关联id", notes = "根据项目id和费用类型查询未还款的财产关联id")
	@GetMapping("/queryAssetsReList" )
	public R queryAssetsReList(Integer projectId,Integer costType,@RequestParam(value = "assetsName",required = false) String assetsName) {
		return R.ok(expenseRecordAssetsReService.queryAssetsReList(projectId,costType,assetsName));
	}
}
