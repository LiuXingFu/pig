package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSQT.LiQui_SSQT_SSQTCPJG_SSQTCPJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.CaseeService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LIQUI_SSQT_SSQTCPJG_SSQTCPJG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	CaseeService caseeService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//诉讼阶段其他裁判结果
		LiQui_SSQT_SSQTCPJG_SSQTCPJG liQui_ssqt_ssqtcpjg_ssqtcpjg = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSQT_SSQTCPJG_SSQTCPJG.class);
		//更改立案录入的诉讼费
		Casee casee=new Casee();
		casee.setCaseeId(taskNode.getCaseeId());
		casee.setJudicialExpenses(liQui_ssqt_ssqtcpjg_ssqtcpjg.getLitigationCosts());
		//修改案件司法费
		caseeService.updateById(casee);
	}
}
