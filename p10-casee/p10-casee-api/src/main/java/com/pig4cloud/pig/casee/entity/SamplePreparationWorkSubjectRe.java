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

/**
 * 看样人员关联看样准备工作表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:17
 */
@Data
@TableName("p10_sample_preparation_work_subject_re")
@ApiModel(value = "看样人员关联看样准备工作表")
public class SamplePreparationWorkSubjectRe {

    /**
     * 看样人员关联看样准备工作表id
     */
    @TableId
    @ApiModelProperty(value="看样人员关联看样准备工作表id")
    private Integer samplePreparationWorkSubjectReId;

    /**
     * 看样准备工作表id
     */
    @ApiModelProperty(value="看样准备工作表id")
    private Integer samplePreparationWorkId;

    /**
     * 看样人员id
     */
    @ApiModelProperty(value="看样人员id")
    private Integer subjectId;


}
