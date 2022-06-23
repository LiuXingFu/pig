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

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 项目表DTO
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
 */
@Data
public class ProjectLiquiModifyDTO {

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 网点id
	 */
	@ApiModelProperty(value="网点id")
	private Integer outlesId;

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
	 * 接收时间
	 */
	@ApiModelProperty(value="接收时间")
	private LocalDate takeTime;

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

}
