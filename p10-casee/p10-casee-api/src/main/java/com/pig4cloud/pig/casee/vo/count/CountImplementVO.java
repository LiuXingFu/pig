package com.pig4cloud.pig.casee.vo.count;

import lombok.Data;

/**
 * 诉讼阶段
 */
@Data
public class CountImplementVO {

	/*****************首执案件********************************/

	/**
	 * 首次执行案件
	 */
	private Long chiefExecutive;

	/**
	 * 首执立案未送达
	 */
	private Long chiefExecutiveStandCaseUndelivered;

	/**
	 * 首执送达未查控***********************
	 */
	private Long chiefExecutiveHeadExecuteServiceNotCheckControl;

	/*****************执恢案件********************************/

	/**
	 * 恢复执行案件
	 */
	private Long reinstate;

	/**
	 * 恢复执行待立案
	 */
	private Long reinstateResumeExecutionTreatFileACase;

	/**
	 * 执恢立案未送达
	 */
	private Long reinstateStandCaseUndelivered;

	/**
	 * 执恢送达未查控*************************
	 */
	private Long reinstateExecuteRestoreServiceUnchecked;

	/**
	 * 到款未拨付
	 */
	private Long paymentNotPaid;

	/**
	 * 结清未实际执结**********************
	 */
	private Long settleNotActualExecution;

}
