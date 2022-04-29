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
 * 看样准备工作表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:17
 */
@Data
@TableName("p10_sample_preparation_work")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "看样准备工作表")
public class SamplePreparationWork extends BaseEntity {

    /**
     * 看样准备工作表
     */
    @TableId
    @ApiModelProperty(value="看样准备工作表")
    private Integer samplePreparationWorkId;

    /**
     * 钥匙联系人
     */
    @ApiModelProperty(value="钥匙联系人")
    private String keyContact;

    /**
     * 联系电话
     */
    @ApiModelProperty(value="联系电话")
    private String phone;

    /**
     * 钥匙位置
     */
    @ApiModelProperty(value="钥匙位置")
    private String keyPosition;

    /**
     * 预约看样时间
     */
    @ApiModelProperty(value="预约看样时间")
    private LocalDateTime reserveSeeSampleTime;

    /**
     * 附件地址
     */
    @ApiModelProperty(value="附件地址")
    private String fileUrl;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}
