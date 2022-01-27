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

package com.pig4cloud.pig.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.admin.service.TaskNodeTemplateService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 流程节点模板表
 *
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasknodetemplate" )
@Api(value = "tasknodetemplate", tags = "流程节点模板表管理")
public class TaskNodeTemplateController {

    private final  TaskNodeTemplateService taskNodeTemplateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param taskNodeTemplate 流程节点模板表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTaskNodeTemplatePage(Page page, TaskNodeTemplate taskNodeTemplate) {
        return R.ok(taskNodeTemplateService.page(page, Wrappers.query(taskNodeTemplate)));
    }


    /**
     * 通过id查询流程节点模板表
     * @param templateId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{templateId}" )
    public R getById(@PathVariable("templateId" ) Integer templateId) {
        return R.ok(taskNodeTemplateService.getById(templateId));
    }

    /**
     * 新增流程节点模板表
     * @param taskNodeTemplate 流程节点模板表
     * @return R
     */
    @ApiOperation(value = "新增流程节点模板表", notes = "新增流程节点模板表")
    @SysLog("新增流程节点模板表" )
    @PostMapping
    public R save(@RequestBody TaskNodeTemplate taskNodeTemplate) {
    	taskNodeTemplate.setInsUserId(SecurityUtils.getUser().getInsId());
        return R.ok(taskNodeTemplateService.save(taskNodeTemplate));
    }

    /**
     * 修改流程节点模板表
     * @param taskNodeTemplate 流程节点模板表
     * @return R
     */
    @ApiOperation(value = "修改流程节点模板表", notes = "修改流程节点模板表")
    @SysLog("修改流程节点模板表" )
    @PutMapping
    public R updateById(@RequestBody TaskNodeTemplate taskNodeTemplate) {
        return R.ok(taskNodeTemplateService.updateById(taskNodeTemplate));
    }

    /**
     * 通过id删除流程节点模板表
     * @param templateId id
     * @return R
     */
    @ApiOperation(value = "通过id删除流程节点模板表", notes = "通过id删除流程节点模板表")
    @SysLog("通过id删除流程节点模板表" )
    @DeleteMapping("/{templateId}" )
    public R removeById(@PathVariable Integer templateId) {
        return R.ok(taskNodeTemplateService.removeByTemplateIdOrOutlesTemplateRe(templateId));
    }

	/**
	 * 查询当前机构类型的模板信息
	 * @param
	 * @return TaskNodeTemplate
	 */
	@ApiOperation(value = "查询当前机构类型的模板信息", notes = "查询当前机构类型的模板信息")
	@GetMapping("/queryTemplateType")
	public R queryTemplateType() {
		return R.ok(taskNodeTemplateService.queryTemplateType());
	}
}
