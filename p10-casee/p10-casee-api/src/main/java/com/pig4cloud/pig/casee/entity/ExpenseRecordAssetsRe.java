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
 * 产生费用财产关联表
 *
 * @author pig code generator
 * @date 2022-05-03 21:15:30
 */
@Data
@TableName("p10_expense_record_assets_re")
@ApiModel(value = "费用产生记录财产关联表")
public class ExpenseRecordAssetsRe {

    /**
     * 产生费用财产关联表id
     */
    @TableId
    @ApiModelProperty(value="费用产生记录财产关联表id")
    private Integer expenseRecordAssetsReId;

    /**
     * 产生费用id
     */
    @ApiModelProperty(value="费用产生记录id")
    private Integer expenseRecordId;

    /**
     * 财产关联id
     */
    @ApiModelProperty(value="财产关联id")
    private Integer assetsReId;


}
