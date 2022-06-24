package com.pig4cloud.pig.casee.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class QueryCaseeLiquiPageVO {

	/**
	 * 项目id
	 */
	private Integer projectId;

	/**
	 * 公司业务案号
	 */
	private String companyCode;

	/**
	 * 案件id
	 */
	private Integer caseeId;

	/**
	 * 案号
	 */
	private String caseeNumber;

	/**
	 * 案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)
	 */
	private Integer caseeType;

	/**
	 * 案件状态(0-待立案 1-在办 2- 撤案 3-结案 4-终结)
	 */
	private Integer status;

	/**
	 * 法院名称
	 */
	private String courtName;

	/**
	 * 法官名称
	 */
	private String judgeName;

	/**
	 * 案件总金额
	 */
	private BigDecimal caseeAmount;

	/**
	 * 司法费金额
	 */
	private BigDecimal judicialExpenses;

	/**
	 * 收案日期
	 */
	@ApiModelProperty(value="收案日期")
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd")
	private LocalDateTime startTime;

	/**
	 * 结案日期
	 */
	@ApiModelProperty(value="结案日期")
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd")
	private LocalDateTime closeTime;

	/**
	 * 被执行人
	 */
	private String executedName;

	/**
	 * 申请人
	 */
	private String applicantName;

}
