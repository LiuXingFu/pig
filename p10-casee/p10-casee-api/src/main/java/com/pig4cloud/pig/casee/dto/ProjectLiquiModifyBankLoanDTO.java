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
package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 项目表DTO
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@Data
public class ProjectLiquiModifyBankLoanDTO {

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 借贷时间
	 */
	@ApiModelProperty(value="借贷时间")
	private LocalDate transferDate;

	/**
	 * 抵押情况（0-有，1-无）
	 */
	@ApiModelProperty(value="抵押情况（0-有，1-无）")
	private Integer mortgageSituation;

	/**
	 * 诉讼情况(0-未诉讼 1-已诉讼)
	 */
	@ApiModelProperty(value="诉讼情况(0-未诉讼 1-已诉讼)")
	private Integer litigation;

	/**
	 * 申请诉讼/执行时效开始时间
	 */
	@ApiModelProperty(value="申请诉讼/执行时效开始时间")
	private LocalDate startingTime;

	/**
	 * 利率
	 */
	@ApiModelProperty(value="利率")
	private BigDecimal interestRate;

	/**
	 * 银行网点id
	 */
	@ApiModelProperty(value="银行网点id")
	private Integer bankLoanOutlesId;

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

}
