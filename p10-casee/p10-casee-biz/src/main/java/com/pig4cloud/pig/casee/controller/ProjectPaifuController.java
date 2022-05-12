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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.paifu.*;
import com.pig4cloud.pig.casee.dto.paifu.count.AssetsRePaifuFlowChartPageDTO;
import com.pig4cloud.pig.casee.dto.paifu.excel.ImportPaifuDTO;
import com.pig4cloud.pig.casee.service.ProjectPaifuService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 拍辅项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projectPaifu" )
@Api(value = "projectPaifu", tags = "拍辅项目表管理")
public class ProjectPaifuController {

	@Autowired
    private ProjectPaifuService projectPaifuService;

	/**
	 * 项目拍辅分页查询
	 * @param page 分页对象
	 * @param projectPaifuPageDTO
	 * @return
	 */
	@ApiOperation(value = "项目拍辅分页查询", notes = "项目拍辅分页查询")
	@GetMapping("/queryProjectCaseePage" )
	public R queryProjectCaseePage(Page page, ProjectPaifuPageDTO projectPaifuPageDTO) {
		return R.ok(projectPaifuService.queryProjectCaseePage(page, projectPaifuPageDTO));
	}

	/**
	 * 新增拍辅项目案件
	 * @param projectPaifuSaveDTO
	 * @return R
	 */
	@ApiOperation(value = "新增拍辅项目案件", notes = "新增拍辅项目案件")
	@SysLog("新增拍辅项目案件" )
	@PostMapping("/saveProjectCasee")
	public R saveProjectCasee(@RequestBody ProjectPaifuSaveDTO projectPaifuSaveDTO) {
		return R.ok(projectPaifuService.saveProjectCasee(projectPaifuSaveDTO));
	}

	/**
	 * 查询项目案件详情
	 * @param projectId 项目id
	 * @return
	 */
	@ApiOperation(value = "查询项目案件详情", notes = "查询项目案件详情")
	@GetMapping("/queryProjectCaseeDetailList" )
	public R queryProjectCaseeDetailList(Integer projectId) {
		return R.ok(projectPaifuService.queryProjectCaseeDetailList(projectId));
	}

	/**
	 * 新增拍辅项目主体关联表
	 * @param projectSubjectReSaveDTO
	 * @return R
	 */
	@ApiOperation(value = "新增拍辅项目主体关联表", notes = "新增拍辅项目主体关联表")
	@SysLog("新增拍辅项目主体关联表" )
	@PostMapping("/addProjectSubjectRe")
	public R addProjectSubjectRe(@RequestBody ProjectSubjectReSaveDTO projectSubjectReSaveDTO) {
		return R.ok(projectPaifuService.addProjectSubjectRe(projectSubjectReSaveDTO));
	}

	/**
	 * 验证项目主体
	 * @param projectId 项目id
	 * @return
	 */
	@ApiOperation(value = "验证项目主体", notes = "验证项目主体")
	@GetMapping("/queryProjectSubjectRe" )
	public R queryProjectSubjectRe(Integer projectId,String unifiedIdentity) {
		return R.ok(projectPaifuService.queryProjectSubjectRe(projectId,unifiedIdentity));
	}

	/**
	 * 根据项目id修改项目和案件基本信息
	 * @param projectPaifuModifyDTO
	 * @return R
	 */
	@ApiOperation(value = "根据项目id修改项目和案件基本信息", notes = "根据项目id修改项目和案件基本信息")
	@SysLog("根据项目id修改项目和案件基本信息" )
	@PutMapping("/modifyByProjectId")
	public R modifyByProjectId(@RequestBody ProjectPaifuModifyDTO projectPaifuModifyDTO) {
		return R.ok(projectPaifuService.modifyByProjectId(projectPaifuModifyDTO));
	}

	/**
	 * 修改项目主体关联表
	 * @param projectSubjectReSaveDTO
	 * @return R
	 */
	@ApiOperation(value = "修改项目主体关联表", notes = "修改项目主体关联表")
	@SysLog("修改项目主体关联表" )
	@PutMapping("/modifyProjectSubjectRe")
	public R modifyProjectSubjectRe(@RequestBody ProjectSubjectReSaveDTO projectSubjectReSaveDTO) {
		return R.ok(projectPaifuService.modifyProjectSubjectRe(projectSubjectReSaveDTO));
	}

	/**
	 * 删除项目主体关联表
	 * @param projectSubjectReRemoveDTO
	 * @return R
	 */
	@ApiOperation(value = "删除项目主体关联表", notes = "删除项目主体关联表")
	@SysLog("删除项目主体关联表" )
	@DeleteMapping("/removeProjectSubjectRe" )
	public R removeProjectSubjectRe(ProjectSubjectReRemoveDTO projectSubjectReRemoveDTO) {
		return R.ok(projectPaifuService.removeProjectSubjectRe(projectSubjectReRemoveDTO));
	}

	/**
	 * 查询项目案件详情
	 * @param projectId 项目id
	 * @return
	 */
	@ApiOperation(value = "查询项目案件详情", notes = "查询项目案件详情")
	@GetMapping("/queryProjectCaseeDetail" )
	public R queryProjectCaseeDetail(Integer projectId) {
		return R.ok(projectPaifuService.queryProjectCaseeDetail(projectId));
	}

	/**
	 * 统计业务流程图
	 * @return
	 */
	@ApiOperation(value = "统计业务流程图", notes = "统计业务流程图")
	@GetMapping("/countProjectFlowChart" )
	public R countProjectFlowChart() {
		return R.ok(projectPaifuService.countProjectFlowChart());
	}

	/**
	 * 分页查询业务流程图节点列表
	 * @param page 分页对象
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	@ApiOperation(value = "项目拍辅分页查询", notes = "项目拍辅分页查询")
	@GetMapping("/queryFlowChartPage" )
	public R queryFlowChartPage(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		return R.ok(projectPaifuService.queryFlowChartPage(page, assetsRePaifuFlowChartPageDTO));
	}

	/**
	 * 分页查询不动产现勘未入户
	 * @param page 分页对象
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	@ApiOperation(value = "项目拍辅分页查询", notes = "项目拍辅分页查询")
	@GetMapping("/queryRealEstateNotSurveyedPage" )
	public R queryRealEstateNotSurveyedPage(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		return R.ok(projectPaifuService.queryRealEstateNotSurveyedPage(page, assetsRePaifuFlowChartPageDTO));
	}

	/**
	 * 公告期未拍卖
	 * @param page 分页对象
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	@ApiOperation(value = "公告期未拍卖", notes = "公告期未拍卖")
	@GetMapping("/queryAnnouncementPeriodNotAuctioned" )
	public R queryAnnouncementPeriodNotAuctioned(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		return R.ok(projectPaifuService.queryAnnouncementPeriodNotAuctioned(page, assetsRePaifuFlowChartPageDTO));
	}

	/**
	 * 拍卖到期无结果
	 * @param page 分页对象
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	@ApiOperation(value = "拍卖到期无结果", notes = "拍卖到期无结果")
	@GetMapping("/queryAuctionExpiresWithoutResults" )
	public R queryAuctionExpiresWithoutResults(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		return R.ok(projectPaifuService.queryAuctionExpiresWithoutResults(page, assetsRePaifuFlowChartPageDTO));
	}

	/**
	 * 拍卖成交未处理
	 * @param page 分页对象
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	@ApiOperation(value = "拍卖成交未处理", notes = "拍卖成交未处理")
	@GetMapping("/queryAuctionTransactionNotProcessed" )
	public R queryAuctionTransactionNotProcessed(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		return R.ok(projectPaifuService.queryAuctionTransactionNotProcessed(page, assetsRePaifuFlowChartPageDTO));
	}

	/**
	 * 拍卖异常未撤销
	 * @param page 分页对象
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	@ApiOperation(value = "拍卖异常未撤销", notes = "拍卖异常未撤销")
	@GetMapping("/queryAuctionExceptionNotCancelled" )
	public R queryAuctionExceptionNotCancelled(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		return R.ok(projectPaifuService.queryAuctionExceptionNotCancelled(page, assetsRePaifuFlowChartPageDTO));
	}

	/**
	 * 裁定未送达
	 * @param page 分页对象
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	@ApiOperation(value = "裁定未送达", notes = "裁定未送达")
	@GetMapping("/queryRulingNotService" )
	public R queryRulingNotService(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		return R.ok(projectPaifuService.queryRulingNotService(page, assetsRePaifuFlowChartPageDTO));
	}

	/**
	 * 拍卖不成交未处理
	 * @param page 分页对象
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	@ApiOperation(value = "拍卖不成交未处理", notes = "拍卖不成交未处理")
	@GetMapping("/queryAuctionTransactionFailedNotProcessed" )
	public R queryAuctionTransactionFailedNotProcessed(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		return R.ok(projectPaifuService.queryAuctionTransactionFailedNotProcessed(page, assetsRePaifuFlowChartPageDTO));
	}

	/**
	 * 到款/抵偿未裁定
	 * @param page 分页对象
	 * @param assetsRePaifuFlowChartPageDTO
	 * @return
	 */
	@ApiOperation(value = "到款/抵偿未裁定", notes = "到款/抵偿未裁定")
	@GetMapping("/queryArrivalCompensationNotAdjudicated" )
	public R queryArrivalCompensationNotAdjudicated(Page page, AssetsRePaifuFlowChartPageDTO assetsRePaifuFlowChartPageDTO) {
		return R.ok(projectPaifuService.queryArrivalCompensationNotAdjudicated(page, assetsRePaifuFlowChartPageDTO));
	}

	/**
	 * 根据id查询拍辅项目
	 * @param projectId
	 * @return
	 */
	@ApiOperation(value = "根据id查询拍辅项目", notes = "根据id查询拍辅项目")
	@GetMapping("/queryById/{projectId}" )
	public R queryById(@PathVariable("projectId" )Integer projectId) {

		return R.ok(projectPaifuService.queryById(projectId));
	}

	/**
	 * 导入excel拍辅数据
	 * @return
	 */
	@ApiOperation(value = "导入excel拍辅数据", notes = "导入excel拍辅数据")
	@SysLog("导入excel拍辅数据" )
	@PostMapping("/importExcel")
	public R importExcel(@RequestBody ImportPaifuDTO importPaifuDTO) {
		System.out.println(importPaifuDTO);
		return R.ok();
	}

}
