package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES.LiQui_SSES_SSESCPJG_SSESCPJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.CaseeService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LIQUI_SSES_SSESCPJG_SSESCPJG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Autowired
	CaseeService caseeService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//诉讼阶段二审裁判结果
		LiQui_SSES_SSESCPJG_SSESCPJG liQui_SSES_SSESCPJG_SSESCPJG = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSES_SSESCPJG_SSESCPJG.class);

		//更改立案录入的诉讼费
		Casee casee=new Casee();
		casee.setCaseeId(taskNode.getCaseeId());
		casee.setJudicialExpenses(liQui_SSES_SSESCPJG_SSESCPJG.getLitigationCosts());
		//修改案件司法费
		caseeService.updateById(casee);


	}
}
