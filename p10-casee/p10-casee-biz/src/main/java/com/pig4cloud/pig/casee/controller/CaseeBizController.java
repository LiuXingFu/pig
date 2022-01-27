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
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;


import com.pig4cloud.pig.admin.api.feign.RemoteAuthUtilsService;
import com.pig4cloud.pig.casee.dto.CaseeBizBaseDTO;
import com.pig4cloud.pig.casee.dto.CaseeOrTargetTaskFlowDTO;
import com.pig4cloud.pig.casee.entity.CaseeBizBase;
import com.pig4cloud.pig.casee.entity.CaseeBizLiqui;
import com.pig4cloud.pig.casee.service.CaseeBizLiquiService;
import com.pig4cloud.pig.casee.service.CaseeBizService;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 案件表
 *
 * @author yy
 * @date 2021-09-15 10:03:22
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/caseeBiz" )
@Api(value = "caseeBiz", tags = "案件表管理")
public class CaseeBizController {


	private final CaseeBizService caseeBizService;

	private final CaseeBizLiquiService caseeBizLiquiService;

	@ApiOperation(value = "测试", notes = "测试")
	@GetMapping("/pub/test" )
	public R pubTest() {
		return R.ok( "成功返回");
	}

	/**
	 * 通过id查询案件表
	 * @param caseeId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{caseeId}" )
	public R getById(@PathVariable("caseeId" ) Integer caseeId) {
		return R.ok(caseeBizService.getById(caseeId));
	}

	/**
	 * 通过id删除案件表
	 * @param caseeId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除案件表", notes = "通过id删除案件表")
	@SysLog("通过id删除案件表" )
	@DeleteMapping("/{caseeId}" )
	public R removeById(@PathVariable Integer caseeId) {
		LambdaUpdateWrapper<CaseeBizBase> caseeLambdaUpdateWrapper=new LambdaUpdateWrapper<>();
		caseeLambdaUpdateWrapper.eq(CaseeBizBase::getCaseeId,caseeId).set(CaseeBizBase::getDelFlag,1);
		return R.ok(caseeBizService.update(caseeLambdaUpdateWrapper));
	}

	/**
	 * 修改案件待确认状态
	 * @param caseeId 案件id
	 * @param status 案件状态：1-开启、2-暂缓、3-中止
	 * @return R
	 */
	@ApiOperation(value = "修改案件待确认状态", notes = "修改案件待确认状态")
	@SysLog("修改案件待确认状态" )
	@PutMapping("/modifyCaseStatus")
	public R modifyCaseStatus(Integer caseeId,Integer status) {
		return R.ok(caseeBizService.modifyCaseStatus(caseeId,status));
	}

	/**
	 * 案件结案开启
	 *   caseeId 案件id
	 *  closeTime 结案时间
	 * @return R
	 */
	@ApiOperation(value = "案件结案开启", notes = "案件结案开启")
	@SysLog("案件结案开启" )
	@PostMapping("/caseClosedOpen")
	public R caseClosedOpen(@RequestBody CaseeBizBaseDTO caseeBizBaseDTO) {
		return R.ok(caseeBizService.caseClosedOpen(caseeBizBaseDTO.getCaseeId(),caseeBizBaseDTO.getCloseTime()));
	}

	/**json_extract
	 * 案件表-获取字号
	 *
	 * @return
	 */
	@ApiOperation(value = "案件表-获取字号", notes = "案件表-获取字号")
	@SysLog("案件表-获取字号" )
	@GetMapping("/getWord")
	public R getWord(CaseeBizBase caseBase) {
		QueryWrapper<CaseeBizBase> caseeBizBaseQueryWrapper =new QueryWrapper<>();
		caseeBizBaseQueryWrapper.eq("casee_type",caseBase.getCaseeType()).eq("year",caseBase.getYear()).eq("alias",caseBase.getAlias()).orderByDesc("word").last("limit 1");
		CaseeBizBase caseeBizBase=caseeBizService.getOne(caseeBizBaseQueryWrapper);
		int word;
		if(caseeBizBase==null){
			word=1;
		}else {
			word =caseeBizBase.getWord()+1;
		}
		return R.ok(word);
	}

	/**
	 * 验证业务案号
	 *
	 * @return
	 */
	@ApiOperation(value = "验证业务案号", notes = "验证业务案号")
	@SysLog("验证业务案号" )
	@GetMapping("/verifyCompanyCode")
	public R verifyCompanyCode(CaseeBizBase caseBase) {
		QueryWrapper<CaseeBizBase> patfuGoodsCaseQueryWrapper=new QueryWrapper<>();
		patfuGoodsCaseQueryWrapper.eq("company_code",caseBase.getCompanyCode()).eq("del_flag","0");
		List<CaseeBizBase> patfuGoodsCaseWord=caseeBizService.list(patfuGoodsCaseQueryWrapper);
		if(patfuGoodsCaseWord.size()>0){
			return R.ok(false);
		}
		return R.ok(true);
	}


	/**
	 * 委托方案件状态确认
	 *  statusConfirm 状态确认：2-已确认，3-已拒绝
	 * @return R
	 */
	@ApiOperation(value = "委托方案件状态确认", notes = "委托方案件状态确认")
	@SysLog("委托方案件状态确认" )
	@PutMapping("/confirmCaseStatus")
	public R confirmCaseStatus(CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO) {
		Integer statusConfirm=null;
		if (caseeOrTargetTaskFlowDTO.getStatus()==403){
			statusConfirm=2;
		}else if (caseeOrTargetTaskFlowDTO.getStatus()==111){
			statusConfirm=3;
		}
		caseeBizService.confirmCaseStatus(caseeOrTargetTaskFlowDTO,statusConfirm);
		return R.ok("更新成功");
	}

}
