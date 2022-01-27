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
 * 车辆详细信息
 *
 * @author yy
 * @date 2021-11-05 16:28:49
 */
@Data
public class TargetCarDetail {

	/**资产编号*/
	@ApiModelProperty(value = "资产编号")
	private String particularsCode;
	/**车辆查封单位*/
	@ApiModelProperty(value = "车辆查封单位")
	private String sealUpUnit;
	/**使用性质 0-非运营 1-运营*/
	@ApiModelProperty(value = "使用性质 0-非运营 1-运营")
	private Integer useNature;
	/**发动机号*/
	@ApiModelProperty(value = "发动机号")
	private String engineNumber;
	/**车辆类型*/
	@ApiModelProperty(value = "车辆类型")
	private Integer vehicleType;
	/**是否有钥匙 0-无 1-有*/
	@ApiModelProperty(value = "是否有钥匙 0-无 1-有")
	private Integer isChangeKey;
	/**行车里程*/
	@ApiModelProperty(value = "行车里程")
	private String mileage;
	/**车辆出厂日期*/
	@ApiModelProperty(value = "车辆出厂日期")

	private String factoryTime;
	/**品牌车型*/
	@ApiModelProperty(value = "品牌车型")
	private String motorcycleType;
	/**车辆初次登记日期*/
	@ApiModelProperty(value = "车辆初次登记日期")
	@JsonFormat(timezone = "GMT+8" ,pattern="yyyy-MM-dd HH:mm:ss")
	private String registrationTime;
	/**是否抵押 0-否 1-是*/
	@ApiModelProperty(value = "是否抵押 0-否 1-是")
	private Integer isPledge;


}
