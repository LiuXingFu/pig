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
import com.pig4cloud.pig.casee.entity.CaseePersonnelRe;
import com.pig4cloud.pig.casee.service.CaseePersonnelReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 案件人员关联案件表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/caseepersonnelre" )
@Api(value = "caseepersonnelre", tags = "案件人员关联案件表管理")
public class CaseePersonnelReController {

    private final  CaseePersonnelReService caseePersonnelReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param caseePersonnelRe 案件人员关联案件表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_caseepersonnelre_get')" )
    public R getCaseePersonnelRePage(Page page, CaseePersonnelRe caseePersonnelRe) {
        return R.ok(caseePersonnelReService.page(page, Wrappers.query(caseePersonnelRe)));
    }


    /**
     * 通过id查询案件人员关联案件表
     * @param personnelReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{personnelReId}" )
    @PreAuthorize("@pms.hasPermission('casee_caseepersonnelre_get')" )
    public R getById(@PathVariable("personnelReId" ) Integer personnelReId) {
        return R.ok(caseePersonnelReService.getById(personnelReId));
    }

    /**
     * 新增案件人员关联案件表
     * @param caseePersonnelRe 案件人员关联案件表
     * @return R
     */
    @ApiOperation(value = "新增案件人员关联案件表", notes = "新增案件人员关联案件表")
    @SysLog("新增案件人员关联案件表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_caseepersonnelre_add')" )
    public R save(@RequestBody CaseePersonnelRe caseePersonnelRe) {
        return R.ok(caseePersonnelReService.save(caseePersonnelRe));
    }

    /**
     * 修改案件人员关联案件表
     * @param caseePersonnelRe 案件人员关联案件表
     * @return R
     */
    @ApiOperation(value = "修改案件人员关联案件表", notes = "修改案件人员关联案件表")
    @SysLog("修改案件人员关联案件表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_caseepersonnelre_edit')" )
    public R updateById(@RequestBody CaseePersonnelRe caseePersonnelRe) {
        return R.ok(caseePersonnelReService.updateById(caseePersonnelRe));
    }

    /**
     * 通过id删除案件人员关联案件表
     * @param personnelReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除案件人员关联案件表", notes = "通过id删除案件人员关联案件表")
    @SysLog("通过id删除案件人员关联案件表" )
    @DeleteMapping("/{personnelReId}" )
    @PreAuthorize("@pms.hasPermission('casee_caseepersonnelre_del')" )
    public R removeById(@PathVariable Integer personnelReId) {
        return R.ok(caseePersonnelReService.removeById(personnelReId));
    }

}
