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
import com.pig4cloud.pig.admin.api.dto.TaskMessageDTO;
import com.pig4cloud.pig.admin.api.entity.ClientUserRe;
import com.pig4cloud.pig.admin.api.entity.InsOutlesUser;
import com.pig4cloud.pig.admin.api.entity.MessageRecord;
import com.pig4cloud.pig.admin.api.vo.CooperationNewsVO;
import com.pig4cloud.pig.admin.api.vo.MessageNumberVO;
import com.pig4cloud.pig.admin.api.vo.MessageRecordVO;
import com.pig4cloud.pig.admin.mapper.MessageRecordMapper;
import com.pig4cloud.pig.admin.service.ClientUserReService;
import com.pig4cloud.pig.admin.service.InsOutlesUserService;
import com.pig4cloud.pig.admin.service.MessageRecordService;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.LiquiTransferRecord;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.feign.RemoteAssetsService;
import com.pig4cloud.pig.casee.feign.RemoteCaseeLiquiService;
import com.pig4cloud.pig.casee.feign.RemoteLiquiTransferRecordService;
import com.pig4cloud.pig.casee.vo.paifu.NodeMessageRecordVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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
	@Autowired
	private RemoteLiquiTransferRecordService remoteLiquiTransferRecordService;
	@Autowired
	private RemoteCaseeLiquiService remoteCaseeLiquiService;
	@Autowired
	private RemoteAssetsService remoteAssetsService;
	@Autowired
	private InsOutlesUserService insOutlesUserService;

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
		MessageRecord messageRecord = new MessageRecord();
		messageRecord.setMessageId(messageId);
		messageRecord.setReadFlag(200);
		this.baseMapper.updateById(messageRecord);
		return this.baseMapper.getById(messageId);
	}

	@Override
	public Boolean batchSendMessageRecordOutPush(List<MessageRecordDTO> messageRecordDTOList) {
		PigUser pigUser = securityUtilsService.getCacheUser();
		List<MessageRecord> messageRecordList = new ArrayList<>();
		for (MessageRecordDTO messageRecordDTO : messageRecordDTOList) {
			MessageRecord messageRecord = new MessageRecord();
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
		List<MessageRecord> messageRecordList = new ArrayList<>();
		for (MessageRecordDTO messageRecordDTO : messageRecordDTOList) {
			MessageRecord messageRecord = new MessageRecord();
			BeanUtils.copyProperties(messageRecordDTO, messageRecord);
			messageRecord.setSenderInsId(pigUser.getInsId());
			messageRecord.setSenderOutlesId(pigUser.getOutlesId());
			messageRecord.setIsPush(1);
			messageRecordList.add(messageRecord);
			List<ClientUserRe> clientUserReList = clientUserReService.list(new LambdaQueryWrapper<ClientUserRe>().eq(ClientUserRe::getUserName, pigUser.getUsername()));
			if (clientUserReList.size() > 0) {
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
	 * 查询当前机构的所有合作消息
	 *
	 * @param messageId
	 * @return
	 */
	@Override
	public IPage<CooperationNewsVO> withCooperativeAgencies(Page page, Integer messageId) {
		return this.baseMapper.withCooperativeAgencies(page, messageId, securityUtilsService.getCacheUser().getInsId());
	}

	@Override
	public MessageNumberVO getMessageNumber() {
		PigUser pigUser = securityUtilsService.getCacheUser();

		MessageNumberVO messageNumberVO = new MessageNumberVO();
		//系统通知数
		messageNumberVO.setMessageNumber(this.baseMapper.selectMessageNumber(pigUser.getInsId(), pigUser.getId()));
		messageNumberVO.setAuditTaskNumber(this.baseMapper.selectAuditTaskNumber(pigUser.getId()));
		messageNumberVO.setTaskCommissionNumber(this.baseMapper.selectTaskCommissionNumber(pigUser.getId()));
		return messageNumberVO;
	}

	/**
	 * 发送拍辅任务消息
	 *
	 * @param taskNode
	 * @return
	 */
	@Override
	@Transactional
	public int sendPaifuTaskMessage(TaskNode taskNode) {
		int send = -1;

		//此处还需修改
		MessageRecordDTO messageRecordDTO = new MessageRecordDTO();
		messageRecordDTO.setCreateBy(securityUtilsService.getCacheUser().getId());
		messageRecordDTO.setCreateTime(LocalDate.now());
		messageRecordDTO.setMessageType(10000);

		Assets assets = remoteAssetsService.queryAssetsByTargetId(taskNode.getTargetId(), SecurityConstants.FROM).getData();

		LiquiTransferRecord liquiTransferRecord = remoteLiquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), assets.getAssetsId(), SecurityConstants.FROM).getData();
//		LiquiTransferRecord liquiTransferRecord = remoteLiquiTransferRecordService.getByPaifuProjectIdAndAssetsId(571, 708, SecurityConstants.FROM).getData();

		if (Objects.nonNull(liquiTransferRecord)) {
			//根据案件id查询案件
			CaseeLiqui caseeLiqui = remoteCaseeLiquiService.getCaseeLiquiByCaseeId(liquiTransferRecord.getCaseeId(), SecurityConstants.FROM).getData();

			//消息标题
			messageRecordDTO.setMessageTitle("执行案号：" + liquiTransferRecord.getCaseeNumber() + "，" + (taskNode.getSubmissionStatus() == 0 ? "添加" : "补录") + "节点“" + taskNode.getNodeName() + "”");

			//消息内容
			messageRecordDTO.setMessageContent("执行案号：" + liquiTransferRecord.getCaseeNumber()
					+ "&财产：" + assets.getAssetsName() + "&申请人："
					+ caseeLiqui.getApplicantName() + "&债务人："
					+ caseeLiqui.getExecutedName()
					+ "&" + (taskNode.getSubmissionStatus() == 0 ? "添加" : "补录") + "任务节点“" + taskNode.getNodeName()
					+ "”，请点击按钮查看详情");

			NodeMessageRecordVO nodeMessageRecordVO = new NodeMessageRecordVO();

			//将任务数据相关数据存入nodeMessageRecordVO
			BeanCopyUtil.copyBean(taskNode, nodeMessageRecordVO);

			nodeMessageRecordVO.setAssetsId(assets.getAssetsId());

			nodeMessageRecordVO.setAssetsType(assets.getAssetsType());

			//将nodeMessageRecordVO转换成json
			String json = JsonUtils.objectToJson(nodeMessageRecordVO);

			messageRecordDTO.setTargetValue(json);

			TaskMessageDTO taskMessageDTO = new TaskMessageDTO();

			taskMessageDTO.setMessageGoalType(2);

			taskMessageDTO.setInsId(liquiTransferRecord.getEntrustInsId());

			taskMessageDTO.setOutlesId(liquiTransferRecord.getEntrustOutlesId());

			taskMessageDTO.setMessageGoalPermission(2001);

			taskMessageDTO.setMessageRecordDTO(messageRecordDTO);

			return this.sendTaskMessageByTaskMessageDTO(taskMessageDTO);
		}

		return send = 0;
	}

	/**
	 * 根据发送消息目标类型发送消息
	 *
	 * @param taskMessageDTO
	 */
	@Override
	public int sendTaskMessageByTaskMessageDTO(TaskMessageDTO taskMessageDTO) {

		int send = 0;

		List<InsOutlesUser> insOutlesUsers = new ArrayList<>();

		//根据消息目标类型查询机构网点用户关联表
		if (taskMessageDTO.getMessageGoalType().equals(1)) {
			//根据机构id查询机构下的所有员工
			if (taskMessageDTO.getMessageGoalPermission().equals(1001)) {
				List<InsOutlesUser> list = this.insOutlesUserService.list(new LambdaQueryWrapper<InsOutlesUser>().eq(InsOutlesUser::getDelFlag, 0)
						.eq(InsOutlesUser::getInsId, taskMessageDTO.getInsId()));
				insOutlesUsers.addAll(list);
				//根据机构id查询为机构管理员的员工
			} else if (taskMessageDTO.getMessageGoalPermission().equals(1101)) {
				List<InsOutlesUser> list = this.insOutlesUserService.list(new LambdaQueryWrapper<InsOutlesUser>().eq(InsOutlesUser::getDelFlag, 0)
						.eq(InsOutlesUser::getInsId, taskMessageDTO.getInsId())
						.eq(InsOutlesUser::getType, 1).isNull(InsOutlesUser::getOutlesId));
				insOutlesUsers.addAll(list);
				//根据机构id查询为机构管理员与网点管理员的员工
			} else {
				List<InsOutlesUser> list = this.insOutlesUserService.list(new LambdaQueryWrapper<InsOutlesUser>().eq(InsOutlesUser::getDelFlag, 0)
						.eq(InsOutlesUser::getInsId, taskMessageDTO.getInsId())
						.eq(InsOutlesUser::getType, 1));
				insOutlesUsers.addAll(list);
			}
			//根据机构+网点与机构无网点的机构网点用户关联数据
		} else if (taskMessageDTO.getMessageGoalType().equals(2)) {
			//根据机构id与网点查询
			if (taskMessageDTO.getMessageGoalPermission().equals(2001)) {
				List<InsOutlesUser> list = this.insOutlesUserService.list(new LambdaQueryWrapper<InsOutlesUser>().eq(InsOutlesUser::getDelFlag, 0)
						.eq(InsOutlesUser::getInsId, taskMessageDTO.getInsId())
						.eq(InsOutlesUser::getOutlesId, taskMessageDTO.getOutlesId()));
				insOutlesUsers.addAll(list);
			} else {
				List<InsOutlesUser> list = this.insOutlesUserService.list(new LambdaQueryWrapper<InsOutlesUser>().eq(InsOutlesUser::getDelFlag, 0)
						.eq(InsOutlesUser::getInsId, taskMessageDTO.getInsId())
						.eq(InsOutlesUser::getOutlesId, taskMessageDTO.getOutlesId())
						.eq(InsOutlesUser::getType, 1));
				insOutlesUsers.addAll(list);
			}
		} else {
			//将传入的用户id查询机构网点用户关联数据
			InsOutlesUser insOutlesUser = insOutlesUserService.getOne(new LambdaQueryWrapper<InsOutlesUser>()
					.eq(InsOutlesUser::getUserId, taskMessageDTO.getUserId())
					.eq(InsOutlesUser::getInsId, taskMessageDTO.getInsId())
					.eq(InsOutlesUser::getDelFlag, 0));
			insOutlesUsers.add(insOutlesUser);
		}

		List<MessageRecordDTO> messageRecordDTOList = new ArrayList<>();

		//循环机构网点用户关联集合并将他们的相关信息存入消息发送集合中
		insOutlesUsers.forEach(insOutlesUser -> {
			MessageRecordDTO messageRecord = new MessageRecordDTO();
			BeanCopyUtil.copyBean(taskMessageDTO.getMessageRecordDTO(), messageRecord);

			//判断机构网点用户有无机构id
			if (Objects.nonNull(insOutlesUser.getInsId())) {
				messageRecord.setReceiverInsId(insOutlesUser.getInsId());
			}

			//判断机构网点用户有无网点id
			if (Objects.nonNull(insOutlesUser.getOutlesId())) {
				messageRecord.setReceiverOutlesId(insOutlesUser.getOutlesId());
			}

			//判断机构网点有无用户id
			if (Objects.nonNull(insOutlesUser.getUserId())) {
				messageRecord.setReceiverUserId(insOutlesUser.getUserId());
			}

			messageRecordDTOList.add(messageRecord);
		});

		//发送消息集合
		this.batchSendMessageRecordOutPush(messageRecordDTOList);

		return send += 1;
	}

	/**
	 * 更新消息状态为已读
	 *
	 * @param messageId
	 * @return
	 */
	@Override
	public int updateMessageStatus(Integer messageId) {
		MessageRecord messageRecord = this.getOne(new LambdaQueryWrapper<MessageRecord>().eq(MessageRecord::getMessageId, messageId));
		messageRecord.setReadFlag(200);
		this.updateById(messageRecord);
		return 1;
	}

}
