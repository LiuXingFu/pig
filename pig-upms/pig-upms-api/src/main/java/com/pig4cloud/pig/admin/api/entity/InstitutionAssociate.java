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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 机构关联表
 *
 * @author yuanduo
 * @date 2021-09-03 11:01:07
 */
@Data
@TableName("p10_institution_associate")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "机构关联表")
public class InstitutionAssociate extends Model<InstitutionAssociate> {
	private static final long serialVersionUID = 1L;

	/**
	 * 机构关联id 主键自增
	 */
	@TableId
	@ApiModelProperty(value = "机构关联id 主键自增")
	private Integer associateId;
	/**
	 * 机构ID
	 */
	@ApiModelProperty(value = "机构ID")
	private Integer insId;
	/**
	 * 关联机构ID
	 */
	@ApiModelProperty(value = "关联机构ID")
	private Integer insAssociateId;
	/**
	 * 关联时间
	 */
	@ApiModelProperty(value = "关联时间")
	private LocalDateTime associateTime;
	/**
	 * 关联状态 0-待确认 100-关联 200-拒绝 300-已断开
	 */
	@ApiModelProperty(value = "关联状态 0-待确认 100-关联 200-拒绝 300-已断开")
	private Integer associateStatus;
	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String  associateRemark;
}
