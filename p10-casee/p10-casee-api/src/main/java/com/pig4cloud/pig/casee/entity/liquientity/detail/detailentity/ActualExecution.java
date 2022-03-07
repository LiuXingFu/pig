package com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.time.LocalDate;

/**
 * 案件实际执结
 */
@Data
public class ActualExecution {

	/**
	 * 结案日期
	 */
	@JSONField(format="yyyy-MM-dd")
	private LocalDate closingDate;

	/**
	 * 执结类型（0-自动履行完毕 1-强制执行完毕 2-和解履行完毕 3-半强制执行半自动履行）
	 */
	private Integer knotType;

	/**
	 * 结案申请文书
	 */
	private String paperwork;

	/**
	 * 备注
	 */
	private String remark;


}
