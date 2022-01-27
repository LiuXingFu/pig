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
 * 案件主体关联案件表
 *
 * @author yy
 * @date 2022-01-10 15:13:16
 */
@Data
@TableName("p10_casee_subject_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "案件主体关联案件表")
public class CaseeSubjectRe extends BaseEntity {

    /**
     * id
     */
    @TableId
    @ApiModelProperty(value="id")
    private Integer subjectReId;

    /**
     * 主体表id
     */
    @ApiModelProperty(value="主体表id")
    private Integer subjectId;

    /**
     * 案件id
     */
    @ApiModelProperty(value="案件id")
    private Integer caseeId;

    /**
     * 类型（0-申请人/原告/上述人/申请执行人等，1-被告/被执行人/被上述人等）
     */
    @ApiModelProperty(value="类型（0-申请人/原告/上述人/申请执行人等，1-被告/被执行人/被上述人等）")
    private Integer type;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;


}
