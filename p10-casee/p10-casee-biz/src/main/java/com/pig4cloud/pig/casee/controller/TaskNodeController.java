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

package com.pig4cloud.pig.casee.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.TaskMessageDTO;
import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.dto.TaskJudgmentDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.casee.service.TaskRecordService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程节点表
 *
 * @author xiaojun
 * @date 2021-09-07 17:01:38
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tasknode" )
@Api(value = "tasknode", tags = "流程节点表管理")
public class TaskNodeController {

    private final TaskNodeService taskNodeService;
	private final TaskRecordService taskRecordService;
    /**
     * 分页查询
     * @param page 分页对象
     * @param taskNode 流程节点表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getTaskNodePage(Page page, TaskNode taskNode) {
        return R.ok(taskNodeService.page(page, Wrappers.query(taskNode)));
    }

	/**
	 * 根据当前登录用户查询办理任务信息
	 * @return
	 */
	@ApiOperation(value = "根据当前登录用户查询办理任务信息", notes = "根据当前登录用户查询办理任务信息")
	@GetMapping("/queryTaskNodeAll" )
	public R queryTaskNodeAll() {
		return R.ok(taskNodeService.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getCreateBy, SecurityUtils.getUser().getId()).eq(TaskNode::getDelFlag, 0)));
	}

    /**
     * 通过id查询流程节点表
     * @param nodeId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{nodeId}" )
    public R getById(@PathVariable("nodeId" ) String nodeId) {
        return R.ok(taskNodeService.getById(nodeId));
    }

	/**
	 * 拍卖撤案显示判断
	 * @param taskNode
	 * @return R
	 */
	@ApiOperation(value = "查询当前拍卖公告环节信息", notes = "查询当前拍卖公告环节信息")
	@GetMapping("/revoke" )
	public R revoke(TaskNode taskNode) {
		return R.ok(taskNodeService.revoke(taskNode));
	}

	/**
	 * 查询当前拍卖公告节点
	 * @param taskNode
	 * @return R
	 */
	@ApiOperation(value = "查询当前拍卖公告节点", notes = "查询当前拍卖公告节点")
	@GetMapping("/queryAuctionAnnouncement" )
	public R queryAuctionAnnouncement(TaskNode taskNode) {
		return R.ok(taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, taskNode.getProjectId()).eq(TaskNode::getCaseeId, taskNode.getCaseeId()).eq(TaskNode::getTargetId, taskNode.getTargetId()).eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXPMGG_CCZXPMGG").orderByDesc(TaskNode::getCreateTime).last("limit 1")));
	}

    /**
     * 新增流程节点表
     * @param taskNode 流程节点表
     * @return R
     */
    @ApiOperation(value = "新增流程节点表", notes = "新增流程节点表")
    @SysLog("新增流程节点表" )
    @PostMapping
    public R save(@RequestBody TaskNode taskNode) {
        return R.ok(taskNodeService.save(taskNode));
    }

    /**
     * 修改流程节点表
     * @param taskNode 流程节点表
     * @return R
     */
    @ApiOperation(value = "修改流程节点表", notes = "修改流程节点表")
    @SysLog("修改流程节点表" )
    @PutMapping
    public R updateById(@RequestBody TaskNode taskNode) {
        return R.ok(taskNodeService.updateById(taskNode));
    }

    /**
     * 通过id删除流程节点表
     * @param nodeId id
     * @return R
     */
    @ApiOperation(value = "通过id删除流程节点表", notes = "通过id删除流程节点表")
    @SysLog("通过id删除流程节点表" )
    @DeleteMapping("/{nodeId}" )
    public R removeById(@PathVariable String nodeId) {
        return R.ok(taskNodeService.removeById(nodeId));
    }

	/**
	 * 根据案件id,程序性质查询模板数据
	 * @param caseeId 案件id
	 * @param id 财产或程序id
	 * @return R
	 */
	@ApiOperation(value = "根据案件id,程序性质查询模板数据", notes = "根据案件id,程序性质查询模板数据")
	@GetMapping("/queryNodeTemplateByCaseeId")
	public R queryNodeTemplateByCaseeId(Integer caseeId,Integer procedureNature,@RequestParam(value = "id",required = false) Integer id) {
		return R.ok(taskNodeService.queryNodeTemplateByCaseeId(caseeId,procedureNature,id));
	}

	/**
	 * 根据条件判断节点状态
	 *
	 * @return
	 * 1 补录
	 * 2 节点前有不可跳过节点 无法填写。请确保从【公告前辅助】开始的必填步骤（包括委托任务）部完成。
	 * 3 待审核
	 * 4 委托中
	 * 5 查看委托审核页面
	 * 6 驳回
	 */
	@ApiOperation(value = "根据标的id与节点key判断节点状态", notes = "根据标的id与节点key判断节点状态")
	@GetMapping("/judgmentTaskStatus")
	public R judgmentTaskStatus(String taskNodeId, Integer canContinueExecution){
		return R.ok(taskNodeService.judgmentTaskStatus(taskNodeId, canContinueExecution));
	}

	/**
	 * 提交任务数据
	 * @param taskFlowDTO 提交任务数据
	 * @return R
	 */
	@ApiOperation(value = "提交任务数据", notes = "提交任务数据")
	@SysLog("提交任务数据" )
	@PostMapping("/saveNodeFormData")
	public R saveNode(@RequestBody TaskFlowDTO taskFlowDTO) {
		return R.ok(taskNodeService.saveNodeFormData(taskFlowDTO));
	}

	/**
	 * 委托办理任务
	 * @param taskFlowDTO 委托办理任务
	 * @return R
	 */
	@ApiOperation(value = "委托办理任务", notes = "委托办理任务")
	@SysLog("委托办理任务" )
	@PostMapping("/commissionTransactionTask")
	public R commissionTaskNode(@RequestBody TaskFlowDTO taskFlowDTO) {
		return R.ok(taskNodeService.commissionTransactionTask(taskFlowDTO));
	}

	/**
	 * 委托审核任务
	 * @param taskFlowDTO 委托审核任务
	 * @return R
	 */
	@ApiOperation(value = "委托审核任务", notes = "委托审核任务")
	@SysLog("委托审核任务" )
	@PostMapping("/commissionAuditTask")
	public R commissionAuditTask(@RequestBody TaskFlowDTO taskFlowDTO) {
		return R.ok(taskNodeService.commissionAuditTask(taskFlowDTO));
	}

	/**
	 * 审核流程节点任务信息
	 * @param taskFlowDTO
	 * @return
	 */
	@ApiOperation(value = "审核流程节点任务信息", notes = "审核流程节点任务信息")
	@SysLog("审核流程节点任务信息" )
	@PostMapping("/auditNode")
	public R auditNode(@RequestBody TaskFlowDTO taskFlowDTO){
		return R.ok(taskNodeService.auditNode(taskFlowDTO));
	}

	/**
	 * 补录流程节点任务信息
	 * @param taskFlowDTO
	 * @return
	 */
	@ApiOperation(value = "补录流程节点任务信息", notes = "补录流程节点任务信息")
	@SysLog("补录流程节点任务信息" )
	@PostMapping("/makeUpNode")
	public R makeUpNode(@RequestBody TaskFlowDTO taskFlowDTO){
		return R.ok(taskNodeService.makeUpNode(taskFlowDTO));
	}

	/**
	 * 消息代办 我的代办/委托列表
	 * @param taskFlowDTO
	 * @return
	 */
	@ApiOperation(value = "消息代办 我的代办/委托列表", notes = "消息代办 我的代办/委托列表")
	@SysLog("消息代办 我的代办/委托列表" )
	@PostMapping("/auditList")
	public R auditList(@RequestBody TaskFlowDTO taskFlowDTO){
		if(taskFlowDTO.getType()==100||taskFlowDTO.getType()==200){
			return R.ok(taskNodeService.auditList(taskFlowDTO.getPage(),taskFlowDTO));
		}else {
			return R.ok(taskRecordService.taskRecordList(taskFlowDTO.getPage(),taskFlowDTO));
		}

	}

	/**
	 * 通过节点id查询该任务审核人是否是当前用户
	 * @param nodeId
	 * @return
	 */
	@ApiOperation(value = "通过节点id查询该任务审核人是否是当前用户", notes = "通过节点id查询该任务审核人是否是当前用户")
	@SysLog("通过节点id查询该任务审核人是否是当前用户" )
	@GetMapping("/queryNodeIdIsAuditor/{nodeId}")
	public R queryNodeIdIsAuditor(@PathVariable String nodeId){
		return R.ok(taskNodeService.queryNodeIdIsAuditor(nodeId));
	}

	/**
	 * 通过任务id查询任务详情信息
	 * @param nodeId id
	 * @return R
	 */
	@ApiOperation(value = "通过任务id查询任务详情信息", notes = "通过任务id查询任务详情信息")
	@GetMapping("taskDetails/{nodeId}")
	public R taskDetails(@PathVariable("nodeId" ) String nodeId) {
		return R.ok(taskNodeService.taskDetails(nodeId));
	}

	/**
	 * 任务节点跳过
	 * @param taskNode
	 * @return
	 */
	@ApiOperation(value = "任务节点跳过", notes = "任务节点跳过")
	@SysLog("任务节点跳过" )
	@PostMapping("/jumpOver")
	public R jumpOver(@RequestBody TaskNode taskNode) {
		return R.ok(taskNodeService.jumpOver(taskNode));
	}

	/**
	 * 根据条件查询任务节点集合
	 * @param taskNode
	 * @return
	 */
	@ApiOperation(value = "根据条件查询任务节点集合", notes = "根据条件查询任务节点集合")
	@SysLog("根据条件查询任务节点集合" )
	@GetMapping("/queryTaskListByCondition")
	public R queryTaskListByCondition(@RequestBody TaskNode taskNode){
		return R.ok(this.taskNodeService.queryTaskListByCondition(taskNode));
	}

	/**
	 * 根据案件id与节点key查询任务对象
	 * @param caseeId 案件id
	 * @param nodeKey 节点key
	 * @return
	 */
	@ApiOperation(value = "根据案件id与节点key查询任务对象", notes = "根据案件id与节点key查询任务对象")
	@SysLog("根据案件id与节点key查询任务对象" )
	@GetMapping("/queryNodeTaskByCaseeIdAndNodeKey")
	public R queryNodeTaskByCaseeIdAndNodeKey(Integer caseeId, String nodeKey){
		return R.ok(this.taskNodeService.queryNodeTaskByCaseeIdAndNodeKey(caseeId, nodeKey));
	}

	/**
	 * 根据项目id与节点key查询任务对象
	 * @param projectId 项目id
	 * @param nodeKey 节点key
	 * @return
	 */
	@ApiOperation(value = "根据项目id与节点key查询任务对象", notes = "根据项目id与节点key查询任务对象")
	@SysLog("根据项目id与节点key查询任务对象" )
	@GetMapping("/queryNodeTaskByProjectIdAndNodeKey")
	public R queryNodeTaskByProjectIdAndNodeKey(Integer projectId, String nodeKey){
		return R.ok(this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId,projectId).eq(TaskNode::getNodeKey,nodeKey)));
	}

	@ApiOperation(value = "添加流程定义的部署", notes = "添加流程定义的部署")
	@SysLog("添加流程定义的部署" )
	@GetMapping("/addDeployment")
	public R addDeployment(String bpmn, String name){
		return R.ok(this.taskNodeService.addDeployment(bpmn, name));
	}



}
