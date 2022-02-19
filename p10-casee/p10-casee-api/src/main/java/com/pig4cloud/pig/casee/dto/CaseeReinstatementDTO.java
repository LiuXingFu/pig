package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaseeReinstatementDTO extends CaseeLiquiAddDTO {

	/**
	 * 父案件id
	 */
	@ApiModelProperty(value = "父案件id")
	private Integer parentId;

	/**
	 * 类别（0-一审 1-二审 2-首执 3- 执恢）
	 */
	@ApiModelProperty(value = "类别（0-一审 1-二审 2-首执 3- 执恢）")
	private Integer category;
}
