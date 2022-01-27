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
package com.pig4cloud.pig.casee.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
@Data
@TableName("p10_task_node")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "流程节点表")
public class TaskNode extends BaseEntity {

    /**
     * 节点 ID
     */
    @TableId(value="node_id",type = IdType.INPUT)
    @ApiModelProperty(value="节点 ID")
    private String nodeId;

	/**
	 * 节点key
	 */
	@ApiModelProperty(value="节点key")
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
     * 节点属性 100:一级节点 200:二级节点 300:三级节点 400:任务节点
     */
    @ApiModelProperty(value="节点属性 100:一级节点 200:二级节点 300:三级节点 400:任务节点")
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
    private Integer nodeOrder;

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
    private String parentNodeId;

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
	 * 子任务最终进度
	 */
	@ApiModelProperty(value="子任务最终进度")
	private Integer finalProcess;

	/**
	 * 当前子节点进度
	 */
	@ApiModelProperty(value="节点当前进度")
	private Integer process;

	/**
	 * 机构id
	 */
	@ApiModelProperty(value="机构id")
	private Integer insId;

	/**
	 * 网点id
	 */
	@ApiModelProperty(value="网点id")
	private Integer outlesId;

	/**
	 * 项目id
	 */
	@ApiModelProperty(value="项目id")
	private Integer projectId;

    /**
     * 案件id
     */
    @ApiModelProperty(value="案件id")
    private Integer caseeId;

    /**
     * 程序id
     */
    @ApiModelProperty(value="程序id")
    private Integer targetId;

	/**
	 * 节点排序
	 */
	@ApiModelProperty(value="节点排序")
	private Integer sort;

	/**
	 * 机构类型 1100-拍辅机构 1200-清收机构
	 */
	@ApiModelProperty(value="机构类型 1100-拍辅机构 1200-清收机构 ")
	private Integer insType;

    /**
     * 流程任务ID 开始办理 具体的任务流ID
     */
    @ApiModelProperty(value="流程任务ID 开始办理 具体的任务流ID")
    private String actTaskId;

    /**
     * 节点状态 节点状态 0-未提交 101-待审核 111-驳回 301-跳过 401-提交不审核 403-审核通过 500-可跳过 600-已委托
     */
    @ApiModelProperty(value="节点状态 节点状态 0-未提交 101-待审核 111-驳回 301-跳过 401-提交不审核 403-审核通过 500-可跳过 600-已委托")
    private Integer status;

	/**
	 * 委托状态 0-委托中 1-拒绝
	 */
	@ApiModelProperty(value="委托状态 0-委托中 1-拒绝")
	private Integer trusteeStatus;

	/**
	 * 委托类型 0-办理任务 1-审核任务
	 */
	@ApiModelProperty(value="委托类型 0-办理任务 1-审核任务")
	private Integer trusteeType;

	/**
	 * 节点类型(0-任务办理节点 100-清收案件开启 101-清收案件结案 102-清收案件中止 103-清收案件暂缓 104-清收案件还款详情 110-清收标的毁损 111-清收标的灭失
	 * 			120-清收标的物毁损 121-清收标的物灭失)
	 */
	@ApiModelProperty(value="0-任务办理节点 100-清收案件开启 101-清收案件结案 102-清收案件中止 103-清收案件暂缓 104-清收案件还款详情 110-清收标的毁损 111-清收标的灭失\n" +
							"120-清收标的物毁损 121-清收标的物灭失")
	private Integer nodeType;

	/**
	 * 节点状态 0-新增 1-补录
	 */
	@ApiModelProperty(value="0-新增 1-补录")
	private Integer submissionStatus;

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
