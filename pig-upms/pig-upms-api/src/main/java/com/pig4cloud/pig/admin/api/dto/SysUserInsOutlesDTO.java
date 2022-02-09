package com.pig4cloud.pig.admin.api.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SysUserInsOutlesDTO {

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Integer userId;

	/**
	 * 机构id
	 */
	@ApiModelProperty(value = "机构id")
	private Integer insId;

	/**
	 * 网点id
	 */
	@ApiModelProperty(value = "网点id")
	private Integer outlesId;

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
}
