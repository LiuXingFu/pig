package com.pig4cloud.pig.casee.entity.liquientity.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "清收项目详情表")
public class ProjectLiQuiDetail {

	/**
	 * 项目金额
	 */
	BigDecimal projectAmount;

	/**
	 * 剩余回款
	 */
	BigDecimal remainingPayment;

}
