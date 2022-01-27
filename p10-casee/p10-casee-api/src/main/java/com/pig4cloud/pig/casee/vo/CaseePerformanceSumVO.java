package com.pig4cloud.pig.casee.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 绩效汇总分组VO
 */
@Data
public class CaseePerformanceSumVO {

	/**
	 * 汇总时间
	 */
	@ApiModelProperty(value="汇总时间")
	private String sumTime;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value="用户id")
	private String userId;

	/**
	 * 汇总金额
	 */
	@ApiModelProperty(value="汇总金额")
	private BigDecimal perfAmountSum;

	/**
	 * 拼接用来做子查询
	 */
	@ApiModelProperty(value="拼接用来做子查询")
	private String sumTimeUserId;

	/**
	 * 明细
	 */
	@ApiModelProperty(value="明细")
	private List<CaseePerformanceVO> performanceVOs;

}
