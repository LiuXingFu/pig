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
import com.pig4cloud.pig.casee.dto.ProjectStatusSaveDTO;
import com.pig4cloud.pig.casee.entity.ProjectStatus;

/**
 * 项目/案件状态表
 *
 * @author ligt
 * @date 2022-01-18 15:21:05
 */
public interface ProjectStatusService extends IService<ProjectStatus> {

	/**
	 * 保存状态记录
	 * @param projectStatusSaveDTO
	 * @return
	 */
	Integer saveStatusRecord(ProjectStatusSaveDTO projectStatusSaveDTO);
}
