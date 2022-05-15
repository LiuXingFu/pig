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

package com.pig4cloud.pig.casee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.paifu.ExpenseRecordPaifuSaveDTO;
import com.pig4cloud.pig.casee.entity.ExpenseRecord;
import com.pig4cloud.pig.casee.vo.ExpenseRecordDistributeVO;
import com.pig4cloud.pig.casee.vo.ExpenseRecordVO;
import com.pig4cloud.pig.casee.vo.paifu.ExpenseRecordDetailVO;
import com.pig4cloud.pig.casee.vo.paifu.ExpenseRecordPaifuAssetsReListVO;
import com.pig4cloud.pig.casee.vo.paifu.ExpenseRecordPageVO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 费用产生记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:53:07
 */
public interface ExpenseRecordService extends IService<ExpenseRecord> {
   IPage<ExpenseRecordVO> getExpenseRecordPage(Page page, ExpenseRecord expenseRecord);

   boolean saveExpenseRecordUpdateProject(ExpenseRecord expenseRecord);

	boolean updateExpenseRecordUpdateProject(ExpenseRecord expenseRecord);

	boolean updateExpenseRecordStatusAndProjectAmount(ExpenseRecord expenseRecord);

	List<ExpenseRecordDistributeVO>getByPaymentType(ExpenseRecord expenseRecord);

	List<ExpenseRecordDistributeVO>getAssetsByPaymentType(Integer projectId,Integer caseeId,Integer assetsId);

	/**
	 * 根据项目id统计总金额
	 * @param projectId
	 * @return
	 */
	BigDecimal totalAmountByProjectId(Integer projectId);

	/**
	 * 保存拍辅费用产生记录
	 * @param expenseRecordPaifuSaveDTO
	 * @return
	 */
	Integer savePaifuExpenseRecord(ExpenseRecordPaifuSaveDTO expenseRecordPaifuSaveDTO);

	/**
	 * 修改拍辅费用产生记录
	 * @param expenseRecordPaifuSaveDTO
	 * @return
	 */
	Integer modifyPaifuExpenseRecord(ExpenseRecordPaifuSaveDTO expenseRecordPaifuSaveDTO);

	/**
	 *	根据费用产生记录id查询费用明细和财产关联集合
	 * @param expenseRecordId
	 * @return
	 */
	ExpenseRecordPaifuAssetsReListVO queryPaifuExpenseRecordAssetsReList(Integer expenseRecordId);

	/**
	 * 拍辅分页查询费用产生记录
	 * @param page
	 * @param projectId
	 * @return
	 */
	IPage<ExpenseRecordPageVO> queryPaifuExpenseRecordPage(Page page, Integer projectId);

	/**
	 * 根据id查询费用产生记录详情
	 * @param expenseRecordId
	 * @return
	 */
	ExpenseRecordDetailVO queryDetailById(Integer expenseRecordId);

	/**
	 * 根据项目id、财产关联id查询拍辅费是否存在
	 * @param projectId
	 * @param assetsReId
	 * @return
	 */
	ExpenseRecord queryByAssetsReId(Integer projectId,Integer assetsReId);

}
