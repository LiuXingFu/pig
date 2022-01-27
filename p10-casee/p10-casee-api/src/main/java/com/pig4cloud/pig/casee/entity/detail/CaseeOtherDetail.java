package com.pig4cloud.pig.casee.entity.detail;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaseeOtherDetail {
	/**
	 * 代理律师
	 */
	@ApiModelProperty(value="代理律师")
	private String attorney;
}
