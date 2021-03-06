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
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.Auction;
import com.pig4cloud.pig.casee.mapper.AuctionMapper;
import com.pig4cloud.pig.casee.service.AssetsReService;
import com.pig4cloud.pig.casee.service.AuctionAssetsReService;
import com.pig4cloud.pig.casee.service.AuctionService;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePaifuDetailVO;
import com.pig4cloud.pig.casee.vo.paifu.AuctionDetailVO;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 拍卖表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
@Service
public class AuctionServiceImpl extends ServiceImpl<AuctionMapper, Auction> implements AuctionService {
	@Autowired
	AssetsReService assetsReService;
	@Autowired
	AuctionAssetsReService auctionAssetsReService;


	@Override
	public AuctionDetailVO queryAuctionByProjectIdCaseeId(Integer projectId, Integer caseeId, Integer assetsId){
		QueryWrapper<AssetsRe> assetsReQueryWrapper = new QueryWrapper<>();
		assetsReQueryWrapper.lambda().eq(AssetsRe::getProjectId,projectId);
		assetsReQueryWrapper.lambda().eq(AssetsRe::getCaseeId,caseeId);
		assetsReQueryWrapper.lambda().eq(AssetsRe::getAssetsId,assetsId);
		AssetsRe assetsRe = assetsReService.getOne(assetsReQueryWrapper);
		AuctionDetailVO auctionDetailVO = this.baseMapper.getByAssetsReId(projectId,assetsRe.getAssetsReId());
		if(auctionDetailVO!=null){
			List<AssetsRePaifuDetailVO> assetsReList = auctionAssetsReService.queryAssetsReByAuctionId(auctionDetailVO.getAuctionId());
			auctionDetailVO.setAssetsReList(assetsReList);
		}
		return auctionDetailVO;
	}
}
