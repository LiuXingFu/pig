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
 * 拍卖结果表
 *
 * @author pig code generator
 * @date 2022-04-25 20:59:35
 */
@Data
@TableName("p10_auction_results")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "拍卖结果表")
public class AuctionResults extends BaseEntity {

    /**
     * 拍卖结果id
     */
    @TableId
    @ApiModelProperty(value="拍卖结果id")
    private Integer auctionResultsId;

    /**
     * 拍卖记录id
     */
    @ApiModelProperty(value="拍卖记录id")
    private Integer auctionRecordId;

    /**
     * 结果时间
     */
    @ApiModelProperty(value="结果时间")
    private LocalDate resultsTime;

	/**
	 * 结果类型（10-成交，20-流拍，30-撤回，40-中止，50-抵偿）
	 */
	@ApiModelProperty(value="结果类型（10-成交，20-流拍，30-撤回，40-中止，50-抵偿）")
	private Integer resultsType;

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
