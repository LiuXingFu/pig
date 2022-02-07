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

import com.pig4cloud.pig.casee.entity.BankLoan;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 银行借贷表DTO
 *
 * @author Mjh
 * @date 2022-01-29 10:20:00
 */
@Data
public class BankLoanDTO extends BankLoan {

	/**
	 * 主体表id
	 */
	private List<Integer> subjectIdList;

	/**
	 * 抵押金额
	 */
	private BigDecimal mortgageAmount;

	/**
	 * 抵押时间
	 */
	private LocalDateTime mortgageTime;

	/**
	 * 抵押财产信息
	 */
	private List<AssetsDTO> assetsDTOList;


	//列表条件查询数据
	/**
	 * 受托方
	 */
	private String trustee;

	/**
	 * 债务人
	 */
	private String debtor;

	/**
	 * 移送时间
	 */
	private LocalDateTime handoverTime;
}
