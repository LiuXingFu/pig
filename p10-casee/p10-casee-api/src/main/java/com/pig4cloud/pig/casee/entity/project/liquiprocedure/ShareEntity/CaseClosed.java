package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 结案
 */
@Data
public class CaseClosed extends CommonalityData implements Serializable {

	/**
	 * 结案日期
	 */
	private LocalDate caseClosedTime;


	/**
	 * 结案类型(0-退案 1-完成)
	 */
	private Integer caseClosedType;

	/**
	 * 结案文书
	 */
	private String closingDocuments;

	/**
	 * 备注
	 */
	private String remark;
}
