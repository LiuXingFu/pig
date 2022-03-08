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
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.entity.PaymentRecord;
import com.pig4cloud.pig.casee.vo.MoneyBackMonthlyRank;
import com.pig4cloud.pig.casee.vo.PaymentRecordCourtPaymentVO;
import com.pig4cloud.pig.casee.vo.PaymentRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * 回款详细记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:52:51
 */
@Mapper
public interface PaymentRecordMapper extends BaseMapper<PaymentRecord> {

	IPage<PaymentRecordVO> getPaymentRecordPage(Page page, @Param("query") PaymentRecord paymentRecord, @Param("login") InsOutlesDTO insOutlesDTO);

	BigDecimal sumCourtPayment(@Param("query") PaymentRecord paymentRecord);

	IPage<PaymentRecordCourtPaymentVO> getCourtPaymentPage(Page page, @Param("projectId")String projectId);

	BigDecimal queryCompareMoneyBackAmountCount(@Param("insId") Integer insId, @Param("outlesId") Integer outlesId);

	List<MoneyBackMonthlyRank> queryMoneyBackMonthlyRankList(@Param("insId") Integer insId, @Param("outlesId") Integer outlesId);

	BigDecimal getTotalRepayments();
}
