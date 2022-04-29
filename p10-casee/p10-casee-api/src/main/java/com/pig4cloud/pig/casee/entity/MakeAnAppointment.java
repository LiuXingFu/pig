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
 * 预约看样表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:16
 */
@Data
@TableName("p10_make_an_appointment")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "预约看样表")
public class MakeAnAppointment extends BaseEntity {

    /**
     * 预约看样表
     */
    @TableId
    @ApiModelProperty(value="预约看样表")
    private Integer makeAnAppointment;

    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private Integer projectId;

    /**
     * 案件id
     */
    @ApiModelProperty(value="案件id")
    private Integer caseeId;

    /**
     * 财产id
     */
    @ApiModelProperty(value="财产id")
    private Integer assetsId;

    /**
     * 拍卖记录id
     */
    @ApiModelProperty(value="拍卖记录id")
    private Integer auctionRecordId;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}
