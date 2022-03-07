package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class BehaviorLiquiDebtorPageDTO {


	/**
	 * 公司业务案号/案件案号
	 */
	@ApiModelProperty(value = "公司业务案号/案件案号")
	private String caseeNumber;

	/**
	 * 债务人
	 */
	@ApiModelProperty(value="债务人")
	private String executedName;

	// 立案时间范围搜索
	/**
	 * 开始时间
	 */
	@ApiModelProperty(value="开始时间")
	private LocalDate beginDate;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value="结束时间")
	private LocalDate endDate;

	/**
	 * 节点KEY
	 */
	@ApiModelProperty(value="节点KEY")
	private String nodeKey;

	/**
	 * 行为类型(100-行为限制 200-行为违法)
	 */
	@ApiModelProperty(value="行为类型(100-行为限制 200-行为违法)")
	private Integer type;
}
