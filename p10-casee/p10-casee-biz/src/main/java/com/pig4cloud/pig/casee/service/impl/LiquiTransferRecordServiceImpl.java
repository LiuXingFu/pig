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
import com.pig4cloud.pig.admin.api.feign.RemoteRelationshipAuthenticateService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.dto.paifu.ProjectPaifuReceiptDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ;
import com.pig4cloud.pig.casee.mapper.LiquiTransferRecordMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.AssetsVO;
import com.pig4cloud.pig.casee.vo.LiquiTransferRecordDetailsVO;
import com.pig4cloud.pig.casee.vo.LiquiTransferRecordVO;
import com.pig4cloud.pig.casee.vo.QueryLiquiTransferRecordDetailsVO;
import com.pig4cloud.pig.casee.vo.paifu.LiquiTransferRecordAssetsDetailsVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * ?????????????????????
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

	@Autowired
	AssetsReLiquiService assetsReLiquiService;

	@Autowired
	CaseeSubjectReService caseeSubjectReService;

	@Autowired
	RemoteSubjectService remoteSubjectService;

	@Autowired
	RemoteRelationshipAuthenticateService remoteRelationshipAuthenticateService;

	@Autowired
	ProjectPaifuService projectPaifuService;

	@Autowired
	AssetsReService assetsReService;

	@Override
	public IPage<LiquiTransferRecordVO> queryLiquiTransferRecordPage(Page page, LiquiTransferRecordPageDTO liquiTransferRecordPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryLiquiTransferRecordPage(page, liquiTransferRecordPageDTO, insOutlesDTO);
	}

	/**
	 * ??????id??????????????????????????????????????????
	 *
	 * @param liquiTransferRecordId
	 * @return
	 */
	@Override
	public LiquiTransferRecordDetailsVO getByLiquiTransferRecordId(Integer liquiTransferRecordId) {
		LiquiTransferRecordDetailsVO liquiTransferRecordDetailsVO = this.baseMapper.getByLiquiTransferRecordId(liquiTransferRecordId);

		//????????????
		if (Objects.nonNull(liquiTransferRecordDetailsVO.getAuctionApplicationFile())) {
			liquiTransferRecordDetailsVO.setAuctionApplicationFile(liquiTransferRecordDetailsVO.getAuctionApplicationFile().substring(1, liquiTransferRecordDetailsVO.getAuctionApplicationFile().length() - 1));
		}

		//?????????????????????
		liquiTransferRecordDetailsVO.setCaseeApplicantList(caseeSubjectReService.querySubjectList(liquiTransferRecordDetailsVO.getCaseeId(), 0));

		//????????????????????????
		liquiTransferRecordDetailsVO.setCaseeExecutorList(caseeSubjectReService.querySubjectList(liquiTransferRecordDetailsVO.getCaseeId(), 1));

		return liquiTransferRecordDetailsVO;
	}

	/**
	 * ?????????????????????
	 * @param caseeId
	 * @return
	 */
	private String getApplicantSubjectName(Integer caseeId) {
		List<Integer> applicantSubIds = caseeSubjectReService.list(new LambdaQueryWrapper<CaseeSubjectRe>()
				.eq(CaseeSubjectRe::getDelFlag, 0).eq(CaseeSubjectRe::getCaseeId, caseeId).eq(CaseeSubjectRe::getCaseePersonnelType, 0)).stream().map(CaseeSubjectRe::getSubjectId).collect(Collectors.toList());

		return remoteSubjectService.querySubjectName(applicantSubIds, SecurityConstants.FROM).getData();
	}

	/**
	 * ????????????????????????
	 *
	 * @param caseeId
	 * @return
	 */
	private String getExecutorSubjectName(Integer caseeId) {
		List<Integer> executorIds = caseeSubjectReService.list(new LambdaQueryWrapper<CaseeSubjectRe>()
				.eq(CaseeSubjectRe::getDelFlag, 0).eq(CaseeSubjectRe::getCaseeId, caseeId).eq(CaseeSubjectRe::getCaseePersonnelType, 1)).stream().map(CaseeSubjectRe::getSubjectId).collect(Collectors.toList());

		return remoteSubjectService.querySubjectName(executorIds, SecurityConstants.FROM).getData();
	}

	@Override
	public List<LiquiTransferRecordVO> queryTransferRecord(Integer projectId, Integer caseeId, String nodeId) {
		List<LiquiTransferRecordVO> liquiTransferRecordVOS = this.baseMapper.queryTransferRecord(projectId, caseeId, nodeId);
		for (LiquiTransferRecordVO liquiTransferRecordVO : liquiTransferRecordVOS) {
			//????????????id???type?????????????????????????????????
			String executorSubjectName = getExecutorSubjectName(liquiTransferRecordVO.getCaseeId());

			liquiTransferRecordVO.setExecutorSubjectName(executorSubjectName);

			String applicantSubjectName = getApplicantSubjectName(liquiTransferRecordVO.getCaseeId());

			liquiTransferRecordVO.setApplicantSubjectName(applicantSubjectName);
		}
		return liquiTransferRecordVOS;
	}

	/**
	 * ????????????
	 *
	 * @param updateLiquiTransferRecordDTO
	 * @return
	 */
	@Override
	@Transactional
	public int updateLiquiTransferRecord(UpdateLiquiTransferRecordDTO updateLiquiTransferRecordDTO) {

		int update = 0;

//		List<AssetsLiquiTransferRecordRe> assetsLiquiTransferRecordReList = assetsLiquiTransferRecordReService.list(new LambdaQueryWrapper<AssetsLiquiTransferRecordRe>().eq(AssetsLiquiTransferRecordRe::getLiquiTransferRecordId, updateLiquiTransferRecordDTO.getLiquiTransferRecordId()));
//
//		for (AssetsLiquiTransferRecordRe assetsLiquiTransferRecordRe : assetsLiquiTransferRecordReList) {
//			AssetsRe assetsRe = assetsReLiquiService.getById(assetsLiquiTransferRecordRe.getAssetsReId());
//
//			//???????????????????????????
//			Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getCaseeId, assetsRe.getCaseeId()).eq(Target::getGoalId, assetsRe.getAssetsId()).eq(Target::getGoalType, 20001).eq(Target::getProcedureNature, 4040));
//
//			TaskNode entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, assetsRe.getProjectId()).eq(TaskNode::getCaseeId, assetsRe.getCaseeId()).eq(TaskNode::getTargetId, target.getTargetId())
//					.eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ"));
//
//			entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.setStatus(0);
//
//			taskNodeService.updateById(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ);
//
//			caseeHandlingRecordsService.remove(new LambdaQueryWrapper<CaseeHandlingRecords>()
//					.eq(CaseeHandlingRecords::getProjectId, entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getProjectId())
//					.eq(CaseeHandlingRecords::getCaseeId, entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getCaseeId())
//					.eq(CaseeHandlingRecords::getTargetId, entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getTargetId())
//					.eq(CaseeHandlingRecords::getNodeId, entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getNodeId()));
//		}

		//??????????????????????????????
		LiquiTransferRecord liquiTransferRecord = new LiquiTransferRecord();

		BeanUtils.copyProperties(updateLiquiTransferRecordDTO, liquiTransferRecord);

		liquiTransferRecord.setStatus(0);

		this.save(liquiTransferRecord);

		//???????????????id????????????????????????????????????????????????????????????????????????????????????
		for (AssetsReDTO assetsReDTO : updateLiquiTransferRecordDTO.getAssetsReDTOList()) {
			AssetsLiquiTransferRecordRe assetsLiquiTransferRecordRe = new AssetsLiquiTransferRecordRe();
			assetsLiquiTransferRecordRe.setLiquiTransferRecordId(liquiTransferRecord.getLiquiTransferRecordId());
			assetsLiquiTransferRecordRe.setAssetsReId(assetsReDTO.getAssetsReId());
			assetsLiquiTransferRecordReService.save(assetsLiquiTransferRecordRe);

			AssetsRe assetsRe = assetsReService.getById(assetsReDTO.getAssetsReId());
			assetsRe.setStatus(700);
			//???????????????????????????????????????
			assetsReService.updateById(assetsRe);

			//???????????????????????????
			Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getCaseeId, assetsReDTO.getCaseeId()).eq(Target::getGoalId, assetsReDTO.getAssetsId()).eq(Target::getGoalType, 20001).eq(Target::getProcedureNature, 4040));

			TaskNode entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, assetsReDTO.getProjectId()).eq(TaskNode::getCaseeId, assetsReDTO.getCaseeId()).eq(TaskNode::getTargetId, target.getTargetId())
					.eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ"));

			//??????????????????
			EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ entityZX_stzx_cczxzcczyj_cczxzcczyj = JsonUtils.jsonToPojo(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getFormData(), EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.class);

			if (Objects.isNull(entityZX_stzx_cczxzcczyj_cczxzcczyj)) {
				entityZX_stzx_cczxzcczyj_cczxzcczyj = new EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ();
			}

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setAssetsReDTOList(updateLiquiTransferRecordDTO.getAssetsReDTOList());

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setApplicationSubmissionTime(updateLiquiTransferRecordDTO.getApplicationSubmissionTime());

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setAuctionApplicationFile(updateLiquiTransferRecordDTO.getAuctionApplicationFile());

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setHandoverTime(updateLiquiTransferRecordDTO.getHandoverTime());

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setEntrustedInsId(updateLiquiTransferRecordDTO.getEntrustedInsId());

			entityZX_stzx_cczxzcczyj_cczxzcczyj.setEntrustedOutlesId(updateLiquiTransferRecordDTO.getEntrustedOutlesId());

			JsonUtils.objectToJsonObject(entityZX_stzx_cczxzcczyj_cczxzcczyj);

			if (entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getStatus() != 101) {
				//????????????????????????
				addCaseeHandlingRecords(assetsReDTO, target, entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ);
			}

			entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.setStatus(101);

			entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.setSubmissionStatus(0);

			entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.setFormData(JsonUtils.objectToJsonObject(entityZX_stzx_cczxzcczyj_cczxzcczyj));

			taskNodeService.updateById(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ);
		}

		return update += 1;
	}

	private void addCaseeHandlingRecords(AssetsRe assetsRe, Target target, TaskNode entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ) {
		CaseeHandlingRecords caseeHandlingRecords = new CaseeHandlingRecords();
		BeanUtils.copyProperties(assetsRe, caseeHandlingRecords);
		caseeHandlingRecords.setNodeName(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getNodeName());
		caseeHandlingRecords.setFormData(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getFormData());
		caseeHandlingRecords.setTargetId(target.getTargetId());
		caseeHandlingRecords.setSourceId(assetsRe.getAssetsReId());
		caseeHandlingRecords.setSourceType(0);
		caseeHandlingRecords.setNodeId(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getNodeId());
		caseeHandlingRecords.setInsId(securityUtilsService.getCacheUser().getInsId());
		caseeHandlingRecords.setOutlesId(securityUtilsService.getCacheUser().getOutlesId());
		caseeHandlingRecords.setSubmissionStatus(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getSubmissionStatus());
		caseeHandlingRecordsService.save(caseeHandlingRecords);
	}

	/**
	 * ??????id???????????????????????????????????????
	 * @param liquiTransferRecordId
	 * @return
	 */
	@Override
	public QueryLiquiTransferRecordDetailsVO queryByLiquiTransferRecordId(Integer liquiTransferRecordId) {
		QueryLiquiTransferRecordDetailsVO queryLiquiTransferRecordDetailsVO = this.baseMapper.queryByLiquiTransferRecordId(liquiTransferRecordId);

		if (Objects.nonNull(queryLiquiTransferRecordDetailsVO.getAuctionApplicationFile())) {
			queryLiquiTransferRecordDetailsVO.setAuctionApplicationFile(queryLiquiTransferRecordDetailsVO.getAuctionApplicationFile().substring(1, queryLiquiTransferRecordDetailsVO.getAuctionApplicationFile().length() - 1));
		}
		//????????????????????????????????????????????????
		TaskNode taskNode = taskNodeService.getById(queryLiquiTransferRecordDetailsVO.getNodeId());
		Target target = targetService.getById(taskNode.getTargetId());
		queryLiquiTransferRecordDetailsVO.setAssetsId(target.getGoalId());

		//????????????id???type?????????????????????????????????
		String executorSubjectName = getExecutorSubjectName(queryLiquiTransferRecordDetailsVO.getCaseeId());

		queryLiquiTransferRecordDetailsVO.setExecutorSubjectName(executorSubjectName);

		String applicantSubjectName = getApplicantSubjectName(queryLiquiTransferRecordDetailsVO.getCaseeId());

		queryLiquiTransferRecordDetailsVO.setApplicantSubjectName(applicantSubjectName);

		return queryLiquiTransferRecordDetailsVO;
	}

	@Override
	@Transactional
	public boolean reception(LiquiTransferRecordDTO liquiTransferRecordDTO) {
		LiquiTransferRecord liquiTransferRecord=new LiquiTransferRecord();
		BeanUtils.copyProperties(liquiTransferRecordDTO,liquiTransferRecord);
		ProjectPaifuReceiptDTO projectPaifuReceiptDTO=new ProjectPaifuReceiptDTO();
		BeanUtils.copyProperties(liquiTransferRecordDTO,projectPaifuReceiptDTO);
		projectPaifuReceiptDTO.setProjectId(liquiTransferRecordDTO.getPaifuProjectId());
		projectPaifuReceiptDTO.setTakeTime(liquiTransferRecordDTO.getReturnTime());

		//??????????????????id
		liquiTransferRecord.setPaifuProjectId(projectPaifuService.saveProjectReceipt(projectPaifuReceiptDTO));

		return this.updateById(liquiTransferRecord);
	}

	@Override
	@Transactional
	public Integer returnTransfer(LiquiTransferRecordDetailsDTO liquiTransferRecordDetailsDTO) {
		List<AssetsVO> assetsVOList = liquiTransferRecordDetailsDTO.getAssetsVOList();
		for (AssetsVO assetsVO : assetsVOList) {
			AssetsRe assetsRe = assetsReService.getById(assetsVO.getAssetsReId());
			assetsRe.setStatus(100);
			//????????????????????????????????????
			assetsReService.updateById(assetsRe);
		}
		return this.baseMapper.updateById(liquiTransferRecordDetailsDTO);
	}

	@Override
	public Project queryCompanyCode(Integer projectId, Integer insId, Integer outlesId) {
		List<LiquiTransferRecord> liquiTransferRecordList = this.list(new LambdaQueryWrapper<LiquiTransferRecord>().eq(LiquiTransferRecord::getProjectId, projectId).eq(LiquiTransferRecord::getEntrustedInsId, insId).eq(LiquiTransferRecord::getEntrustedOutlesId, outlesId).eq(LiquiTransferRecord::getDelFlag, 0));
		if (liquiTransferRecordList.size()>1){
			return projectPaifuService.getById(liquiTransferRecordList.get(0).getPaifuProjectId());
		}
		return null;
	}

	@Override
	public List<LiquiTransferRecordAssetsDetailsVO> getTransferRecordAssetsByProjectId(Integer projectId) {
		return this.baseMapper.getTransferRecordAssetsByProjectId(projectId);
	}
	/**
	 * ??????????????????id??????????????????????????????
	 * @param paifuProjectId
	 * @return
	 */
	@Override
	public LiquiTransferRecord getByPaifuProjectIdAndAssetsId(Integer paifuProjectId, Integer assetsId) {
		return this.baseMapper.getByPaifuProjectIdAndAssetsId(paifuProjectId, assetsId);
	}

}
