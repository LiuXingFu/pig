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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.entity.LiquiTransferRecord;
import com.pig4cloud.pig.casee.service.LiquiTransferRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 清收移交记录表
 *
 * @author Mjh
 * @date 2022-04-06 15:21:31
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/liquitransferrecord" )
@Api(value = "liquitransferrecord", tags = "清收移交记录表管理")
public class LiquiTransferRecordController {

    private final  LiquiTransferRecordService liquiTransferRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param liquiTransferRecord 清收移交记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getLiquiTransferRecordPage(Page page, LiquiTransferRecord liquiTransferRecord) {
        return R.ok(liquiTransferRecordService.queryLiquiTransferRecordPage(page,liquiTransferRecord));
    }


    /**
     * 通过id查询清收移交记录表
     * @param liquiTransferRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{liquiTransferRecordId}" )
    public R getById(@PathVariable("liquiTransferRecordId" ) Integer liquiTransferRecordId) {
        return R.ok(liquiTransferRecordService.getById(liquiTransferRecordId));
    }

	/**
	 * 通过id查询清收移交记录以及财产信息
	 * @param liquiTransferRecordId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询清收移交记录以及财产信息", notes = "通过id查询清收移交记录以及财产信息")
	@GetMapping("/getByLiquiTransferRecordId/{liquiTransferRecordId}" )
	public R getByLiquiTransferRecordId(@PathVariable("liquiTransferRecordId" ) Integer liquiTransferRecordId) {
		return R.ok(liquiTransferRecordService.getByLiquiTransferRecordId(liquiTransferRecordId));
	}

    /**
     * 新增清收移交记录表
     * @param liquiTransferRecord 清收移交记录表
     * @return R
     */
    @ApiOperation(value = "新增清收移交记录表", notes = "新增清收移交记录表")
    @SysLog("新增清收移交记录表" )
    @PostMapping
    public R save(@RequestBody LiquiTransferRecord liquiTransferRecord) {
        return R.ok(liquiTransferRecordService.save(liquiTransferRecord));
    }

    /**
     * 修改清收移交记录表
     * @param liquiTransferRecord 清收移交记录表
     * @return R
     */
    @ApiOperation(value = "修改清收移交记录表", notes = "修改清收移交记录表")
    @SysLog("修改清收移交记录表" )
    @PutMapping
    public R updateById(@RequestBody LiquiTransferRecord liquiTransferRecord) {
        return R.ok(liquiTransferRecordService.updateById(liquiTransferRecord));
    }

    /**
     * 通过id删除清收移交记录表
     * @param liquiTransferRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id删除清收移交记录表", notes = "通过id删除清收移交记录表")
    @SysLog("通过id删除清收移交记录表" )
    @DeleteMapping("/{liquiTransferRecordId}" )
    public R removeById(@PathVariable Integer liquiTransferRecordId) {
        return R.ok(liquiTransferRecordService.removeById(liquiTransferRecordId));
    }

	/**
	 * 根据案件id查询移交记录
	 * @param caseeId
	 * @return
	 */
	@ApiOperation(value = "根据案件id查询移交记录", notes = "根据案件id查询移交记录")
	@GetMapping("/queryTransferRecord/{caseeId}" )
	public R queryTransferRecord(@PathVariable("caseeId") Integer caseeId) {
		return this.liquiTransferRecordService.queryTransferRecord(caseeId);
	}

}
