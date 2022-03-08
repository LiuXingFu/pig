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
import com.pig4cloud.pig.casee.entity.liquientity.FulfillmentRecordsLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.FulfillmentRecordsDetail;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 待履行记录表
 *
 * @author Mjh
 * @date 2022-03-01 20:36:31
 */
@Data
public class FulfillmentRecordsDTO extends FulfillmentRecordsLiqui {

	/**
	 * 项目id
	 */
	private Integer projectId;

	/**
	 * 案件id
	 */
	private Integer caseeId;

	/**
	 * 公司业务案号
	 */
	private String companyCode;

	/**
	 * 案件案号
	 */
	private String caseeNumber;

	/**
	 * 回款类型(100-法院领款 200-法院到款 300-履行到申请人)
	 */
	private Integer paymentType;

	/**
	 * 回款时间
	 */
	private LocalDate paymentDate;

	/**
	 * 回款金额
	 */
	private BigDecimal paymentAmount;

	/**
	 * 还款人名称
	 */
	private String subjectName;

	/**
	 * 还款人
	 */
	private Integer subjectId;

	/**
	 * 分配款项记录
	 */
	List<PaymentRecord> paymentRecordList;

	/**
	 * 推迟履行日期
	 */
	private LocalDate deferPerformance;

}
