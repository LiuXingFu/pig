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

package com.pig4cloud.pig.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.InstitutionPageDTO;
import com.pig4cloud.pig.admin.api.dto.InstitutionSelectDTO;
import com.pig4cloud.pig.admin.api.dto.ProjectInstitutionSelectDTO;
import com.pig4cloud.pig.admin.api.dto.OrganizationQueryDTO;
import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.api.vo.*;
import com.pig4cloud.pig.admin.api.vo.InstitutionPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 *
 * @author xiaojun
 * @date 2021-08-17 16:22:59
 */
@Mapper
public interface InstitutionMapper extends BaseMapper<Institution> {

	IPage<InstitutionAssociatePageVO> getByInsName(Page page, @Param("insName") String insName, @Param("insId") Integer insId);

	IPage<InstitutionVO> getInstitutionVosPage(Page page, @Param("query") Institution institution);

	InstitutionVO getInstitutionById(Integer insId);

	List<OrganizationQueryVO> queryCurrentInstitution(@Param("query") OrganizationQueryDTO organizationQueryDTO, @Param("roleType") Integer roleType);

	List<OrganizationQueryVO> queryAssociatedInstitutions(@Param("query") OrganizationQueryDTO organizationQueryDTO, @Param("roleType") Integer roleType);

	/***********************************************************/

	IPage<InstitutionPageVO> selectPage(Page page, @Param("query") InstitutionPageDTO institutionPageDTO);

	Integer getInstitutionIsInsName(String insName);

	InstitutionDetailsVO selectDetailsById( @Param("insId") int insId);

	List<Institution> selectByUserId( @Param("userId")int userId);

	List<OrganizationQueryVO> querySelectByInsId(@Param("insId") int insId);

	List<OrganizationQueryVO> pageCooperateByInsId(@Param("query")InstitutionSelectDTO insOulesSelectDTO,@Param("insId") int insId);

	List<OrganizationQueryVO> queryProjectInsSelect(@Param("query") ProjectInstitutionSelectDTO projectInstitutionSelectDTO);

}
