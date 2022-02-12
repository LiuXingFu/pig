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
	 * 借贷合同
	 */
	@ApiModelProperty(value="借贷合同")
	private String loanContract;

	/**
	 * 诉讼开始日期
	 */
	@ApiModelProperty(value="诉讼开始日期")
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd")
	private LocalDate litigationStart;

	/**
	 * 其它文件
	 */
	@ApiModelProperty(value="其它文件")
	private String otherFiles;

	/**
	 * 抵押情况（0-有，1-无）
	 */
	@ApiModelProperty(value="抵押情况（0-有，1-无）")
	private Integer mortgageSituation;


	/**
	 * 移交金额
	 */
	@ApiModelProperty(value="移交金额")
	private BigDecimal transfer;

	/**
	 * 诉讼情况
	 */
	@ApiModelProperty(value="诉讼情况")
	private Integer litigation;
}
