package com.pig4cloud.pig.casee.entity.liqui.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 还款记录列表
 */
@Data
public class PaymentHistoryList {

	/**
	 * 还款人身份证号 (被执行人身份证号)
	 */
	private String repayerIDCard;

	/**
	 * 还款人名称
	 */
	private String repayerName;

	/**
	 * 还款日期
	 */
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd")
	private Date repaymentTime;

	/**
	 * 还款金额
	 */
	private BigDecimal repaymentAmount;

	/**
	 * 备注
	 */
	private String laborRemuneration;
}
