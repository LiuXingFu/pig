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
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.dto.liqui.AssetsReUnravelDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.liquientity.AssetsReLiqui;
import com.pig4cloud.pig.casee.vo.*;

import java.util.List;

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
	 * 根据项目id查询是否有可移交的财产
	 * @param projectId
	 * @return
	 */
	List<AssetsReDTO> getTransferAssetsByProjectId(Integer projectId);
	/**
	 * 根据条件查询案件财产表数据
	 * @param assetsRe
	 * @return
	 */
	AssetsReLiqui getAssetsReCasee(AssetsRe assetsRe);


	/**
	 * 根据项目id、案件id、财产id、查询财产关联债务人数据
	 * @param projectId
	 * @param caseeId
	 * @param assetsId
	 * @return
	 */
	AssetsReSubjectDTO queryAssetsSubject(Integer projectId, Integer caseeId, Integer assetsId);

	/**
	 * 通过项目id、案件id、财产id查询财产抵押权信息
	 * @param projectId
	 * @param caseeId
	 * @param assetsId
	 * @return
	 */
	AssetsReLiqui queryAssetsMortgage(Integer projectId, Integer caseeId, Integer assetsId);

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

	/**
	 * 待拍财产
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyToBeAuctioned(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);

	/**
	 * 分页查询不动产现勘未入户
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryRealEstateSurveyNotRegistered(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);

	/**
	 * 分页查询可处置财产程序节点统计列表
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyFlowChartPage(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);


	/**
	 * 较去年财产数
	 * @return
	 */
	Long queryComparePropertyNumbersCount();

	/**
	 * 财产分类统计集合
	 * @return
	 */
	List<PropertyCategoryTotalVO> queryPropertyCategoryTotalList();

	/**
	 * 财产总数量
	 * @return
	 */
	Long queryTotalProperty();

	/**
	 *财产拍卖公告期
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyAuctionAnnouncementPeriod(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);

	/**
	 * 获取案件财产详情，及项目案件财产详情
	 * @param assetsReId
	 * @return
	 */
	AssetsReLiquiDetailsVO getAssetsReDetails(Integer assetsReId);

	/**
	 * 到款/抵偿未裁定
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryDispositionRuling(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);

	/**
	 * 裁定未送达
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryRulingNotService(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);

	/**
	 * 拍卖到期无结果
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyAuctionDue(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);

	/**
	 * 拍卖成功未处理
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyAuctionSuccess(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);

	/**
	 * 拍卖不成功未处理
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyAuctionFailed(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);


	/**
	 * 拍卖异常未撤销
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryPropertyAuctionAbnormal(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);

	/**
	 * 查询项目抵押财产
	 * @param projectId
	 * @return
	 */
	List<AssetsReLiquiMortgageVO> queryAssetsReAddress(Integer projectId);

	/**
	 * 查询案件财产查封冻结未完成
	 * @param page
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return
	 */
	IPage<AssetsReLiquiFlowChartPageVO> queryCaseeAssetsNotFreeze(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO);

	/**
	 * 查询债务人财产
	 * @param subjectId
	 * @return
	 */
	IPage<AssetsReLiquiSubjectVO> queryAssetsReBySubjectId(Page page,Integer subjectId);

	IPage<AssetsReLiquiProjectVO> queryByAssetsId(Page page,Integer assetsId);

	Integer updateAssetsRe(Integer projectId,Integer newCaseeId);


	/**
	 * 查询可移交财产集合
	 * @param projectId
	 * @return
	 */
	List<AssetsReDTO> queryTransferableAssetsReList(Integer projectId);

	List<AssetsReDTO> getAssetsByProjectId(Integer projectId,Integer caseeId);

	/**
	 * 根据项目id案件id查询财产关联信息和财产基本信息
	 * @param projectId
	 * @param caseeId
	 * @return
	 */
	List<AssetsReDTO> queryAssetsReByProjectId(Integer projectId,Integer caseeId);

	/**
	 * 根据项目id、抵押记录id移除财产id集合以外的数据
	 * @param projectId
	 * @param mortgageAssetsRecordsId
	 * @param assetsIdList
	 * @return
	 */
	Integer removeNotInAssetsId(Integer projectId,Integer mortgageAssetsRecordsId,List<Integer> assetsIdList);

	/**
	 * 财产解封/解冻
	 * @param assetsReUnravelDTO
	 * @return
	 */
	Integer assetsUnravelByAssetsReId(AssetsReUnravelDTO assetsReUnravelDTO);

	/**
	 * 根据id获取详情
	 * @param assetsReId
	 * @return
	 */
	AssetsReLiqui getByAssetsReId(Integer assetsReId);
}
