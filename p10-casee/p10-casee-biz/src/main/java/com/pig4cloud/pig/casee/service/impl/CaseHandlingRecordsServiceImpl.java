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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.CaseeHandlingRecordsDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.mapper.CaseeHandlingRecordsMapper;
import com.pig4cloud.pig.casee.service.CaseeHandlingRecordsReService;
import com.pig4cloud.pig.casee.service.CaseeHandlingRecordsService;
import com.pig4cloud.pig.casee.service.LiquiTransferRecordService;
import com.pig4cloud.pig.casee.vo.CaseeHandlingRecordsVO;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件办理记录表
 *
 * @author Mjh
 * @date 2022-03-10 18:05:58
 */
@Service
public class CaseHandlingRecordsServiceImpl extends ServiceImpl<CaseeHandlingRecordsMapper, CaseeHandlingRecords> implements CaseeHandlingRecordsService {
	@Autowired
	CaseeHandlingRecordsReService caseeHandlingRecordsReService;
	@Autowired
	SecurityUtilsService securityUtilsService;
	@Autowired
	LiquiTransferRecordService liquiTransferRecordService;

	@Override
	public List<CaseeHandlingRecordsVO> queryCaseeHandlingRecords(CaseeHandlingRecordsDTO caseeHandlingRecordsDTO) {
		return this.baseMapper.queryCaseeHandlingRecords(caseeHandlingRecordsDTO);
	}

	@Override
	public boolean addCaseeHandlingRecords(Integer assetsId, TaskNode taskNode,Integer auctionType) {
		//添加任务办理记录
		CaseeHandlingRecords caseeHandlingRecords=new CaseeHandlingRecords();
		BeanUtils.copyProperties(taskNode,caseeHandlingRecords);
		caseeHandlingRecords.setCreateTime(LocalDateTime.now());
		caseeHandlingRecords.setInsId(securityUtilsService.getCacheUser().getInsId());
		caseeHandlingRecords.setOutlesId(securityUtilsService.getCacheUser().getOutlesId());
		caseeHandlingRecords.setSourceId(assetsId);
		caseeHandlingRecords.setSourceType(0);
		if (auctionType.equals(100)){
			caseeHandlingRecords.setNodeName(caseeHandlingRecords.getNodeName()+"(一拍)");
		}else if (auctionType.equals(200)){
			caseeHandlingRecords.setNodeName(caseeHandlingRecords.getNodeName()+"(二拍)");
		}else if (auctionType.equals(300)){
			caseeHandlingRecords.setNodeName(caseeHandlingRecords.getNodeName()+"(变卖)");
		}
		this.save(caseeHandlingRecords);

		//根据拍辅项目id和财产id查询清收移交记录信息
		LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), assetsId);

		//添加任务办理记录关联信息
		CaseeHandlingRecordsRe caseeHandlingRecordsRe=new CaseeHandlingRecordsRe();
		caseeHandlingRecordsRe.setCaseeHandlingRecordsId(caseeHandlingRecords.getCaseeHandlingRecordsId());
		if (liquiTransferRecord!=null){//如果是清收移交过来的财产会有移交记录  如果是拍辅这边加的财产那么没有拍卖记录
			caseeHandlingRecordsRe.setProjectId(liquiTransferRecord.getProjectId());
			caseeHandlingRecordsRe.setProjectType(100);
		}
		return caseeHandlingRecordsReService.save(caseeHandlingRecordsRe);
	}
}
