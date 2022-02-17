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

import com.pig4cloud.pig.casee.dto.CaseeLiquiAddDTO;
import com.pig4cloud.pig.casee.service.CaseeLiquiService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 案件表
 *
 * @author yy
 * @date 2022-01-10 14:51:59
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/caseeLiqui" )
@Api(value = "caseeLiqui", tags = "清收案件表管理")
public class CaseeLiquiController {

    private final CaseeLiquiService caseeLiquiService;

	/**
	 * 新增案件表
	 * @param caseeLiquiAddDTO 案件表
	 * @return R
	 */
	@ApiOperation(value = "新增清收案件表", notes = "新增清收案件表")
	@SysLog("新增清收案件表" )
	@PostMapping("/saveCaseeLiqui")
	public R saveCaseeLiqui(@RequestBody CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception {
		return R.ok(caseeLiquiService.addCaseeLiqui(caseeLiquiAddDTO));
	}

}
