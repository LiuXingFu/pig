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
import com.pig4cloud.pig.casee.entity.DeadlineConfigure;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.service.DeadlineConfigureService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;


/**
 * 期限配置表
 *
 * @author yuanduo
 * @date 2022-02-11 21:13:38
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/deadlineconfigure" )
@Api(value = "deadlineconfigure", tags = "期限配置表管理")
public class DeadlineConfigureController {

    private final  DeadlineConfigureService deadlineConfigureService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param deadlineConfigure 期限配置表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getDeadlineConfigurePage(Page page, DeadlineConfigure deadlineConfigure) {
        return R.ok(deadlineConfigureService.getDeadlineConfigurePage(page, deadlineConfigure));
    }


    /**
     * 通过id查询期限配置表
     * @param periodConfigureId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{periodConfigureId}" )
    public R getById(@PathVariable("periodConfigureId" ) Integer periodConfigureId) {
        return R.ok(deadlineConfigureService.getById(periodConfigureId));
    }

    /**
     * 新增期限配置表
     * @param deadlineConfigure 期限配置表
     * @return R
     */
    @ApiOperation(value = "新增期限配置表", notes = "新增期限配置表")
    @SysLog("新增期限配置表" )
    @PostMapping
	@Transactional
    public R save(@RequestBody DeadlineConfigure deadlineConfigure) {
        return R.ok(deadlineConfigureService.save(deadlineConfigure));
    }

    /**
     * 修改期限配置表
     * @param deadlineConfigure 期限配置表
     * @return R
     */
    @ApiOperation(value = "修改期限配置表", notes = "修改期限配置表")
    @SysLog("修改期限配置表" )
    @PutMapping
	@Transactional
    public R updateById(@RequestBody DeadlineConfigure deadlineConfigure) {
        return R.ok(deadlineConfigureService.updateById(deadlineConfigure));
    }

    /**
     * 通过id删除期限配置表
     * @param periodConfigureId id
     * @return R
     */
    @ApiOperation(value = "通过id删除期限配置表", notes = "通过id删除期限配置表")
    @SysLog("通过id删除期限配置表" )
    @DeleteMapping("/{periodConfigureId}" )
	@Transactional
    public R removeById(@PathVariable Integer periodConfigureId) {
		boolean b = deadlineConfigureService.removeById(periodConfigureId);
		if (b) {
			return R.ok("移除成功！");
		} else {
			return R.failed("移除失败!");
		}
    }

	/**
	 * 通过id恢复期限配置
	 * @param periodConfigureId id
	 * @return R
	 */
	@ApiOperation(value = "通过id恢复期限配置", notes = "通过id恢复期限配置")
	@SysLog("通过id恢复期限配置" )
	@PutMapping("/recoverById/{periodConfigureId}" )
    public R recoverById(@PathVariable Integer periodConfigureId) {
		boolean b = this.deadlineConfigureService.recoverById(periodConfigureId);
		if(b) {
			return R.ok("恢复成功！");
		} else {
			return R.failed("恢复失败！");
		}
	}

}
