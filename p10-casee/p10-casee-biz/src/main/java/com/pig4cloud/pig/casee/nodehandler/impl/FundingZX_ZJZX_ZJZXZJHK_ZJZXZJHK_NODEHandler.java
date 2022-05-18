package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.fundingzxprocedure.FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


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
	@Autowired
	AssetsReLiquiService assetsReLiquiService;
	@Autowired
	TargetService targetService;
	@Autowired
	PaymentRecordAssetsReService paymentRecordAssetsReService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);

		//资金划扣
		FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK fundingZX_zjzx_zjzxzjhk_zjzxzjhk = JsonUtils.jsonToPojo(taskNode.getFormData(), FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK.class);

		Target target = targetService.getById(taskNode.getTargetId());

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());
		//添加资金划扣信息
		paymentRecordService.addPaymentRecord(fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getDeductionAmount(), fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getDeductionTime(), projectLiqui, casee, assetsReSubjectDTO, null, 200, 20004);
	}
}
