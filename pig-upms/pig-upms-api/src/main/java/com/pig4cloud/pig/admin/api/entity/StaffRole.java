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

/**
 * 员工角色表
 *
 * @author yuanduo
 * @date 2021-09-08 14:20:07
 */
@Data
@TableName("p10_staff_role")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "员工角色表")
public class StaffRole extends Model<StaffRole> {

    /**
     * 员工ID
     */
    @ApiModelProperty(value="员工ID")
    private Integer staffId;

    /**
     * 角色ID
     */
    @ApiModelProperty(value="角色ID")
    private Integer roleId;


}
