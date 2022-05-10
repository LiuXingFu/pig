package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

@Data
public class InsOutlesCourtReQueryDTO {

	/**
	 * 机构id
	 */
	private Integer insId;

	/**
	 * 网点id
	 */
	private Integer outlesId;

	/**
	 * 法院id
	 */
	private Integer courtId;

	/**
	 * 法院机构id
	 */
	private Integer courtInsId;

}
