package com.pig4cloud.pig.casee.dto.paifu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AssetsReTargetPageDTO {

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String caseeNumber;

	/**
	 * 财产名称
	 */
	@ApiModelProperty(value="财产名称")
	private String assetsName;

	/**
	 * 所有权人
	 */
	@ApiModelProperty(value="所有权人")
	private String owner;

	/**
	 * 财产账号/编号
	 */
	@ApiModelProperty(value="财产账号/编号")
	private String accountNumber;

	@ApiModelProperty(value="开始时间")
	private LocalDate beginDate;

	@ApiModelProperty(value="结束时间")
	private LocalDate endDate;

}
