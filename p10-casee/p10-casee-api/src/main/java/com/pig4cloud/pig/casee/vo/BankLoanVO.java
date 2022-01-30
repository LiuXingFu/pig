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
package com.pig4cloud.pig.casee.vo;

import com.pig4cloud.pig.casee.entity.BankLoan;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 银行借贷表
 *
 * @author Mjh
 * @date 2022-01-29 10:20:00
 */
@Data

public class BankLoanVO extends BankLoan {
	/**
	 * 受托机构
	 */
	private String entrustedInsName;

	/**
	 * 受托网点
	 */
	private String entrustedOutlesName;

	/**
	 * 债务人
	 */
	private List<String> subjectNameList;

	/**
	 * 诉讼情况(0-未诉讼 1-已诉讼)
	 */
	private Integer litigation;

	/**
	 * 移交金额
	 */
	private BigDecimal handoverAmount;

	/**
	 * 移交时间
	 */
	private LocalDateTime handoverTime;

	/**
	 * 状态(0-待接收 1-已接收 2-退回 3-已完成)
	 */
	private Integer status;
}
