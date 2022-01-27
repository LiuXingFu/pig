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

package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.MessageRecord;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 * 消息记录表
 *
 * @author 缪建华
 * @date 2021-09-02 16:16:46
 */
@Data
public class MessageRecordVO extends MessageRecord {
private static final long serialVersionUID = 1L;

	/**
	 * 发送人
	 */
	@ApiModelProperty(value="发送人")
	private String senderUserName;
    /**
     * 接收人
     */
    @ApiModelProperty(value="接收人")
    private String receiverUserName;
    /**
     * 接收人机构
     */
    @ApiModelProperty(value="接收人机构")
    private String receiverInsName;
    /**
     * 接收人网点
     */
    @ApiModelProperty(value="接收人网点")
    private String receiverOutlesName;
    /**
     * 发送人机构
     */
    @ApiModelProperty(value="发送人机构")
    private String senderInsName;
    /**
     * 发送人网点
     */
    @ApiModelProperty(value="发送人网点")
    private String senderOutlesName;

    }
