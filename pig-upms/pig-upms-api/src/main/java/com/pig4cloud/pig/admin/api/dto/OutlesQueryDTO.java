package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class OutlesQueryDTO {

	/**
	 * 机构id
	 */
	private Integer insId;

	/**
	 * 角色类型 0-平台管理员 10000-其他
	 */
	private Integer roleType;

	/**
	 * 网点名称
	 */
	private String outlesName;

	/**
	 * 网点id集合
	 */
	private List<Integer> outlesIds;

}
