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
 * 拍卖记录表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
@Data
@TableName("p10_auction_record")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "拍卖记录表")
public class AuctionRecord extends BaseEntity {

    /**
     * 拍卖记录id
     */
    @TableId
    @ApiModelProperty(value="拍卖记录id")
    private Integer auctionRecordId;

    /**
     * 拍卖表id
     */
    @ApiModelProperty(value="拍卖表id")
    private Integer auctionId;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String auctionTitle;

    /**
     * 拍卖类型（100-一拍，200-二拍，300-变卖）
     */
    @ApiModelProperty(value="拍卖类型（100-一拍，200-二拍，300-变卖）")
    private Integer auctionType;

    /**
     * 拍卖状态（100-即将开始，200-正在进行，300-已结束，400-中止，500-撤回）
     */
    @ApiModelProperty(value="拍卖状态（100-即将开始，200-正在进行，300-已结束，400-中止，500-撤回）")
    private Integer auctionStatus;

    /**
     * 拍卖公告
     */
    @ApiModelProperty(value="拍卖公告")
    private String auctionAnnouncement;

    /**
     * 公告发布时间
     */
    @ApiModelProperty(value="公告发布时间")
    private LocalDate announcementStartTime;

    /**
     * 拍卖开始时间
     */
    @ApiModelProperty(value="拍卖开始时间")
    private LocalDate auctionStartTime;

    /**
     * 拍卖结束时间
     */
    @ApiModelProperty(value="拍卖结束时间")
    private LocalDate auctionEndTime;

    /**
     * 起拍价
     */
    @ApiModelProperty(value="起拍价")
    private BigDecimal startingPrice;

    /**
     * 拍卖平台
     */
    @ApiModelProperty(value="拍卖平台")
    private Integer auctionPlatform;

    /**
     * 拍卖链接
     */
    @ApiModelProperty(value="拍卖链接")
    private String auctionLink;

	/**
	 * 是否联合拍卖(1:否，2-是)
	 */
	@ApiModelProperty(value="是否联合拍卖(1:否，2-是)")
	private Integer jointAuction;

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

	/**
	 * 详情
	 */
	@ApiModelProperty(value="详情")
	private String auctionRecordDetail;


}
