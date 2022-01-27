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

package com.pig4cloud.pig.admin.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.admin.api.dto.OutlesTemplateReDTO;
import com.pig4cloud.pig.admin.api.entity.OutlesTemplateRe;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;

import java.util.List;

/**
 * 网点任务模板关联表
 *
 * @author Mjh
 * @date 2021-12-17 11:31:08
 */
public interface OutlesTemplateReService extends IService<OutlesTemplateRe> {
	/**
	 * 配置网点模板
	 *
	 * @param outlesTemplateReDTO
	 * @return R
	 */
	boolean configureOutlesTemplate(OutlesTemplateReDTO outlesTemplateReDTO);

	/**
	 * 通过网点id查询网点模板关联信息
	 *
	 * @param outlesId
	 * @return R
	 */
	List<OutlesTemplateRe> getByOutlesId(Integer outlesId);

	/**
	 * 通过标的性质查询网点模板信息
	 *
	 * @param templateNature
	 * @return R
	 */
	TaskNodeTemplate queryTemplateByTemplateNature(Integer templateNature,Integer outlesId);

}
