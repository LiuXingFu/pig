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
import com.pig4cloud.pig.casee.dto.TargetPageDTO;
import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.entity.TargetBizLiqui;
import com.pig4cloud.pig.casee.vo.TargetByIdVO;
import com.pig4cloud.pig.casee.vo.TargetPageVO;
import com.pig4cloud.pig.casee.vo.TargetThingByIdVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 标的表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@Mapper
public interface TargetBizLiquiMapper extends BaseMapper<TargetBizLiqui> {

	/**
	 * 根据标的id查询标的物详情
	 * @param targetId
	 * @return
	 */
	TargetThingByIdVO selectTargetThingById(@Param("targetId")Integer targetId);

	/**
	 * 根据标的id查询标的详情
	 * @param targetId
	 * @return
	 */
	TargetByIdVO selectTargetById(@Param("targetId")Integer targetId);

}
