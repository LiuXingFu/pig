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
import com.pig4cloud.pig.casee.dto.TransferRecordDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.entity.liquientity.TransferRecordLiqui;
import com.pig4cloud.pig.casee.vo.AssetsInformationVO;
import com.pig4cloud.pig.casee.vo.MortgageAssetsRecordsVO;
import com.pig4cloud.pig.casee.vo.MortgageAssetsVO;
import com.pig4cloud.pig.casee.vo.TransferRecordBankLoanVO;

import java.util.List;

/**
 * 移交记录表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:40
 */
public interface TransferRecordLiquiService extends IService<TransferRecord> {

	IPage<TransferRecordBankLoanVO> getTransferRecordPage(Page page, TransferRecordDTO transferRecordDTO);

	List<TransferRecordLiqui> getBankLoanIdTransferRecord(Integer sourceId);

	TransferRecordBankLoanVO getTransferRecordBankLoan(Integer transferRecordId,Integer projectId);

	boolean reception(TransferRecordDTO transferRecordDTO);

	List<AssetsInformationVO> getProjectIdByAssets(Integer projectId);

	List<MortgageAssetsVO> getProjectIdByMortgageAssets(Integer projectId);

	/**
	 * 根据项目id查询银行借贷移交详情
	 * @param projectId
	 * @return
	 */
	TransferRecordLiqui getByProjectId(Integer projectId,Integer transferType);

}
