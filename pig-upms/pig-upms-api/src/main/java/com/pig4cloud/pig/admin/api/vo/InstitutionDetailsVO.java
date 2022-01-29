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

import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Institution;
import lombok.Data;

import java.util.List;

/**
 * 机构表
 *
 * @author yuanduo
 * @date 2021-09-02 16:16:06
 */
@Data
public class InstitutionDetailsVO extends Institution {

	/**
	 * 地址信息
	 */
	private Address address;

	/**
	 * 员工集合
	 */
	private List<InsOutlesUserListVO> userList;
}
