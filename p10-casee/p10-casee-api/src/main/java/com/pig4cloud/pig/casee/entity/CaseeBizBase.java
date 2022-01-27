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
package com.pig4cloud.pig.casee.entity;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pig4cloud.pig.casee.entity.detail.CaseeLiQuiDateDetail;
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
@ApiModel(value = "案件表")
public class CaseeBizBase extends BaseEntity {
	/**
	 * caseeId
	 */
	@TableId
	@ApiModelProperty(value="caseeId")
	private Integer caseeId;

	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@ApiModelProperty(value="删除标识（0-正常,1-删除）")
	private String delFlag;

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 执行案号
	 */
	@ApiModelProperty(value="执行案号")
	private String executionCaseNumber;

	/**
	 * 案件类型(0-清收,1-拍辅,2-财产核查)
	 */
	@ApiModelProperty(value="案件类型(100-清收,200-拍辅,300-财产核查)")
	private Integer caseeType;

	/**
	 * 案件状态(0-新建，1-开启、2-暂缓、3-中止、4-结案)
	 */
	@ApiModelProperty(value="案件状态(0-新建，1-开启、2-暂缓、3-中止、4-结案)")
	private Integer status;
	/**
	 * 状态确认(0-默认状态，1-待确认,2-已确认，3-已拒绝)
	 */
	@ApiModelProperty(value="状态确认(0-默认状态，1-待确认,2-已确认，3-已拒绝)")
	private Integer statusConfirm;
	/**
	 * 机构id
	 */
	@ApiModelProperty(value="机构id")
	private Integer createInsId;

	/**
	 * 网点id
	 */
	@ApiModelProperty(value="网点id")
	private Integer createOutlesId;

	/**
	 * 办理人id
	 */
	@ApiModelProperty(value="办理人id")
	private Integer userId;

	/**
	 * 办理人名称
	 */
	@ApiModelProperty(value="办理人名称")
	private String userNickName;

	/**
	 * 收案日期
	 */
	@ApiModelProperty(value="收案日期")
//	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
//	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime startTime;

	/**
	 * 结案日期
	 */
	@ApiModelProperty(value="结案日期")
//	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
//	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime closeTime;

	/**
	 * 年份
	 */
	@ApiModelProperty(value="年份")
	private String year;

	/**
	 * 简称
	 */
	@ApiModelProperty(value="简称")
	private String alias;

	/**
	 * 字号
	 */
	@ApiModelProperty(value="字号")
	private Integer word;

	/**
	 * 描述
	 */
	@ApiModelProperty(value="描述")
	private String describes;

	/**
	 * 所有申请人(用于显示，多个用，号隔开)
	 */
	@ApiModelProperty(value="所有申请人(用于显示，多个用，号隔开)")
	private String proposersNames;

	/**
	 * 所有被执行人(用于显示，多个用，号隔开)
	 */
	@ApiModelProperty(value="所有被执行人(用于显示，多个用，号隔开)")
	private String executedPersons;

	/**
	 * 案件详情数据
	 */
	@ApiModelProperty(value="案件详情数据")
	private String caseeDetail;



	/**
	 * 确认时间
	 */
	@ApiModelProperty(value="确认时间")
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime affirmTime;



}
