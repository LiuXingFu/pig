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

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 股权详细信息
 *
 * @author yy
 * @date 2021-11-05 16:28:49
 */
@Data
public class TargetEquityDetail {


	/**资产编号*/
	@ApiModelProperty(value = "资产编号")
	private String particularsCode;
	@ApiModelProperty(value = "企业名称")
	private String enterpriseName;
	/**法定代表人*/
	@ApiModelProperty(value = "法定代表人")
	private String legalPerson;
	/**注册资本（元）*/
	@ApiModelProperty(value = "注册资本（元）")
	private BigDecimal registeredAssets;
	/**注册地址*/
	@ApiModelProperty(value = "注册地址")
	private String registeredAddress;
	/**成立日期*/
	@ApiModelProperty(value = "成立日期")
	private String completionTime;

}
