package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.admin.api.dto
 * @ClassNAME: ProjectOutlesSelectDTO
 * @Author: yd
 * @DATE: 2022/2/26
 * @TIME: 14:42
 * @DAY_NAME_SHORT: 周六
 */
@Data
public class ProjectOutlesSelectDTO {

	/**
	 * 选中机构id
	 */
	private Integer insId;

	/**
	 * 项目机构id
	 */
	private Integer projectInsId;

	/**
	 * 名称
	 */
	private String outlesName;

}
