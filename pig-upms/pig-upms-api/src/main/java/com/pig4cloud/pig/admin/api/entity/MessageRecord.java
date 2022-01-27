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

package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 消息记录表
 *
 * @author 缪建华
 * @date 2021-09-02 16:16:46
 */
@Data
@TableName("p10_message_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "消息记录表")
public class MessageRecord extends BaseEntity {
private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @TableId
    @ApiModelProperty(value="主键ID")
    private Integer messageId;
    /**
     * 0-正常，1-删除
     */
    @ApiModelProperty(value="0-正常，1-删除")
    private String delFlag;
    /**
     * 是否推送(0-否 1-是)
     */
    @ApiModelProperty(value="是否推送(0-否 1-是)")
    private Integer isPush;
    /**
     * 消息类型(0-普通消息 100-任务消息 200-拍辅消息 300-清收消息 400-合作消息)
     */
    @ApiModelProperty(value="消息类型(0-普通消息 100-任务消息 200-拍辅消息 300-清收消息 400-合作消息)")
    private Integer messageType;
    /**
     * 状态 未读-0 已读-200
     */
    @ApiModelProperty(value="状态 未读-0 已读-200")
    private Integer readFlag;
    /**
     * 消息标题
     */
    @ApiModelProperty(value="消息标题")
    private String messageTitle;
    /**
     * 消息内容
     */
    @ApiModelProperty(value="消息内容")
    private String messageContent;
    /**
     * 接收人id
     */
    @ApiModelProperty(value="接收人id")
    private Integer receiverUserId;
    /**
     * 接收人机构id
     */
    @ApiModelProperty(value="接收人机构id")
    private Integer receiverInsId;
    /**
     * 接收人网点id
     */
    @ApiModelProperty(value="接收人网点id")
    private Integer receiverOutlesId;
    /**
     * 发送人机构id
     */
    @ApiModelProperty(value="发送人机构id")
    private Integer senderInsId;
    /**
     * 发送人网点id
     */
    @ApiModelProperty(value="发送人网点id")
    private Integer senderOutlesId;
    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;
	/**
	 * 目标参数
	 */
	@ApiModelProperty(value="目标参数")
	private String targetValue;

	/**
	 * 处理类型 0-未处理 1-已处理
	 */
	@ApiModelProperty(value="处理类型 0-未处理 1-已处理")
	private Integer treatmentType;

    }
