package com.pig4cloud.pig.casee.vo.count;

import lombok.Data;

@Data
public class CountCompareQuantityVO {

	/**
	 * 较去年项目数
	 */
	private Long compareTheNumberOfItemsCount;

	/**
	 * 较去年回款额
	 */
	private Long compareMoneyBackAmountCount;

	/**
	 * 较去年案件数量
	 */
	private Long compareTheNumberOfCasesCount;

	/**
	 * 较去年财产数
	 */
	private Long comparePropertyNumbersCount;

}
