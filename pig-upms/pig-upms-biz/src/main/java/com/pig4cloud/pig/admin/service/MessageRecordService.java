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

package com.pig4cloud.pig.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.dto.TaskMessageDTO;
import com.pig4cloud.pig.admin.api.entity.MessageRecord;
import com.pig4cloud.pig.admin.api.vo.CooperationNewsVO;
import com.pig4cloud.pig.admin.api.vo.MessageNumberVO;
import com.pig4cloud.pig.admin.api.vo.MessageRecordVO;
import com.pig4cloud.pig.casee.entity.TaskNode;

import java.util.List;

/**
 * 消息记录表
 *
 * @author 缪建华
 * @date 2021-09-02 16:16:46
 */
public interface MessageRecordService extends IService<MessageRecord> {

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param messageRecordDTO 消息记录表
	 * @return
	 */
	IPage getMessageRecordPage(Page page, MessageRecordDTO messageRecordDTO);

	/**
	 * 查看消息
	 * @param messageId 消息对象id
	 * @return
	 */
	MessageRecordVO getById(Integer messageId);

	/**
	 * 发送消息不推送到app
	 * @param messageRecordDTOList
	 * @return R
	 */
	Boolean batchSendMessageRecordOutPush(List<MessageRecordDTO> messageRecordDTOList);

	/**
	 * 发送消息并推送到app
	 * @param messageRecordDTOList
	 * @return R
	 */
	Boolean batchSendMessageRecordPush(List<MessageRecordDTO> messageRecordDTOList);

	/**
	 * 修改消息记录状态全部为已读
	 * @param messageRecordList 修改消息记录状态全部为已读
	 * @return R
	 */
	Boolean updateReadFlagAll(List<MessageRecord> messageRecordList);

	/**
	 *	查询当前机构的所有合作消息
	 * @param messageId
	 * @return
	 */
	List<CooperationNewsVO> withCooperativeAgencies(Integer messageId);

	/**
	 * 查询消息气泡数
	 */
	MessageNumberVO getMessageNumber();

	/**
	 * 发送拍辅任务消息
	 * @param taskNode
	 * @return
	 */
	int sendPaifuTaskMessage(TaskNode taskNode);

	/**
	 * 根据发送消息目标类型发送消息
	 * @param taskMessageDTO
	 */
	int sendTaskMessageByTaskMessageDTO(TaskMessageDTO taskMessageDTO);
}
