package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CaseeSubjectDTO {

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

	/**
	 * 债务人名称
	 */
	@ApiModelProperty(value="债务人名称")
	private String subjectName;

	/**
	 * 类型（0-申请人/原告/上述人/申请执行人等，1-被告/被执行人/被上述人等）
	 */
	@ApiModelProperty(value="类型（0-申请人/原告/上述人/申请执行人等，1-被告/被执行人/被上述人等）")
	private Integer caseePersonnelType;
}
