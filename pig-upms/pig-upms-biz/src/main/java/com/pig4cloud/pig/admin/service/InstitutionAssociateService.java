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
import com.pig4cloud.pig.admin.api.dto.CertificationRelationshipDTO;
import com.pig4cloud.pig.admin.api.entity.InstitutionAssociate;
import com.pig4cloud.pig.admin.api.vo.InstitutionAssociatePageVO;
import com.pig4cloud.pig.admin.api.vo.InstitutionAssociateVO;

/**
 * 机构关联表
 *
 * @author yuanduo
 * @date 2021-09-03 11:01:07
 */
public interface InstitutionAssociateService extends IService<InstitutionAssociate> {

	/**
	 *	关联机构
	 * @param institutionAssociate
	 * @return
	 */
	int saveInstitutionAssociate(InstitutionAssociate institutionAssociate) throws Exception;

	/**
	 * 根据机构ID分页查询合作机构
	 * @param page
	 * @param institutionAssociate
	 * @return
	 */
	IPage<InstitutionAssociatePageVO> pageInstitutionAssociate(Page page, InstitutionAssociate institutionAssociate);

	/**
	 * 根据机构关联id查询相关信息
	 * @param associateId
	 * @return
	 */
	InstitutionAssociateVO queryById(Integer associateId);

	/**
	 * 通过id解除关联
	 * @param associateId id
	 * @return R
	 */
	boolean dismissById(Integer associateId);

	/**
	 * 认证关联关系
	 * @param certificationRelationshipDTO
	 * @return R
	 */
	Integer certificationRelationship(CertificationRelationshipDTO certificationRelationshipDTO);
}
