package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.admin.api.entity.Subject;
import lombok.Data;

/**
 * 	案件主体关联案件表主体DTO
 */
@Data
public class SubjectOptionVO extends Subject {

	/**
	 * 案件id
	 */
	private Integer caseeId;

	/**
	 * 类型（0-申请人/原告/上述人/申请执行人等，1-被告/被执行人/被上述人等）
	 */
	private Integer type;

}
