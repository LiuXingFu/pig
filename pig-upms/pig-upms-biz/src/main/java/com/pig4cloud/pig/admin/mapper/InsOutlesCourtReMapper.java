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
import com.pig4cloud.pig.admin.api.dto.InsOutlesCourtRePageDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesCourtReQueryDTO;
import com.pig4cloud.pig.admin.api.dto.InsOutlesCourtReSelectDTO;
import com.pig4cloud.pig.admin.api.entity.InsOutlesCourtRe;
import com.pig4cloud.pig.admin.api.vo.InsOutlesCourtReVO;
import com.pig4cloud.pig.admin.api.vo.OrganizationQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 机构网点法院关联表
 *
 * @author yuanduo
 * @date 2022-04-28 19:56:42
 */
@Mapper
public interface InsOutlesCourtReMapper extends BaseMapper<InsOutlesCourtRe> {

	IPage<InsOutlesCourtReVO> queryInsOutlesCourtPage(Page page, @Param("query") InsOutlesCourtRePageDTO insOutlesCourtRePageDTO);

	List<InsOutlesCourtReVO> queryInsOutlesCourtReByInsIdAndCourtId(@Param("insId") Integer insId, @Param("courtId") Integer courtId);

	List<OrganizationQueryVO> selectCourtList(@Param("query") InsOutlesCourtReSelectDTO insOutlesCourtReSelectDTO);

	List<InsOutlesCourtReVO> queryByInsOutlesCourtReQueryDTO(@Param("query") InsOutlesCourtReQueryDTO insOutlesCourtReQueryDTO);
}
