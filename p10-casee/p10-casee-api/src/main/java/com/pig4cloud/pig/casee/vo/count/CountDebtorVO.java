package com.pig4cloud.pig.casee.vo.count;

import lombok.Data;

/**
 * 统计债务人管理
 */
@Data
public class CountDebtorVO {


	/**
	 * 行为未限制
	 */
	private Long behaviorNotLimit;

	/**
	 * 限制未送达
	 */
	private Long limitNotService;

	/**
	 * 制裁申请未实施
	 */
	private Long sanctionApplyNotImplemented;

	/**
	 * 已结清限制未撤销
	 */
	private Long alreadySettleLimitNotRevoked;

	/**
	 * 未添加财产
	 */
	private Long notAddProperty;

}
