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
import com.pig4cloud.pig.admin.api.entity.UserInstitutionStaff;
import com.pig4cloud.pig.admin.api.feign.RemoteUserInstitutionStaffService;
import com.pig4cloud.pig.casee.dto.ProjectLiquiDTO;
import com.pig4cloud.pig.casee.dto.ProjectQueryLiquiDTO;
import com.pig4cloud.pig.casee.entity.CaseeBizBase;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.service.ProjectLiquiService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 清收项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projectLiqui" )
@Api(value = "projectLiqui", tags = "清收项目表管理")
public class ProjectLiquiController {

	@Autowired
    private ProjectLiquiService projectLiquiService;

    @Autowired
	private SecurityUtilsService securityUtilsService;

    @Autowired
	private RemoteUserInstitutionStaffService RemoteUserInstitutionStaffService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param projectDTO 项目表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
//    @PreAuthorize("@pms.hasPermission('casee_project_get')" )
    public R getProjectPage(Page page, ProjectQueryLiquiDTO projectDTO) {
		PigUser pigUser= securityUtilsService.getCacheUser();
		projectDTO.setInsId(pigUser.getInsId());
		if(pigUser.getOutlesId() != null){
			projectDTO.setOutlesId(pigUser.getOutlesId());
		} else {
			projectDTO.setBelongOutlesIds(RemoteUserInstitutionStaffService.getStaffOutlesList(SecurityConstants.FROM).getData());
		}
		return R.ok(projectLiquiService.queryLiquiProjectPage(page, projectDTO));
    }



    /**
     * 通过id查询项目表
     * @param projectId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{projectId}" )
//    @PreAuthorize("@pms.hasPermission('casee_project_get')" )
    public R getById(@PathVariable("projectId" ) Integer projectId) {
        return R.ok(projectLiquiService.queryLiquiProjectById(projectId));
    }

    /**
     * 新增清收项目表
     * @param projectDTO 清收项目表
     * @return R
     */
    @ApiOperation(value = "新增清收项目表", notes = "新增清收项目表")
    @SysLog("新增清收项目表" )
    @PostMapping
//    @PreAuthorize("@pms.hasPermission('casee_project_add')" )
    public R save(@RequestBody ProjectLiquiDTO projectDTO) {
        return R.ok(projectLiquiService.addProject(projectDTO));
    }

    /**
     * 修改项目表
     * @param project 项目表
     * @return R
     */
    @ApiOperation(value = "修改项目表", notes = "修改项目表")
    @SysLog("修改项目表" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('casee_project_edit')" )
    public R updateById(@RequestBody Project project) {
        return R.ok(projectLiquiService.updateById(project));
    }

	/**
	 * 修改清收项目状态
	 * @param project 修改清收项目状态
	 * @return R
	 */
	@ApiOperation(value = "修改清收项目状态", notes = "修改清收项目状态")
	@SysLog("修改清收项目状态" )
	@PostMapping("/modifyProjectStatus")
	public R modifyProjectStatus(@RequestBody Project project) throws Exception {
		return R.ok(projectLiquiService.modifyProjectStatus(project));
	}


    /**
     * 通过id删除项目表
     * @param projectId id
     * @return R
     */
    @ApiOperation(value = "通过id删除项目表", notes = "通过id删除项目表")
    @SysLog("通过id删除项目表" )
    @DeleteMapping("/{projectId}" )
    @PreAuthorize("@pms.hasPermission('casee_project_del')" )
    public R removeById(@PathVariable Integer projectId) {
        return R.ok(projectLiquiService.removeById(projectId));
    }

	/**
	 * 案件表-获取字号
	 *
	 * @return
	 */
	@ApiOperation(value = "项目表-获取字号", notes = "项目表-获取字号")
	@SysLog("案件表-获取字号" )
	@GetMapping("/getWord")
	public R getWord(Project project) {
		QueryWrapper<Project> caseeBizBaseQueryWrapper =new QueryWrapper<>();
		caseeBizBaseQueryWrapper.eq("project_type",project.getProjectType()).eq("year",project.getYear()).eq("alias",project.getAlias()).orderByDesc("word").last("limit 1");
		Project projectres=projectLiquiService.getOne(caseeBizBaseQueryWrapper);
		int word;
		if(projectres==null){
			word=1;
		}else {
			word =projectres.getWord()+1;
		}
		return R.ok(word);
	}
}
