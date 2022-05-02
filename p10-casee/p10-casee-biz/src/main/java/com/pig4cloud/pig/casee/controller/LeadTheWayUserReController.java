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
import com.pig4cloud.pig.casee.entity.LeadTheWayUserRe;
import com.pig4cloud.pig.casee.service.LeadTheWayUserReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 业务人员关联引领看样表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/leadthewayuserre" )
@Api(value = "leadthewayuserre", tags = "业务人员关联引领看样表管理")
public class LeadTheWayUserReController {

    private final  LeadTheWayUserReService leadTheWayUserReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param leadTheWayUserRe 业务人员关联引领看样表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_leadthewayuserre_get')" )
    public R getLeadTheWayUserRePage(Page page, LeadTheWayUserRe leadTheWayUserRe) {
        return R.ok(leadTheWayUserReService.page(page, Wrappers.query(leadTheWayUserRe)));
    }


    /**
     * 通过id查询业务人员关联引领看样表
     * @param leadTheWayUserReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{leadTheWayUserReId}" )
    @PreAuthorize("@pms.hasPermission('casee_leadthewayuserre_get')" )
    public R getById(@PathVariable("leadTheWayUserReId" ) Integer leadTheWayUserReId) {
        return R.ok(leadTheWayUserReService.getById(leadTheWayUserReId));
    }

    /**
     * 新增业务人员关联引领看样表
     * @param leadTheWayUserRe 业务人员关联引领看样表
     * @return R
     */
    @ApiOperation(value = "新增业务人员关联引领看样表", notes = "新增业务人员关联引领看样表")
    @SysLog("新增业务人员关联引领看样表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_leadthewayuserre_add')" )
    public R save(@RequestBody LeadTheWayUserRe leadTheWayUserRe) {
        return R.ok(leadTheWayUserReService.save(leadTheWayUserRe));
    }

    /**
     * 修改业务人员关联引领看样表
     * @param leadTheWayUserRe 业务人员关联引领看样表
     * @return R
     */
    @ApiOperation(value = "修改业务人员关联引领看样表", notes = "修改业务人员关联引领看样表")
    @SysLog("修改业务人员关联引领看样表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_leadthewayuserre_edit')" )
    public R updateById(@RequestBody LeadTheWayUserRe leadTheWayUserRe) {
        return R.ok(leadTheWayUserReService.updateById(leadTheWayUserRe));
    }

    /**
     * 通过id删除业务人员关联引领看样表
     * @param leadTheWayUserReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除业务人员关联引领看样表", notes = "通过id删除业务人员关联引领看样表")
    @SysLog("通过id删除业务人员关联引领看样表" )
    @DeleteMapping("/{leadTheWayUserReId}" )
    @PreAuthorize("@pms.hasPermission('casee_leadthewayuserre_del')" )
    public R removeById(@PathVariable Integer leadTheWayUserReId) {
        return R.ok(leadTheWayUserReService.removeById(leadTheWayUserReId));
    }

}
