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


import com.pig4cloud.pig.casee.entity.Assets;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Data
public class AssetsDTO extends Assets {
	/**
	 * 银行借贷表id
	 */
	private Integer bankLoanId;

	/**
	 * 财产关联银行借贷表id
	 */
	private Integer assetsBankLoanId;

	/**
	 * 财产id
	 */
	private Integer assetsId;

	/**
	 * 主体表id
	 */
	private Integer subjectId;

	/**
	 * 地址id
	 */
	private Integer addressAsId;

	/**
	 * 抵押金额
	 */
	private BigDecimal mortgageAmount;

	/**
	 * 抵押时间
	 */
	private LocalDate mortgageTime;

	/**
	 * 省
	 */
	private String province;
	/**
	 * 市
	 */
	private String city;
	/**
	 * 区
	 */
	private String area;
	/**
	 * 信息地址
	 */
	private String informationAddress;
	/**
	 * 行政区划编号
	 */
	private String code;

	/**
	 * 地址来源（比如裁判文书网）
	 */
	private String source;

	/**
	 * 财产性质名称
	 */
	private String assetsTypeName;


}
