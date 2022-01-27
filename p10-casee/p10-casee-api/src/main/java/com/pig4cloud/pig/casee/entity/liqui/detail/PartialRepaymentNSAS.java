package com.pig4cloud.pig.casee.entity.liqui.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.pig4cloud.pig.admin.api.entity.FileAdder;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 部分还款未签署和解协议
 */
@Data
public class PartialRepaymentNSAS {

	/**
	 * 协商还款时间
	 */
	@ApiModelProperty("协商还款时间")
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd")
	private LocalDateTime repaymentTime_BFHKWQSHJXY;

	/**
	 * 还款金额
	 */
	@ApiModelProperty("还款金额")
	private BigDecimal repaymentAmount_BFHKWQSHJXY;

	/**
	 * 回款率
	 */
	@ApiModelProperty("回款率")
	private BigDecimal rateOfReturn_BFHKWQSHJXY;

	/**
	 * 还款记录
	 */
	@ApiModelProperty("还款记录")
	private List<FileAdder> paymentHistoryUrl_BFHKWQSHJXY;

	/**
	 * 银行凭证
	 */
	@ApiModelProperty("银行凭证")
	private List<FileAdder> bankDocumentUrl_BFHKWQSHJXY;

	/**
	 * 其他
	 */
	@ApiModelProperty("其他")
	private List<FileAdder> otherUrl_BFHKWQSHJXY;

	/**
	 * 还款记录列表
	 */
	@ApiModelProperty("还款记录列表")
	private List<PaymentHistoryList> paymentHistoryList_BFHKWQSHJXY;

}
