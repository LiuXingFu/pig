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
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 和解/调解表
 *
 * @author Mjh
 * @date 2022-03-01 20:36:17
 */
@Data
@TableName("p10_reconciliatio_mediation")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "和解/调解表")
public class ReconciliatioMediation extends BaseEntity {

    /**
     * 和解/调解id
     */
    @TableId
    @ApiModelProperty(value="和解/调解id")
    private Integer reconciliatioMediationId;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;

    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private Integer projectId;

    /**
     * 调解诉讼案号id
     */
    @ApiModelProperty(value="调解诉讼案号id")
    private Integer caseeId;

    /**
     * 和解人id
     */
    @ApiModelProperty(value="和解人id")
    private Integer userId;

    /**
     * 和解人名称
     */
    @ApiModelProperty(value="和解人名称")
    private String userNickName;

    /**
     * 和解/调解日期
     */
    @ApiModelProperty(value="和解/调解日期")
    private LocalDate reconciliatioMediationData;

    /**
     * 和解方式(0-分期 1-不分期)
     */
    @ApiModelProperty(value="和解方式(0-不分期 1-分期)")
    private Integer reconciliationWay;

    /**
     * 和解/调解附件
     */
    @ApiModelProperty(value="和解/调解附件")
    private String appendixFile;

    /**
     * 待履行人
     */
    @ApiModelProperty(value="待履行人")
    private String subjectName;

    /**
     * 签订地点
     */
    @ApiModelProperty(value="签订地点")
    private String placeOfSigning;

    /**
     * 金额
     */
    @ApiModelProperty(value="金额")
    private BigDecimal amount;

	/**
	 * 是否有调解文书(0-无 1-有)
	 */
	@ApiModelProperty(value="是否有调解文书(0-无 1-有)")
	private Integer isMediation;

    /**
     * 状态(0-待履行 1-正常履行 2-不能履行 3-推迟履行)
     */
    @ApiModelProperty(value="状态(0-待履行 1-正常履行 2-不能履行 3-推迟履行 4-履行完成)")
    private Integer status;

    /**
     * 类型(0-和解 1-调解)
     */
    @ApiModelProperty(value="类型(0-和解 1-调解)")
    private Integer type;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

	/**
	 * 明细
	 */
	@ApiModelProperty(value="明细")
	private String details;
}
