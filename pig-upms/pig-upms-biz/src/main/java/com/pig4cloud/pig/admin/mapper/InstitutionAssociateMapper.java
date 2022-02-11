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
import com.pig4cloud.pig.admin.api.entity.InstitutionAssociate;
import com.pig4cloud.pig.admin.api.vo.InstitutionAssociateDetailsVO;
import com.pig4cloud.pig.admin.api.vo.InstitutionAssociatePageVO;
import com.pig4cloud.pig.admin.api.vo.InstitutionAssociateVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 机构关联表
 *
 * @author yuanduo
 * @date 2021-09-03 11:01:07
 */
@Mapper
public interface InstitutionAssociateMapper extends BaseMapper<InstitutionAssociate> {

	IPage<InstitutionAssociatePageVO> pageInstitutionAssociate(Page page, @Param("query") InstitutionAssociate institutionAssociate);

	InstitutionAssociateDetailsVO queryById(Integer associateId);

}
