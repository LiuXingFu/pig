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
package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pig4cloud.pig.common.mybatis.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.math.BigDecimal;

/**
 * 流程节点表
 *
 * @author pig code generator
 * @date 2021-09-10 15:05:20
 */
@Data
@TableName("p10_task_node")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程节点表")
public class TaskNode extends BaseEntity {

	/**
	 * 节点 ID
	 */
	@TableId
	@ApiModelProperty(value="节点 ID")
	private Integer nodeId;

	/**
	 * 节点KEY
	 */
	@ApiModelProperty(value="节点KEY")
	private String nodeKey;

	/**
	 * 节点名称
	 */
	@ApiModelProperty(value="节点名称")
	private String nodeName;

	/**
	 * 是否可跳过 0-不可跳过 1-跳过
	 */
	@ApiModelProperty(value="是否可跳过 0-不可跳过 1-跳过")
	private Integer canSkip;

	/**
	 * 节点属性 0-节点 100-任务节点
	 */
	@ApiModelProperty(value="节点属性 0-节点 100-任务节点")
	private Integer nodeAttributes;

	/**
	 * 任务流部署id
	 */
	@ApiModelProperty(value="任务流部署id")
	private String actDeploymentId;

	/**
	 * 节点计费
	 */
	@ApiModelProperty(value="节点计费")
	private BigDecimal charge;

	/**
	 * 节点分值
	 */
	@ApiModelProperty(value="节点分值")
	private Integer score;

	/**
	 * 节点顺序
	 */
	@ApiModelProperty(value="节点顺序")
	private Integer order;

	/**
	 * 该节点激活需要的父节点进度
	 */
	@ApiModelProperty(value="该节点激活需要的父节点进度")
	private Integer indexProcess;

	/**
	 * 增加进度
	 */
	@ApiModelProperty(value="增加进度")
	private Integer addProcess;

	/**
	 * 表单ID
	 */
	@ApiModelProperty(value="表单ID")
	private Integer formId;

	/**
	 * 父节点ID
	 */
	@ApiModelProperty(value="父节点ID")
	private Integer parentNodeId;

	/**
	 * 备注
	 */
	@ApiModelProperty(value="备注")
	private String remark;

	/**
	 * 是否需要审核（0：不需要，1：需要）
	 */
	@ApiModelProperty(value="是否需要审核（0：不需要，1：需要）")
	private Integer needAudit;

	/**
	 * 是否需要计费（0：不需要，1：需要）
	 */
	@ApiModelProperty(value="是否需要计费（0：不需要，1：需要）")
	private Integer needCost;

	/**
	 * 流程实例ID 模板里面配置 任务流实例ID
	 */
	@ApiModelProperty(value="流程实例ID 模板里面配置 任务流实例ID")
	private String actReProcdefId;

	/**
	 * 案件id
	 */
	@ApiModelProperty(value="案件id")
	private Integer caseId;

	/**
	 * 标的id
	 */
	@ApiModelProperty(value="标的id")
	private Integer targetId;

	/**
	 * 节点当前进度
	 */
	@ApiModelProperty(value="节点当前进度")
	private Integer process;

	/**
	 * 流程任务ID 开始办理 具体的任务流ID
	 */
	@ApiModelProperty(value="流程任务ID 开始办理 具体的任务流ID")
	private String actTaskId;

	/**
	 * 节点状态 0-未提交 101-待审核 111-驳回 301-跳过 401-提交不审核 403-审核通过
	 */
	@ApiModelProperty(value="节点状态 0-未提交 101-待审核 111-驳回 301-跳过 401-提交不审核 403-审核通过")
	private Integer status;

	/**
	 * 表单填充数据
	 */
	@ApiModelProperty(value="表单填充数据")
	private String formData;

	/**
	 * 0-正常，1-删除
	 */
	@ApiModelProperty(value="0-正常，1-删除")
	private String delFlag;


}
