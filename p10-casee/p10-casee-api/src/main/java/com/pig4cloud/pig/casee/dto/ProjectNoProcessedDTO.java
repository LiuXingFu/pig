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

import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;

/**
 * 移送记录表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:40
 */
@Data

public class ProjectNoProcessedDTO {
	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 办理人名称
	 */
	@ApiModelProperty(value="办理人名称")
	private String  userNickName;

	// 接收时间范围搜索
	/**
	 * 开始时间
	 */
	@ApiModelProperty(value="开始时间")
	private LocalDate beginDate;

	/**
	 * 结束时间
	 */
	@ApiModelProperty(value="结束时间")
	private LocalDate endDate;

	/**
	 * 债务人
	 */
	@ApiModelProperty(value="债务人")
	private String executedName;



}
