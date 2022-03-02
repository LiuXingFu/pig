package com.pig4cloud.pig.casee.vo.count;

import lombok.Data;

/**
 *接收处理
 */
@Data
public class CountProjectStatisticsVO {

	/**
	 * 待接收统计
	 */
	private Long pendingCount;

	/**
	 * 接收未处理统计
	 */
	private Long notProcessedCount;
}
