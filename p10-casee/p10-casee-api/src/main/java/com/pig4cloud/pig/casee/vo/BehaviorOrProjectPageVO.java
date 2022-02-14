package com.pig4cloud.pig.casee.vo;


import com.pig4cloud.pig.casee.entity.Behavior;
import com.pig4cloud.pig.casee.entity.Project;
import lombok.Data;

@Data
public class BehaviorOrProjectPageVO extends Behavior {

	/**
	 * 项目信息
	 */
	private String companyCode;

	/**
	 * 执行案号
	 */
	private String caseeNumber;

}
