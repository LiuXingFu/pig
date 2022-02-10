package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

@Data
public class InstitutionSelectDTO {

	/**
	 * 类型 1-合作机构，2-当前机构
	 */
	private Integer type;

	/**
	 * 机构类型 1000-运营平台 1100-拍辅机构 1200-清收机构 1300-律师事务所 1400-银行 1500-法院
	 */
	private Integer insType;

	/**
	 * 名称
	 */
	private String insName;
}
