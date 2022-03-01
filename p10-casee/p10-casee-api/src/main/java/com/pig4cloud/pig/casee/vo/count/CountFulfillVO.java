package com.pig4cloud.pig.casee.vo.count;

import lombok.Data;

/**
 * 履行阶段
 */
@Data
public class CountFulfillVO {

	/**
	 * 履行未到期
	 */
	private Long fulfillFulfillNotExpired;

	/**
	 * 达成履行协议未处理
	 */
	private Long fulfillReachFulfillProtocolNotProcessed;

	/**
	 * 首次执行待立案
	 */
	private Long fulfillFirstExecutionPending;

}
