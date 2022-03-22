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
	 * 项目本金
	 */
	private BigDecimal principal;

	/**
	 * 项目利息
	 */
	private BigDecimal interest;

	/**
	 * 项目总金额
	 */
	BigDecimal projectAmount;

	/**
	 * 已回款金额
	 */
	BigDecimal repaymentAmount;

	/**
	 * 抵押情况（0-有，1-无）
	 */
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


}
