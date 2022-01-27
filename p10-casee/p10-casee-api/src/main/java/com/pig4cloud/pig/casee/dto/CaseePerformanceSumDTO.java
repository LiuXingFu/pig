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

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pig4cloud.pig.casee.entity.CaseePerformance;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 案件绩效查询条件（app使用）
 *
 * @author ligt
 * @date 2021-09-07 17:44:19
 */
@Data
@ApiModel(value = "案件绩效查询DTO")
public class CaseePerformanceSumDTO {


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

	@ApiModelProperty(value="查询关键字")
	private String keyword;//查询关键字

	@ApiModelProperty(value="任务名称")
	private String nodeName;//任务名称

	@ApiModelProperty(value="业务案号")
	private String companyCode;

	@ApiModelProperty(value="月份")
	private String month;//2021-12

	@ApiModelProperty(value="分页对象")
	private Page page;

}
