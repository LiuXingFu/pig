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
 * 清收移交表
 *
 * @author yuanduo
 * @date 2022-04-21 15:39:01
 */
@Data
@TableName("p10_liqui_transfer")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "清收移交表")
public class LiquiTransfer extends BaseEntity {

    /**
     * 清收移交表id
     */
    @TableId
    @ApiModelProperty(value="清收移交表id")
    private Integer liquiTransferId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;

    /**
     * 清收项目id
     */
    @ApiModelProperty(value="清收项目id")
    private Integer projectId;

    /**
     * 节点 ID
     */
    @ApiModelProperty(value="节点 ID")
    private String nodeId;

    /**
     * 案件id
     */
    @ApiModelProperty(value="案件id")
    private Integer caseeId;

    /**
     * 案件案号
     */
    @ApiModelProperty(value="案件案号")
    private String caseeNumber;


}
