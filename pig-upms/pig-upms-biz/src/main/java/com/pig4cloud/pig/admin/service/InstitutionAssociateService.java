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
import com.pig4cloud.pig.admin.api.dto.InstitutionAssociatePageDTO;
import com.pig4cloud.pig.admin.api.entity.Court;
import com.pig4cloud.pig.admin.api.entity.InstitutionAssociate;
import com.pig4cloud.pig.admin.api.vo.CourtAndCourtInsIdVO;
import com.pig4cloud.pig.admin.api.vo.InstitutionAssociateDetailsVO;
import com.pig4cloud.pig.admin.api.vo.InstitutionAssociatePageVO;
import com.pig4cloud.pig.admin.api.vo.InstitutionAssociateVO;

import java.util.List;

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
	 * @param institutionAssociatePageDTO
	 * @return
	 */
	IPage<InstitutionAssociatePageVO> pageInstitutionAssociate(Page page, InstitutionAssociatePageDTO institutionAssociatePageDTO);

	/**
	 * 根据机构关联id查询相关信息
	 * @param associateId
	 * @return
	 */
	InstitutionAssociateDetailsVO queryById(Integer associateId);

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

	/**
	 * 查询合作法院
	 * @return
	 */
	List<CourtAndCourtInsIdVO> getAssociateCourt(String courtName);

	List<CourtAndCourtInsIdVO> getAssociateCourtByInsIdAndOutlesId(Integer insId, Integer outlesId, String courtName);
}
