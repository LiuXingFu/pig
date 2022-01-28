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
import com.pig4cloud.pig.admin.api.entity.SysUser;
import lombok.Data;

import java.util.List;

/**
 * 网点表
 *
 * @author yuanduo
 * @date 2021-09-02 16:24:58
 */
@Data
public class OutlesAddDTO{

	/**
	 * 网点名称
	 */
	private String outlesName;
	/**
	 * 机构id
	 */
	private Integer insId;
	/**
	 * 简称
	 */
	private String alias;
	/**
	 * 座机电话
	 */
	private String outlesLandLinePhone;
	/**
	 * 备注
	 */
	private String outlesRemark;

	/**
	 * 地址
	 */
	private Address address;

	/**
	 * 负责人集合
	 */
	private List<SysUser> userList;
}
