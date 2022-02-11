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
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 机构关联网点表
 *
 * @author yuanduo
 * @date 2021-09-03 11:09:36
 */
@Data
@TableName("p10_associate_outles_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "机构关联网点表")
public class AssociateOutlesRe extends Model<AssociateOutlesRe> {
	private static final long serialVersionUID = 1L;

	/**
	 * 网点授权id
	 */
	@TableId
	@ApiModelProperty(value = "网点授权id")
	private Integer associateOutlesId;
	/**
	 * 网点id
	 */
	@ApiModelProperty(value = "网点id")
	private Integer outlesId;
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
	 * 授权时间
	 */
	@ApiModelProperty(value = "授权时间")
	private LocalDateTime authorizationTime;
}
