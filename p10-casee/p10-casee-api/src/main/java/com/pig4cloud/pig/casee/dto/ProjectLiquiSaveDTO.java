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

import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.ProjectLiQuiDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 项目表
 *
 * @author pig code generator
 * @date 2022-02-10 17:30:36
 */
@Data
public class ProjectLiquiSaveDTO {

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 机构id
	 */
	@ApiModelProperty(value="机构id")
	private Integer insId;

	/**
	 * 网点id
	 */
	@ApiModelProperty(value="网点id")
	private Integer outlesId;

	/**
	 * 办理人id
	 */
	@ApiModelProperty(value="办理人id")
	private Integer userId;

	/**
	 * 办理人名称
	 */
	@ApiModelProperty(value="办理人名称")
	private String userNickName;

	/**
	 * 接收时间
	 */
	@ApiModelProperty(value="接收时间")
	private LocalDate takeTime;

	/**
	 * 年份
	 */
	@ApiModelProperty(value="年份")
	private String year;

	/**
	 * 简称
	 */
	@ApiModelProperty(value="简称")
	private String alias;

	/**
	 * 字号
	 */
	@ApiModelProperty(value="字号")
	private Integer word;

	/**
	 * 所有委托机构名称(用于显示，多个用，号隔开)
	 */
	@ApiModelProperty(value="所有委托机构名称(用于显示，多个用，号隔开)")
	private String proposersNames;

	/**
	 * 所有债务人名称(用于显示，多个用，号隔开)
	 */
	@ApiModelProperty(value="所有债务人名称(用于显示，多个用，号隔开)")
	private String subjectPersons;

	/**
	 * 描述
	 */
	@ApiModelProperty(value="描述")
	private String describes;

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
	 * 本金利息总额
	 */
	@ApiModelProperty(value="本金利息总额")
	private BigDecimal principalInterestAmount;

	/**
	 * 利率
	 */
	@ApiModelProperty(value="利率")
	private BigDecimal interestRate;

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
	 * 借贷日期
	 */
	@ApiModelProperty(value="借贷日期")
	private LocalDate transferDate;

	/**
	 * 申请诉讼/执行时效开始时间
	 */
	@ApiModelProperty(value="申请诉讼/执行时效开始时间")
	private LocalDate startingTime;

	/**
	 * 借贷银行
	 */
	@ApiModelProperty(value="借贷银行")
	private Integer bankLoanInsId;

	/**
	 * 银行网点
	 */
	@ApiModelProperty(value="银行网点")
	private Integer bankLoanOutlesId;

	/**
	 * 债务人列表
	 */
	private List<ProjectSaveSubjectDTO> subjectPersonsList;

	/**
	 * 抵押物集合
	 */
	private MortgageAssetsAllDTO mortgageAssetsAllDTO;

}
