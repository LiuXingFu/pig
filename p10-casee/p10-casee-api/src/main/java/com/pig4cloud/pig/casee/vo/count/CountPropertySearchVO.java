package com.pig4cloud.pig.casee.vo.count;

import lombok.Data;

/**
 * 统计财产查控管理
 */
@Data
public class CountPropertySearchVO {


	/**
	 * 应查控财产未查控
	 */
	private Long shouldCheckControlUnchecked;

	/**
	 * 有抵押轮封未商移
	 */
	private Long haveMortgageWheelSealNotTransferred;

	/**
	 * 首冻资金未划扣
	 */
	private Long firstFrozenFundsNotDebited;

	/**
	 * 待拍财产未移交
	 */
	private Long propertyToBeAuctionedNotHandedOver;

	/**
	 * 其他财产查控未处理
	 */
	private Long otherPropertyCheckControlNotProcessed;

	/**
	 * 处理未到款
	 */
	private Long handleNotPayment;

}
