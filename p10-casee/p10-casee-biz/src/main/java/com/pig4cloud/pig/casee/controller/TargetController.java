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

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.TargetAddDTO;
import com.pig4cloud.pig.casee.dto.TargetCaseeProjectPageDTO;
import com.pig4cloud.pig.casee.dto.TargetPageDTO;
import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.service.TargetBizLiquiService;
import com.pig4cloud.pig.casee.service.TargetService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 程序表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/target" )
@Api(value = "target", tags = "程序表管理")
public class TargetController {

    private final TargetService targetService;
	private final TargetBizLiquiService targetBizLiquiService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param targetPageDTO 程序表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('casee_target_get')" )
    public R getTargetPage(Page page, TargetPageDTO targetPageDTO) {
        return R.ok(targetService.queryPageList(page,targetPageDTO));
    }


    /**
     * 通过id查询程序表
     * @param targetId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{targetId}" )
//    @PreAuthorize("@pms.hasPermission('casee_target_get')" )
    public R getByTargetId(@PathVariable("targetId" ) Integer targetId) {
//        return R.ok(targetBizLiquiService.queryTargetById(targetId));
		return R.ok();
    }

	/**
	 * 根据程序DTO添加相应程序与相应任务
	 *
	 * @param target
	 * @return
	 * @throws Exception
	 */
	@ApiOperation(value = "根据程序DTO添加相应程序与相应任务", notes = "根据程序DTO添加相应程序与相应任务")
	@SysLog("根据程序DTO添加相应程序与相应任务" )
	@PostMapping
//    @PreAuthorize("@pms.hasPermission('casee_target_add')" )
	public R save(@RequestBody Target target) {
		return R.ok(targetService.save(target));
	}

	/**
	 * 根据程序DTO添加相应程序与相应任务
	 *
	 * @param targetAddDTO
	 * @return
	 * @throws Exception
	 */
    @ApiOperation(value = "根据程序DTO添加相应程序与相应任务", notes = "根据程序DTO添加相应程序与相应任务")
    @SysLog("根据程序DTO添加相应程序与相应任务" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('casee_target_add')" )
    public R saveTargetAddDTO(@RequestBody TargetAddDTO targetAddDTO) throws Exception  {
        return R.ok(targetService.saveTargetAddDTO(targetAddDTO));
    }

    /**
     * 修改程序表
     * @param target 程序表
     * @return R
     */
    @ApiOperation(value = "修改程序表", notes = "修改程序表")
    @SysLog("修改程序表" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('casee_target_edit')" )
    public R updateById(@RequestBody Target target) {
        return R.ok(targetService.updateById(target));
    }

    /**
     * 通过id删除程序表
     * @param targetId id
     * @return R
     */
    @ApiOperation(value = "通过id删除程序表", notes = "通过id删除程序表")
    @SysLog("通过id删除程序表" )
    @DeleteMapping("/{targetId}" )
//    @PreAuthorize("@pms.hasPermission('casee_target_del')" )
    public R removeById(@PathVariable Integer targetId) {
		LambdaUpdateWrapper<Target> targetLambdaUpdateWrapper=new LambdaUpdateWrapper<>();
		targetLambdaUpdateWrapper.eq(Target::getTargetId,targetId).set(Target::getDelFlag,1);
		return R.ok(targetService.update(targetLambdaUpdateWrapper));

    }


	/**
	 * 根据案件类型分页查询立案未送达
	 * @param page
	 * @param targetCaseeProjectPageDTO
	 * @return
	 */
	@ApiOperation(value = "根据案件类型分页查询立案未送达", notes = "根据案件类型分页查询立案未送达")
	@GetMapping("/standCaseUndeliveredPage")
	public R standCaseUndeliveredPage(Page page, TargetCaseeProjectPageDTO targetCaseeProjectPageDTO) {
		return R.ok(targetService.standCaseUndeliveredPage(page, targetCaseeProjectPageDTO));
	}

}
