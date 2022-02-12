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
import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.mapper.TransferRecordLiquiMapper;
import com.pig4cloud.pig.casee.mapper.TransferRecordMapper;
import com.pig4cloud.pig.casee.service.TransferRecordLiquiService;
import com.pig4cloud.pig.casee.service.TransferRecordService;
import com.pig4cloud.pig.casee.vo.TransferRecordBankLoanVO;
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

	@Override
	public List<TransferRecordBankLoanVO> getTransferRecordPage(Page page, TransferRecord transferRecord) {
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
}
