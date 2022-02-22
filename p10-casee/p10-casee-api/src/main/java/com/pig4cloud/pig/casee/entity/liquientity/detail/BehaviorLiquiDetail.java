package com.pig4cloud.pig.casee.entity.liquientity.detail;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BehaviorLiquiDetail {

	/**
	 * 限制期限
	 */
	private Integer restrictedPeriod;

	/**
	 * 限制结束日期
	 */
	private LocalDate limitEndTime;

}
