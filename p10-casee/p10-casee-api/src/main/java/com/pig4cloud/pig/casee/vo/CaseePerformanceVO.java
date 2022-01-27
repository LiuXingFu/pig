package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.CaseePersonnel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CaseePerformanceVO {

	/**
	 * 绩效id
	 */
	@ApiModelProperty(value = "绩效id")
	private String perfId;
	/**
	 * 节点名称
	 */
	@ApiModelProperty(value = "节点名称")
	private String nodeName;
	/**
	 * 标的名称
	 */
	@ApiModelProperty(value = "标的名称")
	private String targetName;
	/**
	 * 业务案号
	 */
	@ApiModelProperty(value = "业务案号")
	private String companyCode;

	/**
	 * 金额
	 */
	@ApiModelProperty(value = "金额")
	private BigDecimal perfAmount;
	/**
	 * 绩效状态（0：不计费，1：计费）
	 */
	@ApiModelProperty(value = "绩效状态（0：不计费，1：计费）")
	private BigDecimal perfStatus;


	/**
	 * 任务更新时间
	 */
	@ApiModelProperty(value = "任务更新时间")
	private LocalDateTime updateTime;

	/**
	 * 标的业务类型
	 */
	@ApiModelProperty(value = "标的业务类型")
	private Integer businessType;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	@Data
	public static class CaseePersonnelVO extends CaseePersonnel {

		/**
		 * 类型（0-申请人，1-被执行人，2-担保人）
		 */
		private Integer personnelReType;

	}
}
