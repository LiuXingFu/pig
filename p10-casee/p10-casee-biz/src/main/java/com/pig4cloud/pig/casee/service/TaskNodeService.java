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
import com.pig4cloud.pig.admin.api.dto.TaskNodeTemplateDTO;
import com.pig4cloud.pig.admin.api.entity.TaskNodeTemplate;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.entity.TaskNode;

import com.pig4cloud.pig.casee.entity.TaskReminder;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.ShareEntity.ReceiptRecord;
import com.pig4cloud.pig.casee.vo.AgentMatterVO;
import com.pig4cloud.pig.casee.vo.TaskNodeVO;
import net.sf.json.JSONObject;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.util.List;


/**
 * 流程节点表
 *
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
public interface TaskNodeService extends IService<TaskNode> {


	/**
	 * 添加流程定义的部署
	 *
	 * @bpmn 部署流程图路径
	 * @name 部署流程图名称
	 * @return 部署对象
	 */
	Deployment addDeployment(String bpmn, String name);


	/**
	 * 启动一个流程实例,绑定业务主键id(节点任务id),动态设置办理人信息：前提是已经完成了流程定义的部署工作
	 *
	 * objectDTO 通用DTO对象
	 * valueType 转换成 DTO Class对象
	 * taskFlowName 任务流对象名称
	 * @return 流程实例对象
	 */
	ProcessInstance beginFlow(Object objectDTO,String taskFlowName);

	/**
	 * 启动任务,设置排它网关相关信息
	 *
	 * @workFlowDefinition 流程对象
	 * @return 任务实例对象
	 */
	Task executionTask(Object objectDTO,String taskName,String taskFlowName);


	/**
	 * 查询当前任务办理人的任务列表
	 *
	 * @workFlowDefinition 流程对象
	 * @return 任务实例对象集合
	 */
	List<Task> queryTaskList(TaskFlowDTO taskFlowDTO);


	/**
	 * 删除已经部署成功的流程定义。
	 *
	 * @actDeploymentId 流程部署id
	 *
	 */
	void  delDeployment(String actDeploymentId);


	/**
	 * 查询历史数据
	 *
	 * @workFlowDefinition 流程定义id
	 *
	 */
	List<HistoricActivityInstance> queryHistoryData(String actReProcdefId);

	/**
	 * 查询模板内容生成节点信息以及案件绩效信息
	 *
	 * @TaskNodeTemplate taskNodeTemplate模板实体
	 *
	 */
	TaskNodeTemplate queryNodeTemplateAddTaskNode(TaskNodeTemplateDTO taskNodeTemplateDTO, JSONObject jsonObject);

	/**
	 * 根据案件id查询模板数据
	 * @param caseeId 案件id
	 * @return R
	 */
	List<TaskNodeVO> queryNodeTemplateByCaseeId(Integer caseeId,Integer procedureNature,Integer id);

	/**
	 * 根据标的id查询当前主任务进度
	 * @param targetId 标的id
	 * @return
	 */
	Integer queryTaskProgress(String targetId);

	/**
	 * 根据条件判断节点状态
	 * @param taskNodeId
	 * @return
	 */
	TaskReminder judgmentTaskStatus(String taskNodeId, Integer canContinueExecution);

	/**
	 * 任务提交接口
	 * @param taskFlowDTO
	 * @return
	 */
	String saveNodeFormData(TaskFlowDTO taskFlowDTO);


	/**
	 * 委托办理任务
	 * @param taskFlowDTO
	 * @return
	 */
	TaskNode commissionTransactionTask(TaskFlowDTO taskFlowDTO);

	/**
	 * 委托审核任务
	 * @param taskFlowDTO
	 * @return
	 */
	TaskNode commissionAuditTask(TaskFlowDTO taskFlowDTO);

	/**
	 * 拒绝委托任务时修改任务信息
	 * @param
	 * @return
	 */
	TaskFlowDTO refuseEntrustUpdateTask(TaskFlowDTO taskFlowDTO);

	/**
	 * 根据当前节点信息与层级查询层级父节点信息
	 * @param taskNode
	 * @param level
	 * @return
	 */
	TaskNode queryParentTaskNode(TaskNode taskNode, Integer level);

	/**
	 * 修改第二层节点集合
	 * @param taskNode
	 * @param threeLevelParentTaskNode
	 * @param addProcess
	 */
	void updateLevelTaskNodeList(TaskNode taskNode, TaskNode threeLevelParentTaskNode, Integer addProcess);

	/**
	 * 修改第一层节点信息
	 * @param taskNode
	 */
	void updateOneLevelTaskNodeList(TaskNode taskNode, TaskNode threeLevelParentTaskNode);

	/**
	 * 设置跳过节点与修改第三层父节点信息
	 * @param taskNode
	 * @return
	 */
	TaskNode setTaskNode(TaskNode taskNode);

	/**
	 * 根据任务id查询任务信息
	 * @param nodeId
	 * @return
	 */
	TaskNode getByNodeId(String nodeId);

	/**
	 * 审核流程节点任务信息
	 * @param taskFlowDTO
	 * @return
	 */
	boolean auditNode(TaskFlowDTO taskFlowDTO);

	/**
	 * 审核案件或标的任务信息(暂缓、和解)
	 * @param caseeOrTargetTaskFlowDTO
	 * @return
	 */
	CaseeOrTargetTaskFlowDTO auditCaseeOrTargetTask(CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO);

	/**
	 * 拍卖撤案显示判断
	 */
	boolean revoke(TaskNode taskNode);

	/**
	 * 消息代办 我的代办/委托列表
	 * @param taskFlowDTO
	 * @return
	 */
	IPage<AgentMatterVO> auditList(Page page, TaskFlowDTO taskFlowDTO);

	Boolean queryNodeIdIsAuditor(String nodeId);
	/**
	 * 任务详情
	 * @param
	 * @return
	 */
	AgentMatterVO taskDetails(String nodeId);

	void setOneThreeParentNode(TaskNode threeLevelParentTaskNode, TaskNode taskNode);

	void updateBusinessData(AuditTargetDTO auditTargetDTO);

	/**
	 * 补录流程节点任务信息
	 * @param taskFlowDTO
	 * @return
	 */
	String makeUpNode(TaskFlowDTO taskFlowDTO);

	/**
	 * 补录任务、委托任务或提交任务提交（完成任务流任务）
	 * @param taskFlowDTO
	 * @return
	 */
	TaskFlowDTO makeUpEntrustOrSubmit(TaskFlowDTO taskFlowDTO);

	/**
	 * 根据任务信息获取json访问key
	 * @param taskNode
	 * @return
	 */
	String getKey(TaskNode taskNode);

	/**
	 * 根据标的id查询任务集合，然后将标的json更新
	 * @param targetId
	 */
	void setTaskNodeListByTargetId(Integer targetId, List<String> keys);

	/**
	 * 根据任务信息更新标的json
	 * @param taskFlowDTO
	 * @param auditTargetDTO
	 */
	void setTaskNodeByTargetId(TaskFlowDTO taskFlowDTO, AuditTargetDTO auditTargetDTO);

	/**
	 * 设置议价成功的任务集合
	 * @param nodeList
	 * @return
	 */
	List<TaskNode> setTaskNodes(List<TaskNode> nodeList);

	/**
	 * 议价相关补录处理
	 * @param clazz
	 * @param taskNode
	 * @param keys
	 * @param <T>
	 */
	<T> void YJ_HandlerTaskMakeUp(Class<T> clazz, TaskNode taskNode, List<String> keys);

	/**
	 * 查询任务节点变量值
	 * @param clazz
	 * @param taskNode
	 * @param <T>
	 * @return
	 */
	<T> Integer getTaskNodeVariable(Class<T> clazz, TaskNode taskNode);

	/**
	 * 议价相关提交处理
	 * @param clazz
	 * @param taskNode
	 * @param <T>
	 */
	<T> void YJ_HandlerTaskSubmit(Class<T> clazz, TaskNode taskNode);

	/**
	 * 议价相关审核处理
	 * @param clazz
	 * @param taskFlowDTO
	 * @param keys
	 * @param <T>
	 */
	<T> void YJ_HandlerTaskAudit(Class<T> clazz, TaskFlowDTO taskFlowDTO, List<String> keys);

	/**
	 * 拍卖相关提交处理
	 * @param clazz
	 * @param taskNode
	 * @param <T>
	 */
	<T> void PMJG_HandlerTaskSubmit(Class<T> clazz, TaskNode taskNode);

	/**
	 * 拍卖相关补录处理
	 * @param clazz
	 * @param taskNode
	 * @param <T>
	 */
	<T> void PMJG_HandlerTaskMakeUp(Class<T> clazz, TaskNode taskNode);

	/**
	 * 查询AuditTargetDTO对象
	 * @param taskNode
	 * @return
	 */
	AuditTargetDTO getAuditTargetDTO(TaskNode taskNode);

	/**
	 * 任务节点跳过
	 * @param taskNode
	 * @return
	 */
	boolean jumpOver(TaskNode taskNode);

	/**
	 * 根据条件查询任务节点集合
	 * @param taskNode
	 * @return
	 */
	List<TaskNode> queryTaskListByCondition(TaskNode taskNode);

	/**
	 * 根据案件id与节点key查询任务对象
	 * @param caseeId 标的id
	 * @param nodeKey 节点key
	 * @return
	 */
	TaskNode queryNodeTaskByCaseeIdAndNodeKey(Integer caseeId, String nodeKey);

	/**
	 * 修改标的json中的节点最终进度及当前子节点进度
	 * @param process 		子节点进度
	 * @param finalProcess  节点最终进度
	 * @param targetId 		标的id
	 * @param nodeKey 		节点key
	 * @return
	 */
	void updateTargetProcessJson(Integer process,Integer finalProcess,String nodeKey,Integer targetId);

	void setNodeTaskMakeUpProcess(TaskNode taskNode);

	/**
	 * 任务数据提交 保存程序、财产和行为
	 * @param taskNode
	 */
	void setTaskDataSubmission(TaskNode taskNode);

	/**
	 * 更新最终送达时间
	 *
	 * @param taskNode
	 * @param receiptRecordList
	 */
	void updateFinalReceiptTime(TaskNode taskNode, List<ReceiptRecord> receiptRecordList);
}
