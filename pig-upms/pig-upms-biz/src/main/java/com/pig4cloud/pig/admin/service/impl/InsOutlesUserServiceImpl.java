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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserAddDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserModifyDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserObjectDTO;
import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.vo.InsOutlesUserListVO;
import com.pig4cloud.pig.admin.mapper.InsOutlesUserMapper;
import com.pig4cloud.pig.admin.service.*;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

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
	@Autowired
	OutlesService outlesService;

	@Override
	@Transactional
	public int addInsOutlesUser(InsOutlesUserAddDTO insOutlesUserAddDTO) {
		int add = 0;

		Institution institution = institutionService.getById(insOutlesUserAddDTO.getInsId());

		List<InsOutlesUser> insOutlesUserList = setInsOutlesUserList(insOutlesUserAddDTO, institution);

		this.saveBatch(insOutlesUserList);

		List<StaffRole> staffRoleList = setStaffRoleList(insOutlesUserAddDTO, insOutlesUserList, institution);

		staffRoleService.saveBatch(staffRoleList);

		return add = 1;
	}


	@Override
	@Transactional
	public int removeInsOutlesUser(Integer insOutlesUserId) {
		int modify = 0;
		InsOutlesUser insOutlesUser = new InsOutlesUser();
		insOutlesUser.setDelFlag(CommonConstants.STATUS_DEL);
		insOutlesUser.setInsOutlesUserId(insOutlesUserId);
		modify = this.baseMapper.deleteById(insOutlesUser);
		// 删除角色权限
		staffRoleService.remove(new LambdaQueryWrapper<StaffRole>().eq(StaffRole::getStaffId, insOutlesUserId));
		return modify;
	}

	/**
	 * 根据类型、机构id或网点id查询相应机构/网点用户关联表信息
	 *
	 * @param type
	 * @param insId
	 * @param outlesId
	 * @return
	 */
	@Override
	@Transactional
	public List<InsOutlesUserListVO> queryUserList(Integer type, Integer insId, Integer outlesId) {
		return this.baseMapper.selectUserList(type, insId, outlesId);
	}

	@Override
	@Transactional
	public int updateInsOutlesUser(InsOutlesUserModifyDTO insOutlesUserModifyDTO) {

		Integer update = 0;

		if(Objects.nonNull(insOutlesUserModifyDTO.getOutlesId())){
			Outles outles = this.outlesService.getById(insOutlesUserModifyDTO.getOutlesId());
			insOutlesUserModifyDTO.setInsId(outles.getInsId());
		}

		insOutlesUserModifyDTO.setType(1);

		Institution institution = institutionService.getById(insOutlesUserModifyDTO.getInsId());

		List<Integer> insOutlesUserIds = this.list(new LambdaQueryWrapper<InsOutlesUser>()
				.eq(InsOutlesUser::getInsId, institution.getInsId())
				.eq(InsOutlesUser::getOutlesId, insOutlesUserModifyDTO.getOutlesId())
				.eq(InsOutlesUser::getType, insOutlesUserModifyDTO.getType())
				.eq(InsOutlesUser::getDelFlag, 0)).stream().map(insOutlesUser -> insOutlesUser.getInsOutlesUserId()).collect(Collectors.toList());

//		this.staffRoleService.remove(new LambdaQueryWrapper<StaffRole>().in(StaffRole::getStaffId, insOutlesUserIds));
//
//		this.insOutlesUserService.removeInsOutlesUser(new LambdaQueryWrapper<InsOutlesUser>().in(InsOutlesUser::getInsOutlesUserId, insOutlesUserIds));

		this.insOutlesUserService.removeInsOutlesUserList(insOutlesUserIds);

		List<InsOutlesUser> insOutlesUserList = setInsOutlesUserList(insOutlesUserModifyDTO, institution);

		this.saveBatch(insOutlesUserList);

		List<StaffRole> staffRoleList = setStaffRoleList(insOutlesUserModifyDTO, insOutlesUserList, institution);

		staffRoleService.saveBatch(staffRoleList);

		return update = 1;
	}

	@Override
	public int removeInsOutlesUserList(List<Integer> insOutlesUserIds) {
		int modify = 0;
		modify = this.baseMapper.deleteBatchIds(insOutlesUserIds);
		// 删除角色权限
		staffRoleService.remove(new LambdaQueryWrapper<StaffRole>().in(StaffRole::getStaffId, insOutlesUserIds));
		return modify;
	}

	/**
	 * 添加机构/网点用户关联集合
	 * @param insOutlesUserObjectDTO
	 * @param institution
	 * @return
	 */
	private List<InsOutlesUser> setInsOutlesUserList(InsOutlesUserObjectDTO insOutlesUserObjectDTO, Institution institution) {

		List<SysUser> userList = insOutlesUserObjectDTO.getUserList();
		List<InsOutlesUser> insOutlesUserList = new ArrayList<>();
		userList.stream().forEach(item -> {
			// 用户已存在的情况下，验证此网点下是否有已存在记录
			if (Objects.nonNull(item.getUserId())) {
				QueryWrapper<InsOutlesUser> queryWrapper = new QueryWrapper<>();
//				queryWrapper.lambda().eq(InsOutlesUser::getDelFlag,0);
				queryWrapper.lambda().eq(InsOutlesUser::getUserId, item.getUserId());
				queryWrapper.lambda().eq(InsOutlesUser::getInsId, insOutlesUserObjectDTO.getInsId());
				List<InsOutlesUser> users = new ArrayList<>();
				if (Objects.nonNull(insOutlesUserObjectDTO.getOutlesId())) {
					queryWrapper.lambda().eq(InsOutlesUser::getOutlesId, insOutlesUserObjectDTO.getOutlesId());
				}
				users = insOutlesUserService.list(queryWrapper);
				if (users.size() > 0) {
					throw new RuntimeException(item.getActualName() + "已是此网点负责人或员工！");
				}
			}

			InsOutlesUser insOutlesUser = new InsOutlesUser();
			insOutlesUser.setInsId(institution.getInsId());
			insOutlesUser.setOutlesId(insOutlesUserObjectDTO.getOutlesId());
			insOutlesUser.setType(insOutlesUserObjectDTO.getType());
			insOutlesUser.setDelFlag(CommonConstants.STATUS_NORMAL);

			QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(SysUser::getDelFlag, 0);
			queryWrapper.lambda().eq(SysUser::getPhone, item.getPhone());
			SysUser user = sysUserService.getOne(queryWrapper);
			// 判断用户是否存在
			if (Objects.nonNull(user)) {
				insOutlesUser.setUserId(user.getUserId());
			} else {
				UserDTO sysUser = new UserDTO();
				sysUser.setPassword("a123456");
				sysUser.setNickName(item.getActualName());
				sysUser.setPhone(item.getPhone());
				sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
				sysUser.setLockFlag("0");
				sysUser.setUsername(item.getPhone());
				sysUser.setActualName(item.getActualName());
				sysUserService.saveUser(sysUser);
				insOutlesUser.setUserId(sysUser.getUserId());
			}
			insOutlesUserList.add(insOutlesUser);
		});
		return insOutlesUserList;
	}

	/**
	 * 添加员工角色集合
	 * @param insOutlesUserObjectDTO
	 * @param insOutlesUserList
	 * @param institution
	 * @return
	 */
	private List<StaffRole> setStaffRoleList(InsOutlesUserObjectDTO insOutlesUserObjectDTO, List<InsOutlesUser> insOutlesUserList, Institution institution){
		// 5.根据机构类型与字典类型查询角色标识
		SysDictItem sysDictItem = new SysDictItem();
		sysDictItem.setType("ins_type");
		sysDictItem.setValue(institution.getInsType().toString());

		SysDictItem dictItem = sysDictItemService.getDictBySysDictItem(sysDictItem);

		int userType = insOutlesUserObjectDTO.getType();
		String typeName = "";
		if (userType == 1) {
			typeName = "_ADMIN";
		} else if (userType == 2) {
			typeName = "_STAFF_GENERAL";
		}
		// 6.根据角色标识查询角色信息
		SysRole role = this.sysRoleService.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleCode, dictItem.getLabel() + typeName).eq(SysRole::getDelFlag, 0));

		List<StaffRole> staffRoleList = new ArrayList<>();
		insOutlesUserList.stream().forEach(item -> {
			StaffRole staffRole = new StaffRole();
			staffRole.setRoleId(role.getRoleId());
			staffRole.setStaffId(item.getInsOutlesUserId());
			staffRoleList.add(staffRole);
		});
		return staffRoleList;
	}

}
