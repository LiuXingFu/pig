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
package com.pig4cloud.pig.casee.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pig4cloud.pig.casee.entity.CaseePerformance;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 案件绩效表
 *
 * @author xiaojun
 * @date 2021-09-07 17:44:19
 */
@Data
@TableName("p10_casee_performance")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "案件绩效表")
public class CaseePerformanceDTO extends CaseePerformance {


	/**
	 * 网点ID
	 */
	@ApiModelProperty(value="网点ID")
	private Integer outlesId;

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value="用户ID")
	private Integer userId;
	
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime beginDate;//开始时间

	@JsonFormat(timezone = "GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime endDate;//结束时间

	@ApiModelProperty(value="标的名称")
	private String targetName;//标的名称
	@ApiModelProperty(value="任务名称")
	private String nodeName;//任务名称









	@ApiModelProperty(value="分页对象")
	private Page page;

}
