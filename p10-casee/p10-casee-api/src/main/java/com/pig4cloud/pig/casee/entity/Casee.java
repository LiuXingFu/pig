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
import java.time.LocalDateTime;

/**
 * 案件表
 *
 * @author yy
 * @date 2022-01-10 14:51:59
 */
@Data
@TableName("p10_casee")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "案件表")
public class Casee extends BaseEntity {

    /**
     * caseeId
     */
    @TableId
    @ApiModelProperty(value="caseeId")
    private Integer caseeId;

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
     * 案号
     */
    @ApiModelProperty(value="案号")
    private String caseeNumber;

    /**
     * 案件类型(10000:诉前保全案件 10001:诉讼保全案件 10002:一审诉讼案件 10003:二审诉讼案件 10004:其它案件 10005:首次执行案件 10006:执灰案件)
     */
    @ApiModelProperty(value="案件类型(10000:诉前保全案件 10001:诉讼保全案件 10002:一审诉讼案件 10003:二审诉讼案件 10004:其它案件 10005:首次执行案件 10006:执灰案件)")
    private Integer caseeType;

    /**
     * 案件状态(0-待立案 1-在办 2- 撤案 3-结案 4-终结 5-实际执结)
     */
    @ApiModelProperty(value="案件状态(0-待立案 1-在办 2- 撤案 3-结案 4-终结 5-实际执结) ")
    private Integer status;

    /**
     * 办理人id
     */
    @ApiModelProperty(value="办理人id")
    private Integer userId;

    /**
     * 办理人名称
     */
    @ApiModelProperty(value="办理人名称")
    private String userNickName;

    /**
     * 立案日期
     */
    @ApiModelProperty(value="立案日期")
    private LocalDateTime startTime;

    /**
     * 结案日期
     */
    @ApiModelProperty(value="结案日期")
    private LocalDateTime closeTime;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 司法费金额
     */
    @ApiModelProperty(value="司法费金额")
    private BigDecimal judicialExpenses;

    /**
     * 父案件id
     */
    @ApiModelProperty(value="父案件id")
    private Integer parentId;

    /**
     * 类别（0-一审 1-二审 2-首执 3- 执恢）
     */
    @ApiModelProperty(value="类别（0-一审 1-二审 2-首执 3- 执恢）")
    private Integer category;

    /**
     * 承办法院id
     */
    @ApiModelProperty(value="承办法院id")
    private Integer courtId;

    /**
     * 法院部门id
     */
    @ApiModelProperty(value="法院部门id")
    private Integer judgeOutlesId;

    /**
     * 承办法官id
     */
    @ApiModelProperty(value="承办法官id")
    private Integer judgeId;

    /**
     * 法官名称
     */
    @ApiModelProperty(value="法官名称")
    private String judgeName;

    /**
     * 案件总金额
     */
    @ApiModelProperty(value="案件总金额")
    private BigDecimal caseeAmount;

    /**
     * 被告/被执行人/被上诉人等
     */
    @ApiModelProperty(value="被告/被执行人/被上诉人等")
    private String executedName;

    /**
     * 申请人/原告/上诉人/申请执行人等
     */
    @ApiModelProperty(value="申请人/原告/上诉人/申请执行人等")
    private String applicantName;

    /**
     * 案件明细
     */
    @ApiModelProperty(value="案件明细")
    private String caseeDetail;


}
