package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.PaymentRecord;
import com.pig4cloud.pig.casee.entity.PaymentRecordSubjectRe;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.fundingzxprocedure.FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
	private PaymentRecordSubjectReService paymentRecordSubjectReService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);

		//资金划扣
		FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK fundingZX_zjzx_zjzxzjhk_zjzxzjhk = JsonUtils.jsonToPojo(taskNode.getFormData(), FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK.class);

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getAssetsId());

		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());
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
		paymentRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		paymentRecordService.save(paymentRecord);

		List<PaymentRecordSubjectRe> paymentRecordSubjectReList = new ArrayList<>();
		// 遍历财产关联多个债务人
		for (Subject subject:assetsReSubjectDTO.getSubjectList()){
			PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setSubjectId(subject.getSubjectId());
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			paymentRecordSubjectReList.add(paymentRecordSubjectRe);
		}
		//添加抵偿回款信息关联债务人
		paymentRecordSubjectReService.saveBatch(paymentRecordSubjectReList);
	}
}
