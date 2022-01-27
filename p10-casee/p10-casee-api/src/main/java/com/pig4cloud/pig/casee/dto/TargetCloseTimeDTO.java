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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 标的表
 *
 * @author yy
 * @date 2021-11-05 16:28:49
 */
@Data
public class TargetCloseTimeDTO {

	/**
	 * 标的id
	 */
	@TableId
	@ApiModelProperty(value="标的id")
	private Integer targetId;

	/**
	 * 结案日期
	 */
	@ApiModelProperty(value="结案日期")
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd HH:mm:ss")
	private LocalDateTime closeTime;

	/**
	 * 说明
	 */
	@ApiModelProperty(value="说明")
	private String explain;

}
