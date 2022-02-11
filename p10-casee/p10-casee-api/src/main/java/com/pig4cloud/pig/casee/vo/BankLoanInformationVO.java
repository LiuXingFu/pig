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

import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.casee.entity.BankLoan;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


import java.util.List;

/**
 * 银行借贷基本信息
 *
 * @author Mjh
 * @date 2022-01-29 10:20:00
 */
@Data
public class BankLoanInformationVO extends BankLoan {

	/**
	 * 债务人信息
	 */
	private List<SubjectInformationVO> subjectInformationVOList;

	/**
	 * 抵押物信息
	 */
	private List<AssetsInformationVO> assetsList;

	/**
	 * 状态(0-待接收 1-已接收 2-退回 3-已完成)
	 */
	private Integer status;

}
