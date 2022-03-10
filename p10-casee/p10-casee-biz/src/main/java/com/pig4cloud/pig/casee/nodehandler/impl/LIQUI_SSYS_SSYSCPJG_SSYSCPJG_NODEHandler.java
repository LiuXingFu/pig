package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.CaseeLiquiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.FirstTrialRefereeResult;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS.LiQui_SSYS_SSYSCPJG_SSYSCPJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.CaseeLiquiService;
import com.pig4cloud.pig.casee.service.CaseeService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class LIQUI_SSYS_SSYSCPJG_SSYSCPJG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Autowired
	CaseeService caseeService;

	@Autowired
	CaseeLiquiService caseeLiquiService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);

		LiQui_SSYS_SSYSCPJG_SSYSCPJG liQui_ssys_ssyscpjg_ssyscpjg = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSYS_SSYSCPJG_SSYSCPJG.class);
		Casee casee=new Casee();
		casee.setCaseeId(taskNode.getCaseeId());
		casee.setJudicialExpenses(liQui_ssys_ssyscpjg_ssyscpjg.getLitigationCosts());
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
			FirstTrialRefereeResult firstTrialRefereeResult = JsonUtils.jsonToPojo(taskNode.getFormData(), FirstTrialRefereeResult.class);
			caseeLiquiDetail.setFirstTrialRefereeResult(firstTrialRefereeResult);
			caseeLiqui.setCaseeLiquiDetail(caseeLiquiDetail);
			this.caseeLiquiService.updateById(caseeLiqui);
		}
	}

}
