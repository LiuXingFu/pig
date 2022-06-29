package com.pig4cloud.pig.casee.dto;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.entity.liquientity.LawsuitCaseeLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CaseeSecondInstanceDTO extends CaseeLiquiAddDTO {

	/**
	 * 案外人信息
	 */
	@ApiModelProperty(value = "案外人信息")
	private List<Subject> subjectList;

	/**
	 * 案件json
	 */
	@ApiModelProperty(value = "案件json")
	private LawsuitCaseeLiqui lawsuitCaseeLiqui;
}
