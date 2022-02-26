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

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.entity.TaskNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 流程节点表
 *
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
@Data
@TableName("p10_task_node")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程节点表")
public class TaskFlowDTO extends TaskNode {
	//分页对象
	private Page page;
	/**
	 * 流程定义key
	 */
	@ApiModelProperty(value="流程定义key")
	private String taskKey;

	/**
	 * 任务办理人id
	 */
	@ApiModelProperty(value="任务办理人id")
	private Integer transactionId;

	/**
	 * 工作流任务名称
	 */
	@ApiModelProperty(value="工作流任务名称")
	private String taskName;

	/**
	 * 审核人id(可能存在多个)
	 */
	@ApiModelProperty(value="审核人id")
	private List<Integer> auditorIdList =new ArrayList<>();

	/**
	 * 当前任务审核人id
	 */
	@ApiModelProperty(value="当前任务审核人id")
	private Integer auditorId;

	/**
	 * //查询该用户任务id
	 */
	@ApiModelProperty(value="查询该用户任务id")
	private Integer	taskAssigneeId;

	/**
	 * 委托人id
	 */
	@ApiModelProperty(value="委托人id")
	private Integer commissionUserId;

	/**
	 * 委托机构
	 */
	@ApiModelProperty(value="委托机构")
	private Integer commissionInsId;

	/**
	 * 委托网点
	 */
	@ApiModelProperty(value="委托网点")
	private Integer commissionOutlesId;

	/**
	 * 任务流设置是否委托办理（0-否 1-是）
	 */
	@ApiModelProperty(value="任务流设置是否委托办理")
	private Integer needCommission;

	/**
	 * 任务流设置是否委托审核（0-否 1-是）
	 */
	@ApiModelProperty(value="任务流设置是否委托审核")
	private Integer needCommissionedReview;

	//绩效列表
	/**
	 * 绩效状态（0-不计费 1-计费）
	 */
	@ApiModelProperty(value="绩效状态（0-不计费 1-计费）")
	private Integer perfStatus;

	/**
	 * 绩效金额
	 */
	@ApiModelProperty(value="绩效金额")
	private BigDecimal performanceAmount;

	//代办事项 查询参数
	/**
	 * 业务委托 0-不委托 1-委托
	 */
	@ApiModelProperty(value="业务委托 0-不委托 1-委托")
	private Integer canEntrust;

	/**
	 * 拍辅案号
	 */
	@ApiModelProperty(value="拍辅案号")
	private String companyCode;

	/**
	 * 标的名称
	 */
	@ApiModelProperty(value="标的名称")
	private String targetName;


	private String beginDate;//开始时间
	private String endDate;//结束时间

	/**
	 * //描述
	 */
	@ApiModelProperty(value="描述")
	private String	described;

	/**
	 * 消息代办类型（100-待处理 101-已处理 102-我的代办 200-待办理 201-已办理 202-我的委托）
	 */
	@ApiModelProperty(value="消息代办类型（100-待处理 101-已处理 102-我的任务 200-待办理 201-已办理 202-我的委托）")
	private Integer type;
}
