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

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pig4cloud.pig.casee.entity.CaseeBizBase;
import com.pig4cloud.pig.casee.entity.CaseeBizLiqui;
import com.pig4cloud.pig.casee.entity.CaseeOutlesDealRe;
import com.pig4cloud.pig.casee.entity.CaseePersonnel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 案件表
 *
 * @author yy
 * @date 2021-09-15 10:03:22
 */
@Data
@TableName("p10_casee")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "清收案件DTO")
public class CaseeBizBaseDTO extends CaseeBizBase {

	/**
	 * 说明
	 */
	@ApiModelProperty(value="说明")
	private String explain;





}
