package com.pig4cloud.pig.casee.vo;

import lombok.Data;

/**
 * 诉前阶段
 */
@Data
public class PreLitigationStageVO {

	/**
	 * 诉前保全案件统计
	 */
	private Integer preservationCaseCount;

	/**
	 * 立案未送达统计
	 */
	private Integer startCaseeDeliveredCount;

	/**
	 * 未添加财产统计
	 */
	private Integer assetNotAddedCount;

	/**
	 * 查控保全未完成统计
	 */
	private Integer seizedUndoneCount;

	/**
	 * 保全完成未结案统计
	 */
	private Integer notClosedCount;

	/**
	 * 结案未送达统计
	 */
	private Integer CloseCaseeDeliveredCount;

}
