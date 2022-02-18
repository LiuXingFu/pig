package com.pig4cloud.pig.casee.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.dto
 * @ClassNAME: AssetsOrProjectPageDTO
 * @Author: yd
 * @DATE: 2022/2/17
 * @TIME: 22:05
 * @DAY_NAME_SHORT: 周四
 */
@Data
public class AssetsOrProjectPageDTO {

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 财产名称
	 */
	@ApiModelProperty(value="财产名称")
	private String assetsName;

	/**
	 * 债务人名称
	 */
	@ApiModelProperty(value="债务人名称")
	private String debtorName;

	/**
	 * 财产账号/编号
	 */
	@ApiModelProperty(value="财产账号/编号")
	private String accountNumber;

}
