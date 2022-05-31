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

package com.pig4cloud.pig.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.entity.MessageRecord;
import com.pig4cloud.pig.admin.api.vo.CooperationNewsVO;
import com.pig4cloud.pig.admin.api.vo.MessageRecordVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 消息记录表
 *
 * @author 缪建华
 * @date 2021-09-02 16:16:46
 */
@Mapper
public interface MessageRecordMapper extends BaseMapper<MessageRecord> {

	/**
	 * 分页查询
	 * @param page 分页对象
	 * @param messageRecordDTO 消息记录表
	 * @return
	 */
	IPage<MessageRecordVO> getMessageRecordVosPage(Page page, @Param("query") MessageRecordDTO messageRecordDTO);

	MessageRecordVO getById(@Param("messageId")Integer messageId);

	IPage<CooperationNewsVO> withCooperativeAgencies(Page page, @Param("messageId") Integer messageId, @Param("insId") Integer insId);
	//查询消息通知未读数
	Integer selectMessageNumber(@Param("insId") Integer insId, @Param("userId") Integer userId);
	//查询待办理
	Integer selectAuditTaskNumber( @Param("userId") Integer userId);
	//查询待委托
	Integer selectTaskCommissionNumber( @Param("userId") Integer userId);
}
