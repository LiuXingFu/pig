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
import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.casee.dto.CaseePerformanceDTO;
import com.pig4cloud.pig.casee.dto.CaseePerformanceSumDTO;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.entity.CaseePerformance;
import com.pig4cloud.pig.casee.service.CaseePerformanceService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 案件绩效表
 *
 * @author xiaojun
 * @date 2021-09-07 17:44:19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/caseeperformance" )
@Api(value = "caseeperformance", tags = "案件绩效表管理")
public class CaseePerformanceController {

    private final  CaseePerformanceService caseePerformanceService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param caseePerformance 案件绩效表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCaseePerformancePage(Page page, CaseePerformance caseePerformance) {
        return R.ok(caseePerformanceService.page(page, Wrappers.query(caseePerformance)));
    }


    /**
     * 通过id查询案件绩效表
     * @param perfId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{perfId}" )
    public R getById(@PathVariable("perfId" ) Integer perfId) {
        return R.ok(caseePerformanceService.getById(perfId));
    }

    /**
     * 新增案件绩效表
     * @param caseePerformance 案件绩效表
     * @return R
     */
    @ApiOperation(value = "新增案件绩效表", notes = "新增案件绩效表")
    @SysLog("新增案件绩效表" )
    @PostMapping
    public R save(@RequestBody CaseePerformance caseePerformance) {
        return R.ok(caseePerformanceService.save(caseePerformance));
    }

    /**
     * 修改案件绩效表
     * @param caseePerformance 案件绩效表
     * @return R
     */
    @ApiOperation(value = "修改案件绩效表", notes = "修改案件绩效表")
    @SysLog("修改案件绩效表" )
    @PutMapping
    public R updateById(@RequestBody CaseePerformance caseePerformance) {
        return R.ok(caseePerformanceService.updateById(caseePerformance));
    }

    /**
     * 通过id删除案件绩效表
     * @param perfId id
     * @return R
     */
    @ApiOperation(value = "通过id删除案件绩效表", notes = "通过id删除案件绩效表")
    @SysLog("通过id删除案件绩效表" )
    @DeleteMapping("/{perfId}" )
    public R removeById(@PathVariable Integer perfId) {
        return R.ok(caseePerformanceService.removeById(perfId));
    }

	/**
	 * 查询标的绩效
	 * @param targetId 标的id
	 * @return
	 */
	@ApiOperation(value = "查询标的绩效", notes = "查询标的绩效")
	@GetMapping("/getPerformanceByTargetId" )
	public R getPerformanceByTargetId(Integer targetId) {

		return R.ok(caseePerformanceService.getPerformanceByTargetId(targetId));
	}
	/**
	 * 根据用户查询绩效列表
	 * @param caseePerformanceDTO 标的id
	 * @return
	 */
	@ApiOperation(value = "查询标的绩效", notes = "查询标的绩效")
	@PostMapping("/getPerformanceList" )
	public R getPerformanceList(@RequestBody CaseePerformanceDTO caseePerformanceDTO) {

		caseePerformanceDTO.setUserId(SecurityUtils.getUser().getId());
		return R.ok(caseePerformanceService.getPerformanceList(caseePerformanceDTO.getPage(),caseePerformanceDTO));
	}

	/**
	 * 根据用户查询绩效列表
	 * @param caseePerformanceDTO 标的id
	 * @return
	 */
	@ApiOperation(value = "查询标的绩效(按日期分组)", notes = "查询标的绩效(按日期分组)")
	@PostMapping("/getPerformanceSumList" )
	public R getPerformanceSumList(@RequestBody CaseePerformanceSumDTO caseePerformanceDTO) {

		caseePerformanceDTO.setUserId(SecurityUtils.getUser().getId());
		return R.ok(caseePerformanceService.getPerformanceSumList(caseePerformanceDTO.getPage(),caseePerformanceDTO));
	}

	/**
	 * 根据用户条件查询绩效合计
	 * @param caseePerformanceDTO 查询条件
	 * @return
	 */
	@ApiOperation(value = "根据用户条件查询绩效合计", notes = "根据用户条件查询绩效合计")
	@PostMapping("/getPerformanceSum" )
	public R getPerformanceSum(@RequestBody CaseePerformanceSumDTO caseePerformanceDTO) {

		caseePerformanceDTO.setUserId(SecurityUtils.getUser().getId());
		return R.ok(caseePerformanceService.getPerformanceSum(caseePerformanceDTO));
	}

	/**
	 * 根据绩效id获取绩效详情
	 * @param perfId 查询条件
	 * @return
	 */
	@ApiOperation(value = "根据绩效id获取绩效详情", notes = "根据绩效id获取绩效详情")
	@GetMapping("/getPerformanceById" )
	public R getPerformanceById(String perfId) {
		return R.ok(caseePerformanceService.getPerformanceById(perfId));
	}
}
