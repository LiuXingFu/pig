package com.pig4cloud.pig.casee.entity.liquientity.detail;

import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.BehaviorIllegal;
import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.BehaviorRestrictions;
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

	/**
	 * 行为违法实体
	 */
	private BehaviorIllegal behaviorIllegal;

	/**
	 * 行为限制实体
	 */
	private BehaviorRestrictions behaviorRestrictions;

}
