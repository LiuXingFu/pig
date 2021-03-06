package com.pig4cloud.pig.casee.entity.liquientity.detail;

import com.pig4cloud.pig.casee.entity.ExpenseRecord;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@ApiModel(value = "移送详情表")
public class TransferRecordLiquiDetail {

	/**
	 * 本金
	 */
	@ApiModelProperty(value="本金")
	private BigDecimal principal;

	/**
	 * 利率
	 */
	@ApiModelProperty(value="利率")
	private BigDecimal interestRate;

	/**
	 * 利息
	 */
	@ApiModelProperty(value="利息")
	private BigDecimal interest;

	/**
	 * 本金利息总额
	 */
	@ApiModelProperty(value="本金利息总额")
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

	/**
	 * 诉讼案号
	 */
	@ApiModelProperty(value="诉讼案号")
	private String lawsuitNumber;

	/**
	 * 诉讼费
	 */
	@ApiModelProperty(value="诉讼费")
	private BigDecimal litigationCosts;

	/**
	 * 未判决利息起算日
	 */
	@ApiModelProperty(value="未判决利息起算日")
	private LocalDate unjudgedInterestStartDate;

	/**
	 * 主动履行到期日
	 */
	@ApiModelProperty(value="主动履行到期日")
	private LocalDate activeFulfillmentDueDate;

	/**
	 * 费用产生记录集合
	 */
	private List<ExpenseRecord> expenseRecordList;
}
