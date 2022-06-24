package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.CaseeLiquiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.FirstTrialRefereeResult;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
public class LIQUI_SSYS_SSYSCPJG_SSYSCPJG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Autowired
	CaseeService caseeService;

	@Autowired
	CaseeLiquiService caseeLiquiService;

	@Autowired
	ExpenseRecordService expenseRecordService;


	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		addData(taskNode);
	}

	private void addData(TaskNode taskNode){
		taskNodeService.setTaskDataSubmission(taskNode);

		//查询案件与案件详情
		Casee queryCasee = new Casee();
		queryCasee.setCaseeId(taskNode.getCaseeId());
		CaseeLiqui caseeLiqui = caseeLiquiService.getCaseeLiqui(queryCasee);

		//判断不需要审核，处理裁判结果节点加入案件
		if(taskNode.getNeedAudit().equals(Integer.valueOf("0"))) {
			CaseeLiquiDetail caseeLiquiDetail = caseeLiqui.getCaseeLiquiDetail();
			//案件详情为空创建
			if (Objects.isNull(caseeLiquiDetail)) {
				caseeLiquiDetail = new CaseeLiquiDetail();
			}
			FirstTrialRefereeResult firstTrialRefereeResult = JsonUtils.jsonToPojo(taskNode.getFormData(), FirstTrialRefereeResult.class);
			caseeLiquiDetail.setFirstTrialRefereeResult(firstTrialRefereeResult);
			caseeLiqui.setCaseeLiquiDetail(caseeLiquiDetail);
			this.caseeLiquiService.updateById(caseeLiqui);
		}
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		addData(taskNode);
	}
}
