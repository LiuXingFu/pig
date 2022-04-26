package com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity;

import com.pig4cloud.pig.casee.entity.CommonalityData;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 终结
 */
@Data
public class End extends CommonalityData implements Serializable {
	/**
	 * 终结日期
	 */
	private LocalDate endTime;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 终结类型(0-终结本次执行 1-终止本次执行)
	 */
	private Integer endType;

	/**
	 * 终结文书
	 */
	private String endPaperworkFile;



}
