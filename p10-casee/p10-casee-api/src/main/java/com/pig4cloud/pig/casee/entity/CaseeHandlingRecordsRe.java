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
 * @author yuanduo
 * @date 2022-05-05 16:34:49
 */
@Data
@TableName("p10_casee_handling_records_re")
@ApiModel(value = "案件办理记录关联表")
public class CaseeHandlingRecordsRe  {

    /**
     * 案件办理记录关联表id
     */
    @TableId
    @ApiModelProperty(value="案件办理记录关联表id")
    private Integer caseeHandlingRecordsReId;

	/**
	 * 案件办理记录id
	 */
	@ApiModelProperty(value="案件办理记录id")
	private Integer caseeHandlingRecordsId;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 项目类型(100-清收 200-拍辅)
	 */
	@ApiModelProperty(value="项目类型(100-清收 200-拍辅)")
	private Integer projectType;
}
