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
 * 财产关联清收移交记录表
 *
 * @author Mjh
 * @date 2022-04-06 15:22:06
 */
@Data
@TableName("p10_assets_liqui_transfer_record_re")
@ApiModel(value = "财产关联清收移交记录表")
public class AssetsLiquiTransferRecordRe {

    /**
     * 财产关联清收移交记录id
     */
    @TableId
    @ApiModelProperty(value="财产关联清收移交记录id")
    private Integer assetsLiquiTransferRecordId;

    /**
     * 财产id
     */
    @ApiModelProperty(value="财产id")
    private Integer assetsId;

    /**
     * 清收移交记录id
     */
    @ApiModelProperty(value="清收移交记录id")
    private Integer liquiTransferRecordId;


}
