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
import com.pig4cloud.pig.casee.entity.ProjectCaseeRe;
import com.pig4cloud.pig.casee.service.ProjectCaseeReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 *
 *
 * @author pig code generator
 * @date 2022-02-13 22:13:19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projectcaseere" )
@Api(value = "projectcaseere", tags = "项目案件关联")
public class ProjectCaseeReController {

    private final  ProjectCaseeReService ProjectCaseeReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param ProjectCaseeRe
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('projectcaseere_get')" )
    public R getProjectCaseeRePage(Page page, ProjectCaseeRe ProjectCaseeRe) {
        return R.ok(ProjectCaseeReService.page(page, Wrappers.query(ProjectCaseeRe)));
    }

	/**
	 * 根据项目id查询所有执行案件信息
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "根据项目id查询所有执行案件信息", notes = "根据项目id查询所有执行案件信息")
	@GetMapping("/{projectId}" )
	public R getCaseeByProjectId(@PathVariable("projectId" ) Integer projectId) {
		return R.ok(ProjectCaseeReService.getCaseeByProjectId(projectId));
	}

    /**
     * 通过id查询
     * @param projectCaseeId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{projectCaseeId}" )
    @PreAuthorize("@pms.hasPermission('projectcaseere_get')" )
    public R getById(@PathVariable("projectCaseeId" ) Integer projectCaseeId) {
        return R.ok(ProjectCaseeReService.getById(projectCaseeId));
    }

    /**
     * 新增
     * @param ProjectCaseeRe
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('projectcaseere_add')" )
    public R save(@RequestBody ProjectCaseeRe ProjectCaseeRe) {
        return R.ok(ProjectCaseeReService.save(ProjectCaseeRe));
    }

    /**
     * 修改
     * @param ProjectCaseeRe
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('projectcaseere_edit')" )
    public R updateById(@RequestBody ProjectCaseeRe ProjectCaseeRe) {
        return R.ok(ProjectCaseeReService.updateById(ProjectCaseeRe));
    }

    /**
     * 通过id删除
     * @param projectCaseeId id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{projectCaseeId}" )
    @PreAuthorize("@pms.hasPermission('projectcaseere_del')" )
    public R removeById(@PathVariable Integer projectCaseeId) {
        return R.ok(ProjectCaseeReService.removeById(projectCaseeId));
    }

}
