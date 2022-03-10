package com.pig4cloud.pig.common.core.util;

import java.util.Calendar;
import java.util.Date;

public class DataUtils {

	/**
	 *
	 * @param nowTime   当前时间
	 * @param startTime	开始时间
	 * @param endTime   结束时间
	 * @return
	 * @author sunran   判断当前时间在时间区间内
	 */
	public static boolean isEffectiveDate(Date nowTime, Date startTime, Date endTime) {
		if (nowTime.getTime() == startTime.getTime()
				|| nowTime.getTime() == endTime.getTime()) {
			return true;
		}

		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(startTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}
}
