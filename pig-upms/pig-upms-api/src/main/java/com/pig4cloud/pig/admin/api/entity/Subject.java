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
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 主体
 *
 * @author yy
 * @date 2021-09-17 16:55:57
 */
@Data
@TableName("p10_subject")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "主体")
public class Subject  extends BaseEntity {
private static final long serialVersionUID = 1L;

    /**
     * subjectId
     */
    @TableId
    @ApiModelProperty(value="subjectId")
    private Integer subjectId;
    /**
     * delFlag
     */
    @ApiModelProperty(value="delFlag")
    private String delFlag;
    /**
     * unifiedIdentity
     */
    @ApiModelProperty(value="unifiedIdentity")
    private String unifiedIdentity;
    /**
     * 主体类型
     */
    @ApiModelProperty(value="natureType")
    private Integer natureType;
    /**
     * name
     */
    @ApiModelProperty(value="name")
    private String name;
    /**
     * phone
     */
    @ApiModelProperty(value="phone")
    private String phone;
    /**
     * legalPerson
     */
    @ApiModelProperty(value="legalPerson")
    private String legalPerson;
    /**
     * email
     */
    @ApiModelProperty(value="email")
    private String email;
    /**
     * remark
     */
    @ApiModelProperty(value="remark")
    private String remark;
    /**
     * isAuthentication
     */
    @ApiModelProperty(value="isAuthentication")
    private Integer isAuthentication;

	/**
	 * 工作单位
	 */
	@ApiModelProperty(value="工作单位")
	private String employer;

	/**
	 * 债务类型
	 */
	@ApiModelProperty(value="债务类型")
	private Integer debtType;

}
