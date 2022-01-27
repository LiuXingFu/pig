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
package com.pig4cloud.pig.casee.entity.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 标的物数据详情
 *
 * @author yy
 * @date 2021-11-05 16:28:49
 */
@Data
public class TargetThingLiQuiDateDetail {


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

	/** 被执行人唯一编号 */
	@ApiModelProperty(value = "被执行人唯一编号")
	private String executedUnifiedIdentity;

	/** 性质*/
	private Object targetPropertiesDetail;

}
