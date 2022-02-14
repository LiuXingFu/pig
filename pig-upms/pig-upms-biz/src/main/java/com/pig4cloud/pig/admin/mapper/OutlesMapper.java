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
import com.pig4cloud.pig.admin.api.dto.OutlesDTO;
import com.pig4cloud.pig.admin.api.dto.OutlesPageDTO;
import com.pig4cloud.pig.admin.api.dto.OutlesQueryDTO;
import com.pig4cloud.pig.admin.api.dto.OutlesSelectDTO;
import com.pig4cloud.pig.admin.api.entity.Institution;
import com.pig4cloud.pig.admin.api.entity.Outles;
import com.pig4cloud.pig.admin.api.vo.OrganizationQueryVO;
import com.pig4cloud.pig.admin.api.vo.OutlesDetailsVO;
import com.pig4cloud.pig.admin.api.vo.OutlesPageVO;
import com.pig4cloud.pig.admin.api.vo.OutlesVO;
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
public interface OutlesMapper extends BaseMapper<Outles> {

	IPage<OutlesVO> pageOutles(Page page, @Param("query") Outles outles);

	OutlesVO getByIdOutles(Integer outlesId);

	List<OutlesVO> batchQueryOutlesId(@Param("outlesIds") List<Integer> outlesIds);

	List<Outles> getOutlesListByUserId(Integer userId);

	List<Outles> getOutlesListByStaffId(Integer staffId);


	IPage<Outles> getInsIdOrOutlesNameList(Page page, @Param("query") OutlesPageDTO outlesDTO);

	List<Integer> listOutlesId(Integer staffId);

	List<Outles> listOutles(Integer staffId);

	/**********************************************************************/
	IPage<OutlesPageVO> selectPage(Page page, @Param("query") OutlesPageDTO outlesPageDTO,@Param("insId") int insId);

	OutlesDetailsVO selectDetailsById( @Param("outlesId") Integer outlesId);

	List<Outles> selectByUserId( @Param("userId")Integer userId,@Param("insId")Integer insId);

	List<OrganizationQueryVO> querySelectByOutlesId(@Param("query")OutlesSelectDTO outlesSelectDTO,@Param("loginOutlesId") Integer outlesId);

	List<OrganizationQueryVO> pageCooperateByOutlesId(@Param("query")OutlesSelectDTO outlesSelectDTO,@Param("loginInsId")Integer loginInsId);

	List<Outles> pageOutlesList(@Param("insId") Integer insId, @Param("outlesName") String outlesName, @Param("outlesIds") List<Integer> outlesIds);
}
