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
package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.appserver.RequestAppService;
import com.pig4cloud.pig.admin.api.dto.CertificationRelationshipDTO;
import com.pig4cloud.pig.admin.api.dto.InstitutionAssociatePageDTO;
import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.dto.TaskMessageDTO;
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.feign.RemoteAuthUtilsService;
import com.pig4cloud.pig.admin.api.vo.*;
import com.pig4cloud.pig.admin.mapper.InstitutionAssociateMapper;
import com.pig4cloud.pig.admin.service.*;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * ???????????????
 *
 * @author yuanduo
 * @date 2021-09-03 11:01:07
 */
@Service
public class InstitutionAssociateServiceImpl extends ServiceImpl<InstitutionAssociateMapper, InstitutionAssociate> implements InstitutionAssociateService {

	@Autowired
	OutlesService outlesService;

	@Autowired
	AssociateOutlesReService associateOutlesReService;

	@Autowired
	AddressService addressService;

	@Autowired
	InstitutionAssociateService institutionAssociateService;

	@Autowired
	InstitutionService institutionService;

	@Autowired
	MessageRecordService messageRecordService;

	@Autowired
	SecurityUtilsService securityUtilsService;

	@Autowired
	SysUserService sysUserService;

	@Autowired
	RequestAppService requestAppService;

	@Autowired
	ClientUserReService clientUserReService;

	@Autowired
	InsOutlesUserService insOutlesUserService;

	@Autowired
	InsOutlesCourtReService insOutlesCourtReService;


	/**
	 * ????????????
	 *
	 * @param institutionAssociate
	 * @return
	 */
	@Override
	@Transactional
	public int saveInstitutionAssociate(InstitutionAssociate institutionAssociate) throws Exception {
		if (Objects.nonNull(institutionAssociate)) {
			institutionAssociate.setInsId(securityUtilsService.getCacheUser().getInsId());
			institutionAssociate.setAssociateStatus(0);
			institutionAssociate.setAssociateTime(LocalDateTime.now());
			this.save(institutionAssociate);

			// ??????????????????ID????????????
			Institution institution = institutionService.getById(securityUtilsService.getCacheUser().getInsId());

			Institution associate = institutionService.getById(institutionAssociate.getInsAssociateId());

			List<MessageRecordDTO> messageRecordDTOList = new ArrayList<>();
			MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
			messageRecordDTO.setMessageTitle(institution.getInsName() + "???????????????????????????");
			messageRecordDTO.setMessageContent(institutionAssociate.getAssociateRemark());
			messageRecordDTO.setReceiverInsId(associate.getInsId());
			messageRecordDTO.setMessageType(400);
			messageRecordDTOList.add(messageRecordDTO);

			messageRecordService.batchSendMessageRecordPush(messageRecordDTOList);

			return institutionAssociate.getAssociateId();
		} else {
			throw new Exception("???????????????");
		}
	}


	/**
	 * ????????????ID????????????????????????
	 *
	 * @param page
	 * @param institutionAssociatePageDTO
	 * @return
	 */
	@Override
	public IPage<InstitutionAssociatePageVO> pageInstitutionAssociate(Page page, InstitutionAssociatePageDTO institutionAssociatePageDTO) {
		institutionAssociatePageDTO.setInsId(securityUtilsService.getCacheUser().getInsId());
		return this.baseMapper.pageInstitutionAssociate(page, institutionAssociatePageDTO);
	}

	/**
	 * ??????????????????id??????????????????
	 *
	 * @param associateId
	 * @return
	 */
	@Override
	public InstitutionAssociateDetailsVO queryById(Integer associateId) {
		return this.baseMapper.queryById(associateId);
	}

	/**
	 * ??????id????????????
	 *
	 * @param associateId id
	 * @return R
	 */
	@Override
	@Transactional
	public boolean dismissById(Integer associateId) {

		//????????????id????????????????????????
		InstitutionAssociate associate = this.getById(associateId);

		//?????????????????????id?????????id???????????????????????????
		InstitutionAssociate institutionAssociate = this.getOne(new LambdaQueryWrapper<InstitutionAssociate>()
				.eq(InstitutionAssociate::getInsId, associate.getInsAssociateId())
				.eq(InstitutionAssociate::getInsAssociateId, associate.getInsId()).ne(InstitutionAssociate::getAssociateStatus, "200"));

		List<Integer> associateIdList = new ArrayList<>();
		associateIdList.add(associate.getAssociateId());
		associateIdList.add(institutionAssociate.getAssociateId());

		//???????????????????????????id??????????????????
		associateOutlesReService.remove(new LambdaQueryWrapper<AssociateOutlesRe>().eq(AssociateOutlesRe::getInsId, institutionAssociate.getInsId())
				.eq(AssociateOutlesRe::getInsAssociateId, institutionAssociate.getInsAssociateId()));

		//????????????????????????id??????????????????
		associateOutlesReService.remove(new LambdaQueryWrapper<AssociateOutlesRe>().eq(AssociateOutlesRe::getInsId, associate.getInsId())
				.eq(AssociateOutlesRe::getInsAssociateId, associate.getInsAssociateId()));

		//??????????????????
		return this.removeByIds(associateIdList);
	}

	/**
	 * ??????????????????
	 *
	 * @param certificationRelationshipDTO
	 * @return R
	 */
	@Override
	@Transactional
	public Integer certificationRelationship(CertificationRelationshipDTO certificationRelationshipDTO) {

		// 1.???????????????????????????
		Integer insId = securityUtilsService.getCacheUser().getInsId();
		// 2.????????????????????????
		if (certificationRelationshipDTO.getAssociateStatus() == 100) {
			// ????????????
			// 2.1 ?????????????????????????????????
			InstitutionAssociate associate = updateInstitutionAssociate(certificationRelationshipDTO, insId);
			certificationRelationshipDTO.setInsId(insId);
			certificationRelationshipDTO.setAssociateTime(LocalDateTime.now());
			certificationRelationshipDTO.setAssociateRemark("????????????");
			// 2.2 ????????????????????????????????????
			this.save(certificationRelationshipDTO);

			// 2.3 ??????????????????
			sendMessages(certificationRelationshipDTO, insId);
			//2.4 ?????????????????????????????????
			MessageRecord messageRecord = messageRecordService.getById(certificationRelationshipDTO.getMessageId());
			messageRecord.setReadFlag(200);
			messageRecord.setTreatmentType(1);
			messageRecordService.updateById(messageRecord);

			return 0;
		} else {
			// ????????????
			// 2.1 ?????????????????????????????????
			InstitutionAssociate associate = updateInstitutionAssociate(certificationRelationshipDTO, insId);
			// 2.2 ??????????????????
			sendMessages(certificationRelationshipDTO, insId);
			// 2.3 ?????????????????????????????????
			MessageRecord messageRecord = messageRecordService.getById(certificationRelationshipDTO.getMessageId());
			messageRecord.setReadFlag(200);
			messageRecord.setTreatmentType(1);
			messageRecordService.updateById(messageRecord);

			return 1;
		}
	}

	/**
	 * ??????????????????
	 * @return
	 */
	@Override
	public List<CourtAndCourtInsIdVO> getAssociateCourt(String courtName) {
		return this.baseMapper.getAssociateCourt(securityUtilsService.getCacheUser().getInsId(), courtName, null);
	}

	@Override
	public List<CourtAndCourtInsIdVO> getAssociateCourtByInsIdAndOutlesId(Integer insId, Integer outlesId, String courtName) {
		List<Integer> courIds = this.insOutlesCourtReService.list(new LambdaQueryWrapper<InsOutlesCourtRe>()
				.eq(InsOutlesCourtRe::getInsId, insId)
				.eq(InsOutlesCourtRe::getOutlesId, outlesId))
				.stream().map(InsOutlesCourtRe::getCourtId)
				.collect(Collectors.toList());
		return this.baseMapper.getAssociateCourt(insId, courtName, courIds);
	}

	/**
	 * ???????????????????????? ?????????????????????
	 *
	 * @param institutionAssociate
	 * @param insId
	 */
	private void sendMessages(InstitutionAssociate institutionAssociate, Integer insId) {

		// 1.????????????????????????id?????????????????????????????????
		Institution institution = institutionService.getById(insId);

		// 2.??????????????????id????????????????????????
		Institution insAssociate = institutionService.getById(institutionAssociate.getInsAssociateId());

		// 3.??????????????????????????????
		MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
		messageRecordDTO.setMessageTitle(institution.getInsName() + (institutionAssociate.getAssociateStatus() == 100 ? "??????" : "??????") + "?????????????????????");
		messageRecordDTO.setMessageContent(institutionAssociate.getAssociateRemark());
		messageRecordDTO.setMessageType(0);
		messageRecordDTO.setCreateBy(securityUtilsService.getCacheUser().getId());
		messageRecordDTO.setCreateTime(LocalDate.now());

		TaskMessageDTO taskMessageDTO = new TaskMessageDTO();

		taskMessageDTO.setInsId(insAssociate.getInsId());

		taskMessageDTO.setMessageGoalType(1);

		taskMessageDTO.setMessageGoalPermission(1101);

		taskMessageDTO.setMessageRecordDTO(messageRecordDTO);

		messageRecordService.sendTaskMessageByTaskMessageDTO(taskMessageDTO);
	}

	/**
	 * ????????????????????????
	 *
	 * @param institutionAssociate
	 * @param insId
	 * @return
	 */
	private InstitutionAssociate updateInstitutionAssociate(InstitutionAssociate institutionAssociate, Integer insId) {
		// 1.????????????????????????
		InstitutionAssociate associate = this.getOne(new LambdaQueryWrapper<InstitutionAssociate>()
				.eq(InstitutionAssociate::getInsId, institutionAssociate.getInsAssociateId())
				.eq(InstitutionAssociate::getInsAssociateId, insId).ne(InstitutionAssociate::getAssociateStatus, 200));
		// 2.??????????????????
		associate.setAssociateStatus(institutionAssociate.getAssociateStatus());
		// 3.????????????????????????
		this.updateById(associate);
		// 4.??????????????????
		return associate;
	}
}
