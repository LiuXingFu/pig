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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.Form;
import com.pig4cloud.pig.admin.service.FormService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;

import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 表单表
 *
 * @author yy
 * @date 2021-09-06 16:49:33
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/form")
@Api(value = "form", tags = "表单表管理")
public class FormController {

	private final FormService formService;

	/**
	 * 分页查询
	 *
	 * @param page 分页对象
	 * @param form 表单表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getFormPage(Page page, Form form) {
		return R.ok(formService.page(page, Wrappers.query(form)));
	}

	/**
	 * 分页模糊条件查询
	 *
	 * @param page 分页对象
	 * @param form 表单表
	 * @return
	 */
	@ApiOperation(value = "分页模糊条件查询", notes = "分页查询")
	@GetMapping("/pageSearch")
	public R getFormPageSearch(Page page, Form form) {

		return R.ok(formService.pageSearch(page, form));
	}


	/**
	 * 通过id查询表单表
	 *
	 * @param formId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{formId}")
	public R getById(@PathVariable("formId") Integer formId) {
		return R.ok(formService.getById(formId));
	}

	/**
	 * 通过id集合查询表单集合
	 * @param ids
	 * @return
	 */
	@ApiOperation(value = "通过id集合查询表单集合", notes = "通过id集合查询表单集合")
	@GetMapping("/getByIds")
	public R getByIds(String ids){
		String[] idsVec = ids.split("\\,");
		List<Integer> idsInteger = new ArrayList();
		for(String idStr : idsVec){

			Pattern pattern = Pattern.compile("[0-9]*");
			Matcher isNum = pattern.matcher(idStr);
			if(isNum.matches()){
				idsInteger.add(Integer.parseInt(idStr));
			}
		}
		return R.ok(formService.getByIds(idsInteger));
	}


	/**
	 * 根据FORMKEY查询表单信息
	 *
	 * @param formKey 分页对象
	 * @return
	 */
	@ApiOperation(value = "根据FORMKEY查询表单信息", notes = "根据FORMKEY查询表单信息")
	@GetMapping("/getByKey")
	public R getByKey(String formKey) {
		return R.ok(formService.getOne(new LambdaQueryWrapper<Form>().eq(Form::getPropertyKey, formKey)));
	}

	/**
	 * 新增表单表
	 *
	 * @param form 表单表
	 * @return R
	 */
	@ApiOperation(value = "新增表单表", notes = "新增表单表")
	@SysLog("新增表单表")
	@PostMapping
	public R save(@RequestBody Form form) {
		form.setInsUserId(SecurityUtils.getUser().getId());
		return R.ok(formService.save(form));
	}

	/**
	 * 修改表单表
	 *
	 * @param form 表单表
	 * @return R
	 */
	@ApiOperation(value = "修改表单表", notes = "修改表单表")
	@SysLog("修改表单表")
	@PutMapping
	public R updateById(@RequestBody Form form) {
		return R.ok(formService.updateById(form));
	}

	/**
	 * 通过id删除表单表
	 *
	 * @param formId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除表单表", notes = "通过id删除表单表")
	@SysLog("通过id删除表单表")
	@DeleteMapping("/{formId}")
	public R removeById(@PathVariable Integer formId) {
		return R.ok(formService.removeById(formId));
	}

}
