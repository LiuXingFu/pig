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
package com.pig4cloud.pig.casee.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.CaseePerformanceDTO;
import com.pig4cloud.pig.casee.dto.CaseePerformanceSumDTO;
import com.pig4cloud.pig.casee.entity.CaseePerformance;
import com.pig4cloud.pig.casee.mapper.CaseePerformanceMapper;
import com.pig4cloud.pig.casee.service.CaseePerformanceService;
import com.pig4cloud.pig.casee.vo.CaseePerformanceSumVO;
import com.pig4cloud.pig.casee.vo.CaseePerformanceVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * 案件绩效表
 *
 * @author xiaojun
 * @date 2021-09-07 17:44:19
 */
@Service
public class CaseePerformanceServiceImpl extends ServiceImpl<CaseePerformanceMapper, CaseePerformance> implements CaseePerformanceService {
	@Override
	public List<CaseePerformanceVO> getPerformanceByTargetId(Integer targetId){
		return this.baseMapper.selectPerformanceByTargetId(targetId);
	}
	@Override
	public IPage<CaseePerformanceVO> getPerformanceList(Page page, CaseePerformanceDTO caseePerformanceDTO){
		return  this.baseMapper.selectPerformanceList(page,caseePerformanceDTO);
	}

	/**
	 * 根据用户查询所有绩效(按日期分组汇总)
	 */
	public IPage<CaseePerformanceSumVO> getPerformanceSumList(Page page, @Param("query") CaseePerformanceSumDTO caseePerformanceDTO){
		IPage<CaseePerformanceSumVO> caseePerformanceSumVOs = this.baseMapper.selectPerformanceSumList(page,caseePerformanceDTO);
		System.out.println(caseePerformanceSumVOs);
		return  caseePerformanceSumVOs;
	}

	/**
	 * 根据用户条件查询绩效合计
	 */
	public BigDecimal getPerformanceSum(@Param("query") CaseePerformanceSumDTO caseePerformanceDTO){
		return  this.baseMapper.selectPerformanceSum(caseePerformanceDTO);
	}

	/**
	 * 根据绩效id获取绩效详情
	 * @param perfId
	 * @return
	 */
	public CaseePerformanceVO getPerformanceById(String perfId){
		return this.baseMapper.selectPerformanceById(perfId);
	}
}

