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

package com.pig4cloud.pig.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.dto.StaffRelationshipAuthenticateDTO;
import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.api.dto.UserInstitutionStaffDTO;
import com.pig4cloud.pig.admin.api.dto.UserOutlesStaffReDTO;
import com.pig4cloud.pig.admin.api.entity.UserInstitutionStaff;
import com.pig4cloud.pig.admin.api.vo.UserInsStaffVO;
import com.pig4cloud.pig.admin.api.vo.UserInstitutionStaffVO;

import java.util.List;

/**
 * 员工表
 *
 * @author yuanduo
 * @date 2021-09-06 15:20:47
 */
public interface UserInstitutionStaffService extends IService<UserInstitutionStaff> {

	/**
	 * 新增员工
	 * @param userInstitutionStaffDTO
	 * @return
	 */
	int saveUserInstitutionStaff(UserInstitutionStaffDTO userInstitutionStaffDTO) throws Exception;

	/**
	 * 配置角色
	 * @param dept
	 * @param userInstitutionStaff
	 */
	void setStaffDept(List<Integer> dept, UserInstitutionStaff userInstitutionStaff);

	/**
	 * 配置角色
	 * @param userDTO
	 * @param userInstitutionStaff
	 */
	void setStaffRole(UserDTO userDTO, UserInstitutionStaff userInstitutionStaff);

	/**
	 * 分页查询员工信息
	 * @param page
	 * @param userInstitutionStaff
	 * @return
	 */
	IPage<UserInstitutionStaffVO> pageUserInstitutionStaff(Page page, UserInstitutionStaff userInstitutionStaff);

	UserInstitutionStaffVO getByIdUserInstitutionStaff(Integer staffId);

	UserInsStaffVO getByIdStaff(Integer staffId);

	/**
	 * 分页查询
	 * 根据网点id查询员工集合
	 * @return
	 */
	IPage<UserInstitutionStaffVO> getOutlesIdByStaffList(Page page,Integer outlesId,Integer userType,String nickName);

	/**
	 * 分页查询
	 * 根据网点id查询未关联网点与员工集合
	 * @return
	 */
	IPage<UserInstitutionStaffVO> pageNotOutlesStaff(Page page,Integer outlesId);

	boolean updateUserInstitutionStaff(UserInstitutionStaffDTO userInstitutionStaffDTO);

	boolean resignStaff(Integer staffId);

	boolean saveUserOutlesStaffRe(UserOutlesStaffReDTO userOutlesStaffRe);

	boolean delUserOutlesStaffRe(UserOutlesStaffReDTO userOutlesStaffRe);

	void addUserOutlesStaffRe(UserInstitutionStaffDTO userOutlesStaffRe, UserInstitutionStaff userInstitutionStaff);

	List<Integer> getStaffOutlesList();

	IPage<UserInstitutionStaff> getStaffsByAuthenticateGoalId(Page page, StaffRelationshipAuthenticateDTO staffRelationshipAuthenticateDTO);
}
