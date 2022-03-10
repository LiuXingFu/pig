package com.pig4cloud.pig.casee.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: 还款排名实体
 * @Author: yd
 * @DATE: 2022/3/8
 * @TIME: 14:54
 * @DAY_NAME_SHORT: 周二
 */
@Data
public class MoneyBackMonthlyRank {

	/**
	 * 办理人真实姓名
	 */
	private String actualName;

	/**
	 * 还款额
	 */
	private BigDecimal repaymentAmount;

}
