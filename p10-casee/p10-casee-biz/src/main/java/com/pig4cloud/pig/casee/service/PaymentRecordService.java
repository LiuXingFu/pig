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
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
import com.pig4cloud.pig.casee.dto.PaymentRecordDTO;
import com.pig4cloud.pig.casee.dto.PaymentRecordPageDTO;
import com.pig4cloud.pig.casee.dto.count.CountMoneyBackMonthlyRankDTO;
import com.pig4cloud.pig.casee.dto.paifu.PaymentRecordSaveDTO;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.PaymentRecord;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.vo.MoneyBackMonthlyRank;
import com.pig4cloud.pig.casee.vo.PaymentRecordCourtPaymentVO;
import com.pig4cloud.pig.casee.vo.PaymentRecordVO;
import com.pig4cloud.pig.casee.vo.paifu.PaymentRecordListVO;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * 回款详细记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:52:51
 */
public interface PaymentRecordService extends IService<PaymentRecord> {

	IPage<PaymentRecordVO> getPaymentRecordPage(Page page, PaymentRecordPageDTO paymentRecordPageDTO);

	IPage<PaymentRecordVO> getProjectIdByPaymentRecordPage(Page page,String projectId);

	IPage<PaymentRecordVO> getByUnassignedPaymentRecordPage(Page page, PaymentRecordPageDTO paymentRecordPageDTO);

	BigDecimal sumCourtPayment(PaymentRecord paymentRecord);

	IPage<PaymentRecordCourtPaymentVO> getCourtPaymentPage(Page page, String projectId);

	List<PaymentRecordVO> getCourtPaymentUnpaid(Integer projectId);

	boolean savePaymentRecord(PaymentRecordDTO paymentRecordDTO);

	boolean distribute(PaymentRecordDTO paymentRecordDTO);

	boolean collection(PaymentRecordDTO paymentRecordDTO);

	/**
	 * 添加回款记录以及其它关联信息
	 * @param amount					回款金额
	 * @param paymentDate				回款时间
	 * @param project					项目
	 * @param casee 					案件
	 * @param assetsReSubjectDTO		财产关联主体信息
	 * @param jointAuctionAssetsDTOList	联合拍卖财产信息
	 * @param paymentType				联合拍卖财产信息
	 * @param fundsType					联合拍卖财产信息
	 * @return
	 */
	PaymentRecord addPaymentRecord(BigDecimal amount, LocalDate paymentDate, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO, List<JointAuctionAssetsDTO> jointAuctionAssetsDTOList, Integer paymentType, Integer fundsType);
	/**
	 * 较去年回款额
	 * @return
	 */
	BigDecimal queryCompareMoneyBackAmountCount();

	/**
	 * 本月回款额月排名
	 * @return
	 */
	IPage<MoneyBackMonthlyRank> queryMoneyBackMonthlyRankList(Page page, CountMoneyBackMonthlyRankDTO countMoneyBackMonthlyRankDTO);

	/**
	 * 本月总回款额
	 * @return
	 */
	BigDecimal getTotalRepayments();

	/**
	 * 根据时间集合查询回款额
	 * @param polylineColumnActive
	 * @param difference
	 * @return
	 */
	Map<String, BigDecimal> getPaymentRecordMap(Integer polylineColumnActive, List<String> difference);

	/**
	 * 根据项目id查询拍辅回款记录
	 * @param projectId
	 * @return
	 */
	List<PaymentRecordListVO> queryPaifuPaymentRecordList(Integer projectId);

	/**
	 * 保存拍辅回款记录
	 * @param paymentRecordSaveDTO
	 * @return
	 */
	Integer savePaifuPaymentRecord(PaymentRecordSaveDTO paymentRecordSaveDTO);
}
