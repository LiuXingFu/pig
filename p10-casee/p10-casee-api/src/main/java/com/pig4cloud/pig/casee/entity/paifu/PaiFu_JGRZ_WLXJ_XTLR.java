package com.pig4cloud.pig.casee.entity.paifu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
/**
 * 网络询价 系统录入
 */
public class PaiFu_JGRZ_WLXJ_XTLR implements Serializable {
	/**询价结果 0-否 1-是 2-价格不采用*/
	private Integer bargainInquirySuccess;
	/**法院确定市场价*/
	private BigDecimal courtMarketPrice;
	/**法院附件地址*/
	private String courtUrl;
	/**出具报告单位1  0-京东 1-淘宝 2-工商*/
	private Integer reportingUnit1;
	/**市场价1*/
	private BigDecimal marketPrice1;
	/**网络询价时间1*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date networkInquiryTime1;
	/**附件地址1*/
	private String fileUrl1;
	/**出具报告单位2  0-京东 1-淘宝 2-工商*/
	private Integer reportingUnit2;
	/**市场价2*/
	private BigDecimal marketPrice2;
	/**网络询价时间2*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date networkInquiryTime2;
	/**附件地址2*/
	private String fileUrl2;
	/**出具报告单位3  0-京东 1-淘宝 2-工商*/
	private Integer reportingUnit3;
	/**市场价3*/
	private BigDecimal marketPrice3;
	/**网络询价时间3*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date networkInquiryTime3;
	/**附件地址3*/
	private String fileUrl3;
	/**附件地址*/
	private String fileUrl;
	/**图片地址*/
	private String imageUrl;
}
