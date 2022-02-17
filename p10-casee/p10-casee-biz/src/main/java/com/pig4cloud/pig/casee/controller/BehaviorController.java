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
import com.pig4cloud.pig.casee.entity.Behavior;
import com.pig4cloud.pig.casee.service.BehaviorService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 行为表
 *
 * @author yuanduo
 * @date 2022-02-14 15:51:27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/behavior" )
@Api(value = "behavior", tags = "行为表管理")
public class BehaviorController {

    private final  BehaviorService behaviorService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param behavior 行为表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_behavior_get')" )
    public R getBehaviorPage(Page page, Behavior behavior) {
        return R.ok(behaviorService.page(page, Wrappers.query(behavior)));
    }


    /**
     * 通过id查询行为表
     * @param behaviorId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{behaviorId}" )
    @PreAuthorize("@pms.hasPermission('casee_behavior_get')" )
    public R getById(@PathVariable("behaviorId" ) Integer behaviorId) {
        return R.ok(behaviorService.getById(behaviorId));
    }

    /**
     * 新增行为表
     * @param behavior 行为表
     * @return R
     */
    @ApiOperation(value = "新增行为表", notes = "新增行为表")
    @SysLog("新增行为表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_behavior_add')" )
    public R save(@RequestBody Behavior behavior) {
        return R.ok(behaviorService.save(behavior));
    }

    /**
     * 修改行为表
     * @param behavior 行为表
     * @return R
     */
    @ApiOperation(value = "修改行为表", notes = "修改行为表")
    @SysLog("修改行为表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_behavior_edit')" )
    public R updateById(@RequestBody Behavior behavior) {
        return R.ok(behaviorService.updateById(behavior));
    }

    /**
     * 通过id删除行为表
     * @param behaviorId id
     * @return R
     */
    @ApiOperation(value = "通过id删除行为表", notes = "通过id删除行为表")
    @SysLog("通过id删除行为表" )
    @DeleteMapping("/{behaviorId}" )
    @PreAuthorize("@pms.hasPermission('casee_behavior_del')" )
    public R removeById(@PathVariable Integer behaviorId) {
        return R.ok(behaviorService.removeById(behaviorId));
    }

	/**
	 * 根据主体id分页查询行为数据
	 * @param page
	 * @param subjectId
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/queryPageBehaviorOrProject" )
    public R queryPageBehaviorOrProject(Page page, Integer subjectId){
    	return R.ok(this.behaviorService.queryPageBehaviorOrProject(page, subjectId));
	}

	/**
	 * 根据行为id查询行为信息、项目信息、案件信息
	 * @param behaviorId
	 * @return
	 */
	@ApiOperation(value = "根据行为id查询行为信息、项目信息、案件信息", notes = "根据行为id查询行为信息、项目信息、案件信息")
	@GetMapping("/queryById/{behaviorId}")
	public R queryById(@PathVariable Integer behaviorId) {
		return R.ok(this.behaviorService.queryById(behaviorId));
	}

}
