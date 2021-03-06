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
	 * ????????????
	 *
	 * @param institutionDTO
	 * @return
	 * @throws Exception
	 */
	@Override
	@Transactional
	public int saveInstitution(InstitutionDTO institutionDTO) throws Exception {

		// 1.????????????DTO??????????????????
		if (Objects.nonNull(institutionDTO)) {

			// 2.?????????????????????????????????????????????
			SysUser sysUser = this.sysUserService.getOne(new LambdaQueryWrapper<SysUser>()
//					.eq(SysUser::getPhone, institutionDTO.getInsPrincipalPhone())
					.eq(SysUser::getDelFlag, 0));

			// 3.??????????????????
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

			// 5.???????????????????????????????????????????????????
			SysDictItem sysDictItem = new SysDictItem();
			sysDictItem.setType("ins_type");
			sysDictItem.setValue(institutionDTO.getInsType().toString());

			SysDictItem dictItem = sysDictItemService.getDictBySysDictItem(sysDictItem);

			// 6.????????????????????????????????????
			SysRole role = this.sysRoleService.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleCode, dictItem.getLabel() + "_ADMIN").eq(SysRole::getDelFlag, 0));

			integers.add(role.getRoleId());
			userDTO.setRole(integers);
			userDTO.setUserType(1);
			sysUserService.saveUser(userDTO);

			// 8.????????????
			Institution institution = new Institution();
			BeanUtils.copyProperties(institutionDTO, institution);
//			institution.setUserId(userDTO.getUserId());
			this.save(institution);

			// 7.??????????????????
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
					throw new Exception("?????????????????????");
				}
			}

			// 9.????????????
			SysDept sysDept = new SysDept();
			sysDept.setInsId(institution.getInsId());
			sysDept.setName(institution.getInsName());
			sysDept.setParentId(0);
			sysDept.setSort(1);
			sysDeptService.saveDept(sysDept);

			// 10.????????????
			UserInstitutionStaff userInstitutionStaff = new UserInstitutionStaff();
			userInstitutionStaff.setUserId(userDTO.getUserId());
			userInstitutionStaff.setInsId(institution.getInsId());
			userInstitutionStaff.setEntryTime(LocalDateTime.now());
//			userInstitutionStaff.setJobTitle(institutionDTO.getInsPrincipalName());
			userInstitutionService.save(userInstitutionStaff);

			if (institution.getInsType().equals(Integer.valueOf("1500"))) {

				List<String> courtNames = new ArrayList<>();
				courtNames.add("?????????");
				courtNames.add("?????????");
				courtNames.add("?????????");

				List<Outles> outlesList = new ArrayList<>();

				courtNames.forEach((courtName) -> {
					Outles outles = new Outles();
					outles.setInsId(institution.getInsId());
					outles.setDelFlag(CommonConstants.STATUS_NORMAL);
					outles.setOutlesName(courtName);
//					outles.setUserId(userDTO.getUserId());
					if (courtName.equals("?????????")) {
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
				// 11.????????????
				Outles outles = new Outles();
				outles.setInsId(institution.getInsId());
				outles.setDelFlag(CommonConstants.STATUS_NORMAL);
				outles.setOutlesName(institution.getInsName() + "??????");
//				outles.setUserId(userDTO.getUserId());
				outles.setCanDefault(1);
				outlesService.save(outles);

				// 12.??????????????????????????????
				UserOutlesStaffRe userOutlesStaffRe = new UserOutlesStaffRe();
				userOutlesStaffRe.setOutlesId(outles.getOutlesId());
				userOutlesStaffRe.setStaffId(userInstitutionStaff.getStaffId());
				userOutlesStaffRe.setUserId(userDTO.getUserId());
				userOutlesStaffRe.setInsId(institution.getInsId());
				userOutlesStaffRe.setRoleType(1);
				userOutlesStaffReService.save(userOutlesStaffRe);
			}

			// 13.??????????????????????????????
			userInstitutionService.setStaffRole(userDTO, userInstitutionStaff);
			List<Integer> dept = new ArrayList<Integer>();
			dept.add(sysDept.getDeptId());
			userInstitutionService.setStaffDept(dept, userInstitutionStaff);

			return institution.getInsId();
		} else {
			throw new Exception("???????????????");
		}

	}

	@Override
	public Boolean updateInstitution(InstitutionDTO institutionDTO) throws Exception {
		// 1.????????????DTO????????????
		if (Objects.nonNull(institutionDTO)) {

			// 2.??????????????????
			UserDTO userDTO = new UserDTO();
//			userDTO.setUserId(institutionDTO.getUserId());
//			userDTO.setUsername(institutionDTO.getInsPrincipalPhone());
//			userDTO.setPhone(institutionDTO.getInsPrincipalPhone());

			// 5.???????????????????????????????????????????????????
			SysDictItem sysDictItem = new SysDictItem();
			sysDictItem.setType("ins_type");
			sysDictItem.setValue(institutionDTO.getInsType().toString());

			List<Integer> integers = new ArrayList<>();

			SysDictItem dictItem = sysDictItemService.getDictBySysDictItem(sysDictItem);

			// 6.????????????????????????????????????
			SysRole role = this.sysRoleService.getOne(new LambdaQueryWrapper<SysRole>().eq(SysRole::getRoleCode, dictItem.getLabel() + "_ADMIN").eq(SysRole::getDelFlag, 0));

			integers.add(role.getRoleId());
			userDTO.setRole(integers);

			sysUserService.updateUser(userDTO);

			//??????????????????
			UserInstitutionStaff userInstitutionStaff = userInstitutionService.getOne(new LambdaQueryWrapper<UserInstitutionStaff>().eq(UserInstitutionStaff::getUserId, userDTO.getUserId()).eq(UserInstitutionStaff::getDelFlag, 0));
//			userInstitutionStaff.setJobTitle(institutionDTO.getInsPrincipalName());
			userInstitutionService.updateById(userInstitutionStaff);

			// 3.??????????????????
			Address address = this.addressService.getByUserId(institutionDTO.getInsId(), 2);
			BeanUtils.copyProperties(institutionDTO.getAddress(), address);
			addressService.updateById(address);

			// 4.??????????????????
			Institution institution = new Institution();
			BeanUtils.copyProperties(institutionDTO, institution);

			// 5.?????????????????????
			userInstitutionService.setStaffRole(userDTO, userInstitutionStaff);

			this.updateById(institution);

			if (institution.getInsType().equals(Integer.valueOf("1500"))) {
				if (Objects.nonNull(institutionDTO.getCourt().getCourtId())) {
					RelationshipAuthenticate relationshipAuthenticate = relationshipAuthenticateService.getOne(new LambdaQueryWrapper<RelationshipAuthenticate>().eq(RelationshipAuthenticate::getAuthenticateId, institution.getInsId()));
//					relationshipAuthenticate.setAuthenticateGoalId(institutionDTO.getCourt().getCourtId());
					relationshipAuthenticateService.updateById(relationshipAuthenticate);
				} else {
					throw new Exception("?????????????????????");
				}
			}

			return true;

		} else {
			throw new Exception("???????????????");
		}
	}

	/**
	 * ????????????insName????????????????????????
	 *
	 * @param insName
	 * @return
	 */
	@Override
	public IPage<InstitutionAssociatePageVO> getByInsName(Page page, String insName) {
		return this.baseMapper.getByInsName(page, insName, securityUtilsService.getCacheUser().getInsId());
	}

	/**
	 * ????????????????????????
	 *
	 * @param page        ????????????
	 * @param institution ????????????
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
	 * ??????id??????????????????
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
	 * ??????id????????????
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
	 * ??????????????????????????????
	 *
	 * @param organizationQueryDTO
	 * @return
	 */
	@Override
	public List<OrganizationQueryVO> queryCurrentInstitution(OrganizationQueryDTO organizationQueryDTO) {
		Integer roleType = getRoleType();
		// 1.????????????????????????id
		organizationQueryDTO.setLoginInsId(securityUtilsService.getCacheUser().getInsId());
		// 2.????????????????????????id
		organizationQueryDTO.setLoginOutlesId(securityUtilsService.getCacheUser().getOutlesId());
		// 3.??????????????????????????????
		return this.baseMapper.queryCurrentInstitution(organizationQueryDTO, roleType);
	}

	/**
	 * ?????????????????????????????????????????????????????????
	 *
	 * @param organizationQueryDTO
	 * @return
	 */
	@Override
	public List<OrganizationQueryVO> queryAssociatedInstitutions(OrganizationQueryDTO organizationQueryDTO) {
		Integer roleType = getRoleType();
		// 1.????????????????????????id
		organizationQueryDTO.setLoginInsId(securityUtilsService.getCacheUser().getInsId());
		// 2.????????????????????????id
		organizationQueryDTO.setLoginOutlesId(securityUtilsService.getCacheUser().getOutlesId());
		// ????????????????????????id
		organizationQueryDTO.setLoginUserId(securityUtilsService.getCacheUser().getId());
		// 3.???????????????????????????????????????????????????????????????????????????
		if (organizationQueryDTO.getType() == 0) {
			// 3.1???????????????????????????
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
			// 3.1????????????????????????????????????
			List<OrganizationQueryVO> organizationQueryVOS = new ArrayList<>();
			// 3.2????????????id????????????
			if (Objects.isNull(organizationQueryDTO.getInsId())) {
				// 3.2.1??????????????????????????????????????????
				organizationQueryVOS = this.baseMapper.queryAssociatedInstitutions(organizationQueryDTO, roleType);
				// 3.2.2??????????????????
				Institution institution = this.getById(securityUtilsService.getCacheUser().getInsId());
				OrganizationQueryVO organizationQueryVO = new OrganizationQueryVO();
				organizationQueryVO.setId(institution.getInsId());
				organizationQueryVO.setName(institution.getInsName());
				// 3.2.3??????????????????????????????????????????
				organizationQueryVOS.add(organizationQueryVO);
				// 3.3????????????id???????????????id??????
			} else if (Objects.nonNull(organizationQueryDTO.getInsId()) && Objects.isNull(organizationQueryDTO.getOutlesId())) {
				// 3.3.1????????????id???????????????id????????????
				if (organizationQueryDTO.getInsId().equals(organizationQueryDTO.getLoginInsId())) {
					// 3.3.2???????????????????????????????????????
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
					// 3.3.3????????????id??????????????????
					organizationQueryVOS = this.baseMapper.queryAssociatedInstitutions(organizationQueryDTO, roleType);
				}
				// 3.4????????????id???????????????id?????????
			} else {
				// 3.4.1????????????????????????????????????
				if (organizationQueryDTO.getInsId().equals(organizationQueryDTO.getLoginInsId())) {
					organizationQueryVOS = userOutlesStaffReService.queryBranchManager(organizationQueryDTO);
				} else {
					// 3.4.2????????????id?????????id????????????????????????????????????
					organizationQueryVOS = this.baseMapper.queryAssociatedInstitutions(organizationQueryDTO, roleType);
				}
			}
			return organizationQueryVOS;
		}
	}

	/**
	 * ???????????????????????????
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
	 * ??????????????????????????????
	 *
	 * @return
	 */
	@Override
	public boolean getInstitutionIsInsName(String insName) {
		Integer count = this.baseMapper.getInstitutionIsInsName(insName);
		return count > 0 ? true : false;
	}

	/**
	 * ???????????????????????????????????????
	 *
	 * @return
	 */
	private Integer getRoleType() {
		// ???????????????????????????????????????
		List<Integer> roles = SecurityUtils.getRoles();

		// ??????????????????id??????????????????
		List<String> roleCodes = sysRoleService.listByIds(roles).stream().map(SysRole::getRoleCode).collect(Collectors.toList());

		// ????????????????????????
		Integer roleType = 10000;

		// ??????????????????????????????????????????????????????????????????????????????0
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

		//???????????????????????????????????????????????????
		if (institution.getInsType().equals(Integer.valueOf("1500"))) {
			if (Objects.nonNull(institutionAddDTO.getCourtId())) {
				RelationshipAuthenticate relationshipAuthenticate = new RelationshipAuthenticate();
				relationshipAuthenticate.setAuthenticateId(institution.getInsId());
				relationshipAuthenticate.setAuthenticateGoalId(institutionAddDTO.getCourtId());
				relationshipAuthenticateService.save(relationshipAuthenticate);
			} else {
				throw new RuntimeException("?????????????????????");
			}
		}

		//????????????????????????????????????????????????????????????????????????
		InstitutionModifyDTO institutionModifyDTO = new InstitutionModifyDTO();
		BeanUtils.copyProperties(institutionAddDTO, institutionModifyDTO);
		setIstitutionSubject(institutionModifyDTO, institution);

		// ????????????????????????
		if (Objects.nonNull(institutionAddDTO.getInformationAddress()) || Objects.nonNull(institutionAddDTO.getCode())) {
			// ????????????
			Address address = new Address();
			BeanUtils.copyProperties(institutionAddDTO, address);
			address.setDelFlag(CommonConstants.STATUS_NORMAL);
			// ??????2=????????????
			address.setType(2);
			address.setUserId(institution.getInsId());
			addressService.save(address);
		}
		// ???????????????????????????
		InsOutlesUserObjectDTO insOutlesUserAddDTO = new InsOutlesUserObjectDTO();
		insOutlesUserAddDTO.setInsId(institution.getInsId());
		insOutlesUserAddDTO.setType(1);
		insOutlesUserAddDTO.setUserList(institutionAddDTO.getUserList());
		insOutlesUserService.addInsOutlesUser(insOutlesUserAddDTO);
//
//		// ????????????????????????????????????????????????
//		Outles outles = new Outles();
//		outles.setInsId(insOutlesUserAddDTO.getInsId());
//		outlesService.addDefaultOutles(outles);

		return save;
	}

	private void setIstitutionSubject(InstitutionModifyDTO institutionModifyDTO, Institution institution) {
//		if (institutionModifyDTO.getSubject().getSubjectId() == null){
		//????????????????????????????????????????????????????????????????????????
		if (institutionModifyDTO.getInsType().equals(Integer.valueOf("1100")) || institutionModifyDTO.getInsType().equals(Integer.valueOf("1200")) || institutionModifyDTO.getInsType().equals(Integer.valueOf("1300")) || institutionModifyDTO.getInsType().equals(Integer.valueOf("1400"))) {
			SubjectVO subjectVO = subjectService.getByUnifiedIdentity(institutionModifyDTO.getSubject().getUnifiedIdentity());
			if (Objects.nonNull(subjectVO)) {

				//????????????????????????
				Subject subject = new Subject();

				BeanUtils.copyProperties(subjectVO, subject);

				this.subjectService.updateById(subject);

			} else {
				subjectVO = new SubjectVO();

				//????????????????????????????????????????????????????????????
				int subjectId = this.subjectService.addSubjectOrAddress(institutionModifyDTO.getSubject());
//
				subjectVO.setSubjectId(subjectId);
			}

			InstitutionSubjectRe institutionSubjectRe = institutionSubjectReService.getOne(new LambdaQueryWrapper<InstitutionSubjectRe>()
					.eq(InstitutionSubjectRe::getDelFlag, 0)
					.eq(InstitutionSubjectRe::getSubjectId, subjectVO.getSubjectId()).eq(InstitutionSubjectRe::getInsId, institution.getInsId()));

//			????????????????????????
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
			// ????????????
			Address address = new Address();
			BeanUtils.copyProperties(institutionModifyDTO, address);
			address.setUserId(institution.getInsId());
			address.setType(2);
			addressService.saveOrUpdate(address);
		}

		if (institutionModifyDTO.getSubject() != null) {

			//????????????????????????????????????????????????????????????????????????
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
	 * ??????????????????id???????????????????????????????????????
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
		// ???????????????????????????
		List<InsOutlesUser> insOutlesUserList = insOutlesUserService.list(queryWrapper);
		insOutlesUserList.stream().forEach(item -> {
			QueryWrapper<InsOutlesUser> userQueryWrapper = new QueryWrapper<>();
			userQueryWrapper.lambda().eq(InsOutlesUser::getUserId, item.getUserId());
			userQueryWrapper.lambda().eq(InsOutlesUser::getDelFlag, CommonConstants.STATUS_NORMAL);
			userQueryWrapper.lambda().ne(InsOutlesUser::getInsId, insId);
			// ????????????????????????????????????????????????????????????
			List<InsOutlesUser> userList = insOutlesUserService.list(userQueryWrapper);
			if (userList.size() == 0) {
				UpdateWrapper<SysUser> userUpdateWrapper = new UpdateWrapper<>();
				userUpdateWrapper.lambda().eq(SysUser::getUserId, item.getUserId());
				userUpdateWrapper.lambda().set(SysUser::getDelFlag, CommonConstants.STATUS_DEL);
				// ???????????????????????????
				sysUserService.update(userUpdateWrapper);
			}
		});
		UpdateWrapper<InsOutlesUser> userUpdateWrapper = new UpdateWrapper<>();
		userUpdateWrapper.lambda().eq(InsOutlesUser::getInsId, insId);
		userUpdateWrapper.lambda().set(InsOutlesUser::getDelFlag, CommonConstants.STATUS_DEL);
		// ????????????????????????????????????????????????
		insOutlesUserService.update(userUpdateWrapper);
		// ???????????????????????????
		UpdateWrapper<Institution> institutionUpdateWrapper = new UpdateWrapper();
		institutionUpdateWrapper.lambda().eq(Institution::getInsId, insId);
		institutionUpdateWrapper.lambda().set(Institution::getDelFlag, CommonConstants.STATUS_DEL);
		this.update(institutionUpdateWrapper);
		// ????????????????????????
		QueryWrapper<InstitutionSubjectRe> subjectReQueryWrapper = new QueryWrapper<>();
		subjectReQueryWrapper.lambda().eq(InstitutionSubjectRe::getInsId, insId);
		institutionSubjectReService.remove(subjectReQueryWrapper);

		return 1;
	}

	/**
	 * ?????????????????????????????????
	 * @return
	 */
	@Override
	public Integer getInstitutionInsType() {
		return this.getById(securityUtilsService.getCacheUser().getInsId()).getInsType();
	}

}
