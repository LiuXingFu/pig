package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.PaymentRecord;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.fundingzxprocedure.FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	PaymentRecordService paymentRecordService;
	@Autowired
	ProjectLiquiService projectLiquiService;
	@Autowired
	CaseeLiquiService caseeLiquiService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);

		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());
		//资金划扣
		FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK fundingZX_zjzx_zjzxzjhk_zjzxzjhk = JsonUtils.jsonToPojo(taskNode.getFormData(), FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK.class);
		PaymentRecord paymentRecord=new PaymentRecord();
		paymentRecord.setPaymentDate(fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getDeductionTime());
		paymentRecord.setPaymentAmount(fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getDeductionAmount());
		paymentRecord.setProjectId(taskNode.getProjectId());
		paymentRecord.setCompanyCode(projectLiqui.getCompanyCode());
		paymentRecord.setCaseeId(taskNode.getCaseeId());
		paymentRecord.setCaseeNumber(casee.getCaseeNumber());
		paymentRecord.setPaymentType(200);
		paymentRecord.setFundsType(20004);
		paymentRecord.setStatus(0);

		paymentRecordService.save(paymentRecord);

	}
}
