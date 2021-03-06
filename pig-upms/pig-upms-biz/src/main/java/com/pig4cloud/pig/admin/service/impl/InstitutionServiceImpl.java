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

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.*;
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.vo.*;
import com.pig4cloud.pig.admin.mapper.InstitutionMapper;
import com.pig4cloud.pig.admin.service.*;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author xiaojun
 * @date 2021-08-17 16:22:59
 */
@Service
public class InstitutionServiceImpl extends ServiceImpl<InstitutionMapper, Institution> implements InstitutionService {

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	@Autowired
	SysUserService sysUserService;

	@Autowired
	AddressService addressService;

	@Autowired
	SysDeptService sysDeptService;

	@Autowired
	UserInstitutionStaffService userInstitutionService;

	@Autowired
	OutlesService outlesService;

	@Autowired
	SysRoleService sysRoleService;

	@Autowired
	SysDictItemService sysDictItemService;

	@Autowired
	RegionService regionService;

	@Autowired
	UserOutlesStaffReService userOutlesStaffReService;

	@Autowired
	SecurityUtilsService securityUtilsService;

	@Autowired
	RelationshipAuthenticateService relationshipAuthenticateService;

	@Autowired
	CourtService courtService;

	@Autowired
	InsOutlesUserService insOutlesUserService;

	@Autowired
	StaffRoleService staffRoleService;

	@Autowired
	JurisdictionUtilsService jurisdictionUtilsService;

	@Autowired
	SubjectService subjectService;

	@Autowired
	InstitutionSubjectReService institutionSubjectReService;

	/**
	 * 新增机构
	 *
	 * @param institutionDTO
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public int saveInstitution(InstitutionDTO institutionDTO) throws Exception {

		// 1.判断机构DTO对象是否为空
		if (Objects.nonNull(institutionDTO)) {

			// 2.根据负责人联系电话查询用户数据
			SysUser sysUser = this.sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
//					.eq(SysUser::getPhone, institutionDTO.getInsPrincipalPhone())
					.eq(SysUser::getDelFlag, 0));

			// 3.新增机构用户
			UserDTO userDTO = new UserDTO();
			if (Objects.nonNull(sysUser)) {
				return -1;
			}

//			userDTO.setUsername(institutionDTO.getInsPrincipalPhone());
			userDTO.setPassword("a123456!");
//			userDTO.setPhone(institutionDTO.getInsPrincipalPhone());
			userDTO.setDelFlag(CommonConstants.STATUS_NORMAL);
//			userDTO.setNickName(institutionDTO.getInsPrincipalName());
			userDTO.setUserType(1);
			List<Integer> integers = new ArrayList<>();

			// 5.根据机构类型与字典类型查询角色标识
			SysDictItem sysDictItem = new SysDictItem();
			sysDictItem.setType("ins_type");
			sysDictItem.setValue(institutionDTO.getInsType().toString());

			SysDictItem dictItem = sysDictItemService.getDictBySysDictItem(sysDictItem);

			// 6.根据角色标识查询角色信息
			SysRole role = this.sysRoleService.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleCode, dictItem.getLabel() + "_ADMIN").eq(SysRole::getDelFlag, 0));

			integers.add(role.getRoleId());
			userDTO.setRole(integers);
			userDTO.setUserType(1);
			sysUserService.saveUser(userDTO);

			// 8.新增机构
			Institution institution = new Institution();
			BeanUtils.copyProperties(institutionDTO, institution);
//			institution.setUserId(userDTO.getUserId());
			this.save(institution);

			// 7.新增机构地址
			Address address = new Address();
			address.setUserId(institution.getInsId());
			address.setType(2);
			BeanUtils.copyProperties(institutionDTO.getAddress(), address);
			addressService.saveAddress(address);

			if (institution.getInsType().equals(Integer.valueOf("1500"))) {
				if (Objects.nonNull(institutionDTO.getCourt().getCourtId())) {
					RelationshipAuthenticate relationshipAuthenticate = new RelationshipAuthenticate();
					relationshipAuthenticate.setAuthenticateId(institution.getInsId());
					relationshipAuthenticate.setAuthenticateGoalId(institutionDTO.getCourt().getCourtId());
					relationshipAuthenticateService.save(relationshipAuthenticate);
				} else {
					throw new Exception("关联法院为空！");
				}
			}

			// 9.添加部门
			SysDept sysDept = new SysDept();
			sysDept.setInsId(institution.getInsId());
			sysDept.setName(institution.getInsName());
			sysDept.setParentId(0);
			sysDept.setSort(1);
			sysDeptService.saveDept(sysDept);

			// 10.添加员工
			UserInstitutionStaff userInstitutionStaff = new UserInstitutionStaff();
			userInstitutionStaff.setUserId(userDTO.getUserId());
			userInstitutionStaff.setInsId(institution.getInsId());
			userInstitutionStaff.setEntryTime(LocalDateTime.now());
//			userInstitutionStaff.setJobTitle(institutionDTO.getInsPrincipalName());
			userInstitutionService.save(userInstitutionStaff);

			if (institution.getInsType().equals(Integer.valueOf("1500"))) {

				List<String> courtNames = new ArrayList<>();
				courtNames.add("诉讼庭");
				courtNames.add("执行局");
				courtNames.add("保全组");

				List<Outles> outlesList = new ArrayList<>();

				courtNames.forEach((courtName) -> {
					Outles outles = new Outles();
					outles.setInsId(institution.getInsId());
					outles.setDelFlag(CommonConstants.STATUS_NORMAL);
					outles.setOutlesName(courtName);
//					outles.setUserId(userDTO.getUserId());
					if (courtName.equals("诉讼庭")) {
						outles.setCanDefault(1);
					}
					outlesList.add(outles);
				});

				outlesService.saveBatch(outlesList);

				List<UserOutlesStaffRe> userOutlesStaffReList = new ArrayList<>();

				outlesList.forEach((outles -> {
					UserOutlesStaffRe userOutlesStaffRe = new UserOutlesStaffRe();
					userOutlesStaffRe.setOutlesId(outles.getOutlesId());
					userOutlesStaffRe.setStaffId(userInstitutionStaff.getStaffId());
					userOutlesStaffRe.setUserId(userDTO.getUserId());
					userOutlesStaffRe.setInsId(institution.getInsId());
					userOutlesStaffRe.setRoleType(1);
					userOutlesStaffReList.add(userOutlesStaffRe);
				}));

				userOutlesStaffReService.saveBatch(userOutlesStaffReList);

			} else {
				// 11.添加网点
				Outles outles = new Outles();
				outles.setInsId(institution.getInsId());
				outles.setDelFlag(CommonConstants.STATUS_NORMAL);
				outles.setOutlesName(institution.getInsName() + "网点");
//				outles.setUserId(userDTO.getUserId());
				outles.setCanDefault(1);
				outlesService.save(outles);

				// 12.添加员工网点关联数据
				UserOutlesStaffRe userOutlesStaffRe = new UserOutlesStaffRe();
				userOutlesStaffRe.setOutlesId(outles.getOutlesId());
				userOutlesStaffRe.setStaffId(userInstitutionStaff.getStaffId());
				userOutlesStaffRe.setUserId(userDTO.getUserId());
				userOutlesStaffRe.setInsId(institution.getInsId());
				userOutlesStaffRe.setRoleType(1);
				userOutlesStaffReService.save(userOutlesStaffRe);
			}

			// 13.给账户配置权限与部门
			userInstitutionService.setStaffRole(userDTO, userInstitutionStaff);
			List<Integer> dept = new ArrayList<Integer>();
			dept.add(sysDept.getDeptId());
			userInstitutionService.setStaffDept(dept, userInstitutionStaff);

			return institution.getInsId();
		} else {
			throw new Exception("参数异常！");
		}

	}

	@Override
	public Boolean updateInstitution(InstitutionDTO institutionDTO) throws Exception {
		// 1.判断机构DTO是否为空
		if (Objects.nonNull(institutionDTO)) {

			// 2.修改用户信息
			UserDTO userDTO = new UserDTO();
//			userDTO.setUserId(institutionDTO.getUserId());
//			userDTO.setUsername(institutionDTO.getInsPrincipalPhone());
//			userDTO.setPhone(institutionDTO.getInsPrincipalPhone());

			// 5.根据机构类型与字典类型查询角色标识
			SysDictItem sysDictItem = new SysDictItem();
			sysDictItem.setType("ins_type");
			sysDictItem.setValue(institutionDTO.getInsType().toString());

			List<Integer> integers = new ArrayList<>();

			SysDictItem dictItem = sysDictItemService.getDictBySysDictItem(sysDictItem);

			// 6.根据角色标识查询角色信息
			SysRole role = this.sysRoleService.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleCode, dictItem.getLabel() + "_ADMIN").eq(SysRole::getDelFlag, 0));

			integers.add(role.getRoleId());
			userDTO.setRole(integers);

			sysUserService.updateUser(userDTO);

			//修改员工信息
			UserInstitutionStaff userInstitutionStaff = userInstitutionService.getOne(new LambdaQueryWrapper<UserInstitutionStaff>().eq(UserInstitutionStaff::getUserId, userDTO.getUserId()).eq(UserInstitutionStaff::getDelFlag, 0));
//			userInstitutionStaff.setJobTitle(institutionDTO.getInsPrincipalName());
			userInstitutionService.updateById(userInstitutionStaff);

			// 3.修改机构地址
			Address address = this.addressService.getByUserId(institutionDTO.getInsId(), 2);
			BeanUtils.copyProperties(institutionDTO.getAddress(), address);
			addressService.updateById(address);

			// 4.修改机构信息
			Institution institution = new Institution();
			BeanUtils.copyProperties(institutionDTO, institution);

			// 5.给账户配置权限
			userInstitutionService.setStaffRole(userDTO, userInstitutionStaff);

			this.updateById(institution);

			if (institution.getInsType().equals(Integer.valueOf("1500"))) {
				if (Objects.nonNull(institutionDTO.getCourt().getCourtId())) {
					RelationshipAuthenticate relationshipAuthenticate = relationshipAuthenticateService.getOne(new LambdaQueryWrapper<RelationshipAuthenticate>().eq(RelationshipAuthenticate::getAuthenticateId, institution.getInsId()));
//					relationshipAuthenticate.setAuthenticateGoalId(institutionDTO.getCourt().getCourtId());
					relationshipAuthenticateService.updateById(relationshipAuthenticate);
				} else {
					throw new Exception("关联法院为空！");
				}
			}

			return true;

		} else {
			throw new Exception("参数异常！");
		}
	}

	/**
	 * 根据机构insName模糊搜索合作机构
	 *
	 * @param insName
	 * @return
	 */
	@Override
	public IPage<InstitutionAssociatePageVO> getByInsName(Page page, String insName) {
		return this.baseMapper.getByInsName(page, insName, securityUtilsService.getCacheUser().getInsId());
	}

	/**
	 * 分页查询机构信息
	 *
	 * @param page        分页对象
	 * @param institution 参数列表
	 * @return
	 */
	@Override
	public IPage<InstitutionVO> getInstitutionPage(Page page, Institution institution) {
		IPage<InstitutionVO> institutionVosPage = this.baseMapper.getInstitutionVosPage(page, institution);

		List<InstitutionVO> records = institutionVosPage.getRecords();

		records.forEach(((institutionVO) -> {
			Address address = addressService.getByUserId(institutionVO.getInsId(), 2);
			institutionVO.setAddress(address);
		}));

		institutionVosPage.setRecords(records);

		return institutionVosPage;
	}

	/**
	 * 根据id查询机构信息
	 *
	 * @param insId
	 * @return
	 */
	@Override
	public InstitutionVO getInstitutionById(Integer insId) {
		InstitutionVO institutionVO = this.baseMapper.getInstitutionById(insId);
		Address address = this.addressService.getByUserId(institutionVO.getInsId(), 2);
		if (institutionVO.getInsType().equals(Integer.valueOf("1500"))) {
			RelationshipAuthenticate relationshipAuthenticate = relationshipAuthenticateService.getOne(new LambdaQueryWrapper<RelationshipAuthenticate>().eq(RelationshipAuthenticate::getAuthenticateId, institutionVO.getInsId()));
			Court court = courtService.getById(relationshipAuthenticate.getAuthenticateGoalId());
			institutionVO.setCourt(court);
		}
		institutionVO.setAddress(address);
		return institutionVO;
	}

	/**
	 * 通过id禁用机构
	 *
	 * @param insId id
	 * @return R
	 */
	@Override
	public boolean disableById(Integer insId) {
		Institution institution = this.getOne(new LambdaQueryWrapper<Institution>().eq(Institution::getInsId, insId).eq(Institution::getDelFlag, 0));
		return this.removeById(institution);
	}

	/**
	 * 查询当前组织架构信息
	 *
	 * @param organizationQueryDTO
	 * @return
	 */
	@Override
	public List<OrganizationQueryVO> queryCurrentInstitution(OrganizationQueryDTO organizationQueryDTO) {
		Integer roleType = getRoleType();
		// 1.存入当前登录机构id
		organizationQueryDTO.setLoginInsId(securityUtilsService.getCacheUser().getInsId());
		// 2.存入当前登录网点id
		organizationQueryDTO.setLoginOutlesId(securityUtilsService.getCacheUser().getOutlesId());
		// 3.返回相应组织架构信息
		return this.baseMapper.queryCurrentInstitution(organizationQueryDTO, roleType);
	}

	/**
	 * 查询合作组织机构与不包括自己的组织架构
	 *
	 * @param organizationQueryDTO
	 * @return
	 */
	@Override
	public List<OrganizationQueryVO> queryAssociatedInstitutions(OrganizationQueryDTO organizationQueryDTO) {
		Integer roleType = getRoleType();
		// 1.存入当前登录机构id
		organizationQueryDTO.setLoginInsId(securityUtilsService.getCacheUser().getInsId());
		// 2.存入当前登录网点id
		organizationQueryDTO.setLoginOutlesId(securityUtilsService.getCacheUser().getOutlesId());
		// 存入当前登录用户id
		organizationQueryDTO.setLoginUserId(securityUtilsService.getCacheUser().getId());
		// 3.判断类型是查询合作组织架构还是不包括自己的组织架构
		if (organizationQueryDTO.getType() == 0) {
			// 3.1查询合作的组织机构
			if (StringUtil.isNotBlank(organizationQueryDTO.getOutlesName())) {
				Outles outles = this.outlesService.getOne(new LambdaQueryWrapper<Outles>()
						.eq(Outles::getInsId, organizationQueryDTO.getInsId())
						.eq(Outles::getOutlesName, organizationQueryDTO.getOutlesName()));
				if (Objects.nonNull(outles)) {
					organizationQueryDTO.setOutlesId(outles.getOutlesId());
				}
			}

			return this.baseMapper.queryAssociatedInstitutions(organizationQueryDTO, roleType);
		} else {
			// 3.1查询不包括自己的组织架构
			List<OrganizationQueryVO> organizationQueryVOS = new ArrayList<>();
			// 3.2判断机构id是否为空
			if (Objects.isNull(organizationQueryDTO.getInsId())) {
				// 3.2.1根据部分条件查询所有合作机构
				organizationQueryVOS = this.baseMapper.queryAssociatedInstitutions(organizationQueryDTO, roleType);
				// 3.2.2查询当前机构
				Institution institution = this.getById(securityUtilsService.getCacheUser().getInsId());
				OrganizationQueryVO organizationQueryVO = new OrganizationQueryVO();
				organizationQueryVO.setId(institution.getInsId());
				organizationQueryVO.setName(institution.getInsName());
				// 3.2.3将合作机构信息与当前机构合并
				organizationQueryVOS.add(organizationQueryVO);
				// 3.3判断机构id不为空网点id为空
			} else if (Objects.nonNull(organizationQueryDTO.getInsId()) && Objects.isNull(organizationQueryDTO.getOutlesId())) {
				// 3.3.1判断机构id与当前机构id是否一致
				if (organizationQueryDTO.getInsId().equals(organizationQueryDTO.getLoginInsId())) {
					// 3.3.2将除自己以为的所有网点查出
					LambdaQueryWrapper<Outles> query = new LambdaQueryWrapper<>();
					query.eq(Outles::getInsId, organizationQueryDTO.getLoginInsId());
					if (Objects.nonNull(organizationQueryDTO.getLoginOutlesId())) {
						query.ne(Outles::getOutlesId, organizationQueryDTO.getLoginOutlesId());
					}
					query.eq(Outles::getDelFlag, 0);
					List<Outles> list = outlesService.list(query);
					for (Outles outles : list) {
						OrganizationQueryVO organizationQueryVO = new OrganizationQueryVO();
						organizationQueryVO.setId(outles.getOutlesId());
						organizationQueryVO.setName(outles.getOutlesName());
						organizationQueryVOS.add(organizationQueryVO);
					}
				} else {
					// 3.3.3根据机构id查询合作网点
					organizationQueryVOS = this.baseMapper.queryAssociatedInstitutions(organizationQueryDTO, roleType);
				}
				// 3.4判断机构id不为空网点id不为空
			} else {
				// 3.4.1将除自己以为的所有办理人
				if (organizationQueryDTO.getInsId().equals(organizationQueryDTO.getLoginInsId())) {
					organizationQueryVOS = userOutlesStaffReService.queryBranchManager(organizationQueryDTO);
				} else {
					// 3.4.2根据机构id与网点id查询所有合作机构授权网点
					organizationQueryVOS = this.baseMapper.queryAssociatedInstitutions(organizationQueryDTO, roleType);
				}
			}
			return organizationQueryVOS;
		}
	}

	/**
	 * 查询机构是否有简称
	 *
	 * @return
	 */
	@Override
	public boolean getInstitutionAlias() {
		PigUser pigUser = securityUtilsService.getCacheUser();
		Institution institution = this.getById(pigUser.getInsId());
		if (institution.getInsType() == 1100 || institution.getInsType() == 1200) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查询机构名称是否存在
	 *
	 * @return
	 */
	@Override
	public boolean getInstitutionIsInsName(String insName) {
		Integer count = this.baseMapper.getInstitutionIsInsName(insName);
		return count > 0 ? true : false;
	}

	/**
	 * 获取当前用户登录的角色集合
	 *
	 * @return
	 */
	private Integer getRoleType() {
		// 获取当前员工的所有角色信息
		List<Integer> roles = SecurityUtils.getRoles();

		// 根基所有角色id查询角色标识
		List<String> roleCodes = sysRoleService.listByIds(roles).stream().map(SysRole::getRoleCode).collect(Collectors.toList());

		// 设置默认角色类型
		Integer roleType = 10000;

		// 循环角色标识集合判断如果是运营平台管理员将角色类型为0
		for (String roleCode : roleCodes) {
			if (roleCode.equals("PLAT_ADMIN")) {
				roleType = 0;
			}
		}
		return roleType;
	}


	/********************************************************************************************/

	@Override
	public IPage<InstitutionPageVO> queryPage(Page page, InstitutionPageDTO institutionPageDTO) {
		return this.baseMapper.selectPage(page, institutionPageDTO);
	}

	@Override
	@Transactional
	public int addInstitution(InstitutionAddDTO institutionAddDTO) {
		LambdaQueryWrapper<Institution> queryWrapper = new LambdaQueryWrapper<>();
		queryWrapper.eq(Institution::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.eq(Institution::getInsName, institutionAddDTO.getInsName());
		int save = 0;
		Institution institution = new Institution();

		BeanUtils.copyProperties(institutionAddDTO, institution);
		save = this.baseMapper.insert(institution);

		//如果机构类型为法院，关联法院与机构
		if (institution.getInsType().equals(Integer.valueOf("1500"))) {
			if (Objects.nonNull(institutionAddDTO.getCourtId())) {
				RelationshipAuthenticate relationshipAuthenticate = new RelationshipAuthenticate();
				relationshipAuthenticate.setAuthenticateId(institution.getInsId());
				relationshipAuthenticate.setAuthenticateGoalId(institutionAddDTO.getCourtId());
				relationshipAuthenticateService.save(relationshipAuthenticate);
			} else {
				throw new RuntimeException("关联法院为空！");
			}
		}

		//如果机构类型为拍辅、清收、律所和银行添加企业信息
		InstitutionModifyDTO institutionModifyDTO = new InstitutionModifyDTO();
		BeanUtils.copyProperties(institutionAddDTO, institutionModifyDTO);
		setIstitutionSubject(institutionModifyDTO, institution);

		// 判断地址是否为空
		if (Objects.nonNull(institutionAddDTO.getInformationAddress()) || Objects.nonNull(institutionAddDTO.getCode())) {
			// 添加地址
			Address address = new Address();
			BeanUtils.copyProperties(institutionAddDTO, address);
			address.setDelFlag(CommonConstants.STATUS_NORMAL);
			// 类型2=机构地址
			address.setType(2);
			address.setUserId(institution.getInsId());
			addressService.save(address);
		}
		// 添加机构负责人用户
		InsOutlesUserObjectDTO insOutlesUserAddDTO = new InsOutlesUserObjectDTO();
		insOutlesUserAddDTO.setInsId(institution.getInsId());
		insOutlesUserAddDTO.setType(1);
		insOutlesUserAddDTO.setUserList(institutionAddDTO.getUserList());
		insOutlesUserService.addInsOutlesUser(insOutlesUserAddDTO);
//
//		// 新增机构成功后，自动添加默认网点
//		Outles outles = new Outles();
//		outles.setInsId(insOutlesUserAddDTO.getInsId());
//		outlesService.addDefaultOutles(outles);

		return save;
	}

	private void setIstitutionSubject(InstitutionModifyDTO institutionModifyDTO, Institution institution) {
//		if (institutionModifyDTO.getSubject().getSubjectId() == null){
		//如果机构类型为拍辅、清收、律所和银行添加企业信息
		if (institutionModifyDTO.getInsType().equals(Integer.valueOf("1100")) || institutionModifyDTO.getInsType().equals(Integer.valueOf("1200")) || institutionModifyDTO.getInsType().equals(Integer.valueOf("1300")) || institutionModifyDTO.getInsType().equals(Integer.valueOf("1400"))) {
			SubjectVO subjectVO = subjectService.getByUnifiedIdentity(institutionModifyDTO.getSubject().getUnifiedIdentity());
			if (Objects.nonNull(subjectVO)) {

				//更新主体认证状态
				Subject subject = new Subject();

				BeanUtils.copyProperties(subjectVO, subject);

				this.subjectService.updateById(subject);

			} else {
				subjectVO = new SubjectVO();

				//主体不存在创建主体添加机构与主体认证信息
				int subjectId = this.subjectService.addSubjectOrAddress(institutionModifyDTO.getSubject());
//
				subjectVO.setSubjectId(subjectId);
			}

			InstitutionSubjectRe institutionSubjectRe = institutionSubjectReService.getOne(new LambdaQueryWrapper<InstitutionSubjectRe>()
					.eq(InstitutionSubjectRe::getDelFlag, 0)
					.eq(InstitutionSubjectRe::getSubjectId, subjectVO.getSubjectId()).eq(InstitutionSubjectRe::getInsId, institution.getInsId()));

//			添加主体认证信息
			if (Objects.isNull(institutionSubjectRe)) {
				institutionSubjectRe = new InstitutionSubjectRe();
				institutionSubjectRe.setInsId(institution.getInsId());
				institutionSubjectRe.setSubjectId(subjectVO.getSubjectId());
				institutionSubjectReService.save(institutionSubjectRe);
			} else {
				institutionSubjectReService.updateById(institutionSubjectRe);
			}
		}
	}

	@Override
	@Transactional
	public int modifyInstitutionById(InstitutionModifyDTO institutionModifyDTO) {
		int modify = 0;
		Institution institution = new Institution();
		BeanUtils.copyProperties(institutionModifyDTO, institution);
		modify = this.baseMapper.updateById(institution);
		if (Objects.nonNull(institutionModifyDTO.getInformationAddress()) || Objects.nonNull(institutionModifyDTO.getCode())) {
			// 更新地址
			Address address = new Address();
			BeanUtils.copyProperties(institutionModifyDTO, address);
			address.setUserId(institution.getInsId());
			address.setType(2);
			addressService.saveOrUpdate(address);
		}

		if (institutionModifyDTO.getSubject() != null) {

			//如果机构类型为拍辅、清收、律所和银行添加企业信息
			setIstitutionSubject(institutionModifyDTO, institution);
		}

		return modify;
	}

	@Override
	public InstitutionDetailsVO queryById(Integer insId) {
		InstitutionDetailsVO institutionVO = this.baseMapper.selectDetailsById(insId);
		if (institutionVO.getInsType().equals(Integer.valueOf("1500"))) {
			RelationshipAuthenticate relationshipAuthenticate = relationshipAuthenticateService.getOne(new LambdaQueryWrapper<RelationshipAuthenticate>().eq(RelationshipAuthenticate::getAuthenticateId, institutionVO.getInsId()));
			institutionVO.setCourtId(relationshipAuthenticate.getAuthenticateGoalId());
		}

		if (institutionVO.getInsType().equals(Integer.valueOf("1100")) || institutionVO.getInsType().equals(Integer.valueOf("1200")) || institutionVO.getInsType().equals(Integer.valueOf("1300")) || institutionVO.getInsType().equals(Integer.valueOf("1400"))) {
			InstitutionSubjectRe institutionSubjectRe = this.institutionSubjectReService.getOne(new LambdaQueryWrapper<InstitutionSubjectRe>().eq(InstitutionSubjectRe::getInsId, institutionVO.getInsId()));
			if (Objects.nonNull(institutionSubjectRe)) {
				institutionVO.setSubject(this.subjectService.getSubjectDetailBySubjectId(institutionSubjectRe.getSubjectId()));
			}
		}
		List<InsOutlesUserListVO> userList = insOutlesUserService.queryUserList(1, insId, 0);
		institutionVO.setUserList(userList);
		return institutionVO;
	}

	@Override
	public List<Institution> queryByUserIdList(Integer userId) {
		return this.baseMapper.selectByUserId(userId);
	}

	@Override
	public Institution queryByInsId(Integer insId) {
		return this.baseMapper.selectById(insId);
	}

	@Override
	public ReselectInfoVO queryReselectInfo(Integer insId, Integer outlesId) {
		ReselectInfoVO reselectInfoVO = new ReselectInfoVO();
		Institution institution = this.baseMapper.selectById(insId);
		reselectInfoVO.setInstitution(institution);
		if (Objects.nonNull(outlesId) || outlesId != 0) {
			Outles outles = outlesService.queryByOutlesId(outlesId);
			reselectInfoVO.setOutles(outles);
		}
		return reselectInfoVO;
	}

	@Override
	public List<OrganizationQueryVO> queryInsSelect(InstitutionSelectDTO insOulesSelectDTO) {
		List<OrganizationQueryVO> organizationQueryVOS = new ArrayList<>();
		Integer insId = jurisdictionUtilsService.queryByInsId("PLAT_");
		Integer type = insOulesSelectDTO.getType();
		if (type == 1) {
			organizationQueryVOS = this.baseMapper.pageCooperateByInsId(insOulesSelectDTO, insId);
		} else if (type == 2) {
			organizationQueryVOS = this.baseMapper.querySelectByInsId(insId);
		}
		return organizationQueryVOS;
	}

	/**
	 * 根据项目机构id查询项目机构关联机构下拉框
	 *
	 * @param projectInstitutionSelectDTO
	 * @return
	 */
	public List<OrganizationQueryVO> queryProjectInsSelect(ProjectInstitutionSelectDTO projectInstitutionSelectDTO) {
		return this.baseMapper.queryProjectInsSelect(projectInstitutionSelectDTO);
	}

	@Override
	@Transactional
	public Integer deleteByInsId(Integer insId) {
		QueryWrapper<InsOutlesUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(InsOutlesUser::getInsId, insId);
		queryWrapper.lambda().eq(InsOutlesUser::getDelFlag, CommonConstants.STATUS_NORMAL);
		// 查询机构下所有用户
		List<InsOutlesUser> insOutlesUserList = insOutlesUserService.list(queryWrapper);
		insOutlesUserList.stream().forEach(item -> {
			QueryWrapper<InsOutlesUser> userQueryWrapper = new QueryWrapper<>();
			userQueryWrapper.lambda().eq(InsOutlesUser::getUserId, item.getUserId());
			userQueryWrapper.lambda().eq(InsOutlesUser::getDelFlag, CommonConstants.STATUS_NORMAL);
			userQueryWrapper.lambda().ne(InsOutlesUser::getInsId, insId);
			// 查询除删除以外的网点没有所属机构和网点外
			List<InsOutlesUser> userList = insOutlesUserService.list(userQueryWrapper);
			if (userList.size() == 0) {
				UpdateWrapper<SysUser> userUpdateWrapper = new UpdateWrapper<>();
				userUpdateWrapper.lambda().eq(SysUser::getUserId, item.getUserId());
				userUpdateWrapper.lambda().set(SysUser::getDelFlag, CommonConstants.STATUS_DEL);
				// 修改用户状态为删除
				sysUserService.update(userUpdateWrapper);
			}
		});
		UpdateWrapper<InsOutlesUser> userUpdateWrapper = new UpdateWrapper<>();
		userUpdateWrapper.lambda().eq(InsOutlesUser::getInsId, insId);
		userUpdateWrapper.lambda().set(InsOutlesUser::getDelFlag, CommonConstants.STATUS_DEL);
		// 修改机构网点用户关联变状态为删除
		insOutlesUserService.update(userUpdateWrapper);
		// 修改机构状态为删除
		UpdateWrapper<Institution> institutionUpdateWrapper = new UpdateWrapper();
		institutionUpdateWrapper.lambda().eq(Institution::getInsId, insId);
		institutionUpdateWrapper.lambda().set(Institution::getDelFlag, CommonConstants.STATUS_DEL);
		this.update(institutionUpdateWrapper);
		// 删除机构主体关联
		QueryWrapper<InstitutionSubjectRe> subjectReQueryWrapper = new QueryWrapper<>();
		subjectReQueryWrapper.lambda().eq(InstitutionSubjectRe::getInsId, insId);
		institutionSubjectReService.remove(subjectReQueryWrapper);

		return 1;
	}

	/**
	 * 查询当期机构的机构类型
	 * @return
	 */
	@Override
	public Integer getInstitutionInsType() {
		return this.getById(securityUtilsService.getCacheUser().getInsId()).getInsType();
	}

}
