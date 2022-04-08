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

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.casee.dto.CaseeSubjectReDTO;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ProjectPaifuDetail;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.util.List;

/**
 * 项目表
 *
 * @author pig code generator
 * @date 2022-02-10 17:30:36
 */
@Data
public class ProjectPaifuSaveDTO {

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
	 * 机构id
	 */
	@ApiModelProperty(value="机构id")
	private Integer insId;

	/**
	 * 网点id
	 */
	@ApiModelProperty(value="网点id")
	private Integer outlesId;

	/**
	 * 案号
	 */
	@ApiModelProperty(value="案号")
	private String caseeNumber;

	/**
	 * 承办法院id
	 */
	@ApiModelProperty(value="承办法院id")
	private Integer courtId;

	/**
	 * 法官名称
	 */
	@ApiModelProperty(value="法官名称")
	private String judgeName;

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
	 * 人员集合
	 */
	@ApiModelProperty(value = "人员集合")
	private List<CaseeSubjectReListDTO> applicantList;


}
