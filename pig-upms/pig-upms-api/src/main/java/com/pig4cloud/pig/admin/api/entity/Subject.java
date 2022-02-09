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
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 主体
 *
 * @author yy
 * @date 2021-09-17 16:55:57
 */
@Data
@TableName("p10_subject")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "主体")
public class Subject extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 主体id
	 */
	@TableId
	@ApiModelProperty(value = "主体id")
	private Integer subjectId;

	/**
	 * 删除状态(0-正常,1-已删除)
	 */
	@ApiModelProperty(value = "删除状态(0-正常,1-已删除)")
	private String delFlag;

	/**
	 * 统一标识(身份证/统一社会信用代码/组织机构代码)
	 */
	@ApiModelProperty(value = "统一标识(身份证/统一社会信用代码/组织机构代码)")
	private String unifiedIdentity;

	/**
	 * 性质类型 0-个人 1-企业/公司
	 */
	@ApiModelProperty(value = "性质类型 0-个人 1-企业/公司")
	private Integer natureType;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 联系方式
	 */
	@ApiModelProperty(value = "联系方式")
	private String phone;

	/**
	 * 法人
	 */
	@ApiModelProperty(value = "法人")
	private String legalPerson;

	/**
	 * 电子邮件
	 */
	@ApiModelProperty(value = "电子邮件")
	private String email;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 是否认证(0-否，1-是)
	 */
	@ApiModelProperty(value = "是否认证(0-否，1-是)")
	private Integer isAuthentication;

	/**
	 * 用户id
	 */
	@ApiModelProperty(value = "用户id")
	private Integer userId;

	/**
	 * 工作单位
	 */
	@ApiModelProperty(value = "工作单位")
	private String employer;


}
