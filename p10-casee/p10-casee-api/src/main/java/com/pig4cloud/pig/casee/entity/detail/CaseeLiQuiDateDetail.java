package com.pig4cloud.pig.casee.entity.detail;

import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.liqui.RepaymentDetails;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@ApiModel(value = "清收案件详情表")
public class CaseeLiQuiDateDetail {

	/**
	 * 借贷合同
	 */
	@ApiModelProperty(value="借贷合同")
	private String loanContract;

	/**
	 * 抵押情况（0-有，1-无）
	 */
	@ApiModelProperty(value="抵押情况（0-有，1-无）")
	private Integer mortgageSituation;


	/**
	 * 抵押详情
	 */
	@ApiModelProperty(value="抵押详情")
	private List<CaseeLiQuipLedge> pledgeList;


	/**
	 * 本金
	 */
	@ApiModelProperty(value="本金")
	private BigDecimal principal;

	/**
	 * 利息
	 */
	@ApiModelProperty(value="利息")
	private BigDecimal interest;

	/**
	 * 异常说明
	 */
	@ApiModelProperty(value="异常说明")
	private String exceptionDescription;

	/**
	 * 结案说明
	 */
	@ApiModelProperty(value="结案说明")
	private String claseeCloseDescription;

	/**
	 * 还款详情
	 */
	@ApiModelProperty(value="还款详情")
	private RepaymentDetails  repaymentDetails;

	/**
	 * 还款总金额
	 */
	@ApiModelProperty(value="还款总金额")
	private BigDecimal  repaymentSum;

	/**
	 * 案件总金额
	 */
	@ApiModelProperty(value="案件总金额")
	private BigDecimal  sumMoney;

	/**
	 * 回款率
	 */
	@ApiModelProperty(value="回款率")
	private double repaymentRate;



}
