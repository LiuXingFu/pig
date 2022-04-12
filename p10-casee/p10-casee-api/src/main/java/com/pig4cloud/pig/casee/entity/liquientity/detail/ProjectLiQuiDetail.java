package com.pig4cloud.pig.casee.entity.liquientity.detail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ApiModel(value = "清收项目详情表")
public class ProjectLiQuiDetail {

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
	 * 项目总金额
	 */
	@ApiModelProperty(value="项目总金额")
	BigDecimal projectAmount;

	/**
	 * 已回款金额
	 */
	@ApiModelProperty(value="已回款金额")
	BigDecimal repaymentAmount;

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
	 * 诉讼案号
	 */
	@ApiModelProperty(value="诉讼案号")
	private Integer lawsuitNumber;

	/**
	 * 申请诉讼/执行时效开始时间
	 */
	@ApiModelProperty(value="申请诉讼/执行时效开始时间")
	private LocalDate startingTime;

	/**
	 * 未判决利息起算日
	 */
	@ApiModelProperty(value="未判决利息起算日")
	private LocalDate unjudgedInterestStartDate;

	/**
	 * 主动履行到期日
	 */
	@ApiModelProperty(value="主动履行到期日")
	private LocalDate activeFulfillmentDueDate;


}
