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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.MortgageAssetsDTO;
import com.pig4cloud.pig.casee.entity.MortgageAssetsRe;
import com.pig4cloud.pig.casee.entity.MortgageAssetsSubjectRe;
import com.pig4cloud.pig.casee.service.MortgageAssetsReService;
import com.pig4cloud.pig.casee.service.MortgageAssetsSubjectReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.entity.MortgageAssetsRecords;
import com.pig4cloud.pig.casee.service.MortgageAssetsRecordsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 抵押记录表
 *
 * @author Mjh
 * @date 2022-04-13 11:24:18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/mortgageassetsrecords" )
@Api(value = "mortgageassetsrecords", tags = "抵押记录表管理")
public class MortgageAssetsRecordsController {

	private final  MortgageAssetsRecordsService mortgageAssetsRecordsService;

	private final MortgageAssetsReService mortgageAssetsReService;

	private final MortgageAssetsSubjectReService mortgageAssetsSubjectReService;


    /**
     * 分页查询
     * @param page 分页对象
     * @param mortgageAssetsRecords 抵押记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getMortgageAssetsRecordsPage(Page page, MortgageAssetsRecords mortgageAssetsRecords) {
        return R.ok(mortgageAssetsRecordsService.page(page, Wrappers.query(mortgageAssetsRecords)));
    }


    /**
     * 通过id查询抵押记录表
     * @param mortgageAssetsRecordsId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{mortgageAssetsRecordsId}" )
    public R getById(@PathVariable("mortgageAssetsRecordsId" ) Integer mortgageAssetsRecordsId) {
        return R.ok(mortgageAssetsRecordsService.getById(mortgageAssetsRecordsId));
    }

	/**
	 * 通过id查询抵押记录以及财产主体信息
	 * @param mortgageAssetsRecordsId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getByMortgageAssetsRecordsId/{mortgageAssetsRecordsId}" )
	public R getByMortgageAssetsRecordsId(@PathVariable("mortgageAssetsRecordsId" ) Integer mortgageAssetsRecordsId) {
		return R.ok(mortgageAssetsRecordsService.getByMortgageAssetsRecordsId(mortgageAssetsRecordsId));
	}

	/**
     * 新增抵押记录表
     * @param mortgageAssetsRecords 抵押记录表
     * @return R
     */
    @ApiOperation(value = "新增抵押记录表", notes = "新增抵押记录表")
    @SysLog("新增抵押记录表" )
    @PostMapping
    public R save(@RequestBody MortgageAssetsRecords mortgageAssetsRecords) {
        return R.ok(mortgageAssetsRecordsService.save(mortgageAssetsRecords));
    }

    /**
     * 修改抵押记录表
     * @param mortgageAssetsRecords 抵押记录表
     * @return R
     */
    @ApiOperation(value = "修改抵押记录表", notes = "修改抵押记录表")
    @SysLog("修改抵押记录表" )
    @PutMapping
    public R updateById(@RequestBody MortgageAssetsRecords mortgageAssetsRecords) {
        return R.ok(mortgageAssetsRecordsService.updateById(mortgageAssetsRecords));
    }

	/**
	 * 修改抵押财产信息
	 * @param mortgageAssetsDTO 修改抵押财产信息
	 * @return R
	 */
	@ApiOperation(value = "修改抵押财产信息", notes = "修改抵押财产信息")
	@SysLog("修改抵押财产信息" )
	@PutMapping("/updateByMortgageAssets")
	public R updateByMortgageAssets(@RequestBody MortgageAssetsDTO mortgageAssetsDTO) {
		return R.ok(mortgageAssetsRecordsService.updateByMortgageAssets(mortgageAssetsDTO));
	}

    /**
     * 通过id删除抵押记录表
     * @param mortgageAssetsRecordsId id
     * @return R
     */
    @ApiOperation(value = "通过id删除抵押记录表", notes = "通过id删除抵押记录表")
    @SysLog("通过id删除抵押记录表" )
    @DeleteMapping("/{mortgageAssetsRecordsId}" )
    public R removeById(@PathVariable Integer mortgageAssetsRecordsId) {
        return R.ok(mortgageAssetsRecordsService.removeById(mortgageAssetsRecordsId));
    }

	/**
	 * 通过id删除抵押记录以及财产和主体信息
	 * @param mortgageAssetsRecordsId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除抵押记录以及财产和主体信息", notes = "通过id删除抵押记录以及财产和主体信息")
	@SysLog("通过id删除抵押记录以及财产和主体信息" )
	@DeleteMapping("/removeMortgageAssetsRecords/{mortgageAssetsRecordsId}" )
	public R removeMortgageAssetsRecords(@PathVariable Integer mortgageAssetsRecordsId) {
		List<MortgageAssetsRe> list = mortgageAssetsReService.list(new LambdaQueryWrapper<MortgageAssetsRe>().eq(MortgageAssetsRe::getMortgageAssetsRecordsId, mortgageAssetsRecordsId));
		for (MortgageAssetsRe mortgageAssetsRe : list) {
			//删除财产关联债务人信息
			mortgageAssetsSubjectReService.remove(new LambdaQueryWrapper<MortgageAssetsSubjectRe>().eq(MortgageAssetsSubjectRe::getMortgageAssetsReId,mortgageAssetsRe.getMortgageAssetsReId()));
		}
		//删除抵押记录信息关联财产
		mortgageAssetsReService.remove(new LambdaQueryWrapper<MortgageAssetsRe>().eq(MortgageAssetsRe::getMortgageAssetsRecordsId,mortgageAssetsRecordsId));

		return R.ok(mortgageAssetsRecordsService.removeById(mortgageAssetsRecordsId));
	}

}
