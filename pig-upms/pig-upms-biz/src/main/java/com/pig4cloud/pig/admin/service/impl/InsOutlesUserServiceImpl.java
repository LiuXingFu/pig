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
package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserAddDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserByOutlesDTO;
import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.vo.InsOutlesUserInsOutlesVO;
import com.pig4cloud.pig.admin.api.vo.InsOutlesUserListVO;
import com.pig4cloud.pig.admin.mapper.InsOutlesUserMapper;
import com.pig4cloud.pig.admin.service.*;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 机构/网点用户关联表
 *
 * @author xls
 * @date 2022-01-27 19:30:49
 */
@Service
public class InsOutlesUserServiceImpl extends ServiceImpl<InsOutlesUserMapper, InsOutlesUser> implements InsOutlesUserService {
	@Autowired
	InstitutionService institutionService;
	@Autowired
	SysUserService sysUserService;
	@Autowired
	SysDictItemService sysDictItemService;
	@Autowired
	SysRoleService sysRoleService;
	@Autowired
	StaffRoleService staffRoleService;
	@Autowired
	InsOutlesUserService insOutlesUserService;

	@Override
	@Transactional
	public int addInsOutlesUser(InsOutlesUserAddDTO insOutlesUserAddDTO){
		Institution institution = institutionService.getById(insOutlesUserAddDTO.getInsId());

		List<SysUser> userList = insOutlesUserAddDTO.getUserList();
		List<InsOutlesUser> insOutlesUserList = new ArrayList<>();
		userList.stream().forEach(item ->{
			// 用户已存在的情况下，验证此网点下是否有已存在记录
			if(Objects.nonNull(item.getUserId())){
				QueryWrapper<InsOutlesUser> queryWrapper = new QueryWrapper<>();
				queryWrapper.lambda().eq(InsOutlesUser::getDelFlag,0);
				queryWrapper.lambda().eq(InsOutlesUser::getUserId,item.getUserId());
				queryWrapper.lambda().eq(InsOutlesUser::getInsId,insOutlesUserAddDTO.getInsId());
				List<InsOutlesUser> users = insOutlesUserService.list(queryWrapper);
				if(users.size() > 0){
					throw new RuntimeException("此用户已是负责人或员工！");
				}
			}

			InsOutlesUser insOutlesUser = new InsOutlesUser();
			insOutlesUser.setInsId(institution.getInsId());
			insOutlesUser.setOutlesId(insOutlesUserAddDTO.getOutlesId());
			insOutlesUser.setType(insOutlesUserAddDTO.getType());
			insOutlesUser.setDelFlag(CommonConstants.STATUS_NORMAL);

			QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(SysUser::getDelFlag,0);
			queryWrapper.lambda().eq(SysUser::getPhone,item.getPhone());
			SysUser user = sysUserService.getOne(queryWrapper);
			// 判断用户是否存在
			if(Objects.nonNull(user)){
				insOutlesUser.setUserId(user.getUserId());
			}else{
				UserDTO sysUser = new UserDTO();
				sysUser.setPassword("123456");
				sysUser.setNickName(item.getActualName());
				sysUser.setPhone(item.getPhone());
				sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
				sysUser.setLockFlag("0");
				sysUser.setUsername(item.getPhone());
				sysUser.setActualName(item.getActualName());
				sysUserService.saveUser(sysUser);
				insOutlesUser.setUserId(sysUser.getUserId());
			}
			insOutlesUser.setEntryTime(LocalDateTime.now());
			insOutlesUserList.add(insOutlesUser);
		});
		this.saveBatch(insOutlesUserList);

		// 5.根据机构类型与字典类型查询角色标识
		SysDictItem sysDictItem = new SysDictItem();
		sysDictItem.setType("ins_type");
		sysDictItem.setValue(institution.getInsType().toString());

		SysDictItem dictItem = sysDictItemService.getDictBySysDictItem(sysDictItem);

		int userType = insOutlesUserAddDTO.getType();
		String typeName = "";
		if(userType==1){
			typeName = "_ADMIN";
		}else if(userType==2){
			typeName = "_STAFF_GENERAL";
		}
		// 6.根据角色标识查询角色信息
		SysRole role = this.sysRoleService.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleCode, dictItem.getLabel() + typeName).eq(SysRole::getDelFlag, 0));

		List<StaffRole> staffRoleList = new ArrayList<>();
		insOutlesUserList.stream().forEach(item ->{
			StaffRole staffRole = new StaffRole();
			staffRole.setRoleId(role.getRoleId());
			staffRole.setStaffId(item.getInsOutlesUserId());
			staffRoleList.add(staffRole);
		});
		staffRoleService.saveBatch(staffRoleList);

		return 1;
	}

	@Override
	@Transactional
	public int removeInsOutlesUser(Integer insOutlesUserId){
		int modify = 0;
		InsOutlesUser insOutlesUser = new InsOutlesUser();
		insOutlesUser.setDelFlag(CommonConstants.STATUS_DEL);
		insOutlesUser.setInsOutlesUserId(insOutlesUserId);
		modify = this.baseMapper.deleteById(insOutlesUser);
		// 删除角色权限
		staffRoleService.remove(new LambdaQueryWrapper<StaffRole>().eq(StaffRole::getStaffId,insOutlesUserId));
		return modify;
	}

	@Override
	@Transactional
	public List<InsOutlesUserListVO> queryUserList(Integer type,Integer insId, Integer outlesId){
		return this.baseMapper.selectUserList(type,insId,outlesId);
	}

	@Override
	public List<InsOutlesUserInsOutlesVO> queryOutlesName(Integer userId,Integer insId){
		return this.baseMapper.selectOutlesName(userId,insId);
	}

	@Override
	public List<InsOutlesUserInsOutlesVO> queryInsName(Integer userId){
		return this.baseMapper.selectInsName(userId);
	}

	@Override
	public IPage<InsOutlesUserInsOutlesVO> queryInsOutlesUserByOutles(Page page, InsOutlesUserByOutlesDTO insOutlesUserByOutlesDTO) {
		return this.baseMapper.queryInsOutlesUserByOutles(page, insOutlesUserByOutlesDTO);
	}


}
