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
package com.pig4cloud.pig.casee.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 主体关联银行借贷表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:44
 */
@Data
@TableName("p10_subject_bank_loan_re")
@ApiModel(value = "主体关联银行借贷表")
public class SubjectBankLoanRe {

    /**
     * 主体关联银行借贷表id
     */
    @TableId
    @ApiModelProperty(value="主体关联银行借贷表id")
    private Integer subjectBankLoanId;

    /**
     * 银行借贷表id
     */
    @ApiModelProperty(value="银行借贷表id")
    private Integer bankLoanId;

    /**
     * 主体表id
     */
    @ApiModelProperty(value="主体表id")
    private Integer subjectId;

	/**
	 * 债务类型(1-借款人，2-共同借款人，3-担保人)
	 */
	@ApiModelProperty(value = "债务类型(1-借款人，2-共同借款人，3-担保人)")
	private Integer debtType;

}
