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

package com.pig4cloud.pig.admin.service.impl;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.SysUserInsOutlesDTO;
import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.api.dto.UserInfo;
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.vo.SysUserInsOutlesVO;
import com.pig4cloud.pig.admin.api.vo.UserInsOutlesRoleVO;
import com.pig4cloud.pig.admin.api.vo.UserVO;
import com.pig4cloud.pig.admin.mapper.SysUserMapper;
import com.pig4cloud.pig.admin.service.*;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	@Autowired
	private final SysMenuService sysMenuService;

	@Autowired
	private final SysDeptService sysDeptService;

	@Autowired
	private final SysUserRoleService sysUserRoleService;

	@Autowired
	private UserInstitutionStaffService userInstitutionStaffService;

	@Autowired
	private final InstitutionService institutionService;

	@Autowired
	private final StaffRoleService staffRoleService;

	@Autowired
	private final CacheManager cacheManager;

	@Autowired
	private final OutlesService outlesService;

	@Autowired
	private final ClientUserReService clientUserReService;

	@Autowired
	private final SysRoleService sysRoleService;
	@Autowired
	private SecurityUtilsService securityUtilsService;

	/**
	 * 保存用户信息
	 *
	 * @param userDto DTO 对象
	 * @return success/fail
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean saveUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setDelFlag(CommonConstants.STATUS_NORMAL);
		sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		int insert = baseMapper.insert(sysUser);
		BeanUtils.copyProperties(sysUser, userDto);
//		List<SysUserRole> userRoleList = userDto.getRole().stream().map(roleId -> {
//			SysUserRole userRole = new SysUserRole();
//			userRole.setUserId(sysUser.getUserId());
//			userRole.setRoleId(roleId);
//			return userRole;
//		}).collect(Collectors.toList());
//		return sysUserRoleService.saveBatch(userRoleList);
		if (insert > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Outles selectUsedOutles(Integer outlesId) {
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		PigUser cacheUser = cache.get(SecurityUtils.getUser().getUsername(), PigUser.class);

		if (outlesId != null) {
			Outles outles = outlesService.getUserIdOutlesIdByRelevance(cacheUser.getId(), outlesId);
			if (outles != null) {
				cacheUser.setOutlesId(outlesId);
				cache.put(SecurityUtils.getUser().getUsername(), cacheUser);
				return outles;
			}else {
				return null;
			}
		} else {
			cacheUser.setOutlesId(null);
			cache.put(SecurityUtils.getUser().getUsername(), cacheUser);
			return null;
		}
	}

	/**
	 * 更新clientId
	 * @param username
	 * @param clientId
	 * @return 用户信息
	 */
	@Override
	public Integer updateUserClientInfo(String username, String clientId) {
		return clientUserReService.updateUserClientInfo(username, clientId);
	}

	/**
	 * 根据电话查询用户信息
	 * @param phone
	 * @return
	 */
	@Override
	public SysUser queryUserByPhone(String phone) {
		return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, phone).eq(SysUser::getDelFlag, 0));
//		return this.baseMapper.queryUserListByPhone(page, phone);
	}

	/**
	 * 通过查用户的全部信息
	 *
	 * @param sysUser 用户
	 * @return
	 */
	@Override
	public UserInfo getUserInfo(SysUser sysUser) {
		UserInfo userInfo = new UserInfo();
		userInfo.setSysUser(sysUser);

		// 根据用户id查询员工信息
		UserInstitutionStaff institutionStaff = userInstitutionStaffService.getOne(new LambdaQueryWrapper<UserInstitutionStaff>()
				.eq(UserInstitutionStaff::getUserId, sysUser.getUserId())
				.eq(UserInstitutionStaff::getDelFlag, 0));

		if (null != institutionStaff) {
			// 根据机构id查询机构信息
			Institution institution = institutionService.getOne(new LambdaQueryWrapper<Institution>().eq(Institution::getInsId, institutionStaff.getInsId()).eq(Institution::getDelFlag, 0));
			userInfo.setInstitution(institution);

			// 设置角色列表 （ID）
			List<Integer> roleIds = staffRoleService.findRolesByUserId(institutionStaff.getStaffId()).stream().map(SysRole::getRoleId)
					.collect(Collectors.toList());

			userInfo.setRoles(ArrayUtil.toArray(roleIds, Integer.class));

			// 设置权限列表（menu.permission）
			Set<String> permissions = new HashSet<>();
			roleIds.forEach(roleId -> {
				List<String> permissionList = sysMenuService.findMenuByRoleId(roleId).stream()
						.filter(menuVo -> StrUtil.isNotEmpty(menuVo.getPermission())).map(SysMenu::getPermission)
						.collect(Collectors.toList());
				permissions.addAll(permissionList);
			});
			userInfo.setPermissions(ArrayUtil.toArray(permissions, String.class));
		}


		return userInfo;
	}

	/**
	 * 分页查询用户信息（含有角色信息）
	 *
	 * @param page    分页对象
	 * @param userDTO 参数列表
	 * @return
	 */
	@Override
	public IPage getUserWithRolePage(Page page, UserDTO userDTO) {
		return baseMapper.getUserVosPage(page, userDTO);
	}

	/**
	 * 通过ID查询用户信息
	 *
	 * @param id 用户ID
	 * @return 用户信息
	 */
	@Override
	public UserVO getUserVoById(Integer id) {
		return baseMapper.getUserVoById(id);
	}

	/**
	 * 删除用户
	 *
	 * @param sysUser 用户
	 * @return Boolean
	 */
	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#sysUser.username")
	public Boolean removeUserById(SysUser sysUser) {
		sysUserRoleService.removeRoleByUserId(sysUser.getUserId());
		this.removeById(sysUser.getUserId());
		return Boolean.TRUE;
	}

	/**
	 * 更新当前用户基本信息
	 * @param userDto 用户信息
	 * @return Boolean 操作成功返回true,操作失败返回false
	 */
	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	public Boolean updateUserInfo(UserDTO userDto) {
		UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUsername());

		Assert.isTrue(ENCODER.matches(userDto.getPassword(), userVO.getPassword()), "原密码错误，修改失败");

		SysUser sysUser = new SysUser();
		if (StrUtil.isNotBlank(userDto.getNewpassword1())) {
			sysUser.setPassword(ENCODER.encode(userDto.getNewpassword1()));
		}
		sysUser.setPhone(userDto.getPhone());
		sysUser.setUserId(userVO.getUserId());
		sysUser.setAvatar(userDto.getAvatar());
		return this.updateById(sysUser);
	}

	/**
	 * 更新指定用户信息
	 * @param userDto 用户信息
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	public Boolean updateUser(UserDTO userDto) {
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(userDto, sysUser);
		sysUser.setUpdateTime(LocalDateTime.now());

		if (StrUtil.isNotBlank(userDto.getPassword())) {
			sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		}
		this.updateById(sysUser);

//		sysUserRoleService
//				.remove(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId, userDto.getUserId()));
//		userDto.getRole().forEach(roleId -> {
//			SysUserRole userRole = new SysUserRole();
//			userRole.setUserId(sysUser.getUserId());
//			userRole.setRoleId(roleId);
//			userRole.insert();
//		});
		return Boolean.TRUE;
	}

	/**
	 * 查询上级部门的用户信息
	 *
	 * @param username 用户名
	 * @return R
	 */
	@Override
	public List<SysUser> listAncestorUsersByUsername(String username) {
		SysUser sysUser = this.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));

		SysDept sysDept = sysDeptService.getById(sysUser.getDeptId());
		if (sysDept == null) {
			return null;
		}

		Integer parentId = sysDept.getParentId();
		return this.list(Wrappers.<SysUser>query().lambda().eq(SysUser::getDeptId, parentId));
	}

	@Override
	public UserInsOutlesRoleVO getInsOutlesRole(Integer insId, Integer outlesId){
		UserInsOutlesRoleVO userInsOutlesRoleVO = new UserInsOutlesRoleVO();
		Institution institution = institutionService.getById(insId);
		userInsOutlesRoleVO.setInstitution(institution);
		if(Objects.nonNull(outlesId)){
			Outles outles = outlesService.getById(outlesId);
			userInsOutlesRoleVO.setOutles(outles);
		}
		PigUser pigUser = securityUtilsService.getCacheUser();
		SysRole sysRole = sysRoleService.queryByUserIdList(pigUser.getId(),insId,outlesId,null);
		userInsOutlesRoleVO.setSysRole(sysRole);


		List<SysMenu> sysMenuList = sysMenuService.findMenuByRoleId(sysRole.getRoleId());
		// 设置权限列表（menu.permission）
		Set<String> permissions = new HashSet<>();
		sysMenuList.stream().forEach(item ->{
			permissions.add(item.getPermission());
		});
		userInsOutlesRoleVO.setPermissions(ArrayUtil.toArray(permissions, String.class));

		// 保存缓存用户信息机构id和网点id
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		PigUser cacheUser = cache.get(SecurityUtils.getUser().getUsername(), PigUser.class);
		cacheUser.setInsId(institution.getInsId());
		cache.put(SecurityUtils.getUser().getUsername(), cacheUser);
		if (Objects.nonNull(userInsOutlesRoleVO.getOutles())) {
			cacheUser.setOutlesId(userInsOutlesRoleVO.getOutles().getOutlesId());
		}
		cache.put(SecurityUtils.getUser().getUsername(), cacheUser);

		return userInsOutlesRoleVO;
	}

	@Override
	public IPage<SysUserInsOutlesVO> pageOutlesUser(Page page, SysUserInsOutlesDTO sysUserInsOutlesDTO){
		return this.baseMapper.pageOutlesUser(page,sysUserInsOutlesDTO);
	}

}
