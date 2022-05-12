package com.pig4cloud.pig.casee.entity.paifuentity.excel;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 拍辅导入数据实体
 */
@Data
public class ImportPaifu {

	/**
	 * 案号（业务案号）
	 */
	private String companyCode;

	/**
	 * 执行案号（法院案号）
	 */
	private String caseeNumber;

	/**
	 * 申请执行人(联系方式) （申请人/原告/上诉人/申请执行人等）
	 */
	private String applicantName;

	/**
	 * 被执行人（联系方式）（被告/被执行人/被上诉人等）
	 */
	private String executedName;

	/**
	 * 所有权人
	 */
	private String owner;

	/**
	 * 标的物
	 */
	private String assetsName;

	/**
	 * 标的性质 (财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））)
	 */
	private Integer assetsType;

	/**
	 * 标的面积（㎡）/重量
	 */
	private String unit;

	/**
	 * 收案日期
	 */
	private LocalDate startTime;

	/**
	 * 承办法官
	 */
	private String judgeName;

	/**
	 * 财产定价方式 (定价方式(0-议价 1-网络询价 2-询价 3-评估))
	 */
	private Integer pricingManner;

	/**
	 * 参考价 (定价价格)
	 */
	private BigDecimal listPrice;

	/**
	 * 拍卖平台(0-淘宝司法拍卖 1-京东司法拍卖 2-公拍网 3-人民法院诉讼资产网 4-中国拍卖行业协会 5-工商银行融e购 6-北京产权交易所 7-其它网拍平台)
	 */
	private Integer auctionPlatform;

	/**
	 * 公告发布时间(一次)
	 */
	private LocalDate onceAnnouncementStartTime;

	/**
	 * 开拍日期（拍卖开始日期）(一次)
	 */
	private LocalDate onceAuctionStartTime;

	/**
	 * 起拍价格（起拍价）(一次)
	 */
	private BigDecimal onceStartingPrice;

	/**
	 * 成交价格(一次)
	 */
	private BigDecimal onceDealPrice;

	/**
	 * 拍卖情况（10-成交，20-流拍，抵偿-30，撤案-40，未挂拍-50）(一次)
	 */
	private Integer  onceAuctionResults;

	/**
	 * 发布日期（公告发布时间）(二次)
	 */
	private LocalDate secondaryAnnouncementStartTime;

	/**
	 * 开拍日期（拍卖开始日期）(二次)
	 */
	private LocalDate secondaryAuctionStartTime;

	/**
	 * 起拍价格（起拍价）(二次)
	 */
	private BigDecimal secondaryStartingPrice;

	/**
	 * 成交价格(二次)
	 */
	private BigDecimal secondaryDealPrice;

	/**
	 * 拍卖情况（10-成交，20-流拍，抵偿-30，撤案-40）(二次)
	 */
	private Integer  secondaryAuctionResults;

	/**
	 * 发布日期（公告发布时间）(变卖)
	 */
	private LocalDate sellOffAnnouncementStartTime;

	/**
	 * 开拍时间（拍卖开始日期）(变卖)
	 */
	private LocalDate sellOffAuctionStartTime;

	/**
	 * 结束时间(拍卖结束日期)
	 */
	private LocalDate auctionEndTime;

	/**
	 * 变卖价格(变卖)
	 */
	private BigDecimal sellOffPrice;

	/**
	 * 成交价格(变卖)
	 */
	private BigDecimal sellOffDealPrice;

	/**
	 * 拍卖情况（10-成交，20-流拍）(变卖)
	 */
	private Integer  sellOffAuctionResults;

	/**
	 * 成交或抵偿日期
	 */
	private LocalDate closingDate;

	/**
	 * 应收费用
	 */
	private BigDecimal feesPayable;

	/**
	 * 开票日期
	 */
	private LocalDate BillingDate;

	/**
	 * 领款时间
	 */
	private LocalDate collectionTime;

	/**
	 * 到款日期
	 */
	private LocalDate paymentDate;

	/**
	 * 实收费用（合计 元）
	 */
	private BigDecimal actualCost;

	/**
	 * 结案日期
	 */
	private LocalDate closeTime;

	/**
	 * 办理人
	 */
	private String userName;

	/**
	 * 备注
	 */
	private String remark;

}
