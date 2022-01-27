package com.pig4cloud.pig.casee.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class WorkFlowDefinition implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 流程定义key
	 */
	@ApiModelProperty(value="流程定义key")
	private String taskKey;

	/**
	 * 流程实例id
	 */
	@ApiModelProperty(value="流程实例id")
	private String actReProcdefId;

	/**
	 * 任务id
	 */
	@ApiModelProperty(value="任务id")
	private String taskNodeId;
	/**
	 * 任务办理人id
	 */
	@ApiModelProperty(value="任务办理人id")
	private Integer transactionId;
	/**
	 * 审核人id
	 */
	@ApiModelProperty(value="审核人id")
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
	private Integer commissionId;


	/**
	 * 是否委托办理（0-否 1-是）
	 */
	@ApiModelProperty(value="是否委托办理")
	private Integer needCommission;

	/**
	 * 是否委托审核（0-否 1-是）
	 */
	@ApiModelProperty(value="是否委托审核")
	private Integer needCommissionedReview;


}