package com.pig4cloud.pig.casee.vo.count;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo.count
 * @ClassNAME: 回款额折线柱状VO
 * @Author: yd
 * @DATE: 2022/3/10
 * @TIME: 15:42
 * @DAY_NAME_SHORT: 周四
 */
@Data
public class CountLineChartColumnarChartVO {

	/**
	 * 折线柱状时间线
	 */
	private List<String> lineColumnarTimelineList;

	/**
	 * 折线柱状回款额
	 */
	private List<BigDecimal> moneyBackAmountLit;

}
