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
import com.pig4cloud.pig.casee.dto.CaseeAddDTO;
import com.pig4cloud.pig.casee.dto.CaseeGetListDTO;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.service.CaseeService;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/casee" )
@Api(value = "casee", tags = "案件表管理")
public class CaseeController {

    private final  CaseeService caseeService;

    /**
     * 查询
     * @param caseeGetListDTO 案件表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCaseePage(  CaseeGetListDTO caseeGetListDTO) {

        return R.ok(caseeService.getCaseeList(caseeGetListDTO));
    }


    /**
     * 通过id查询案件表
     * @param caseeId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{caseeId}" )
    public R getById(@PathVariable("caseeId" ) Integer caseeId) {
        return R.ok(caseeService.getById(caseeId));
    }

    /**
     * 新增案件表
     * @param caseeAddDTO 案件表
     * @return R
     */
    @ApiOperation(value = "新增案件表", notes = "新增案件表")
    @SysLog("新增案件表" )
    @PostMapping
    public R save(@RequestBody CaseeAddDTO caseeAddDTO) throws Exception {
		Integer integer = caseeService.addCase(caseeAddDTO);
		if (integer<0){
			return R.failed("添加失败，当前网点没有配置相应模板!");
		}
		return R.ok(integer);
    }

    /**
     * 修改案件表
     * @param casee 案件表
     * @return R
     */
    @ApiOperation(value = "修改案件表", notes = "修改案件表")
    @SysLog("修改案件表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('demo_casee_edit')" )
    public R updateById(@RequestBody Casee casee) {
        return R.ok(caseeService.updateById(casee));
    }

    /**
     * 通过id删除案件表
     * @param caseeId id
     * @return R
     */
    @ApiOperation(value = "通过id删除案件表", notes = "通过id删除案件表")
    @SysLog("通过id删除案件表" )
    @DeleteMapping("/{caseeId}" )
    @PreAuthorize("@pms.hasPermission('demo_casee_del')" )
    public R removeById(@PathVariable Integer caseeId) {
        return R.ok(caseeService.removeById(caseeId));
    }

}
