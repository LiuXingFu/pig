package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.dto.AuditTargetDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES.LiQui_SSES_SSESCPJG_SSESCPJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LIQUI_SSES_SSESCPJG_SSESCPJG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {

		LiQui_SSES_SSESCPJG_SSESCPJG liQui_SSES_SSESCPJG_SSESCPJG = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSES_SSESCPJG_SSESCPJG.class);

		//更改立案录入的诉讼费

		//1.将拼接任务key存入dao
		//2.将任务数据存入dao
		//3.将程序id存入dao
		AuditTargetDTO auditTargetDTO = taskNodeService.getAuditTargetDTO(taskNode);
		taskNodeService.updateBusinessData(auditTargetDTO);

	}
}
