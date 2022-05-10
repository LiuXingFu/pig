package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

@Data
public class InsOutlesCourtReSelectDTO {

	/**
	 * 机构id
	 */
	private Integer insId;

	/**
	 * 网点id
	 */
	private Integer outlesId;

	/**
	 * 名称
	 */
	private String courtName;
}
