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
import com.pig4cloud.pig.casee.entity.CaseePersonnel;
import com.pig4cloud.pig.casee.service.CaseePersonnelService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 案件人员表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/caseepersonnel" )
@Api(value = "caseepersonnel", tags = "案件人员表管理")
public class CaseePersonnelController {

    private final  CaseePersonnelService caseePersonnelService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param caseePersonnel 案件人员表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_caseepersonnel_get')" )
    public R getCaseePersonnelPage(Page page, CaseePersonnel caseePersonnel) {
        return R.ok(caseePersonnelService.page(page, Wrappers.query(caseePersonnel)));
    }


    /**
     * 通过id查询案件人员表
     * @param casePersonnelId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{casePersonnelId}" )
    @PreAuthorize("@pms.hasPermission('casee_caseepersonnel_get')" )
    public R getById(@PathVariable("casePersonnelId" ) Integer casePersonnelId) {
        return R.ok(caseePersonnelService.getById(casePersonnelId));
    }

    /**
     * 新增案件人员表
     * @param caseePersonnel 案件人员表
     * @return R
     */
    @ApiOperation(value = "新增案件人员表", notes = "新增案件人员表")
    @SysLog("新增案件人员表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_caseepersonnel_add')" )
    public R save(@RequestBody CaseePersonnel caseePersonnel) {
        return R.ok(caseePersonnelService.save(caseePersonnel));
    }

    /**
     * 修改案件人员表
     * @param caseePersonnel 案件人员表
     * @return R
     */
    @ApiOperation(value = "修改案件人员表", notes = "修改案件人员表")
    @SysLog("修改案件人员表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_caseepersonnel_edit')" )
    public R updateById(@RequestBody CaseePersonnel caseePersonnel) {
        return R.ok(caseePersonnelService.updateById(caseePersonnel));
    }

    /**
     * 通过id删除案件人员表
     * @param casePersonnelId id
     * @return R
     */
    @ApiOperation(value = "通过id删除案件人员表", notes = "通过id删除案件人员表")
    @SysLog("通过id删除案件人员表" )
    @DeleteMapping("/{casePersonnelId}" )
    @PreAuthorize("@pms.hasPermission('casee_caseepersonnel_del')" )
    public R removeById(@PathVariable Integer casePersonnelId) {
        return R.ok(caseePersonnelService.removeById(casePersonnelId));
    }
	/**
	 * 根据案件id和人员类型查询案件人员
	 * @param caseeId
	 * @param type （0-申请人，1-被执行人，2-担保人）
	 * @return
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getByCaseeId" )
	public R getByCaseeId( Integer caseeId,Integer type) {
		return R.ok(caseePersonnelService.queryByCaseeId(caseeId,type));
	}

}
