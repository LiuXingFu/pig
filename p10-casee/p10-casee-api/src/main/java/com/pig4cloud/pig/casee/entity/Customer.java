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
 * 客户表
 *
 * @author yuanduo
 * @date 2022-04-06 10:40:19
 */
@Data
@TableName("p10_customer")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "客户表")
public class Customer extends BaseEntity {

    /**
     * 客户id
     */
    @TableId
    @ApiModelProperty(value="客户id")
    private Integer customerId;

    /**
     * 删除状态(0-正常,1-已删除)
     */
    @ApiModelProperty(value="删除状态(0-正常,1-已删除)")
    private String delFlag;

    /**
     * 主体id
     */
    @ApiModelProperty(value="主体id")
    private Integer subjectId;

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
     * 客户类型 可推荐客户-10000 意向客户-20000 成交客户-30000 关联申请人-40000 其他关联客户-50000
     */
    @ApiModelProperty(value="客户类型 可推荐客户-10000 意向客户-20000 成交客户-30000 关联申请人-40000 其他关联客户-50000")
    private Integer customerType;

    /**
     * 推荐人id
     */
    @ApiModelProperty(value="推荐人id")
    private Integer recommenderId;

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


}
