package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaseeSubjectReDTO {

	/**
	 * 主体id
	 */
	@ApiModelProperty(value = "主体id")
	private Integer subjectId;

	/**
	 * 类型（0-申请人，1-借款人，2-共同借款人，3-担保人，4-案外人）
	 */
	@ApiModelProperty(value = "类型（0-申请人，1-借款人，2-共同借款人，3-担保人，4-案外人）")
	private Integer type;

}
