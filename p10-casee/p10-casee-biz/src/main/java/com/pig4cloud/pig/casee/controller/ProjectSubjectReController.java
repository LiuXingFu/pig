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
import com.pig4cloud.pig.casee.dto.ProjectSubjectReModifyDTO;
import com.pig4cloud.pig.casee.entity.ProjectSubjectRe;
import com.pig4cloud.pig.casee.service.ProjectSubjectReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 项目主体关联案件表
 *
 * @author pig code generator
 * @date 2022-03-22 16:59:18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projectsubjectre" )
@Api(value = "projectsubjectre", tags = "项目主体关联案件表管理")
public class ProjectSubjectReController {

	private final ProjectSubjectReService projectSubjectReService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param projectSubjectRe 项目主体关联案件表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
	public R getP10ProjectSubjectRePage(Page page, ProjectSubjectRe projectSubjectRe) {
		return R.ok(projectSubjectReService.page(page, Wrappers.query(projectSubjectRe)));
	}


	/**
	 * 通过id查询项目主体关联案件表
	 * @param subjectReId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{subjectReId}" )
	public R getById(@PathVariable("subjectReId" ) Integer subjectReId) {
		return R.ok(projectSubjectReService.getById(subjectReId));
	}

	/**
	 * 新增项目主体关联案件表
	 * @param projectSubjectRe 项目主体关联案件表
	 * @return R
	 */
	@ApiOperation(value = "新增项目主体关联案件表", notes = "新增项目主体关联案件表")
	@SysLog("新增项目主体关联案件表" )
	@PostMapping
	public R save(@RequestBody ProjectSubjectRe projectSubjectRe) {
		return R.ok(projectSubjectReService.save(projectSubjectRe));
	}

	/**
	 * 修改项目主体关联案件表
	 * @param projectSubjectRe 项目主体关联案件表
	 * @return R
	 */
	@ApiOperation(value = "修改项目主体关联案件表", notes = "修改项目主体关联案件表")
	@SysLog("修改项目主体关联案件表" )
	@PutMapping
	public R updateById(@RequestBody ProjectSubjectRe projectSubjectRe) {
		return R.ok(projectSubjectReService.updateById(projectSubjectRe));
	}

	/**
	 * 通过id删除项目主体关联案件表
	 * @param subjectReId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除项目主体关联案件表", notes = "通过id删除项目主体关联案件表")
	@SysLog("通过id删除项目主体关联案件表" )
	@DeleteMapping("/{subjectReId}" )
	public R removeById(@PathVariable Integer subjectReId) {
		return R.ok(projectSubjectReService.removeById(subjectReId));
	}

	/**
	 * 根据项目主体关联id更新债务人类型和主体信息
	 * @param projectSubjectReModifyDTO
	 * @return R
	 */
	@ApiOperation(value = "根据项目主体关联id更新债务人类型和主体信息", notes = "根据项目主体关联id更新债务人类型和主体信息")
	@SysLog("根据项目主体关联id更新债务人类型和主体信息" )
	@PutMapping("/modifySubjectBySubjectReId")
	public R modifySubjectBySubjectReId(@RequestBody ProjectSubjectReModifyDTO projectSubjectReModifyDTO) {
		return R.ok(projectSubjectReService.modifySubjectBySubjectReId(projectSubjectReModifyDTO));
	}


}
