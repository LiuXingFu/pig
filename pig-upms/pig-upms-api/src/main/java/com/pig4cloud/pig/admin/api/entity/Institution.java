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
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机构表
 *
 * @author yuanduo
 * @date 2021-09-02 16:16:06
 */
@Data
@TableName("p10_institution")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "机构表")
public class Institution extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 机构id 主键自增
	 */
	@TableId
	@ApiModelProperty(value = "机构id 主键自增")
	private Integer insId;
	/**
	 * 机构名称
	 */
	@ApiModelProperty(value = "机构名称")
	private String insName;
	/**
	 * 机构类型
	 */
	@ApiModelProperty(value = "机构类型")
	private Integer insType;
	/**
	 * 地址id
	 */
	@ApiModelProperty(value = "地址id")
	private Integer addressId;
	/**
	 * 是否禁用 0-否 1-是
	 */
	@ApiModelProperty(value = "是否禁用 0-否 1-是")
	private Integer canDisable;
	/**
	 * 传真
	 */
	@ApiModelProperty(value = "传真")
	private String insFax;
	/**
	 * 座机电话
	 */
	@ApiModelProperty(value = "座机电话")
	private String insLandLinePhone;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String insRemark;
	/**
	 * 删除状态(0-正常,1-已删除)
	 */
	@TableLogic
	private String delFlag;
}
