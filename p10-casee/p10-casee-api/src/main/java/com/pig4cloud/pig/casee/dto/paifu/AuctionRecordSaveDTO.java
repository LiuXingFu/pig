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
package com.pig4cloud.pig.casee.dto.paifu;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 拍卖记录表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
@Data
public class AuctionRecordSaveDTO {

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
	 * 项目案件财产关联id集合
	 */
	@ApiModelProperty(value="项目案件财产关联id集合")
	private List<Integer> assetsReIdList;

    /**
     * 拍卖表id
     */
    @ApiModelProperty(value="拍卖表id")
    private Integer auctionId;

    /**
     * 标题
     */
    @ApiModelProperty(value="标题")
    private String title;

    /**
     * 拍卖类型（100-一拍，200-二拍，300-变卖）
     */
    @ApiModelProperty(value="拍卖类型（100-一拍，200-二拍，300-变卖）")
    private String type;

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
