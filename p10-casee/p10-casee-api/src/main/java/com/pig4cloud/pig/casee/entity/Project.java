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
import java.time.LocalDateTime;

/**
 * 项目表
 *
 * @author ligt
 * @date 2022-01-10 15:05:49
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
     * 项目状态(0-待接收，1-在办、2-暂缓、3-和解、4-退出，5-退回)
     */
    @ApiModelProperty(value="项目状态(0-待接收，1-在办、2-暂缓、3-和解、4-退出，5-退回)")
    private Integer status;

    /**
     * 受托机构id
     */
    @ApiModelProperty(value="受托机构id")
    private Integer createInsId;

    /**
     * 受托网点id
     */
    @ApiModelProperty(value="受托网点id")
    private Integer createOutlesId;

    /**
     * 移交日期
     */
    @ApiModelProperty(value="移交日期")
    private LocalDate startTime;

    /**
     * 退出日期
     */
    @ApiModelProperty(value="退出日期")
    private LocalDateTime closeTime;

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
     * 接收时间
     */
    @ApiModelProperty(value="接收时间")
    private LocalDate takeTime;

    /**
     * 接受人id
     */
    @ApiModelProperty(value="接受人id")
    private Integer userId;

    /**
     * 接收人名称
     */
    @ApiModelProperty(value="接收人名称")
    private String userNickName;


}
