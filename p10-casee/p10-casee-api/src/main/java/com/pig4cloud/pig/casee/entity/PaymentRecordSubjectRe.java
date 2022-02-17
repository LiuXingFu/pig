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
 * 回款记录关联主体信息表
 *
 * @author Mjh
 * @date 2022-02-17 17:52:08
 */
@Data
@TableName("p10_payment_record_subject_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "回款记录关联主体信息表")
public class PaymentRecordSubjectRe extends BaseEntity {

    /**
     * 回款记录关联主体信息
     */
    @TableId
    @ApiModelProperty(value="回款记录关联主体信息")
    private Integer paymentRecordSubjectId;

    /**
     * 回款记录id
     */
    @ApiModelProperty(value="回款记录id")
    private Integer paymentRecordId;

    /**
     * 主体id
     */
    @ApiModelProperty(value="主体id")
    private Integer subjectId;


}
