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

package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.CaseeGetListDTO;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.vo.CaseeBizLiquiVO;
import com.pig4cloud.pig.casee.vo.CaseeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 案件表
 *
 * @author yy
 * @date 2022-01-10 14:51:59
 */
@Mapper
public interface CaseeMapper extends BaseMapper<Casee> {
	/**
	 * 查询案件列表
	 */
	List<CaseeVO> selectCaseeList(@Param("query") CaseeGetListDTO caseeGetListDTO);

	List<CaseeVO> selectByPaifuProjectId(@Param("projectId") Integer projectId);

	CaseeVO selectByCaseeId(@Param("caseeId") Integer caseeId);
}
