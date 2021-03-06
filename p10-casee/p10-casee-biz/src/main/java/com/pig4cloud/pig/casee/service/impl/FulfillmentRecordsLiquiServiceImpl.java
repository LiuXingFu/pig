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
import com.pig4cloud.pig.casee.dto.FulfillmentRecordsDTO;
import com.pig4cloud.pig.casee.dto.PaymentRecordAddDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.FulfillmentRecordsLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.mapper.FulfillmentRecordsLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.FulfillmentRecordsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 待履行记录表
 *
 * @author Mjh
 * @date 2022-03-01 20:36:31
 */
@Service
public class FulfillmentRecordsLiquiServiceImpl extends ServiceImpl<FulfillmentRecordsLiquiMapper, FulfillmentRecords> implements FulfillmentRecordsLiquiService {
	@Autowired
	private PaymentRecordService paymentRecordService;
	@Autowired
	private ExpenseRecordService expenseRecordService;

	@Autowired
	private ProjectLiquiService projectLiquiService;

	@Autowired
	private ReconciliatioMediationService reconciliatioMediationService;

	@Autowired
	private PaymentRecordSubjectReService paymentRecordSubjectReService;




	@Override
	public List<FulfillmentRecordsVO> getByReconciliatioMediationId(Integer reconciliatioMediationId) {
		return this.baseMapper.getByReconciliatioMediationId(reconciliatioMediationId);
	}

	@Override
	public IPage<FulfillmentRecordsVO> getFulfillmentRecordsPage(Page page, Integer reconciliatioMediationId) {
		return this.baseMapper.getFulfillmentRecordsPage(page,reconciliatioMediationId);
	}

	@Override
	@Transactional
	public boolean saveFulfillmentRecords(FulfillmentRecordsDTO fulfillmentRecordsDTO) {
		FulfillmentRecordsLiqui fulfillmentRecords=new FulfillmentRecordsLiqui();
		BeanUtils.copyProperties(fulfillmentRecordsDTO,fulfillmentRecords);
		if (fulfillmentRecordsDTO.getStatus()==1){
			PaymentRecord paymentRecord=new PaymentRecord();
			BeanUtils.copyProperties(fulfillmentRecordsDTO,paymentRecord);
			paymentRecord.setPaymentType(300);
			paymentRecord.setStatus(null);
			paymentRecord.setPaymentDate(fulfillmentRecords.getFulfillmentTime());
			paymentRecord.setPaymentAmount(fulfillmentRecords.getFulfillmentAmount());
			//添加回款记录
			paymentRecordService.save(paymentRecord);

			PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setSubjectId(fulfillmentRecordsDTO.getSubjectId());
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			paymentRecordSubjectReService.save(paymentRecordSubjectRe);

			fulfillmentRecords.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			for (PaymentRecordAddDTO paymentRecordDTO : fulfillmentRecordsDTO.getPaymentRecordList()) {
				paymentRecordDTO.setFatherId(paymentRecord.getPaymentRecordId());
				paymentRecordDTO.setPaymentType(300);
				paymentRecordDTO.setPaymentDate(paymentRecord.getPaymentDate());
				paymentRecordDTO.setSubjectName(paymentRecordDTO.getSubjectName());

				//添加分配款项信息
				paymentRecordService.save(paymentRecordDTO);

				//添加分配款项与主体关联信息
				paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
				paymentRecordSubjectRe.setSubjectId(fulfillmentRecordsDTO.getSubjectId());
				this.paymentRecordSubjectReService.save(paymentRecordSubjectRe);

				//修改明细记录状态
				ExpenseRecord expenseRecord = expenseRecordService.getById(paymentRecordDTO.getExpenseRecordId());
				if (expenseRecord.getCostAmount()==paymentRecord.getPaymentAmount().add(paymentRecordDTO.getPaymentSumAmount())){
					expenseRecord.setExpenseRecordId(paymentRecordDTO.getExpenseRecordId());
					expenseRecord.setStatus(1);
					expenseRecordService.updateById(expenseRecord);
				}
			}

			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(fulfillmentRecordsDTO.getProjectId());
			projectLiqui.getProjectLiQuiDetail().setRepaymentAmount(projectLiqui.getProjectLiQuiDetail().getRepaymentAmount().add(fulfillmentRecordsDTO.getFulfillmentAmount()));
			projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
			//修改项目回款金额
			projectLiquiService.updateById(projectLiqui);
		}else if (fulfillmentRecordsDTO.getStatus()==2){//不能履行
			FulfillmentRecords queryFulfillmentRecords = this.getById(fulfillmentRecordsDTO.getFulfillmentRecordId());
			ReconciliatioMediation reconciliatioMediation=new ReconciliatioMediation();
			reconciliatioMediation.setReconciliatioMediationId(queryFulfillmentRecords.getReconciliatioMediationId());
			reconciliatioMediation.setStatus(1);
			reconciliatioMediationService.updateById(reconciliatioMediation);
		}if (fulfillmentRecordsDTO.getStatus()==3){//推迟履行
			FulfillmentRecords queryFulfillmentRecords = this.getById(fulfillmentRecordsDTO.getFulfillmentRecordId());
			//新增一条推迟待履行记录信息
			FulfillmentRecordsLiqui savaFulfillmentRecords=new FulfillmentRecordsLiqui();
			BeanUtils.copyProperties(queryFulfillmentRecords,savaFulfillmentRecords);
			savaFulfillmentRecords.setFulfillmentRecordId(null);
			savaFulfillmentRecords.setDetails(null);
			savaFulfillmentRecords.setAppendixFile(null);
			savaFulfillmentRecords.setRemark(null);
			savaFulfillmentRecords.setStatus(0);
			savaFulfillmentRecords.setPeriod(queryFulfillmentRecords.getPeriod());
			savaFulfillmentRecords.setFulfillmentTime(fulfillmentRecordsDTO.getFulfillmentRecordsDetail().getDeferPerformance());
			this.save(savaFulfillmentRecords);
		}
		return this.updateById(fulfillmentRecords);
	}
}
