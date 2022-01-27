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
import com.pig4cloud.pig.admin.api.dto.StaffRelationshipAuthenticateDTO;
import com.pig4cloud.pig.admin.api.dto.UserInstitutionStaffDTO;
import com.pig4cloud.pig.admin.api.dto.UserOutlesStaffReDTO;
import com.pig4cloud.pig.admin.api.entity.UserInstitutionStaff;
import com.pig4cloud.pig.admin.service.UserInstitutionStaffService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 员工表
 *
 * @author yuanduo
 * @date 2021-09-08 16:46:23
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/userinstitutionstaff" )
@Api(value = "userinstitutionstaff", tags = "员工表管理")
public class UserInstitutionStaffController {

    private final UserInstitutionStaffService userInstitutionStaffService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param userInstitutionStaff 员工表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getUserInstitutionStaffPage(Page page, UserInstitutionStaff userInstitutionStaff) {
		SecurityUtils.getUser();
        return R.ok(userInstitutionStaffService.pageUserInstitutionStaff(page, userInstitutionStaff));
    }


    /**
     * 通过id查询员工表
     * @param staffId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{staffId}" )
    public R getById(@PathVariable("staffId" ) Integer staffId) {
        return R.ok(userInstitutionStaffService.getByIdUserInstitutionStaff(staffId));
    }

	/**
	 * 通过id查询需要编辑的员工信息
	 * @param staffId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询需要编辑的员工信息", notes = "通过id查询需要编辑的员工信息")
	@GetMapping("/getByIdStaff/{staffId}" )
	public R getByIdStaff(@PathVariable("staffId" ) Integer staffId) {
		return R.ok(userInstitutionStaffService.getByIdStaff(staffId));
	}

	/**
	 * 分页查询
	 * 根据网点id查询员工集合
	 * @return
	 */
	@ApiOperation(value = "根据网点id查询员工集合", notes = "根据网点id查询员工集合")
	@GetMapping("/getOutlesIdByStaffList")
	public R getOutlesIdByStaffList(Page page,Integer outlesId,Integer userType, String nickName){
		return R.ok(userInstitutionStaffService.getOutlesIdByStaffList(page,outlesId, userType,nickName));
	}

	@ApiOperation(value = "查询员工的所属网点id集合", notes = "查询员工的所属网点id集合")
	@GetMapping("/getStaffOutlesList")
	public R getStaffOutlesList(){
		return R.ok(userInstitutionStaffService.getStaffOutlesList());
	}

	/**
	 * 分页查询
	 * 根据网点id查询未关联网点与员工集合
	 * @return
	 */
	@ApiOperation(value = "根据网点id查询未关联网点与员工集合", notes = "根据网点id查询未关联网点与员工集合")
	@GetMapping("/pageNotOutlesStaff")
	public R pageNotOutlesStaff(Page page,Integer outlesId){
		return R.ok(userInstitutionStaffService.pageNotOutlesStaff(page,outlesId));
	}

	/**
	 * 根据认证目标id与认证类型查询员工集合
	 * @return
	 */
	@ApiOperation(value = "根据认证目标id与认证类型查询员工集合", notes = "根据认证目标id与认证类型查询员工集合")
	@GetMapping("/getStaffsByAuthenticateGoalId")
	public R getStaffsByAuthenticateGoalId(Page page, StaffRelationshipAuthenticateDTO staffRelationshipAuthenticateDTO){
		return R.ok(this.userInstitutionStaffService.getStaffsByAuthenticateGoalId(page, staffRelationshipAuthenticateDTO));
	}

    /**
     * 新增员工表
     * @param userInstitutionStaffDTO 员工表
     * @return R
     */
    @ApiOperation(value = "新增员工表", notes = "新增员工表")
    @SysLog("新增员工表" )
    @PostMapping
    public R save(@RequestBody UserInstitutionStaffDTO userInstitutionStaffDTO) throws Exception {
		int save = userInstitutionStaffService.saveUserInstitutionStaff(userInstitutionStaffDTO);
		if(save == 100){
			return R.failed("此员工已存在！");
		} else {
			return R.ok(save);
		}
    }

    /**
     * 修改员工表
     * @param userInstitutionStaffDTO 员工表
     * @return R
     */
    @ApiOperation(value = "修改员工表", notes = "修改员工表")
    @SysLog("修改员工表" )
    @PutMapping
//    @PreAuthorize("@pms.hasPermission('admin_userinstitutionstaff_edit')" )
    public R updateById(@RequestBody UserInstitutionStaffDTO userInstitutionStaffDTO) {
        return R.ok(userInstitutionStaffService.updateUserInstitutionStaff(userInstitutionStaffDTO));
    }

    /**
     * 通过id删除员工表
     * @param staffId id
     * @return R
     */
    @ApiOperation(value = "通过id删除员工表", notes = "通过id删除员工表")
    @SysLog("通过id删除员工表" )
    @DeleteMapping("/{staffId}" )
//    @PreAuthorize("@pms.hasPermission('admin_userinstitutionstaff_del')" )
    public R removeById(@PathVariable Integer staffId) {
        return R.ok(userInstitutionStaffService.removeById(staffId));
    }

	/**
	 * 通过id让离职员工
	 * @param staffId id
	 * @return R
	 */
	@ApiOperation(value = "通过id让离职员工", notes = "通过id让离职员工")
	@SysLog("通过id让离职员工" )
	@DeleteMapping("/resignStaff/{staffId}" )
    public R resignStaff(@PathVariable Integer staffId){
		return R.ok(userInstitutionStaffService.resignStaff(staffId));
	}


	/**
	 * 新增员工网点关联信息
	 * @param userOutlesStaffRe 新增员工网点关联实体
	 * @return R
	 */
	@ApiOperation(value = "新增员工网点关联信息", notes = "新增员工网点关联信息")
	@SysLog("新增员工网点关联信息" )
	@PostMapping("/saveUserOutlesStaffRe")
	public R saveUserOutlesStaffRe(@RequestBody UserOutlesStaffReDTO userOutlesStaffRe) {
		return R.ok(userInstitutionStaffService.saveUserOutlesStaffRe(userOutlesStaffRe));
	}

	/**
	 * 删除员工网点关联信息
	 * @param userOutlesStaffRe 删除员工网点关联信息
	 * @return R
	 */
	@ApiOperation(value = "删除员工网点关联信息", notes = "删除员工网点关联信息")
	@SysLog("删除员工网点关联信息" )
	@PostMapping("/delUserOutlesStaffRe")
	public R delUserOutlesStaffRe(@RequestBody UserOutlesStaffReDTO userOutlesStaffRe) {
		return R.ok(userInstitutionStaffService.delUserOutlesStaffRe(userOutlesStaffRe));
	}
}
