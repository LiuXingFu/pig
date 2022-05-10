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

package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.AuditTargetDTO;
import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.vo.AgentMatterVO;
import com.pig4cloud.pig.casee.vo.TaskNodeVO;
import com.pig4cloud.pig.common.core.util.KeyValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 流程节点表
 *
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
@Mapper
public interface TaskNodeMapper extends BaseMapper<TaskNode> {

	/**
	 * 根据案件id查询所有模板数据
	 * @param caseeId 案件id
	 * @return R
	 */
	List<TaskNodeVO> getTaskNodeAll(@Param("caseeId")Integer caseeId);


	/**
	 * 审核列表及条件筛选
	 * @param userId
	 * @param taskFlowDTO
	 * @return R
	 */
	IPage<AgentMatterVO> auditList(Page page, @Param("userId") Integer userId, @Param("taskFlowDTO")TaskFlowDTO taskFlowDTO);

	/**
	 * 任务详情
	 * @param
	 * @return
	 */
	AgentMatterVO taskDetails(@Param("nodeId") String nodeId);


	TaskNode queryNewTaskNode(@Param("taskNodeKey")String taskNodeKey,@Param("taskNode")TaskNode taskNode);

	TaskNode queryLastTaskNode(@Param("taskNodeKey")String taskNodeKey,@Param("targetId")Integer targetId);

}
