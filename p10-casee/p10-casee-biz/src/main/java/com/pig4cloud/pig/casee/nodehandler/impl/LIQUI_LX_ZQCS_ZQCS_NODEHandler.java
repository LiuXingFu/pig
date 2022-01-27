package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.dto.AuditTargetDTO;
import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liqui.LiQui_LX_ZQCS_ZQCS;
import com.pig4cloud.pig.casee.entity.liqui.LiQui_SQ_SQCS_SQCS;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LIQUI_LX_ZQCS_ZQCS_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {

		updateTaskNode_SQ_SQCS_SQCS(taskNode);

		// 设置跳过节点与修改第三层父节点信息
		TaskNode threeLevelParentTaskNode = taskNodeService.setTaskNode(taskNode);

//		3.判断当前节点顺序(议价结果----顺序为3)等于第三层父节点子任务最终进度（双方议价----子任务最终进度3）则第二层节点（价格认证等）当前子节点进度=增加进度+1
		if (threeLevelParentTaskNode.getFinalProcess() == taskNode.getNodeOrder()) {
			taskNodeService.updateLevelTaskNodeList(taskNode, threeLevelParentTaskNode, taskNode.getAddProcess());
		} else {
			taskNodeService.updateOneLevelTaskNodeList(taskNode, threeLevelParentTaskNode);
		}

	}

	/**
	 * 修改诉前催收表单信息
	 * @param taskNode
	 */
	public void updateTaskNode_SQ_SQCS_SQCS(TaskNode taskNode) {
		// 将执前催收json转成实体类
		LiQui_LX_ZQCS_ZQCS liQui_LX_ZQCS_ZQCS = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_LX_ZQCS_ZQCS.class);

		TaskNode SQCS = getTaskNode_SQCS(taskNode.getTargetId());

		LiQui_SQ_SQCS_SQCS liQui_SQ_SQCS_SQCS = null;

		if(Objects.nonNull(SQCS.getFormData())){
			// 将诉前催收json转成实体类
			liQui_SQ_SQCS_SQCS = JsonUtils.jsonToPojo(SQCS.getFormData(), LiQui_SQ_SQCS_SQCS.class);
		} else {
			liQui_SQ_SQCS_SQCS = new LiQui_SQ_SQCS_SQCS();
		}

		// 将执前催收的催收列表复制到诉前催收的催收列表
		liQui_SQ_SQCS_SQCS.setCollectionRecordsList(liQui_LX_ZQCS_ZQCS.getCollectionRecordsList());

		// 将实体诉前催收实体类转成json
		String json = JsonUtils.objectToJson(liQui_SQ_SQCS_SQCS);

		// 将转成json的数据存入任务对象中
		SQCS.setFormData(json);

		this.taskNodeService.updateById(SQCS);
	}

	/**
	 * 根据标的id与节点key查询诉前催收的对象信息
	 * @param targetId
	 * @return
	 */
	public TaskNode getTaskNode_SQCS(Integer targetId) {
		//根据标的id与节点key查询诉前催收的对象信息
		return this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getTargetId, targetId).eq(TaskNode::getNodeKey, "liQui_SQ_SQCS_SQCS"));
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		updateTaskNode_SQ_SQCS_SQCS(taskNode);
		taskNodeService.setNodeTaskMakeUpProcess(taskNode);
	}

	@Override
	public void handlerTaskAudit(TaskFlowDTO taskFlowDTO) {
		AuditTargetDTO auditTargetDTO = taskNodeService.getAuditTargetDTO(taskFlowDTO);

//		1.判断是否审核成功 判断是否计费
		if (taskFlowDTO.getStatus() == 403) {
//			计费------》
			taskNodeService.setTaskNodeByTargetId(taskFlowDTO, auditTargetDTO);

			// 根据标的id与节点key查询诉前催收的对象信息
			TaskNode taskNode_SQCS = this.getTaskNode_SQCS(taskFlowDTO.getTargetId());

			// 诉前催收标的提交对象
			AuditTargetDTO auditTargetDTO_SQCS = taskNodeService.getAuditTargetDTO(taskNode_SQCS);

			//3.节点key修改标的表单json数据
			this.taskNodeService.updateBusinessData(auditTargetDTO_SQCS);

		}


	}
}
