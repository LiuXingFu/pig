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
import com.pig4cloud.pig.admin.api.entity.RelationshipAuthenticate;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.admin.service.RelationshipAuthenticateService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 机构法院关联表
 *
 * @author yuanduo
 * @date 2022-04-20 11:34:11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/relationshipauthenticate" )
@Api(value = "relationshipauthenticate", tags = "机构法院关联表管理")
public class RelationshipAuthenticateController {

    private final  RelationshipAuthenticateService relationshipAuthenticateService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param relationshipAuthenticate 机构法院关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getRelationshipAuthenticatePage(Page page, RelationshipAuthenticate relationshipAuthenticate) {
        return R.ok(relationshipAuthenticateService.page(page, Wrappers.query(relationshipAuthenticate)));
    }


    /**
     * 通过id查询机构法院关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{id}" )
    public R getById(@PathVariable("id" ) Integer id) {
        return R.ok(relationshipAuthenticateService.getById(id));
    }

    /**
     * 新增机构法院关联表
     * @param relationshipAuthenticate 机构法院关联表
     * @return R
     */
    @ApiOperation(value = "新增机构法院关联表", notes = "新增机构法院关联表")
    @SysLog("新增机构法院关联表" )
    @PostMapping
    public R save(@RequestBody RelationshipAuthenticate relationshipAuthenticate) {
        return R.ok(relationshipAuthenticateService.save(relationshipAuthenticate));
    }

    /**
     * 修改机构法院关联表
     * @param relationshipAuthenticate 机构法院关联表
     * @return R
     */
    @ApiOperation(value = "修改机构法院关联表", notes = "修改机构法院关联表")
    @SysLog("修改机构法院关联表" )
    @PutMapping
    public R updateById(@RequestBody RelationshipAuthenticate relationshipAuthenticate) {
        return R.ok(relationshipAuthenticateService.updateById(relationshipAuthenticate));
    }

    /**
     * 通过id删除机构法院关联表
     * @param id id
     * @return R
     */
    @ApiOperation(value = "通过id删除机构法院关联表", notes = "通过id删除机构法院关联表")
    @SysLog("通过id删除机构法院关联表" )
    @DeleteMapping("/{id}" )
    public R removeById(@PathVariable Integer id) {
        return R.ok(relationshipAuthenticateService.removeById(id));
    }

	/**
	 * 通过AuthenticateId查询authenticateGoalId
	 * @return
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getByAuthenticateId/{authenticateId}" )
    public R getByAuthenticateId(@PathVariable("authenticateId") Integer authenticateId) {
		return R.ok(this.relationshipAuthenticateService.getByAuthenticateId(authenticateId));
	}

}
