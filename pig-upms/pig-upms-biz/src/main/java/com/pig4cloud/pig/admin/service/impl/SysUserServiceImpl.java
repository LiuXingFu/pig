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
import com.pig4cloud.pig.admin.api.dto.UserSelectDTO;
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.vo.OrganizationQueryVO;
import com.pig4cloud.pig.admin.api.vo.SysUserInsOutlesVO;
import com.pig4cloud.pig.admin.api.vo.UserInsOutlesRoleVO;
import com.pig4cloud.pig.admin.api.vo.UserVO;
import com.pig4cloud.pig.admin.mapper.SysUserMapper;
import com.pig4cloud.pig.admin.service.*;
import com.pig4cloud.pig.common.core.constant.CacheConstants;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
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
	@Autowired
	JurisdictionUtilsService jurisdictionUtilsService;


	/**
	 * ??????????????????
	 *
	 * @param userDto DTO ??????
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
	 * ??????clientId
	 * @param username
	 * @param clientId
	 * @return ????????????
	 */
	@Override
	public Integer updateUserClientInfo(String username, String clientId) {
		return clientUserReService.updateUserClientInfo(username, clientId);
	}

	/**
	 * ??????????????????????????????
	 * @param phone
	 * @return
	 */
	@Override
	public SysUser queryUserByPhone(String phone) {
		return this.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getPhone, phone).eq(SysUser::getDelFlag, 0));
//		return this.baseMapper.queryUserListByPhone(page, phone);
	}

	/**
	 * ??????????????????????????????
	 *
	 * @param sysUser ??????
	 * @return
	 */
	@Override
	public UserInfo getUserInfo(SysUser sysUser) {
		UserInfo userInfo = new UserInfo();
		userInfo.setSysUser(sysUser);

		// ????????????id??????????????????
		UserInstitutionStaff institutionStaff = userInstitutionStaffService.getOne(new LambdaQueryWrapper<UserInstitutionStaff>()
				.eq(UserInstitutionStaff::getUserId, sysUser.getUserId())
				.eq(UserInstitutionStaff::getDelFlag, 0));

		if (null != institutionStaff) {
			// ????????????id??????????????????
			Institution institution = institutionService.getOne(new LambdaQueryWrapper<Institution>().eq(Institution::getInsId, institutionStaff.getInsId()).eq(Institution::getDelFlag, 0));
			userInfo.setInstitution(institution);

			// ?????????????????? ???ID???
			List<Integer> roleIds = staffRoleService.findRolesByUserId(institutionStaff.getStaffId()).stream().map(SysRole::getRoleId)
					.collect(Collectors.toList());

			userInfo.setRoles(ArrayUtil.toArray(roleIds, Integer.class));

			// ?????????????????????menu.permission???
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
	 * ????????????????????????????????????????????????
	 *
	 * @param page    ????????????
	 * @param userDTO ????????????
	 * @return
	 */
	@Override
	public IPage getUserWithRolePage(Page page, UserDTO userDTO) {
		return baseMapper.getUserVosPage(page, userDTO);
	}

	/**
	 * ??????ID??????????????????
	 *
	 * @param id ??????ID
	 * @return ????????????
	 */
	@Override
	public UserVO getUserVoById(Integer id) {
		return baseMapper.getUserVoById(id);
	}

	/**
	 * ????????????
	 *
	 * @param sysUser ??????
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
	 * ??????????????????????????????
	 * @param userDto ????????????
	 * @return Boolean ??????????????????true,??????????????????false
	 */
	@Override
	@CacheEvict(value = CacheConstants.USER_DETAILS, key = "#userDto.username")
	public Boolean updateUserInfo(UserDTO userDto) {
		UserVO userVO = baseMapper.getUserVoByUsername(userDto.getUsername());

		Assert.isTrue(ENCODER.matches(userDto.getPassword(), userVO.getPassword()), "??????????????????????????????");

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
	 * ????????????????????????
	 * @param userDto ????????????
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
	 * ?????????????????????????????????
	 *
	 * @param username ?????????
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


		List<String> permissions = sysMenuService.queryByRoleId(sysRole.getRoleId());
		// ?????????????????????menu.permission???
//		Set<String> permissions = new HashSet<>();
//		sysMenuList.stream().forEach(item ->{
//			permissions.add(item.getPermission());
//		});
		userInsOutlesRoleVO.setPermissions(ArrayUtil.toArray(permissions, String.class));

		// ??????????????????????????????id?????????id
		Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
		PigUser cacheUser = cache.get(SecurityUtils.getUser().getUsername(), PigUser.class);
		cacheUser.setInsId(institution.getInsId());
		cache.put(SecurityUtils.getUser().getUsername(), cacheUser);
		if (Objects.nonNull(userInsOutlesRoleVO.getOutles())) {
			cacheUser.setOutlesId(userInsOutlesRoleVO.getOutles().getOutlesId());
		}else{
			cacheUser.setOutlesId(null);
		}
		cache.put(SecurityUtils.getUser().getUsername(), cacheUser);

		return userInsOutlesRoleVO;
	}

	@Override
	public IPage<SysUserInsOutlesVO> pageOutlesUser(Page page, SysUserInsOutlesDTO sysUserInsOutlesDTO){
		return this.baseMapper.pageOutlesUser(page,sysUserInsOutlesDTO,jurisdictionUtilsService.queryByInsId("PLAT_"),securityUtilsService.getCacheUser().getOutlesId());
	}

	@Override
	public List<OrganizationQueryVO> pageCooperateByUserId(UserSelectDTO userSelectDTO){
		return this.baseMapper.pageCooperateByUserId(userSelectDTO);
	}

//	/**
//	 * ??????????????????????????????
//	 * @param page ????????????
//	 * @param user
//	 * @return
//	 */
//	@Override
//	public IPage<SysUser> pageJudge(Page page, SysUser user) {
//		return this.baseMapper.pageJudge(page, user);
//	}

	@Override
	public SysUser getByPhone(String phone){
		return this.baseMapper.getByPhone(phone);
	}

	@Override
	@Transactional
	public 	Boolean updateByUserId(UserDTO userDto){
		SysUser sysUser = new SysUser();
		BeanCopyUtil.copyBean(userDto,sysUser);
		if (StrUtil.isNotBlank(userDto.getPassword())) {
			sysUser.setPassword(ENCODER.encode(userDto.getPassword()));
		}
		return this.baseMapper.updateByUserId(sysUser);
	}

}
