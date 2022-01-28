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
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
@Data
@TableName("p10_assets_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "财产关联表")
public class AssetsRe extends BaseEntity {

    /**
     * 财产关联表id
     */
    @TableId
    @ApiModelProperty(value="财产关联表id")
    private Integer assetsReId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;

	/**
	 * 财产表id
	 */
	@ApiModelProperty(value="财产表id")
	private Integer assetsId;

    /**
     * 程序表id
     */
    @ApiModelProperty(value="程序表id")
    private Integer targetId;

    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private Integer projectId;

    /**
     * 案件id
     */
    @ApiModelProperty(value="案件id")
    private Integer caseeId;

    /**
     * 债务人id
     */
    @ApiModelProperty(value="债务人id")
    private Integer subjectId;

    /**
     * 债务人名称
     */
    @ApiModelProperty(value="债务人名称")
    private String subjectName;

    /**
     * 财产关联详情
     */
    @ApiModelProperty(value="财产关联详情")
    private String assetsReDetail;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}