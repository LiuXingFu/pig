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
import com.pig4cloud.pig.casee.dto.AssetsReLiquiFlowChartPageDTO;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.assets.AssetsReLiqui;
import com.pig4cloud.pig.casee.vo.AssetsReLiquiDetailsVO;
import com.pig4cloud.pig.casee.vo.AssetsReLiquiFlowChartPageVO;
import com.pig4cloud.pig.casee.vo.AssetsReLiquiMortgageVO;
import com.pig4cloud.pig.casee.vo.PropertyCategoryTotalVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
@Mapper
public interface AssetsReLiquiMapper extends BaseMapper<AssetsRe> {

	AssetsReLiqui getAssetsReCasee(@Param("query") AssetsRe assetsRe);

	IPage<AssetsReLiquiFlowChartPageVO> selectAssetsNotSeizeAndFreeze(Page page, @Param("query") AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsReLiquiFlowChartPageVO> selectBusinessTransfer(Page page, @Param("query") AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsReLiquiFlowChartPageVO> selectFundDeduction(Page page, @Param("query") AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsReLiquiFlowChartPageVO> selectPropertyToBeAuctioned(Page page, @Param("query") AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsReLiquiFlowChartPageVO> selectPropertyFlowChartPage(Page page, @Param("query") AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	Long queryComparePropertyNumbersCount(@Param("insId") Integer insId, @Param("outlesId") Integer outlesId);

	List<PropertyCategoryTotalVO> queryPropertyCategoryTotalList(@Param("insId") Integer insId, @Param("outlesId") Integer outlesId);

	Long queryTotalProperty(@Param("insId") Integer insId, @Param("outlesId") Integer outlesId);

	IPage<AssetsReLiquiFlowChartPageVO> selectPropertyAuctionAnnouncementPeriod(Page page, @Param("query") AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	AssetsReLiquiDetailsVO selectAssetsReDetails(@Param("assetsReId") Integer assetsReId);

	IPage<AssetsReLiquiFlowChartPageVO> selectDispositionRuling(Page page, @Param("query") AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsReLiquiFlowChartPageVO> selectPropertyAuctionDue(Page page, @Param("query") AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsReLiquiFlowChartPageVO> selectPropertyAuctionSuccess(Page page, @Param("query") AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsReLiquiFlowChartPageVO> selectPropertyAuctionFailed(Page page, @Param("query") AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	IPage<AssetsReLiquiFlowChartPageVO> selectPropertyAuctionAbnormal(Page page, @Param("query") AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	List<AssetsReLiquiMortgageVO> selectAssetsReAddress(@Param("projectId")Integer projectId);

}
