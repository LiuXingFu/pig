package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.admin.api.entity.Subject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class ProjectSubjectVO extends Subject {

	/**
	 * 类型（0-申请人，1-借款人，2-共同借款人，3-担保人）
	 */
	@ApiModelProperty(value="类型（0-申请人，1-借款人，2-共同借款人，3-担保人）")
	private Integer type;

	/**
	 * 项目主体关联id
	 */
	@ApiModelProperty(value="项目主体关联id")
	private Integer subjectReId;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String describes;
}
