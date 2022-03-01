package com.pig4cloud.pig.casee.dto;

import com.pig4cloud.pig.casee.entity.liquientity.LawsuitCaseeLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaseeLawsuitsDTO extends CaseeLiquiAddDTO {

	/**
	 * 类别（0-一审 1-二审 2-首执 3- 执恢）
	 */
	@ApiModelProperty(value = "类别（0-一审 1-二审 2-首执 3- 执恢）")
	private Integer category;

	/**
	 * 律师名称
	 */
	@ApiModelProperty(value = "律师名称")
	private String lawyerName;

	/**
	 * 案件json
	 */
	@ApiModelProperty(value = "案件json")
	private LawsuitCaseeLiqui lawsuitCaseeLiqui;
}
