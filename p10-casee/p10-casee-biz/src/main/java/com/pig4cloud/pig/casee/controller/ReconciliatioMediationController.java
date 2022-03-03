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
import com.pig4cloud.pig.casee.dto.ReconciliatioMediationDTO;
import com.pig4cloud.pig.casee.entity.ReconciliatioMediation;
import com.pig4cloud.pig.casee.service.ReconciliatioMediationService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 和解/调解表
 *
 * @author Mjh
 * @date 2022-03-01 20:36:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/reconciliatiomediation" )
@Api(value = "reconciliatiomediation", tags = "和解/调解表管理")
public class ReconciliatioMediationController {

    private final ReconciliatioMediationService reconciliatioMediationService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param reconciliatioMediationDTO 和解/调解表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getReconciliatioMediationPage(Page page, ReconciliatioMediationDTO reconciliatioMediationDTO) {
        return R.ok(reconciliatioMediationService.getReconciliatioMediationPage(page, reconciliatioMediationDTO));
    }


    /**
     * 通过id查询和解/调解表
     * @param reconciliatioMediationId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{reconciliatioMediationId}" )
    public R getById(@PathVariable("reconciliatioMediationId" ) Integer reconciliatioMediationId) {
        return R.ok(reconciliatioMediationService.getById(reconciliatioMediationId));
    }

	/**
	 * 通过id查询和解/调解表
	 * @param reconciliatioMediationId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getByReconciliatioMediationId/{reconciliatioMediationId}" )
	public R getByReconciliatioMediationId(@PathVariable("reconciliatioMediationId" ) Integer reconciliatioMediationId) {
		return R.ok(reconciliatioMediationService.getByReconciliatioMediationId(reconciliatioMediationId));
	}

    /**
     * 新增和解/调解表
     * @param reconciliatioMediation 和解/调解表
     * @return R
     */
    @ApiOperation(value = "新增和解/调解表", notes = "新增和解/调解表")
    @SysLog("新增和解/调解表" )
    @PostMapping
    public R save(@RequestBody ReconciliatioMediation reconciliatioMediation) {
        return R.ok(reconciliatioMediationService.save(reconciliatioMediation));
    }

	/**
	 * 新增和解/调解待履行记录信息
	 * @param reconciliatioMediationDTO
	 * @return R
	 */
	@ApiOperation(value = "新增和解/调解待履行记录信息", notes = "新增和解/调解待履行记录信息")
	@SysLog("新增和解/调解待履行记录信息" )
	@PostMapping("/saveReconciliatioMediation")
	public R saveReconciliatioMediation(@RequestBody ReconciliatioMediationDTO reconciliatioMediationDTO) {
		return R.ok(reconciliatioMediationService.saveReconciliatioMediation(reconciliatioMediationDTO));
	}



    /**
     * 修改和解/调解表
     * @param reconciliatioMediation 和解/调解表
     * @return R
     */
    @ApiOperation(value = "修改和解/调解表", notes = "修改和解/调解表")
    @SysLog("修改和解/调解表" )
    @PutMapping
    public R updateById(@RequestBody ReconciliatioMediation reconciliatioMediation) {
        return R.ok(reconciliatioMediationService.updateById(reconciliatioMediation));
    }

    /**
     * 通过id删除和解/调解表
     * @param reconciliatioMediationId id
     * @return R
     */
    @ApiOperation(value = "通过id删除和解/调解表", notes = "通过id删除和解/调解表")
    @SysLog("通过id删除和解/调解表" )
    @DeleteMapping("/{reconciliatioMediationId}" )
    public R removeById(@PathVariable Integer reconciliatioMediationId) {
        return R.ok(reconciliatioMediationService.removeById(reconciliatioMediationId));
    }

}
