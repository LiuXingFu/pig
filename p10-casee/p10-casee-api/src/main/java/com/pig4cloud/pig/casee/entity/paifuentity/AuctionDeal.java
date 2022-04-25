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
package com.pig4cloud.pig.casee.entity.paifuentity;

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
 * 拍卖成交表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:57
 */
@Data
@TableName("p10_auction_deal")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "拍卖成交表")
public class AuctionDeal extends BaseEntity {

    /**
     * 拍卖成交id
     */
    @TableId
    @ApiModelProperty(value="拍卖成交id")
    private Integer auctionDeal;

    /**
     * 拍卖记录id
     */
    @ApiModelProperty(value="拍卖记录id")
    private Integer auctionRecordId;

    /**
     * 成交时间
     */
    @ApiModelProperty(value="成交时间")
    private LocalDate dealTime;

    /**
     * 成交价格
     */
    @ApiModelProperty(value="成交价格")
    private BigDecimal dealPrice;

    /**
     * 参拍人数
     */
    @ApiModelProperty(value="参拍人数")
    private Integer auctionPeopleNumber;

    /**
     * 买受人
     */
    @ApiModelProperty(value="买受人")
    private String buyer;

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
