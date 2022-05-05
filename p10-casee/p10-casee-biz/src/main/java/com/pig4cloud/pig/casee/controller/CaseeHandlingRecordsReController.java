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
import com.pig4cloud.pig.casee.entity.CaseeHandlingRecordsRe;
import com.pig4cloud.pig.casee.service.CaseeHandlingRecordsReService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 案件办理记录表
 *
 * @author yuanduo
 * @date 2022-05-05 16:34:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/caseehandlingrecordsre" )
@Api(value = "caseehandlingrecordsre", tags = "案件办理记录表管理")
public class CaseeHandlingRecordsReController {

    private final  CaseeHandlingRecordsReService caseeHandlingRecordsReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param caseeHandlingRecordsRe 案件办理记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCaseeHandlingRecordsRePage(Page page, CaseeHandlingRecordsRe caseeHandlingRecordsRe) {
        return R.ok(caseeHandlingRecordsReService.page(page, Wrappers.query(caseeHandlingRecordsRe)));
    }


    /**
     * 通过id查询案件办理记录表
     * @param caseeHandlingRecordsId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{caseeHandlingRecordsId}" )
    public R getById(@PathVariable("caseeHandlingRecordsId" ) Integer caseeHandlingRecordsId) {
        return R.ok(caseeHandlingRecordsReService.getById(caseeHandlingRecordsId));
    }

    /**
     * 新增案件办理记录表
     * @param caseeHandlingRecordsRe 案件办理记录表
     * @return R
     */
    @ApiOperation(value = "新增案件办理记录表", notes = "新增案件办理记录表")
    @SysLog("新增案件办理记录表" )
    @PostMapping
    public R save(@RequestBody CaseeHandlingRecordsRe caseeHandlingRecordsRe) {
        return R.ok(caseeHandlingRecordsReService.save(caseeHandlingRecordsRe));
    }

    /**
     * 修改案件办理记录表
     * @param caseeHandlingRecordsRe 案件办理记录表
     * @return R
     */
    @ApiOperation(value = "修改案件办理记录表", notes = "修改案件办理记录表")
    @SysLog("修改案件办理记录表" )
    @PutMapping
    public R updateById(@RequestBody CaseeHandlingRecordsRe caseeHandlingRecordsRe) {
        return R.ok(caseeHandlingRecordsReService.updateById(caseeHandlingRecordsRe));
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
        return R.ok(caseeHandlingRecordsReService.removeById(caseeHandlingRecordsId));
    }

}
