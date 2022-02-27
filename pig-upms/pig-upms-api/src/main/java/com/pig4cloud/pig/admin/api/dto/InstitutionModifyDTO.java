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


import lombok.Data;

/**
 * 机构表
 *
 * @author yuanduo
 * @date 2021-09-02 16:16:06
 */
@Data
public class InstitutionModifyDTO{

	/**
	 * 机构id 主键自增
	 */
	private Integer insId;
	/**
	 * 机构名称
	 */
	private String insName;
	/**
	 * 机构类型
	 */
	private Integer insType;
	/**
	 * 传真
	 */
	private String insFax;
	/**
	 * 座机电话
	 */
	private String insLandLinePhone;
	/**
	 * 备注
	 */
	private String insRemark;
	/**
	 * 地址id
	 */
	private Integer addressId;
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
	 * 法院id
	 */
	private Integer courtId;

	/**
	 * 主体
	 */
	private AddSubjectOrAddressDTO subject;
}
