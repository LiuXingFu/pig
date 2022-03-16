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

package com.pig4cloud.pig.casee.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.AssetsAddDTO;
import com.pig4cloud.pig.casee.dto.AssetsReLiquiFlowChartPageDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.service.AssetsReLiquiService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/assetsReLiqui" )
@Api(value = "assetsReLiqui", tags = "财产关联表管理")
public class AssetsReLiquiController {

    private final AssetsReLiquiService assetsReLiquiService;

	/**
	 * 添加案件财产
	 * @param assetsAddDTO
	 * @return R
	 */
	@ApiOperation(value = "添加案件财产", notes = "添加案件财产")
	@SysLog("新增财产关联表" )
	@PostMapping("/saveAssetsCasee")
	public R save(@RequestBody AssetsAddDTO assetsAddDTO)throws Exception {
		return R.ok(assetsReLiquiService.saveAssetsCasee(assetsAddDTO));
	}


	/**
	 * 通过项目id和财产id查询案件id
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "通过项目id和财产id查询案件id", notes = "通过项目id和财产id查询案件id")
	@GetMapping("/getCaseeId" )
	public R getCaseeId(Integer projectId,Integer assetsId) {
		QueryWrapper<AssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AssetsRe::getDelFlag,0);
		queryWrapper.lambda().eq(AssetsRe::getAssetsId,assetsId);
		queryWrapper.lambda().eq(AssetsRe::getProjectId,projectId);
		return R.ok(assetsReLiquiService.getOne(queryWrapper));
	}

	/**
	 * 通过项目id、案件id、财产id查询财产债务人信息
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "通过项目id、案件id、财产id查询财产债务人信息", notes = "通过项目id、案件id、财产id查询财产债务人信息")
	@GetMapping("/queryAssetsSubject" )
	public R queryAssetsSubject(Integer projectId,Integer caseeId,Integer assetsId) {
		return R.ok(assetsReLiquiService.queryAssetsSubject(projectId,caseeId,assetsId));
	}

	/**
	 * 通过项目id、案件id、程序id查询财产抵押权信息
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "通过项目id、案件id、程序id查询财产抵押权信息", notes = "通过项目id、案件id、程序id查询财产抵押权信息")
	@GetMapping("/queryAssetsMortgage" )
	public R queryAssetsMortgage(Integer projectId,Integer caseeId,Integer targetId) {
		return R.ok(assetsReLiquiService.queryAssetsMortgage(projectId,caseeId,targetId));
	}

	/**
	 * 分页查询案件财产查封冻结情况
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "分页查询案件财产查封冻结情况", notes = "分页查询案件财产查封冻结情况")
	@GetMapping("/queryAssetsNotSeizeAndFreeze" )
	public R queryAssetsNotSeizeAndFreeze(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryAssetsNotSeizeAndFreeze(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 有抵押权轮封未商请移送
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "有抵押权轮封未商请移送", notes = "有抵押权轮封未商请移送")
	@GetMapping("/queryBusinessTransfer" )
	public R queryBusinessTransfer(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryBusinessTransfer(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 首冻资金未划扣
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "首冻资金未划扣", notes = "首冻资金未划扣")
	@GetMapping("/queryFundDeduction" )
	public R queryFundDeduction(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryFundDeduction(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 待拍财产
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "待拍财产", notes = "待拍财产")
	@GetMapping("/queryPropertyToBeAuctioned" )
	public R queryPropertyToBeAuctioned(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryPropertyToBeAuctioned(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 分页查询可处置财产程序节点统计列表
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "分页查询可处置财产程序节点统计列表", notes = "分页查询可处置财产程序节点统计列表")
	@GetMapping("/queryPropertyFlowChartPage" )
	public R queryPropertyFlowChartPage(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryPropertyFlowChartPage(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 财产拍卖公告期
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "财产拍卖公告期", notes = "财产拍卖公告期")
	@GetMapping("/queryPropertyAuctionAnnouncementPeriod" )
	public R queryPropertyAuctionAnnouncementPeriod(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryPropertyAuctionAnnouncementPeriod(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 查询案件财产详情
	 * @param assetsReId
	 * @return R
	 */
	@ApiOperation(value = "财产拍卖公告期", notes = "财产拍卖公告期")
	@GetMapping("/getAssetsReDetails" )
	public R getAssetsReDetails(Integer assetsReId) {
		return R.ok(assetsReLiquiService.getAssetsReDetails(assetsReId));
	}

	/**
	 * 到款/抵偿未裁定
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "到款/抵偿未裁定", notes = "到款/抵偿未裁定")
	@GetMapping("/queryDispositionRuling" )
	public R queryDispositionRuling(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryDispositionRuling(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 拍卖到期无结果
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "拍卖到期无结果", notes = "拍卖到期无结果")
	@GetMapping("/queryPropertyAuctionDue" )
	public R queryPropertyAuctionDue(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryPropertyAuctionDue(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 拍卖成功未处理
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "拍卖成功未处理", notes = "拍卖成功未处理")
	@GetMapping("/queryPropertyAuctionSuccess" )
	public R queryPropertyAuctionSuccess(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryPropertyAuctionSuccess(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 拍卖不成功未处理
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "拍卖不成功未处理", notes = "拍卖不成功未处理")
	@GetMapping("/queryPropertyAuctionFailed" )
	public R queryPropertyAuctionFailed(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryPropertyAuctionFailed(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 拍卖异常未撤销
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "拍卖异常未撤销", notes = "拍卖异常未撤销")
	@GetMapping("/queryPropertyAuctionAbnormal" )
	public R queryPropertyAuctionAbnormal(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryPropertyAuctionAbnormal(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 查询项目抵押财产
	 * @param projectId
	 * @return R
	 */
	@ApiOperation(value = "查询项目抵押财产", notes = "查询项目抵押财产")
	@GetMapping("/queryAssetsReAddress/{projectId}" )
	public R queryAssetsReAddress(@PathVariable("projectId")Integer projectId) {
		return R.ok(assetsReLiquiService.queryAssetsReAddress(projectId));
	}

	/**
	 * 查询案件财产查封冻结未完成
	 * @param assetsReLiquiFlowChartPageDTO
	 * @return R
	 */
	@ApiOperation(value = "查询案件财产查封冻结未完成", notes = "查询案件财产查封冻结未完成")
	@GetMapping("/queryCaseeAssetsNotFreeze" )
	public R queryCaseeAssetsNotFreeze(Page page, AssetsReLiquiFlowChartPageDTO assetsReLiquiFlowChartPageDTO) {
		return R.ok(assetsReLiquiService.queryCaseeAssetsNotFreeze(page,assetsReLiquiFlowChartPageDTO));
	}

	/**
	 * 查询债务人财产
	 * @param subjectId
	 * @return R
	 */
	@ApiOperation(value = "查询债务人财产", notes = "查询债务人财产")
	@GetMapping("/queryAssetsReBySubjectId" )
	public R queryAssetsReBySubjectId(Page page,Integer subjectId) {
		return R.ok(assetsReLiquiService.queryAssetsReBySubjectId(page,subjectId));
	}

}
