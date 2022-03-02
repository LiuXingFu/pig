package com.pig4cloud.pig.casee.vo.count;

import lombok.Data;

/**
 * 诉前阶段
 */
@Data
public class CountPreLitigationStageVO {

	/**
	 * 诉前保全案件统计
	 */
	private Long preservationCaseCount;

	/**
	 * 立案未送达统计
	 */
	private Long startCaseeDeliveredCount;

	/**
	 * 未添加财产统计
	 */
	private Long assetNotAddedCount;

	/**
	 * 查控保全未完成统计
	 */
	private Long seizedUndoneCount;

	/**
	 * 保全完成未结案统计
	 */
	private Long notClosedCount;

	/**
	 * 结案未送达统计
	 */
	private Long CloseCaseeDeliveredCount;

}
