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
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 和解/调解主体关联表
 *
 * @author Mjh
 * @date 2022-03-09 19:48:49
 */
@Data
@TableName("p10_reconciliatio_mediation_subject_re")
@ApiModel(value = "和解/调解主体关联表")
public class ReconciliatioMediationSubjectRe {

    /**
     * 和解/调解主体关联id
     */
    @TableId
    @ApiModelProperty(value="和解/调解主体关联id")
    private Integer reconciliatioMediationSubjectId;

    /**
     * 和解/调解id
     */
    @ApiModelProperty(value="和解/调解id")
    private Integer reconciliatioMediationId;

    /**
     * 主体id
     */
    @ApiModelProperty(value="主体id")
    private Integer subjectId;


}
