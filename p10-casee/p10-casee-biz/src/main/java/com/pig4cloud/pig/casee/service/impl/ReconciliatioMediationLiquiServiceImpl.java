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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.ProjectModifyStatusDTO;
import com.pig4cloud.pig.casee.dto.ReconciliatioMediationDTO;
import com.pig4cloud.pig.casee.entity.FulfillmentRecords;
import com.pig4cloud.pig.casee.entity.ReconciliatioMediation;
import com.pig4cloud.pig.casee.entity.liquientity.ReconciliatioMediationLiqui;
import com.pig4cloud.pig.casee.mapper.ReconciliatioMediationLiquiMapper;
import com.pig4cloud.pig.casee.service.FulfillmentRecordsLiquiService;
import com.pig4cloud.pig.casee.service.ProjectLiquiService;
import com.pig4cloud.pig.casee.service.ReconciliatioMediationLiquiService;
import com.pig4cloud.pig.casee.vo.FulfillmentRecordsVO;
import com.pig4cloud.pig.casee.vo.ReconciliatioMediationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 和解/调解表
 *
 * @author Mjh
 * @date 2022-03-01 20:36:17
 */
@Service
public class ReconciliatioMediationLiquiServiceImpl extends ServiceImpl<ReconciliatioMediationLiquiMapper, ReconciliatioMediation> implements ReconciliatioMediationLiquiService {
	@Autowired
	private FulfillmentRecordsLiquiService fulfillmentRecordsService;
	@Autowired
	private ProjectLiquiService projectLiquiService;

	@Override
	public IPage<ReconciliatioMediationVO> getReconciliatioMediationPage(Page page, ReconciliatioMediationDTO reconciliatioMediationDTO) {
		IPage<ReconciliatioMediationVO> reconciliatioMediationPage = this.baseMapper.getReconciliatioMediationPage(page, reconciliatioMediationDTO);
		for (ReconciliatioMediationVO reconciliatioMediationVO : reconciliatioMediationPage.getRecords()) {
			for (FulfillmentRecordsVO fulfillmentRecordsVO : reconciliatioMediationVO.getFulfillmentRecordsList()) {
				if (fulfillmentRecordsVO.getPaymentAmount()!=null){
					reconciliatioMediationVO.setBalance(reconciliatioMediationVO.getAmount().subtract(fulfillmentRecordsVO.getPaymentAmount()));
				}else {
					reconciliatioMediationVO.setBalance(reconciliatioMediationVO.getAmount());
				}
			}
		}
		return reconciliatioMediationPage;
	}

	@Override
	public ReconciliatioMediationVO getByReconciliatioMediationId(Integer reconciliatioMediationId) {
		ReconciliatioMediationVO reconciliatioMediationVO = this.baseMapper.getByReconciliatioMediationId(reconciliatioMediationId);
		BigDecimal amount = reconciliatioMediationVO.getAmount();
		for (FulfillmentRecordsVO fulfillmentRecordsVO : reconciliatioMediationVO.getFulfillmentRecordsList()) {
			if (fulfillmentRecordsVO.getPaymentAmount()!=null){
				amount=amount.subtract(fulfillmentRecordsVO.getPaymentAmount());
			}else {
				amount=reconciliatioMediationVO.getAmount();
			}
			reconciliatioMediationVO.setBalance(amount);
		}
		return reconciliatioMediationVO;
	}

	@Override
	@Transactional
	public boolean saveReconciliatioMediation(ReconciliatioMediationDTO reconciliatioMediationDTO) {
		//添加和解/调解信息
		this.save(reconciliatioMediationDTO);

		//修改项目状态为和解
		ProjectModifyStatusDTO projectModifyStatusDTO=new ProjectModifyStatusDTO();
		projectModifyStatusDTO.setProjectId(reconciliatioMediationDTO.getProjectId());
		projectModifyStatusDTO.setStatus(3000);
		projectModifyStatusDTO.setStatusName("和解");
		projectLiquiService.modifyStatusById(projectModifyStatusDTO);

		for (FulfillmentRecords fulfillmentRecords : reconciliatioMediationDTO.getFulfillmentRecordsList()) {
			fulfillmentRecords.setReconciliatioMediationId(reconciliatioMediationDTO.getReconciliatioMediationId());
		}
		//添加待履行记录信息
		return fulfillmentRecordsService.saveBatch(reconciliatioMediationDTO.getFulfillmentRecordsList());
	}

	@Override
	public boolean cancellation(ReconciliatioMediationLiqui reconciliatioMediation) {
		//如果和解/调解记录作废修改待履行记录还未履行的数据为作废
		List<FulfillmentRecords> fulfillmentRecordsList = fulfillmentRecordsService.list(new LambdaQueryWrapper<FulfillmentRecords>().eq(FulfillmentRecords::getReconciliatioMediationId, reconciliatioMediation.getReconciliatioMediationId()).ne(FulfillmentRecords::getStatus,1));
		for (FulfillmentRecords fulfillmentRecords : fulfillmentRecordsList) {
			fulfillmentRecords.setStatus(2);
		}
		fulfillmentRecordsService.updateBatchById(fulfillmentRecordsList);

		//修改项目状态为在办
		ProjectModifyStatusDTO projectModifyStatusDTO=new ProjectModifyStatusDTO();
		projectModifyStatusDTO.setProjectId(reconciliatioMediation.getProjectId());
		projectModifyStatusDTO.setStatus(1000);
		projectModifyStatusDTO.setStatusName("在办");
		projectLiquiService.modifyStatusById(projectModifyStatusDTO);

		return this.updateById(reconciliatioMediation);
	}
}
