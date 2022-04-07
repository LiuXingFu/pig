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

import java.time.LocalDate;

/**
 * 清收移交记录表
 *
 * @author Mjh
 * @date 2022-04-06 15:21:31
 */
@Data
@TableName("p10_liqui_transfer_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "清收移交记录表")
public class LiquiTransferRecord extends BaseEntity {

    /**
     * 清收移交记录id
     */
    @TableId
    @ApiModelProperty(value="清收移交记录id")
    private Integer liquiTransferRecordId;

    /**
     * 委托机构id
     */
    @ApiModelProperty(value="委托机构id")
    private Integer entrustInsId;

    /**
     * 委托网点id
     */
    @ApiModelProperty(value="委托网点id")
    private Integer entrustOutlesId;

    /**
     * 受托机构id
     */
    @ApiModelProperty(value="受托机构id")
    private Integer entrustedInsId;

    /**
     * 受托网点id
     */
    @ApiModelProperty(value="受托网点id")
    private Integer entrustedOutlesId;

    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private Integer projectId;

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

    /**
     * 移送时间
     */
    @ApiModelProperty(value="移送时间")
    private LocalDate handoverTime;

    /**
     * 接收/退回时间
     */
    @ApiModelProperty(value="接收/退回时间")
    private LocalDate returnTime;

    /**
     * 状态(0-待接收 1-已接收 2-退回 3-已完成)
     */
    @ApiModelProperty(value="状态(0-待接收 1-已接收 2-退回 3-已完成)")
    private Integer status;

    /**
     * 反馈备注
     */
    @ApiModelProperty(value="反馈备注")
    private String feedbackNotes;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;


}
