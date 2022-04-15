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
import com.pig4cloud.pig.casee.entity.AssetsReSubject;
import com.pig4cloud.pig.casee.service.AssetsReSubjectService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 项目案件财产关联主体
 *
 * @author pig code generator
 * @date 2022-04-14 17:09:52
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/assetsresubject" )
@Api(value = "assetsresubject", tags = "项目案件财产关联主体管理")
public class AssetsReSubjectController {

    private final  AssetsReSubjectService assetsReSubjectService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param assetsReSubject 项目案件财产关联主体
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_assetsresubject_get')" )
    public R getAssetsReSubjectPage(Page page, AssetsReSubject assetsReSubject) {
        return R.ok(assetsReSubjectService.page(page, Wrappers.query(assetsReSubject)));
    }


    /**
     * 通过id查询项目案件财产关联主体
     * @param assetsReSubjectId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{assetsReSubjectId}" )
    @PreAuthorize("@pms.hasPermission('casee_assetsresubject_get')" )
    public R getById(@PathVariable("assetsReSubjectId" ) Integer assetsReSubjectId) {
        return R.ok(assetsReSubjectService.getById(assetsReSubjectId));
    }

    /**
     * 新增项目案件财产关联主体
     * @param assetsReSubject 项目案件财产关联主体
     * @return R
     */
    @ApiOperation(value = "新增项目案件财产关联主体", notes = "新增项目案件财产关联主体")
    @SysLog("新增项目案件财产关联主体" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_assetsresubject_add')" )
    public R save(@RequestBody AssetsReSubject assetsReSubject) {
        return R.ok(assetsReSubjectService.save(assetsReSubject));
    }

    /**
     * 修改项目案件财产关联主体
     * @param assetsReSubject 项目案件财产关联主体
     * @return R
     */
    @ApiOperation(value = "修改项目案件财产关联主体", notes = "修改项目案件财产关联主体")
    @SysLog("修改项目案件财产关联主体" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_assetsresubject_edit')" )
    public R updateById(@RequestBody AssetsReSubject assetsReSubject) {
        return R.ok(assetsReSubjectService.updateById(assetsReSubject));
    }

    /**
     * 通过id删除项目案件财产关联主体
     * @param assetsReSubjectId id
     * @return R
     */
    @ApiOperation(value = "通过id删除项目案件财产关联主体", notes = "通过id删除项目案件财产关联主体")
    @SysLog("通过id删除项目案件财产关联主体" )
    @DeleteMapping("/{assetsReSubjectId}" )
    @PreAuthorize("@pms.hasPermission('casee_assetsresubject_del')" )
    public R removeById(@PathVariable Integer assetsReSubjectId) {
        return R.ok(assetsReSubjectService.removeById(assetsReSubjectId));
    }

}
