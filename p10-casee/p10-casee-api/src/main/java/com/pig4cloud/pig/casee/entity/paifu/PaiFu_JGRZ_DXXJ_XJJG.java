package com.pig4cloud.pig.casee.entity.paifu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
/**
 * 定向询价 询价结果
 */
public class PaiFu_JGRZ_DXXJ_XJJG implements Serializable {
	/**询价成功 0-否 1-是 2-价格不采用*/
	private Integer bargainInquirySuccess;
	/**询价对象 0-价格认证中心 1-税务局 2-其它*/
	private Integer inquiryObject;
	/**市场价*/
	private BigDecimal marketPrice;
	/**有效期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date validPeriod;
	/**附件地址*/
	private String fileUrl;
	/**图片地址*/
	private String imageUrl;
}
