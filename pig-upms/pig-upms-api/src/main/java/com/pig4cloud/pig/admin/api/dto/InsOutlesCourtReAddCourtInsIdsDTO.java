package com.pig4cloud.pig.admin.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class InsOutlesCourtReAddCourtInsIdsDTO {

	Integer insId;

	/**
	 * 法院机构id
	 */
	List<Integer> courtInsIds;

	/**
	 * 入驻法院的网点id集合
	 */
	Integer outlesId;
}
