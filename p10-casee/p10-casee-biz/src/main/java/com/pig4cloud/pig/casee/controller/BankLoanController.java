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
import com.pig4cloud.pig.casee.dto.BankLoanDTO;
import com.pig4cloud.pig.casee.dto.BankLoanInformationDTO;
import com.pig4cloud.pig.casee.entity.BankLoan;
import com.pig4cloud.pig.casee.entity.SubjectBankLoanRe;
import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.mapper.BankLoanMapper;
import com.pig4cloud.pig.casee.service.BankLoanService;
import com.pig4cloud.pig.casee.service.SubjectBankLoanReService;
import com.pig4cloud.pig.casee.service.TransferRecordService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Objects;


/**
 * 银行借贷表
 *
 * @author Mjh
 * @date 2022-01-29 10:20:00
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/bankloan" )
@Api(value = "bankloan", tags = "银行借贷表管理")
public class BankLoanController {

    private final BankLoanService bankLoanService;

	private final BankLoanMapper bankLoanMapper;

	private final SubjectBankLoanReService subjectBankLoanReService;

	private final TransferRecordService transferRecordService;



	/**
     * 分页查询
     * @param page 分页对象
     * @param bankLoanDTO 银行借贷表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getBankLoanPage(Page page, BankLoanDTO bankLoanDTO) {
        return R.ok(bankLoanService.bankLoanPage(page, bankLoanDTO));
    }


    /**
     * 通过id查询银行借贷基本信息
     * @param bankLoanId id
     * @return R
     */
    @ApiOperation(value = "通过id查询银行借贷基本信息", notes = "通过id查询银行借贷基本信息")
    @GetMapping("/{bankLoanId}" )
    public R getById(@PathVariable("bankLoanId" ) Integer bankLoanId) {
        return R.ok(bankLoanService.getByBankLoanInformation(bankLoanId));
    }

	/**
	 * 通过id查询银行借贷基本信息
	 * @param bankLoanId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询银行借贷基本信息", notes = "通过id查询银行借贷基本信息")
	@GetMapping("/getByBankLoanId/{bankLoanId}" )
	public R getByBankLoanId(@PathVariable("bankLoanId" ) Integer bankLoanId) {
		return R.ok(bankLoanService.getById(bankLoanId));
	}

    /**
     * 新增银行借贷表
     * @param bankLoan 银行借贷表
     * @return R
     */
    @ApiOperation(value = "新增银行借贷表", notes = "新增银行借贷表")
    @SysLog("新增银行借贷表" )
    @PostMapping
    public R save(@RequestBody BankLoan bankLoan) {
		BigDecimal principal = bankLoan.getPrincipal();
		BigDecimal interest = bankLoan.getInterest();
		bankLoan.setRental(principal.add(interest));
		bankLoanMapper.insert(bankLoan);
        return R.ok(bankLoan.getBankLoanId());
    }

    /**
     * 修改银行借贷表
     * @param bankLoan 银行借贷表
     * @return R
     */
    @ApiOperation(value = "修改银行借贷表", notes = "修改银行借贷表")
    @SysLog("修改银行借贷表" )
    @PutMapping
    public R updateById(@RequestBody BankLoan bankLoan) {
        return R.ok(bankLoanService.updateBankLoan(bankLoan));
    }

    /**
     * 通过id删除银行借贷表
     * @param bankLoanId id
     * @return R
     */
    @ApiOperation(value = "通过id删除银行借贷表", notes = "通过id删除银行借贷表")
    @SysLog("通过id删除银行借贷表" )
    @DeleteMapping("/{bankLoanId}" )
    public R removeById(@PathVariable Integer bankLoanId) {
		QueryWrapper<SubjectBankLoanRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(SubjectBankLoanRe::getBankLoanId,bankLoanId);
		subjectBankLoanReService.remove(queryWrapper);

		QueryWrapper<TransferRecord> transferRecordQueryWrapper = new QueryWrapper<>();
		transferRecordQueryWrapper.lambda().eq(TransferRecord::getSourceId,bankLoanId);
		transferRecordQueryWrapper.lambda().eq(TransferRecord::getTransferType,0);
		transferRecordService.remove(transferRecordQueryWrapper);

        return R.ok(bankLoanService.removeById(bankLoanId));
    }

}
