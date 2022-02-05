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
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserAddDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesUserModifyDTO;
import com.pig4cloud.pig.admin.api.entity.InsOutlesUser;
import com.pig4cloud.pig.admin.api.vo.InsOutlesUserListVO;
import com.pig4cloud.pig.common.core.util.R;

import java.util.List;

/**
 * 机构/网点用户关联表
 *
 * @author xls
 * @date 2022-01-27 19:30:49
 */
public interface InsOutlesUserService extends IService<InsOutlesUser> {

	int addInsOutlesUser(InsOutlesUserAddDTO insOutlesUserAddDTO);

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

	int updateInsOutlesUser(InsOutlesUserModifyDTO insOutlesUserModifyDTO);

	int removeInsOutlesUserList(List<Integer> insOutlesUserIds);
}
