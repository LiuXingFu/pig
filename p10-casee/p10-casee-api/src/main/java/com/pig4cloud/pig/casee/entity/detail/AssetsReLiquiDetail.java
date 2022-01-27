package com.pig4cloud.pig.casee.entity.detail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@ApiModel(value = "清收移交财产详情")
public class AssetsReLiquiDetail {

	/**
	 * 抵押金额
	 */
	@ApiModelProperty(value="抵押金额")
	private BigDecimal pledgeMoney;

	/**
	 * 抵押时间
	 */
	@ApiModelProperty(value="抵押时间")
	private LocalDate pledgeTime;
}
