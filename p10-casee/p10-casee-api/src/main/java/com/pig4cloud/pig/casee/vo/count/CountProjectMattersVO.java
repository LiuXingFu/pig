package com.pig4cloud.pig.casee.vo.count;

import lombok.Data;

@Data
public class CountProjectMattersVO {

	/**
	 * 待接收统计
	 */
	private Long pendingCount;

	/**
	 * 项目在办
	 */
	private Long projectInProgressCount;

	/**
	 * 项目暂缓
	 */
	private Long projectSuspendCount;

	/**
	 * 提醒事项
	 */
	private Long remindMatterCount;

	/**
	 * 债务人
	 */
	private Long debtorCount;

}
