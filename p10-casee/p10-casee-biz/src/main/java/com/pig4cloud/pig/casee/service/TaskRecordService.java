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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.entity.TaskRecord;
import com.pig4cloud.pig.casee.vo.AgentMatterVO;


/**
 * 已处理任务表
 *
 * @author Mjh
 * @date 2021-09-15 10:03:23
 */
public interface TaskRecordService extends IService<TaskRecord> {
	IPage<AgentMatterVO> taskRecordList(Page page, TaskFlowDTO taskFlowDTO);

	/**
	 * 添加消息记录
	 * @param objectDTO		提交数据DTO对象
	 * @param taskFlowName	任务流对象名称
	 * @return
	 */
	Boolean addTaskRecord(Object objectDTO,String taskFlowName);
}
