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
import com.pig4cloud.pig.casee.dto.TransferRecordDTO;
import com.pig4cloud.pig.casee.entity.BankLoan;
import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.entity.liquientity.TransferRecordLiqui;
import com.pig4cloud.pig.casee.service.BankLoanService;
import com.pig4cloud.pig.casee.service.TransferRecordLiquiService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 移交记录表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:40
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/transferRecordliqui" )
@Api(value = "transferRecordliqui", tags = "移交记录表管理")
public class TransferRecordliquiController {

	private final TransferRecordLiquiService transferRecordLiquiService;

	private final BankLoanService bankLoanService;

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param transferRecordDTO 移交记录表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page" )
	public R getTransferRecordPage(Page page, TransferRecordDTO transferRecordDTO) {
		return R.ok(transferRecordLiquiService.getTransferRecordPage(page,transferRecordDTO));
	}

	/**
	 * 通过源id查询移送详情
	 * @param sourceId 银行借贷id
	 * @return
	 */
	@ApiOperation(value = "通过源id查询移送详情", notes = "通过源id查询移送详情")
	@GetMapping("/getBankLoanIdTransferRecord/{sourceId}" )
	public R getBankLoanIdTransferRecordPage(@PathVariable("sourceId") Integer sourceId) {
		return R.ok(transferRecordLiquiService.getBankLoanIdTransferRecord(sourceId));
	}

	/**
	 * 通过id查询移交记录表
	 * @param transferRecordId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{transferRecordId}" )
	public R getById(@PathVariable("transferRecordId" ) Integer transferRecordId) {
		return R.ok(transferRecordLiquiService.getById(transferRecordId));
	}

	/**
	 * 通过项目id查询抵押财产信息
	 * @param projectId 项目id
	 * @return R
	 */
	@ApiOperation(value = "通过项目id查询抵押财产信息", notes = "通过项目id查询抵押财产信息")
	@GetMapping("/getProjectIdByAssets/{projectId}" )
	public R getProjectIdByAssets(@PathVariable("projectId" ) Integer projectId) {
		return R.ok(transferRecordLiquiService.getProjectIdByAssets(projectId));
	}

	/**
	 * 通过id查询移交记录和银行借贷信息
	 * @param transferRecordId id
	 * @return R
	 */
	@ApiOperation(value = "通过移交id或项目id查询移交记录和银行借贷信息", notes = "通过移交id或项目id查询移交记录和银行借贷信息")
	@GetMapping("/getByTransferRecordBankLoan" )
	public R getByTransferRecordBankLoan(Integer transferRecordId,Integer projectId) {
		return R.ok(transferRecordLiquiService.getTransferRecordBankLoan(transferRecordId,projectId));
	}

	/**
	 * 新增移交记录表
	 * @param transferRecordLiqui 移交记录表
	 * @return R
	 */
	@ApiOperation(value = "新增移交记录表", notes = "新增移交记录表")
	@SysLog("新增移交记录表" )
	@PostMapping("/saveTransferRecord")
	public R saveTransferRecord(@RequestBody TransferRecordLiqui transferRecordLiqui) {
		return R.ok(transferRecordLiquiService.save(transferRecordLiqui));
	}

	/**
	 * 修改移交记录表
	 * @param transferRecordLiqui 移交记录表
	 * @return R
	 */
	@ApiOperation(value = "修改移交记录表", notes = "修改移交记录表")
	@SysLog("修改移交记录表" )
	@PostMapping("/updateTransferRecord")
	public R updateTransferRecord(@RequestBody TransferRecordLiqui transferRecordLiqui) {
		BankLoan bankLoan=new BankLoan();
		bankLoan.setPrincipal(transferRecordLiqui.getTransferRecordLiquiDetail().getPrincipal());
		bankLoan.setInterest(transferRecordLiqui.getTransferRecordLiquiDetail().getInterest());
		bankLoan.setRental(transferRecordLiqui.getTransferRecordLiquiDetail().getHandoverAmount());
		bankLoan.setBankLoanId(transferRecordLiqui.getSourceId());
		bankLoanService.updateById(bankLoan);
		return R.ok(transferRecordLiquiService.updateById(transferRecordLiqui));
	}

	/**
	 * 修改移交记录表
	 * @param transferRecord 移交记录表
	 * @return R
	 */
	@ApiOperation(value = "修改移交记录表", notes = "修改移交记录表")
	@SysLog("修改移交记录表" )
	@PutMapping
	public R updateById(@RequestBody TransferRecord transferRecord) {
		return R.ok(transferRecordLiquiService.updateById(transferRecord));
	}

	/**
	 * 批量修改移交记录表
	 * @param transferRecordList 移交记录表
	 * @return R
	 */
	@ApiOperation(value = "批量修改移交记录表", notes = "批量修改移交记录表")
	@SysLog("批量修改移交记录表" )
	@PostMapping("/updateBatchById")
	public R updateBatchById(@RequestBody List<TransferRecord> transferRecordList) {
		return R.ok(transferRecordLiquiService.updateBatchById(transferRecordList));
	}

	/**
	 * 通过id删除移交记录表
	 * @param transferRecordId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除移交记录表", notes = "通过id删除移交记录表")
	@SysLog("通过id删除移交记录表" )
	@DeleteMapping("/{transferRecordId}" )
	public R removeById(@PathVariable Integer transferRecordId) {
		return R.ok(transferRecordLiquiService.removeById(transferRecordId));
	}

	/**
	 * 通过项目id查询抵押财产信息
	 * @param projectId 项目id
	 * @return R
	 */
	@ApiOperation(value = "通过项目id查询抵押财产信息", notes = "通过项目id查询抵押财产信息")
	@GetMapping("/getProjectIdByMortgageAssets/{projectId}" )
	public R getProjectIdByMortgageAssets(@PathVariable("projectId" ) Integer projectId) {
		return R.ok(transferRecordLiquiService.getProjectIdByMortgageAssets(projectId));
	}

	/**
	 * 接收移交记录
	 * @param transferRecordDTO 接收移交记录
	 * @return R
	 */
	@ApiOperation(value = "接收移交记录", notes = "接收移交记录")
	@SysLog("接收移交记录" )
	@PostMapping("/reception")
	public R reception(@RequestBody TransferRecordDTO transferRecordDTO) {
		return R.ok(transferRecordLiquiService.reception(transferRecordDTO));
	}

	/**
	 * 退回移交记录
	 * @param transferRecordLiqui 退回移交记录
	 * @return R
	 */
	@ApiOperation(value = "退回移交记录", notes = "退回移交记录")
	@SysLog("退回移交记录" )
	@PostMapping("/returnTransfer")
	public R returnTransfer(@RequestBody TransferRecordLiqui transferRecordLiqui) {
		return R.ok(transferRecordLiquiService.updateById(transferRecordLiqui));
	}
}
