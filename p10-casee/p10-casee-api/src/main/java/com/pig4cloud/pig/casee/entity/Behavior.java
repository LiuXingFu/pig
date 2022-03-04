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
import java.time.LocalDateTime;

/**
 * 行为表
 *
 * @author yuanduo
 * @date 2022-02-14 15:51:27
 */
@Data
@TableName("p10_behavior")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "行为表")
public class Behavior extends BaseEntity {

    /**
     * 行为表id
     */
    @TableId
    @ApiModelProperty(value="行为表id")
    private Integer behaviorId;

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
     * 债务人id
     */
    @ApiModelProperty(value="债务人id")
    private Integer subjectId;

	/**
	 * 被执行人名称
	 */
	@ApiModelProperty(value="被执行人名称")
	private String executedName;

    /**
     * 案件id
     */
    @ApiModelProperty(value="案件id")
    private Integer caseeId;

    /**
     * 程序id
     */
    @ApiModelProperty(value="程序id")
    private Integer targetId;

    /**
     * 行为类型(100-行为限制 200-行为违法)
     */
    @ApiModelProperty(value="行为类型(100-行为限制 200-行为违法)")
    private Integer type;

    /**
     * 行为日期
     */
    @ApiModelProperty(value="行为日期")
    private LocalDate behaviorDate;

    /**
     * 限制类型（101-限制高消费 102-纳入失信名单 103-限制出境 201-伪造证据、暴力、威胁等方法妨碍法院强制执行 202-虚假诉讼、虚假仲裁、隐匿、转移财产等规避执行 203-涉及刑事犯罪）
     */
    @ApiModelProperty(value="限制类型（101-限制高消费 102-纳入失信名单 103-限制出境 201-伪造证据、暴力、威胁等方法妨碍法院强制执行 202-虚假诉讼、虚假仲裁、隐匿、转移财产等规避执行 203-涉及刑事犯罪）")
    private Integer limitType;

    /**
     * 行为详情
     */
    @ApiModelProperty(value="行为详情")
    private String behaviorDetail;

    /**
     * 行为状态（1-正常 2-撤销）
     */
    @ApiModelProperty(value="行为状态（1-正常 2-撤销）")
    private Integer behaviorStatus;

	/**
	 * 附件
	 */
	@ApiModelProperty(value="附件")
	private String appendix;

    /**
     * 备注
     */
    @ApiModelProperty(value="备注")
    private String remark;


}
