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
 * 项目表
 *
 * @author pig code generator
 * @date 2022-02-10 17:30:36
 */
@Data
public class ProjectStatusSaveDTO {

	/**
	 * 类型（1-项目 2-案件）
	 */
	@ApiModelProperty(value="类型（1-项目 2-案件）")
	private Integer type;

	/**
	 * 状态值
	 */
	@ApiModelProperty(value="状态值")
	private Integer statusVal;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

	/**
	 * 源id
	 */
	@ApiModelProperty(value="源id")
	private Integer sourceId;

	/**
	 * 状态名称
	 */
	@ApiModelProperty(value="状态名称")
	private String statusName;

	/**
	 * 状态选择类型名称
	 */
	@ApiModelProperty(value="状态选择类型名称")
	private String statusNameType;

	/**
	 * 变更时间
	 */
	@ApiModelProperty(value="变更时间")
	private LocalDate changeTime;

	/**
	 * 描述
	 */
	@ApiModelProperty(value="描述")
	private String describes;

	/**
	 * 附件
	 */
	@ApiModelProperty(value="附件")
	private String appendix;

}
