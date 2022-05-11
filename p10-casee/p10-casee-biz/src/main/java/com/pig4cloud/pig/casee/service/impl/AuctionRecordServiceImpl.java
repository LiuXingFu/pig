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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.AssetsReDTO;
import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionRecordSaveDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionRecordStatusSaveDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionResultsSaveDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.paifuentity.AssetsRePaifu;
import com.pig4cloud.pig.casee.mapper.AuctionRecordMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
	@Autowired
	AssetsRePaifuService assetsRePaifuService;
	@Autowired
	AuctionRecordAssetsReService auctionRecordAssetsReService;
	@Autowired
	TargetService targetService;
	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	AssetsReService assetsReService;

	@Override
	@Transactional
	public AuctionRecord saveAuctionRecord(AuctionRecordSaveDTO auctionRecordSaveDTO) {
		Integer jointAuction = 1;
		if(auctionRecordSaveDTO.getAssetsReIdList().size()>1){
			jointAuction = 2;
		}
		// 判断发布拍卖时间是否小于当前时间，true的话将拍卖状态set为200：正在进行中
		Integer auctionStatus = 100;
		LocalDate now =LocalDate.now();
		if(now.isBefore(auctionRecordSaveDTO.getAnnouncementStartTime())){
			auctionStatus = 200;
		}
		// 保存拍卖信息
		Auction auction = new Auction();
		BeanCopyUtil.copyBean(auctionRecordSaveDTO, auction);
		auction.setAuctionStatus(auctionStatus);
		auction.setJointAuction(jointAuction);
		auctionService.saveOrUpdate(auction);

		// 保存拍卖记录
		AuctionRecord auctionRecord = new AuctionRecord();
		BeanCopyUtil.copyBean(auctionRecordSaveDTO, auctionRecord);
		auctionRecord.setJointAuction(jointAuction);
		auctionRecord.setAuctionId(auction.getAuctionId());
		auctionRecord.setAuctionStatus(auctionStatus);
		this.baseMapper.insert(auctionRecord);

		List<AuctionAssetsRe> auctionAssetsRes = new ArrayList<>();
		List<AuctionRecordAssetsRe> auctionRecordAssetsRes = new ArrayList<>();
		if (auctionRecordSaveDTO.getAssetsReIdList().size() > 0) {
			if (auctionRecordSaveDTO.getAuctionId() != null) {
				// 已存在记录先删除财产关联
				QueryWrapper<AuctionAssetsRe> queryWrapper = new QueryWrapper<>();
				queryWrapper.lambda().eq(AuctionAssetsRe::getAuctionId, auctionRecordSaveDTO.getAuctionId());
				auctionAssetsReService.remove(queryWrapper);
			}
			// 保存拍卖财产关联
			for (AssetsReDTO assetsReDTO : auctionRecordSaveDTO.getAssetsReIdList()) {
				AuctionAssetsRe auctionAssetsRe = new AuctionAssetsRe();
				auctionAssetsRe.setAssetsReId(assetsReDTO.getAssetsReId());
				auctionAssetsRe.setAuctionId(auction.getAuctionId());
				auctionAssetsRes.add(auctionAssetsRe);
				// 更新项目案件财产关联表状态为拍卖中
				AssetsRePaifu assetsRePaifu = new AssetsRePaifu();
				assetsRePaifu.setAssetsReId(assetsReDTO.getAssetsReId());
				assetsRePaifu.setStatus(200);
				assetsRePaifuService.updateById(assetsRePaifu);

				AuctionRecordAssetsRe auctionRecordAssetsRe = new AuctionRecordAssetsRe();
				auctionRecordAssetsRe.setAssetsReId(assetsReDTO.getAssetsReId());
				auctionRecordAssetsRe.setAuctionRecordId(auctionRecord.getAuctionRecordId());
				auctionRecordAssetsRes.add(auctionRecordAssetsRe);
			}
			auctionAssetsReService.saveBatch(auctionAssetsRes);
			auctionRecordAssetsReService.saveBatch(auctionRecordAssetsRes);
		}

		// 保存拍卖记录状态
		AuctionRecordStatus auctionRecordStatus = new AuctionRecordStatus();
		auctionRecordStatus.setAuctionRecordId(auctionRecord.getAuctionRecordId());
		auctionRecordStatus.setStatus(auctionStatus);
		auctionRecordStatus.setChangeTime(auctionRecordSaveDTO.getAnnouncementStartTime());
		auctionRecordStatusService.save(auctionRecordStatus);

		return auctionRecord;
	}

	@Override
	@Transactional
	public void saveAuctionResults(AuctionResultsSaveDTO auctionResultsSaveDTO) {
		// 更新拍卖记录状态
		AuctionRecord auctionRecord = new AuctionRecord();
		auctionRecord.setAuctionRecordId(auctionResultsSaveDTO.getAuctionRecordId());
		Integer resultsType = auctionResultsSaveDTO.getResultsType();
		Integer auctionStatus = null;
		if (resultsType == 10) {
			auctionStatus = 300;
		} else if (resultsType == 20) {
			auctionStatus = 600;
		}else if (resultsType == 30) {
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

	@Override
	public AuctionRecord getLastAuctionRecord(Integer projectId,Integer caseeId,Integer assetsId){
		return this.baseMapper.getLastAuctionRecord(projectId,caseeId,assetsId);
	}

	@Override
	@Transactional
	public Integer revokeAuctionRecord(AuctionRecordStatusSaveDTO auctionRecordStatusSaveDTO){
		// 保存拍卖记录状态表
		AuctionRecordStatus auctionRecordStatus = new AuctionRecordStatus();
		BeanCopyUtil.copyBean(auctionRecordStatusSaveDTO,auctionRecordStatus);
		auctionRecordStatusService.save(auctionRecordStatus);
		// 更新拍卖记录状态
		AuctionRecord auctionRecord = new AuctionRecord();
		auctionRecord.setAuctionRecordId(auctionRecordStatusSaveDTO.getAuctionRecordId());
		auctionRecord.setAuctionStatus(auctionRecordStatusSaveDTO.getStatus());

		AuctionResults auctionResults = new AuctionResults();
		auctionResults.setAuctionRecordId(auctionRecordStatusSaveDTO.getAuctionRecordId());
		auctionResults.setResultsTime(auctionRecordStatusSaveDTO.getChangeTime());
		auctionResults.setAppendix(auctionRecordStatusSaveDTO.getAppendix());
		auctionResults.setRemark(auctionRecordStatusSaveDTO.getRemark());
		auctionResults.setResultsType(500);
		auctionResultsService.save(auctionResults);

		// 更新拍卖表当前状态
		AuctionRecord auctionRecordDetail = this.baseMapper.selectById(auctionRecordStatusSaveDTO.getAuctionRecordId());
		Auction auction = new Auction();
		auction.setAuctionId(auctionRecordDetail.getAuctionId());
		auction.setAuctionStatus(auctionRecordStatusSaveDTO.getStatus());
		auctionService.updateById(auction);

		QueryWrapper<AuctionRecordAssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(AuctionRecordAssetsRe::getAuctionRecordId,auctionRecordStatusSaveDTO.getAuctionRecordId());
		List<AuctionRecordAssetsRe> auctionRecordAssetsRes = auctionRecordAssetsReService.list(queryWrapper);
		List<AssetsRe> assetsRes = new ArrayList<>();
		List<Integer> assetsReIdList = new ArrayList<>();
		// 更新项目案件财产关联表状态
		for(AuctionRecordAssetsRe auctionRecordAssetsRe : auctionRecordAssetsRes){
			AssetsRe assetsRe = new AssetsRe();
			assetsRe.setAssetsReId(auctionRecordAssetsRe.getAssetsReId());
			assetsRe.setStatus(100);
			assetsRes.add(assetsRe);

			Integer assetsReId = auctionRecordAssetsRe.getAssetsReId();
			assetsReIdList.add(assetsReId);
		}
		assetsRePaifuService.updateBatchById(assetsRes);

		if (auctionRecordStatusSaveDTO.getProjectType()==100){//清收撤销
			Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getProjectId, auctionRecordStatusSaveDTO.getProjectId()).eq(Target::getCaseeId, auctionRecordStatusSaveDTO.getCaseeId()).eq(Target::getGoalId, auctionRecordStatusSaveDTO.getAssetsId()).eq(Target::getGoalType, 20001).eq(Target::getProcedureNature, 4040));

			//查询最后一条拍卖公告信息
			TaskNode entityZX_stzx_cczxpmgg_cczxpmgg = taskNodeService.queryLastTaskNode("entityZX_STZX_CCZXPMGG_CCZXPMGG",target.getTargetId());
			if (entityZX_stzx_cczxpmgg_cczxpmgg!=null){
				//复制拍卖公告节点
				taskNodeService.copyTaskNode(entityZX_stzx_cczxpmgg_cczxpmgg);
			}

			//查询最后一条拍卖结果信息
			TaskNode entityZX_stzx_cczxpmjg_cczxpmjg = taskNodeService.queryLastTaskNode("entityZX_STZX_CCZXPMJG_CCZXPMJG",target.getTargetId());
			if (entityZX_stzx_cczxpmjg_cczxpmjg!=null){
				//复制拍卖结果节点
				taskNodeService.copyTaskNode(entityZX_stzx_cczxpmjg_cczxpmjg);
			}
		}else {//如果是拍辅撤销 同步联合拍卖财产程序
			List<JointAuctionAssetsDTO> jointAuctionAssetsDTOList = assetsReService.queryByAssetsReIdList(assetsReIdList);
			taskNodeService.auctionResultsCopyTaskNode(jointAuctionAssetsDTOList,auctionRecordStatusSaveDTO.getProjectType());
		}

		return this.baseMapper.updateById(auctionRecord);
	}

	@Override
	@Transactional
	public void refreshAuctionStatus(){

		List<AuctionRecord> auctionRecordsList = this.baseMapper.selectAcutionRecordStatus();
		Integer auctionStatus = 0;

		List<AuctionRecordStatus> auctionRecordStatuses = new ArrayList<>();
		List<AuctionRecord> auctionRecords = new ArrayList<>();
		List<Auction> auctions = new ArrayList<>();
		for(AuctionRecord auctionRecord : auctionRecordsList){
			LocalDate changeTime = auctionRecord.getAuctionStartTime();
			if(auctionRecord.getAuctionStatus()==100){
				auctionStatus = 200;
			}else if(auctionRecord.getAuctionStatus()==200){
				auctionStatus = 300;
				changeTime = auctionRecord.getAuctionEndTime();
			}
			AuctionRecord updateAuctionRecord = new AuctionRecord();
			updateAuctionRecord.setAuctionRecordId(auctionRecord.getAuctionRecordId());
			updateAuctionRecord.setAuctionStatus(auctionStatus);
			auctionRecords.add(updateAuctionRecord);

			AuctionRecordStatus auctionRecordStatus = new AuctionRecordStatus();
			auctionRecordStatus.setAuctionRecordId(auctionRecord.getAuctionRecordId());
			auctionRecordStatus.setStatus(auctionStatus);
			auctionRecordStatus.setChangeTime(changeTime);
			auctionRecordStatuses.add(auctionRecordStatus);

			Auction auction = new Auction();
			auction.setAuctionId(auctionRecord.getAuctionId());
			auction.setAuctionStatus(auctionStatus);
			auctions.add(auction);
		}
		this.updateBatchById(auctionRecords);
		auctionRecordStatusService.saveBatch(auctionRecordStatuses);
		auctionService.updateBatchById(auctions);
	}
}
