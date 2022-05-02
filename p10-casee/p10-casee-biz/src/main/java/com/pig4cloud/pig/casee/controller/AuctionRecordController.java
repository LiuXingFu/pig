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
import com.pig4cloud.pig.casee.dto.paifu.AuctionRecordStatusSaveDTO;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.entity.AuctionRecord;
import com.pig4cloud.pig.casee.service.AuctionRecordService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 拍卖记录表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/auctionrecord" )
@Api(value = "auctionrecord", tags = "拍卖记录表管理")
public class AuctionRecordController {

    private final  AuctionRecordService auctionRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param auctionRecord 拍卖记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_auctionrecord_get')" )
    public R getAuctionRecordPage(Page page, AuctionRecord auctionRecord) {
        return R.ok(auctionRecordService.page(page, Wrappers.query(auctionRecord)));
    }


    /**
     * 通过id查询拍卖记录表
     * @param auctionRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{auctionRecordId}" )
    @PreAuthorize("@pms.hasPermission('casee_auctionrecord_get')" )
    public R getById(@PathVariable("auctionRecordId" ) Integer auctionRecordId) {
        return R.ok(auctionRecordService.getById(auctionRecordId));
    }

    /**
     * 新增拍卖记录表
     * @param auctionRecord 拍卖记录表
     * @return R
     */
    @ApiOperation(value = "新增拍卖记录表", notes = "新增拍卖记录表")
    @SysLog("新增拍卖记录表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_auctionrecord_add')" )
    public R save(@RequestBody AuctionRecord auctionRecord) {
        return R.ok(auctionRecordService.save(auctionRecord));
    }

    /**
     * 修改拍卖记录表
     * @param auctionRecord 拍卖记录表
     * @return R
     */
    @ApiOperation(value = "修改拍卖记录表", notes = "修改拍卖记录表")
    @SysLog("修改拍卖记录表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_auctionrecord_edit')" )
    public R updateById(@RequestBody AuctionRecord auctionRecord) {
        return R.ok(auctionRecordService.updateById(auctionRecord));
    }

    /**
     * 通过id删除拍卖记录表
     * @param auctionRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id删除拍卖记录表", notes = "通过id删除拍卖记录表")
    @SysLog("通过id删除拍卖记录表" )
    @DeleteMapping("/{auctionRecordId}" )
    @PreAuthorize("@pms.hasPermission('casee_auctionrecord_del')" )
    public R removeById(@PathVariable Integer auctionRecordId) {
        return R.ok(auctionRecordService.removeById(auctionRecordId));
    }

	/**
	 * 根据项目id、案件id、财产id查询最后一条拍卖记录
	 * @return R
	 */
	@ApiOperation(value = "根据项目id、案件id、财产id查询最后一条拍卖记录", notes = "根据项目id、案件id、财产id查询最后一条拍卖记录")
	@GetMapping("/getLastAuctionRecord" )
	public R getLastAuctionRecord(Integer projectId,Integer caseeId,Integer assetsId) {
		return R.ok(auctionRecordService.getLastAuctionRecord(projectId,caseeId,assetsId));
	}

	/**
	 * 撤销拍卖记录
	 * @param auctionRecordStatusSaveDTO
	 * @return R
	 */
	@ApiOperation(value = "撤销拍卖记录", notes = "撤销拍卖记录")
	@SysLog("撤销拍卖记录" )
	@PutMapping("/revokeAuctionRecord")
	public R revokeAuctionRecord(@RequestBody AuctionRecordStatusSaveDTO auctionRecordStatusSaveDTO) {
		return R.ok(auctionRecordService.revokeAuctionRecord(auctionRecordStatusSaveDTO));
	}

}
