package com.pig4cloud.pig.casee.entity.liquientity.detail;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import java.math.BigDecimal;

@Data
@ApiModel(value = "清收项目详情表")
public class ProjectLiQuiDetail {

	/**
	 * 项目金额
	 */
	BigDecimal projectAmount;

	/**
	 * 回款金额
	 */
	BigDecimal repaymentAmount;


}
