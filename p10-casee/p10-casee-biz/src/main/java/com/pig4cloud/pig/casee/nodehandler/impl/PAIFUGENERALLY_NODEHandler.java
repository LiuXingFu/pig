package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.feign.RemoteMessageRecordService;
import com.pig4cloud.pig.casee.dto.AuditTargetDTO;
import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.entity.CaseeHandlingRecords;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.CaseeHandlingRecordsService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PAIFUGENERALLY_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	SecurityUtilsService securityUtilsService;
	@Autowired
	CaseeHandlingRecordsService caseeHandlingRecordsService;
	@Autowired
	RemoteMessageRecordService remoteMessageRecordService;

	/**
	 * 更新程序
	 * @param taskNode
	 */
	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);

		//更新json
		taskNodeService.setTaskDataSubmission(taskNode);
	}

	/**
	 * 补录程序
	 * @param taskNode
	 */
	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);

		//更新json
		taskNodeService.setTaskDataSubmission(taskNode);
	}
//之前拍辅的处理方法 暂时不删 先注释
//	@Override
//	public void handlerTaskSubmit(TaskNode taskNode) {
//
//		// 设置跳过节点与修改第三层父节点信息
//		TaskNode threeLevelParentTaskNode = taskNodeService.setTaskNode(taskNode);
//
////			3.判断当前节点顺序(议价结果----顺序为3)等于第三层父节点子任务最终进度（双方议价----子任务最终进度3）则第二层节点（价格认证等）当前子节点进度=增加进度+1
//		if (threeLevelParentTaskNode.getFinalProcess() == taskNode.getNodeOrder()) {
//			taskNodeService.updateLevelTaskNodeList(taskNode, threeLevelParentTaskNode, taskNode.getAddProcess());
//		} else {
//			taskNodeService.updateOneLevelTaskNodeList(taskNode, threeLevelParentTaskNode);
//		}
////		4.修改第一层父节点信息
////		taskNodeService.updateOneLevelTaskNodeList(taskNode);
//
//	}
//
//	@Override
//	public void handlerTaskAudit(TaskFlowDTO taskFlowDTO) {
//		AuditTargetDTO auditTargetDTO = taskNodeService.getAuditTargetDTO(taskFlowDTO);
//
////		1.判断是否审核成功 判断是否计费
//		if (taskFlowDTO.getStatus() == 403) {
////			计费------》
//			taskNodeService.setTaskNodeByTargetId(taskFlowDTO, auditTargetDTO);
//		} else {
////			不计费----》
////			1.节点key修改标的表单json数据
////			taskNodeService.updateContent(auditTargetDTO);
//		}
//	}
//
//	@Override
//	public void handlerTaskMakeUp(TaskNode taskNode){
//		taskNodeService.setNodeTaskMakeUpProcess(taskNode);
//	};
}
