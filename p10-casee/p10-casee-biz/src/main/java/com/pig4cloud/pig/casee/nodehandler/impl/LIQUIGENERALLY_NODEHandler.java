package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.dto.AuditTargetDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LIQUIGENERALLY_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	/**
	 * 更新程序
	 * @param taskNode
	 */
	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {

	}

}
