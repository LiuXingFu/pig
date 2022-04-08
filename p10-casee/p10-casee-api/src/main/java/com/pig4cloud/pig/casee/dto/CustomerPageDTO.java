package com.pig4cloud.pig.casee.dto;

import lombok.Data;

@Data
	public class CustomerPageDTO {

	/**
	 * 主体名称
	 */
	private String subjectName;

	/**
	 * 推荐人
	 */
	private String phone;

	/**
	 * 客户类型 可推荐客户-10000 意向客户-20000 成交客户-30000 关联申请人-40000 其他关联客户-50000
	 */
	private Integer customerType;

	/**
	 * 机构id
	 */
	private Integer insId;

	/**
	 * 网点id
	 */
	private Integer outlesId;

}
