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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.*;
import com.pig4cloud.pig.admin.service.OutlesService;
import com.pig4cloud.pig.admin.service.UserInstitutionStaffService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.service.InstitutionService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @author xiaojun
 * @date 2021-08-17 16:22:59
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/institution")
@Api(value = "institution", tags = "机构表管理")
public class InstitutionController {

	private final InstitutionService institutionService;

	@Autowired
	private OutlesService outlesService;

	@Autowired
	private UserInstitutionStaffService userInstitutionStaffService;

	@Autowired
	private SecurityUtilsService securityUtilsService;

	/**
	 * 分页查询
	 *
	 * @param page        分页对象
	 * @param institution
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getInstitutionPage(Page page, Institution institution) {
		return R.ok(institutionService.getInstitutionPage(page, institution));
	}


	/**
	 * 通过id查询
	 *
	 * @param insId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{insId}")
	public R getById(@PathVariable("insId") Integer insId) {
		return R.ok(institutionService.getInstitutionById(insId));
	}

	/**
	 * 查询当前登录用户所属机构id
	 *
	 * @param
	 * @return R
	 */
	@ApiOperation(value = "查询当前登录用户所属机构id", notes = "查询当前登录用户所属机构id")
	@GetMapping("/getByInsId")
	public R getByInsId() {
		return R.ok(securityUtilsService.getCacheUser().getInsId());
	}

	/**
	 * 根据机构insName模糊搜索合作机构
	 *
	 * @param insName
	 * @return
	 */
	@ApiOperation(value = "根据机构insName模糊搜索合作机构", notes = "根据机构insName模糊搜索合作机构")
	@GetMapping("/getByInsName")
	public R getByInsName(Page page, String insName) {
		return R.ok(institutionService.getByInsName(page, insName));
	}

	/**
	 * 根据相应id与相应类型查询组织架构
	 *
	 * @return
	 */
	@ApiOperation(value = "根据相应id与相应类型查询组织架构", notes = "根据相应id与相应类型查询组织架构")
	@GetMapping("/organizationQueryList")
	public R organizationQueryList(OrganizationQueryDTO organizationQueryDTO) {

		if (organizationQueryDTO.getType() == 0 || organizationQueryDTO.getType() == 2) {
			return R.ok(institutionService.queryAssociatedInstitutions(organizationQueryDTO));
		} else {
			return R.ok(institutionService.queryCurrentInstitution(organizationQueryDTO));
		}
	}

	/**
	 * 查询机构是否有简称
	 *
	 * @return
	 */
	@ApiOperation(value = "查询机构是否有简称", notes = "查询机构是否有简称")
	@GetMapping("/getInstitutionAlias")
	public R getInstitutionAlias() {
		return R.ok(institutionService.getInstitutionAlias());
	}

	/**
	 * 查询机构名称是否存在
	 *
	 * @return
	 */
	@ApiOperation(value = "查询机构名称是否存在", notes = "查询机构名称是否存在")
	@GetMapping("/getInstitutionIsInsName/{insName}")
	public R getInstitutionIsInsName(@PathVariable("insName") String insName){
		return R.ok(institutionService.getInstitutionIsInsName(insName));
	}

	/**
	 * 新增
	 *
	 * @param institutionDTO
	 * @return R
	 */
	@ApiOperation(value = "新增", notes = "新增")
	@SysLog("新增")
	@PostMapping
	public R save(@RequestBody InstitutionDTO institutionDTO) throws Exception {
		int save = institutionService.saveInstitution(institutionDTO);
		if (save == -1) {
			return R.failed("手机已被注册，请重新录入！");
		} else {
			return R.ok(save);
		}

	}

	/**
	 * 修改
	 *
	 * @param institutionDTO
	 * @return R
	 */
	@ApiOperation(value = "修改", notes = "修改")
	@SysLog("修改")
	@PutMapping
	public R updateInstitution(@RequestBody InstitutionDTO institutionDTO) throws Exception {
		return R.ok(institutionService.updateInstitution(institutionDTO));
	}

	/**
	 * 通过id删除
	 *
	 * @param insId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除", notes = "通过id删除")
	@SysLog("通过id删除")
	@DeleteMapping("/{insId}")
	public R removeById(@PathVariable Integer insId) {
		return R.ok(institutionService.removeById(insId));
	}

	/**
	 * 通过id禁用机构
	 *
	 * @param insId id
	 * @return R
	 */
	@ApiOperation(value = "通过id禁用机构", notes = "通过id禁用机构")
	@SysLog("通过id禁用机构")
	@DeleteMapping("/disableById/{insId}")
	public R disableById(@PathVariable Integer insId) {
		return R.ok(institutionService.disableById(insId));
	}


	/******************************************************************/

	/**
	 * 机构列表分页查询
	 * @param page        分页对象
	 * @param institutionPageDTO
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/pageList")
	public R queryPage(Page page, InstitutionPageDTO institutionPageDTO) {
		return R.ok(institutionService.queryPage(page,institutionPageDTO));
	}

	/**
	 * 添加机构
	 *
	 * @param institutionAddDTO
	 * @return R
	 */
	@ApiOperation(value = "添加机构", notes = "添加机构")
	@SysLog("添加机构")
	@PostMapping("/addInstitution")
	public R addInstitution(@RequestBody InstitutionAddDTO institutionAddDTO) throws Exception {
		int save = institutionService.addInstitution(institutionAddDTO);
		if (save<=0) {
			return R.failed("添加失败");
		} else {
			return R.ok(save);
		}
	}

	/**
	 * 修改机构
	 *
	 * @param institutionModifyDTO
	 * @return R
	 */
	@ApiOperation(value = "修改机构", notes = "修改机构")
	@SysLog("修改机构")
	@PutMapping("/modifyInstitutionById")
	public R modifyInstitutionById(@RequestBody InstitutionModifyDTO institutionModifyDTO) throws Exception {
		return R.ok(institutionService.modifyInstitutionById(institutionModifyDTO));
	}

	/**
	 * 查询机构详情
	 *
	 * @param insId id
	 * @return R
	 */
	@ApiOperation(value = "查询机构详情", notes = "查询机构详情")
	@GetMapping("/queryById/{insId}")
	public R queryById(@PathVariable("insId") Integer insId) {
		return R.ok(institutionService.queryById(insId));
	}


	/**
	 * 根据用户id查询相关机构
	 * @return
	 */
	@ApiOperation(value = "根据用户id查询相关机构", notes = "根据用户id查询相关机构")
	@GetMapping("/queryByUserIdList/{userId}")
	public R queryByUserIdList(@PathVariable("userId")Integer userId) {
		return R.ok(institutionService.queryByUserIdList(userId));
	}

	/**
	 * 通过id查询
	 *
	 * @param insId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/queryByInsId/{insId}")
	public R queryByInsId(@PathVariable("insId") Integer insId) {
		return R.ok(institutionService.queryByInsId(insId));
	}

	/**
	 * 查询机构下拉框组件集合
	 * @param insOulesSelectDTO
	 * @return
	 */
	@ApiOperation(value = "查询机构下拉框组件集合", notes = "查询机构下拉框组件集合")
	@GetMapping("/queryInsSelect")
	public R queryInsSelect(InstitutionSelectDTO insOulesSelectDTO) {
		return R.ok(institutionService.queryInsSelect(insOulesSelectDTO));
	}


}
