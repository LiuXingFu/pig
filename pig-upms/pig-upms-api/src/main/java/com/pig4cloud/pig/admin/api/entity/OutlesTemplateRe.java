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
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 网点任务模板关联表
 *
 * @author Mjh
 * @date 2021-12-17 11:31:08
 */
@Data
@TableName("p10_outles_template_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "网点任务模板关联表")
public class OutlesTemplateRe extends BaseEntity {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value="id")
    private Integer templateReId;

    /**
     * 网点id
     */
    @ApiModelProperty(value="网点id")
    private Integer outlesId;

    /**
     * 模板id
     */
    @ApiModelProperty(value="模板id")
    private Integer templateId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;
}
