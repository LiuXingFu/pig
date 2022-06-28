package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class CaseeLiquiModifyDTO {

	/**
	 * 案件id
	 */
	@ApiModelProperty(value = "案件id")
	private Integer caseeId;

	/**
	 * 案号
	 */
	@ApiModelProperty(value = "案号")
	private String caseeNumber;

	/**
	 * 立案日期
	 */
	@ApiModelProperty(value = "立案日期")
	private LocalDate startTime;

	/**
	 * 案件受理费
	 */
	@ApiModelProperty(value = "案件受理费")
	private BigDecimal judicialExpenses;

	/**
	 * 承办法院id
	 */
	@ApiModelProperty(value = "承办法院id")
	private Integer courtId;

	/**
	 * 承办法官id
	 */
	@ApiModelProperty(value = "承办法官id")
	private Integer judgeId;

	/**
	 * 法官名称
	 */
	@ApiModelProperty(value = "法官名称")
	private String judgeName;

	/**
	 * 案件总金额
	 */
	@ApiModelProperty(value = "案件总金额")
	private BigDecimal caseeAmount;

	/**
	 * 办理人id
	 */
	@ApiModelProperty(value = "办理人id")
	private Integer userId;

	/**
	 * 办理人名称
	 */
	@ApiModelProperty(value = "办理人名称")
	private String actualName;

	/**
	 * 附件
	 */
	@ApiModelProperty(value="附件")
	private String appendix;

	/**
	 * 律师名称
	 */
	@ApiModelProperty(value = "律师名称")
	private String lawyerName;

}
