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


import com.fasterxml.jackson.databind.ObjectMapper;

import com.pig4cloud.pig.casee.dto.CaseeOrTargetTaskFlowDTO;
import com.pig4cloud.pig.casee.dto.TargetAddDTO;
import com.pig4cloud.pig.casee.dto.TargetCloseTimeDTO;
import com.pig4cloud.pig.casee.dto.TargetThingAddDTO;

import com.pig4cloud.pig.casee.entity.TargetBizLiqui;

import com.pig4cloud.pig.casee.entity.detail.TargetThingLiQuiDateDetail;
import com.pig4cloud.pig.casee.service.TargetBizLiquiService;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 清收标的表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/targetBizLiqui" )
@Api(value = "targetBizLiqui", tags = "清收标的表管理")
public class TargetBizLiquiController {

    private final TargetBizLiquiService targetBizLiquiService;

//    /**
//     * 通过id查询标的详情
//     * @param targetId id
//     * @return R
//     */
//    @ApiOperation(value = "通过id查询标的详情", notes = "通过id查询标的详情")
//    @GetMapping("/{targetId}" )
//    public R getById(@PathVariable("targetId" ) Integer targetId) {
//        return R.ok(targetBizLiquiService.queryTargetById(targetId));
//    }
//
//	/**
//	 * 新增标的
//	 *
//	 * @param targetAddDTO 标的表
//	 * @return R
//	 */
//	@ApiOperation(value = "新增标的", notes = "新增标的")
//	@SysLog("新增标的" )
//	@PostMapping("/addTarget")
//	public R addTarget(@RequestBody TargetAddDTO targetAddDTO) {
//		Integer integer = targetBizLiquiService.addTarget(targetAddDTO);
//		if (integer==-1){
//			return R.failed("添加标的失败，请选择网点");
//		}else if (integer==-2){
//			return R.failed("添加标的失败，请检查网点是否添加该标的性质模板");
//		}
//		return R.ok(integer);
//	}
//
//
//	/**
//	 * 新增标的物
//	 *
//	 * @param targetThingAddDTO 新增标的物
//	 * @return R
//	 */
//	@ApiOperation(value = "新增标的物", notes = "新增标的物")
//	@SysLog("新增标的物" )
//	@PostMapping("/addTargetThing")
//	public R addTargetThing(@RequestBody TargetThingAddDTO targetThingAddDTO) {
//		Integer integer = targetBizLiquiService.addTargetThing(targetThingAddDTO);
//		if (integer==-1){
//			return R.failed("添加标的物失败，请选择网点");
//		}else if (integer==-2){
//			return R.failed("添加标的失败，请检查网点是否添加该标的性质模板");
//		}
//		return R.ok(integer);
//	}
//    /**
//     * 修改标的表
//     * @param targetBizLiqui 标的表
//     * @return R
//     */
//    @ApiOperation(value = "修改标的表", notes = "修改标的表")
//    @SysLog("修改标的表" )
//    @PutMapping
//    public R updateById(@RequestBody TargetBizLiqui targetBizLiqui) {
//        return R.ok(targetBizLiquiService.updateById(targetBizLiqui));
//    }
//
//	/**
//	 * 通过id查询标的物详情
//	 * @param targetId id
//	 * @return R
//	 */
//	@ApiOperation(value = "通过id查询标的物详情", notes = "通过id查询标的物详情")
//	@GetMapping("/queryTargetThingById/{targetId}" )
//	public R queryTargetThingById(@PathVariable("targetId" ) Integer targetId) {
//		return R.ok(targetBizLiquiService.queryTargetThingById(targetId));
//	}
//
//	/**
//	 * 修改标的和标的物状态
//	 * @param targetId 标的id
//	 * @param status 状态（101：毁损，102：灭失）
//	 * @param explain 说明
//	 * @return
//	 */
//	@ApiOperation(value = "修改标的和标的物状态", notes = "修改标的和标的物状态")
//	@SysLog("修改标的和标的物状态" )
//	@GetMapping("/modifyStatusById")
//	public R modifyStatusById(  Integer targetId, Integer status,String explain) {
//		return R.ok(targetBizLiquiService.modifyStatusById(targetId,status,explain));
//	}
//
//	/**
//	 * 完成标的和标的物
//	 * @param targetCloseTimeDTO
//	 * @return
//	 */
//	@ApiOperation(value = "完成标的和标的物", notes = "完成标的和标的物")
//	@SysLog("完成标的和标的物" )
//	@PutMapping("/closeTargetById")
//	public R closeTargetById(@RequestBody TargetCloseTimeDTO targetCloseTimeDTO) {
//		return R.ok(targetBizLiquiService.closeTargetById(targetCloseTimeDTO));
//	}
//
//	/**
//	 * 委托方标的状态确认(添加消息中心推送)
//	 * @return R
//	 */
//	@ApiOperation(value = "委托方案件状态确认", notes = "委托方案件状态确认")
//	@SysLog("委托方案件状态确认" )
//	@PutMapping("/confirmCaseStatus")
//	public R confirmCaseStatus(CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO) {
//		Integer statusConfirm=null;
//		if (caseeOrTargetTaskFlowDTO.getStatus()==403){
//			statusConfirm=2;
//		}else if (caseeOrTargetTaskFlowDTO.getStatus()==111){
//			statusConfirm=3;
//		}
//		return R.ok(targetBizLiquiService.confirmCaseStatus(caseeOrTargetTaskFlowDTO,statusConfirm));
//	}



}
