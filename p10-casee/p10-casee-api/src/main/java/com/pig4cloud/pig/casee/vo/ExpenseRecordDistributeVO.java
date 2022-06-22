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


import com.pig4cloud.pig.casee.vo.paifu.AssetsRePaifuDetailVO;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * 费用产生记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:53:07
 */
@Data
public class ExpenseRecordDistributeVO {
	/**
	 * 财产关联集合
	 */
	List<AssetsRePaifuDetailVO> assetsReList;

	/**
	 * 回款总金额
	 */
	private BigDecimal paymentSumAmount;

	/**
	 * 费用记录id
	 */
	private Integer expenseRecordId;

    /**
     * 款项类型(10001-本金/利息 10002-保全费 10003-一审诉讼费 10004-二审诉讼费 10005-首次执行费 10006-定价费 10007-拍辅费 10008-代理费 10009-其它费用 20001-调解结果 30001-移交金额)
     */
    private Integer fundsType;

	/**
	 * 案件id
	 */
	private Integer caseeId;

	/**
	 * 项目id
	 */
	private Integer projectId;

	/**
     * 费用产生时间
     */
    private LocalDate costIncurredTime;

    /**
     * 回款金额
     */
    private BigDecimal paymentAmount;

	/**
	 * 案件案号
	 */
	private String caseeNumber;

	/**
	 * 公司业务案号
	 */
	private String companyCode;

	/**
	 * 所有债务人名称
	 */
	private String subjectName;

}
