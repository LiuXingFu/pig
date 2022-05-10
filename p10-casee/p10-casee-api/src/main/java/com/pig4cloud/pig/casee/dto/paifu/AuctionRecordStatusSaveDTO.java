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

import java.time.LocalDate;

/**
 * 拍卖记录状态表
 *
 * @author pig code generator
 * @date 2022-04-25 18:54:58
 */
@Data
public class AuctionRecordStatusSaveDTO{

    /**
     * 拍卖记录id
     */
    @ApiModelProperty(value="拍卖记录id")
    private Integer auctionRecordId;

    /**
     * 改变时间
     */
    @ApiModelProperty(value="改变时间")
    private LocalDate changeTime;

    /**
     * 状态（100-即将开始，200-正在进行，300-已结束，400-中止，500-撤回）
     */
    @ApiModelProperty(value="状态（100-即将开始，200-正在进行，300-已结束，400-中止，500-撤回）")
    private Integer status;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseeId;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

	/**
	 * 财产id
	 */
	@ApiModelProperty(value="财产id")
	private Integer assetsId;
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
