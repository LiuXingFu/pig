package com.pig4cloud.pig.casee.entity.liquientity.detail;

import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CaseeLiquiDetail {

	/**
	 * 一审、二审和其他案件最终送达时间
	 */
	private LocalDate finalReceiptTime;

	/**
	 * 案件终结
	 */
	private End end;

	/**
	 * 案件实际执结
	 */
	private ActualExecution actualExecution;

	/**
	 * 案件撤案
	 */
	private WithdrawTheCase withdrawTheCase;

	/**
	 * 一审裁判结果
	 */
	private FirstTrialRefereeResult firstTrialRefereeResult;

	/**
	 * 二审裁判结果
	 */
	private SecondTrialRefereeResult secondTrialRefereeResult;

	/**
	 * 其他裁判结果
	 */
	private OtherRefereeResult otherRefereeResult;
}
