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
import com.pig4cloud.pig.admin.api.dto.OutlesDTO;
import com.pig4cloud.pig.admin.api.dto.UserInstitutionStaffDTO;
import com.pig4cloud.pig.admin.api.entity.UserInstitutionStaff;
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.vo.OutlesVO;
import com.pig4cloud.pig.admin.mapper.OutlesMapper;
import com.pig4cloud.pig.admin.service.*;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author xiaojun
 * @date 2021-08-17 16:22:59
 */
@Service
public class OutlesServiceImpl extends ServiceImpl<OutlesMapper, Outles> implements OutlesService {

	@Autowired
	AddressService addressService;

	@Autowired
	InstitutionService institutionService;

	@Autowired
	RegionService regionService;

	@Autowired
	UserInstitutionStaffService userInstitutionStaffService;

	@Autowired
	UserOutlesStaffReService userOutlesStaffReService;

	@Autowired
	SysRoleService sysRoleService;

	/**
	 * 新增
	 *
	 * @param outlesDTO
	 * @return R
	 */
	@Override
	@Transactional
	public int saveOutles(OutlesDTO outlesDTO) {
		// 1.根据当前登录机构id查询机构
		Institution institution = institutionService.getById(SecurityUtils.getUser().getInsId());

		outlesDTO.setInsId(institution.getInsId());

//		outlesDTO.setUserId(institution.getUserId());

		Outles outles = new Outles();
		BeanUtils.copyProperties(outlesDTO, outles);

		outles.setCanDefault(0);

		// 2.创建网点
		this.save(outles);

		// 3.新增网点地址
		Address address = new Address();
		BeanUtils.copyProperties(outlesDTO.getAddress(), address);
		address.setUserId(outles.getOutlesId());
		address.setType(3);
		addressService.save(address);

		UserInstitutionStaffDTO userInstitutionStaffDTO = new UserInstitutionStaffDTO();

		List<Integer> outlesList = new ArrayList<>();

		outlesList.add(outles.getOutlesId());

		userInstitutionStaffDTO.setOutles(outlesList);

		userInstitutionStaffDTO.setRoleType(1);

		// 根据用户id与机构id查询网点信息集合
		UserInstitutionStaff userInstitutionStaff = userInstitutionStaffService.getOne(new LambdaQueryWrapper<UserInstitutionStaff>()
				.eq(UserInstitutionStaff::getUserId, SecurityUtils.getUser().getId())
				.eq(UserInstitutionStaff::getInsId, SecurityUtils.getUser().getInsId())
				.eq(UserInstitutionStaff::getDelFlag, 0));

		userInstitutionStaffService.addUserOutlesStaffRe(userInstitutionStaffDTO, userInstitutionStaff);

		return outles.getOutlesId();
	}

	/**
	 * 分页查询
	 *
	 * @param page      分页对象
	 * @param outlesDTO
	 * @return
	 */
	@Override
	public IPage<OutlesVO> pageOutles(Page page, OutlesDTO outlesDTO) {
		outlesDTO.setInsId(SecurityUtils.getUser().getInsId());
		IPage<OutlesVO> outlesVOIPage = this.baseMapper.pageOutles(page, outlesDTO);
//		List<OutlesVO> records = outlesVOIPage.getRecords();

//		records.forEach(((outlesVO -> {
//			Address address = addressService.getByUserId(outlesVO.getOutlesId(), 3);
//			outlesVO.setAddress(address);
//		})));

//		outlesVOIPage.setRecords(records);

		return outlesVOIPage;
	}

	/**
	 * 修改
	 *
	 * @param outlesDTO
	 * @return R
	 */
	@Override
	public boolean updateByOutlesId(OutlesDTO outlesDTO) {

		Outles outles = new Outles();
		BeanUtils.copyProperties(outlesDTO, outles);

		this.updateById(outles);
		// 3.判断地址id是否为空
		if (Objects.nonNull(outlesDTO.getAddressId())) {

			// 2.查询网点地址
			Address address = addressService.getByUserId(outles.getOutlesId(), 3);
			BeanUtils.copyProperties(outlesDTO.getAddress(), address);

			// 3.1不为空修改地址
			address.setAddressId(outlesDTO.getAddressId());
			addressService.updateById(address);
		} else {
			// 3.2新增网点地址
			Address address = new Address();
			BeanUtils.copyProperties(outlesDTO.getAddress(), address);
			address.setUserId(outles.getOutlesId());
			address.setType(3);
			addressService.save(address);
		}

		// 3.修改网点信息
		return true;
	}

	/**
	 * 通过id查询
	 *
	 * @param outlesId id
	 * @return R
	 */
	@Override
	public OutlesVO getByIdOutles(Integer outlesId) {

		OutlesVO outlesVO = this.baseMapper.getByIdOutles(outlesId);

		getAddress(outlesVO);

		return outlesVO;
	}

	/**
	 * 查询并保存地址信息
	 * @param outlesVO
	 */
	private void getAddress(OutlesVO outlesVO){
		Address address = addressService.getByUserId(outlesVO.getOutlesId(), 3);
		outlesVO.setAddress(address);
	}

	@Override
	public List<OutlesVO> batchQueryOutlesId(List<Integer> outlesIds) {
		List<OutlesVO> outlesVOS = this.baseMapper.batchQueryOutlesId(outlesIds);

		outlesVOS.forEach(((outlesVO) -> {
			getAddress(outlesVO);
		}));

		return outlesVOS;
	}

	/**
	 * 根据用户id查询用户网点集合
	 *
	 * @return
	 */
	@Override
	public List<Outles> getOutlesListByUserId() {
		return this.baseMapper.getOutlesListByUserId(SecurityUtils.getUser().getId());
	}

	/**
	 * 根据员工id查询用户网点集合
	 *
	 * @return
	 */
	@Override
	public List<Outles> getOutlesListByStaffId() {
		// 根据用户id与机构id查询网点信息集合
		UserInstitutionStaff userInstitutionStaff = userInstitutionStaffService.getOne(new LambdaQueryWrapper<UserInstitutionStaff>()
				.eq(UserInstitutionStaff::getUserId, SecurityUtils.getUser().getId())
				.eq(UserInstitutionStaff::getInsId, SecurityUtils.getUser().getInsId())
				.eq(UserInstitutionStaff::getDelFlag, 0));
		return this.baseMapper.getOutlesListByStaffId(userInstitutionStaff.getStaffId());
	}

	/**
	 * 通过机构id和网点名称查询所有网点
	 *
	 * @param insId
	 * @return R
	 */
	@Override
	public List<Outles> getInsIdOrOutlesNameList(Integer insId, String outlesName) {
		return this.baseMapper.getInsIdOrOutlesNameList(insId, outlesName);
	}

	/**
	 * 通过用户id和网点id查询该用户是否关联该网点
	 *
	 * @param userId,outlesId
	 * @return R
	 */
	@Override
	public Outles getUserIdOutlesIdByRelevance(Integer userId, Integer outlesId) {
		UserOutlesStaffRe userOutlesStaffRe = userOutlesStaffReService.getOne(new LambdaQueryWrapper<UserOutlesStaffRe>().eq(UserOutlesStaffRe::getUserId, userId).eq(UserOutlesStaffRe::getOutlesId, outlesId));
		if (userOutlesStaffRe != null) {
			return this.baseMapper.selectById(userOutlesStaffRe.getOutlesId());
		}
		return null;
	}
}
