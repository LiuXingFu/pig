/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.casee.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.entity.liquientity.BehaviorLiqui;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 行为表
 *
 * @author yuanduo
 * @date 2022-02-14 15:51:27
 */
@Data
public class BehaviorDetailVO extends BehaviorLiqui {

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)
	 */
	@ApiModelProperty(value="项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)")
	private Integer status;

	/**
	 * 项目总金额
	 */
	@ApiModelProperty(value="项目总金额")
	BigDecimal projectAmount;

	/**
	 * 接收时间
	 */
	@ApiModelProperty(value="接收时间")
	private LocalDate takeTime;

	/**
	 * 所有委托机构名称(用于显示，多个用，号隔开)
	 */
	@ApiModelProperty(value="所有委托机构名称(用于显示，多个用，号隔开)")
	private String proposersNames;

	/**
	 * 所有债务人名称(用于显示，多个用，号隔开)
	 */
	@ApiModelProperty(value="所有债务人名称(用于显示，多个用，号隔开)")
	private String subjectPersons;

	/**
	 * 案号
	 */
	@ApiModelProperty(value="案号")
	private String caseeNumber;

	/**
	 * 立案日期
	 */
	@ApiModelProperty(value="立案日期")
	private LocalDate startTime;

	/**
	 * 法官名称
	 */
	@ApiModelProperty(value="法官名称")
	private String judgeName;

	/**
	 * 案件受理费
	 */
	@ApiModelProperty(value="案件受理费")
	private BigDecimal judicialExpenses;

	/**
	 * 法院名称
	 */
	@ApiModelProperty(value="法院名称")
	private String courtName;

}
