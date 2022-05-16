/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.casee.vo.paifu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 项目表
 *
 * @author pig code generator
 * @date 2022-02-10 17:30:36
 */
@Data
public class ProjectPaifuExportVO  {

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 案号
	 */
	@ApiModelProperty(value="案号")
	private String caseeNumber;

	/**
	 * 申请人/原告/上诉人/申请执行人等
	 */
	@ApiModelProperty(value="申请人/原告/上诉人/申请执行人等")
	private String applicantName;

	/**
	 * 被告/被执行人/被上诉人等
	 */
	@ApiModelProperty(value="被告/被执行人/被上诉人等")
	private String executedName;

	/**
	 * 所有权人
	 */
	@ApiModelProperty(value="所有权人")
	private String owner;

	/**
	 * 财产名称
	 */
	@ApiModelProperty(value="财产名称")
	private String assetsName;

	/**
	 * 财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））
	 */
	@ApiModelProperty(value="财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））")
	private Integer assetsType;

	/**
	 * 立案日期
	 */
	@ApiModelProperty(value="立案日期")

	private String startTime;

	/**
	 * 法官名称
	 */
	@ApiModelProperty(value="法官名称")
	private String judgeName;

	/**
	 * 定价方式(0-议价 1-网络询价 2-询价 3-评估)
	 */
	private Integer pricingManner;

	/**
	 * 定价费用
	 */
	private BigDecimal pricingFee;

	/**
	 * 拍卖平台
	 */
	private Integer auctionPlatform;

	/**
	 * 退出日期
	 */
	@ApiModelProperty(value="退出日期")
	private String closeTime;

	/**
	 * 办理人名称
	 */
	@ApiModelProperty(value="办理人名称")
	private String userNickName;

	//-----------一拍--------------------

	/**
	 * 一拍公告发布时间
	 */
	@ApiModelProperty(value="一拍公告发布时间")
	private String oneBeatAnnouncementStartTime;
	/**
	 * 一拍开拍时间
	 */
	@ApiModelProperty(value="一拍开拍时间")
	private String oneBeatAuctionStartTime;
	/**
	 * 一拍结束时间
	 */
	@ApiModelProperty(value="一拍结束时间")
	private String oneBeatAuctionEndTime;
	/**
	 * 一拍起拍价
	 */
	private BigDecimal oneBeatStartingPrice;
	/**
	 * 一拍成交价格
	 */
	private BigDecimal oneBeatDealPrice;
	/**
	 * 结果类型（10-成交，20-流拍，30-撤回，40-中止，50-抵偿）
	 */
	@ApiModelProperty(value="结果类型（10-成交，20-流拍，30-撤回，40-中止，50-抵偿）")
	private Integer oneBeatResultsType;

	//-----------二拍--------------------

	/**
	 * 二拍公告发布时间
	 */
	@ApiModelProperty(value="二拍公告发布时间")
	private String twoBeatAnnouncementStartTime;
	/**
	 * 二拍开拍时间
	 */
	@ApiModelProperty(value="二拍开拍时间")
	private String twoBeatAuctionStartTime;
	/**
	 * 二拍结束时间
	 */
	@ApiModelProperty(value="二拍结束时间")
	private String twoBeatAuctionEndTime;
	/**
	 * 二拍起拍价
	 */
	private BigDecimal twoBeatStartingPrice;
	/**
	 * 二拍成交价格
	 */
	private BigDecimal twoBeatDealPrice;
	/**
	 * 结果类型（10-成交，20-流拍，30-撤回，40-中止，50-抵偿）
	 */
	@ApiModelProperty(value="结果类型（10-成交，20-流拍，30-撤回，40-中止，50-抵偿）")
	private Integer twoBeatResultsType;

	//-----------变卖--------------------

	/**
	 * 变卖公告发布时间
	 */
	@ApiModelProperty(value="变卖公告发布时间")
	private String sellOffAnnouncementStartTime;
	/**
	 * 变卖开拍时间
	 */
	@ApiModelProperty(value="变卖开拍时间")
	private String sellOffAuctionStartTime;
	/**
	 * 变卖结束时间
	 */
	@ApiModelProperty(value="变卖结束时间")
	private String sellOffAuctionEndTime;
	/**
	 * 变卖起拍价
	 */
	private BigDecimal sellOffStartingPrice;
	/**
	 * 变卖成交价格
	 */
	private BigDecimal sellOffDealPrice;
	/**
	 * 结果类型（10-成交，20-流拍，30-撤回，40-中止，50-抵偿）
	 */
	@ApiModelProperty(value="结果类型（10-成交，20-流拍，30-撤回，40-中止，50-抵偿）")
	private Integer sellOffResultsType;

	/**
	 * 回款时间
	 */
	@ApiModelProperty(value="回款时间")
	private String paymentDate;

	/**
	 * 回款金额
	 */
	@ApiModelProperty(value="回款金额")
	private BigDecimal paymentAmount;

}
