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
 * 财产关联债务人表
 *
 * @author Mjh
 * @date 2022-04-13 11:23:59
 */
@Data
@TableName("p10_mortgage_assets_subject_re")
@ApiModel(value = "财产关联债务人表")
public class MortgageAssetsSubjectRe  {

    /**
     * 财产关联债务人id
     */
    @TableId
    @ApiModelProperty(value="财产关联债务人id")
    private Integer mortgageAssetsSubjectReId;

    /**
     * 抵押财产关联表id
     */
    @ApiModelProperty(value="抵押财产关联表id")
    private Integer mortgageAssetsReId;

    /**
     * 债务人id
     */
    @ApiModelProperty(value="债务人id")
    private Integer subjectId;


}
