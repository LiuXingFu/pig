package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

@Data
public class UserSelectDTO {

	/**
	 * 类型：1-管理员，2-普通员工
	 */
	private Integer type;

	/**
	 * 机构id
	 */
	private Integer insId;

	/**
	 * 网点id
	 */
	private Integer outlesId;

	/**
	 * 真实姓名
	 */
	private String actualName;
}
