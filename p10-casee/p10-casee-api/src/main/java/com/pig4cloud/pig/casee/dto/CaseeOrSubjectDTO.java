package com.pig4cloud.pig.casee.dto;

import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.dto
 * @ClassNAME: CaseeOrSubjectDTO
 * @Author: yd
 * @DATE: 2022/2/20
 * @TIME: 16:08
 * @DAY_NAME_SHORT: 周日
 */
@Data
public class CaseeOrSubjectDTO {

	/**
	 * 案件id
	 */
	private Integer caseeId;

	/**
	 * 类型（0-申请人/原告/上述人/申请执行人等，1-被告/被执行人/被上述人等）
	 */
	private Integer caseePersonnelType;

}
