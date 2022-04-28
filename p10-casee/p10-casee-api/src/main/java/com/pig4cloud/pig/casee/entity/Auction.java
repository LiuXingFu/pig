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
 * 拍卖表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
@Data
@TableName("p10_auction")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "拍卖表")
public class Auction extends BaseEntity {

    /**
     * 拍卖表
     */
    @TableId
    @ApiModelProperty(value="拍卖表")
    private Integer auctionId;

    /**
     * 是否联合拍卖(1:否，2-是)
     */
    @ApiModelProperty(value="是否联合拍卖(1:否，2-是)")
    private Integer jointAuction;

	/**
	 * 标题
	 */
	@ApiModelProperty(value="标题")
	private String auctionTitle;

	/**
	 * 当前拍卖类型（100-一拍，200-二拍，300-变卖）
	 */
	@ApiModelProperty(value="当前拍卖类型（100-一拍，200-二拍，300-变卖）")
	private String auctionType;

	/**
	 * 当前拍卖状态（100-即将开始，200-正在进行，300-已结束，400-中止，500-撤回）
	 */
	@ApiModelProperty(value="当前拍卖状态（100-即将开始，200-正在进行，300-已结束，400-中止，500-撤回）")
	private Integer auctionStatus;

}
