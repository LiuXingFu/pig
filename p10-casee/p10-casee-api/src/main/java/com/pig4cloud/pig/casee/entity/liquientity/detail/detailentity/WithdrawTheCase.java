package com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;

/**
 * 案件撤案
 */
@Data
public class WithdrawTheCase {

	/**
	 * 撤案日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private LocalDate withdrawalDate;

	/**
	 * 结案类型（1-退案 2-完成）
	 */
	private Integer endType;

	/**
	 * 文书
	 */
	private String paperwork;

	/**
	 * 备注
	 */
	private String remark;


}
