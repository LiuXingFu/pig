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
package com.pig4cloud.pig.casee.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.entity.TaskNode;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CaseeOrTargetTaskFlowDTO extends TaskNode {
	//分页对象
	private Page page;
	/**
	 * 流程定义key
	 */
	@ApiModelProperty(value="流程定义key")
	private String taskKey;

	/**
	 * 任务流实例ID
	 */
	@ApiModelProperty(value="任务流实例ID")
	private String actReProcdefId;

	/**
	 * 案件或标的审核申请人id
	 */
	@ApiModelProperty(value="案件审核申请人id")
	private Integer caseeOrTargetAuditProposer;


	/**
	 * 工作流任务名称
	 */
	@ApiModelProperty(value="工作流任务名称")
	private String taskName;
	/**
	 * 案件或标的审核人id(可能存在多个)
	 */
	@ApiModelProperty(value="案件或标的审核人id(可能存在多个)")
	private List<Integer> caseeOrTargetAuditorList;

	/**
	 * //查询该用户任务id
	 */
	@ApiModelProperty(value="查询该用户任务id")
	private Integer	taskAssigneeId;

	/**
	 * 任务类型（0-完成委托审核任务 1-完成委托办理任务 2-委托办理任务 3-委托审核任务 4-完成审核任务 5-完成办理任务）
	 */
	@ApiModelProperty(value="任务类型（0-完成委托审核任务 1-完成委托办理任务 2-委托办理任务 3-委托审核任务 4-完成审核任务 5-完成办理任务）")
	private Integer type;

	/**
	 * 受托机构
	 */
	@ApiModelProperty(value="受托机构")
	private Integer trusteeInsId;

	/**
	 * 受托网点
	 */
	@ApiModelProperty(value="受托网点")
	private Integer trusteeOutlesId;

	/**
	 * //描述
	 */
	@ApiModelProperty(value="描述")
	private String	described;

}
