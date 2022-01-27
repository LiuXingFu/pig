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
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.pig4cloud.pig.casee.entity.CaseeBizLiqui;
import com.pig4cloud.pig.casee.entity.CaseeOutlesDealRe;
import com.pig4cloud.pig.casee.entity.CaseePersonnel;
import com.pig4cloud.pig.casee.entity.detail.CaseeLiQuiDateDetail;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.Date;
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
public class CaseeBizLiquiDTO extends CaseeBizLiqui {


	//---------------------------------------dto
	// 被执行人
	@ApiModelProperty(value="被执行人列表")
	List<CaseePersonnel> executedList;
	// 担保人
	@ApiModelProperty(value="担保人列表")
	List<CaseePersonnel> bondsmanList;
	//案件机构关联表
	@ApiModelProperty(value="关联网点信息")
	List<CaseeOutlesDealRe> outlesList;


	@ApiModelProperty(value="委托机构")
	private Integer  entrustInsId;


	@ApiModelProperty(value="委托网点")
	private Integer  entrustOutlesId;



	private String beginDate;//开始时间
	private String endDate;//结束时间


	@ApiModelProperty(value="当前登录机构")
	private Integer   insId;


	@ApiModelProperty(value="当前登录网点")
	private Integer   outlesId;

	private String caseStatus;//案件状态

	private String sequence;//案收案时间排序

	@ApiModelProperty(value="查询关键字")
	private String keyword;

}
