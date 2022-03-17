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

package com.pig4cloud.pig.admin.api.dto;

import com.pig4cloud.pig.admin.api.entity.Address;
import lombok.Data;

import java.util.List;

/**
 * 主体
 *
 * @author yy
 * @date 2021-09-17 16:55:57
 */
@Data
public class SubjectAddressDTO {
	/**
	 * 银行借贷id
	 */
	private Integer bankLoanId;

	/**
	 * 银行借贷所有债务人名称
	 */
	private String subjectName;

	//债务人主体信息
	/**
	 * 主体id
	 */
	private Integer subjectId;

	/**
	 * 统一标识(身份证/统一社会信用代码/组织机构代码)
	 */
	private String unifiedIdentity;

	/**
	 * 性质类型 0-个人 1-企业/公司
	 */
	private Integer natureType;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 联系方式
	 */
	private String phone;

	/**
	 * 法人
	 */
	private String legalPerson;

	/**
	 * 电子邮件
	 */
	private String email;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 是否认证(0-否，1-是)
	 */
	private Integer isAuthentication;

	/**
	 * 用户id
	 */
	private Integer userId;

	/**
	 * 工作单位
	 */
	private String employer;

	/**
	 * 债务类型(1-借款人，2-共同借款人，3-担保人)
	 */
	private Integer debtType;

	/**
	 * 主体关联银行借贷表id
	 */
	private Integer subjectBankLoanId;

	/**
	 * 性别（1-男，2-女）默认值0-不详
	 */
	private Integer gender;

	/**
	 * 民族
	 */
	private String ethnic;

	//债务人联系地址
	List<Address> addressList;
}
