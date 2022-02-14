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
import java.time.LocalDateTime;

/**
 * 银行借贷表
 *
 * @author Mjh
 * @date 2022-01-29 10:20:00
 */
@Data
@TableName("p10_bank_loan")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "银行借贷表")
public class BankLoan extends BaseEntity {

    /**
     * 主键id
     */
    @TableId
    @ApiModelProperty(value="主键id")
    private Integer bankLoanId;

    /**
     * 删除状态(0-正常,1-已删除)
     */
    @ApiModelProperty(value="删除状态(0-正常,1-已删除)")
    private String delFlag;

    /**
     * 银行机构id
     */
    @ApiModelProperty(value="银行机构id")
    private Integer insId;

    /**
     * 银行网点id
     */
    @ApiModelProperty(value="银行网点id")
    private Integer outlesId;

    /**
     * 本金
     */
    @ApiModelProperty(value="本金")
    private BigDecimal principal;

    /**
     * 利息
     */
    @ApiModelProperty(value="利息")
    private BigDecimal interest;

    /**
     * 总额
     */
    @ApiModelProperty(value="总额")
    private BigDecimal rental;

    /**
     * 抵押情况（0-有，1-无）
     */
    @ApiModelProperty(value="抵押情况（0-有，1-无）")
    private Integer mortgageSituation;

    /**
     * 借贷合同
     */
    @ApiModelProperty(value="借贷合同")
    private String loanContract;

    /**
     * 其它文件
     */
    @ApiModelProperty(value="其它文件")
    private String otherFile;

	/**
	 * 借贷日期
	 */
	@ApiModelProperty(value="借贷日期")
	private LocalDate transferDate;

	/**
	 * 银行借贷所有债务人名称
	 */
	@ApiModelProperty(value="银行借贷所有债务人名称")
	private String subjectName;


	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;
}
