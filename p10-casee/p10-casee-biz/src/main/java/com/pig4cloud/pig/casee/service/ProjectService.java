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

package com.pig4cloud.pig.casee.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.entity.Project;

import java.math.BigDecimal;

/**
 * 项目表
 *
 * @author pig code generator
 * @date 2022-02-10 17:30:36
 */
public interface ProjectService extends IService<Project> {

	BigDecimal getProjectAmountBySubjectId(Integer subjectId);

	/**
	 * 根据项目类型和案件案号查询项目id
	 * @param projectType
	 * @param caseeNumber
	 * @param insOutlesDTO
	 * @return
	 */
	Project getProjectIdByCaseeNumber(Integer projectType,String caseeNumber, InsOutlesDTO insOutlesDTO);

}
