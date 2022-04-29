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
 * 引领看样表
 *
 * @author Mjh
 * @date 2022-04-29 10:49:17
 */
@Data
@TableName("p10_lead_the_way")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "引领看样表")
public class LeadTheWay extends BaseEntity {

    /**
     * 引领看样表id
     */
    @TableId
    @ApiModelProperty(value="引领看样表id")
    private Integer leadTheWayId;

    /**
     * 看样准备工作表id
     */
    @ApiModelProperty(value="看样准备工作表id")
    private Integer samplePreparationWorkId;

    /**
     * 附件地址
     */
    @ApiModelProperty(value="附件地址")
    private String fileUrl;

    /**
     * 图片地址
     */
    @ApiModelProperty(value="图片地址")
    private String imageUrl;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}
