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
import com.pig4cloud.pig.admin.api.dto.*;
import com.pig4cloud.pig.admin.api.entity.InsOutlesCourtRe;
import com.pig4cloud.pig.admin.api.vo.InsOutlesCourtReVO;
import com.pig4cloud.pig.admin.api.vo.OrganizationQueryVO;

import java.util.List;

/**
 * 机构网点法院关联表
 *
 * @author yuanduo
 * @date 2022-04-28 19:56:42
 */
public interface InsOutlesCourtReService extends IService<InsOutlesCourtRe> {

	/**
	 * 分页查询机构网点法院关联
	 * @param page
	 * @return
	 */
	IPage<InsOutlesCourtReVO> queryInsOutlesCourtPage(Page page, InsOutlesCourtRePageDTO insOutlesCourtRePageDTO);

	/**
	 * 根据机构id与法院id查询机构网点法院关联表信息
	 * @param insId
	 * @param courtId
	 * @return
	 */
	List<InsOutlesCourtReVO> queryInsOutlesCourtReByInsIdAndCourtId(Integer insId, Integer courtId);

	/**
	 * 将入驻网点id集合入驻到法院机构绑定的法院
	 * @param insOutlesCourtReAddOutlesIdsDTO
	 * @return
	 */
	int addInsOutlesCourtReByOutlesIds(InsOutlesCourtReAddOutlesIdsDTO insOutlesCourtReAddOutlesIdsDTO);

	/**
	 * 机构网点关联法院选择
	 * @param insOutlesCourtReSelectDTO
	 * @return
	 */
	List<OrganizationQueryVO> getCourtList(InsOutlesCourtReSelectDTO insOutlesCourtReSelectDTO);

	/**
	 * 将入驻法院id集合入驻到绑定的相关网点
	 * @param insOutlesCourtReAddCourtInsIdsDTO
	 * @return
	 */
	int addInsOutlesCourtReByCourtInsIds(InsOutlesCourtReAddCourtInsIdsDTO insOutlesCourtReAddCourtInsIdsDTO);

	/**
	 * 根据特定条件查询机构网点法院关联数据
	 * @param insOutlesCourtReQueryDTO
	 * @return
	 */
	List<InsOutlesCourtReVO> queryByInsOutlesCourtReQueryDTO(InsOutlesCourtReQueryDTO insOutlesCourtReQueryDTO);

	/**
	 * 根据登录权限查询机构网点法院关联信息
	 * @return
	 */
	InsOutlesCourtRe getInsOutlesCourtRe();

}
