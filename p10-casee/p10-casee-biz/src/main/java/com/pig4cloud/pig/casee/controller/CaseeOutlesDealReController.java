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
import com.pig4cloud.pig.casee.entity.CaseeOutlesDealRe;
import com.pig4cloud.pig.casee.service.CaseeOutlesDealReService;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 案件机构关联表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/caseeoutlesdealre" )
@Api(value = "caseeoutlesdealre", tags = "案件机构关联表管理")
public class CaseeOutlesDealReController {

    private final  CaseeOutlesDealReService caseeOutlesDealReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param caseeOutlesDealRe 案件机构关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_caseeoutlesdealre_get')" )
    public R getCaseeOutlesDealRePage(Page page, CaseeOutlesDealRe caseeOutlesDealRe) {
        return R.ok(caseeOutlesDealReService.page(page, Wrappers.query(caseeOutlesDealRe)));
    }


    /**
     * 通过id查询案件机构关联表
     * @param dealReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{dealReId}" )
    @PreAuthorize("@pms.hasPermission('casee_caseeoutlesdealre_get')" )
    public R getById(@PathVariable("dealReId" ) Integer dealReId) {
        return R.ok(caseeOutlesDealReService.getById(dealReId));
    }

    /**
     * 新增案件机构关联表
     * @param caseeOutlesDealRe 案件机构关联表
     * @return R
     */
    @ApiOperation(value = "新增案件机构关联表", notes = "新增案件机构关联表")
    @SysLog("新增案件机构关联表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_caseeoutlesdealre_add')" )
    public R save(@RequestBody CaseeOutlesDealRe caseeOutlesDealRe) {
        return R.ok(caseeOutlesDealReService.save(caseeOutlesDealRe));
    }

    /**
     * 修改案件机构关联表
     * @param caseeOutlesDealRe 案件机构关联表
     * @return R
     */
    @ApiOperation(value = "修改案件机构关联表", notes = "修改案件机构关联表")
    @SysLog("修改案件机构关联表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_caseeoutlesdealre_edit')" )
    public R updateById(@RequestBody CaseeOutlesDealRe caseeOutlesDealRe) {
        return R.ok(caseeOutlesDealReService.updateById(caseeOutlesDealRe));
    }

    /**
     * 通过id删除案件机构关联表
     * @param dealReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除案件机构关联表", notes = "通过id删除案件机构关联表")
    @SysLog("通过id删除案件机构关联表" )
    @DeleteMapping("/{dealReId}" )
    @PreAuthorize("@pms.hasPermission('casee_caseeoutlesdealre_del')" )
    public R removeById(@PathVariable Integer dealReId) {
        return R.ok(caseeOutlesDealReService.removeById(dealReId));
    }

	/**
	 * 根据案件id、机构id、网点id和类型查询案件机构关联表
	 * @param caseeId
	 * @param type
	 * @return
	 */
	@ApiOperation(value = "根据案件id、机构id、网点id和类型查询案件机构关联表", notes = "根据案件id、机构id、网点id和类型查询案件机构关联表")
	@GetMapping("/getByCaseeIdAndType" )
	public R getByCaseeIdAndType(Integer caseeId,Integer insId,Integer outlesId,Integer type) {
		return R.ok(caseeOutlesDealReService.queryCaseeOutlesDealRe(caseeId,insId,outlesId,type));
	}
}
