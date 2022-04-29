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
import com.pig4cloud.pig.casee.entity.ReceiveConsultationQuestionRe;
import com.pig4cloud.pig.casee.service.ReceiveConsultationQuestionReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 接受咨询关联咨询问题表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:18
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/receiveconsultationquestionre" )
@Api(value = "receiveconsultationquestionre", tags = "接受咨询关联咨询问题表管理")
public class ReceiveConsultationQuestionReController {

    private final  ReceiveConsultationQuestionReService receiveConsultationQuestionReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param receiveConsultationQuestionRe 接受咨询关联咨询问题表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_receiveconsultationquestionre_get')" )
    public R getReceiveConsultationQuestionRePage(Page page, ReceiveConsultationQuestionRe receiveConsultationQuestionRe) {
        return R.ok(receiveConsultationQuestionReService.page(page, Wrappers.query(receiveConsultationQuestionRe)));
    }


    /**
     * 通过id查询接受咨询关联咨询问题表
     * @param receiveConsultationQuestionReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{receiveConsultationQuestionReId}" )
    @PreAuthorize("@pms.hasPermission('casee_receiveconsultationquestionre_get')" )
    public R getById(@PathVariable("receiveConsultationQuestionReId" ) Integer receiveConsultationQuestionReId) {
        return R.ok(receiveConsultationQuestionReService.getById(receiveConsultationQuestionReId));
    }

    /**
     * 新增接受咨询关联咨询问题表
     * @param receiveConsultationQuestionRe 接受咨询关联咨询问题表
     * @return R
     */
    @ApiOperation(value = "新增接受咨询关联咨询问题表", notes = "新增接受咨询关联咨询问题表")
    @SysLog("新增接受咨询关联咨询问题表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_receiveconsultationquestionre_add')" )
    public R save(@RequestBody ReceiveConsultationQuestionRe receiveConsultationQuestionRe) {
        return R.ok(receiveConsultationQuestionReService.save(receiveConsultationQuestionRe));
    }

    /**
     * 修改接受咨询关联咨询问题表
     * @param receiveConsultationQuestionRe 接受咨询关联咨询问题表
     * @return R
     */
    @ApiOperation(value = "修改接受咨询关联咨询问题表", notes = "修改接受咨询关联咨询问题表")
    @SysLog("修改接受咨询关联咨询问题表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_receiveconsultationquestionre_edit')" )
    public R updateById(@RequestBody ReceiveConsultationQuestionRe receiveConsultationQuestionRe) {
        return R.ok(receiveConsultationQuestionReService.updateById(receiveConsultationQuestionRe));
    }

    /**
     * 通过id删除接受咨询关联咨询问题表
     * @param receiveConsultationQuestionReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除接受咨询关联咨询问题表", notes = "通过id删除接受咨询关联咨询问题表")
    @SysLog("通过id删除接受咨询关联咨询问题表" )
    @DeleteMapping("/{receiveConsultationQuestionReId}" )
    @PreAuthorize("@pms.hasPermission('casee_receiveconsultationquestionre_del')" )
    public R removeById(@PathVariable Integer receiveConsultationQuestionReId) {
        return R.ok(receiveConsultationQuestionReService.removeById(receiveConsultationQuestionReId));
    }

}
