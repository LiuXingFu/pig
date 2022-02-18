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
	 * 财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））
	 */
	@ApiModelProperty(value="财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））")
	private Integer assetsType;

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

	/**
	 * 主体id
	 */
	@ApiModelProperty(value="主体id")
	private Integer subjectId;

}
