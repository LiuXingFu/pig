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
package com.pig4cloud.pig.casee.dto.paifu;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * 项目表
 *
 * @author pig code generator
 * @date 2022-02-10 17:30:36
 */
@Data
public class ProjectPaifuReceiptDTO {

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 接收时间
	 */
	@ApiModelProperty(value="接收时间")
	private LocalDate takeTime;

    /**
     * 公司业务案号
     */
    @ApiModelProperty(value="公司业务案号")
    private String companyCode;

    /**
     * 办理人id
     */
    @ApiModelProperty(value="办理人id")
    private Integer userId;

    /**
     * 办理人名称
     */
    @ApiModelProperty(value="办理人名称")
    private String userNickName;

    /**
     * 年份
     */
    @ApiModelProperty(value="年份")
    private String year;

    /**
     * 简称
     */
    @ApiModelProperty(value="简称")
    private String alias;

    /**
     * 字号
     */
    @ApiModelProperty(value="字号")
    private Integer word;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String describes;

	/**
	 * 清收移交记录id
	 */
	@ApiModelProperty(value="清收移交记录id")
	private Integer liquiTransferRecordId;

}
