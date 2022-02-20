package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.admin.api.entity.Subject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaseeSubjectVO extends Subject {

	/**
	 * 类型（0-申请人，1-债务人）
	 */
	@ApiModelProperty(value="类型（0-申请人，1-贷款人，2-担保人）")
	private Integer type;
}
