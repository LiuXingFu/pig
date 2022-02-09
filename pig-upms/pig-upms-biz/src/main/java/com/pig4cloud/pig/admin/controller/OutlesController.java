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
import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.api.entity.SysRole;
import com.pig4cloud.pig.admin.service.SysRoleService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.admin.service.OutlesService;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


/**
 * @author xiaojun
 * @date 2021-08-17 16:22:59
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/outles")
@Api(value = "outles", tags = "网点管理")
public class OutlesController {

	private final OutlesService outlesService;


	/**
	 * 分页查询
	 *
	 * @param page   分页对象
	 * @param outlesDTO
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getOutlesPage(Page page, OutlesDTO outlesDTO) {
		return R.ok(outlesService.pageOutles(page, outlesDTO));
	}

	/**
	 * 根据用户id查询用户网点集合
	 * @return
	 */
	@ApiOperation(value = "根据用户id查询用户网点集合", notes = "根据用户id查询用户网点集合")
	@GetMapping("/getOutlesListByUserId")
	public R getOutlesListByUserId(){
		return R.ok(outlesService.getOutlesListByUserId());
	}

	/**
	 * 根据员工id查询用户网点集合
	 * @return
	 */
	@ApiOperation(value = "根据员工id查询用户网点集合", notes = "根据用户id查询用户网点集合")
	@GetMapping("/getOutlesListByStaffId")
	public R getOutlesListByStaffId(){
		return R.ok(outlesService.getOutlesListByStaffId());
	}

	/**
	 * 通过id查询
	 *
	 * @param outlesId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/getById/{outlesId}")
	public R getById(@PathVariable("outlesId") Integer outlesId) {
		return R.ok(outlesService.getByIdOutles(outlesId));
	}

	/**
	 * 通过网点id批量查询
	 *
	 * @param outlesIds
	 * @return R
	 */
	@ApiOperation(value = "通过网点id批量查询", notes = "通过网点id批量查询")
	@GetMapping("/batchQueryOutlesId")
	public R BatchQueryOutlesId(@RequestParam(value = "outlesIds" ,required=false) List<Integer> outlesIds) {
		return R.ok(outlesService.batchQueryOutlesId(outlesIds));
	}

	/**
	 * 通过机构id和网点名称查询所有网点
	 *
	 * @param outlesDTO
	 * @return R
	 */
	@ApiOperation(value = "通过机构id和网点名称查询所有网点", notes = "通过机构id和网点名称查询所有网点")
	@GetMapping("/getInsIdOrOutlesNameList")
	public R getInsIdOrOutlesNameList(Page page, OutlesPageDTO outlesDTO) {
		return R.ok(outlesService.getInsIdOrOutlesNameList(page, outlesDTO));
	}

	/**
	 * 新增
	 *
	 * @param outlesDTO
	 * @return R
	 */
	@ApiOperation(value = "新增", notes = "新增")
	@SysLog("新增")
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('outles_add')")
	public R save(@RequestBody OutlesDTO outlesDTO) {
		return R.ok(outlesService.saveOutles(outlesDTO));
	}

	/**
	 * 修改
	 *
	 * @param outlesDTO
	 * @return R
	 */
	@ApiOperation(value = "修改", notes = "修改")
	@SysLog("修改")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('outles_edit')")
	public R updateById(@RequestBody OutlesDTO outlesDTO) {
		return R.ok(outlesService.updateByOutlesId(outlesDTO));
	}

	/**
	 * 通过id删除
	 *
	 * @param outlesId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除", notes = "通过id删除")
	@SysLog("通过id删除")
	@DeleteMapping("/{outlesId}")
	@PreAuthorize("@pms.hasPermission('outles_del')")
	public R removeById(@PathVariable Integer outlesId) {
		return R.ok(outlesService.removeById(outlesId));
	}

	/**
	 * 通过用户id和网点id查询该用户是否关联该网点
	 *
	 * @param userId,outlesId
	 * @return R
	 */
	@ApiOperation(value = "通过用户id和网点id查询该用户是否关联该网点", notes = "通过用户id和网点id查询该用户是否关联该网点")
	@GetMapping("/getUserIdOutlesIdByRelevanceInner")
	@Inner
	public R<Outles> getUserIdOutlesIdByRelevanceInner(Integer userId, Integer outlesId) {
		return R.ok(outlesService.getUserIdOutlesIdByRelevance(userId,outlesId));
	}

	/****************************************/

	/**
	 * 分页查询
	 *
	 * @param page   分页查询
	 * @param outlesPageDTO
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/queryPage")
	public R queryPage(Page page, OutlesPageDTO outlesPageDTO) {
		return R.ok(outlesService.queryPage(page, outlesPageDTO));
	}

	/**
	 * 添加网点
	 *
	 * @param outlesAddDTO
	 * @return R
	 */
	@ApiOperation(value = "添加网点", notes = "添加网点")
	@SysLog("添加网点")
	@PostMapping("/addOutles")
	public R addOutles(@RequestBody OutlesAddDTO outlesAddDTO) {
		return R.ok(outlesService.addOutles(outlesAddDTO));
	}

	/**
	 * 修改网点
	 *
	 * @param outlesModifyDTO
	 * @return R
	 */
	@ApiOperation(value = "修改网点", notes = "修改网点")
	@SysLog("修改网点")
	@PutMapping("/modifyOutlesById")
	public R modifyOutlesById(@RequestBody OutlesModifyDTO outlesModifyDTO) throws Exception {
		return R.ok(outlesService.modifyOutlesById(outlesModifyDTO));
	}

	/**
	 * 查询网点详情
	 *
	 * @param outlesId id
	 * @return R
	 */
	@ApiOperation(value = "查询网点详情", notes = "查询网点详情")
	@GetMapping("/queryById/{outlesId}")
	public R queryById(@PathVariable("outlesId") Integer outlesId) {
		return R.ok(outlesService.queryById(outlesId));
	}

	/**
	 * 根据机构id和用户id查询网点
	 *
	 * @param insId id
	 * @return R
	 */
	@ApiOperation(value = "根据机构id和用户id查询网点", notes = "根据机构id和用户id查询网点")
	@GetMapping("/queryByUserIdList/{insId}/{userId}")
	public R queryByUserIdList(@PathVariable("insId")Integer insId,@PathVariable("userId")Integer userId) {
		return R.ok(outlesService.queryByUserIdList(insId,userId));
	}

	/**
	 * 通过id查询
	 *
	 * @param outlesId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/queryByOutlesId/{outlesId}")
	public R queryByOutlesId(@PathVariable("outlesId") Integer outlesId) {
		return R.ok(outlesService.queryByOutlesId(outlesId));
	}

}
