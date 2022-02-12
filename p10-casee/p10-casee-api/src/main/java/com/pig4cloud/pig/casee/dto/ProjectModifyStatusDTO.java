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
public class ProjectModifyStatusDTO {

    /**
     * projectId
     */
    @ApiModelProperty(value="projectId")
    private Integer projectId;

    /**
     * 项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)
     */
    @ApiModelProperty(value="项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)")
    private Integer status;

	/**
	 * 变更时间
	 */
	@ApiModelProperty(value="变更时间")
	private LocalDate changeTime;

	/**
	 * 状态名称
	 */
	@ApiModelProperty(value="状态名称")
	private String statusName;

	/**
	 * 说明
	 */
	@ApiModelProperty(value="说明")
	private String describes;

}
