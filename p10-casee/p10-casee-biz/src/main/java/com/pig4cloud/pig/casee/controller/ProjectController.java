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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.service.ProjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * 项目表
 *
 * @author pig code generator
 * @date 2022-02-10 17:30:36
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/project" )
@Api(value = "project", tags = "项目表管理")
public class ProjectController {

    private final  ProjectService projectService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param project 项目表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('demo_project_get')" )
    public R getProjectPage(Page page, Project project) {
        return R.ok(projectService.page(page, Wrappers.query(project)));
    }


    /**
     * 通过id查询项目表
     * @param projectId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{projectId}" )
    @PreAuthorize("@pms.hasPermission('demo_project_get')" )
    public R getById(@PathVariable("projectId" ) Integer projectId) {
        return R.ok(projectService.getById(projectId));
    }

    /**
     * 新增项目表
     * @param project 项目表
     * @return R
     */
    @ApiOperation(value = "新增项目表", notes = "新增项目表")
    @SysLog("新增项目表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('demo_project_add')" )
    public R save(@RequestBody Project project) {
        return R.ok(projectService.save(project));
    }

    /**
     * 修改项目表
     * @param project 项目表
     * @return R
     */
    @ApiOperation(value = "修改项目表", notes = "修改项目表")
    @SysLog("修改项目表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('demo_project_edit')" )
    public R updateById(@RequestBody Project project) {
        return R.ok(projectService.updateById(project));
    }

    /**
     * 通过id删除项目表
     * @param projectId id
     * @return R
     */
    @ApiOperation(value = "通过id删除项目表", notes = "通过id删除项目表")
    @SysLog("通过id删除项目表" )
    @DeleteMapping("/{projectId}" )
    @PreAuthorize("@pms.hasPermission('demo_project_del')" )
    public R removeById(@PathVariable Integer projectId) {
        return R.ok(projectService.removeById(projectId));
    }

	/**
	 * 根据主体id查询项目
	 * @param subjectId
	 * @return
	 */
	@ApiOperation(value = "根据主体id查询项目", notes = "根据主体id查询项目")
	@GetMapping("/getProjectAmountBySubjectId/{subjectId}")
	public R getProjectAmountBySubjectId(@PathVariable("subjectId") Integer subjectId){
    	return R.ok(projectService.getProjectAmountBySubjectId(subjectId));
	}

	/**
	 * 验证公司业务案号
	 *
	 * @return
	 */
	@ApiOperation(value = "验证公司业务案号", notes = "验证公司业务案号")
	@SysLog("验证公司业务案号" )
	@GetMapping("/verifyCompanyCode")
	public R verifyCompanyCode(String companyCode,@RequestParam(value = "projectId",required = false) Integer projectId) {
		QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(Project::getCompanyCode,companyCode);
		if(Objects.nonNull(projectId)){
			queryWrapper.lambda().ne(Project::getProjectId,projectId);
		}
		queryWrapper.lambda().eq(Project::getDelFlag, CommonConstants.STATUS_NORMAL);
		return R.ok(projectService.getOne(queryWrapper));
	}

}
