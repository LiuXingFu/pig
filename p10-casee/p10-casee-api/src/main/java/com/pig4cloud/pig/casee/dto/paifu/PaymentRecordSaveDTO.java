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
import com.pig4cloud.pig.casee.entity.PaymentRecord;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 回款详细记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:52:51
 */
@Data
public class PaymentRecordSaveDTO {

	/**
	 * 费用记录id
	 */
	@ApiModelProperty(value="费用记录id")
	private Integer expenseRecordId;

	/**
	 * 回款时间
	 */
	@ApiModelProperty(value="回款时间")
	private LocalDate paymentDate;

	/**
	 * 回款金额
	 */
	@ApiModelProperty(value="回款金额")
	private BigDecimal paymentAmount;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;

	/**
	 * 附件
	 */
	@ApiModelProperty(value="附件")
	private String appendixFile;

	/**
	 * 还款人名称
	 */
	@ApiModelProperty(value="还款人名称")
	private String subjectName;

	/**
	 * 还款人id集合
	 */
	@ApiModelProperty(value="还款人id集合")
	private List<Integer> subjectIdList;

	/**
	 * 状态(0-正常 1-已还款 2-作废)
	 */
	@ApiModelProperty(value="状态(0-正常 1-已还款 2-作废)")
	private Integer expenseRecordStatus;
}
