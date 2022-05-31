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
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.*;
import com.pig4cloud.pig.admin.api.entity.UserInstitutionStaff;
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.feign.RemoteSysRoleService;
import com.pig4cloud.pig.admin.api.vo.*;
import com.pig4cloud.pig.admin.mapper.OutlesMapper;
import com.pig4cloud.pig.admin.service.*;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
	@Autowired
	InsOutlesUserService insOutlesUserService;

	@Autowired
	private SecurityUtilsService securityUtilsService;

	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private TaskNodeTemplateService taskNodeTemplateService;

	@Autowired
	private OutlesTemplateReService outlesTemplateReService;

	@Autowired
	InsOutlesCourtReService insOutlesCourtReService;

	@Autowired
	OutlesService outlesService;

	@Autowired
	RelationshipAuthenticateService relationshipAuthenticateService;

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
		Institution institution = institutionService.getById(securityUtilsService.getCacheUser().getInsId());

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
				.eq(UserInstitutionStaff::getInsId, securityUtilsService.getCacheUser().getInsId())
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
		outlesDTO.setInsId(securityUtilsService.getCacheUser().getInsId());
		IPage<OutlesVO> outlesVOIPage = this.baseMapper.pageOutles(page, outlesDTO);

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
		if (Objects.nonNull(outlesDTO.getAddress().getAddressId())) {

			// 2.查询网点地址
			Address address = addressService.getByUserId(outles.getOutlesId(), 3);
			BeanUtils.copyProperties(outlesDTO.getAddress(), address);

			// 3.1不为空修改地址
			address.setAddressId(outlesDTO.getAddress().getAddressId());
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
				.eq(UserInstitutionStaff::getInsId, securityUtilsService.getCacheUser().getInsId())
				.eq(UserInstitutionStaff::getDelFlag, 0));
		return this.baseMapper.getOutlesListByStaffId(userInstitutionStaff.getStaffId());
	}

	/**
	 * 通过机构id和网点名称查询所有网点
	 *
	 * @param outlesDTO
	 * @return R
	 */
	@Override
	public IPage<Outles> getInsIdOrOutlesNameList(Page page, OutlesPageDTO outlesDTO) {
		return this.baseMapper.getInsIdOrOutlesNameList(page, outlesDTO);
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


	/********************************************/

	@Override
	public IPage<OutlesPageVO> queryPage(Page page, OutlesPageDTO outlesPageDTO){
		return this.baseMapper.selectPage(page,outlesPageDTO, jurisdictionUtilsService.queryByInsId("PLAT_"),jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	@Override
	public Outles queryByOutlesName(int insId,String outlesName){
		QueryWrapper<Outles> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(Outles::getDelFlag, CommonConstants.STATUS_NORMAL);
		queryWrapper.lambda().eq(Outles::getInsId,insId);
		queryWrapper.lambda().eq(Outles::getOutlesName,outlesName);
		return this.baseMapper.selectOne(queryWrapper);
	}

	@Override
	@Transactional
	public int addOutles(OutlesAddDTO outlesAddDTO){
		int save = 0;
		PigUser pigUser = securityUtilsService.getCacheUser();
		outlesAddDTO.setInsId(pigUser.getInsId());

		Outles outles = new Outles();
		BeanUtils.copyProperties(outlesAddDTO,outles);
		save = this.baseMapper.insert(outles);
		// 判断地址是否为空
		if(Objects.nonNull(outlesAddDTO.getCode()) || Objects.nonNull(outlesAddDTO.getInformationAddress())){
			// 添加地址
			Address address = new Address();
			address.setDelFlag(CommonConstants.STATUS_NORMAL);
			BeanUtils.copyProperties(outlesAddDTO,address);
			// 类型3=网点地址
			address.setType(3);
			address.setUserId(outles.getOutlesId());
			addressService.save(address);
		}
		// 添加网点负责人用户
		InsOutlesUserObjectDTO insOutlesUserAddDTO = new InsOutlesUserObjectDTO();
		insOutlesUserAddDTO.setInsId(outlesAddDTO.getInsId());
		insOutlesUserAddDTO.setOutlesId(outles.getOutlesId());
		insOutlesUserAddDTO.setType(1);
		insOutlesUserAddDTO.setUserList(outlesAddDTO.getUserList());
		insOutlesUserService.addInsOutlesUser(insOutlesUserAddDTO);

		//筛选法院机构id
		List<Integer> courtIds = outlesAddDTO.getCourtAndCourtInsIdVOS().stream().map(CourtAndCourtInsIdVO::getCourtId).collect(Collectors.toList());

		//将法院机构id集合、机构id与网点id传给机构网点法院service添加入驻方法中
		InsOutlesCourtReAddCourtInsIdsDTO insOutlesCourtReAddCourtInsIdsDTO = new InsOutlesCourtReAddCourtInsIdsDTO();
		insOutlesCourtReAddCourtInsIdsDTO.setCourtIds(courtIds);
		insOutlesCourtReAddCourtInsIdsDTO.setInsId(securityUtilsService.getCacheUser().getInsId());
		insOutlesCourtReAddCourtInsIdsDTO.setOutlesId(outles.getOutlesId());
		insOutlesCourtReService.addInsOutlesCourtReByCourtInsIds(insOutlesCourtReAddCourtInsIdsDTO);

		//根据当前
		List<TaskNodeTemplate> taskNodeTemplates = taskNodeTemplateService.queryTemplateType();
		for (TaskNodeTemplate template : taskNodeTemplates) {
			OutlesTemplateRe outlesTemplateRe = new OutlesTemplateRe();
			outlesTemplateRe.setTemplateId(template.getTemplateId());
			outlesTemplateRe.setOutlesId(outles.getOutlesId());
			outlesTemplateReService.save(outlesTemplateRe);
		}
		return save;
	}

	@Override
	@Transactional
	public int modifyOutlesById(OutlesModifyDTO outlesModifyDTO){
		int modify = 0;

		Outles outles = new Outles();
		BeanUtils.copyProperties(outlesModifyDTO,outles);
		modify = this.baseMapper.updateById(outles);
		if(Objects.nonNull(outlesModifyDTO.getCode())){
			// 更新地址
			Address address = new Address();
			BeanUtils.copyProperties(outlesModifyDTO,address);
			address.setUserId(outles.getOutlesId());
			address.setType(3);
			addressService.saveOrUpdate(address);
		}

		return modify;
	}

	@Override
	public OutlesDetailsVO queryById(Integer outlesId){
		OutlesDetailsVO outlesDetailsVO = this.baseMapper.selectDetailsById(outlesId);
		List<InsOutlesUserListVO> userList = insOutlesUserService.queryUserList(1,0, outlesId);
		outlesDetailsVO.setUserList(userList);
		return outlesDetailsVO;
	}

	@Override
	public List<Outles> queryByUserIdList(Integer insId,Integer userId){
		return this.baseMapper.selectByUserId(userId,insId);
	}

	@Override
	@Transactional
	public Integer addDefaultOutles(Outles outles){
		outles.setCanDefault(1);
		outles.setOutlesName("默认网点");
		return this.baseMapper.insert(outles);
	}

	@Override
	public Outles queryByOutlesId(Integer outlesId){
		return this.baseMapper.selectById(outlesId);
	}

	@Override
	public List<OrganizationQueryVO> queryOutlesIdSelect(OutlesSelectDTO outlesSelectDTO){
		Integer type = outlesSelectDTO.getType();
		List<OrganizationQueryVO> organizationQueryVOS = new ArrayList<>();
		PigUser cacheUser = securityUtilsService.getCacheUser();
		if(type == 1){
			organizationQueryVOS = this.baseMapper.pageCooperateByOutlesId(outlesSelectDTO,jurisdictionUtilsService.queryByInsId("PLAT_"));
		}else if(type == 2){
			organizationQueryVOS = this.baseMapper.querySelectByOutlesId(outlesSelectDTO,jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		}
		return organizationQueryVOS;
	}

	@Override
	public List<Outles> pageOutlesList(Integer insId, String outlesName, List<Integer> outlesIds) {
		return this.baseMapper.pageOutlesList(insId, outlesName, outlesIds);
	}

	/**
	 * 根据项目机构id和选中机构id查询合作网点
	 * @param projectOutlesSelectDTO
	 * @return
	 */
	@Override
	public List<OrganizationQueryVO> queryProjectOutlesSelect(ProjectOutlesSelectDTO projectOutlesSelectDTO) {
		return this.baseMapper.queryProjectOutlesSelect(projectOutlesSelectDTO);
	}

	@Override
	@Transactional
	public Integer deleteOutlesById(Integer outlesId){
		QueryWrapper<InsOutlesUser> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(InsOutlesUser::getOutlesId,outlesId);
		queryWrapper.lambda().eq(InsOutlesUser::getDelFlag,CommonConstants.STATUS_NORMAL);
		// 查询网点下所有用户
		List<InsOutlesUser> insOutlesUserList = insOutlesUserService.list(queryWrapper);
		insOutlesUserList.stream().forEach(item->{
			QueryWrapper<InsOutlesUser> userQueryWrapper = new QueryWrapper<>();
			userQueryWrapper.lambda().eq(InsOutlesUser::getUserId,item.getUserId());
			userQueryWrapper.lambda().eq(InsOutlesUser::getDelFlag,CommonConstants.STATUS_NORMAL);
			userQueryWrapper.lambda().ne(InsOutlesUser::getOutlesId,outlesId);
			// 查询除删除以外的网点没有所属机构和网点外
			List<InsOutlesUser> userList = insOutlesUserService.list(userQueryWrapper);
			if(userList.size()==0){
				UpdateWrapper<SysUser> userUpdateWrapper = new UpdateWrapper<>();
				userUpdateWrapper.lambda().eq(SysUser::getUserId,item.getUserId());
				userUpdateWrapper.lambda().set(SysUser::getDelFlag,CommonConstants.STATUS_DEL);
				// 修改用户状态为删除
				sysUserService.update(userUpdateWrapper);
			}
		});

		UpdateWrapper<InsOutlesUser> userUpdateWrapper = new UpdateWrapper<>();
		userUpdateWrapper.lambda().eq(InsOutlesUser::getOutlesId,outlesId);
		userUpdateWrapper.lambda().set(InsOutlesUser::getDelFlag,CommonConstants.STATUS_DEL);
		// 修改机构网点用户关联变状态为删除
		insOutlesUserService.update(userUpdateWrapper);
		// 修改网点状态为删除
		UpdateWrapper<Outles> outles = new UpdateWrapper();
		outles.lambda().eq(Outles::getOutlesId,outlesId);
		outles.lambda().set(Outles::getDelFlag,CommonConstants.STATUS_DEL);
		this.update(outles);
		return 1;
	}

	/**
	 * 查询还没入驻的合作法院相关部门
	 * @param insId
	 * @param courtInsId
	 * @param outlesName
	 * @return
	 */
	@Override
	public List<Outles> queryCooperativeCourt(Integer insId, Integer courtInsId, String outlesName) {
		RelationshipAuthenticate relationshipAuthenticate = relationshipAuthenticateService.getOne(new LambdaQueryWrapper<RelationshipAuthenticate>()
				.eq(RelationshipAuthenticate::getAuthenticateId, courtInsId));

		List<Integer> outlesIds = insOutlesCourtReService.queryInsOutlesCourtReByInsIdAndCourtId(insId, relationshipAuthenticate.getAuthenticateGoalId())
				.stream().map(InsOutlesCourtReVO::getOutlesId).collect(Collectors.toList());

		return outlesService.pageOutlesList(insId, outlesName, outlesIds);
	}
}
