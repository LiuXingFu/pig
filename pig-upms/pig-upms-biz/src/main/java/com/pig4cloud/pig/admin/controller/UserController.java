/*
 * Copyright (c) 2020 pig4cloud Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.api.dto.UserInfo;
import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.service.SysUserService;
import com.pig4cloud.pig.admin.service.UserOutlesStaffReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Api(value = "user", tags = "用户管理模块")
public class UserController {

	private final SecurityUtilsService securityUtilsService;
	private final SysUserService userService;

	@Autowired
	private UserOutlesStaffReService userOutlesStaffReService;

	/**
	 * 获取当前用户全部信息
	 * @return 用户信息
	 */
	@GetMapping(value = { "/info" })
	public R info() {
		PigUser cacheUser = securityUtilsService.getCacheUser();
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, cacheUser.getUsername()));
		if (user == null) {
			return R.failed("获取当前用户信息失败");
		}
		UserInfo userRes = userService.getUserInfo(user);
		userRes.setSelectOutlesId(cacheUser.getOutlesId());
		return R.ok(userRes);
	}

	/**
	 * 获取指定用户全部信息
	 * @return 用户信息
	 */
	@Inner
	@GetMapping("/info/{username}")
	public R info(@PathVariable String username) {
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
		if (user == null) {
			return R.failed(String.format("用户信息为空 %s", username));
		}
		return R.ok(userService.getUserInfo(user));
	}

	/**
	 * 获取指定用户全部信息通过手机号
	 * @return 用户信息
	 */
	@Inner
	@GetMapping("/infoByPhone/{phone}")
	public R infoByPhone(@PathVariable String phone) {
		SysUser user = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getPhone, phone));
		if (user == null) {
			return R.failed(String.format("用户信息为空 %s", phone));
		}
		return R.ok(userService.getUserInfo(user));
	}

	/**
	 * 通过ID查询用户信息
	 * @param id ID
	 * @return 用户信息
	 */
	@GetMapping("/{id}")
	public R user(@PathVariable Integer id) {
		return R.ok(userService.getUserVoById(id));
	}

	/**
	 * 更新clientId
	 * @param username
	 * @param clientId
	 * @return 用户信息
	 */
	@Inner
	@GetMapping("/updateUserClientInfo")
	public R updateUserClientInfo(String username, String clientId) {
		return R.ok(userService.updateUserClientInfo(username, clientId));
	}


	/**
	 * 根据用户名查询用户信息
	 * @param username 用户名
	 * @return
	 */
	@GetMapping("/details/{username}")
	public R user(@PathVariable String username) {
		SysUser condition = new SysUser();
		condition.setUsername(username);
		return R.ok(userService.getOne(new QueryWrapper<>(condition)));
	}

	/**
	 * 删除用户信息
	 * @param id ID
	 * @return R
	 */
	@SysLog("删除用户信息")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_user_del')")
	public R userDel(@PathVariable Integer id) {
		SysUser sysUser = userService.getById(id);
		return R.ok(userService.removeUserById(sysUser));
	}

	/**
	 * 添加用户
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@SysLog("添加用户")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_user_add')")
	public R user(@RequestBody UserDTO userDto) {
		return R.ok(userService.saveUser(userDto));
	}

	/**
	 * 更新用户信息
	 * @param userDto 用户信息
	 * @return R
	 */
	@SysLog("更新用户信息")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_user_edit')")
	public R updateUser(@Valid @RequestBody UserDTO userDto) {
		return R.ok(userService.updateUser(userDto));
	}

	/**
	 * 分页查询用户
	 * @param page 参数集
	 * @param userDTO 查询参数列表
	 * @return 用户集合
	 */
	@GetMapping("/page")
	public R getUserPage(Page page, UserDTO userDTO) {
		return R.ok(userService.getUserWithRolePage(page, userDTO));
	}

	/**
	 * 修改个人信息
	 * @param userDto userDto
	 * @return success/false
	 */
	@SysLog("修改个人信息")
	@PutMapping("/edit")
	public R updateUserInfo(@Valid @RequestBody UserDTO userDto) {
		return R.ok(userService.updateUserInfo(userDto));
	}

	/**
	 * @param username 用户名称
	 * @return 上级部门用户列表
	 */
	@GetMapping("/ancestor/{username}")
	public R listAncestorUsers(@PathVariable String username) {
		return R.ok(userService.listAncestorUsersByUsername(username));
	}



	/**
	 * 更新选择的当前网点
	 * @param outles 网点
	 * @return R
	 */
	@SysLog("更新选择的当前网点")
	@PutMapping("/selectUsedOutles")
	public R selectUsedOutles( @RequestBody Outles outles) {
		return R.ok(userService.selectUsedOutles(outles.getOutlesId()));
	}

	/**
	 * 通过网点id查询用户信息
	 * @param outlesId 网点id
	 * @return
	 */
	@ApiOperation(value = "通过网点id查询用户信息", notes = "通过网点id查询用户信息")
	@GetMapping("/queryByOutlesId")
	public R queryByOutlesId(Integer outlesId) {
		return R.ok(userOutlesStaffReService.queryByOutlesId(outlesId));
	}

	/***********************************************************/

	/**
	 * 根据电话查询用户信息
	 * @param phone
	 * @return
	 */
	@ApiOperation(value = "根据电话查询用户信息", notes = "根据电话查询用户信息")
	@GetMapping("/queryUserByPhone/{phone}")
	public R queryUserByPhone(@PathVariable String phone){
		return R.ok(userService.queryUserByPhone(phone));
	}

}
