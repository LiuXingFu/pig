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
import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.entity.ClientUserRe;
import com.pig4cloud.pig.admin.api.entity.MessageRecord;
import com.pig4cloud.pig.admin.api.vo.CooperationNewsVO;
import com.pig4cloud.pig.admin.api.vo.MessageNumberVO;
import com.pig4cloud.pig.admin.api.vo.MessageRecordVO;
import com.pig4cloud.pig.admin.mapper.MessageRecordMapper;
import com.pig4cloud.pig.admin.service.ClientUserReService;
import com.pig4cloud.pig.admin.service.MessageRecordService;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息记录表
 *
 * @author 缪建华
 * @date 2021-09-02 16:16:46
 */
@Service
public class MessageRecordServiceImpl extends ServiceImpl<MessageRecordMapper, MessageRecord> implements MessageRecordService {
	@Autowired
	private SecurityUtilsService securityUtilsService;
	@Autowired
	private ClientUserReService clientUserReService;
	@Autowired
	private RequestAppService requestAppService;
	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Override
	public IPage getMessageRecordPage(Page page, MessageRecordDTO messageRecordDTO) {
		messageRecordDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		messageRecordDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		messageRecordDTO.setUserId(securityUtilsService.getCacheUser().getId());
		return this.baseMapper.getMessageRecordVosPage(page, messageRecordDTO);
	}

	@Override
	public MessageRecordVO getById(Integer messageId) {
		//用户点击查看时，消息改成已读状态
		MessageRecord messageRecord=new MessageRecord();
		messageRecord.setMessageId(messageId);
		messageRecord.setReadFlag(200);
		this.baseMapper.updateById(messageRecord);
		return this.baseMapper.getById(messageId);
	}

	@Override
	public Boolean batchSendMessageRecordOutPush(List<MessageRecordDTO> messageRecordDTOList) {
		PigUser pigUser = securityUtilsService.getCacheUser();
		List<MessageRecord> messageRecordList=new ArrayList<>();
		for (MessageRecordDTO messageRecordDTO : messageRecordDTOList) {
			MessageRecord messageRecord=new MessageRecord();
			BeanUtils.copyProperties(messageRecordDTO, messageRecord);
			messageRecord.setSenderInsId(pigUser.getInsId());
			messageRecord.setSenderOutlesId(pigUser.getOutlesId());
			messageRecordList.add(messageRecord);
		}
		return this.saveBatch(messageRecordList);
	}

	@Override
	public Boolean batchSendMessageRecordPush(List<MessageRecordDTO> messageRecordDTOList) {
		PigUser pigUser = securityUtilsService.getCacheUser();
		List<MessageRecord> messageRecordList=new ArrayList<>();
		for (MessageRecordDTO messageRecordDTO : messageRecordDTOList) {
			MessageRecord messageRecord=new MessageRecord();
			BeanUtils.copyProperties(messageRecordDTO, messageRecord);
			messageRecord.setSenderInsId(pigUser.getInsId());
			messageRecord.setSenderOutlesId(pigUser.getOutlesId());
			messageRecord.setIsPush(1);
			messageRecordList.add(messageRecord);
			List<ClientUserRe> clientUserReList = clientUserReService.list(new LambdaQueryWrapper<ClientUserRe>().eq(ClientUserRe::getUserName, pigUser.getUsername()));
			if(clientUserReList.size() > 0){
				List<String> clientIds = new ArrayList<>();
				for (ClientUserRe clientUserRe : clientUserReList) {
					clientIds.add(clientUserRe.getClientId());
				}
				requestAppService.pushAppMessage(clientIds, messageRecordDTO.getMessageTitle(), messageRecordDTO.getMessageContent());
			}
		}
		return this.saveBatch(messageRecordList);
	}

	@Override
	public Boolean updateReadFlagAll(List<MessageRecord> messageRecordList) {
		for (MessageRecord messageRecord : messageRecordList) {
			messageRecord.setReadFlag(200);
		}
		return this.updateBatchById(messageRecordList);
	}

	/**
	 *	查询当前机构的所有合作消息
	 * @param messageId
	 * @return
	 */
	@Override
	public List<CooperationNewsVO> withCooperativeAgencies(Integer messageId) {
		return this.baseMapper.withCooperativeAgencies(messageId, securityUtilsService.getCacheUser().getInsId());
	}
	@Override
	public MessageNumberVO getMessageNumber(){
		PigUser pigUser = securityUtilsService.getCacheUser();

		MessageNumberVO messageNumberVO =new MessageNumberVO();
		//系统通知数
		messageNumberVO.setMessageNumber(this.baseMapper.selectMessageNumber(pigUser.getInsId(),pigUser.getId()));
		messageNumberVO.setAuditTaskNumber(this.baseMapper.selectAuditTaskNumber(pigUser.getId()));
		messageNumberVO.setTaskCommissionNumber(this.baseMapper.selectTaskCommissionNumber(pigUser.getId()));
		return messageNumberVO;
	}

}
