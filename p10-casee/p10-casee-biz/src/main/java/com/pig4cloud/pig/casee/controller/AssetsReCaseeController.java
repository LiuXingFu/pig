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

import com.pig4cloud.pig.casee.dto.AssetsAddDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.service.AssetsReCaseeService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/assetsReCasee" )
@Api(value = "assetsReCasee", tags = "财产关联表管理")
public class AssetsReCaseeController {

    private final AssetsReCaseeService assetsReCaseeService;

	/**
	 * 添加案件财产
	 * @param assetsAddDTO
	 * @return R
	 */
	@ApiOperation(value = "添加案件财产", notes = "添加案件财产")
	@SysLog("新增财产关联表" )
	@PostMapping("/saveAssetsCasee")
	public R save(@RequestBody AssetsAddDTO assetsAddDTO) {
		return R.ok(assetsReCaseeService.saveAssetsCasee(assetsAddDTO));
	}


}
