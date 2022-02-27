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

import java.time.LocalDateTime;

/**
 * 程序表
 *
 * @author yy
 * @date 2021-11-05 16:28:49
 */
@Data
@TableName("p10_target")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "程序表")
public class Target extends BaseEntity {

	/**
	 * 程序id
	 */
	@TableId
	@ApiModelProperty(value="程序id")
	private Integer targetId;

	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@ApiModelProperty(value="删除标识（0-正常,1-删除）")
	private String delFlag;

	/**
	 * 程序名称
	 */
	@ApiModelProperty(value="程序名称")
	private String targetName;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

	/**
	 * 状态（0：待开启，1：正在执行，2：已结案，3：停案，（清收状态：101：毁损，102：灭失），（拍辅状态：201：暂缓，202：中止，203：其它））
	 */
	@ApiModelProperty(value="状态（0：待开启，1：正在执行，2：已结案，3：停案，（清收状态：101：毁损，102：灭失），（拍辅状态：201：暂缓，202：中止，203：其它））")
	private Integer status;

	/**
	 * 程序性质（1010:诉前保全案件 2010:诉讼保全案件 2020:一审诉讼案件 2021:二审诉讼案件 2030:其它案件 3010:首次执行案件 3031:执灰案件  4040:实体财产程序 4041:资金财产程序5050:行为限制程序 5051:行为违法程序 6060:拍卖程序 7070:履行程序））
	 */
	@ApiModelProperty(value="程序性质（1010:诉前保全案件 2010:诉讼保全案件 2020:一审诉讼案件 2021:二审诉讼案件 2030:其它案件 3010:首次执行案件 3031:执灰案件  4040:实体财产程序 4041:资金财产程序5050:行为限制程序 5051:行为违法程序 6060:拍卖程序 7070:履行程序））")
	private Integer procedureNature;


	/**
	 * 目标id
	 */
	@ApiModelProperty(value="为空案件、存储财产id、行为id")
	private Integer goalId;

	/**
	 * 创建网点id
	 */
	@ApiModelProperty(value="创建网点id")
	private Integer createOutlesId;

	/**
	 * 创建机构id
	 */
	@ApiModelProperty(value="创建机构id")
	private Integer createInsId;

	/**
	 * 程序业务数据保存
	 */
	@ApiModelProperty(value="程序业务数据保存")
	private String businessData;

}
