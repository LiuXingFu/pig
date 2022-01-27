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

import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import lombok.Data;

/**
 * 流程节点模板表
 *
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
@Data
public class TaskNodeTemplateDTO extends TaskNodeTemplate {
	/**
	 * 机构id
	 */
	Integer insId;

	/**
	 * 网点id
	 */
	Integer outlesId;

	/**
	 * 项目id
	 */
	Integer projectId;

	/**
	 * 案件id
	 */
	Integer caseeId;

	/**
	 * 程序id
	 */
	Integer targetId;




}
