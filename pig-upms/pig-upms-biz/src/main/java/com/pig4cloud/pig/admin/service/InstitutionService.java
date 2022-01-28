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
import com.pig4cloud.pig.admin.api.dto.InstitutionAddDTO;
import com.pig4cloud.pig.admin.api.dto.InstitutionDTO;
import com.pig4cloud.pig.admin.api.dto.InstitutionPageDTO;
import com.pig4cloud.pig.admin.api.dto.OrganizationQueryDTO;
import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.api.vo.InstitutionPageVO;
import com.pig4cloud.pig.admin.api.vo.InstitutionVO;
import com.pig4cloud.pig.admin.api.vo.OrganizationQueryVO;

import java.util.List;

/**
 * 
 *
 * @author xiaojun
 * @date 2021-08-17 16:22:59
 */
public interface InstitutionService extends IService<Institution> {

	/**
	 * 新增机构
	 *
	 * @param institutionDTO
	 * @return
	 * @throws Exception
	 */
	int saveInstitution(InstitutionDTO institutionDTO) throws Exception;

	/**
	 * 修改机构
	 *
	 * @param institutionDTO
	 * @return
	 * @throws Exception
	 */
	Boolean updateInstitution(InstitutionDTO institutionDTO) throws Exception;

	/**
	 * 根据机构insName模糊搜索合作机构
	 * @param insName
	 * @return
	 */
	List<InstitutionVO> getByInsName(String insName);

	/**
	 * 分页查询机构信息
	 * @param page 分页对象
	 * @param institution 参数列表
	 * @return 分页对象
	 */
	IPage<InstitutionVO> getInstitutionPage(Page page, Institution institution);

	/**
	 * 根据机构id查询机构信息
	 * @param insId
	 * @return
	 */
	InstitutionVO getInstitutionById(Integer insId);

	/**
	 * 通过id禁用机构
	 * @param insId id
	 * @return R
	 */
	boolean disableById(Integer insId);

	List<OrganizationQueryVO> queryCurrentInstitution(OrganizationQueryDTO organizationQueryDTO);

	List<OrganizationQueryVO> queryAssociatedInstitutions(OrganizationQueryDTO organizationQueryDTO);

	boolean getInstitutionAlias();

	/***********************************************************/

	IPage<InstitutionPageVO> queryPage(Page page, InstitutionPageDTO institutionPageDTO);

	int addInstitution(InstitutionAddDTO institutionAddDTO);


	boolean getInstitutionIsInsName(String insName);
}
