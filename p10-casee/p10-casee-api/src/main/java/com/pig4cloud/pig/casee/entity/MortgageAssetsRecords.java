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
 * 抵押记录表
 *
 * @author Mjh
 * @date 2022-04-13 11:24:18
 */
@Data
@TableName("p10_mortgage_assets_records")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "抵押记录表")
public class MortgageAssetsRecords extends BaseEntity {

    /**
     * 抵押记录表
     */
    @TableId
    @ApiModelProperty(value="抵押记录表")
    private Integer mortgageAssetsRecordsId;

    /**
     * 银行借贷表id
     */
    @ApiModelProperty(value="银行借贷表id")
    private Integer bankLoanId;

    /**
     * 抵押金额
     */
    @ApiModelProperty(value="抵押金额")
    private BigDecimal mortgageAmount;

    /**
     * 抵押开始时间
     */
    @ApiModelProperty(value="抵押开始时间")
    private LocalDate mortgageStartTime;

    /**
     * 抵押结束时间
     */
    @ApiModelProperty(value="抵押结束时间")
    private LocalDate mortgageEndTime;

    /**
     * 是否联合抵押(0-否 1-是)
     */
    @ApiModelProperty(value="是否联合抵押(0-否 1-是)")
    private Integer jointMortgage;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String describes;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;


}
