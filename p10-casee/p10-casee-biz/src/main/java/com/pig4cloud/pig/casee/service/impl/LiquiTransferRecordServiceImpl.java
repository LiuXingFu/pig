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
import com.pig4cloud.pig.casee.entity.LiquiTransferRecord;
import com.pig4cloud.pig.casee.mapper.LiquiTransferRecordMapper;
import com.pig4cloud.pig.casee.service.LiquiTransferRecordService;
import com.pig4cloud.pig.casee.vo.LiquiTransferRecordDetailsVO;
import com.pig4cloud.pig.casee.vo.LiquiTransferRecordVO;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 清收移交记录表
 *
 * @author Mjh
 * @date 2022-04-06 15:21:31
 */
@Service
public class LiquiTransferRecordServiceImpl extends ServiceImpl<LiquiTransferRecordMapper, LiquiTransferRecord> implements LiquiTransferRecordService {
	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Override
	public IPage<LiquiTransferRecordVO> queryLiquiTransferRecordPage(Page page, LiquiTransferRecord liquiTransferRecord) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryLiquiTransferRecordPage(page,liquiTransferRecord,insOutlesDTO);
	}

	@Override
	public LiquiTransferRecordDetailsVO getByLiquiTransferRecordId(Integer liquiTransferRecordId) {
		LiquiTransferRecordDetailsVO liquiTransferRecordDetailsVO = this.baseMapper.getByLiquiTransferRecordId(liquiTransferRecordId);
		liquiTransferRecordDetailsVO.setAuctionApplicationFile(liquiTransferRecordDetailsVO.getAuctionApplicationFile().substring(1,liquiTransferRecordDetailsVO.getAuctionApplicationFile().length()-1));
		return liquiTransferRecordDetailsVO;
	}
}
