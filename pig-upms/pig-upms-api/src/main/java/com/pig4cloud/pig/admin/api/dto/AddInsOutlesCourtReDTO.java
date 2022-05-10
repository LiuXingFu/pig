package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddInsOutlesCourtReDTO {

	Integer insId;

	/**
	 * 法院机构id
	 */
	Integer courtInsId;

	/**
	 * 入驻法院的网点id集合
	 */
	List<Integer> outlesIds;

}
