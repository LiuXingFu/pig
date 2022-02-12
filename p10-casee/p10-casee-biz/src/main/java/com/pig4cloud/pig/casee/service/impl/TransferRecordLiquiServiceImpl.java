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

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.ProjectLiquiAddDTO;
import com.pig4cloud.pig.casee.dto.TransferRecordDTO;
import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.entity.liquientity.TransferRecordLiqui;
import com.pig4cloud.pig.casee.mapper.TransferRecordLiquiMapper;
import com.pig4cloud.pig.casee.service.ProjectLiquiService;
import com.pig4cloud.pig.casee.service.TransferRecordLiquiService;
import com.pig4cloud.pig.casee.vo.AssetsInformationVO;
import com.pig4cloud.pig.casee.vo.TransferRecordBankLoanVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public List<TransferRecordBankLoanVO> getTransferRecordPage(Page page, TransferRecord transferRecord) {
		if (transferRecord.getHandoverTime()!=null){
			String s =transferRecord.getHandoverTime().toString();
		}
		return this.baseMapper.getTransferRecordPage(page,transferRecord);
	}

	@Override
	public List<TransferRecord> getBankLoanIdTransferRecord(Integer sourceId) {
		return this.baseMapper.getBankLoanIdTransferRecord(sourceId,0);
	}

	@Override
	public TransferRecordBankLoanVO getTransferRecordBankLoan(Integer transferRecordId,Integer projectId) {
		return this.baseMapper.getTransferRecordBankLoan(transferRecordId,projectId);
	}

	@Override
	public boolean reception(TransferRecordDTO transferRecordDTO) {
		ProjectLiquiAddDTO projectLiquiAddDTO =new ProjectLiquiAddDTO();
		BeanUtils.copyProperties(transferRecordDTO,projectLiquiAddDTO);
		TransferRecordLiqui transferRecordLiqui=new TransferRecordLiqui();
		BeanUtils.copyProperties(transferRecordDTO,transferRecordLiqui);
		//添加项目信息
		Integer projectId = projectLiquiService.addProjectLiqui(projectLiquiAddDTO);
		transferRecordLiqui.setProjectId(projectId);
		return this.updateById(transferRecordLiqui);
	}

	@Override
	public List<AssetsInformationVO> getProjectIdByAssets(Integer projectId) {
		return this.baseMapper.getProjectIdByAssets(projectId);
	}
}
