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
 * 房产详细信息
 *
 * @author yy
 * @date 2021-11-05 16:28:49
 */
@Data
public class TargetHouseDetail {

	/**资产编号*/
	@ApiModelProperty(value = "资产编号")
	private String particularsCode;
	/**建筑总面积*/
	@ApiModelProperty(value = "建筑总面积")
	private String buildingArea;
	/**分摊总面积*/
	@ApiModelProperty(value = "分摊总面积")
	private String apportionArea;
	/**套内总面积*/
	@ApiModelProperty(value = "套内总面积")
	private String insideArea;
	/**房产类型 0-商业 1-住宅 2-办公 3-杂房 4-车库 5-其它*/
	@ApiModelProperty(value = "房产类型 0-商业 1-住宅 2-办公 3-杂房 4-车库 5-其它")
	private Integer housePropertyType;
	/**土地性质 0-商业 1-住宅 2-工业 3-综合*/
	@ApiModelProperty(value = "土地性质 0-商业 1-住宅 2-工业 3-综合")
	private Integer landNature;
	/**是否抵押 0-否 1-是*/
	@ApiModelProperty(value = "是否抵押 0-否 1-是")
	private Integer isPledge;
	/**建成/成立日期*/
	@ApiModelProperty(value = "建成/成立日期")
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd HH:mm:ss")
	private String completionTime;

}
