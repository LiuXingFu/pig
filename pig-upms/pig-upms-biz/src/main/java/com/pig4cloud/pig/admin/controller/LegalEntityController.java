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
import com.pig4cloud.pig.admin.api.entity.LegalEntity;
import com.pig4cloud.pig.admin.service.LegalEntityService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 
 *
 * @author xiaojun
 * @date 2021-08-17 16:22:59
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/legalentity" )
@Api(value = "legalentity", tags = "管理")
public class LegalEntityController {

    private final  LegalEntityService legalEntityService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param legalEntity 
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getLegalEntityPage(Page page, LegalEntity legalEntity) {
        return R.ok(legalEntityService.page(page, Wrappers.query(legalEntity)));
    }


    /**
     * 通过id查询
     * @param entityId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{entityId}" )
    public R getById(@PathVariable("entityId" ) Integer entityId) {
        return R.ok(legalEntityService.getById(entityId));
    }

    /**
     * 新增
     * @param legalEntity 
     * @return R
     */
    @ApiOperation(value = "新增", notes = "新增")
    @SysLog("新增" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('legalentity_add')" )
    public R save(@RequestBody LegalEntity legalEntity) {
        return R.ok(legalEntityService.save(legalEntity));
    }

    /**
     * 修改
     * @param legalEntity 
     * @return R
     */
    @ApiOperation(value = "修改", notes = "修改")
    @SysLog("修改" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('legalentity_edit')" )
    public R updateById(@RequestBody LegalEntity legalEntity) {
        return R.ok(legalEntityService.updateById(legalEntity));
    }

    /**
     * 通过id删除
     * @param entityId id
     * @return R
     */
    @ApiOperation(value = "通过id删除", notes = "通过id删除")
    @SysLog("通过id删除" )
    @DeleteMapping("/{entityId}" )
    @PreAuthorize("@pms.hasPermission('legalentity_del')" )
    public R removeById(@PathVariable Integer entityId) {
        return R.ok(legalEntityService.removeById(entityId));
    }

}
