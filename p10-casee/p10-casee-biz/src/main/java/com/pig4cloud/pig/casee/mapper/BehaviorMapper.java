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
import com.pig4cloud.pig.casee.dto.SaveBehaviorDTO;
import com.pig4cloud.pig.casee.entity.Behavior;
import com.pig4cloud.pig.casee.entity.liquientity.BehaviorLiqui;
import com.pig4cloud.pig.casee.vo.BehaviorOrCaseeVO;
import com.pig4cloud.pig.casee.vo.BehaviorOrProjectOrCasee;
import com.pig4cloud.pig.casee.vo.BehaviorOrProjectPageVO;
import com.pig4cloud.pig.common.core.util.KeyValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 行为表
 *
 * @author yuanduo
 * @date 2022-02-14 15:51:27
 */
@Mapper
public interface BehaviorMapper extends BaseMapper<Behavior> {

	List<BehaviorOrCaseeVO> getBehaviorByProjectId(Integer projectId);

	IPage<BehaviorOrProjectPageVO> queryPageBehaviorOrProject(Page page, @Param("subjectId") Integer subjectId, @Param("insId") Integer insId, @Param("outlesId") Integer outlesId);

	BehaviorOrProjectOrCasee queryById(Integer behaviorId);

	List<Behavior> selectBySubjectId(@Param("projectId") Integer projectId,@Param("caseeId") Integer caseeId,@Param("subjectId") Integer subjectId);

	IPage<BehaviorOrProjectPageVO> queryPageByCaseeId(Page page, @Param("caseeId") Integer caseeId);

	BehaviorLiqui getBehaviorLiqui(@Param("query") Behavior behavior);
}
