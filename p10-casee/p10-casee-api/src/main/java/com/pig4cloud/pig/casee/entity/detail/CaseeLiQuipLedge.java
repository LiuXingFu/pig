package com.pig4cloud.pig.casee.entity.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CaseeLiQuipLedge {
	/**
	 * 抵押类型
	 */
	@ApiModelProperty(value="抵押类型")
	private Integer targetType;

	/**
	 * 所有权人
	 */
	@ApiModelProperty(value="所有权人")
	private String owner;

	/**
	 * 抵押名称
	 */
	@ApiModelProperty(value="抵押名称")
	private String targetName;

	/**
	 * 省
	 */
	@ApiModelProperty(value="省")
	private String province;

	/**
	 * 市
	 */
	@ApiModelProperty(value="市")
	private String city;

	/**
	 * 区
	 */
	@ApiModelProperty(value="区")
	private String area;

	/**
	 * 行政区划编号
	 */
	@ApiModelProperty(value = "行政区划编号")
	private String code;


	/**
	 * 抵押详细地址
	 */
	@ApiModelProperty(value="抵押详细地址")
	private String informationAddress;
	/**
	 * 抵押金额
	 */
	@ApiModelProperty(value="抵押金额")
	private BigDecimal pledgeMoney;

	/**
	 * 抵押时间
	 */
	@ApiModelProperty(value="抵押时间")
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd")
	private LocalDateTime pledgeTime;

	/**
	 * 是否存在租赁
	 */
	@ApiModelProperty(value="是否存在租赁")
	private Integer isLease;


	/**
	 * 被执行人唯一编号
	 */
	@ApiModelProperty(value="被执行人唯一编号")
	private String executedUnifiedIdentity;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;



}
