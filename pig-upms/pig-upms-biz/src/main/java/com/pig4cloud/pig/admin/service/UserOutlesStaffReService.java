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


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.dto.OrganizationQueryDTO;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.api.entity.UserOutlesStaffRe;
import com.pig4cloud.pig.admin.api.vo.OrganizationQueryVO;

import java.util.List;

/**
 * 用户网点表
 *
 * @author yuanduo
 * @date 2021-09-03 10:52:47
 */
public interface UserOutlesStaffReService extends IService<UserOutlesStaffRe> {

	/**
	 * 查询出自己以为的办理人
	 * @param organizationQueryDTO
	 * @return
	 */
	List<OrganizationQueryVO> queryBranchManager(OrganizationQueryDTO organizationQueryDTO);

	/**
	 * 根据网点id查询该网点下所有用户
	 * @param outlesId
	 * @return
	 */
	List<OrganizationQueryVO> queryByOutlesId(Integer outlesId);
}
