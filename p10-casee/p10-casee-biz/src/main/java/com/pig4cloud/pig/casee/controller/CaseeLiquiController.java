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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.service.CaseeLiquiService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 案件表
 *
 * @author yy
 * @date 2022-01-10 14:51:59
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/caseeLiqui")
@Api(value = "caseeLiqui", tags = "清收案件表管理")
public class CaseeLiquiController {

	private final CaseeLiquiService caseeLiquiService;

	/**
	 * 新增案件表
	 *
	 * @param caseeLiquiAddDTO
	 * @return R
	 */
	@ApiOperation(value = "新增清收案件表", notes = "新增清收案件表")
	@SysLog("新增清收案件表")
	@PostMapping("/saveCaseeLiqui")
	public R saveCaseeLiqui(@RequestBody CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception {
		return R.ok(caseeLiquiService.addCaseeLiqui(caseeLiquiAddDTO));
	}

	/**
	 * 添加保全案件
	 *
	 * @param caseeLiquiAddDTO
	 * @return R
	 */
	@ApiOperation(value = "添加保全案件", notes = "添加保全案件")
	@SysLog("添加保全案件")
	@PostMapping("/savePreservationCasee")
	public R savePreservationCasee(@RequestBody CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception {
		return R.ok(caseeLiquiService.addPreservationCasee(caseeLiquiAddDTO));
	}

	/**
	 * 添加一审诉讼案件
	 *
	 * @param caseeLawsuitsDTO
	 * @return R
	 */
	@ApiOperation(value = "添加一审诉讼案件", notes = "添加一审诉讼案件")
	@SysLog("添加一审诉讼案件")
	@PostMapping("/saveLawsuitsCasee")
	public R saveLawsuitsCasee(@RequestBody CaseeLawsuitsDTO caseeLawsuitsDTO) throws Exception {
		return R.ok(caseeLiquiService.addLawsuitsCasee(caseeLawsuitsDTO));
	}

	/**
	 * 添加二审诉讼案件
	 * @param caseeSecondInstanceDTO
	 * @return R
	 */
	@ApiOperation(value = "添加二审诉讼案件", notes = "添加二审诉讼案件")
	@SysLog("添加二审诉讼案件")
	@PostMapping("/saveSecondInstanceCasee")
	public R saveSecondInstanceCasee(@RequestBody CaseeSecondInstanceDTO caseeSecondInstanceDTO) throws Exception {
		return R.ok(caseeLiquiService.saveSecondInstanceCasee(caseeSecondInstanceDTO));
	}

	/**
	 * 添加首执案件
	 *
	 * @param caseeLiquiAddDTO 案件表
	 * @return R
	 */
	@ApiOperation(value = "添加首执案件", notes = "添加首执案件")
	@SysLog("添加首执案件")
	@PostMapping("/saveExecutionCasee")
	public R saveExecutionCasee(@RequestBody CaseeLiquiAddDTO caseeLiquiAddDTO) throws Exception {
		return R.ok(caseeLiquiService.addExecutionCasee(caseeLiquiAddDTO));
	}

	/**
	 * 添加执恢案件
	 *
	 * @param caseeReinstatementDTO
	 * @return R
	 */
	@ApiOperation(value = "添加执恢案件", notes = "添加执恢案件")
	@SysLog("添加执恢案件")
	@PostMapping("/saveReinstatementCasee")
	public R saveReinstatementCasee(@RequestBody CaseeReinstatementDTO caseeReinstatementDTO) throws Exception {
		return R.ok(caseeLiquiService.addReinstatementCasee(caseeReinstatementDTO));
	}

	/**
	 * 根据项目id、案件类型查询一审或首执案件信息
	 *
	 * @param projectId id
	 * @return R
	 */
	@ApiOperation(value = "根据项目id、案件类型查询一审或首执案件信息", notes = "根据项目id、案件类型查询一审或首执案件信息")
	@GetMapping("/getCaseeParentId/{projectId}/{caseeType}")
	public R getCaseeParentId(@PathVariable("projectId") Integer projectId,@PathVariable("caseeType") Integer caseeType) {
		return R.ok(caseeLiquiService.getCaseeParentId(projectId, caseeType));
	}

	/**
	 * 案件清收分页查询
	 *
	 * @param page              分页对象
	 * @param caseeLiquiPageDTO 案件清收表
	 * @return
	 */
	@ApiOperation(value = "案件清收分页查询", notes = "案件清收分页查询")
	@GetMapping("/queryPageCaseeLiqui")
	public R queryPageCaseeLiqui(Page page, CaseeLiquiPageDTO caseeLiquiPageDTO) {
		return R.ok(caseeLiquiService.queryPage(page, caseeLiquiPageDTO));
	}

	/**
	 * 根据案件id查询案件信息
	 *
	 * @param caseeId
	 * @return
	 */
	@ApiOperation(value = "根据案件id查询案件信息", notes = "根据案件id查询案件信息")
	@GetMapping("/queryByCaseeId/{caseeId}")
	public R queryByCaseeId(@PathVariable("caseeId") Integer caseeId) {
		return R.ok(this.caseeLiquiService.queryByCaseeId(caseeId));
	}

	/**
	 * 查询案件主体集合
	 *
	 * @param caseeSubjectDTO
	 * @return
	 */
	@ApiOperation(value = "查询案件主体集合", notes = "查询案件主体集合")
	@GetMapping("/queryCaseeSubjectList")
	public R queryCaseeSubjectList(CaseeSubjectDTO caseeSubjectDTO) {
		return R.ok(caseeLiquiService.queryCaseeSubjectList(caseeSubjectDTO));
	}

	/**
	 * 根据项目id、案件类型集合查询案件信息
	 *
	 * @param caseeByProjectIdListDTO
	 * @return
	 */
	@ApiOperation(value = "根据项目id、案件类型集合查询案件信息", notes = "根据项目id、案件类型集合查询案件信息")
	@GetMapping("/queryByIdList")
	public R queryByIdList(CaseeByProjectIdListDTO caseeByProjectIdListDTO) {
		if (caseeByProjectIdListDTO.getCaseeType() != null) {
			return R.ok(caseeLiquiService.queryByIdList(caseeByProjectIdListDTO.getProjectId(), caseeByProjectIdListDTO.getCaseeType()));
		} else {
			return R.ok(caseeLiquiService.queryByIdList(caseeByProjectIdListDTO.getProjectId(), null));
		}
	}

	/**
	 * 查询债务人信息及财产和行为集合
	 *
	 * @param caseeId
	 * @return
	 */
	@ApiOperation(value = "查询债务人信息及财产和行为集合", notes = "查询债务人信息及财产和行为集合")
	@GetMapping("/queryAssetsBehavior")
	public R queryAssetsBehavior(Integer caseeId,@RequestParam(value = "caseePersonnelType",required = false)Integer caseePersonnelType) {
		return R.ok(caseeLiquiService.queryAssetsBehavior(caseeId,caseePersonnelType));
	}

	/**
	 * 分页查询财产未添加案件
	 *
	 * @param page              分页对象
	 * @param caseeLiquiPageDTO 案件清收表
	 * @return
	 */
	@ApiOperation(value = "分页查询财产未添加案件", notes = "分页查询财产未添加案件")
	@GetMapping("/queryAssetNotAddedPage")
	public R queryAssetNotAddedPage(Page page, CaseeLiquiPageDTO caseeLiquiPageDTO) {
		return R.ok(caseeLiquiService.queryAssetNotAddedPage(page, caseeLiquiPageDTO));
	}

	/**
	 * 分页查询案件流程图接口
	 *
	 * @param page              分页对象
	 * @param caseeLiquiFlowChartPageDTO 案件清收表
	 * @return
	 */
	@ApiOperation(value = "分页查询案件流程图接口", notes = "分页查询案件流程图接口")
	@GetMapping("/queryFlowChartPage")
	public R queryFlowChartPage(Page page, CaseeLiquiFlowChartPageDTO caseeLiquiFlowChartPageDTO) {
		return R.ok(caseeLiquiService.queryFlowChartPage(page, caseeLiquiFlowChartPageDTO));
	}

	/**
	 * 根据案件id查询案件详情
	 *
	 * @param caseeId
	 * @return
	 */
	@ApiOperation(value = "根据案件id查询案件详情", notes = "根据案件id查询案件详情")
	@GetMapping("/queryByCaseeIdDetails/{caseeId}")
	public R queryByCaseeIdDetails(@PathVariable("caseeId") Integer caseeId) {
		return R.ok(this.caseeLiquiService.queryByCaseeIdDetails(caseeId));
	}
}
