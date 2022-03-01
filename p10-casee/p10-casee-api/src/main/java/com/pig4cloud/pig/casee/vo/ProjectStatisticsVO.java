package com.pig4cloud.pig.casee.vo;

import lombok.Data;

/**
 *接收处理
 */
@Data
public class ProjectStatisticsVO {

	/**
	 * 待接收统计
	 */
	private Integer pendingCount;

	/**
	 * 接收未处理统计
	 */
	private Integer notProcessedCount;
}
