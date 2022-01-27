package com.pig4cloud.pig.casee.entity.paifu;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
/**
 * 拍卖结果
 */
public class PaiFu_PMFZ_PMJG_PMJG implements Serializable {
	/**拍卖结果 0-成交 1-流拍 2-撤回*/
	private Integer auctionResultsType;
	/**市场价*/
	private BigDecimal marketPrice;
	/**成交价*/
	private BigDecimal finalPrice;
	/**应收拍辅费*/
	private BigDecimal auxiliaryFee;
	/**附件地址*/
	private String fileUrl;
	/**图片地址*/
	private String imageUrl;
	/**尾款缴纳时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date finalPaymentTime;
	/**撤回原因*/
	private String reasonForWithdrawal;
	/**抵偿价*/
	private BigDecimal offsetPrice;
	/**抵偿时间*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date offsetTime;
	/**接受抵偿人/公司名称*/
	private String offsetName;
	/**身份证/统一社会信用代码/组织机构代码*/
	private String offsetIdentity;
}
