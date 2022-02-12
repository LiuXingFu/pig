package com.pig4cloud.pig.casee.entity.liquientity.detail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ApiModel(value = "移送详情表")
public class TransferRecordLiquiDetail {

	/**
	 * 移送金额
	 */
	@ApiModelProperty(value="移送金额")
	private BigDecimal handoverAmount;

	/**
	 * 诉讼情况(0-未诉讼 1-已诉讼)
	 */
	@ApiModelProperty(value="诉讼情况(0-未诉讼 1-已诉讼)")
	private Integer litigation;

	/**
	 * 申请诉讼/执行时效开始时间
	 */
	@ApiModelProperty(value="申请诉讼/执行时效开始时间")
	private LocalDate startingTime;
}
