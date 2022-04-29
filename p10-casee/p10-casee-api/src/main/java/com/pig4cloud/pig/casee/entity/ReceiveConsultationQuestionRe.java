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
package com.pig4cloud.pig.casee.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 接受咨询关联咨询问题表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:18
 */
@Data
@TableName("p10_receive_consultation_question_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "接受咨询关联咨询问题表")
public class ReceiveConsultationQuestionRe extends BaseEntity {

    /**
     * 接受咨询关联咨询问题表id
     */
    @TableId
    @ApiModelProperty(value="接受咨询关联咨询问题表id")
    private Integer receiveConsultationQuestionReId;

    /**
     * 接受咨询表id
     */
    @ApiModelProperty(value="接受咨询表id")
    private Integer receiveConsultationId;

    /**
     * 咨询问题
     */
    @ApiModelProperty(value="咨询问题")
    private String askQuestions;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}
