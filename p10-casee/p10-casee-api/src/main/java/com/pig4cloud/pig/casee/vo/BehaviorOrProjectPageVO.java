package com.pig4cloud.pig.casee.vo;


import com.pig4cloud.pig.casee.entity.Behavior;
import com.pig4cloud.pig.casee.entity.Project;
import lombok.Data;

@Data
public class BehaviorOrProjectPageVO extends Behavior {

	/**
	 * 项目id
	 */
	private Integer projectId;

	/**
	 * 公司业务案号
	 */
	private String companyCode;

	/**
	 * 项目状态
	 */
	private String status;

	/**
	 * 债务人
	 */
	private String subjectPersons;

	/**
	 * 执行案号
	 */
	private String caseeNumber;

}
