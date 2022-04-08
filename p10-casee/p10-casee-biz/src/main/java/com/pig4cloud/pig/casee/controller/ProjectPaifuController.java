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
import com.pig4cloud.pig.casee.dto.paifu.ProjectPaifuPageDTO;
import com.pig4cloud.pig.casee.dto.paifu.ProjectPaifuSaveDTO;
import com.pig4cloud.pig.casee.dto.paifu.ProjectSubjectReSaveDTO;
import com.pig4cloud.pig.casee.service.ProjectPaifuService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 拍辅项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projectPaifu" )
@Api(value = "projectPaifu", tags = "拍辅项目表管理")
public class ProjectPaifuController {

	@Autowired
    private ProjectPaifuService projectPaifuService;

	/**
	 * 项目拍辅分页查询
	 * @param page 分页对象
	 * @param projectPaifuPageDTO
	 * @return
	 */
	@ApiOperation(value = "项目拍辅分页查询", notes = "项目拍辅分页查询")
	@GetMapping("/queryProjectCaseePage" )
	public R queryProjectCaseePage(Page page, ProjectPaifuPageDTO projectPaifuPageDTO) {
		return R.ok(projectPaifuService.queryProjectCaseePage(page, projectPaifuPageDTO));
	}

	/**
	 * 新增拍辅项目案件
	 * @param projectPaifuSaveDTO
	 * @return R
	 */
	@ApiOperation(value = "新增拍辅项目案件", notes = "新增拍辅项目案件")
	@SysLog("新增拍辅项目案件" )
	@PostMapping("/saveProjectCasee")
	public R saveProjectCasee(@RequestBody ProjectPaifuSaveDTO projectPaifuSaveDTO) {
		return R.ok(projectPaifuService.saveProjectCasee(projectPaifuSaveDTO));
	}

	/**
	 * 查询项目案件详情
	 * @param projectId 项目id
	 * @return
	 */
	@ApiOperation(value = "查询项目案件详情", notes = "查询项目案件详情")
	@GetMapping("/queryProjectCaseeDetail" )
	public R queryProjectCaseeDetail(Integer projectId) {
		return R.ok(projectPaifuService.queryProjectCaseeDetail(projectId));
	}

	/**
	 * 新增拍辅项目主体关联表
	 * @param projectSubjectReSaveDTO
	 * @return R
	 */
	@ApiOperation(value = "新增拍辅项目主体关联表", notes = "新增拍辅项目主体关联表")
	@SysLog("新增拍辅项目主体关联表" )
	@PostMapping("/addProjectSubjectRe")
	public R addProjectSubjectRe(@RequestBody ProjectSubjectReSaveDTO projectSubjectReSaveDTO) {
		return R.ok(projectPaifuService.addProjectSubjectRe(projectSubjectReSaveDTO));
	}

	/**
	 * 验证项目主体
	 * @param projectId 项目id
	 * @return
	 */
	@ApiOperation(value = "验证项目主体", notes = "验证项目主体")
	@GetMapping("/queryProjectSubjectRe" )
	public R queryProjectSubjectRe(Integer projectId,String unifiedIdentity) {
		return R.ok(projectPaifuService.queryProjectSubjectRe(projectId,unifiedIdentity));
	}

}
