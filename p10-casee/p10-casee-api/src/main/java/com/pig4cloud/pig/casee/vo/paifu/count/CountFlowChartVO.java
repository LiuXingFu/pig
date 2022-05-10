package com.pig4cloud.pig.casee.vo.paifu.count;

import lombok.Data;

/**
 * 统计财产程序
 */
@Data
public class CountFlowChartVO {

	/**
	 * 待接收统计
	 */
	private Long pendingCount;

	/**
	 * 动产未现勘
	 */
	private Long chattelNotAvailable;

	/**
	 * 不动产未现勘
	 */
	private Long realEstateNotSurveyed;

	/**
	 * 不动产现勘未入户
	 */
	private Long realEstateSurveyNotRegistered;

	/**
	 * 拍卖价格依据未出具
	 */
	private Long auctionPriceBasisNotIssued;

	/**
	 * 有依据未上拍
	 */
	private Long thereIsEvidenceNotListed;

	/**
	 * 公告期未拍卖
	 */
	private Long announcementPeriodNotAuctioned;

	/**
	 * 拍卖到期无结果
	 */
	private Long auctionExpiresWithoutResults;

	/**
	 * 拍卖成交未处理
	 */
	private Long auctionTransactionNotProcessed;

	/**
	 * 拍卖不成交未处理
	 */
	private Long auctionTransactionFailedNotProcessed;

	/**
	 * 拍卖异常未撤销
	 */
	private Long auctionExceptionNotCancelled;

	/**
	 * 到款/抵偿未裁定
	 */
	private Long arrivalCompensationNotAdjudicated;

	/**
	 * 裁定未送达
	 */
	private Long rulingNotService;

	/**
	 * 送达未腾退
	 */
	private Long deliveredButNotVacated;

}
