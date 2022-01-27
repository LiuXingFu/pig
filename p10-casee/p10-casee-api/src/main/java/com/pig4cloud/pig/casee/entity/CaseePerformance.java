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
package com.pig4cloud.pig.casee.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 案件绩效表
 *
 * @author xiaojun
 * @date 2021-09-07 17:44:19
 */
@Data
@TableName("p10_casee_performance")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "案件绩效表")
public class CaseePerformance extends BaseEntity {
	/**
	 * 绩效ID
	 */
	@TableId
	@ApiModelProperty(value="绩效ID")
	private Integer perfId;

	/**
	 * 绩效金额
	 */
	@ApiModelProperty(value="绩效金额")
	private BigDecimal perfAmount;

	/**
	 * 绩效分值
	 */
	@ApiModelProperty(value="绩效分值")
	private Integer perfScore;

	/**
	 * 绩效状态（0：不计费，1：计费）
	 */
	@ApiModelProperty(value="绩效状态（0：不计费，1：计费）")
	private Integer perfStatus;

	/**
	 * 单位
	 */
	@ApiModelProperty(value="单位")
	private String unit;

	/**
	 * 节点ID
	 */
	@ApiModelProperty(value="节点ID")
	private String nodeId;

	/**
	 * 项目ID
	 */
	@ApiModelProperty(value="项目ID")
	private Integer projectId;

	/**
	 * 案件ID
	 */
	@ApiModelProperty(value="案件ID")
	private Integer caseeId;

	/**
	 * 程序ID
	 */
	@ApiModelProperty(value="程序ID")
	private Integer targetId;

	/**
	 * 机构ID
	 */
	@ApiModelProperty(value="机构ID")
	private Integer insId;

	/**
	 * 网点ID
	 */
	@ApiModelProperty(value="网点ID")
	private Integer outlesId;

	/**
	 * 用户ID
	 */
	@ApiModelProperty(value="用户ID")
	private Integer userId;

	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@ApiModelProperty(value="删除标识（0-正常,1-删除）")
	private String delFlag;

	/**
	 * //备注
	 */
	@ApiModelProperty(value="备注")
	private String	remark;

}
