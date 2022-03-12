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
package com.pig4cloud.pig.casee.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.PaymentRecordAddDTO;
import com.pig4cloud.pig.casee.dto.PaymentRecordDTO;
import com.pig4cloud.pig.casee.entity.ExpenseRecord;
import com.pig4cloud.pig.casee.dto.count.CountMoneyBackMonthlyRankDTO;
import com.pig4cloud.pig.casee.entity.PaymentRecord;
import com.pig4cloud.pig.casee.entity.PaymentRecordSubjectRe;
import com.pig4cloud.pig.casee.entity.Project;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.mapper.PaymentRecordMapper;
import com.pig4cloud.pig.casee.service.ExpenseRecordService;
import com.pig4cloud.pig.casee.service.PaymentRecordService;
import com.pig4cloud.pig.casee.service.PaymentRecordSubjectReService;
import com.pig4cloud.pig.casee.service.ProjectLiquiService;
import com.pig4cloud.pig.casee.vo.MoneyBackMonthlyRank;
import com.pig4cloud.pig.casee.vo.PaymentRecordCourtPaymentVO;
import com.pig4cloud.pig.casee.vo.PaymentRecordVO;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 回款详细记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:52:51
 */
@Service
public class PaymentRecordServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecord> implements PaymentRecordService {
	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;
	@Autowired
	private PaymentRecordSubjectReService paymentRecordSubjectReService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private ExpenseRecordService expenseRecordService;

	@Override
	public IPage<PaymentRecordVO> getPaymentRecordPage(Page page, PaymentRecord paymentRecord) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.getPaymentRecordPage(page,paymentRecord,insOutlesDTO);
	}

	@Override
	public BigDecimal sumCourtPayment(PaymentRecord paymentRecord){
		return this.baseMapper.sumCourtPayment(paymentRecord);
	}

	@Override
	public IPage<PaymentRecordCourtPaymentVO> getCourtPaymentPage(Page page, String projectId) {
		return this.baseMapper.getCourtPaymentPage(page,projectId);
	}

	@Override
	public List<PaymentRecordVO> getCourtPaymentUnpaid(Integer projectId) {
		return this.baseMapper.getCourtPaymentUnpaid(projectId);
	}

	@Override
	public boolean savePaymentRecord(PaymentRecordDTO paymentRecordDTO) {
		this.save(paymentRecordDTO);
		PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
		paymentRecordSubjectRe.setPaymentRecordId(paymentRecordDTO.getPaymentRecordId());
		paymentRecordSubjectRe.setSubjectId(paymentRecordDTO.getSubjectId());
		return this.paymentRecordSubjectReService.save(paymentRecordSubjectRe);
	}

	@Override
	public boolean collection(PaymentRecordDTO paymentRecordDTO) {
		//添加领款信息
		boolean save = this.save(paymentRecordDTO);

		//修改项目回款总金额
		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(paymentRecordDTO.getProjectId());
		projectLiqui.getProjectLiQuiDetail().setRepaymentAmount(projectLiqui.getProjectLiQuiDetail().getRepaymentAmount().add(paymentRecordDTO.getPaymentAmount()));
		projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
		projectLiquiService.updateById(projectLiqui);

		List<Integer> subjectIdList = paymentRecordDTO.getSubjectIdList();
		//添加回款记录与主体关联信息
		for (Integer integer : subjectIdList) {
			PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecordDTO.getPaymentRecordId());
			paymentRecordSubjectRe.setSubjectId(integer);
			this.paymentRecordSubjectReService.save(paymentRecordSubjectRe);
		}

		for (PaymentRecordAddDTO paymentRecord : paymentRecordDTO.getPaymentRecordList()) {
			paymentRecord.setFatherId(paymentRecordDTO.getPaymentRecordId());
			paymentRecord.setPaymentDate(paymentRecordDTO.getPaymentDate());
			paymentRecord.setPaymentType(paymentRecordDTO.getPaymentType());
			paymentRecord.setSubjectName(paymentRecordDTO.getSubjectName());
			//添加分配款项信息
			this.save(paymentRecord);

			//添加分配款项明细与主体关联信息
			for (Integer integer : subjectIdList) {
				PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
				paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
				paymentRecordSubjectRe.setSubjectId(integer);
				this.paymentRecordSubjectReService.save(paymentRecordSubjectRe);
			}

			//修改明细记录状态
			ExpenseRecord expenseRecord = expenseRecordService.getById(paymentRecord.getExpenseRecordId());
			if (expenseRecord.getCostAmount()==paymentRecord.getPaymentAmount().add(paymentRecord.getPaymentSumAmount())){
				expenseRecord.setExpenseRecordId(paymentRecord.getExpenseRecordId());
				expenseRecord.setStatus(1);
				expenseRecordService.updateById(expenseRecord);
			}
		}

		//修改到款记录状态
		List<PaymentRecord> courtPayment = paymentRecordDTO.getCourtPayment();
		if (courtPayment!=null&&courtPayment.size()>0){
			for (PaymentRecord paymentRecord : courtPayment) {
				paymentRecord.setStatus(1);
			}
			this.updateBatchById(courtPayment);
		}

		return save;
	}

	/**
	 * 查询较去年回款额
	 * @return
	 */
	@Override
	public BigDecimal queryCompareMoneyBackAmountCount() {
		return this.baseMapper.queryCompareMoneyBackAmountCount(jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * 本月回款额月排名
	 * @return
	 */
	@Override
	public IPage<MoneyBackMonthlyRank> queryMoneyBackMonthlyRankList(Page page, CountMoneyBackMonthlyRankDTO countMoneyBackMonthlyRankDTO) {
		return this.baseMapper.queryMoneyBackMonthlyRankList(page, countMoneyBackMonthlyRankDTO, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * 本月总回款额
	 * @return
	 */
	@Override
	public BigDecimal getTotalRepayments() {
		return this.baseMapper.getTotalRepayments();
	}

	/**
	 * 根据时间集合查询回款额
	 * @param polylineColumnActive
	 * @param difference
	 * @return
	 */
	@Override
	public Map<String, BigDecimal> getPaymentRecordMap(Integer polylineColumnActive, List<String> difference) {
		return this.baseMapper.getPaymentRecordMap(polylineColumnActive, difference, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}
}
