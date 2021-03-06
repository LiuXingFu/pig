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
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.ProjectModifyStatusDTO;
import com.pig4cloud.pig.casee.dto.ReconciliatioMediationDTO;
import com.pig4cloud.pig.casee.entity.FulfillmentRecords;
import com.pig4cloud.pig.casee.entity.ReconciliatioMediation;
import com.pig4cloud.pig.casee.entity.ReconciliatioMediationSubjectRe;
import com.pig4cloud.pig.casee.entity.liquientity.ReconciliatioMediationLiqui;
import com.pig4cloud.pig.casee.mapper.ReconciliatioMediationLiquiMapper;
import com.pig4cloud.pig.casee.service.FulfillmentRecordsLiquiService;
import com.pig4cloud.pig.casee.service.ProjectLiquiService;
import com.pig4cloud.pig.casee.service.ReconciliatioMediationLiquiService;
import com.pig4cloud.pig.casee.service.ReconciliatioMediationSubjectReService;
import com.pig4cloud.pig.casee.vo.FulfillmentRecordsVO;
import com.pig4cloud.pig.casee.vo.ReconciliatioMediationVO;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * ??????/?????????
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
	@Autowired
	private ReconciliatioMediationSubjectReService reconciliatioMediationSubjectReService;
	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Override
	public IPage<ReconciliatioMediationVO> getReconciliatioMediationPage(Page page, ReconciliatioMediationDTO reconciliatioMediationDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.getReconciliatioMediationPage(page, reconciliatioMediationDTO,insOutlesDTO);
	}

	@Override
	public ReconciliatioMediationVO getByReconciliatioMediationId(Integer reconciliatioMediationId) {
		return this.baseMapper.getByReconciliatioMediationId(reconciliatioMediationId);
	}

	@Override
	@Transactional
	public boolean saveReconciliatioMediation(ReconciliatioMediationDTO reconciliatioMediationDTO) {
		//????????????/????????????
		this.save(reconciliatioMediationDTO);

		//???????????????????????????
		ProjectModifyStatusDTO projectModifyStatusDTO=new ProjectModifyStatusDTO();
		projectModifyStatusDTO.setProjectId(reconciliatioMediationDTO.getProjectId());
		projectModifyStatusDTO.setChangeTime(reconciliatioMediationDTO.getReconciliatioMediationData());
		projectModifyStatusDTO.setStatus(3000);
		projectModifyStatusDTO.setStatusName("??????");
		projectLiquiService.modifyStatusById(projectModifyStatusDTO);

		//????????????/???????????????????????????
		for (Integer integer : reconciliatioMediationDTO.getSubjectIdList()) {
			ReconciliatioMediationSubjectRe reconciliatioMediationSubjectRe=new ReconciliatioMediationSubjectRe();
			reconciliatioMediationSubjectRe.setSubjectId(integer);
			reconciliatioMediationSubjectRe.setReconciliatioMediationId(reconciliatioMediationDTO.getReconciliatioMediationId());
			reconciliatioMediationSubjectReService.save(reconciliatioMediationSubjectRe);
		}

		for (FulfillmentRecords fulfillmentRecords : reconciliatioMediationDTO.getFulfillmentRecordsList()) {
			fulfillmentRecords.setReconciliatioMediationId(reconciliatioMediationDTO.getReconciliatioMediationId());
		}
		//???????????????????????????
		return fulfillmentRecordsService.saveBatch(reconciliatioMediationDTO.getFulfillmentRecordsList());
	}

	@Override
	public boolean cancellation(ReconciliatioMediationLiqui reconciliatioMediation) {
		//????????????/?????????????????????????????????????????????????????????????????????
		List<FulfillmentRecords> fulfillmentRecordsList = fulfillmentRecordsService.list(new LambdaQueryWrapper<FulfillmentRecords>().eq(FulfillmentRecords::getReconciliatioMediationId, reconciliatioMediation.getReconciliatioMediationId()).ne(FulfillmentRecords::getStatus,1));
		for (FulfillmentRecords fulfillmentRecords : fulfillmentRecordsList) {
			fulfillmentRecords.setStatus(2);
		}
		fulfillmentRecordsService.updateBatchById(fulfillmentRecordsList);

		//???????????????????????????
		ProjectModifyStatusDTO projectModifyStatusDTO=new ProjectModifyStatusDTO();
		projectModifyStatusDTO.setProjectId(reconciliatioMediation.getProjectId());
		projectModifyStatusDTO.setStatus(1000);
		projectModifyStatusDTO.setStatusName("??????");
		projectLiquiService.modifyStatusById(projectModifyStatusDTO);

		return this.updateById(reconciliatioMediation);
	}

	/**
	 * ????????????????????????
	 * @return
	 */
	@Override
	public Long queryCompareReconciliationCount() {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryCompareReconciliationCount(insOutlesDTO);
	}
}
