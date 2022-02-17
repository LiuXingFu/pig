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
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.TransferRecordDTO;
import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.entity.liquientity.TransferRecordLiqui;
import com.pig4cloud.pig.casee.vo.AssetsInformationVO;
import com.pig4cloud.pig.casee.vo.TransferRecordBankLoanVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 移交记录表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:40
 */
@Mapper
public interface TransferRecordLiquiMapper extends BaseMapper<TransferRecord> {

	List<TransferRecordBankLoanVO> getTransferRecordPage(Page page,@Param("query") TransferRecordDTO transferRecordDTO);

	List<TransferRecordLiqui> getBankLoanIdTransferRecord(@Param("sourceId")Integer sourceId, @Param("transferType") Integer transferType);

	TransferRecordBankLoanVO getTransferRecordBankLoan(@Param("transferRecordId") Integer transferRecordId, @Param("projectId")Integer projectId);

	List<AssetsInformationVO> getProjectIdByAssets(Integer projectId);
}
