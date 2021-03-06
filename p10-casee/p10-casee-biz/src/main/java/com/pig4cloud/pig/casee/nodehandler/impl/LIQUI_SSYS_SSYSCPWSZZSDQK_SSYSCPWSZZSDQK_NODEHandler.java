package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS.LiQui_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @PACKAGE_NAME: com.pig4cloud.pig.casee.nodehandler.impl
 * @ClassNAME: LIQUI_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK_NODEHandler
 * @Author: yd
 * @DATE: 2022/3/3
 * @TIME: 15:20
 * @DAY_NAME_SHORT: 周四
 */
@Component
public class LIQUI_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		addData(taskNode);
	}

	private void addData(TaskNode taskNode){
		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

		//如果任务为不审核更新最终送达时间
		if (taskNode.getNeedAudit().equals(Integer.valueOf("0"))) {
			LiQui_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK liQui_ssys_ssyscpwszzsdqk_ssyscpwszzsdqk = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSYS_SSYSCPWSZZSDQK_SSYSCPWSZZSDQK.class);
			taskNodeService.updateFinalReceiptTimeOrEffectiveDate(taskNode, liQui_ssys_ssyscpwszzsdqk_ssyscpwszzsdqk.getReceiptRecordList(), liQui_ssys_ssyscpwszzsdqk_ssyscpwszzsdqk.getEffectiveDate());
		}
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		addData(taskNode);
	}
}
