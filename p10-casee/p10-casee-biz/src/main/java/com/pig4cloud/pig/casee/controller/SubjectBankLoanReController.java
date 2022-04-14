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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.SubjectBankLoanReDTO;
import com.pig4cloud.pig.casee.entity.SubjectBankLoanRe;
import com.pig4cloud.pig.casee.service.SubjectBankLoanReService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 主体关联银行借贷表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:44
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/subjectbankloanre" )
@Api(value = "subjectbankloanre", tags = "主体关联银行借贷表管理")
public class SubjectBankLoanReController {

    private final SubjectBankLoanReService subjectBankLoanReService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param subjectBankLoanRe 主体关联银行借贷表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getSubjectBankLoanRePage(Page page, SubjectBankLoanRe subjectBankLoanRe) {
        return R.ok(subjectBankLoanReService.page(page, Wrappers.query(subjectBankLoanRe)));
    }


    /**
     * 通过id查询主体关联银行借贷表
     * @param subjectBankLoanId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{subjectBankLoanId}" )
    public R getById(@PathVariable("subjectBankLoanId" ) Integer subjectBankLoanId) {
        return R.ok(subjectBankLoanReService.getById(subjectBankLoanId));
    }

    /**
     * 新增主体关联银行借贷表
     * @param subjectBankLoanRe 主体关联银行借贷表
     * @return R
     */
    @ApiOperation(value = "新增主体关联银行借贷表", notes = "新增主体关联银行借贷表")
    @SysLog("新增主体关联银行借贷表" )
    @PostMapping
    public R save(@RequestBody SubjectBankLoanRe subjectBankLoanRe) {
		subjectBankLoanReService.save(subjectBankLoanRe);
        return R.ok(subjectBankLoanRe.getSubjectBankLoanId());
    }

    /**
     * 修改主体关联银行借贷表
     * @param subjectBankLoanRe 主体关联银行借贷表
     * @return R
     */
    @ApiOperation(value = "修改主体关联银行借贷表", notes = "修改主体关联银行借贷表")
    @SysLog("修改主体关联银行借贷表" )
    @PutMapping
    public R updateById(@RequestBody SubjectBankLoanRe subjectBankLoanRe) {
        return R.ok(subjectBankLoanReService.updateById(subjectBankLoanRe));
    }

	/**
	 * 根据银行借贷主体关联id更新债务人类型和主体信息
	 * @param subjectBankLoanReDTO 根据银行借贷主体关联id更新债务人类型和主体信息
	 * @return R
	 */
	@ApiOperation(value = "根据银行借贷主体关联id更新债务人类型和主体信息", notes = "根据银行借贷主体关联id更新债务人类型和主体信息")
	@SysLog("根据银行借贷主体关联id更新债务人类型和主体信息" )
	@PutMapping("/modifySubjectBySubjectBankLoanId")
	public R modifySubjectBySubjectBankLoanId(@RequestBody SubjectBankLoanReDTO subjectBankLoanReDTO) {
		return R.ok(subjectBankLoanReService.modifySubjectBySubjectBankLoanId(subjectBankLoanReDTO));
	}

    /**
     * 通过id删除主体关联银行借贷表
     * @param subjectBankLoanId id
     * @return R
     */
    @ApiOperation(value = "通过id删除主体关联银行借贷表", notes = "通过id删除主体关联银行借贷表")
    @SysLog("通过id删除主体关联银行借贷表" )
    @DeleteMapping("/{subjectBankLoanId}" )
    public R removeById(@PathVariable Integer subjectBankLoanId) {
        return R.ok(subjectBankLoanReService.removeById(subjectBankLoanId));
    }

	/**
	 * 删除主体以及主体关联银行借贷信息
	 * @param bankLoanId 银行借贷id
	 * @param subjectIds 主体id
	 * @return R
	 */
	@ApiOperation(value = "删除主体以及主体关联银行借贷信息", notes = "删除主体以及主体关联银行借贷信息")
	@SysLog("删除主体以及主体关联银行借贷信息" )
	@DeleteMapping("/removeSubjectAndBankLoan" )
	public R removeSubjectAndBankLoan(Integer bankLoanId, List<Integer> subjectIds) {
		return R.ok(subjectBankLoanReService.removeSubjectAndBankLoan(bankLoanId,subjectIds));
	}

	/**
	 * 删除主体关联银行借贷信息修改银行借贷债务人名称
	 * @param subjectBankLoanId 主体关联银行借贷id
	 * @param name 主体名称
	 * @return R
	 */
	@ApiOperation(value = "删除主体关联银行借贷信息修改银行借贷债务人名称", notes = "删除主体关联银行借贷信息修改银行借贷债务人名称")
	@SysLog("删除主体以及主体关联银行借贷信息" )
	@DeleteMapping("/removeSubjectBankLoanRe" )
	public R removeSubjectBankLoanRe(Integer subjectBankLoanId,Integer bankLoanId, String name) {
		return R.ok(subjectBankLoanReService.removeSubjectBankLoanRe(subjectBankLoanId,bankLoanId,name));
	}
}
