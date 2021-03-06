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
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserAddOutlesListDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserObjectDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserByOutlesDTO;
import com.pig4cloud.pig.admin.api.entity.InsOutlesUser;
import com.pig4cloud.pig.admin.service.InsOutlesUserService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 机构/网点用户关联表
 *
 * @author xls
 * @date 2022-01-27 19:30:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/insoutlesuser")
@Api(value = "insoutlesuser", tags = "机构/网点用户关联表管理")
public class InsOutlesUserController {

	private final InsOutlesUserService insOutlesUserService;

	/**
	 * 分页查询
	 *
	 * @param page          分页对象
	 * @param insOutlesUser 机构/网点用户关联表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getInsOutlesUserPage(Page page, InsOutlesUser insOutlesUser) {
		return R.ok(insOutlesUserService.page(page, Wrappers.query(insOutlesUser)));
	}


	/**
	 * 通过id查询机构/网点用户关联表
	 *
	 * @param insOutlesUserId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{insOutlesUserId}")
	public R getById(@PathVariable("insOutlesUserId") Integer insOutlesUserId) {
		return R.ok(insOutlesUserService.getById(insOutlesUserId));
	}

	/**
	 * 新增机构/网点用户关联表
	 *
	 * @param insOutlesUser 机构/网点用户关联表
	 * @return R
	 */
	@ApiOperation(value = "新增机构/网点用户关联表", notes = "新增机构/网点用户关联表")
	@SysLog("新增机构/网点用户关联表")
	@PostMapping
	public R save(@RequestBody InsOutlesUser insOutlesUser) {
		return R.ok(insOutlesUserService.save(insOutlesUser));
	}

	/**
	 * 修改机构/网点用户关联表
	 *
	 * @param insOutlesUser 机构/网点用户关联表
	 * @return R
	 */
	@ApiOperation(value = "修改机构/网点用户关联表", notes = "修改机构/网点用户关联表")
	@SysLog("修改机构/网点用户关联表")
	@PutMapping
	public R updateById(@RequestBody InsOutlesUser insOutlesUser) {
		return R.ok(insOutlesUserService.updateById(insOutlesUser));
	}

	/**
	 * 通过id删除机构/网点用户关联表
	 *
	 * @param insOutlesUserId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除机构/网点用户关联表", notes = "通过id删除机构/网点用户关联表")
	@SysLog("通过id删除机构/网点用户关联表")
	public R removeById(@PathVariable Integer insOutlesUserId) {
		return R.ok(insOutlesUserService.removeById(insOutlesUserId));
	}

	/**
	 * 移除用户
	 *
	 * @param insOutlesUserId 员工id
	 * @return R
	 */
	@ApiOperation(value = "移除用户", notes = "移除用户")
	@SysLog("移除用户")
	@PutMapping("/removeInsOutlesUser/{insOutlesUserId}")
	public R removeInsOutlesUser(@PathVariable int insOutlesUserId) {
		int i = insOutlesUserService.removeInsOutlesUser(insOutlesUserId);
		if (i > 0) {
			return R.ok("移除用户成功！");
		} else {
			return R.failed("移除用户失败！");
		}
	}

	/**
	 * 根据类型、机构id或网点id查询相应机构/网点用户关联表信息
	 *
	 * @param type
	 * @param insId
	 * @param outlesId
	 * @return
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/queryUserList")
	public R queryUserList(Integer type, Integer insId, Integer outlesId) {
		return R.ok(insOutlesUserService.queryUserList(type, insId, outlesId));
	}

	/**
	 * 通过网点id和类型查询网点负责人
	 *
	 * @param type
	 * @param outlesId
	 * @return
	 */
	@ApiOperation(value = "通过网点id和类型查询网点负责人", notes = "通过网点id和类型查询网点负责人")
	@GetMapping("/getOutlesPrincipal")
	public R getOutlesPrincipal(Integer type, Integer outlesId) {
		return R.ok(insOutlesUserService.list(new LambdaQueryWrapper<InsOutlesUser>().eq(InsOutlesUser::getType,type).eq(InsOutlesUser::getOutlesId,outlesId)));
	}


	/**
	 * 添加用户
	 *
	 * @param insOutlesUserAddDTO
	 * @return R
	 */
	@ApiOperation(value = "添加用户", notes = "添加用户")
	@SysLog("添加用户")
	@PostMapping("/addInsOutlesUser")
	public R addInsOutlesUser(@RequestBody InsOutlesUserObjectDTO insOutlesUserAddDTO) throws Exception {
		int save = insOutlesUserService.addInsOutlesUser(insOutlesUserAddDTO);
		if (save <= 0) {
			return R.failed("添加失败");
		} else {
			return R.ok(save);
		}
	}

	/**
	 * 根据网点id添加这个网点的普通用户
	 *
	 * @param insOutlesUserAddOutlesListDTO
	 * @return R
	 */
	@ApiOperation(value = "根据网点id添加这个网点的普通用户", notes = "根据网点id添加这个网点的普通用户")
	@PostMapping("/addInsOutlesUserByOutlesId")
	public R addInsOutlesUserByOutlesId(@RequestBody InsOutlesUserAddOutlesListDTO insOutlesUserAddOutlesListDTO) throws Exception {
		int save = insOutlesUserService.addInsOutlesUserByOutlesId(insOutlesUserAddOutlesListDTO);
		if (save <= 0) {
			return R.failed("添加失败");
		} else {
			return R.ok(save);
		}
	}

	/**
	 * 根据用户id查询员工权限、网点名称
	 *
	 * @param userId id
	 * @return R
	 */
	@ApiOperation(value = "根据用户id查询员工权限、网点名称", notes = "根据用户id查询员工权限、网点名称")
	@GetMapping("/queryOutlesName/{userId}/{insId}")
	public R queryOutlesName(@PathVariable("userId") Integer userId, @PathVariable("insId") Integer insId) {
		return R.ok(insOutlesUserService.queryOutlesName(userId, insId));
	}

	/**
	 * 根据用户id查询员工权限、机构名称
	 *
	 * @param userId id
	 * @return R
	 */
	@ApiOperation(value = "根据用户id查询员工权限、机构名称", notes = "根据用户id查询员工权限、机构名称")
	@GetMapping("/queryInsName/{userId}")
	public R queryInsName(@PathVariable("userId") Integer userId) {
		return R.ok(insOutlesUserService.queryInsName(userId));
	}

	/**
	 * 根据用户id查询员工权限、机构名称
	 *
	 * @return R
	 */
	@ApiOperation(value = "根据用户id查询员工权限、机构名称", notes = "根据用户id查询员工权限、机构名称")
	@GetMapping("/queryInsOutlesUserByOutles")
	public R queryInsOutlesUserByOutles(Page page, InsOutlesUserByOutlesDTO insOutlesUserByOutlesDTO) {
		return R.ok(insOutlesUserService.queryInsOutlesUserByOutles(page, insOutlesUserByOutlesDTO));
	}

	/**
	 *	根据机构/网点用户关联表id查询机构/网点用户关联表与用户信息
	 * @param insOutlesUserId
	 * @return
	 */
	@ApiOperation(value = "根据机构/网点用户关联表id查询机构/网点用户关联表与用户信息", notes = "根据机构/网点用户关联表id查询机构/网点用户关联表与用户信息")
	@GetMapping("/queryById/{insOutlesUserId}")
	public R queryById(@PathVariable("insOutlesUserId") Integer insOutlesUserId) {
		return R.ok(insOutlesUserService.queryById(insOutlesUserId));
	}

	/**
	 * 修改网点用户关联表信息
	 * @param insOutlesUser
	 * @return
	 */
	@ApiOperation(value = "修改网点用户关联表信息", notes = "修改网点用户关联表信息")
	@PutMapping("/updateInsOutlesUser")
	public R updateInsOutlesUser(@RequestBody InsOutlesUser insOutlesUser) {
		int count = insOutlesUserService.updateInsOutlesUser(insOutlesUser);

		if (count > 0) {
			return R.ok("修改成功！");
		} else {
			return R.failed("修改失败！");
		}
	}

}
