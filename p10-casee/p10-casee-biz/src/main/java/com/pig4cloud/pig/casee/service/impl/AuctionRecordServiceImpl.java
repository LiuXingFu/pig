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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.paifu.AuctionRecordSaveDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionResultsSaveDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.mapper.AuctionRecordMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 拍卖记录表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
@Service
public class AuctionRecordServiceImpl extends ServiceImpl<AuctionRecordMapper, AuctionRecord> implements AuctionRecordService {
	@Autowired
	AuctionService auctionService;
	@Autowired
	AuctionRecordStatusService auctionRecordStatusService;
	@Autowired
	AuctionAssetsReService auctionAssetsReService;
	@Autowired
	AuctionResultsService auctionResultsService;


	@Override
	@Transactional
	public Integer saveAuctionRecord(AuctionRecordSaveDTO auctionRecordSaveDTO) {
		// 保存拍卖信息
		Auction auction = new Auction();
		BeanCopyUtil.copyBean(auctionRecordSaveDTO, auction);
		auction.setAuctionStatus(100);
		auctionService.saveOrUpdate(auction);

		List<AuctionAssetsRe> auctionAssetsRes = new ArrayList<>();
		if (auctionRecordSaveDTO.getAssetsReIdList().size() > 0) {
			if (auctionRecordSaveDTO.getAuctionId() != null) {
				// 已存在记录先删除财产关联
				QueryWrapper<AuctionAssetsRe> queryWrapper = new QueryWrapper<>();
				queryWrapper.lambda().eq(AuctionAssetsRe::getAuctionId, auctionRecordSaveDTO.getAuctionId());
				auctionAssetsReService.remove(queryWrapper);
			}
			// 保存拍卖财产关联
			for (Integer assetsId : auctionRecordSaveDTO.getAssetsReIdList()) {
				AuctionAssetsRe auctionAssetsRe = new AuctionAssetsRe();
				auctionAssetsRe.setAssetsReId(assetsId);
				auctionAssetsRe.setAuctionId(auction.getAuctionId());
				auctionAssetsRes.add(auctionAssetsRe);
			}
			auctionAssetsReService.saveBatch(auctionAssetsRes);
		}

		// 保存拍卖记录
		AuctionRecord auctionRecord = new AuctionRecord();
		BeanCopyUtil.copyBean(auctionRecordSaveDTO, auctionRecord);
		Integer save = this.baseMapper.insert(auctionRecord);
		// 保存拍卖记录状态
		AuctionRecordStatus auctionRecordStatus = new AuctionRecordStatus();
		auctionRecordStatus.setAuctionRecordId(auctionRecord.getAuctionRecordId());
		auctionRecordStatus.setStatus(100);
		auctionRecordStatus.setChangeTime(auctionRecordSaveDTO.getAnnouncementStartTime());
		auctionRecordStatusService.save(auctionRecordStatus);

		return save;
	}

	@Override
	@Transactional
	public void saveAuctionResults(AuctionResultsSaveDTO auctionResultsSaveDTO) {
		// 更新拍卖记录状态
		AuctionRecord auctionRecord = new AuctionRecord();
		auctionRecord.setAuctionRecordId(auctionResultsSaveDTO.getAuctionRecordId());
		Integer resultsType = auctionResultsSaveDTO.getResultsType();
		Integer auctionStatus = null;
		if (resultsType == 10 || resultsType == 20 || resultsType == 50) {
			auctionStatus = 300;
		} else if (resultsType == 30) {
			auctionStatus = 500;
		} else if (resultsType == 40) {
			auctionStatus = 400;
		}
		auctionRecord.setAuctionStatus(auctionStatus);
		this.baseMapper.updateById(auctionRecord);

		// 更新拍卖表当前拍卖状态
		Auction auction = new Auction();
		auction.setAuctionId(auctionResultsSaveDTO.getAuctionId());
		auction.setAuctionStatus(auctionStatus);
		auctionService.updateById(auction);

		// 保存拍卖记录状态
		AuctionRecordStatus auctionRecordStatus = new AuctionRecordStatus();
		auctionRecordStatus.setAuctionRecordId(auctionResultsSaveDTO.getAuctionRecordId());
		auctionRecordStatus.setStatus(auctionStatus);
		auctionRecordStatus.setChangeTime(auctionResultsSaveDTO.getResultsTime());
		auctionRecordStatusService.save(auctionRecordStatus);
		// 保存拍卖结果
		AuctionResults auctionResults = new AuctionResults();
		BeanCopyUtil.copyBean(auctionResultsSaveDTO, auctionResults);
		auctionResultsService.save(auctionResults);
	}
}
