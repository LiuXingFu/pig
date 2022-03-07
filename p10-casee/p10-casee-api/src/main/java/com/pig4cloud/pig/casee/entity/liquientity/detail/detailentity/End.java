package com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;

/**
 * 案件实际执结
 */
@Data
public class End {

	/**
	 * 终结日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private LocalDate endDate;

	/**
	 * 终结类型（0-终结本次执行 1-终止本次执行）
	 */
	private Integer endType;

	/**
	 * 终结文书
	 */
	private String paperwork;

	/**
	 * 备注
	 */
	private String remark;


}
