package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.liquientity.BehaviorLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 业务流程图债务人分页返回vo
 */
@Data
public class CaseeLiquiDebtorPageVO extends BehaviorLiqui {

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 案件
	 */
	@ApiModelProperty(value="案件")
	private Integer caseeId;

	/**
	 * 案号
	 */
	@ApiModelProperty(value="案号")
	private String caseeNumber;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value="节点id")
	private String nodeId;
}
