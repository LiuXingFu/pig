package com.pig4cloud.pig.casee.vo.count;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 到期提醒
 */
@Data
public class ExpirationReminderVO {

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
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

	/**
	 * 案号
	 */
	@ApiModelProperty(value="案号")
	private String caseeNumber;

	/**
	 * 业务id
	 */
	@ApiModelProperty(value="业务id")
	private Integer businessId;

	/**
	 * 业务名称
	 */
	@ApiModelProperty(value="业务名称")
	private String businessName;

	/**
	 * 债务人名称
	 */
	@ApiModelProperty(value="债务人名称")
	private String executedName;

	/**
	 * 到期时间
	 */
	@ApiModelProperty(value="到期时间")
	private LocalDate expireDate;

	/**
	 * 剩余天数
	 */
	@ApiModelProperty(value="剩余天数")
	private Integer remainingDays;

	/**
	 * 提醒类型
	 */
	@ApiModelProperty(value="提醒类型")
	private Integer reminderType;

}
