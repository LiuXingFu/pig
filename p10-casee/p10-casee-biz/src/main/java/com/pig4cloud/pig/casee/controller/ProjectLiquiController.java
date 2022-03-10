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
import com.pig4cloud.pig.casee.dto.ProjectLiquiPageDTO;
import com.pig4cloud.pig.casee.dto.ProjectModifyStatusDTO;
import com.pig4cloud.pig.casee.dto.ProjectNoProcessedDTO;
import com.pig4cloud.pig.casee.dto.ProjectSubjectDTO;
import com.pig4cloud.pig.casee.dto.count.CountPolylineLineChartDTO;
import com.pig4cloud.pig.casee.dto.count.ExpirationReminderDTO;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.service.ProjectLiquiService;
import com.pig4cloud.pig.casee.vo.ProjectLiquiDealtVO;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 清收项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/projectLiqui" )
@Api(value = "projectLiqui", tags = "清收项目表管理")
public class ProjectLiquiController {

	@Autowired
    private ProjectLiquiService projectLiquiService;

	/**
	 * 项目清收分页查询
	 * @param page 分页对象
	 * @param projectLiquiPageDTO 项目清收表
	 * @return
	 */
	@ApiOperation(value = "项目清收分页查询", notes = "项目清收分页查询")
	@GetMapping("/queryPageLiqui" )
	public R queryPageLiqui(Page page, ProjectLiquiPageDTO projectLiquiPageDTO) {
		return R.ok(projectLiquiService.queryPageLiqui(page, projectLiquiPageDTO));
	}

	/**
	 * 通过id查询项目清收详情
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询项目清收详情", notes = "通过id查询项目清收详情")
	@GetMapping("/getProjectDetails/{projectId}" )
	public R getProjectDetails(@PathVariable("projectId" )Integer projectId) {
		return R.ok(projectLiquiService.getProjectDetails(projectId));
	}

	/**
	 * 根据id修改项目状态
	 * @param projectModifyStatusDTO 项目表
	 * @return R
	 */
	@ApiOperation(value = "根据id修改项目状态", notes = "根据id修改项目状态")
	@PutMapping("/modifyStatusById")
	public R modifyStatusById(@RequestBody ProjectModifyStatusDTO projectModifyStatusDTO) {
		return R.ok(projectLiquiService.modifyStatusById(projectModifyStatusDTO));
	}

	/**
	 * 通过id查询项目清收详情
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询项目清收详情", notes = "通过id查询项目清收详情")
	@GetMapping("/getByProjectId/{projectId}" )
	public R getByProjectId(@PathVariable("projectId" )Integer projectId) {
		return R.ok(projectLiquiService.getByProjectId(projectId));
	}

	/**
	 *	根据主体id分页查询项目与项目下的行为数据
	 * @param page
	 * @param subjectId
	 * @return
	 */
	@ApiOperation(value = "根据主体id分页查询项目与项目下的行为数据", notes = "根据主体id分页查询项目与项目下的行为数据")
	@GetMapping("/queryPageProjectLiqui")
	public R queryPageProjectLiqui(Page page, Integer subjectId){
		return R.ok(projectLiquiService.queryPageProjectLiqui(page, subjectId));
	}
	/**
	 * 通过id查询项目主体信息
	 * @param projectSubjectDTO id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询项目主体信息", notes = "通过id查询项目主体信息")
	@GetMapping("/queryProjectSubjectList" )
	public R queryProjectSubjectList(ProjectSubjectDTO projectSubjectDTO) {
		return R.ok(projectLiquiService.queryProjectSubjectList(projectSubjectDTO));
	}

	/**
	 * 案件表-获取字号
	 *
	 * @return
	 */
	@ApiOperation(value = "项目表-获取字号", notes = "项目表-获取字号")
	@SysLog("案件表-获取字号" )
	@GetMapping("/getWord")
	public R getWord(ProjectLiqui projectLiqui) {
		QueryWrapper<Project> caseeBizBaseQueryWrapper =new QueryWrapper<>();
		caseeBizBaseQueryWrapper.eq("project_type",projectLiqui.getProjectType()).eq("year",projectLiqui.getYear()).eq("alias",projectLiqui.getAlias()).orderByDesc("word").last("limit 1");
		Project projectres=projectLiquiService.getOne(caseeBizBaseQueryWrapper);
		int word;
		if(projectres==null){
			word=1;
		}else {
			word =projectres.getWord()+1;
		}
		return R.ok(word);
	}

	/**
	 * 通过项目id查询办理信息
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "通过项目id查询办理信息", notes = "通过项目id查询办理信息")
	@GetMapping("/queryDealt/{projectId}" )
	public R queryDealt(@PathVariable("projectId" )Integer projectId) {
		return R.ok(projectLiquiService.queryDealt(projectId));
	}

	/**
	 * 查询项目下执行案件债务人行为和财产集合
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "查询项目下执行案件债务人行为和财产集合", notes = "查询项目下执行案件债务人行为和财产集合")
	@GetMapping("/queryAssetsBehavior" )
	public R queryAssetsBehavior(Integer projectId,Integer caseePersonnelType) {
		return R.ok(projectLiquiService.queryAssetsBehavior(projectId,caseePersonnelType));
	}

	/**
	 * 统计项目待接收处理数量
	 * @return R
	 */
	@ApiOperation(value = "统计项目待接收处理数量", notes = "统计项目待接收处理数量")
	@GetMapping("/countProject" )
	public R countProject() {
		return R.ok(projectLiquiService.countProject());
	}

	/**
	 * 接收未处理分页查询
	 * @param page 分页对象
	 * @param projectNoProcessedDTO 项目清收表
	 * @return
	 */
	@ApiOperation(value = "接收未处理分页查询", notes = "接收未处理分页查询")
	@GetMapping("/queryNotProcessedPage" )
	public R queryNotProcessedPage(Page page, ProjectNoProcessedDTO projectNoProcessedDTO) {
		return R.ok(projectLiquiService.queryNotProcessedPage(page, projectNoProcessedDTO));
	}

	/**
	 * 诉前阶段数量统计
	 * @return R
	 */
	@ApiOperation(value = "诉前阶段数量统计", notes = "诉前阶段数量统计")
	@GetMapping("/countPreLitigationStage" )
	public R countPreLitigationStage() {
		return R.ok(projectLiquiService.countPreLitigationStage());
	}

	/**
	 * 诉讼阶段数量统计
	 * @return R
	 */
	@ApiOperation(value = "诉讼阶段数量统计", notes = "诉讼阶段数量统计")
	@GetMapping("/countlitigation" )
	public R countlitigation() {
		return R.ok(projectLiquiService.countlitigation());
	}

	/**
	 * 履行阶段数量统计
	 * @return R
	 */
	@ApiOperation(value = "履行阶段数量统计", notes = "履行阶段数量统计")
	@GetMapping("/countFulfill" )
	public R countFulfill() {
		return R.ok(projectLiquiService.countFulfill());
	}

	/**
	 * 执行阶段数量统计
	 * @return R
	 */
	@ApiOperation(value = "执行阶段数量统计", notes = "执行阶段数量统计")
	@GetMapping("/countImplement" )
	public R countImplement() {
		return R.ok(projectLiquiService.countImplement());
	}

	/**
	 * 统计债务人统计接口
	 * @return R
	 */
	@ApiOperation(value = "统计债务人统计接口", notes = "统计债务人统计接口")
	@GetMapping("/countDebtor" )
	public R countDebtor() {
		return R.ok(projectLiquiService.countDebtor());
	}

	/**
	 * 财产查控统计接口
	 * @return R
	 */
	@ApiOperation(value = "财产查控统计接口", notes = "财产查控统计接口")
	@GetMapping("/countPropertySearch" )
	public R countPropertySearch() {
		return R.ok(projectLiquiService.countPropertySearch());
	}

	/**
	 * 可处置财产统计接口
	 * @return R
	 */
	@ApiOperation(value = "可处置财产统计接口", notes = "可处置财产统计接口")
	@GetMapping("/countAuctionProperty" )
	public R countAuctionProperty() {
		return R.ok(projectLiquiService.countAuctionProperty());
	}

	/**
	 * 首页项目、事项统计接口
	 * @return
	 */
	@ApiOperation(value = "首页项目、事项统计接口", notes = "首页项目、事项统计接口")
	@GetMapping("/countProjectMatters" )
	public R countProjectMatters() {
		return R.ok(projectLiquiService.countProjectMatters());
	}

	/**
	 * 今年比较去年相关数量如：项目、回款额、案件数和财产数
	 * @return
	 */
	@ApiOperation(value = "今年比较去年相关数量如：项目、回款额、案件数和财产数", notes = "今年比较去年相关数量如：项目、回款额、案件数和财产数")
	@GetMapping("/countCompareQuantity" )
	public R countCompareQuantity() {
		return R.ok(projectLiquiService.countCompareQuantity());
	}

	/**
	 * 查询本月回款额排名
	 * @return
	 */
	@ApiOperation(value = "查询本月回款额排名", notes = "查询本月回款额排名")
	@GetMapping("/countMoneyBackMonthlyRank" )
	public  R countMoneyBackMonthlyRank() {
		return R.ok(projectLiquiService.countMoneyBackMonthlyRank());
	}

	/**
	 * 查询项目案件折线图
	 * @param countPolylineLineChartDTO
	 * @return
	 */
	@ApiOperation(value = "查询项目案件折线图", notes = "查询项目案件折线图")
	@GetMapping("/countPolylineLineChart" )
	public R countPolylineLineChart(CountPolylineLineChartDTO countPolylineLineChartDTO) {
		return R.ok(projectLiquiService.countPolylineLineChart(countPolylineLineChartDTO));
	}

	/**
	 * 履行阶段首执待立案
	 * @param projectNoProcessedDTO
	 * @return
	 */
	@ApiOperation(value = "履行阶段首执待立案", notes = "履行阶段首执待立案")
	@GetMapping("/queryFulfillFirstExecutionPending" )
	public R queryFulfillFirstExecutionPending(Page page, ProjectNoProcessedDTO projectNoProcessedDTO) {
		return R.ok(projectLiquiService.queryFulfillFirstExecutionPending(page,projectNoProcessedDTO));
	}

	/**
	 * 提醒事项
	 * @param expirationReminderDTO
	 * @return
	 */
	@ApiOperation(value = "提醒事项", notes = "提醒事项")
	@GetMapping("/queryStatisticsReminder" )
	public R queryStatisticsReminder(Page page, ExpirationReminderDTO expirationReminderDTO) {
		return R.ok(projectLiquiService.queryStatisticsReminder(page,expirationReminderDTO));
	}

}
