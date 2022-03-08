package com.pig4cloud.pig.casee.vo.count;

import lombok.Data;

/**
 * 诉讼阶段
 */
@Data
public class CountLitigationVO {

	/*****************一审案件********************************/

	/**
	 * 一审诉讼案件
	 */
	private Long litigationFirstInstance;

	/**
	 * 立案未送达
	 */
	private Long litigationFirstInstanceStandCaseUndelivered;

	/**
	 * 送达未庭审
	 */
	private Long litigationFirstInstanceServiceNotCourtTrial;

	/**
	 * 庭审未判决
	 */
	private Long litigationFirstInstanceCourtTrialNotJudgment;

	/**
	 * 裁判文书未送达
	 */
	private Long litigationFirstInstanceJudgmentPaperworkNotService;

	/**
	 * 上诉确认
	 */
	private Long litigationFirstInstanceAppealConfirmation;

	/**
	 * 上诉到期未处理
	 */
	private Long litigationFirstInstanceAppealExpired;

	/*****************保全案件********************************/

	/**
	 * 诉讼保全案件
	 */
	private Long litigationHold;

	/**
	 * 立案未送达
	 */
	private Long litigationHoldStandCaseUndelivered;

	/**
	 * 送达未添加财产
	 */
	private Long litigationHoldServiceNotAddProperty;

	/**
	 * 查控保全未完成
	 */
	private Long litigationHoldCheckControlPreservationUndone;

	/**
	 * 保全完成未结案
	 */
	private Long litigationHoldPreservationCompleteNotKnotCase;

	/**
	 * 结案未送达
	 */
	private Long litigationHoldKnotCaseUndelivered;

	/*****************二审案件********************************/

	/**
	 * 二审诉讼案件
	 */
	private Long litigationSecondInstance;

	/**
	 * 立案未送达
	 */
	private Long litigationSecondInstanceStandCaseUndelivered;

	/**
	 * 送达未庭审
	 */
	private Long litigationSecondInstanceServiceNotCourtTrial;

	/**
	 * 庭审未判决
	 */
	private Long litigationSecondInstanceCourtTrialNotJudgment;

	/**
	 * 裁判文书未送达
	 */
	private Long litigationSecondInstanceJudgmentPaperworkNotService;

	/**
	 * 发回重审未处理*******************************
	 */
	private Long litigationSecondInstanceRevertRetrialNotProcessed;

	/*****************其它案件********************************/

	/**
	 * 其他案件
	 */
	private Long litigationOthers;

	/**
	 * 立案未送达
	 */
	private Long litigationOthersStandCaseUndelivered;

	/**
	 * 送达未庭审
	 */
	private Long litigationOthersServiceNotCourtTrial;

	/**
	 * 庭审未判决
	 */
	private Long litigationOthersCourtTrialNotJudgment;

	/**
	 * 判决未送达
	 */
	private Long litigationOthersJudgmentNotService;

	/**
	 * 送达未生效********************************
	 */
	private Long litigationOthersServiceNotActive;

}
