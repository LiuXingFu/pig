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

/**
 * 案件办理记录表
 *
 * @author Mjh
 * @date 2022-03-10 18:05:58
 */
@Data
@TableName("p10_casee_handling_records")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "案件办理记录表")
public class CaseeHandlingRecords extends BaseEntity {

    /**
     * 案件办理记录id
     */
    @TableId
    @ApiModelProperty(value="案件办理记录id")
    private Integer caseeHandlingRecordsId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;

    /**
     * 节点id
     */
    @ApiModelProperty(value="节点id")
    private String nodeId;

	/**
	 * 节点名称
	 */
	@ApiModelProperty(value="节点名称")
	private String nodeName;

	/**
	 * 0-新增 1-补录
	 */
	@ApiModelProperty(value="0-新增 1-补录")
	private Integer submissionStatus;

    /**
     * 节点办理数据
     */
    @ApiModelProperty(value="节点办理数据")
    private String formData;

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
	 * 程序id
	 */
	@ApiModelProperty(value="程序id")
	private Integer targetId;



}
