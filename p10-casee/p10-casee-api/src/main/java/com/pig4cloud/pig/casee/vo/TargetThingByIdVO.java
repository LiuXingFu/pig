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
package com.pig4cloud.pig.casee.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 标的表
 *
 * @author yy
 * @date 2021-11-05 16:28:49
 */
@Data
public class TargetThingByIdVO extends BaseEntity {

	/**
	 * 标的id
	 */
	@TableId
	@ApiModelProperty(value="标的id")
	private Integer targetId;

	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@ApiModelProperty(value="删除标识（0-正常,1-删除）")
	private String delFlag;

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
	 * 状态（0：待开启，1：正在执行，2：已结案，3：停案，（清收状态：101：存在，102：毁损，103：灭失），（拍辅状态：201：暂缓，202：中止，203：其它））
	 */
	@ApiModelProperty(value="状态（0：待开启，1：正在执行，2：已结案，3：停案，（清收状态：101：存在，102：毁损，103：灭失），（拍辅状态：201：暂缓，202：中止，203：其它））")
	private Integer status;

	/**
	 * 标的类别（1：标的，2：标的物）
	 */
	@ApiModelProperty(value="标的类别（1：标的，2：标的物）")
	private Integer targetCategory;

	/**
	 * 标的性质
	 */
	@ApiModelProperty(value="标的性质")
	private Integer targetType;

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
	 * 描述
	 */
	@ApiModelProperty(value="描述")
	private String describes;

	/**
	 * 办理业务数据保存
	 */
	@ApiModelProperty(value="办理业务数据保存")
	private String businessData;

	/**
	 * 地址id
	 */
	@ApiModelProperty(value="地址id")
	private Integer addressId;

	/**
	 * 状态确认(0-默认状态，1-待确认,2-已确认，3-已拒绝)
	 */
	@ApiModelProperty(value="状态确认(0-默认状态，1-待确认,2-已确认，3-已拒绝)")
	private Integer statusConfirm;

	/**
	 * 结案日期
	 */
	@ApiModelProperty(value="结案日期")
	private LocalDateTime closeTime;

	/**
	 * 标的详情
	 */
	@ApiModelProperty(value="标的详情")
	private Object targetDetail;

	/**
	 * 省
	 */
	@ApiModelProperty(value="省")
	private String province;

	/**
	 * 市
	 */
	@ApiModelProperty(value="市")
	private String city;

	/**
	 * 区
	 */
	@ApiModelProperty(value="区")
	private String area;

	/**
	 * 信息地址
	 */
	@ApiModelProperty(value="信息地址")
	private String informationAddress;
	@ApiModelProperty(value="法院名称")
	private String courtName;

	/**
	 * 公司业务案号
	 */
	@ApiModelProperty(value="公司业务案号")
	private String companyCode;

	/**
	 * 被执行人名称
	 */
	@ApiModelProperty(value="被执行人名称")
	private String executedName;


}
