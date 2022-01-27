package com.pig4cloud.pig.casee.entity.paifu;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
/**
 * 双方议价 议价结果
 */
public class PaiFu_JGRZ_SFYJ_YJJG implements Serializable {
	/**议价成功 0-否 1-是 2-价格不采用*/
	private Integer bargainInquirySuccess;
	/**市场价*/
	private BigDecimal marketPrice;
	/**起拍价*/
	private BigDecimal startingPrice;
	/**拍卖平台 0-淘宝网 1-京东网 2-人民法院诉讼资产网 3-公拍网 4-中国拍卖行业协会网 5-公司银行融e购 6-北京产权交易所*/
	private Integer auctionPlatform;
	/**保证金*/
	private BigDecimal cashDeposit;
	/**加价幅度*/
	private BigDecimal markUp;
	/**附件地址*/
	private String fileUrl;
	/**图片地址*/
	private String imageUrl;
}
