package com.pig4cloud.pig.casee.taskscheduling;

import com.pig4cloud.pig.casee.taskscheduling.impl.LiQuiTaskSchedulingImpl;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.taskscheduling
 * @ClassNAME: liquiTaskScheduling
 * @Author: yd
 * @DATE: 2022/3/3
 * @TIME: 14:36
 * @DAY_NAME_SHORT: 周四
 */
@Slf4j
@Component
public class LiQuiTaskScheduling {

	@Autowired
	LiQuiTaskSchedulingImpl liQuiTaskScheduling;

	/**
	 * 每天定时更新
	 * 1.诉讼案件自动结案
	 */
	@XxlJob("refreshEveryDay")
	public void refreshEveryDay() {
		String param = XxlJobHelper.getJobParam(); // 获取参数
		int shardIndex = XxlJobHelper.getShardIndex();// 获取分片参数

		//诉讼案件自动结案
		liQuiTaskScheduling.litigationCaseeClose();


		XxlJobHelper.handleSuccess(); // v2.3.0 设置任务结果

	}

}
