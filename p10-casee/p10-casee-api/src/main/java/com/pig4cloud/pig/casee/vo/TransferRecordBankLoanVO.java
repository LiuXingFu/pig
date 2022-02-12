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
package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.entity.liquientity.TransferRecordLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 移交记录银行借贷VO
 *
 * @author Mjh
 * @date 2022-01-28 18:52:40
 */
@Data
public class TransferRecordBankLoanVO extends TransferRecordLiqui {

	/**
	 * 银行机构id
	 */
	private Integer insId;

	/**
	 * 银行网点id
	 */
	private Integer outlesId;

	/**
	 * 银行机构名称
	 */
	private String insName;

	/**
	 * 银行网点名称
	 */
	private String outlesName;

	/**
	 * 受托机构名称
	 */
	private String entrustedInsName;

	/**
	 * 受托网点名称
	 */
	private String entrustedOutlesName;

	/**
	 * 本金
	 */
	private BigDecimal principal;

	/**
	 * 利息
	 */
	private BigDecimal interest;

	/**
	 * 总额
	 */
	private BigDecimal rental;

	/**
	 * 抵押情况（0-有，1-无）
	 */
	private Integer mortgageSituation;

    /**
     * 借贷合同
     */
    private String loanContract;

    /**
     * 其它文件
     */
    private String otherFile;

	/**
	 * 借贷日期
	 */
	private LocalDate transferDate;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 银行借贷所有债务人名称
	 */
	private String subjectName;


}
