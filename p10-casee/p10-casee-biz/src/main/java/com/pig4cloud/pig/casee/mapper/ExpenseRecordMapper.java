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

package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.entity.ExpenseRecord;
import com.pig4cloud.pig.casee.entity.ExpenseRecordSubjectRe;
import com.pig4cloud.pig.casee.vo.ExpenseRecordDistributeVO;
import com.pig4cloud.pig.casee.vo.ExpenseRecordMoneyBackVO;
import com.pig4cloud.pig.casee.vo.ExpenseRecordVO;
import com.pig4cloud.pig.casee.vo.paifu.ExpenseRecordDetailVO;
import com.pig4cloud.pig.casee.vo.paifu.ExpenseRecordPageVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 费用产生记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:53:07
 */
@Mapper
public interface ExpenseRecordMapper extends BaseMapper<ExpenseRecord> {
	IPage<ExpenseRecordVO> getExpenseRecordPage(Page page, @Param("query")ExpenseRecord expenseRecord);

	List<ExpenseRecordDistributeVO> getByPaymentType(@Param("query") ExpenseRecord expenseRecord);

	ExpenseRecordMoneyBackVO getByExpenseRecordMoneyBack(@Param("query") ExpenseRecord expenseRecord);

	List<ExpenseRecordDistributeVO> getAssetsByPaymentType(@Param("expenseRecordSubjectReList") List<ExpenseRecordSubjectRe> expenseRecordSubjectReList,@Param("projectId")Integer projectId,@Param("caseeId")Integer caseeId);

	List<ExpenseRecordDistributeVO> selectByProjectCaseeAssetsId(@Param("projectId")Integer projectId,@Param("caseeId")Integer caseeId,@Param("assetsId")Integer assetsId);

	BigDecimal totalAmountByProjectId(@Param("projectId")Integer projectId);

	IPage<ExpenseRecordPageVO> queryPaifuExpenseRecordPage(Page page, @Param("projectId")Integer projectId);

	ExpenseRecordDetailVO queryDetailById(@Param("expenseRecordId")Integer expenseRecordId);

	ExpenseRecord queryByAssetsReId(@Param("projectId")Integer projectId,@Param("assetsReId")Integer assetsReId);
}
