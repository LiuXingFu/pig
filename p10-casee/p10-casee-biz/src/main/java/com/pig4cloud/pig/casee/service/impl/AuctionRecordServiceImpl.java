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
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.mapper.AuctionRecordMapper;
import com.pig4cloud.pig.casee.service.AuctionAssetsReService;
import com.pig4cloud.pig.casee.service.AuctionRecordService;
import com.pig4cloud.pig.casee.service.AuctionRecordStatusService;
import com.pig4cloud.pig.casee.service.AuctionService;
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


	@Override
	@Transactional
	public 	Integer saveAuctionRecord(AuctionRecordSaveDTO auctionRecordSaveDTO){
		Auction auction = new Auction();
		BeanCopyUtil.copyBean(auctionRecordSaveDTO,auction);
		auction.setAuctionStatus(100);
		auctionService.save(auction);
		if(auctionRecordSaveDTO.getAuctionId()==null){
			QueryWrapper<AuctionAssetsRe> queryWrapper = new QueryWrapper<>();
			queryWrapper.lambda().eq(AuctionAssetsRe::getAuctionId,auctionRecordSaveDTO.getAuctionId());
			auctionAssetsReService.remove(queryWrapper);
		}
		List<AuctionAssetsRe> auctionAssetsRes = new ArrayList<>();
		for(Integer assetsId:auctionRecordSaveDTO.getAssetsReIdList()){
			AuctionAssetsRe auctionAssetsRe = new AuctionAssetsRe();
			auctionAssetsRe.setAssetsReId(assetsId);
			auctionAssetsRe.setAuctionId(auction.getAuctionId());
			auctionAssetsRes.add(auctionAssetsRe);
		}
		auctionAssetsReService.saveBatch(auctionAssetsRes);

		AuctionRecord auctionRecord = new AuctionRecord();
		BeanCopyUtil.copyBean(auctionRecordSaveDTO,auctionRecord);
		Integer save = this.baseMapper.insert(auctionRecord);

		AuctionRecordStatus auctionRecordStatus = new AuctionRecordStatus();
		auctionRecordStatus.setAuctionRecordId(auctionRecord.getAuctionRecordId());
		auctionRecordStatus.setStatus(100);
		auctionRecordStatus.setChangeTime(auctionRecordSaveDTO.getAnnouncementStartTime());
		auctionRecordStatusService.save(auctionRecordStatus);

		return save;
	}
}
