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
 * 案件机构关联表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@Data
@TableName("p10_casee_outles_deal_re")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "案件机构关联表")
public class CaseeOutlesDealRe extends BaseEntity {

    /**
     * dealReId
     */
    @TableId
    @ApiModelProperty(value="dealReId")
    private Integer dealReId;

    /**
     * 案件id
     */
    @ApiModelProperty(value="案件id")
    private Integer caseeId;

    /**
     * 机构id
     */
    @ApiModelProperty(value="机构id")
    private Integer insId;

    /**
     * 网点id
     */
    @ApiModelProperty(value="网点id")
    private Integer outlesId;

    /**
     * 网点选中用户id
     */
    @ApiModelProperty(value="网点选中用户id")
    private Integer userId;

    /**
     * 用户名称
     */
    @ApiModelProperty(value="用户名称")
    private String userNickName;

    /**
     * 类型(1-委托机构，2-协办网点)
     */
    @ApiModelProperty(value="类型(1-委托机构，2-协办网点)")
    private Integer type;

    /**
     * 是否可办理
     */
    @ApiModelProperty(value="是否可办理")
    private Integer canDeal;

	/**
	 * 案件状态(0-新建，1-开启、2-暂缓、3-中止、4-结案)
	 */
	@ApiModelProperty(value="案件状态(0-新建，1-开启、2-暂缓、3-中止、4-结案)")
	private Integer status;
	/**
	 * 状态确认(0-默认状态，1-待确认,2-已确认，3-已拒绝)
	 */
	@ApiModelProperty(value="状态确认(0-默认状态，1-待确认,2-已确认，3-已拒绝)")
	private Integer statusConfirm;

    /**
     * 删除标识（0-正常,1-删除）
     */
    @ApiModelProperty(value="删除标识（0-正常,1-删除）")
    private String delFlag;


	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;
}
