package com.pig4cloud.pig.casee.taskscheduling.impl;

import com.pig4cloud.pig.casee.service.CaseeLiquiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.taskscheduling.impl
 * @ClassNAME: LiQuiTaskSchedulingImpl
 * @Author: yd
 * @DATE: 2022/3/3
 * @TIME: 15:01
 * @DAY_NAME_SHORT: 周四
 */
@Component
public class LiQuiTaskSchedulingImpl {

	@Autowired
	CaseeLiquiService caseeLiquiService;

	/**
	 * 诉讼案件自动结案
	 */
	public void litigationCaseeClose() {
		caseeLiquiService.litigationCaseeClose();
	}

}
