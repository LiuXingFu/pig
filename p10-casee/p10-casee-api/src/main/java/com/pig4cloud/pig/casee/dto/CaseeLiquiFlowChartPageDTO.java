package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CaseeLiquiFlowChartPageDTO {

	/**
	 * 案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件,，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)
	 */
	@ApiModelProperty(value="案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件,，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)")
	private Integer caseeType;

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
	 * 办理人名称
	 */
	@ApiModelProperty(value="办理人名称")
	private String actualName;

	/**
	 * 案件状态(0-待立案 1-在办 2- 撤案 3-结案 4-终结)
	 */
	@ApiModelProperty(value="案件状态(0-待立案 1-在办 2- 撤案 3-结案 4-终结) ")
	private Integer status;

	/**
	 * 节点KEY
	 */
	@ApiModelProperty(value="节点KEY")
	private String nodeKey;

	/**
	 * 程序目标类型 (10001：案件, 20001：财产，30001：行为)
	 */
	@ApiModelProperty(value="程序目标类型 (10001：案件, 20001：财产，30001：行为)")
	private Integer goalType;
}
