package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liquientity.BehaviorLiqui;
import com.pig4cloud.pig.casee.entity.project.beillegalprocedure.BeIllegal_XWWF_XWWFXZCX_XWWFXZCX;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.BehaviorLiquiService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BEILLEGAL_XWWF_XWWFXZCX_XWWFXZCX_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	BehaviorLiquiService behaviorLiquiService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//行为违法限制撤销
		BeIllegal_XWWF_XWWFXZCX_XWWFXZCX beIllegal_xwwf_xwwfxzcx_xwwfxzcx = JsonUtils.jsonToPojo(taskNode.getFormData(), BeIllegal_XWWF_XWWFXZCX_XWWFXZCX.class);
		BehaviorLiqui behaviorLiqui=new BehaviorLiqui();
		behaviorLiqui.setBehaviorId(beIllegal_xwwf_xwwfxzcx_xwwfxzcx.getBehaviorId());
		behaviorLiqui.setBehaviorStatus(2);
		//修改行为状态
		behaviorLiquiService.updateById(behaviorLiqui);
	}
}
