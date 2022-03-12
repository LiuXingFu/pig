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
import com.pig4cloud.pig.admin.api.dto.AssociateOutlesReDTO;
import com.pig4cloud.pig.admin.api.entity.AssociateOutlesRe;
import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.api.vo.AssociateOutlesRePageVO;
import com.pig4cloud.pig.admin.api.vo.AssociateOutlesReVO;

import java.util.List;

/**
 * 机构关联网点表
 *
 * @author yuanduo
 * @date 2021-09-03 11:09:36
 */
public interface AssociateOutlesReService extends IService<AssociateOutlesRe> {

	/**
	 * 分页查询已授权的网点信息
	 * @param page
	 * @param associateOutlesRe
	 * @return
	 */
	IPage<AssociateOutlesRe> pageAssociateOutles(Page page, AssociateOutlesRe associateOutlesRe);

	/**
	 * 根据associateID查询授权列表
	 * @param associateId
	 * @Patam outlesName
	 * @return
	 */
	List<Outles> getAuthorizationPage(Integer associateId, String outlesName) throws Exception;

	/**
	 * 新增机构授权网点
	 * @param associateOutlesDTO 机构关联网点表
	 * @return R
	 */
	boolean saveAssociateOutles(AssociateOutlesReDTO associateOutlesDTO) throws Exception;

	/**
	 * 解除网点授权关系
	 * @param associateOutlesId
	 * @return R
	 */
	boolean dismissById(Integer associateOutlesId);

	IPage<AssociateOutlesRePageVO> queryCooperateOutlesPage(Page page, AssociateOutlesRe associateOutlesRe);
}
