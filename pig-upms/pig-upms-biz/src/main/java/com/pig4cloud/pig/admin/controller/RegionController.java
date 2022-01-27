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

package com.pig4cloud.pig.admin.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.admin.api.entity.Region;
import com.pig4cloud.pig.admin.service.RegionService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 地址级联关系表
 *
 * @author yuanduo
 * @date 2021-09-23 15:37:44
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/region" )
@Api(value = "region", tags = "地址级联关系表管理")
public class RegionController {

    private final  RegionService regionService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param region 地址级联关系表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('admin_region_get')" )
    public R getRegionPage(Page page, Region region) {
        return R.ok(regionService.page(page, Wrappers.query(region)));
    }


    /**
     * 通过id查询地址级联关系表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
//    @PreAuthorize("@pms.hasPermission('admin_region_get')" )
    public R getById(@PathVariable("id" ) Long id) {
        return R.ok(regionService.getById(id));
    }

	/**
	 * 通过父id查询地址级联关系表 省
	 * @return R
	 */
	@ApiOperation(value = "通过父id查询地址级联关系表 省", notes = "通过父id查询地址级联关系表 省")
	@GetMapping("/getProvinceList" )
    public R getProvinceList(){
    	return R.ok(regionService.getProvinceList(-1));
	}

	/**
	 * 通过父id查询地址级联关系表 市与区
	 * @return R
	 */
	@ApiOperation(value = "通过父id查询地址级联关系表 市与区", notes = "通过父id查询地址级联关系表 市与区")
	@GetMapping("/getCityAreaList/{parentId}" )
	public R getCityAreaList(@PathVariable("parentId" ) Integer parentId){
		return R.ok(regionService.getProvinceList(parentId));
	}

    /**
     * 新增地址级联关系表
     * @param region 地址级联关系表
     * @return R
     */
    @ApiOperation(value = "新增地址级联关系表", notes = "新增地址级联关系表")
    @SysLog("新增地址级联关系表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('admin_region_add')" )
    public R save(@RequestBody Region region) {
        return R.ok(regionService.save(region));
    }

    /**
     * 修改地址级联关系表
     * @param region 地址级联关系表
     * @return R
     */
    @ApiOperation(value = "修改地址级联关系表", notes = "修改地址级联关系表")
    @SysLog("修改地址级联关系表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('admin_region_edit')" )
    public R updateById(@RequestBody Region region) {
        return R.ok(regionService.updateById(region));
    }

    /**
     * 通过id删除地址级联关系表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除地址级联关系表", notes = "通过id删除地址级联关系表")
    @SysLog("通过id删除地址级联关系表" )
    @DeleteMapping("/{id}" )
    @PreAuthorize("@pms.hasPermission('admin_region_del')" )
    public R removeById(@PathVariable Long id) {
        return R.ok(regionService.removeById(id));
    }

}
