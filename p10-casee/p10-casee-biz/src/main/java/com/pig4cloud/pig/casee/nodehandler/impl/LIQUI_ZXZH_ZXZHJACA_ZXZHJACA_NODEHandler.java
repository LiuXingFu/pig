package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.dto.AuditTargetDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LIQUI_ZXZH_ZXZHJACA_ZXZHJACA_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		//执恢案件结案/撤案接口，待创建

		//1.将拼接任务key存入dao
		//2.将任务数据存入dao
		//3.将程序id存入dao
		AuditTargetDTO auditTargetDTO = taskNodeService.getAuditTargetDTO(taskNode);
		taskNodeService.updateBusinessData(auditTargetDTO);
	}
}
