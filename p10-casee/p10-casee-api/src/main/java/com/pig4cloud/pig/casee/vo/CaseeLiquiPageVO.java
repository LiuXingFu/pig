package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CaseeLiquiPageVO extends CaseeLiqui {

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 代理律师名称
	 */
	@ApiModelProperty(value="代理律师名称")
	private String lawyerName;

	/**
	 * 法院名称
	 */
	@ApiModelProperty(value="法院名称")
	private String courtName;

	/**
	 * 办理人名称
	 */
	@ApiModelProperty(value="办理人名称")
	private String userName;

}
