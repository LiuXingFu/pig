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

import java.time.LocalDateTime;

/**
 * 报名看样表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:17
 */
@Data
@TableName("p10_sign_up_look_like")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "报名看样表")
public class SignUpLookLike extends BaseEntity {

    /**
     * 报名看样表id
     */
    @TableId
    @ApiModelProperty(value="报名看样表id")
    private Integer signUpLookLikeId;

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
    private String unifiedIdentity;

    /**
     * 报名时间
     */
    @ApiModelProperty(value="报名时间")
    private LocalDateTime registrationTime;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}
