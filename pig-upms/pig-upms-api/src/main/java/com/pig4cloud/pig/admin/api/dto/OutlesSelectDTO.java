package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

@Data
public class OutlesSelectDTO {

	/**
	 * 类型 1-合作网点，2-当前网点
	 */
	private Integer type;

	/**
	 * 机构id
	 */
	private Integer insId;

	/**
	 * 名称
	 */
	private String outlesName;
}
