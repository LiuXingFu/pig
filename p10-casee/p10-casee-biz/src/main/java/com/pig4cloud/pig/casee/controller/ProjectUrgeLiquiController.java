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

import com.pig4cloud.pig.casee.dto.ProjectUrgeSaveDTO;
import com.pig4cloud.pig.casee.service.ProjectUrgeLiquiService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 项目督促表
 *
 * @author pig code generator
 * @date 2022-03-10 20:53:32
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projecturgeliqui" )
@Api(value = "projecturgeliqui", tags = "项目督促清收")
public class ProjectUrgeLiquiController {

    private final ProjectUrgeLiquiService projectUrgeLiquiService;

    /**
     * 新增项目督促表
     * @param projectUrgeSaveDTO
     * @return R
     */
    @ApiOperation(value = "新增项目督促表", notes = "新增项目督促表")
    @SysLog("新增项目督促表" )
    @PostMapping("/saveProjectUrge")
    public R saveProjectUrge(@RequestBody ProjectUrgeSaveDTO projectUrgeSaveDTO) {
        return R.ok(projectUrgeLiquiService.saveProjectUrge(projectUrgeSaveDTO));
    }


}
