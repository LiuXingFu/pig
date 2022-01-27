package com.pig4cloud.pig.casee.utils;

import com.pig4cloud.pig.admin.api.dto.MessageRecordDTO;
import com.pig4cloud.pig.admin.api.feign.RemoteMessageRecordService;
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.admin.api.vo.UserVO;
import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.persistence.entity.VariableInstance;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MessageRecordUtils {

	public static void pushTaskMessageRecord(DelegateExecution delegateExecution,String message){
		RemoteMessageRecordService remoteMessageRecordService= SpringUtils.getObject(RemoteMessageRecordService.class);
		TaskNodeService taskNodeService= SpringUtils.getObject(TaskNodeService.class);
		RemoteUserService remoteUserService = SpringUtils.getObject(RemoteUserService.class);
		String processInstanceBusinessKey = delegateExecution.getProcessInstanceBusinessKey();//获取业务id
		Map<String, VariableInstance> variableInstances = delegateExecution.getParent().getVariableInstances();
		TaskFlowDTO taskFlowDTO = (TaskFlowDTO)variableInstances.get("taskFlowDTO").getCachedValue();//获取委托提交的数据
		TaskNode taskNode = taskNodeService.getByNodeId(processInstanceBusinessKey);//获取当前任务信息
		R<UserVO> userById = remoteUserService.getUserById(taskNode.getUpdateBy(), SecurityConstants.FROM);
		MessageRecordDTO messageRecordDTO=new MessageRecordDTO();
		messageRecordDTO.setCreateBy(taskNode.getUpdateBy());
		messageRecordDTO.setCreateTime(LocalDateTime.now());
		messageRecordDTO.setMessageType(100);
		messageRecordDTO.setMessageTitle(userById.getData().getNickName()+message+taskNode.getNodeName());
		messageRecordDTO.setMessageContent(JsonUtils.objectToJson(taskNode));
		if (taskFlowDTO!=null){
			messageRecordDTO.setReceiverInsId(taskFlowDTO.getCommissionInsId());
			messageRecordDTO.setReceiverOutlesId(taskFlowDTO.getCommissionOutlesId());
			messageRecordDTO.setReceiverUserId(taskFlowDTO.getCommissionUserId());
		}
		List<MessageRecordDTO>messageRecordDTOList=new ArrayList<>();
		messageRecordDTOList.add(messageRecordDTO);
		remoteMessageRecordService.batchSendMessageRecordOutPush(messageRecordDTOList, SecurityConstants.FROM);
	}

}
