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

/**
 * 财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
@TableName("p10_assets")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "财产表")
public class Assets extends BaseEntity {

    /**
     * 财产id
     */
    @TableId
    @ApiModelProperty(value="财产id")
    private Integer assetsId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;

    /**
     * 财产名称
     */
    @ApiModelProperty(value="财产名称")
    private String assetsName;

//    /**
//     * 地址id
//     */
//    @ApiModelProperty(value="地址id")
//    private Integer addressId;

    /**
     * 财产类型（20100-资金财产 20200-实体财产）
     */
    @ApiModelProperty(value="财产类型（20100-资金财产 20200-实体财产）")
    private Integer type;

    /**
     * 财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））
     */
    @ApiModelProperty(value="财产性质（资金财产：(20101:银行存款，20102：住房公积金)，实体财产：（20201：房产，20202：车辆，20203：股权，20204：土地，20205：其它））")
    private Integer assetsType;

    /**
     * 描述
     */
    @ApiModelProperty(value="描述")
    private String describes;

	/**
	 * 所有权人
	 */
	@ApiModelProperty(value="所有权人")
	private String owner;

	/**
	 * 财产详情
	 */
	@ApiModelProperty(value="财产详情")
	private String assetsDetail;

}
