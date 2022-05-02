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
import com.pig4cloud.pig.casee.entity.SignUpLookLike;
import com.pig4cloud.pig.casee.service.SignUpLookLikeService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 报名看样表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/signuplooklike" )
@Api(value = "signuplooklike", tags = "报名看样表管理")
public class SignUpLookLikeController {

    private final  SignUpLookLikeService signUpLookLikeService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param signUpLookLike 报名看样表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_signuplooklike_get')" )
    public R getSignUpLookLikePage(Page page, SignUpLookLike signUpLookLike) {
        return R.ok(signUpLookLikeService.page(page, Wrappers.query(signUpLookLike)));
    }


    /**
     * 通过id查询报名看样表
     * @param signUpLookLikeId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{signUpLookLikeId}" )
    @PreAuthorize("@pms.hasPermission('casee_signuplooklike_get')" )
    public R getById(@PathVariable("signUpLookLikeId" ) Integer signUpLookLikeId) {
        return R.ok(signUpLookLikeService.getById(signUpLookLikeId));
    }

    /**
     * 新增报名看样表
     * @param signUpLookLike 报名看样表
     * @return R
     */
    @ApiOperation(value = "新增报名看样表", notes = "新增报名看样表")
    @SysLog("新增报名看样表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_signuplooklike_add')" )
    public R save(@RequestBody SignUpLookLike signUpLookLike) {
        return R.ok(signUpLookLikeService.save(signUpLookLike));
    }

    /**
     * 修改报名看样表
     * @param signUpLookLike 报名看样表
     * @return R
     */
    @ApiOperation(value = "修改报名看样表", notes = "修改报名看样表")
    @SysLog("修改报名看样表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_signuplooklike_edit')" )
    public R updateById(@RequestBody SignUpLookLike signUpLookLike) {
        return R.ok(signUpLookLikeService.updateById(signUpLookLike));
    }

    /**
     * 通过id删除报名看样表
     * @param signUpLookLikeId id
     * @return R
     */
    @ApiOperation(value = "通过id删除报名看样表", notes = "通过id删除报名看样表")
    @SysLog("通过id删除报名看样表" )
    @DeleteMapping("/{signUpLookLikeId}" )
    @PreAuthorize("@pms.hasPermission('casee_signuplooklike_del')" )
    public R removeById(@PathVariable Integer signUpLookLikeId) {
        return R.ok(signUpLookLikeService.removeById(signUpLookLikeId));
    }

}
