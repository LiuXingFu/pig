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
import com.pig4cloud.pig.casee.entity.CaseeSubjectRe;
import com.pig4cloud.pig.casee.service.CaseeSubjectReService;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 案件主体关联案件表
 *
 * @author yy
 * @date 2022-01-10 15:13:16
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/caseesubjectre" )
@Api(value = "caseesubjectre", tags = "案件主体关联案件表管理")
public class CaseeSubjectReController {

    private final  CaseeSubjectReService caseeSubjectReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param caseeSubjectRe 案件主体关联案件表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_caseesubjectre_get')" )
    public R getCaseeSubjectRePage(Page page, CaseeSubjectRe caseeSubjectRe) {
        return R.ok(caseeSubjectReService.page(page, Wrappers.query(caseeSubjectRe)));
    }

    /**
     * 通过id查询案件主体关联案件表
     * @param subjectReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{subjectReId}" )
    @PreAuthorize("@pms.hasPermission('casee_caseesubjectre_get')" )
    public R getById(@PathVariable("subjectReId" ) Integer subjectReId) {
        return R.ok(caseeSubjectReService.getById(subjectReId));
    }

	/**
	 * 通过id查询案件主体关联案件表
	 * @param caseeId
	 * @Param type
	 * @return R
	 */
	@ApiOperation(value = "通过id查询案件主体关联案件表", notes = "通过id查询案件主体关联案件表")
	@GetMapping("/getByCaseeId" )
	public R getByCaseeId(Integer caseeId,@RequestParam(value = "type",required = false) Integer type,@RequestParam(value = "caseePersonnelType",required = false) Integer caseePersonnelType){
		return R.ok(caseeSubjectReService.getByCaseeId(caseeId, type,caseePersonnelType));
	}

	/**
	 * 通过项目id查询所有执行案件主体信息
	 * @param projectId
	 * @Param type
	 * @return R
	 */
	@ApiOperation(value = "通过项目id查询所有执行案件主体信息", notes = "通过项目id查询所有执行案件主体信息")
	@GetMapping("/getByProjectId/{projectId}" )
	public R getByProjectId(@PathVariable("projectId" )Integer projectId){
		return R.ok(caseeSubjectReService.getByProjectId(projectId));
	}

    /**
     * 新增案件主体关联案件表
     * @param caseeSubjectRe 案件主体关联案件表
     * @return R
     */
    @ApiOperation(value = "新增案件主体关联案件表", notes = "新增案件主体关联案件表")
    @SysLog("新增案件主体关联案件表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_caseesubjectre_add')" )
    public R save(@RequestBody CaseeSubjectRe caseeSubjectRe) {
        return R.ok(caseeSubjectReService.save(caseeSubjectRe));
    }

    /**
     * 修改案件主体关联案件表
     * @param caseeSubjectRe 案件主体关联案件表
     * @return R
     */
    @ApiOperation(value = "修改案件主体关联案件表", notes = "修改案件主体关联案件表")
    @SysLog("修改案件主体关联案件表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_caseesubjectre_edit')" )
    public R updateById(@RequestBody CaseeSubjectRe caseeSubjectRe) {
        return R.ok(caseeSubjectReService.updateById(caseeSubjectRe));
    }

    /**
     * 通过id删除案件主体关联案件表
     * @param subjectReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除案件主体关联案件表", notes = "通过id删除案件主体关联案件表")
    @SysLog("通过id删除案件主体关联案件表" )
    @DeleteMapping("/{subjectReId}" )
    @PreAuthorize("@pms.hasPermission('casee_caseesubjectre_del')" )
    public R removeById(@PathVariable Integer subjectReId) {
        return R.ok(caseeSubjectReService.removeById(subjectReId));
    }

}
