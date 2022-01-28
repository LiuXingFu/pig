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
 * 财产关联银行借贷表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:30
 */
@Data
@TableName("p10_assets_bank_loan_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "财产关联银行借贷表")
public class AssetsBankLoanRe extends BaseEntity {

    /**
     * 财产关联银行借贷表id
     */
    @TableId
    @ApiModelProperty(value="财产关联银行借贷表id")
    private Integer assetsBankLoanId;

    /**
     * 银行借贷表id
     */
    @ApiModelProperty(value="银行借贷表id")
    private Integer bankLoanId;

    /**
     * 财产表id
     */
    @ApiModelProperty(value="财产表id")
    private Integer assetsId;

    /**
     * 主体表id
     */
    @ApiModelProperty(value="主体表id")
    private Integer subjectId;

    /**
     * 抵押金额
     */
    @ApiModelProperty(value="抵押金额")
    private BigDecimal mortgageAmount;

    /**
     * 抵押时间
     */
    @ApiModelProperty(value="抵押时间")
    private LocalDateTime mortgageTime;


}
