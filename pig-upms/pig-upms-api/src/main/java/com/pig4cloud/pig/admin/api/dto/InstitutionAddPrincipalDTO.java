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

package com.pig4cloud.pig.admin.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class InstitutionAddPrincipalDTO {

	/**
	 * 机构id
	 */
	@ApiModelProperty(value = "机构id")
	private Integer insId;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Integer userId;

	/**
	 * 真实姓名
	 */
	@ApiModelProperty(value = "真实姓名")
	private String actualName;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String phone;
}
