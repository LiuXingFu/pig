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
import com.pig4cloud.pig.casee.dto.CaseeHandlingRecordsDTO;
import com.pig4cloud.pig.casee.entity.CaseeHandlingRecords;
import com.pig4cloud.pig.casee.service.CaseeHandlingRecordsService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 案件办理记录表
 *
 * @author Mjh
 * @date 2022-03-10 18:05:58
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/casehandlingrecords" )
@Api(value = "casehandlingrecords", tags = "案件办理记录表管理")
public class CaseeHandlingRecordsController {

    private final CaseeHandlingRecordsService caseeHandlingRecordsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param caseHandlingRecords 案件办理记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCaseHandlingRecordsPage(Page page, CaseeHandlingRecords caseHandlingRecords) {
        return R.ok(caseeHandlingRecordsService.page(page, Wrappers.query(caseHandlingRecords)));
    }


    /**
     * 通过id查询案件办理记录表
     * @param caseeHandlingRecordsId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{caseeHandlingRecordsId}" )
    public R getById(@PathVariable("caseeHandlingRecordsId" ) Integer caseeHandlingRecordsId) {
        return R.ok(caseeHandlingRecordsService.getById(caseeHandlingRecordsId));
    }

	/**
	 * 查询案件办理记录表
	 * @param caseeHandlingRecordsDTO
	 * @return R
	 */
	@ApiOperation(value = "查询案件办理记录表", notes = "查询案件办理记录表")
	@GetMapping("/queryCaseeHandlingRecords" )
	public R queryCaseeHandlingRecords(CaseeHandlingRecordsDTO caseeHandlingRecordsDTO) {
		return R.ok(caseeHandlingRecordsService.queryCaseeHandlingRecords(caseeHandlingRecordsDTO));
	}

    /**
     * 新增案件办理记录表
     * @param caseeHandlingRecords 案件办理记录表
     * @return R
     */
    @ApiOperation(value = "新增案件办理记录表", notes = "新增案件办理记录表")
    @SysLog("新增案件办理记录表" )
    @PostMapping
    public R save(@RequestBody CaseeHandlingRecords caseeHandlingRecords) {
        return R.ok(caseeHandlingRecordsService.save(caseeHandlingRecords));
    }

    /**
     * 修改案件办理记录表
     * @param caseeHandlingRecords 案件办理记录表
     * @return R
     */
    @ApiOperation(value = "修改案件办理记录表", notes = "修改案件办理记录表")
    @SysLog("修改案件办理记录表" )
    @PutMapping
    public R updateById(@RequestBody CaseeHandlingRecords caseeHandlingRecords) {
        return R.ok(caseeHandlingRecordsService.updateById(caseeHandlingRecords));
    }

    /**
     * 通过id删除案件办理记录表
     * @param caseeHandlingRecordsId id
     * @return R
     */
    @ApiOperation(value = "通过id删除案件办理记录表", notes = "通过id删除案件办理记录表")
    @SysLog("通过id删除案件办理记录表" )
    @DeleteMapping("/{caseeHandlingRecordsId}" )
    public R removeById(@PathVariable Integer caseeHandlingRecordsId) {
        return R.ok(caseeHandlingRecordsService.removeById(caseeHandlingRecordsId));
    }

}
