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
package com.pig4cloud.pig.admin.api.vo;

import com.pig4cloud.pig.admin.api.entity.InsOutlesUser;
import lombok.Data;

/**
 * 机构/网点用户关联表
 *
 * @author xls
 * @date 2022-01-27 19:30:49
 */
@Data
public class InsOutlesUserInsOutlesVO extends InsOutlesUser {

	/**
	 * 机构名称
	 */
	private String insName;

	/**
	 * 网点名称
	 */
	private String outlesName;

	/**
	 * 真实姓名
	 */
	private String actualName;

	/**
	 * 电话
	 */
	private String phone;


}
