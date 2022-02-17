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
import com.pig4cloud.pig.casee.entity.ExpenseRecordSubjectRe;
import com.pig4cloud.pig.casee.service.ExpenseRecordSubjectReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 费用记录关联主体信息
 *
 * @author Mjh
 * @date 2022-02-17 17:53:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/expenserecordsubjectre" )
@Api(value = "expenserecordsubjectre", tags = "费用记录关联主体信息管理")
public class ExpenseRecordSubjectReController {

    private final ExpenseRecordSubjectReService expenseRecordSubjectReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param expenseRecordSubjectRe 费用记录关联主体信息
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_expenserecordsubjectre_get')" )
    public R getExpenseRecordSubjectRePage(Page page, ExpenseRecordSubjectRe expenseRecordSubjectRe) {
        return R.ok(expenseRecordSubjectReService.page(page, Wrappers.query(expenseRecordSubjectRe)));
    }


    /**
     * 通过id查询费用记录关联主体信息
     * @param expenseRecordSubjectId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{expenseRecordSubjectId}" )
    @PreAuthorize("@pms.hasPermission('casee_expenserecordsubjectre_get')" )
    public R getById(@PathVariable("expenseRecordSubjectId" ) Integer expenseRecordSubjectId) {
        return R.ok(expenseRecordSubjectReService.getById(expenseRecordSubjectId));
    }

    /**
     * 新增费用记录关联主体信息
     * @param expenseRecordSubjectRe 费用记录关联主体信息
     * @return R
     */
    @ApiOperation(value = "新增费用记录关联主体信息", notes = "新增费用记录关联主体信息")
    @SysLog("新增费用记录关联主体信息" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_expenserecordsubjectre_add')" )
    public R save(@RequestBody ExpenseRecordSubjectRe expenseRecordSubjectRe) {
        return R.ok(expenseRecordSubjectReService.save(expenseRecordSubjectRe));
    }

    /**
     * 修改费用记录关联主体信息
     * @param expenseRecordSubjectRe 费用记录关联主体信息
     * @return R
     */
    @ApiOperation(value = "修改费用记录关联主体信息", notes = "修改费用记录关联主体信息")
    @SysLog("修改费用记录关联主体信息" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_expenserecordsubjectre_edit')" )
    public R updateById(@RequestBody ExpenseRecordSubjectRe expenseRecordSubjectRe) {
        return R.ok(expenseRecordSubjectReService.updateById(expenseRecordSubjectRe));
    }

    /**
     * 通过id删除费用记录关联主体信息
     * @param expenseRecordSubjectId id
     * @return R
     */
    @ApiOperation(value = "通过id删除费用记录关联主体信息", notes = "通过id删除费用记录关联主体信息")
    @SysLog("通过id删除费用记录关联主体信息" )
    @DeleteMapping("/{expenseRecordSubjectId}" )
    @PreAuthorize("@pms.hasPermission('casee_expenserecordsubjectre_del')" )
    public R removeById(@PathVariable Integer expenseRecordSubjectId) {
        return R.ok(expenseRecordSubjectReService.removeById(expenseRecordSubjectId));
    }

}
