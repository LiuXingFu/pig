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
import com.pig4cloud.pig.admin.api.dto.CertificationRelationshipDTO;
import com.pig4cloud.pig.admin.service.InstitutionAssociateService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.admin.api.entity.InstitutionAssociate;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 机构关联表
 *
 * @author yuanduo
 * @date 2021-09-03 11:01:07
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/institutionassociate" )
@Api(value = "institutionassociate", tags = "机构关联表管理")
public class InstitutionAssociateController {

    private final InstitutionAssociateService institutionAssociateService;

    /**
     * 根据机构ID分页查询合作机构
     * @param page 分页对象
     * @param institutionAssociate 机构关联表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getInstitutionAssociatePage(Page page, InstitutionAssociate institutionAssociate) {
		return R.ok(institutionAssociateService.pageInstitutionAssociate(page, institutionAssociate));
	}


    /**
     * 通过id查询机构关联表
     * @param associateId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{associateId}" )
    public R getById(@PathVariable("associateId" ) Integer associateId) {
        return R.ok(institutionAssociateService.queryById(associateId));
    }

    /**
     * 新增机构关联表
     * @param institutionAssociate 机构关联表
     * @return R
     */
    @ApiOperation(value = "新增机构关联表", notes = "新增机构关联表")
    @SysLog("新增机构关联表" )
    @PostMapping
    public R save(@RequestBody InstitutionAssociate institutionAssociate) throws Exception {
		int save = institutionAssociateService.saveInstitutionAssociate(institutionAssociate);
		return R.ok(save);
    }

    /**
     * 修改机构关联表
     * @param institutionAssociate 机构关联表
     * @return R
     */
    @ApiOperation(value = "修改机构关联表", notes = "修改机构关联表")
    @SysLog("修改机构关联表" )
    @PutMapping
    public R updateById(@RequestBody InstitutionAssociate institutionAssociate) {
        return R.ok(institutionAssociateService.updateById(institutionAssociate));
    }

    /**
     * 通过id删除机构关联表
     * @param associateId id
     * @return R
     */
    @ApiOperation(value = "通过id删除机构关联表", notes = "通过id删除机构关联表")
    @SysLog("通过id删除机构关联表" )
    @DeleteMapping("/{associateId}" )
    public R removeById(@PathVariable Integer associateId) {
        return R.ok(institutionAssociateService.removeById(associateId));
    }

	/**
	 * 通过id解除关联
	 * @param associateId id
	 * @return R
	 */
	@ApiOperation(value = "通过id解除关联", notes = "通过id解除关联")
	@SysLog("通过id删除机构关联表" )
	@DeleteMapping("/dismissById/{associateId}" )
    public R dismissById(@PathVariable Integer associateId){
		return R.ok(institutionAssociateService.dismissById(associateId));
	}

	/**
	 * 认证关联关系
	 * @param certificationRelationshipDTO
	 * @return R
	 */
	@ApiOperation(value = "认证关联关系", notes = "认证关联关系")
	@SysLog("认证关联关系" )
	@PostMapping("/certificationRelationship" )
	public R certificationRelationship(@RequestBody CertificationRelationshipDTO certificationRelationshipDTO){
		return R.ok(institutionAssociateService.certificationRelationship(certificationRelationshipDTO));
	}

	/**
	 * 查询合作法院
	 * @return
	 */
	@ApiOperation(value = "查询合作法院", notes = "查询合作法院")
	@GetMapping("/getAssociateCourt/{courtName}")
	public R getAssociateCourt(@PathVariable("courtName") String courtName) {
		return R.ok(institutionAssociateService.getAssociateCourt(courtName));
	}

	/**
	 * 根据机构id和网点id查询合作法院
	 * @param insId
	 * @param outlesId
	 * @return
	 */
	@ApiOperation(value = "根据机构id和网点id查询合作法院", notes = "根据机构id和网点id查询合作法院")
	@GetMapping("/getAssociateCourtByInsIdAndOutlesId")
	public R getAssociateCourtByInsIdAndOutlesId(Integer insId, Integer outlesId, String courtName) {
		return R.ok(institutionAssociateService.getAssociateCourtByInsIdAndOutlesId(insId, outlesId, courtName));
	}
}
