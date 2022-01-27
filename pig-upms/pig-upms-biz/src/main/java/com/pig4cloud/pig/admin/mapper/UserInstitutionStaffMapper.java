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

package com.pig4cloud.pig.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.StaffRelationshipAuthenticateDTO;
import com.pig4cloud.pig.admin.api.entity.UserInstitutionStaff;
import com.pig4cloud.pig.admin.api.vo.UserInsStaffVO;
import com.pig4cloud.pig.admin.api.vo.UserInstitutionStaffVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 员工表
 *
 * @author yuanduo
 * @date 2021-09-06 15:20:47
 */
@Mapper
public interface UserInstitutionStaffMapper extends BaseMapper<UserInstitutionStaff> {

	IPage<UserInstitutionStaffVO> pageUserInstitutionStaff(Page page, @Param("query") UserInstitutionStaff userInstitutionStaff);

	IPage<UserInstitutionStaffVO> pageOutlesStaff(Page page,@Param("idList")List<Integer> idList,@Param("userType") Integer userType,@Param("nickName") String nickName);

	IPage<UserInstitutionStaffVO> pageNotOutlesStaff(Page page, @Param("idList")List<Integer> idList , @Param("insId")Integer insId);

	UserInstitutionStaffVO getByIdUserInstitutionStaff(Integer staffId);

	UserInsStaffVO getByIdStaff(Integer staffId);

	IPage<UserInstitutionStaff> getStaffsByAuthenticateGoalId(Page page, @Param("staffRelationshipAuthenticateDTO") StaffRelationshipAuthenticateDTO staffRelationshipAuthenticateDTO);
}
