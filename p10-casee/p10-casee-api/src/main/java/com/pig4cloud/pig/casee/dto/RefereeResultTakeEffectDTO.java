package com.pig4cloud.pig.casee.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 裁判结果生效修改项目本金、利息以及诉讼费DTO
 *
 * @author Mjh
 * @date 2022-03-01 20:36:17
 */
@Data
public class RefereeResultTakeEffectDTO {
	/**
	 * 项目id
	 */
	private Integer projectId;

	/**
	 * 案件id
	 */
	private Integer caseeId;

	/**
	 * 裁判/调解日期
	 */
	private LocalDate refereeMediationTime;

	/**
	 * 诉讼费用
	 */
	private BigDecimal litigationCosts;

	/**
	 * 裁判金额
	 */
	private BigDecimal refereeAmount;

	/**
	 * 本金
	 */
	private BigDecimal principal;

	/**
	 * 利息
	 */
	private BigDecimal interest;




}
