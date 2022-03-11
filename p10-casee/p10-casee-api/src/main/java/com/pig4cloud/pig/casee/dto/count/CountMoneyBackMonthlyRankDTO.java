package com.pig4cloud.pig.casee.dto.count;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.dto.count
 * @ClassNAME: 回款额排名DTO
 * @Author: yd
 * @DATE: 2022/3/11
 * @TIME: 16:25
 * @DAY_NAME_SHORT: 周五
 */
@Data
public class CountMoneyBackMonthlyRankDTO {

	/**
	 * 办理人真实姓名
	 */
	private String managerName;

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
