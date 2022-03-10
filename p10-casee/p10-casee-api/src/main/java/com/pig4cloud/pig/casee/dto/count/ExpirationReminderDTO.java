package com.pig4cloud.pig.casee.dto.count;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 到期提醒
 */
@Data
public class ExpirationReminderDTO {

	/**
	 * 公司业务案号/案件案号
	 */
	@ApiModelProperty(value="公司业务案号/案件案号")
	private String companyCode;

	/**
	 * 债务人名称
	 */
	@ApiModelProperty(value="债务人名称")
	private String executedName;

	/**
	 * 开始时间
	 */
	@ApiModelProperty(value="开始时间")
	private LocalDate beginDate;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value="结束时间")
	private LocalDate endDate;

}
