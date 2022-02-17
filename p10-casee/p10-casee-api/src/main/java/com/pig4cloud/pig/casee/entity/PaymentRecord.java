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
 * 回款详细记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:52:51
 */
@Data
@TableName("p10_payment_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "回款详细记录表")
public class PaymentRecord extends BaseEntity {

    /**
     * 回款记录id
     */
    @TableId
    @ApiModelProperty(value="回款记录id")
    private Integer paymentRecordId;

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
     * 回款类型(0-法院领款 1-存款划扣 2-拍卖款)
     */
    @ApiModelProperty(value="回款类型(0-法院领款 1-存款划扣 2-拍卖款)")
    private Integer paymentType;

    /**
     * 款项类型(0-本金/利息 1-首次执行费 2-一审诉讼费 3-二审诉讼费)
     */
    @ApiModelProperty(value="款项类型(0-本金/利息 1-首次执行费 2-一审诉讼费 3-二审诉讼费)")
    private Integer fundsType;

    /**
     * 回款时间
     */
    @ApiModelProperty(value="回款时间")
    private LocalDate paymentDate;

    /**
     * 还款人
     */
    @ApiModelProperty(value="还款人")
    private Integer subjectId;

    /**
     * 回款金额
     */
    @ApiModelProperty(value="回款金额")
    private BigDecimal paymentAmount;

    /**
     * 案件id
     */
    @ApiModelProperty(value="案件id")
    private Integer caseeId;

    /**
     * 公司业务案号
     */
    @ApiModelProperty(value="公司业务案号")
    private String companyCode;


}
