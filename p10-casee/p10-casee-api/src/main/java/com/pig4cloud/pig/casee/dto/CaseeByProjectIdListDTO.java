package com.pig4cloud.pig.casee.dto;

import lombok.Data;

import java.util.List;

@Data
public class CaseeByProjectIdListDTO {
	/**
	 * 项目id
	 */
	private Integer projectId;
	/**
	 * 案件类型
	 */
	private List<Integer> caseeType;
}
