package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.admin.api.dto
 * @ClassNAME: InsOutlesUserByOutlesDTO
 * @Author: yd
 * @DATE: 2022/2/8
 * @TIME: 20:14
 * @DAY_NAME_SHORT: 周二
 */
@Data
public class InsOutlesUserByOutlesDTO {

	//机构id
	private Integer insId;

	//网点id
	private Integer outlesId;

	//真实姓名
	private String actualName;

	//类型
	private Integer type;

}
