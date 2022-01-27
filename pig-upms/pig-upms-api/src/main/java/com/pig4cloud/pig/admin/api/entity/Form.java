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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 表单表
 *
 * @author yy
 * @date 2021-09-06 16:49:33
 */
@Data
@TableName("p10_form")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "表单表")
public class Form extends BaseEntity {
private static final long serialVersionUID = 1L;

    /**
     * formId
     */
    @TableId
    @ApiModelProperty(value="formId")
    private Integer formId;
    /**
     * delFlag
     */
    @ApiModelProperty(value="delFlag")
    private String delFlag;
    /**
     * name
     */
    @ApiModelProperty(value="name")
    private String name;
    /**
     * content
     */
    @ApiModelProperty(value="content")
	private String content;
    /**
     * remark
     */
    @ApiModelProperty(value="remark")
    private String remark;
    /**
     * insUserId
     */
    @ApiModelProperty(value="insUserId")
    private Integer insUserId;
	/**
	 * propertyKey
	 */
	@ApiModelProperty(value="propertyKey")
	private String propertyKey;


    }
