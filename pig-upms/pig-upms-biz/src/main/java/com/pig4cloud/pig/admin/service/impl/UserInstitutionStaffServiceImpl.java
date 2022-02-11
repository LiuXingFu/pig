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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.StaffRelationshipAuthenticateDTO;
import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.api.dto.UserInstitutionStaffDTO;
import com.pig4cloud.pig.admin.api.dto.UserOutlesStaffReDTO;
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.vo.UserInsStaffVO;
import com.pig4cloud.pig.admin.api.vo.UserInstitutionStaffVO;
import com.pig4cloud.pig.admin.mapper.UserInstitutionStaffMapper;
import com.pig4cloud.pig.admin.service.*;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 员工表
 *
 * @author yuanduo
 * @date 2021-09-06 15:20:47
 */
@Service
public class UserInstitutionStaffServiceImpl extends ServiceImpl<UserInstitutionStaffMapper, UserInstitutionStaff> implements UserInstitutionStaffService {

	@Autowired
	SysUserService sysUserService;

	@Autowired
	InstitutionService institutionService;

	@Autowired
	StaffRoleService staffRoleService;

	@Autowired
	StaffDeptReService staffDeptReService;

	@Autowired
	OutlesService outlesService;

	@Autowired
	UserOutlesStaffReService userOutlesStaffReService;

	@Autowired
	SysRoleService sysRoleService;

	@Autowired
	SecurityUtilsService securityUtilsService;

	/**
	 * 新增员工 // 需要修改
	 *
	 * @param userInstitutionStaffDTO
	 * @return
	 */
	@Override
	@Transactional
	public int saveUserInstitutionStaff(UserInstitutionStaffDTO userInstitutionStaffDTO) throws Exception {
		// 判断员工参数信息是否为空
		if (Objects.nonNull(userInstitutionStaffDTO)) {
			// 根据电话查询账户信息
			SysUser sysUser = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, userInstitutionStaffDTO.getPhone()).eq(SysUser::getDelFlag, 0));
			// 新增机构用户
			UserDTO userDTO = new UserDTO();

			List<Integer> roles = new ArrayList<>();
			roles.add(userInstitutionStaffDTO.getRoleId());

			// 判断账户是否存在
			if (Objects.nonNull(sysUser)) {
				// 根据账户id与机构id查询员工是否存在
				UserInstitutionStaff userInstitutionStaff = this.getOne(new LambdaQueryWrapper<UserInstitutionStaff>().eq(UserInstitutionStaff::getUserId, sysUser.getUserId())
						.eq(UserInstitutionStaff::getInsId, securityUtilsService.getCacheUser().getInsId()).eq(UserInstitutionStaff::getDelFlag, 0));
				// 员工存在返回提示信息
				if (Objects.nonNull(userInstitutionStaff)) {
					return 100;
				}
				userInstitutionStaff = new UserInstitutionStaff();
				// 不存在创建员工信息
				userInstitutionStaff.setUserId(sysUser.getUserId());
				userInstitutionStaff.setEntryTime(LocalDateTime.now());
				userInstitutionStaff.setJobTitle(userInstitutionStaffDTO.getJobTitle());
				userInstitutionStaff.setInsId(securityUtilsService.getCacheUser().getInsId());
				this.save(userInstitutionStaff);
				//添加用户网点关联信息
				saveUserOutlesStaffRe(userInstitutionStaffDTO, userInstitutionStaff);
				userDTO.setRole(roles);
				setStaffRole(userDTO, userInstitutionStaff);
				setStaffDept(userInstitutionStaffDTO.getDept(), userInstitutionStaff);

				return userInstitutionStaff.getStaffId();
			} else {
				userDTO.setUsername(userInstitutionStaffDTO.getPhone());
				userDTO.setPassword("a123456!");
				userDTO.setPhone(userInstitutionStaffDTO.getPhone());
				userDTO.setUserType(0);
				userDTO.setDelFlag(CommonConstants.STATUS_NORMAL);
				userDTO.setNickName(userInstitutionStaffDTO.getJobTitle());
				userDTO.setRole(roles);
				sysUserService.saveUser(userDTO);

				// 创建员工信息
				UserInstitutionStaff userInstitutionStaff = new UserInstitutionStaff();
				userInstitutionStaff.setInsId(securityUtilsService.getCacheUser().getInsId());
				userInstitutionStaff.setJobTitle(userInstitutionStaffDTO.getJobTitle());
				userInstitutionStaff.setEntryTime(LocalDateTime.now());
				userInstitutionStaff.setUserId(userDTO.getUserId());
				this.save(userInstitutionStaff);
				//添加用户网点关联信息
				saveUserOutlesStaffRe(userInstitutionStaffDTO, userInstitutionStaff);
				setStaffRole(userDTO, userInstitutionStaff);
				setStaffDept(userInstitutionStaffDTO.getDept(), userInstitutionStaff);

				return userInstitutionStaff.getStaffId();
			}
		} else {
			throw new Exception("参数异常");
		}
	}

	public void saveUserOutlesStaffRe(UserInstitutionStaffDTO userInstitutionStaffDTO, UserInstitutionStaff userInstitutionStaff) {
		Map<String, Object> map = new HashedMap();
		map.put("staff_id", userInstitutionStaff.getStaffId());
		map.put("user_id", userInstitutionStaff.getUserId());
		map.put("ins_id", userInstitutionStaff.getInsId());
		userOutlesStaffReService.removeByMap(map);

		setOutlesStaffRe(userInstitutionStaffDTO, userInstitutionStaff);
	}

	/**
	 * 添加根据网点集合循环关联员工网点
	 *
	 * @param userInstitutionStaffDTO
	 * @param userInstitutionStaff
	 */
	private void setOutlesStaffRe(UserInstitutionStaffDTO userInstitutionStaffDTO, UserInstitutionStaff userInstitutionStaff) {
		List<UserOutlesStaffRe> outlesStaffReList = new ArrayList<>();
		List<Integer> outles = userInstitutionStaffDTO.getOutles();
		for (Integer outle : outles) {
			UserOutlesStaffRe userOutlesStaffRe = new UserOutlesStaffRe();
			userOutlesStaffRe.setInsId(userInstitutionStaff.getInsId());
			userOutlesStaffRe.setStaffId(userInstitutionStaff.getStaffId());
			userOutlesStaffRe.setUserId(userInstitutionStaff.getUserId());
			userOutlesStaffRe.setOutlesId(outle);
			if (Objects.nonNull(userInstitutionStaffDTO.getRoleType())) {
				userOutlesStaffRe.setRoleType(userInstitutionStaffDTO.getRoleType());
			}
			outlesStaffReList.add(userOutlesStaffRe);
		}
		userOutlesStaffReService.saveBatch(outlesStaffReList);
	}

	/**
	 * 配置部门
	 *
	 * @param dept
	 * @param userInstitutionStaff
	 */
	@Override
	public void setStaffDept(List<Integer> dept, UserInstitutionStaff userInstitutionStaff) {
		List<StaffDeptRe> staffDeptReList = dept.stream().map(deptId -> {
			StaffDeptRe staffDeptRe = new StaffDeptRe();
			staffDeptRe.setDeptId(deptId);
			staffDeptRe.setStaffId(userInstitutionStaff.getStaffId());
			return staffDeptRe;
		}).collect(Collectors.toList());
		staffDeptReService.saveBatch(staffDeptReList);
	}

	/**
	 * 配置角色
	 *
	 * @param userDTO
	 * @param userInstitutionStaff
	 */
	@Override
	public void setStaffRole(UserDTO userDTO, UserInstitutionStaff userInstitutionStaff) {
		//给账户配置角色
		List<StaffRole> staffRoleList = userDTO.getRole().stream().map(roleId -> {
			StaffRole staffRole = new StaffRole();
			staffRole.setStaffId(userInstitutionStaff.getStaffId());
			staffRole.setRoleId(roleId);
			return staffRole;
		}).collect(Collectors.toList());
		staffRoleService.saveBatch(staffRoleList);
	}

	/**
	 * 分页查询员工信息
	 *
	 * @param page
	 * @param userInstitutionStaff
	 * @return
	 */
	@Override
	public IPage<UserInstitutionStaffVO> pageUserInstitutionStaff(Page page, UserInstitutionStaff userInstitutionStaff) {
		userInstitutionStaff.setInsId(securityUtilsService.getCacheUser().getInsId());
		return this.baseMapper.pageUserInstitutionStaff(page, userInstitutionStaff);
	}

	/**
	 * 通过id查询员工表
	 *
	 * @param staffId id
	 * @return R
	 */
	@Override
	public UserInstitutionStaffVO getByIdUserInstitutionStaff(Integer staffId) {
		return this.baseMapper.getByIdUserInstitutionStaff(staffId);
	}

	/**
	 * 通过id查询需要编辑的员工信息
	 *
	 * @param staffId id
	 * @return R
	 */
	@Override
	public UserInsStaffVO getByIdStaff(Integer staffId) {
		return this.baseMapper.getByIdStaff(staffId);
	}

	/**
	 * 分页查询
	 * 根据网点id查询员工集合
	 *
	 * @return
	 */
	@Override
	public IPage<UserInstitutionStaffVO> getOutlesIdByStaffList(Page page, Integer outlesId, Integer userType, String nickName) {
		List<UserOutlesStaffRe> list = userOutlesStaffReService.list(new LambdaQueryWrapper<UserOutlesStaffRe>().eq(UserOutlesStaffRe::getOutlesId, outlesId));
		List<Integer> ids = new ArrayList<>();
		for (UserOutlesStaffRe userOutlesStaffRe : list) {
			ids.add(userOutlesStaffRe.getStaffId());
		}

		return this.baseMapper.pageOutlesStaff(page, ids, userType, nickName);

	}

	/**
	 * 分页查询
	 * 根据网点id查询未关联网点与员工集合
	 *
	 * @return
	 */
	@Override
	public IPage<UserInstitutionStaffVO> pageNotOutlesStaff(Page page, Integer outlesId) {
		List<UserOutlesStaffRe> list = userOutlesStaffReService.list(new LambdaQueryWrapper<UserOutlesStaffRe>().eq(UserOutlesStaffRe::getOutlesId, outlesId));
		List<Integer> ids = new ArrayList<>();
		for (UserOutlesStaffRe userOutlesStaffRe : list) {
			ids.add(userOutlesStaffRe.getStaffId());
		}
		return this.baseMapper.pageNotOutlesStaff(page, ids, list.get(0).getInsId());

	}

	/**
	 * 修改员工表
	 *
	 * @param userInstitutionStaffDTO 员工表
	 * @return R
	 */
	@Override
	public boolean updateUserInstitutionStaff(UserInstitutionStaffDTO userInstitutionStaffDTO) {
		UserDTO userDTO = new UserDTO();
		userDTO.setUserId(userInstitutionStaffDTO.getUserId());
		userDTO.setUsername(userInstitutionStaffDTO.getPhone());
		userDTO.setPhone(userInstitutionStaffDTO.getPhone());
		sysUserService.updateUser(userDTO);
		List<Integer> roles = new ArrayList<>();
		roles.add(userInstitutionStaffDTO.getRoleId());
		userDTO.setRole(roles);
		this.sysUserService.updateUser(userDTO);

		UserInstitutionStaff userInstitutionStaff = new UserInstitutionStaff();
		BeanUtils.copyProperties(userInstitutionStaffDTO, userInstitutionStaff);

		staffDeptReService.remove(new LambdaQueryWrapper<StaffDeptRe>().eq(StaffDeptRe::getStaffId, userInstitutionStaffDTO.getStaffId()));

		staffRoleService.remove(new LambdaQueryWrapper<StaffRole>().eq(StaffRole::getStaffId, userInstitutionStaffDTO.getStaffId()));

		setStaffRole(userDTO, userInstitutionStaff);
		setStaffDept(userInstitutionStaffDTO.getDept(), userInstitutionStaff);

		//修改用户网点关联信息
		saveUserOutlesStaffRe(userInstitutionStaffDTO, userInstitutionStaff);
		return this.updateById(userInstitutionStaff);
	}

	/**
	 * 通过id离职员工
	 *
	 * @param staffId id
	 * @return R
	 */
	@Override
	public boolean resignStaff(Integer staffId) {
		return this.removeById(staffId);
	}

	@Override
	public boolean saveUserOutlesStaffRe(UserOutlesStaffReDTO userOutlesStaffRe) {
		List<UserOutlesStaffRe> staffList = userOutlesStaffRe.getStaffList();
		for (UserOutlesStaffRe outlesStaffRe : staffList) {
			outlesStaffRe.setOutlesId(userOutlesStaffRe.getOutlesId());
		}
		return userOutlesStaffReService.saveBatch(staffList);
	}

	/**
	 * 删除员工网点关联信息
	 *
	 * @param userOutlesStaffRe 删除员工网点关联信息
	 * @return R
	 */
	@Override
	public boolean delUserOutlesStaffRe(UserOutlesStaffReDTO userOutlesStaffRe) {
		Map<String, Object> map = new HashedMap();
		map.put("outles_id", userOutlesStaffRe.getOutlesId());
		map.put("staff_id", userOutlesStaffRe.getStaffId());
		map.put("ins_id", userOutlesStaffRe.getInsId());
		map.put("user_id", userOutlesStaffRe.getUserId());
		return userOutlesStaffReService.removeByMap(map);
	}

	/**
	 * 添加员工关联网点
	 *
	 * @param userOutlesStaffRe
	 * @param userInstitutionStaff
	 */
	@Override
	public void addUserOutlesStaffRe(UserInstitutionStaffDTO userOutlesStaffRe, UserInstitutionStaff userInstitutionStaff) {
		setOutlesStaffRe(userOutlesStaffRe, userInstitutionStaff);
	}

	/**
	 * 查询员工的所属网点id集合
	 * @return
	 */
	@Override
	public List<Integer> getStaffOutlesList() {
		PigUser pigUser = securityUtilsService.getCacheUser();
		UserInstitutionStaff userInstitutionStaff = this.getOne(new LambdaQueryWrapper<UserInstitutionStaff>()
				.eq(UserInstitutionStaff::getInsId, pigUser.getInsId())
				.eq(UserInstitutionStaff::getUserId, pigUser.getId())
				.eq(UserInstitutionStaff::getDelFlag, 0));
		List<Integer> outlesIds = userOutlesStaffReService.list(new LambdaQueryWrapper<UserOutlesStaffRe>()
				.eq(UserOutlesStaffRe::getStaffId, userInstitutionStaff.getStaffId()))
				.stream().map(UserOutlesStaffRe::getOutlesId)
				.collect(Collectors.toList());
		return outlesIds;
	}

	/**
	 * 根据认证目标id与认证类型查询员工集合
	 * @param page
	 * @param staffRelationshipAuthenticateDTO
	 * @return
	 */
	@Override
	public IPage<UserInstitutionStaff> getStaffsByAuthenticateGoalId(Page page, StaffRelationshipAuthenticateDTO staffRelationshipAuthenticateDTO) {
		return this.baseMapper.getStaffsByAuthenticateGoalId(page, staffRelationshipAuthenticateDTO);
	}

}
