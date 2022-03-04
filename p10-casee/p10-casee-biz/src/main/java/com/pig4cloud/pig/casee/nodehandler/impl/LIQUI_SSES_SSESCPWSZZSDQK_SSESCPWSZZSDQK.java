package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES.LiQui_SSES_SSESCPWSZZSDQK_SSESCPWSZZSDQK;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.CaseeLiquiService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.nodehandler.impl
 * @ClassNAME: LIQUI_SSES_SSESCPWSZZSDQK_SSESCPWSZZSDQK
 * @Author: yd
 * @DATE: 2022/3/4
 * @TIME: 11:05
 * @DAY_NAME_SHORT: 周五
 */
@Component
public class LIQUI_SSES_SSESCPWSZZSDQK_SSESCPWSZZSDQK extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Autowired
	CaseeLiquiService caseeLiquiService;


	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {

		taskNodeService.setTaskDataSubmission(taskNode);

		LiQui_SSES_SSESCPWSZZSDQK_SSESCPWSZZSDQK liQui_sses_ssescpwszzsdqk_ssescpwszzsdqk = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSES_SSESCPWSZZSDQK_SSESCPWSZZSDQK.class);

		taskNodeService.updateFinalReceiptTime(taskNode, liQui_sses_ssescpwszzsdqk_ssescpwszzsdqk.getReceiptRecordList());
	}
}
