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
package com.pig4cloud.pig.casee.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 标的表
 *
 * @author yy
 * @date 2021-11-05 16:28:49
 */
@Data
public class TargetPageDTO{


	/**
	 * 标的名称
	 */
	@ApiModelProperty(value="标的名称")
	private String targetName;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

	/**
	 * 业务类型（100-清收标的，200-拍辅标的物）
	 */
	@ApiModelProperty(value="业务类型（100-清收标的，200-拍辅标的物）")
	private Integer businessType;

	/**
	 * 标的类别（1：标的，2：标的物）
	 */
	@ApiModelProperty(value="标的类别（1：标的，2：标的物）")
	private Integer targetCategory;

	/**
	 * 状态（0：待开启，1：正在执行，2：已结案，（清收状态：101：存在，102：毁损，103：灭失），（拍辅状态：201：暂缓，202：中止，203：其它））
	 */
	@ApiModelProperty(value="状态（0：待开启，1：正在执行，2：已结案，（清收状态：101：存在，102：毁损，103：灭失），（拍辅状态：201：暂缓，202：中止，203：其它））")
	private Integer status;

	@ApiModelProperty(value="当前登录机构")
	private Integer   insId;

	@ApiModelProperty(value="当前登录网点")
	private Integer   outlesId;

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 标的性质
	 */
	@ApiModelProperty(value="标的性质")
	private Integer targetType;

}
