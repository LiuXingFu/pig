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

@Data
public class ProjectLiquiSumAmountDTO {

	/**
	 * 本金总和
	 */
	@ApiModelProperty(value="本金总和")
	private BigDecimal sumPrincipal;

	/**
	 * 本金总和
	 */
	@ApiModelProperty(value="利息总和")
	private BigDecimal sumInterest;

	/**
	 * 本金总和
	 */
	@ApiModelProperty(value="项目金额总和")
	private BigDecimal sumPrincipalInterestAmount;


}
