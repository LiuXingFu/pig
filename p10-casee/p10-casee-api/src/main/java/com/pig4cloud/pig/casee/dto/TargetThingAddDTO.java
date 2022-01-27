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

import com.pig4cloud.pig.casee.entity.TargetBizLiqui;
import com.pig4cloud.pig.casee.entity.detail.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



/**
 * 标的表
 *
 * @author yy
 * @date 2021-11-05 16:28:49
 */
@Data
public class TargetThingAddDTO extends TargetBizLiqui {

	/** 房产详情 */
	private TargetHouseDetail houseDetail;

	/** 车辆详情 */
	private TargetCarDetail carDetail;

	/** 股权详情 */
	private TargetEquityDetail equityDetail;

	/** 土地详情 */
	private TargetLandDetail landDetail;

	/** 其它详情 */
	private TargetOtherDetail otherDetail;
	/**
	 * 省
	 */
	@ApiModelProperty(value = "省")
	private String province;
	/**
	 * 市
	 */
	@ApiModelProperty(value = "市")
	private String city;
	/**
	 * 区
	 */
	@ApiModelProperty(value = "区")
	private String area;
	/**
	 * 详细地址
	 */
	@ApiModelProperty(value = "详细地址")
	private String informationAddress;
	/**
	 * 行政区划编号
	 */
	@ApiModelProperty(value = "行政区划编号")
	private String code;

	/**
	 * 所有权人
	 */
	@ApiModelProperty(value="所有权人")
	private String owner;

	/** 首封法院 */
	@ApiModelProperty(value = "首封法院")
	private Integer courtId;

	/**查封情况（0：首封，1：轮候）*/
	@ApiModelProperty(value = "查封情况（0：首封，1：轮候）")
	private Integer sealCase;

	/**查封时间*/
	@ApiModelProperty(value = "查封时间")
	private String sealUpTime;

	/**查封到期日*/
	@ApiModelProperty(value = "查封到期日")
	private String sealTime;

	/**照片地址*/
	@ApiModelProperty(value = "照片地址")
	private String imgUrl;

	/**附件路径*/
	@ApiModelProperty(value = "附件路径")
	private String fileUrl;
	/**
	 * 被执行人唯一编码（身份证 公司编码）
	 */
	@ApiModelProperty(value="被执行人唯一编码（身份证 公司编码）")
	private String executedUnifiedIdentity;


}
