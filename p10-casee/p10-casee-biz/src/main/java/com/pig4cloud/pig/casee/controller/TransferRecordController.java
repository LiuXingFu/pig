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
import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.service.TransferRecordService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 移交记录表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:40
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/transferrecord" )
@Api(value = "transferrecord", tags = "移交记录表管理")
public class TransferRecordController {

    private final TransferRecordService transferRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param transferRecord 移交记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTransferRecordPage(Page page, TransferRecord transferRecord) {
        return R.ok(transferRecordService.page(page, Wrappers.query(transferRecord)));
    }


    /**
     * 通过id查询移交记录表
     * @param transferRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{transferRecordId}" )
    public R getById(@PathVariable("transferRecordId" ) Integer transferRecordId) {
        return R.ok(transferRecordService.getById(transferRecordId));
    }

    /**
     * 新增移交记录表
     * @param transferRecord 移交记录表
     * @return R
     */
    @ApiOperation(value = "新增移交记录表", notes = "新增移交记录表")
    @SysLog("新增移交记录表" )
    @PostMapping
    public R save(@RequestBody TransferRecord transferRecord) {
        return R.ok(transferRecordService.save(transferRecord));
    }

    /**
     * 修改移交记录表
     * @param transferRecord 移交记录表
     * @return R
     */
    @ApiOperation(value = "修改移交记录表", notes = "修改移交记录表")
    @SysLog("修改移交记录表" )
    @PutMapping
    public R updateById(@RequestBody TransferRecord transferRecord) {
        return R.ok(transferRecordService.updateById(transferRecord));
    }

    /**
     * 通过id删除移交记录表
     * @param transferRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id删除移交记录表", notes = "通过id删除移交记录表")
    @SysLog("通过id删除移交记录表" )
    @DeleteMapping("/{transferRecordId}" )
    public R removeById(@PathVariable Integer transferRecordId) {
        return R.ok(transferRecordService.removeById(transferRecordId));
    }

}
