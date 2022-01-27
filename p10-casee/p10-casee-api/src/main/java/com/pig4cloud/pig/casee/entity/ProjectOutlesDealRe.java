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
 * 项目机构关联表
 *
 * @author ligt
 * @date 2022-01-11 10:05:13
 */
@Data
@TableName("p10_project_outles_deal_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "项目机构关联表")
public class ProjectOutlesDealRe extends BaseEntity {

    /**
     * dealReId
     */
    @TableId
    @ApiModelProperty(value="dealReId")
    private Integer dealReId;

    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private Integer projectId;

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
     * 网点选中用户id
     */
    @ApiModelProperty(value="网点选中用户id")
    private Integer userId;

    /**
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    private String userNickName;

    /**
     * 类型(1-委托机构，2-协办网点（待定）)
     */
    @ApiModelProperty(value="类型(1-委托机构，2-协办网点（待定）)")
    private Integer type;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}
