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
import com.pig4cloud.pig.casee.dto.BehaviorLiquiDebtorPageDTO;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.entity.Behavior;
import com.pig4cloud.pig.casee.vo.BehaviorOrCaseeVO;
import com.pig4cloud.pig.casee.vo.BehaviorOrProjectOrCasee;
import com.pig4cloud.pig.casee.vo.BehaviorOrProjectPageVO;
import com.pig4cloud.pig.casee.vo.CaseeLiquiDebtorPageVO;
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
public interface BehaviorLiquiMapper extends BaseMapper<Behavior> {

	IPage<CaseeLiquiDebtorPageVO> selectSanctionApplyNotImplemented(Page page, @Param("query")BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<CaseeLiquiDebtorPageVO> selectLimitNotService(Page page, @Param("query")BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<CaseeLiquiDebtorPageVO> behaviorPaymentCompleted(Page page, @Param("query")BehaviorLiquiDebtorPageDTO behaviorLiquiDebtorPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<BehaviorOrProjectPageVO> queryBehaviorBySubjecrId(Page page, @Param("subjectId")Integer subjectId, @Param("login") InsOutlesDTO insOutlesDTO);
}
