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
package com.pig4cloud.pig.casee.entity;

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
@TableName("p10_project")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "项目表")
public class Project extends BaseEntity {

    /**
     * projectId
     */
    @TableId
    @ApiModelProperty(value="projectId")
    private Integer projectId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;

    /**
     * 项目类型(100-清收,200-拍辅)
     */
    @ApiModelProperty(value="项目类型(100-清收,200-拍辅)")
    private Integer projectType;

    /**
     * 公司业务案号
     */
    @ApiModelProperty(value="公司业务案号")
    private String companyCode;

    /**
     * 项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)
     */
    @ApiModelProperty(value="项目状态(1000-在办、2000-暂缓、3000-和解、4000-退出)")
    private Integer status;

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
    private String projectDetail;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String describes;


}
