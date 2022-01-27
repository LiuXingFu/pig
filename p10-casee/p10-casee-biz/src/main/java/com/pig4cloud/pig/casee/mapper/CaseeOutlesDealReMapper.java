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
import com.pig4cloud.pig.casee.entity.CaseeOutlesDealRe;
import com.pig4cloud.pig.casee.vo.CaseeOutlesDealReVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 案件机构关联表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@Mapper
public interface CaseeOutlesDealReMapper extends BaseMapper<CaseeOutlesDealRe> {

	/**
	 * 根据案件id、类型查询案件机构关联表
	 * @param caseeId
	 * @param type
	 * @return
	 */
	List<CaseeOutlesDealReVo> queryCaseeOutlesDealRe(@Param("caseeId") Integer caseeId,@Param("insId") Integer insId,@Param("outlesId") Integer outlesId,@Param("type") Integer type);
}
