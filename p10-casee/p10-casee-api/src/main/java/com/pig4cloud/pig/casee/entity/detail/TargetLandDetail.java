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

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 土地详细信息
 *
 * @author yy
 * @date 2021-11-05 16:28:49
 */
@Data
public class TargetLandDetail {

	/**资产编号*/
	@ApiModelProperty(value = "资产编号")
	private String particularsCode;

	/**土地性质 0-商业 1-住宅 2-工业 3-综合*/
	@ApiModelProperty(value = "土地性质 0-商业 1-住宅 2-工业 3-综合")
	private Integer landNature;
	/**土地面积*/
	@ApiModelProperty(value = "土地面积")
	private String areaOfLand;
	/**使用期限*/
	@ApiModelProperty(value = "使用期限")
	private String lifeSpan;
	/**是否抵押 0-否 1-是*/
	@ApiModelProperty(value = "是否抵押 0-否 1-是")
	private Integer isPledge;


}
