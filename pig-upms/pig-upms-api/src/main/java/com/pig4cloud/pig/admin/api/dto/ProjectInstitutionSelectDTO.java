package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.admin.api.dto
 * @ClassNAME: InstitutionSelectProjectDTO
 * @Author: yd
 * @DATE: 2022/2/26
 * @TIME: 12:12
 * @DAY_NAME_SHORT: 周六
 */
@Data
public class ProjectInstitutionSelectDTO {

	/**
	 * 项目机构id
	 */
	private Integer projectInsId;

	/**
	 * 机构类型 1000-运营平台 1100-拍辅机构 1200-清收机构 1300-律师事务所 1400-银行 1500-法院
	 */
	private Integer insType;

	/**
	 * 名称
	 */
	private String insName;

}
