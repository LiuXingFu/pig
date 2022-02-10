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
import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.api.vo.*;

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
	IPage<InstitutionAssociatePageVO> getByInsName(Page page, String insName);

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

	boolean getInstitutionIsInsName(String insName);

	/**
	 * 分页查询
	 * @param page
	 * @param institutionPageDTO
	 * @return
	 */
	IPage<InstitutionPageVO> queryPage(Page page, InstitutionPageDTO institutionPageDTO);

	/**
	 * 添加机构及机构负责人
	 * @param institutionAddDTO
	 * @return
	 */
	int addInstitution(InstitutionAddDTO institutionAddDTO);

	/**
	 * 根据机构id修改机构信息
	 * @param institutionModifyDTO
	 * @return
	 */
	int modifyInstitutionById(InstitutionModifyDTO institutionModifyDTO);

	/**
	 * 根据机构id查询机构详情
	 * @param insId
	 * @return
	 */
	InstitutionDetailsVO queryById(Integer insId);

	/**
	 * 根据用户id查询相关机构
	 * @param userId
	 * @return
	 */
	List<Institution> queryByUserIdList(Integer userId);

	Institution queryByInsId(Integer insId);

	/**
	 * 查询当前登录用户机构、网点、角色信息
	 * @param insId
	 * @param outlesId
	 * @return
	 */
	ReselectInfoVO queryReselectInfo(Integer insId,Integer outlesId);

	/**
	 * 查询机构下拉框组件集合
	 * @param insOulesSelectDTO
	 * @return
	 */
	List<OrganizationQueryVO> queryInsSelect(InstitutionSelectDTO insOulesSelectDTO);

}
