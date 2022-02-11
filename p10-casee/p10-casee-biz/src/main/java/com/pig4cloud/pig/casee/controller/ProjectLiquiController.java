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
import com.pig4cloud.pig.casee.dto.ProjectLiquiPageDTO;
import com.pig4cloud.pig.casee.service.ProjectLiquiService;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

	/**
	 * 项目清收分页查询
	 * @param page 分页对象
	 * @param projectLiquiPageDTO 项目清收表
	 * @return
	 */
	@ApiOperation(value = "项目清收分页查询", notes = "项目清收分页查询")
	@GetMapping("/queryPageLiqui" )
	public R queryPageLiqui(Page page, ProjectLiquiPageDTO projectLiquiPageDTO) {
		return R.ok(projectLiquiService.queryPageLiqui(page, projectLiquiPageDTO));
	}






}
