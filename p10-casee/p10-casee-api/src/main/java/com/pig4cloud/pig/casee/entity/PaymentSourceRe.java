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
 * 回款来源表
 *
 * @author Mjh
 * @date 2022-06-23 17:07:54
 */
@Data
@TableName("p10_payment_source_re")
@ApiModel(value = "回款来源表")
public class PaymentSourceRe {

    /**
     * 回款来源关联表id
     */
    @TableId
    @ApiModelProperty(value="回款来源关联表id")
    private Integer paymentSourceReId;

    /**
     * 源id
     */
    @ApiModelProperty(value="源id")
    private Integer sourceId;

    /**
     * 回款记录id
     */
    @ApiModelProperty(value="回款记录id")
    private Integer paymentRecordId;

    /**
     * 类型(100-法院到款)
     */
    @ApiModelProperty(value="类型(100-法院到款)")
    private Integer type;


}
