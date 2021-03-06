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

import com.pig4cloud.pig.casee.entity.PaymentRecord;
import lombok.Data;

import java.util.List;

/**
 * 回款详细记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:52:51
 */
@Data
public class PaymentRecordDTO extends PaymentRecord {
	/**
	 * 分配款项记录
	 */
	List<PaymentRecordAddDTO> paymentRecordList;

	/**
	 * 主体id集合
	 */
	private List<Integer> subjectIdList;

	/**
	 * 财产关联id集合
	 */
	private List<Integer> assetsReIdList;

	/**
	 * 到款记录
	 */
	private List<PaymentRecord> courtPayment;

}

