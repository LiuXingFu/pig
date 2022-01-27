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

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 员工部门表
 *
 * @author yuanduo
 * @date 2021-09-22 11:25:02
 */
@Data
@TableName("p10_staff_dept_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "员工部门表")
public class StaffDeptRe extends Model<StaffDeptRe> {

    /**
     * 员工ID
     */
    @ApiModelProperty(value="员工ID")
    private Integer staffId;

    /**
     * 部门ID
     */
    @ApiModelProperty(value="部门ID")
    private Integer deptId;


}
