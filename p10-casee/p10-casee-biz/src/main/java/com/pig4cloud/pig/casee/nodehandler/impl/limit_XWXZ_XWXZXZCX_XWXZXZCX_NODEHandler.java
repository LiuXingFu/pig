package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liquientity.BehaviorLiqui;
import com.pig4cloud.pig.casee.entity.project.limitprocedure.Limit_XWXZ_XWXZXZCX_XWXZXZCX;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.BehaviorLiquiService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class limit_XWXZ_XWXZXZCX_XWXZXZCX_NODEHandler extends TaskNodeHandler {
	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	BehaviorLiquiService behaviorLiquiService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//行为限制限制撤销
		Limit_XWXZ_XWXZXZCX_XWXZXZCX limit_xwxz_xwxzxzcx_xwxzxzcx = JsonUtils.jsonToPojo(taskNode.getFormData(), Limit_XWXZ_XWXZXZCX_XWXZXZCX.class);
		BehaviorLiqui behaviorLiqui=new BehaviorLiqui();
		behaviorLiqui.setBehaviorId(limit_xwxz_xwxzxzcx_xwxzxzcx.getBehaviorId());
		behaviorLiqui.setBehaviorStatus(2);
		//修改行为状态
		behaviorLiquiService.updateById(behaviorLiqui);
	}
}
