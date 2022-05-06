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

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 费用产生记录拍辅保存dto
 *
 * @author Mjh
 * @date 2022-02-17 17:53:07
 */
@Data
public class ExpenseRecordPaifuSaveDTO {

	/**
	 * 费用产生记录id
	 */
	@ApiModelProperty(value="费用产生记录id")
	private Integer expenseRecordId;

    /**
     * 项目id
     */
    @ApiModelProperty(value="项目id")
    private Integer projectId;

    /**
     * 费用类型(10001-本金 10002-保全费 10003-一审诉讼费 10004-二审诉讼费 10005-首次执行费 10006-定价费 10007-拍辅费 10008-代理费 10009-其它费用 20001-调解结果 30001-利息)
     */
    @ApiModelProperty(value="费用类型(10001-本金 10002-保全费 10003-一审诉讼费 10004-二审诉讼费 10005-首次执行费 10006-定价费 10007-拍辅费 10008-代理费 10009-其它费用 20001-调解结果 30001-利息)")
    private Integer costType;

    /**
     * 费用产生时间
     */
    @ApiModelProperty(value="费用产生时间")
    private LocalDate costIncurredTime;

    /**
     * 费用金额
     */
    @ApiModelProperty(value="费用金额")
    private BigDecimal costAmount;

    /**
     * 公司业务案号
     */
    @ApiModelProperty(value="公司业务案号")
    private String companyCode;

	/**
	 * 所有债务人名称
	 */
	@ApiModelProperty(value="所有债务人名称")
	private String subjectName;

	/**
	 * 关联财产id集合
	 */
	@ApiModelProperty(value="关联财产id集合")
	private List<Integer> assetsReIdList;

}
