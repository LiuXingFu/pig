package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CaseeLiquiFlowChartPageVO extends CaseeLiqui {

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 代理律师名称
	 */
	@ApiModelProperty(value="代理律师名称")
	private String lawyerName;

	/**
	 * 法院名称
	 */
	@ApiModelProperty(value="法院名称")
	private String courtName;

	/**
	 * 办理人名称
	 */
	@ApiModelProperty(value="办理人名称")
	private String userName;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 节点id
	 */
	@ApiModelProperty(value="节点id")
	private String nodeId;

	/**
	 * 项目机构id
	 */
	@ApiModelProperty(value="项目机构id")
	private Integer insId;

	/**
	 * 项目网点id
	 */
	@ApiModelProperty(value="项目网点id")
	private Integer outlesId;

	/**
	 * 法院到款
	 */
	@ApiModelProperty(value="法院到款")
	private BigDecimal courtSum;

}
