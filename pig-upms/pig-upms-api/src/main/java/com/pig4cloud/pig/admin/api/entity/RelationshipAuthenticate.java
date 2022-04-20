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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 机构法院关联表
 *
 * @author yuanduo
 * @date 2022-01-11 14:48:51
 */
@Data
@TableName("p10_relationship_authenticate")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "机构法院关联表")
public class RelationshipAuthenticate  extends Model<RelationshipAuthenticate> {

    /**
     * 关联id
     */
    @TableId
    @ApiModelProperty(value="关联id")
    private Integer id;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id")
    private Integer authenticateId;

    /**
     * 法院id
     */
    @ApiModelProperty(value="法院id")
    private Integer authenticateGoalId;


}
