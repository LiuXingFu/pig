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
 * 案件人员表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@Data
@TableName("p10_casee_personnel")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "案件人员表")
public class CaseePersonnel extends BaseEntity {

    /**
     * 案件人员ID
     */
    @TableId
    @ApiModelProperty(value="案件人员ID")
    private Integer casePersonnelId;

    /**
     * 删除状态(0-正常,1-已删除)
     */
    @ApiModelProperty(value="删除状态(0-正常,1-已删除)")
    private String delFlag;

    /**
     * 统一标识(身份证/统一社会信用代码)
     */
    @ApiModelProperty(value="统一标识(身份证/统一社会信用代码)")
    private String unifiedIdentity;

    /**
     * 联系电话
     */
    @ApiModelProperty(value="联系电话")
    private String phone;

    /**
     * 名称
     */
    @ApiModelProperty(value="名称")
    private String name;

    /**
     * 工作状态
     */
    @ApiModelProperty(value="工作状态")
    private String workStatus;

    /**
     * 联系地址
     */
    @ApiModelProperty(value="联系地址")
    private String contactAddress;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}
