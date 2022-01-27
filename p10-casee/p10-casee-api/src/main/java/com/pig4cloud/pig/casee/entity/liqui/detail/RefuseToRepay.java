package com.pig4cloud.pig.casee.entity.liqui.detail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 拒绝还款
 */
@Data
public class RefuseToRepay {

	/**
	 * 备注
	 */
	@ApiModelProperty("备注")
	private String remark_JJHK;

}
