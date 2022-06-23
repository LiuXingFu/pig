package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

@Data
public class CourtExcelDTO {

	/**
	 * 法院名称
	 */
	private String courtName;

	/**
	 * 地区别称
	 */
	private String areaAnotherName;

	/**
	 * 法院代字
	 */
	private String courtCode;

	/**
	 * 区划代码
	 */
	private String divisionCode;

}
