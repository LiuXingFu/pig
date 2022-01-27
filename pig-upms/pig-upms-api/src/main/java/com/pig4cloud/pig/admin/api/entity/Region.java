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
package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 地址级联关系表
 *
 * @author yuanduo
 * @date 2021-09-23 15:37:44
 */
@Data
@TableName("p10_region")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "地址级联关系表")
public class Region extends Model<Region> {

    /**
     * 主键
     */
    @TableId
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 行政区划编码
     */
    @ApiModelProperty(value="行政区划编码")
    private String code;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 简称
     */
    @ApiModelProperty(value="简称")
    private String shortName;

    /**
     * 聚合名称
     */
    @ApiModelProperty(value="聚合名称")
    private String mergeName;

    /**
     * 聚合简称
     */
    @ApiModelProperty(value="聚合简称")
    private String mergeShortName;

    /**
     * 上级编码
     */
    @ApiModelProperty(value="上级编码")
    private Long parentId;

    /**
     * 简拼
     */
    @ApiModelProperty(value="简拼")
    private String pinyin;

    /**
     * 全拼
     */
    @ApiModelProperty(value="全拼")
    private String fullpinyin;

    /**
     * 电话区号
     */
    @ApiModelProperty(value="电话区号")
    private String cityCode;

    /**
     * 邮政编码
     */
    @ApiModelProperty(value="邮政编码")
    private String zipCode;

    /**
     * 经度
     */
    @ApiModelProperty(value="经度")
    private String longitude;

    /**
     * 纬度
     */
    @ApiModelProperty(value="纬度")
    private String latitude;

    /**
     * 首字母
     */
    @ApiModelProperty(value="首字母")
    private String firstLetter;

    /**
     * 行政级别 0:国家级 1:省级/直辖市 2:地级市/直辖市区县级 3:区县级 4:街道/乡镇级 5:社区/村委级
     */
    @ApiModelProperty(value="行政级别 0:国家级 1:省级/直辖市 2:地级市/直辖市区县级 3:区县级 4:街道/乡镇级 5:社区/村委级")
    private String regionLevel;

    /**
     * 所属国家代码
     */
    @ApiModelProperty(value="所属国家代码")
    private String countryCode;

    /**
     * 是否直辖市 0：不是 1：是
     */
    @ApiModelProperty(value="是否直辖市 0：不是 1：是")
    private String isMunicipality;

    /**
     * status
     */
    @ApiModelProperty(value="status")
    private Integer status;

    /**
     * 是否删除  1：已删除  0：正常
     */
    @ApiModelProperty(value="是否删除  1：已删除  0：正常")
    private Integer delFlag;

    /**
     * modifyBy
     */
    @ApiModelProperty(value="modifyBy")
    private Long modifyBy;

    /**
     * modifyTime
     */
    @ApiModelProperty(value="modifyTime")
    private LocalDateTime modifyTime;

    /**
     * tenantId
     */
    @ApiModelProperty(value="tenantId")
    private Long tenantId;


}
