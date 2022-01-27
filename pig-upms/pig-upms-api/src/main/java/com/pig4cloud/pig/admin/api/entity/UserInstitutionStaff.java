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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * 员工表
 *
 * @author yuanduo
 * @date 2021-09-06 15:20:47
 */
@Data
@TableName("p10_user_institution_staff")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "员工表")
public class UserInstitutionStaff extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 员工ID 主键自增
	 */
	@TableId
	@ApiModelProperty(value = "员工ID 主键自增")
	private Integer staffId;
	/**
	 * 用户ID
	 */
	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	/**
	 * 机构ID
	 */
	@ApiModelProperty(value = "机构ID")
	private Integer insId;
	/**
	 * 职称
	 */
	@ApiModelProperty(value = "职称")
	private String jobTitle;
	/**
	 * 入职时间
	 */
	@JsonDeserialize(using = LocalDateTimeDeserializer.class)
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	@ApiModelProperty(value = "入职时间")
	private LocalDateTime entryTime;
	/**
	 * 0-正常 1-删除
	 */
	@TableLogic
	private Integer delFlag;
}
