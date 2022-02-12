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
package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 项目表
 *
 * @author pig code generator
 * @date 2022-02-10 17:30:36
 */
@Data
public class ProjectLiquiPageVO extends ProjectLiqui {


	@ApiModelProperty(value="诉讼情况(0-未诉讼 1-已诉讼)")
	private Integer litigation;

	@ApiModelProperty(value="移送金额")
	private BigDecimal handoverAmount;

}
