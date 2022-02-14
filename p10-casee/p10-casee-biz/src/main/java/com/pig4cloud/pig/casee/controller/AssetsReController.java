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
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.service.AssetsReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/assetsre" )
@Api(value = "assetsre", tags = "财产关联表管理")
public class AssetsReController {

    private final  AssetsReService assetsReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param assetsRe 财产关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_assetsre_get')" )
    public R getAssetsRePage(Page page, AssetsRe assetsRe) {
        return R.ok(assetsReService.page(page, Wrappers.query(assetsRe)));
    }


    /**
     * 通过id查询财产关联表
     * @param assetsReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{assetsReId}" )
    @PreAuthorize("@pms.hasPermission('casee_assetsre_get')" )
    public R getById(@PathVariable("assetsReId" ) Integer assetsReId) {
        return R.ok(assetsReService.getById(assetsReId));
    }


	/**
	 * 通过项目id查询财产信息
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "通过项目id查询财产信息", notes = "通过项目id查询财产信息")
	@GetMapping("/getProjectIdByAssets/{projectId}" )
	public R getProjectIdByAssets(@PathVariable("projectId" ) Integer projectId) {
		return R.ok(assetsReService.getProjectIdByAssets(projectId));
	}

    /**
     * 新增财产关联表
     * @param assetsRe 财产关联表
     * @return R
     */
    @ApiOperation(value = "新增财产关联表", notes = "新增财产关联表")
    @SysLog("新增财产关联表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_assetsre_add')" )
    public R save(@RequestBody AssetsRe assetsRe) {
        return R.ok(assetsReService.save(assetsRe));
    }

    /**
     * 修改财产关联表
     * @param assetsRe 财产关联表
     * @return R
     */
    @ApiOperation(value = "修改财产关联表", notes = "修改财产关联表")
    @SysLog("修改财产关联表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_assetsre_edit')" )
    public R updateById(@RequestBody AssetsRe assetsRe) {
        return R.ok(assetsReService.updateById(assetsRe));
    }

    /**
     * 通过id删除财产关联表
     * @param assetsReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除财产关联表", notes = "通过id删除财产关联表")
    @SysLog("通过id删除财产关联表" )
    @DeleteMapping("/{assetsReId}" )
    @PreAuthorize("@pms.hasPermission('casee_assetsre_del')" )
    public R removeById(@PathVariable Integer assetsReId) {
        return R.ok(assetsReService.removeById(assetsReId));
    }

}
