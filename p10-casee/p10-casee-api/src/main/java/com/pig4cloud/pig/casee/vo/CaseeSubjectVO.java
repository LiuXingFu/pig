package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.admin.api.entity.Subject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaseeSubjectVO extends Subject {

	/**
	 * 类型（0-申请人，1-借款人，2-共同借款人，3-担保人）
	 */
	@ApiModelProperty(value="类型（0-申请人，1-借款人，2-共同借款人，3-担保人）")
	private Integer type;
}
