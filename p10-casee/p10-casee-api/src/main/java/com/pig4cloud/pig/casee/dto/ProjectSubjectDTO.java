package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ProjectSubjectDTO {

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 债务人名称
	 */
	@ApiModelProperty(value="债务人名称")
	private String subjectName;
}
