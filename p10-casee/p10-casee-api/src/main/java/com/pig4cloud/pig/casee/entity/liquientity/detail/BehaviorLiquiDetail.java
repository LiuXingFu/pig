package com.pig4cloud.pig.casee.entity.liquientity.detail;

import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.*;
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
	 * 行为违法送达情况
	 */
	private BehaviorIllegalServedSituation behaviorIllegalServedSituation;

	/**
	 * 行为违法限制撤销
	 */
	private BehaviorIllegalRevoke behaviorIllegalRevoke;

	/**
	 * 行为违法限制撤销送达情况
	 */
	private BehaviorIllegalRevokeServedSituation behaviorIllegalRevokeServedSituation;

	/**
	 * 行为违法实施行为违法
	 */
	private BehaviorIllegalCommittingAnIllegalAct behaviorIllegalCommittingAnIllegalAct;

	/**
	 * 行为违法实施行为违法送达情况
	 */
	private BehaviorIllegalCommittingAnIllegalActServedSituation behaviorIllegalCommittingAnIllegalActServedSituation;

	/**
	 * 行为限制实体
	 */
	private BehaviorRestrictions behaviorRestrictions;

	/**
	 * 行为限制送达情况
	 */
	private BehaviorRestrictionsServedSituation behaviorRestrictionsServedSituation;

	/**
	 * 行为限制限制撤销
	 */
	private BehaviorRestrictionsRevoke behaviorRestrictionsRevoke;

	/**
	 * 行为限制限制撤销送达情况
	 */
	private BehaviorRestrictionsRevokeServedSituation behaviorRestrictionsRevokeServedSituation;

}
