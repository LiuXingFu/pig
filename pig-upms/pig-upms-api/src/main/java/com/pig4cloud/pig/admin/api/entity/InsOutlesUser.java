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
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 机构/网点用户关联表
 *
 * @author xls
 * @date 2022-01-27 19:30:49
 */
@Data
@TableName("p10_ins_outles_user")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "机构/网点用户关联表")
public class InsOutlesUser extends BaseEntity {

    /**
     * 主键id
     */
    @TableId
    @ApiModelProperty(value="主键id")
    private Integer insOutlesUserId;

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
     * 用户id
     */
    @ApiModelProperty(value="用户id")
    private Integer userId;

    /**
     * 类型：1-管理员，2-普通员工
     */
    @ApiModelProperty(value="类型：1-管理员，2-普通员工")
    private Integer type;

    /**
     * 入职时间
     */
    @ApiModelProperty(value="入职时间")
    private LocalDateTime entryTime;

    /**
     * 职位
     */
    @ApiModelProperty(value="职位")
    private String position;

	/**
	 * 删除状态(0-正常,1-已删除)
	 */
	@TableLogic
	private String delFlag;


}
