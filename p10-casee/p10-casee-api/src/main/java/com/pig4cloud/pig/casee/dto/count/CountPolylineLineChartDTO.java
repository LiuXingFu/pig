package com.pig4cloud.pig.casee.dto.count;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.dto.count
 * @ClassNAME: 折线图DTO
 * @Author: yd
 * @DATE: 2022/3/9
 * @TIME: 11:44
 * @DAY_NAME_SHORT: 周三
 */
@Data
public class CountPolylineLineChartDTO {

	/**
	 * 折线年月份类型 0-年 1-月
	 */
	private Integer polylineActive;

	/**
	 * 起始年份
	 */
	private String polylineYearStar;

	/**
	 * 结束年份
	 */
	private String polylineYearEnd;

	/**
	 * 起始月份
	 */
	private String polylineMonthStar;

	/**
	 * 结束月份
	 */
	private String polylineMonthEnd;

}
