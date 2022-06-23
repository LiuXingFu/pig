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

package com.pig4cloud.pig.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.CourtExcelDTO;
import com.pig4cloud.pig.admin.api.entity.Court;
import com.pig4cloud.pig.admin.service.CourtService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 法院表
 *
 * @author Mjh
 * @date 2021-09-03 17:14:47
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/court")
@Api(value = "court", tags = "法院表")
public class CourtController {

	private final CourtService courtService;

	/**
	 * 分页查询
	 *
	 * @param page    分页对象
	 * @param court 法院表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R getCourtPage(Page page, Court court) {
		return R.ok(courtService.page(page, Wrappers.query(court)));
	}

	/**
	 * 根据名称分页查询相应法院
	 * @param page
	 * @param court
	 * @return
	 */
	@ApiOperation(value = "根据名称分页查询相应法院", notes = "根据名称分页查询相应法院")
	@GetMapping("/getCourtPageList")
	public R getCourtPageList(Page page, Court court){
		return R.ok(courtService.getCourtPageList(page, court));
	}

	/**
	 * 通过id查询法院表
	 *
	 * @param courtId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{courtId}")
	public R getById(@PathVariable("courtId") Integer courtId) {
		return R.ok(courtService.getById(courtId));
	}

	/**
	 * 新增法院表
	 *
	 * @param court 法院表
	 * @return R
	 */
	@ApiOperation(value = "新增法院表", notes = "新增法院表")
	@SysLog("新增法院表")
	@PostMapping
	public R save(@RequestBody Court court) {
		return R.ok(courtService.save(court));
	}

	/**
	 * 修改法院表
	 *
	 * @param court 法院表
	 * @return R
	 */
	@ApiOperation(value = "修改法院表", notes = "修改法院表")
	@SysLog("修改法院表")
	@PutMapping
	public R updateById(@RequestBody Court court) {
		return R.ok(courtService.updateById(court));
	}

	/**
	 * 通过id删除法院表
	 *
	 * @param courtId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除法院表", notes = "通过id删除法院表")
	@SysLog("通过id删除法院表")
	@DeleteMapping("/{courtId}")
	public R removeById(@PathVariable Integer courtId) {
		return R.ok(courtService.removeById(courtId));
	}


	/**
	 * 通过地区code或法院名称查询法院信息
	 *
	 * @param regionCode 地区code
	 * @return R
	 */
	@ApiOperation(value = "通过地区code或法院名称查询法院信息", notes = "通过地区code或法院名称查询法院信息")
	@GetMapping("/getByRegionIdOrCourtName")
	public R getByRegionId(Integer regionCode,String courtName) {
		return R.ok(courtService.getByRegionCodeOrCourtName(regionCode,courtName));
	}

	/**
	 *	查询入驻法院
	 * @param courtName
	 * @return
	 */
	@ApiOperation(value = "查询入驻法院", notes = "查询入驻法院")
	@GetMapping("/queryCourtPage")
	public R queryCourtPage(Page page, String courtName) {
		return R.ok(courtService.queryCourtPage(page, courtName));
	}

	/**
	 * 查询未入驻的法院
	 * @param page
	 * @param insId
	 * @param outlesId
	 * @param courtName
	 * @return
	 */
	@ApiOperation(value = "查询入驻法院", notes = "查询入驻法院")
	@GetMapping("/queryCourtPageByInsIdAndOutlesId")
	public R queryCourtPageByInsIdAndOutlesId(Page page, Integer insId, Integer outlesId, String courtName){
		return R.ok(courtService.queryCourtPageByInsIdAndOutlesId(page, insId, outlesId, courtName));
	}

	/**
	 * 查询所有法院
	 * @return
	 */
	@ApiOperation(value = "查询所有法院", notes = "查询所有法院")
	@GetMapping("/queryCourtList")
	public R queryCourtList(String courtName){
		QueryWrapper<Court> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().like(Court::getCourtName,courtName);
		queryWrapper.lambda().eq(Court::getDelFlag,0);
		return R.ok(courtService.list(queryWrapper));
	}

	/**
	 * 导入法院数据
	 * @return
	 */
	@ApiOperation(value = "新增法院表", notes = "新增法院表")
	@PostMapping("/importCourt")
	public R importCourt(@RequestBody List<CourtExcelDTO> courtExcelDTOList) {
		courtService.importCourt(courtExcelDTOList);
		return R.ok();
	}

	/**
	 * 将法院的省市区存入对应的法院表中
	 * @return
	 */
	@ApiOperation(value = "将法院的省市区存入对应的法院表中", notes = "将法院的省市区存入对应的法院表中")
	@PostMapping("/setRegion")
	public R setRegion() {
		courtService.setRegion();
		return R.ok();
	}

}
