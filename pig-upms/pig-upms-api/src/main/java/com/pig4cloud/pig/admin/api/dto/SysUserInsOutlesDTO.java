package com.pig4cloud.pig.admin.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserInsOutlesDTO {

	/**
	 * 网点名称
	 */
	@ApiModelProperty(value = "网点名称")
	private String outlesName;

	/**
	 * 真实姓名
	 */
	@ApiModelProperty(value = "真实姓名")
	private String actualName;

	/**
	 * 电话
	 */
	@ApiModelProperty(value = "电话")
	private String phone;
}
