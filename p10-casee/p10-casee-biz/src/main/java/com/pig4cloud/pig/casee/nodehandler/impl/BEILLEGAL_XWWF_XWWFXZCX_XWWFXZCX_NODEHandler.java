package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liquientity.BehaviorLiqui;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.BehaviorLiquiService;
import com.pig4cloud.pig.casee.service.TargetService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BEILLEGAL_XWWF_XWWFXZCX_XWWFXZCX_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	BehaviorLiquiService behaviorLiquiService;
	@Autowired
	TargetService targetService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		Target target = targetService.getById(taskNode.getTargetId());
		taskNodeService.setTaskDataSubmission(taskNode);
		BehaviorLiqui behaviorLiqui=new BehaviorLiqui();
		behaviorLiqui.setBehaviorId(target.getGoalId());
		behaviorLiqui.setBehaviorStatus(2);
		//修改行为状态
		behaviorLiquiService.updateById(behaviorLiqui);
	}
}
