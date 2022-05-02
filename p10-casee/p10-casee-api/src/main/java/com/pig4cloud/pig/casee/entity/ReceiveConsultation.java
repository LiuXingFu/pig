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
 * 接受咨询名单表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:18
 */
@Data
@TableName("p10_receive_consultation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "接受咨询名单表")
public class ReceiveConsultation extends BaseEntity {

    /**
     * 接受咨询表id
     */
    @TableId
    @ApiModelProperty(value="接受咨询表id")
    private Integer receiveConsultationId;

    /**
     * 主体id
     */
    @ApiModelProperty(value="主体id")
    private Integer subjectId;

    /**
     * 姓名
     */
    @ApiModelProperty(value="姓名")
    private String name;

    /**
     * 联系电话
     */
    @ApiModelProperty(value="联系电话")
    private String phone;

    /**
     * 身份证
     */
    @ApiModelProperty(value="身份证")
    private String identityCard;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}
