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

package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户网点表
 *
 * @author yuanduo
 * @date 2021-09-03 10:52:47
 */
@Data
@TableName("p10_user_outles_staff_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户网点表")
public class UserOutlesStaffRe extends Model<UserOutlesStaffRe> {
	private static final long serialVersionUID = 1L;

	/**
	 * 网点id
	 */
	@ApiModelProperty(value = "网点id")
	private Integer outlesId;
	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Integer userId;
	/**
	 * 员工id
	 */
	@ApiModelProperty(value = "员工id")
	private Integer staffId;
	/**
	 * 机构id
	 */
	@ApiModelProperty(value = "机构id")
	private Integer insId;
	/**
	 * 权限类型 0-普通 1-管理
	 */
	@ApiModelProperty(value = "权限类型 0-普通 1-管理")
	private Integer roleType;

}
