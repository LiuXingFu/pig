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

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.paifu.AuctionRecordSaveDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionRecordStatusSaveDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionResultsSaveDTO;
import com.pig4cloud.pig.casee.entity.AuctionRecord;
import com.pig4cloud.pig.casee.vo.paifu.AuctionDetailVO;

/**
 * 拍卖记录表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
public interface AuctionRecordService extends IService<AuctionRecord> {

	/**
	 * 添加拍卖记录
	 * @param auctionRecordSaveDTO
	 * @return
	 */
	Integer saveAuctionRecord(AuctionRecordSaveDTO auctionRecordSaveDTO);

	/**
	 * 添加拍卖结果
	 * @param auctionResultsSaveDTO
	 */
	void saveAuctionResults(AuctionResultsSaveDTO auctionResultsSaveDTO);

	/**
	 * 根据项目id、案件id、财产id查询最后一条拍卖记录
	 * @param projectId
	 * @param caseeId
	 * @param assetsId
	 * @return
	 */
	AuctionRecord getLastAuctionRecord(Integer projectId,Integer caseeId,Integer assetsId);

	/**
	 * 撤销拍卖记录
	 * @param auctionRecordStatusSaveDTO
	 * @return
	 */
	Integer revokeAuctionRecord(AuctionRecordStatusSaveDTO auctionRecordStatusSaveDTO);

}
