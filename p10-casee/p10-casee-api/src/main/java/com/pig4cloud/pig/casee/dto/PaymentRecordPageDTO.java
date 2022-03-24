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
package com.pig4cloud.pig.casee.dto;

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
 * 回款详细记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:52:51
 */
@Data
public class PaymentRecordPageDTO {

    /**
     * 案件id
     */
    @ApiModelProperty(value="案件id")
    private Integer caseeId;

    /**
     * 公司业务案号
     */
    @ApiModelProperty(value="公司业务案号")
    private String companyCode;


	/**
	 * 还款人名称
	 */
	@ApiModelProperty(value="还款人名称")
	private String subjectName;

	@ApiModelProperty(value="开始时间")
	private LocalDate beginDate;

	@ApiModelProperty(value="结束时间")
	private LocalDate endDate;



}
