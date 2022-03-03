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
import com.pig4cloud.pig.casee.entity.FulfillmentRecords;
import com.pig4cloud.pig.casee.service.FulfillmentRecordsService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 待履行记录表
 *
 * @author Mjh
 * @date 2022-03-01 20:36:31
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/fulfillmentrecords" )
@Api(value = "fulfillmentrecords", tags = "待履行记录表管理")
public class FulfillmentRecordsController {

    private final FulfillmentRecordsService fulfillmentRecordsService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param fulfillmentRecords 待履行记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getFulfillmentRecordsPage(Page page, FulfillmentRecords fulfillmentRecords) {
		return R.ok(fulfillmentRecordsService.page(page, Wrappers.query(fulfillmentRecords)));
    }

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param reconciliatioMediationId 和解/调解id
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/getFulfillmentRecordsPage" )
	public R getFulfillmentRecordsPage(Page page, Integer reconciliatioMediationId) {
		return R.ok(fulfillmentRecordsService.getFulfillmentRecordsPage(page, reconciliatioMediationId));
	}

    /**
     * 通过id查询待履行记录表
     * @param pendingRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{pendingRecordId}" )
    public R getById(@PathVariable("pendingRecordId" ) Integer pendingRecordId) {
        return R.ok(fulfillmentRecordsService.getById(pendingRecordId));
    }

    /**
     * 新增待履行记录表
     * @param fulfillmentRecords 待履行记录表
     * @return R
     */
    @ApiOperation(value = "新增待履行记录表", notes = "新增待履行记录表")
    @SysLog("新增待履行记录表" )
    @PostMapping
    public R save(@RequestBody FulfillmentRecords fulfillmentRecords) {
        return R.ok(fulfillmentRecordsService.save(fulfillmentRecords));
    }

    /**
     * 修改待履行记录表
     * @param fulfillmentRecords 待履行记录表
     * @return R
     */
    @ApiOperation(value = "修改待履行记录表", notes = "修改待履行记录表")
    @SysLog("修改待履行记录表" )
    @PutMapping
    public R updateById(@RequestBody FulfillmentRecords fulfillmentRecords) {
        return R.ok(fulfillmentRecordsService.updateById(fulfillmentRecords));
    }

    /**
     * 通过id删除待履行记录表
     * @param pendingRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id删除待履行记录表", notes = "通过id删除待履行记录表")
    @SysLog("通过id删除待履行记录表" )
    @DeleteMapping("/{pendingRecordId}" )
    public R removeById(@PathVariable Integer pendingRecordId) {
        return R.ok(fulfillmentRecordsService.removeById(pendingRecordId));
    }

}
