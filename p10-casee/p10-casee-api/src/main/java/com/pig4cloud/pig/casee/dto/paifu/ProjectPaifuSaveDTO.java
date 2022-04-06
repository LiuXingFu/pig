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
     * 接收时间
     */
    @ApiModelProperty(value="接收时间")
    private LocalDate takeTime;

    /**
     * 退出日期
     */
    @ApiModelProperty(value="退出日期")
    private LocalDate closeTime;

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
     * 所有委托机构名称(用于显示，多个用，号隔开)
     */
    @ApiModelProperty(value="所有委托机构名称(用于显示，多个用，号隔开)")
    private String proposersNames;

    /**
     * 所有债务人名称(用于显示，多个用，号隔开)
     */
    @ApiModelProperty(value="所有债务人名称(用于显示，多个用，号隔开)")
    private String subjectPersons;

    /**
     * 项目详情数据
     */
    @ApiModelProperty(value="项目详情数据")
    private ProjectPaifuDetail projectPaifuDetail;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String describes;

	/**
	 * 案号
	 */
	@ApiModelProperty(value="案号")
	private String caseeNumber;

	/**
	 * 案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件,，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)
	 */
	@ApiModelProperty(value="案件类型(1010:诉前保全案件，2010:诉讼保全案件，2020:一审诉讼案件,，2021:二审诉讼案件，2030:其它案件，3010:首次执行案件，3031:执恢案件)")
	private Integer caseeType;

	/**
	 * 立案日期
	 */
	@ApiModelProperty(value="立案日期")
	private LocalDate startTime;

	/**
	 * 承办法院id
	 */
	@ApiModelProperty(value="承办法院id")
	private Integer courtId;

	/**
	 * 承办法官id
	 */
	@ApiModelProperty(value="承办法官id")
	private Integer judgeId;

	/**
	 * 法官名称
	 */
	@ApiModelProperty(value="法官名称")
	private String judgeName;

	/**
	 * 申请执行人集合
	 */
	@ApiModelProperty(value = "申请人/原告/上诉人/申请执行人等集合")
	private List<CaseeSubjectReListDTO> applicantList;

	/**
	 * 被执行人集合
	 */
	@ApiModelProperty(value = "被告/被执行人/被上诉人等集合")
	private List<CaseeSubjectReListDTO> executedList;


}
