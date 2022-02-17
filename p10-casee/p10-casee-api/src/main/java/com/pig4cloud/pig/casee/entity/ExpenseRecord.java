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
 * 费用产生记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:53:07
 */
@Data
@TableName("p10_expense_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "费用产生记录表")
public class ExpenseRecord extends BaseEntity {

    /**
     * 费用记录id
     */
    @TableId
    @ApiModelProperty(value="费用记录id")
    private Integer expenseRecordId;

    /**
     * 0-正常，1-删除
     */
    @ApiModelProperty(value="0-正常，1-删除")
    private String delFlag;

    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private Integer projectId;

    /**
     * 费用类型(0-本金/利息 1-保全费 2-一审诉讼费 3-二审诉讼费 4-首次执行费 5-定价费 6-拍辅费 7-代理费 8-其它费用)
     */
    @ApiModelProperty(value="费用类型(0-本金/利息 1-保全费 2-一审诉讼费 3-二审诉讼费 4-首次执行费 5-定价费 6-拍辅费 7-代理费 8-其它费用)")
    private Integer costType;

    /**
     * 费用产生时间
     */
    @ApiModelProperty(value="费用产生时间")
    private LocalDate costIncurredTime;

    /**
     * 费用金额
     */
    @ApiModelProperty(value="费用金额")
    private BigDecimal costAmount;

    /**
     * 状态(0-正常 1-已还款 2-作废)
     */
    @ApiModelProperty(value="状态(0-正常 1-已还款 2-作废)")
    private Integer status;

    /**
     * 案件id
     */
    @ApiModelProperty(value="案件id")
    private Integer caseeId;

    /**
     * 业务案号
     */
    @ApiModelProperty(value="业务案号")
    private String companyCode;


}
