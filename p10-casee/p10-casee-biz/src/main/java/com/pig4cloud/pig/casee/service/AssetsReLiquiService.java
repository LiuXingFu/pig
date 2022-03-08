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

package com.pig4cloud.pig.casee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.AssetsAddDTO;
import com.pig4cloud.pig.casee.dto.AssetsReLiquiFlowChartPageDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.assets.AssetsReCasee;
import com.pig4cloud.pig.casee.vo.AssetsReLiquiFlowChartPageVO;

/**
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
public interface AssetsReLiquiService extends IService<AssetsRe> {

	/**
	 * 添加案件财产
	 * @param assetsAddDTO
	 * @return
	 */
	Integer saveAssetsCasee(AssetsAddDTO assetsAddDTO)throws Exception;

	/**
	 * 根据条件查询案件财产表数据
	 * @param assetsRe
	 * @return
	 */
	AssetsReCasee getAssetsReCasee(AssetsRe assetsRe);

	/**
	 * 分页查询案件财产查封冻结情况
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryAssetsNotSeizeAndFreeze(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);

	/**
	 * 有抵押权轮封未商请移送
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryBusinessTransfer(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);

	/**
	 * 首冻资金未划扣
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryFundDeduction(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);


}
