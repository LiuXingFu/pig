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
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserAddOutlesListDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserObjectDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserByOutlesDTO;
import com.pig4cloud.pig.admin.api.entity.InsOutlesUser;
import com.pig4cloud.pig.admin.api.vo.InsOutlesUserInsOutlesVO;
import com.pig4cloud.pig.admin.api.vo.InsOutlesUserListVO;

import java.util.List;

/**
 * 机构/网点用户关联表
 *
 * @author xls
 * @date 2022-01-27 19:30:49
 */
public interface InsOutlesUserService extends IService<InsOutlesUser> {

	int addInsOutlesUser(InsOutlesUserObjectDTO insOutlesUserAddDTO);

	/**
	 * 移除负责人
	 * @param insOutlesUserId
	 * @return
	 */
	int removeInsOutlesUser(Integer insOutlesUserId);

	/**
	 * 根据机构id或网点id查询员工集合
	 * @param insId
	 * @param outlesId
	 * @return
	 */
	List<InsOutlesUserListVO> queryUserList(Integer type,Integer insId, Integer outlesId);

	/**
	 * 根据用户id查询员工权限、网点名称
	 * @param userId
	 * @return
	 */
	List<InsOutlesUserInsOutlesVO> queryOutlesName(Integer userId,Integer insId);

	/**
	 * 根据用户id查询员工权限、机构名称
	 * @param userId
	 * @return
	 */
	List<InsOutlesUserInsOutlesVO> queryInsName(Integer userId);


	IPage<InsOutlesUserInsOutlesVO> queryInsOutlesUserByOutles(Page page, InsOutlesUserByOutlesDTO insOutlesUserByOutlesDTO);

	int addInsOutlesUserByOutlesId(InsOutlesUserAddOutlesListDTO insOutlesUserAddOutlesListDTO);

	InsOutlesUserInsOutlesVO queryById(Integer insOutlesUserId);

	int updateInsOutlesUser(InsOutlesUser insOutlesUser);
}
