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
 * 期限配置表
 *
 * @author yuanduo
 * @date 2022-02-11 21:13:38
 */
@Data
@TableName("p10_deadline_configure")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "期限配置表")
public class DeadlineConfigure extends BaseEntity {

    /**
     * 期限配置id
     */
    @TableId
    @ApiModelProperty(value="期限配置id")
    private Integer periodConfigureId;

    /**
     * key
     */
    @ApiModelProperty(value="key")
    private String key;

    /**
     * 期限名称
     */
    @ApiModelProperty(value="期限名称")
    private String deadlineConfigureName;

    /**
     * 期限 
     */
    @ApiModelProperty(value="期限 ")
    private Integer deadlineConfigureNum;

    /**
     * 单位 （1：天、2：月、3：年）
     */
    @ApiModelProperty(value="单位 （1：天、2：月、3：年）")
    private Integer unit;

    /**
     * 提前提醒天数
     */
    @ApiModelProperty(value="提前提醒天数")
    private Integer reminderDays;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String deadlineConfigureRemark;


}
