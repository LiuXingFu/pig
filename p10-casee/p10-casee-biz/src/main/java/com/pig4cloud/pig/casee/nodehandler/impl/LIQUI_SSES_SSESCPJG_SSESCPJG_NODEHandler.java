package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.CaseeLiquiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.SecondTrialRefereeResult;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES.LiQui_SSES_SSESCPJG_SSESCPJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.CaseeLiquiService;
import com.pig4cloud.pig.casee.service.CaseeService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LIQUI_SSES_SSESCPJG_SSESCPJG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Autowired
	CaseeService caseeService;

	@Autowired
	CaseeLiquiService caseeLiquiService;

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

		//判断不需要审核，处理裁判结果节点加入案件
		if(taskNode.getNeedAudit().equals(Integer.valueOf("0"))) {
			//查询案件与案件详情
			Casee queryCasee = new Casee();
			queryCasee.setCaseeId(taskNode.getCaseeId());
				CaseeLiqui caseeLiqui = caseeLiquiService.getCaseeLiqui(queryCasee);
				CaseeLiquiDetail caseeLiquiDetail = caseeLiqui.getCaseeLiquiDetail();
				//案件详情为空创建
				if (Objects.isNull(caseeLiquiDetail)) {
					caseeLiquiDetail = new CaseeLiquiDetail();
				}
			SecondTrialRefereeResult secondTrialRefereeResult = JsonUtils.jsonToPojo(taskNode.getFormData(), SecondTrialRefereeResult.class);
			caseeLiquiDetail.setSecondTrialRefereeResult(secondTrialRefereeResult);
			caseeLiqui.setCaseeLiquiDetail(caseeLiquiDetail);
			this.caseeLiquiService.updateById(caseeLiqui);
		}
	}
}
