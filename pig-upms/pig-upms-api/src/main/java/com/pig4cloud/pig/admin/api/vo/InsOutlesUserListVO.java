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

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 机构/网点用户关联表
 *
 * @author xls
 * @date 2022-01-27 19:30:49
 */
@Data
public class InsOutlesUserListVO {

    /**
     * 主键id
     */
    private Integer insOutlesUserId;

    /**
     * 入职时间
     */
    private LocalDateTime entryTime;

    /**
     * 职位
     */
    private String position;

	/**
	 * 真实姓名
	 */
	private String actualName;

	/**
	 * 手机号
	 */
	private String phone;


}
