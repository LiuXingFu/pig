package com.pig4cloud.pig.casee.dto;


import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;


@Data
public class CaseeLiquiModifyStatusDTO {

	/**
	 * 项目id
	 */
	private Integer projectId;

	/**
	 * 案件id
	 */
	private Integer caseeId;

	/**
	 * 案件状态(0-待立案 1-在办 2- 撤案 3-结案 4-终结)
	 */
	@ApiModelProperty(value="案件状态(0-待立案 1-在办 2- 撤案 3-结案 4-终结)")
	private Integer status;

	/**
	 * 状态选择类型名称
	 */
	@ApiModelProperty(value="状态选择类型名称")
	private String statusNameType;

	/**
	 * 变更时间
	 */
	@ApiModelProperty(value="变更时间")
	private LocalDate changeTime;

	/**
	 * 描述
	 */
	@ApiModelProperty(value="描述")
	private String describes;

	/**
	 * 附件
	 */
	@ApiModelProperty(value="附件")
	private String appendix;
}
