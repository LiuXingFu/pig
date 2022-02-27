package com.pig4cloud.pig.casee.dto;

import com.pig4cloud.pig.admin.api.entity.Subject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CaseeSecondInstanceDTO extends CaseeLiquiAddDTO {

	/**
	 * 律师名称
	 */
	@ApiModelProperty(value = "律师名称")
	private String lawyerName;

	/**
	 * 案外人信息
	 */
	@ApiModelProperty(value = "案外人信息")
	private List<Subject> subjectList;
}
