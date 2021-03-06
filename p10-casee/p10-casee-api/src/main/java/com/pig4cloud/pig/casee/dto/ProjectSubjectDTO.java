package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

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

	/**
	 * 类型（0-申请人，1-借款人，2-共同借款人，3-担保人）
	 */
	@ApiModelProperty(value="类型（0-申请人，1-借款人，2-共同借款人，3-担保人）")
	private List<Integer> typeList;
}
