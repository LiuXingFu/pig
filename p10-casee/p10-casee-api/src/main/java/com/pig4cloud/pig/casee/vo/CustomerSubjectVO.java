package com.pig4cloud.pig.casee.vo;

import lombok.Data;

@Data
public class CustomerSubjectVO {

	/**
	 * 客户id
	 */
	private Integer customerId;

	/**
	 * 主体名称
	 */
	private String subjectName;

	/**
	 * 客户类型 可推荐客户-10000 意向客户-20000 成交客户-30000 关联申请人-40000 其他关联客户-50000
	 */
	private Integer customerType;

	/**
	 * 推荐人
	 */
	private String recommenderName;

	/**
	 * 推荐机构名称
	 */
	private String insName;

	/**
	 * 推荐网点名称
	 */
	private String outlesName;

}
