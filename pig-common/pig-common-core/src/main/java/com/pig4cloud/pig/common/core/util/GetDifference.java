package com.pig4cloud.pig.common.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.common.core.util
 * @ClassNAME: getMonthDifference
 * @Author: yd
 * @DATE: 2022/3/9
 * @TIME: 14:42
 * @DAY_NAME_SHORT: 周三
 */
public class GetDifference {

	public static List<String> getMonthDifference(String y1, String y2) {

		List<String> list = new ArrayList<String>();
		try {
			Date startDate = new SimpleDateFormat("yyyy-MM").parse(y1);
			Date endDate = new SimpleDateFormat("yyyy-MM").parse(y2);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			// 获取开始年份和开始月份
			int startYear = calendar.get(Calendar.YEAR);
			int startMonth = calendar.get(Calendar.MONTH);
			// 获取结束年份和结束月份
			calendar.setTime(endDate);
			int endYear = calendar.get(Calendar.YEAR);
			int endMonth = calendar.get(Calendar.MONTH);

			for (int i = startYear; i <= endYear; i++) {
				String date = "";
				if (startYear == endYear) {
					for (int j = startMonth; j <= endMonth; j++) {
						if (j < 9) {
							date = i + "-0" + (j + 1);
						} else {
							date = i + "-" + (j + 1);
						}
						list.add(date);
					}

				} else {
					if (i == startYear) {
						for (int j = startMonth; j < 12; j++) {
							if (j < 9) {
								date = i + "-0" + (j + 1);
							} else {
								date = i + "-" + (j + 1);
							}
							list.add(date);
						}
					} else if (i == endYear) {
						for (int j = 0; j <= endMonth; j++) {
							if (j < 9) {
								date = i + "-0" + (j + 1);
							} else {
								date = i + "-" + (j + 1);
							}
							list.add(date);
						}
					} else {
						for (int j = 0; j < 12; j++) {
							if (j < 9) {
								date = i + "-0" + (j + 1);
							} else {
								date = i + "-" + (j + 1);
							}
							list.add(date);
						}
					}

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	public static List<String> getYearDifference(String y1, String y2) {

		List<String> list = new ArrayList<String>();
		try {
			Date startDate = new SimpleDateFormat("yyyy").parse(y1);
			Date endDate = new SimpleDateFormat("yyyy").parse(y2);

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			// 获取开始年份
			int startYear = calendar.get(Calendar.YEAR);
			// 获取结束年份
			calendar.setTime(endDate);
			int endYear = calendar.get(Calendar.YEAR);

			for (int i = startYear; i <= endYear; i++) {
				list.add(String.valueOf(i));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
