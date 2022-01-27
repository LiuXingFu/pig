package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifu.PaiFu_PMFZ_PMJG_PMJG;
import com.pig4cloud.pig.casee.nodehandler.NodeTaskHandlerRegister;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PAIFU_PMFZ_PMJGYP_PMJG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {

		taskNodeService.PMJG_HandlerTaskSubmit(PaiFu_PMFZ_PMJG_PMJG.class, taskNode);

	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		taskNodeService.PMJG_HandlerTaskMakeUp(PaiFu_PMFZ_PMJG_PMJG.class, taskNode);
	}

}
