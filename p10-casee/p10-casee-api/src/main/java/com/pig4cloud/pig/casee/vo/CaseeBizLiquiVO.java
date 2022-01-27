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
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pig4cloud.pig.casee.entity.CaseeBizLiqui;
import com.pig4cloud.pig.casee.entity.CaseeOutlesDealRe;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 案件表
 *
 * @author yy
 * @date 2021-09-15 10:03:22
 */
@Data
@TableName("p10_casee")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "清收案件VO")
public class CaseeBizLiquiVO extends CaseeBizLiqui {

	 /**
	 * 受托机构
	 */
	@ApiModelProperty(value="受托机构名称")
	private String insName;
	/**
	 * 受托网点
	 */
	@ApiModelProperty(value="受托网点名称")
	private String outlesName;

	@ApiModelProperty(value="机构网点关联表")
	private CaseeOutlesDealRe caseeOutlesDealRe;
	/**
	 * 委托网点id
	 */
	@ApiModelProperty(value="委托网点id")
	private Integer entrustOutlesId;
	/**
	 * 委托网点名称
	 */
	@ApiModelProperty(value="委托网点名称")
	private String entrustOutlesName;


	/**
	 * 委托机构id
	 */
	@ApiModelProperty(value="委托机构id")
	private Integer entrustInsId;
	/**
	 * 委托机构名称
	 */
	@ApiModelProperty(value="委托机构名称")
	private String entrustInsName;


	//机构关联表数据
	/**
	 * 机构关联表id
	 */
	@ApiModelProperty(value="机构关联表id")
	private Integer dealReId;

	/**
	 * 机构关联案件状态
	 */
	@ApiModelProperty(value="机构关联案件状态")
	private Integer dealReStatus;

	/**
	 * 机构关联状态确认
	 */
	@ApiModelProperty(value="状态确认")
	private Integer statusConfirm;

	/**
	 * 是否可办理（0-不可办理，1-可办理）
	 */
	@ApiModelProperty(value="是否可办理（0-不可办理，1-可办理）")
	private Integer canDeal;










}
