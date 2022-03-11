package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSQT.LiQui_SSQT_SSQTCPJGSX_SSQTCPJGSX;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.CaseeLiquiService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LiQui_SSQT_SSQTCPJGSX_SSQTCPJGSX_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	CaseeLiquiService caseeLiquiService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//诉讼其它案件 裁判结果生效
		LiQui_SSQT_SSQTCPJGSX_SSQTCPJGSX liQui_ssqt_ssqtcpjgsx_ssqtcpjgsx = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSQT_SSQTCPJGSX_SSQTCPJGSX.class);
		CaseeLiqui caseeLiqui=new CaseeLiqui();
		caseeLiqui.setCaseeId(taskNode.getCaseeId());
		caseeLiqui.setStatus(3);
		caseeLiqui.setCloseTime(liQui_ssqt_ssqtcpjgsx_ssqtcpjgsx.getEffectiveDate());
		caseeLiquiService.updateById(caseeLiqui);
	}
}
