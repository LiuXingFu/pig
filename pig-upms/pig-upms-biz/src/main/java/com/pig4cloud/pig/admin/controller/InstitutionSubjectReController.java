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
import com.pig4cloud.pig.admin.api.entity.InstitutionSubjectRe;
import com.pig4cloud.pig.admin.service.InstitutionSubjectReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 机构主体关联关系
 *
 * @author yuanduo
 * @date 2022-02-25 00:26:06
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/institutionsubjectre" )
@Api(value = "institutionsubjectre", tags = "机构主体关联关系管理")
public class InstitutionSubjectReController {

    private final InstitutionSubjectReService institutionSubjectReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param institutionSubjectRe 机构主体关联关系
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    @PreAuthorize("@pms.hasPermission('casee_institutionsubjectre_get')" )
    public R getInstitutionSubjectRePage(Page page, InstitutionSubjectRe institutionSubjectRe) {
        return R.ok(institutionSubjectReService.page(page, Wrappers.query(institutionSubjectRe)));
    }


    /**
     * 通过id查询机构主体关联关系
     * @param insSubjectReId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{insSubjectReId}" )
    @PreAuthorize("@pms.hasPermission('casee_institutionsubjectre_get')" )
    public R getById(@PathVariable("insSubjectReId" ) Integer insSubjectReId) {
        return R.ok(institutionSubjectReService.getById(insSubjectReId));
    }

    /**
     * 新增机构主体关联关系
     * @param institutionSubjectRe 机构主体关联关系
     * @return R
     */
    @ApiOperation(value = "新增机构主体关联关系", notes = "新增机构主体关联关系")
    @SysLog("新增机构主体关联关系" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_institutionsubjectre_add')" )
    public R save(@RequestBody InstitutionSubjectRe institutionSubjectRe) {
        return R.ok(institutionSubjectReService.save(institutionSubjectRe));
    }

    /**
     * 修改机构主体关联关系
     * @param institutionSubjectRe 机构主体关联关系
     * @return R
     */
    @ApiOperation(value = "修改机构主体关联关系", notes = "修改机构主体关联关系")
    @SysLog("修改机构主体关联关系" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_institutionsubjectre_edit')" )
    public R updateById(@RequestBody InstitutionSubjectRe institutionSubjectRe) {
        return R.ok(institutionSubjectReService.updateById(institutionSubjectRe));
    }

    /**
     * 通过id删除机构主体关联关系
     * @param insSubjectReId id
     * @return R
     */
    @ApiOperation(value = "通过id删除机构主体关联关系", notes = "通过id删除机构主体关联关系")
    @SysLog("通过id删除机构主体关联关系" )
    @DeleteMapping("/{insSubjectReId}" )
    @PreAuthorize("@pms.hasPermission('casee_institutionsubjectre_del')" )
    public R removeById(@PathVariable Integer insSubjectReId) {
        return R.ok(institutionSubjectReService.removeById(insSubjectReId));
    }

	/**
	 * 根据机构id查询主体信息
	 * @param insId
	 * @return
	 */
	@ApiOperation(value = "根据机构id查询主体信息", notes = "根据机构id查询主体信息")
	@GetMapping("/getSubjectByInsId/{insId}")
	public R getSubjectByInsId(@PathVariable Integer insId) {
		return R.ok(institutionSubjectReService.getSubjectByInsId(insId));
	}

}
