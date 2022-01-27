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
import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.entity.*;
import com.pig4cloud.pig.admin.api.feign.RemoteAuthUtilsService;
import com.pig4cloud.pig.admin.api.vo.InstitutionAssociateVO;
import com.pig4cloud.pig.admin.api.vo.MessageRecordVO;
import com.pig4cloud.pig.admin.mapper.InstitutionAssociateMapper;
import com.pig4cloud.pig.admin.service.*;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 机构关联表
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


	/**
	 *	关联机构
	 * @param institutionAssociate
	 * @return
	 */
	@Override
	@Transactional
	public int saveInstitutionAssociate(InstitutionAssociate institutionAssociate) throws Exception {
		if(Objects.nonNull(institutionAssociate)){
			institutionAssociate.setInsId( SecurityUtils.getUser().getInsId());
			institutionAssociate.setAssociateStatus(0);
			institutionAssociate.setAssociateTime(LocalDateTime.now());
			this.save(institutionAssociate);

			Outles outles = outlesService.getOne(new LambdaQueryWrapper<Outles>().eq(Outles::getInsId, institutionAssociate.getInsId())
					.eq(Outles::getCanDefault, 1).eq(Outles::getDelFlag, 0));

			if(Objects.nonNull(outles)){
				AssociateOutlesRe associateOutlesRe = new AssociateOutlesRe();
				associateOutlesRe.setOutlesId(outles.getOutlesId());
				associateOutlesRe.setAssociateId(institutionAssociate.getAssociateId());
				associateOutlesRe.setAuthorizationTime(LocalDateTime.now());
				associateOutlesRe.setInsId(institutionAssociate.getInsId());
				associateOutlesRe.setInsAssociateId(institutionAssociate.getInsAssociateId());
				associateOutlesReService.save(associateOutlesRe);
			} else {
				throw new Exception("默认网点不存在，请联系系统相关人员！");
			}

			// 根据关联机构ID发送关联
			Institution institution = institutionService.getById(SecurityUtils.getUser().getInsId());

			Institution associate = institutionService.getById(institutionAssociate.getInsAssociateId());

			MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
			messageRecordDTO.setMessageTitle(institution.getInsName() + "向你发送了合作请求");
			messageRecordDTO.setMessageContent(institutionAssociate.getAssociateRemark());
			messageRecordDTO.setReceiverUserId(associate.getUserId());
			SysUser user = sysUserService.getById(associate.getUserId());

			List<ClientUserRe> clientUserReList = clientUserReService.list(new LambdaQueryWrapper<ClientUserRe>().eq(ClientUserRe::getUserName, user.getUsername()));

			if(clientUserReList.size() > 0){
				List<String> clientIds = new ArrayList<>();

				for (ClientUserRe clientUserRe : clientUserReList) {
					clientIds.add(clientUserRe.getClientId());
				}

				//内容暂时有问题 后续还需测试

				requestAppService.pushAppMessage(clientIds, institution.getInsName() + "向你发送了合作请求", "你好，我想和你合作！");
			}

			messageRecordDTO.setReceiverInsId(associate.getInsId());
			messageRecordDTO.setMessageType(400);
			List<MessageRecordDTO> messageRecordDTOList=new ArrayList<>();
			messageRecordDTOList.add(messageRecordDTO);

			messageRecordService.batchSendMessageRecordPush(messageRecordDTOList);

			return institutionAssociate.getAssociateId();
		} else {
			throw new Exception("参数异常！");
		}
	}



	/**
	 * 根据机构ID分页查询合作机构
	 * @param page
	 * @param institutionAssociate
	 * @return
	 */
	@Override
	public IPage<InstitutionAssociateVO> pageInstitutionAssociate(Page page, InstitutionAssociate institutionAssociate) {
		institutionAssociate.setInsId( SecurityUtils.getUser().getInsId());
		return this.baseMapper.pageInstitutionAssociate(page, institutionAssociate);
	}

	/**
	 * 根据机构关联id查询相关信息
	 * @param associateId
	 * @return
	 */
	@Override
	public InstitutionAssociateVO queryById(Integer associateId) {
		InstitutionAssociateVO institutionAssociateVO = this.baseMapper.queryById(associateId);
		Address address = addressService.getByUserId(institutionAssociateVO.getInsAssociateId(), 2);
		institutionAssociateVO.setAddress(address);
		return institutionAssociateVO;
	}

	/**
	 * 通过id解除关联
	 * @param associateId id
	 * @return R
	 */
	@Override
	@Transactional
	public boolean dismissById(Integer associateId) {

		//根据关联id查询机构关联对象
		InstitutionAssociate associate = this.getById(associateId);

		//根据被关联机构id与机构id查询被关联机构对象
		InstitutionAssociate institutionAssociate = this.getOne(new LambdaQueryWrapper<InstitutionAssociate>()
				.eq(InstitutionAssociate::getInsId, associate.getInsAssociateId())
				.eq(InstitutionAssociate::getInsAssociateId, associate.getInsId()).ne(InstitutionAssociate::getAssociateStatus,"200"));

		List<Integer> associateIdList = new ArrayList<>();
		associateIdList.add(associate.getAssociateId());
		associateIdList.add(institutionAssociate.getAssociateId());

		//根据被关联机构对象id删除授权网点
		associateOutlesReService.remove(new LambdaQueryWrapper<AssociateOutlesRe>().eq(AssociateOutlesRe::getAssociateId, institutionAssociate.getAssociateId()));

		//根据关联机构对象id删除授权网点
		associateOutlesReService.remove(new LambdaQueryWrapper<AssociateOutlesRe>().eq(AssociateOutlesRe::getAssociateId, associate.getAssociateId()));

		//删除关联对象
		return this.removeByIds(associateIdList);
	}

	/**
	 * 认证关联关系
	 *
	 * @param certificationRelationshipDTO
	 * @return R
	 */
	@Override
	@Transactional
	public Integer certificationRelationship(CertificationRelationshipDTO certificationRelationshipDTO) {

		// 1.查询当前登录的机构
		Integer insId = securityUtilsService.getCacheUser().getInsId();
		// 2.判断状态是否关联
		if(certificationRelationshipDTO.getAssociateStatus() == 100){
			// 状态关联
			// 2.1 修改关联公司的关联状态
			InstitutionAssociate associate = updateInstitutionAssociate(certificationRelationshipDTO, insId);
			certificationRelationshipDTO.setInsId(insId);
			certificationRelationshipDTO.setAssociateTime(LocalDateTime.now());
			certificationRelationshipDTO.setAssociateRemark("同意合作");
			// 2.2 创建当前合作机构关联数据
			this.save(certificationRelationshipDTO);

			Outles outles = this.outlesService.getOne(new LambdaQueryWrapper<Outles>()
					.eq(Outles::getInsId, insId)
					.eq(Outles::getCanDefault, 1)
					.eq(Outles::getDelFlag, 0));

			AssociateOutlesRe associateOutlesRe = new AssociateOutlesRe();
			associateOutlesRe.setInsId(insId);
			associateOutlesRe.setInsAssociateId(certificationRelationshipDTO.getInsAssociateId());
			associateOutlesRe.setAssociateId(certificationRelationshipDTO.getAssociateId());
			associateOutlesRe.setOutlesId(outles.getOutlesId());
			associateOutlesRe.setAuthorizationTime(LocalDateTime.now());
			associateOutlesReService.save(associateOutlesRe);

			// 2.3 发送提示消息
			sendMessages(certificationRelationshipDTO, insId);
			//2.4 将合作消息状态改为已读
			MessageRecord messageRecord = messageRecordService.getById(certificationRelationshipDTO.getMessageId());
			messageRecord.setReadFlag(200);
			messageRecord.setTreatmentType(1);
			messageRecordService.updateById(messageRecord);

			return 0;
		} else {
			// 状态拒绝
			// 2.1 修改关联公司的关联状态
			InstitutionAssociate associate = updateInstitutionAssociate(certificationRelationshipDTO, insId);
			// 2.2 发送提示消息
			sendMessages(certificationRelationshipDTO, insId);
			// 2.3 将合作消息状态改为已读
			MessageRecord messageRecord = messageRecordService.getById(certificationRelationshipDTO.getMessageId());
			messageRecord.setReadFlag(200);
			messageRecord.setTreatmentType(1);
			messageRecordService.updateById(messageRecord);

			return 1;
		}
	}

	/**
	 * 回复合作机构消息 同意或拒绝关联
	 * @param institutionAssociate
	 * @param insId
	 */
	private void sendMessages(InstitutionAssociate institutionAssociate, Integer insId) {

		// 1.根据当前登录机构id查询当前登录的机构信息
		Institution institution = institutionService.getById(insId);

		// 2.根据关联机构id查询关联机构信息
		Institution insAssociate = institutionService.getById(institutionAssociate.getInsAssociateId());

		// 3.发送消息回复合作请求
		MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
		messageRecordDTO.setMessageTitle(institution.getInsName() + (institutionAssociate.getAssociateStatus() == 100 ? "同意" : "拒接") +"了你的合作请求");
		messageRecordDTO.setMessageContent(institutionAssociate.getAssociateRemark());
		messageRecordDTO.setReceiverUserId(insAssociate.getUserId());
		messageRecordDTO.setReceiverInsId(insAssociate.getInsId());
		messageRecordDTO.setMessageType(0);
		List<MessageRecordDTO> messageRecordDTOList=new ArrayList<>();
		messageRecordDTOList.add(messageRecordDTO);

		messageRecordService.batchSendMessageRecordPush(messageRecordDTOList);
	}

	/**
	 * 修改合作机构数据
	 * @param institutionAssociate
	 * @param insId
	 * @return
	 */
	private InstitutionAssociate updateInstitutionAssociate(InstitutionAssociate institutionAssociate, Integer insId) {
		// 1.查询关联机构信息
		InstitutionAssociate associate = this.getOne(new LambdaQueryWrapper<InstitutionAssociate>()
				.eq(InstitutionAssociate::getInsId, institutionAssociate.getInsAssociateId())
				.eq(InstitutionAssociate::getInsAssociateId, insId).ne(InstitutionAssociate::getAssociateStatus, 200));
		// 2.添加合作状态
		associate.setAssociateStatus(institutionAssociate.getAssociateStatus());
		// 3.修改合作机构信息
		this.updateById(associate);
		// 4.返回机构信息
		return associate;
	}
}
