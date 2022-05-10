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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * 项目/案件状态表
 *
 * @author ligt
 * @date 2022-01-18 15:21:05
 */
@Data
@TableName("p10_project_status")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "项目/案件状态表")
public class ProjectStatus extends BaseEntity {

    /**
     * 状态记录id
     */
    @TableId
    @ApiModelProperty(value="状态记录id")
    private Integer statusId;

    /**
     * 类型（1-项目，2-案件，3-财产）
     */
    @ApiModelProperty(value="类型（1-项目，2-案件，3-财产）")
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
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;

    /**
     * 创建人名称
     */
    @ApiModelProperty(value="创建人名称")
    private String userName;

	/**
	 * 状态名称
	 */
	@ApiModelProperty(value="状态名称")
	private String statusName;

	/**
	 * 变更时间
	 */
	@ApiModelProperty(value="变更时间")
	private LocalDate changeTime;

	/**
	 * 状态详情
	 */
	@ApiModelProperty(value="状态详情")
	private String statusDetail;

	/**
	 * 描述
	 */
	@ApiModelProperty(value="描述")
	private String describes;
}
