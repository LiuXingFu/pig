package com.pig4cloud.pig.casee.dto.count;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.dto.count
 * @ClassNAME: 回款折线图柱状图DTO
 * @Author: yd
 * @DATE: 2022/3/10
 * @TIME: 15:29
 * @DAY_NAME_SHORT: 周四
 */
@Data
public class CountLineChartColumnarChartDTO {


	/**
	 * 折线、柱状年月份类型 0-年 1-月
	 */
	private Integer polylineColumnActive;

	/**
	 * 折线柱状开始年份
	 */
	private String polylineColumnYearStar;

	/**
	 * 折线柱状结束年份
	 */
	private String polylineColumnYearEnd;

	/**
	 * 折线柱状月份开始月份
	 */
	private String polylineColumnMonthStar;

	/**
	 * 折线柱状月份结束月份
	 */
	private String polylineColumnMonthEnd;

}
