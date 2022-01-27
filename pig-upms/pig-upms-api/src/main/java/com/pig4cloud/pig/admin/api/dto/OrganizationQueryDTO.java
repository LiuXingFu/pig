package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

@Data
public class OrganizationQueryDTO {

	/**
	 * 类型 0-关联 1-当前 2-不包括当前+关联数据
	 */
	private Integer type;
	/**
	 * 当前登录机构id
	 */
	private Integer loginInsId;

	/**
	 * 当前登录的网点
	 */
	private Integer loginOutlesId;

	/**
	 * 当前登录的用户id
	 */
	private Integer loginUserId;

	/**
	 * 机构类型 1000 运营平台 1100 拍辅机构 1200 清收机构 1300 律师事务所 1400 银行 1500 法院
	 */
	private Integer insType;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 机构id
	 */
	private Integer insId;
	/**
	 * 网点id
	 */
	private Integer outlesId;

	/**
	 * 网点名称
	 */
	private String outlesName;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 回显机构id
	 */
	private Integer echoInsId;

	/**
	 * 回显网点id
	 */
	private Integer echoOutlesId;

	/**
	 * 回显用户id
	 */
	private Integer echoUserId;
}
