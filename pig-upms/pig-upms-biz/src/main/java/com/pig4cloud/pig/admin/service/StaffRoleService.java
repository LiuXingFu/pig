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
import com.pig4cloud.pig.admin.api.entity.StaffRole;
import com.pig4cloud.pig.admin.api.entity.SysRole;

import java.util.Collection;
import java.util.List;

/**
 * 员工角色表
 *
 * @author yuanduo
 * @date 2021-09-08 14:20:07
 */
public interface StaffRoleService extends IService<StaffRole> {

	/**
	 * 根据员工id，查询角色信息
	 * @param userInsId
	 * @return
	 */
	List<SysRole> findRolesByUserId(Integer userInsId);
}
