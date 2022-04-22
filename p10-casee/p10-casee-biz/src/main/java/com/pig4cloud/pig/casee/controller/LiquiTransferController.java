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
import com.pig4cloud.pig.casee.dto.LiquiTransferPageDTO;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.entity.LiquiTransfer;
import com.pig4cloud.pig.casee.service.LiquiTransferService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 清收移交表
 *
 * @author yuanduo
 * @date 2022-04-21 15:39:01
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/liquitransfer" )
@Api(value = "liquitransfer", tags = "清收移交表管理")
public class LiquiTransferController {

    private final  LiquiTransferService liquiTransferService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param liquiTransfer 清收移交表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getLiquiTransferPage(Page page, LiquiTransfer liquiTransfer) {
        return R.ok(liquiTransferService.page(page, Wrappers.query(liquiTransfer)));
    }


    /**
     * 通过id查询清收移交表
     * @param liquiTransferId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{liquiTransferId}" )
    public R getById(@PathVariable("liquiTransferId" ) Integer liquiTransferId) {
        return R.ok(liquiTransferService.getById(liquiTransferId));
    }

    /**
     * 新增清收移交表
     * @param liquiTransfer 清收移交表
     * @return R
     */
    @ApiOperation(value = "新增清收移交表", notes = "新增清收移交表")
    @SysLog("新增清收移交表" )
    @PostMapping
    public R save(@RequestBody LiquiTransfer liquiTransfer) {
        return R.ok(liquiTransferService.save(liquiTransfer));
    }

    /**
     * 修改清收移交表
     * @param liquiTransfer 清收移交表
     * @return R
     */
    @ApiOperation(value = "修改清收移交表", notes = "修改清收移交表")
    @SysLog("修改清收移交表" )
    @PutMapping
    public R updateById(@RequestBody LiquiTransfer liquiTransfer) {
        return R.ok(liquiTransferService.updateById(liquiTransfer));
    }

    /**
     * 通过id删除清收移交表
     * @param liquiTransferId id
     * @return R
     */
    @ApiOperation(value = "通过id删除清收移交表", notes = "通过id删除清收移交表")
    @SysLog("通过id删除清收移交表" )
    @DeleteMapping("/{liquiTransferId}" )
    public R removeById(@PathVariable Integer liquiTransferId) {
        return R.ok(liquiTransferService.removeById(liquiTransferId));
    }

	/**
	 * 分页查询清收移交信息
	 * @param page 分页对象
	 * @param liquiTransferPageDTO
	 * @return
	 */
	@ApiOperation(value = "分页查询清收移交信息", notes = "分页查询清收移交信息")
	@GetMapping("/getLiquiTransferPage" )
	public R getLiquiTransferPage(Page page, LiquiTransferPageDTO liquiTransferPageDTO) {
		return R.ok(liquiTransferService.getLiquiTransferPage(page, liquiTransferPageDTO));
	}

}
