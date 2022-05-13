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
public class CaseeHandlingRecordsDTO {

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

	/**
	 * 源id
	 */
	@ApiModelProperty(value="源id")
	private Integer sourceId;


	/**
	 * 类型
	 */
	@ApiModelProperty(value="类型")
	private Integer sourceType;

	/**
	 * 项目类型(100-清收 200-拍辅)
	 */
	@ApiModelProperty(value="项目类型(100-清收 200-拍辅)")
	private Integer projectType;

}
