package com.pig4cloud.pig.casee.vo.count;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo.count
 * @ClassNAME: 项目案件折线VO
 * @Author: yd
 * @DATE: 2022/3/9
 * @TIME: 12:06
 * @DAY_NAME_SHORT: 周三
 */
@Data
public class CountPolylineLineChartVO {

	/**
	 * 时间线
	 */
	private List<String> timelineList;

	/**
	 * 项目Map
	 */
	private List<BigDecimal> projectList;

	/**
	 * 案件Map
	 */
	private List<BigDecimal> caseeList;

}
