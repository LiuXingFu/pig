package com.pig4cloud.pig.casee.taskscheduling.impl;

import com.pig4cloud.pig.casee.service.AuctionRecordService;
import com.pig4cloud.pig.casee.service.CaseeLiquiService;
import com.pig4cloud.pig.casee.service.MortgageAssetsRecordsService;
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

	@Autowired
	AuctionRecordService auctionRecordService;

	@Autowired
	MortgageAssetsRecordsService mortgageAssetsRecordsService;

	/**
	 * 诉讼案件自动结案
	 */
	public void litigationCaseeClose() {
		caseeLiquiService.litigationCaseeClose();
	}

	/**
	 * 刷新拍卖状态
	 */
	public void refreshAuctionStatus(){
		auctionRecordService.refreshAuctionStatus();
	}

	public void synchronize(){
		mortgageAssetsRecordsService.synchronize();
	}
}
