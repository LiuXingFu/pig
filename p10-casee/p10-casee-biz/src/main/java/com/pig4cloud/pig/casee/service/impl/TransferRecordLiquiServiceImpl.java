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
import com.pig4cloud.pig.casee.dto.ProjectLiquiAddDTO;
import com.pig4cloud.pig.casee.dto.TransferRecordDTO;
import com.pig4cloud.pig.casee.entity.BankLoan;
import com.pig4cloud.pig.casee.entity.SubjectBankLoanRe;
import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.entity.liquientity.TransferRecordLiqui;
import com.pig4cloud.pig.casee.mapper.TransferRecordLiquiMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.AssetsInformationVO;
import com.pig4cloud.pig.casee.vo.MortgageAssetsVO;
import com.pig4cloud.pig.casee.vo.TransferRecordBankLoanVO;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 移交记录表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:40
 */
@Service
public class TransferRecordLiquiServiceImpl extends ServiceImpl<TransferRecordLiquiMapper, TransferRecord> implements TransferRecordLiquiService {
	@Autowired
	private ProjectLiquiService projectLiquiService;

	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Autowired
	private BankLoanService bankLoanService;

	@Autowired
	private ExpenseRecordService expenseRecordService;

	@Override
	public IPage<TransferRecordBankLoanVO> getTransferRecordPage(Page page, TransferRecordDTO transferRecordDTO) {
		if (transferRecordDTO.getHandoverTime() != null) {
			String andoverTime = transferRecordDTO.getHandoverTime().toString();
		}
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));

		return this.baseMapper.getTransferRecordPage(page, transferRecordDTO, insOutlesDTO);
	}

	@Override
	public List<TransferRecordLiqui> getBankLoanIdTransferRecord(Integer sourceId) {
		return this.baseMapper.getBankLoanIdTransferRecord(sourceId, 0);
	}

	@Override
	public TransferRecordBankLoanVO getTransferRecordBankLoan(Integer transferRecordId, Integer projectId) {
		return this.baseMapper.getTransferRecordBankLoan(transferRecordId, projectId);
	}

	@Override
	@Transactional
	public boolean reception(TransferRecordDTO transferRecordDTO) {
		ProjectLiquiAddDTO projectLiquiAddDTO = new ProjectLiquiAddDTO();
		BeanUtils.copyProperties(transferRecordDTO, projectLiquiAddDTO);
		TransferRecordLiqui transferRecordLiqui = new TransferRecordLiqui();
		BeanUtils.copyProperties(transferRecordDTO, transferRecordLiqui);
		projectLiquiAddDTO.setTakeTime(transferRecordDTO.getReturnTime());
		//添加项目信息
		Integer projectId = projectLiquiService.addProjectLiqui(projectLiquiAddDTO);
		transferRecordLiqui.setProjectId(projectId);
		System.out.println(transferRecordLiqui);

		expenseRecordService.addExpenseRecordByProjectIdAndExpenseRecordList(projectId, transferRecordLiqui.getTransferRecordId());

		return this.updateById(transferRecordLiqui);
	}

	@Override
	public List<AssetsInformationVO> getProjectIdByAssets(Integer projectId) {
		return this.baseMapper.getProjectIdByAssets(projectId);
	}

	@Override
	public List<MortgageAssetsVO> getProjectIdByMortgageAssets(Integer projectId) {
		return this.baseMapper.getProjectIdByMortgageAssets(projectId);
	}

	@Override
	public TransferRecordLiqui getByProjectId(Integer projectId, Integer transferType) {
		return this.baseMapper.getByProjectId(projectId, transferType);
	}

	/**
	 * 新增移交记录表
	 *
	 * @param transferRecordLiqui 移交记录表
	 * @return R
	 */
	@Override
	public Boolean saveTransferRecord(TransferRecordLiqui transferRecordLiqui) {

		//修改银行借贷信息
		BankLoan bankLoan = bankLoanService.getById(transferRecordLiqui.getSourceId());

		bankLoan.setPrincipal(transferRecordLiqui.getTransferRecordLiquiDetail().getPrincipal());
		bankLoan.setRental(transferRecordLiqui.getTransferRecordLiquiDetail().getHandoverAmount());
		bankLoan.setInterest(transferRecordLiqui.getTransferRecordLiquiDetail().getInterest());
		bankLoan.setInterestRate(transferRecordLiqui.getTransferRecordLiquiDetail().getInterestRate());

		bankLoanService.updateById(bankLoan);

		return this.save(transferRecordLiqui);
	}

	@Override
	public TransferRecordLiqui queryTransferRecordLiquiById(Integer transferRecordId) {
		return this.baseMapper.queryTransferRecordLiquiById(transferRecordId);
	}
}
