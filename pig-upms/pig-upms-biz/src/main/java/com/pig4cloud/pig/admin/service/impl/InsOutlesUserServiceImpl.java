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
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserAddOutlesListDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserObjectDTO;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

/**
 * ??????/?????????????????????
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
	public int addInsOutlesUser(InsOutlesUserObjectDTO insOutlesUserAddDTO) {
		Institution institution = institutionService.getById(insOutlesUserAddDTO.getInsId());

		List<SysUser> userList = insOutlesUserAddDTO.getUserList();
		List<InsOutlesUser> insOutlesUserList = new ArrayList<>();
		userList.stream().forEach(item -> {
			// ????????????????????????????????????????????????????????????????????????
			if (Objects.nonNull(item.getUserId())) {
				QueryWrapper<InsOutlesUser> queryWrapper = new QueryWrapper<>();
				queryWrapper.lambda().eq(InsOutlesUser::getDelFlag, 0);
				queryWrapper.lambda().eq(InsOutlesUser::getUserId, item.getUserId());
				queryWrapper.lambda().eq(InsOutlesUser::getInsId, insOutlesUserAddDTO.getInsId());
				if (Objects.nonNull(insOutlesUserAddDTO.getOutlesId())) {
					queryWrapper.lambda().and(wrapper -> wrapper.isNull(InsOutlesUser::getOutlesId).or().eq(InsOutlesUser::getOutlesId, insOutlesUserAddDTO.getOutlesId()));
				}
				List<InsOutlesUser> users = insOutlesUserService.list(queryWrapper);
				if (users.size() > 0) {
					throw new RuntimeException("????????????????????????????????????");
				}
			}

			InsOutlesUser insOutlesUser = new InsOutlesUser();
			insOutlesUser.setInsId(institution.getInsId());
			insOutlesUser.setOutlesId(insOutlesUserAddDTO.getOutlesId());
			insOutlesUser.setType(insOutlesUserAddDTO.getType());
			insOutlesUser.setDelFlag(CommonConstants.STATUS_NORMAL);
			insOutlesUser.setPosition(insOutlesUserAddDTO.getPosition());

			SysUser user = sysUserService.getByPhone(item.getPhone());
			// ????????????????????????
			if (Objects.nonNull(user)) {
				insOutlesUser.setUserId(user.getUserId());
				// ?????????????????????????????????????????????????????????????????????
				if (!user.getDelFlag().equals(CommonConstants.STATUS_NORMAL)) {
					UserDTO sysUser = new UserDTO();
					sysUser.setUserId(user.getUserId());
					sysUser.setPassword("123456");
					sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
					sysUser.setLockFlag(CommonConstants.STATUS_NORMAL);
					sysUser.setActualName(item.getActualName());
					sysUser.setNickName(item.getActualName());
					sysUserService.updateByUserId(sysUser);
				}
			} else {
				UserDTO sysUser = new UserDTO();
				sysUser.setPassword("123456");
				sysUser.setNickName(item.getActualName());
				sysUser.setPhone(item.getPhone());
				sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
				sysUser.setLockFlag(CommonConstants.STATUS_NORMAL);
				sysUser.setUsername(item.getPhone());
				sysUser.setActualName(item.getActualName());
				sysUserService.saveUser(sysUser);
				insOutlesUser.setUserId(sysUser.getUserId());
			}
			insOutlesUser.setEntryTime(LocalDateTime.now());
			insOutlesUserList.add(insOutlesUser);
		});
		this.saveBatch(insOutlesUserList);

		// 5.???????????????????????????????????????????????????
		SysDictItem sysDictItem = new SysDictItem();
		sysDictItem.setType("ins_type");
		sysDictItem.setValue(institution.getInsType().toString());

		SysDictItem dictItem = sysDictItemService.getDictBySysDictItem(sysDictItem);

		int userType = insOutlesUserAddDTO.getType();
		Integer insId = insOutlesUserAddDTO.getInsId();
		Integer outlesId = insOutlesUserAddDTO.getOutlesId();
		String typeName = "";
		if (userType == 1 && Objects.nonNull(insId) && Objects.isNull(outlesId)) {
			typeName = "_ADMIN";
		} else if (userType == 1 && Objects.nonNull(insId) && Objects.nonNull(outlesId)) {
			typeName = "_OUTLES_ADMIN";
		} else if (userType == 2) {
			typeName = "_STAFF_GENERAL";
		}
		// 6.????????????????????????????????????
		SysRole role = this.sysRoleService.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleCode, dictItem.getLabel() + typeName).eq(SysRole::getDelFlag, 0));

		List<StaffRole> staffRoleList = new ArrayList<>();
		insOutlesUserList.stream().forEach(item -> {
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
	public int removeInsOutlesUser(Integer insOutlesUserId) {
		int modify = 0;
		InsOutlesUser insOutlesUser = new InsOutlesUser();
		insOutlesUser.setDelFlag(CommonConstants.STATUS_DEL);
		insOutlesUser.setInsOutlesUserId(insOutlesUserId);
		modify = this.baseMapper.deleteById(insOutlesUser);
		// ??????????????????
		staffRoleService.remove(new LambdaQueryWrapper<StaffRole>().eq(StaffRole::getStaffId, insOutlesUserId));
		return modify;
	}

	@Override
	@Transactional
	public List<InsOutlesUserListVO> queryUserList(Integer type, Integer insId, Integer outlesId) {
		return this.baseMapper.selectUserList(type, insId, outlesId);
	}

	@Override
	public List<InsOutlesUserInsOutlesVO> queryOutlesName(Integer userId, Integer insId) {
		return this.baseMapper.selectOutlesName(userId, insId);
	}

	@Override
	public List<InsOutlesUserInsOutlesVO> queryInsName(Integer userId) {
		return this.baseMapper.selectInsName(userId);
	}

	@Override
	public IPage<InsOutlesUserInsOutlesVO> queryInsOutlesUserByOutles(Page page, InsOutlesUserByOutlesDTO insOutlesUserByOutlesDTO) {
		return this.baseMapper.queryInsOutlesUserByOutles(page, insOutlesUserByOutlesDTO);
	}

	/**
	 * ????????????id?????????????????????????????????
	 *
	 * @param insOutlesUserAddOutlesListDTO
	 * @return
	 */
	@Override
	public int addInsOutlesUserByOutlesId(InsOutlesUserAddOutlesListDTO insOutlesUserAddOutlesListDTO) {

		InsOutlesUserObjectDTO insOutlesUserObjectDTO = new InsOutlesUserObjectDTO();
		insOutlesUserObjectDTO.setInsId(insOutlesUserAddOutlesListDTO.getInsId());
		insOutlesUserObjectDTO.setType(insOutlesUserAddOutlesListDTO.getType());
		insOutlesUserObjectDTO.setOutlesId(insOutlesUserAddOutlesListDTO.getOutlesId());
		insOutlesUserObjectDTO.setPosition(insOutlesUserAddOutlesListDTO.getPosition());
		List<SysUser> sysUserList = new ArrayList<>();
		SysUser sysUser = new SysUser();
		sysUser.setPhone(insOutlesUserAddOutlesListDTO.getPhone());
		sysUser.setUserId(insOutlesUserAddOutlesListDTO.getUserId());
		sysUser.setActualName(insOutlesUserAddOutlesListDTO.getActualName());
		sysUserList.add(sysUser);
		insOutlesUserObjectDTO.setUserList(sysUserList);
		insOutlesUserService.addInsOutlesUser(insOutlesUserObjectDTO);
		return 1;
	}

	/**
	 * ????????????/?????????????????????id????????????/????????????????????????????????????
	 *
	 * @param insOutlesUserId
	 * @return
	 */
	@Override
	public InsOutlesUserInsOutlesVO queryById(Integer insOutlesUserId) {
		return this.baseMapper.queryById(insOutlesUserId);
	}

	/**
	 * ?????????????????????????????????
	 *
	 * @param insOutlesUser
	 * @return
	 */
	@Override
	public int updateInsOutlesUser(InsOutlesUser insOutlesUser) {
		int update = 0;

		this.updateById(insOutlesUser);

		return update += 1;
	}


}
