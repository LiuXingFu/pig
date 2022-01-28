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
import java.time.LocalDateTime;

/**
 * 移交记录表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:40
 */
@Data
@TableName("p10_transfer_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "移交记录表")
public class TransferRecord extends BaseEntity {

    /**
     * 移交记录id
     */
    @TableId
    @ApiModelProperty(value="移交记录id")
    private Integer transferRecordId;

    /**
     * 银行借贷表id
     */
    @ApiModelProperty(value="银行借贷表id")
    private Integer bankLoanId;

    /**
     * 受托机构id
     */
    @ApiModelProperty(value="受托机构id")
    private Integer entrustedInsId;

    /**
     * 受托网点id
     */
    @ApiModelProperty(value="受托网点id")
    private Integer entrustedOutlesId;

    /**
     * 诉讼情况(0-未诉讼 1-已诉讼)
     */
    @ApiModelProperty(value="诉讼情况(0-未诉讼 1-已诉讼)")
    private Integer litigation;

    /**
     * 移交金额
     */
    @ApiModelProperty(value="移交金额")
    private BigDecimal handoverAmount;

    /**
     * 移交时间
     */
    @ApiModelProperty(value="移交时间")
    private LocalDateTime handoverTime;

    /**
     * 申请诉讼/执行时效开始时间
     */
    @ApiModelProperty(value="申请诉讼/执行时效开始时间")
    private LocalDateTime startingTime;

    /**
     * 接收/退回时间
     */
    @ApiModelProperty(value="接收/退回时间")
    private LocalDateTime returnTime;

    /**
     * 状态(0-待接收 1-已接收 2-退回 )
     */
    @ApiModelProperty(value="状态(0-待接收 1-已接收 2-退回 )")
    private Integer status;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}
