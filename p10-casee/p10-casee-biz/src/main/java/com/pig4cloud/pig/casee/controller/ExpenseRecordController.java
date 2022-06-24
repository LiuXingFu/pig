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
import com.pig4cloud.pig.casee.dto.paifu.ExpenseRecordPaifuSaveDTO;
import com.pig4cloud.pig.casee.entity.ExpenseRecord;
import com.pig4cloud.pig.casee.service.ExpenseRecordService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 费用产生记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:53:07
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/expenserecord" )
@Api(value = "expenserecord", tags = "费用产生记录表管理")
public class ExpenseRecordController {

    private final ExpenseRecordService expenseRecordService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param expenseRecord 费用产生记录表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getExpenseRecordPage(Page page, ExpenseRecord expenseRecord) {
        return R.ok(expenseRecordService.getExpenseRecordPage(page, expenseRecord));
    }


    /**
     * 通过id查询费用产生记录表
     * @param expenseRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{expenseRecordId}" )
    public R getById(@PathVariable("expenseRecordId" ) Integer expenseRecordId) {
        return R.ok(expenseRecordService.getById(expenseRecordId));
    }

	/**
	 * 根据案件案号和费用类型查询费用信息
	 * @param expenseRecord
	 * @return R
	 */
	@ApiOperation(value = "根据案件案号和费用类型查询费用信息", notes = "根据案件案号和费用类型查询费用信息")
	@GetMapping("/getByCaseeNumberAndCostType" )
	public R getByCaseeNumberAndCostType(ExpenseRecord expenseRecord) {
		return R.ok(expenseRecordService.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId,expenseRecord.getProjectId()).eq(ExpenseRecord::getCostType,expenseRecord.getCostType()).eq(ExpenseRecord::getCaseeNumber,expenseRecord.getCaseeNumber()).eq(ExpenseRecord::getStatus,0)));
	}

	/**
	 * 根据项目id、案件id和状态查询费用信息
	 * @param expenseRecord
	 * @return R
	 */
	@ApiOperation(value = "根据项目id、案件id和状态查询费用信息", notes = "根据项目id、案件id和状态查询费用信息")
	@GetMapping("/getByPaymentType" )
	public R getByPaymentType(ExpenseRecord expenseRecord) {
			return R.ok(expenseRecordService.getByPaymentType(expenseRecord));
	}

	/**
	 * 根据项目id、案件id费用类型和状态查询该费用已回多少款
	 * @param expenseRecord
	 * @return R
	 */
	@ApiOperation(value = "根据项目id、案件id费用类型和状态查询该费用已回多少款", notes = "根据项目id、案件id、费用类型和状态查询该费用已回多少款")
	@GetMapping("/getByExpenseRecordMoneyBack" )
	public R getByExpenseRecordMoneyBack(ExpenseRecord expenseRecord) {
		return R.ok(expenseRecordService.getByExpenseRecordMoneyBack(expenseRecord));
	}

	/**
	 * 根据项目id、案件id和财产id查询财产债务人费用信息
	 * @param projectId
	 * @return R
	 */
	@ApiOperation(value = "根据项目id、案件id和财产id查询财产债务人费用信息", notes = "根据项目id、案件id和财产id查询财产债务人费用信息")
	@GetMapping("/getAssetsByPaymentType" )
	public R getAssetsByPaymentType(Integer projectId,Integer caseeId,Integer assetsId) {
		return R.ok(expenseRecordService.getAssetsByPaymentType(projectId,caseeId,assetsId));
	}


    /**
     * 新增费用产生记录表
     * @param expenseRecord 费用产生记录表
     * @return R
     */
    @ApiOperation(value = "新增费用产生记录表", notes = "新增费用产生记录表")
    @SysLog("新增费用产生记录表" )
    @PostMapping
    public R save(@RequestBody ExpenseRecord expenseRecord) {
        return R.ok(expenseRecordService.save(expenseRecord));
    }

	/**
	 * 新增费用产生记录并修改项目金额
	 * @param expenseRecord 新增费用产生记录并修改项目金额
	 * @return R
	 */
	@ApiOperation(value = "新增费用产生记录并修改项目金额", notes = "新增费用产生记录并修改项目金额")
	@SysLog("新增费用产生记录并修改项目金额" )
	@PostMapping("/saveExpenseRecordUpdateProject")
	public R saveExpenseRecordUpdateProject(@RequestBody ExpenseRecord expenseRecord) {
		return R.ok(expenseRecordService.saveExpenseRecordUpdateProject(expenseRecord));
	}

	/**
	 * 修改费用产生记录并修改项目金额
	 * @param expenseRecord 修改费用产生记录并修改项目金额
	 * @return R
	 */
	@ApiOperation(value = "修改费用产生记录并修改项目金额", notes = "修改费用产生记录并修改项目金额")
	@SysLog("修改费用产生记录并修改项目金额" )
	@PutMapping("/updateExpenseRecordUpdateProject")
	public R updateExpenseRecordUpdateProject(@RequestBody ExpenseRecord expenseRecord) {
		return R.ok(expenseRecordService.updateExpenseRecordUpdateProject(expenseRecord));
	}

    /**
     * 修改费用产生记录表
     * @param expenseRecord 费用产生记录表
     * @return R
     */
    @ApiOperation(value = "修改费用产生记录表", notes = "修改费用产生记录表")
    @SysLog("修改费用产生记录表" )
    @PutMapping
    public R updateById(@RequestBody ExpenseRecord expenseRecord) {
        return R.ok(expenseRecordService.updateById(expenseRecord));
    }

	/**
	 * 修改费用产生记录状态以及项目金额
	 * @param expenseRecord
	 * @return R
	 */
	@ApiOperation(value = "修改费用产生记录状态以及项目金额", notes = "修改费用产生记录状态以及项目金额")
	@SysLog("修改费用产生记录状态以及项目金额" )
	@PutMapping("/updateExpenseRecordStatusAndProjectAmount")
	public R updateExpenseRecordStatusAndProjectAmount(@RequestBody ExpenseRecord expenseRecord) {
		return R.ok(expenseRecordService.updateExpenseRecordStatusAndProjectAmount(expenseRecord));
	}

    /**
     * 通过id删除费用产生记录表
     * @param expenseRecordId id
     * @return R
     */
    @ApiOperation(value = "通过id删除费用产生记录表", notes = "通过id删除费用产生记录表")
    @SysLog("通过id删除费用产生记录表" )
    @DeleteMapping("/{expenseRecordId}" )
    public R removeById(@PathVariable Integer expenseRecordId) {
        return R.ok(expenseRecordService.removeById(expenseRecordId));
    }

	/**
	 * 保存拍辅费用产生记录
	 * @param expenseRecordPaifuSaveDTO
	 * @return R
	 */
	@ApiOperation(value = "保存拍辅费用产生记录", notes = "保存拍辅费用产生记录")
	@SysLog("保存拍辅费用产生记录" )
	@PostMapping("/savePaifuExpenseRecord")
	public R savePaifuExpenseRecord(@RequestBody ExpenseRecordPaifuSaveDTO expenseRecordPaifuSaveDTO) {
		return R.ok(expenseRecordService.savePaifuExpenseRecord(expenseRecordPaifuSaveDTO));
	}

	/**
	 * 修改拍辅费用产生记录
	 * @param expenseRecordPaifuSaveDTO 修改拍辅费用产生记录
	 * @return R
	 */
	@ApiOperation(value = "修改拍辅费用产生记录", notes = "修改拍辅费用产生记录")
	@SysLog("修改拍辅费用产生记录" )
	@PutMapping("/modifyPaifuExpenseRecord")
	public R updateById(@RequestBody ExpenseRecordPaifuSaveDTO expenseRecordPaifuSaveDTO) {
		return R.ok(expenseRecordService.modifyPaifuExpenseRecord(expenseRecordPaifuSaveDTO));
	}

	/**
	 * 根据费用产生记录id查询费用明细和财产关联集合
	 * @param expenseRecordId
	 * @return R
	 */
	@ApiOperation(value = "根据费用产生记录id查询费用明细和财产关联集合", notes = "根据费用产生记录id查询费用明细和财产关联集合")
	@GetMapping("/queryPaifuExpenseRecordAssetsReList/{expenseRecordId}" )
	public R queryPaifuExpenseRecordAssetsReList(@PathVariable Integer expenseRecordId) {
		return R.ok(expenseRecordService.queryPaifuExpenseRecordAssetsReList(expenseRecordId));
	}

	/**
	 * 拍辅分页查询费用产生记录
	 * @param page 分页对象
	 * @param projectId 项目id
	 * @return
	 */
	@ApiOperation(value = "拍辅分页查询费用产生记录", notes = "拍辅分页查询费用产生记录")
	@GetMapping("/queryPaifuExpenseRecordPage" )
	public R queryPaifuExpenseRecordPage(Page page, Integer projectId) {
		return R.ok(expenseRecordService.queryPaifuExpenseRecordPage(page, projectId));
	}

	/**
	 * 根据id查询费用产生记录详情
	 * @param expenseRecordId 费用产生记录id
	 * @return
	 */
	@ApiOperation(value = "根据id查询费用产生记录详情", notes = "根据id查询费用产生记录详情")
	@GetMapping("/queryDetailById/{expenseRecordId}" )
	public R queryDetailById(@PathVariable Integer expenseRecordId) {
		return R.ok(expenseRecordService.queryDetailById(expenseRecordId));
	}
}
