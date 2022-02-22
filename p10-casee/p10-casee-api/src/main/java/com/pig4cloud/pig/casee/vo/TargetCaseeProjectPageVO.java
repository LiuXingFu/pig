package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.vo
 * @ClassNAME: TargetCaseeProjectPageVO
 * @Author: yd
 * @DATE: 2022/2/21
 * @TIME: 17:39
 * @DAY_NAME_SHORT: 周一
 */
@Data
public class TargetCaseeProjectPageVO  extends CaseeLiqui {

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

	/**
	 * 程序id
	 */
	private Integer targetId;

}
