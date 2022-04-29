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
import com.pig4cloud.pig.casee.entity.MakeAnAppointment;
import com.pig4cloud.pig.casee.service.MakeAnAppointmentService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 预约看样表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/makeanappointment" )
@Api(value = "makeanappointment", tags = "预约看样表管理")
public class MakeAnAppointmentController {

    private final  MakeAnAppointmentService makeAnAppointmentService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param makeAnAppointment 预约看样表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_makeanappointment_get')" )
    public R getMakeAnAppointmentPage(Page page, MakeAnAppointment makeAnAppointment) {
        return R.ok(makeAnAppointmentService.page(page, Wrappers.query(makeAnAppointment)));
    }


    /**
     * 通过id查询预约看样表
     * @param makeAnAppointment id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{makeAnAppointment}" )
    @PreAuthorize("@pms.hasPermission('casee_makeanappointment_get')" )
    public R getById(@PathVariable("makeAnAppointment" ) Integer makeAnAppointment) {
        return R.ok(makeAnAppointmentService.getById(makeAnAppointment));
    }

    /**
     * 新增预约看样表
     * @param makeAnAppointment 预约看样表
     * @return R
     */
    @ApiOperation(value = "新增预约看样表", notes = "新增预约看样表")
    @SysLog("新增预约看样表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_makeanappointment_add')" )
    public R save(@RequestBody MakeAnAppointment makeAnAppointment) {
        return R.ok(makeAnAppointmentService.save(makeAnAppointment));
    }

    /**
     * 修改预约看样表
     * @param makeAnAppointment 预约看样表
     * @return R
     */
    @ApiOperation(value = "修改预约看样表", notes = "修改预约看样表")
    @SysLog("修改预约看样表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_makeanappointment_edit')" )
    public R updateById(@RequestBody MakeAnAppointment makeAnAppointment) {
        return R.ok(makeAnAppointmentService.updateById(makeAnAppointment));
    }

    /**
     * 通过id删除预约看样表
     * @param makeAnAppointment id
     * @return R
     */
    @ApiOperation(value = "通过id删除预约看样表", notes = "通过id删除预约看样表")
    @SysLog("通过id删除预约看样表" )
    @DeleteMapping("/{makeAnAppointment}" )
    @PreAuthorize("@pms.hasPermission('casee_makeanappointment_del')" )
    public R removeById(@PathVariable Integer makeAnAppointment) {
        return R.ok(makeAnAppointmentService.removeById(makeAnAppointment));
    }

}
