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
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 网点表
 *
 * @author yuanduo
 * @date 2021-09-02 16:24:58
 */
@Data
@TableName("p10_outles")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "网点表")
public class Outles extends BaseEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 网点id 主键自增
	 */
	@TableId
	@ApiModelProperty(value = "网点id 主键自增")
	private Integer outlesId;
	/**
	 * 网点名称
	 */
	@ApiModelProperty(value = "网点名称")
	private String outlesName;
	/**
	 * 机构id
	 */
	@ApiModelProperty(value = "机构id")
	private Integer insId;
	/**
	 * 简称
	 */
	@ApiModelProperty(value = "简称")
	private String alias;
	/**
	 * 座机电话
	 */
	@ApiModelProperty(value = "座机电话")
	private String outlesLandLinePhone;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String outlesRemark;
	/**
	 * 是否是默认网点 0-否 1-是
	 */
	@ApiModelProperty(value = "是否是默认网点 0-否 1-是")
	private Integer canDefault;
	/**
	 * 0-正常，1-删除
	 */
	@TableLogic
	private String delFlag;
}
