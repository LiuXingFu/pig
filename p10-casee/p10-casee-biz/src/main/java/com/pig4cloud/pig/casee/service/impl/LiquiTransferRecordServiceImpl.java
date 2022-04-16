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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.AssetsReDTO;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.UpdateLiquiTransferRecordDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ;
import com.pig4cloud.pig.casee.mapper.LiquiTransferRecordMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.LiquiTransferRecordDetailsVO;
import com.pig4cloud.pig.casee.vo.LiquiTransferRecordVO;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

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

	@Autowired
	private AssetsLiquiTransferRecordReService assetsLiquiTransferRecordReService;

	@Autowired
	TaskNodeService taskNodeService;

	@Autowired
	TargetService targetService;

	@Autowired
	CaseeHandlingRecordsService caseeHandlingRecordsService;

	@Autowired
	SecurityUtilsService securityUtilsService;

	@Override
	public IPage<LiquiTransferRecordVO> queryLiquiTransferRecordPage(Page page, LiquiTransferRecord liquiTransferRecord) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryLiquiTransferRecordPage(page, liquiTransferRecord, insOutlesDTO);
	}

	@Override
	public LiquiTransferRecordDetailsVO getByLiquiTransferRecordId(Integer liquiTransferRecordId) {
		LiquiTransferRecordDetailsVO liquiTransferRecordDetailsVO = this.baseMapper.getByLiquiTransferRecordId(liquiTransferRecordId);
		if (Objects.nonNull(liquiTransferRecordDetailsVO.getAuctionApplicationFile())) {
			liquiTransferRecordDetailsVO.setAuctionApplicationFile(liquiTransferRecordDetailsVO.getAuctionApplicationFile().substring(1, liquiTransferRecordDetailsVO.getAuctionApplicationFile().length() - 1));
		}
		return liquiTransferRecordDetailsVO;
	}

	@Override
	public List<LiquiTransferRecordVO> queryTransferRecord(Integer caseeId) {
		return this.baseMapper.queryTransferRecord(caseeId);
	}

	/**
	 * 再次移送
	 * @param updateLiquiTransferRecordDTO
	 * @return
	 */
	@Override
	@Transactional
	public int updateLiquiTransferRecord(UpdateLiquiTransferRecordDTO updateLiquiTransferRecordDTO) {

		int update = 0;

		//创建一条新的移送信息
		LiquiTransferRecord liquiTransferRecord = new LiquiTransferRecord();

		BeanUtils.copyProperties(updateLiquiTransferRecordDTO, liquiTransferRecord);

		this.save(liquiTransferRecord);

		//将新的移送id与财产保存进财产关联清收移交记录表和将财产的节点数据更新
		for (AssetsReDTO assetsReDTO : updateLiquiTransferRecordDTO.getAssetsReDTOList()) {
			AssetsLiquiTransferRecordRe assetsLiquiTransferRecordRe = new AssetsLiquiTransferRecordRe();
			assetsLiquiTransferRecordRe.setLiquiTransferRecordId(liquiTransferRecord.getLiquiTransferRecordId());
			assetsLiquiTransferRecordRe.setAssetsReId(assetsReDTO.getAssetsReId());
			assetsLiquiTransferRecordReService.save(assetsLiquiTransferRecordRe);

			//查询该财产程序信息
			Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getCaseeId, assetsReDTO.getCaseeId()).eq(Target::getGoalId, assetsReDTO.getAssetsId()).eq(Target::getGoalType, 20001).eq(Target::getProcedureNature, 4040));

			TaskNode entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, assetsReDTO.getProjectId()).eq(TaskNode::getCaseeId, assetsReDTO.getCaseeId()).eq(TaskNode::getTargetId, target.getTargetId())
					.eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ"));

			//资产处置移交
			EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ entityZX_stzx_cczxzcczyj_cczxzcczyj = JsonUtils.jsonToPojo(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getFormData(), EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.class);

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setAssetsReDTOList(updateLiquiTransferRecordDTO.getAssetsReDTOList());

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setApplicationSubmissionTime(updateLiquiTransferRecordDTO.getApplicationSubmissionTime());

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setAuctionApplicationFile(updateLiquiTransferRecordDTO.getAuctionApplicationFile());

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setHandoverTime(updateLiquiTransferRecordDTO.getHandoverTime());

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setEntrustedInsId(updateLiquiTransferRecordDTO.getEntrustedInsId());

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setEntrustedOutlesId(updateLiquiTransferRecordDTO.getEntrustedOutlesId());

			if (entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getStatus() != 101) {
				//添加任务办理记录
				CaseeHandlingRecords caseeHandlingRecords=new CaseeHandlingRecords();
				BeanUtils.copyProperties(assetsReDTO,caseeHandlingRecords);
				caseeHandlingRecords.setNodeName(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getNodeName());
				caseeHandlingRecords.setFormData(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getFormData());
				caseeHandlingRecords.setTargetId(target.getTargetId());
				caseeHandlingRecords.setSourceId(assetsReDTO.getAssetsReId());
				caseeHandlingRecords.setSourceType(0);
				caseeHandlingRecords.setNodeId(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getNodeId());
				caseeHandlingRecords.setInsId(securityUtilsService.getCacheUser().getInsId());
				caseeHandlingRecords.setOutlesId(securityUtilsService.getCacheUser().getOutlesId());
				caseeHandlingRecords.setSubmissionStatus(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getSubmissionStatus());
				caseeHandlingRecordsService.save(caseeHandlingRecords);
			}

			entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.setStatus(101);

			entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.setSubmissionStatus(0);

			taskNodeService.updateById(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ);
		}

		return update += 1;
	}
}
