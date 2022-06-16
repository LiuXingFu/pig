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

import java.time.LocalDate;

/**
 * 项目表
 *
 * @author pig code generator
 * @date 2022-02-10 17:30:36
 */
@Data
public class ProjectLiquiPageDTO {

    @ApiModelProperty(value="公司业务案号")
    private String companyCode;

    @ApiModelProperty(value="项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)")
    private Integer status;

    @ApiModelProperty(value="办理人名称")
    private String userNickName;

    @ApiModelProperty(value="所有委托机构名称(用于显示，多个用，号隔开)")
    private String proposersNames;

    @ApiModelProperty(value="所有债务人名称(用于显示，多个用，号隔开)")
    private String subjectPersons;

	@ApiModelProperty(value="开始时间")
	private LocalDate beginDate;

	@ApiModelProperty(value="结束时间")
	private LocalDate endDate;

	@ApiModelProperty(value="机构名称")
	private String insName;

	@ApiModelProperty(value="网点名称")
	private String outlesName;

	@ApiModelProperty(value="抵押情况（0-有，1-无）")
	private Integer mortgageSituation;

}
