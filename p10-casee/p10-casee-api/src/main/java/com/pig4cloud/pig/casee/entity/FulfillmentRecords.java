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
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 待履行记录表
 *
 * @author Mjh
 * @date 2022-03-01 20:36:31
 */
@Data
@TableName("p10_fulfillment_records")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "待履行记录表")
public class FulfillmentRecords extends BaseEntity {

    /**
     * 待履行记录id
     */
    @TableId
    @ApiModelProperty(value="待履行记录id")
    private Integer fulfillmentRecordId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;

    /**
     * 和解/调解ID
     */
    @ApiModelProperty(value="和解/调解ID")
    private Integer reconciliatioMediationId;

    /**
     * 回款记录id
     */
    @ApiModelProperty(value="回款记录id")
    private Integer paymentRecordId;

    /**
     * 期数
     */
    @ApiModelProperty(value="期数")
    private Integer period;

    /**
     * 履行时间
     */
    @ApiModelProperty(value="待履行时间")
    private LocalDate fulfillmentTime;

    /**
     * 履行金额
     */
    @ApiModelProperty(value="待履行金额")
    private BigDecimal fulfillmentAmount;

    /**
     * 履行人
     */
    @ApiModelProperty(value="待履行人")
    private Integer subjectId;

	/**
	 * 履行人名称
	 */
	@ApiModelProperty(value="待履行人名称")
	private String subjectName;


    /**
     * (0-待履行 1-正常履行 2-不能履行 3-推迟履行)
     */
    @ApiModelProperty(value="(0-待履行 1-正常履行 2-不能履行 3-推迟履行)")
    private Integer status;

	/**
	 * 明细
	 */
	@ApiModelProperty(value="明细")
	private String details;
}
