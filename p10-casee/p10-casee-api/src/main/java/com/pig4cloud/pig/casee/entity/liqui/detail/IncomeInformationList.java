package com.pig4cloud.pig.casee.entity.liqui.detail;

import lombok.Data;

import java.math.BigDecimal;


/**
 * 被执行人收入信息
 */
@Data
public class IncomeInformationList {
	/**
	 * 当事人身份证 被执行人身份证
	 */
	private String partyIDCard;

	/**
	 * 当事人名称
	 */
	private String partyName;

	/**
	 * 工资
	 */
	private BigDecimal salary;

	/**
	 * 奖金
	 */
	private BigDecimal bonus;

	/**
	 * 劳动报酬
	 */
	private BigDecimal laborRemuneration;

	/**
	 * 其他收入
	 */
	private BigDecimal otherRevenue;
}
