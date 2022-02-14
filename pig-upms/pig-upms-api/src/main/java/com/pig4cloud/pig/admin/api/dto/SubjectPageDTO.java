package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.admin.api.dto
 * @ClassNAME: SubjectPageDTO
 * @Author: yd
 * @DATE: 2022/2/13
 * @TIME: 15:13
 * @DAY_NAME_SHORT: 周日
 */
@Data
public class SubjectPageDTO {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 联系方式
	 */
	private String phone;

	/**
	 * 统一标识(身份证/统一社会信用代码/组织机构代码)
	 */
	private String unifiedIdentity;

	/**
	 * 性质类型 0-个人 1-企业/公司
	 */
	private Integer natureType;
}
