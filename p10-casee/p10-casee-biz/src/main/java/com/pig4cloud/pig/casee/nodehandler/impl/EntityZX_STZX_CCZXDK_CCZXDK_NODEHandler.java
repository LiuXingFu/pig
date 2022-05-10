package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXDK_CCZXDK;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EntityZX_STZX_CCZXDK_CCZXDK_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	private ExpenseRecordService expenseRecordService;
	@Autowired
	AssetsReLiquiService assetsReLiquiService;
	@Autowired
	private ExpenseRecordSubjectReService expenseRecordSubjectReService;
	@Autowired
	private CaseeLiquiService caseeLiquiService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private PaymentRecordSubjectReService paymentRecordSubjectReService;
	@Autowired
	private PaymentRecordService paymentRecordService;
	@Autowired
	AssetsReService assetsReService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//到款
		EntityZX_STZX_CCZXDK_CCZXDK entityZX_stzx_cczxdk_cczxdk = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXDK_CCZXDK.class);

		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		AssetsRe assetsReDTO = assetsReService.getOne(new LambdaQueryWrapper<AssetsRe>().eq(AssetsRe::getProjectId, taskNode.getProjectId()).eq(AssetsRe::getCaseeId, taskNode.getCaseeId()).eq(AssetsRe::getAssetsId, entityZX_stzx_cczxdk_cczxdk.getAssetsId()));
		//修改当前财产关联表状态
		assetsReDTO.setStatus(500);
		assetsReService.updateById(assetsReDTO);

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), entityZX_stzx_cczxdk_cczxdk.getAssetsId());

		//添加拍辅金额时需添加项目总金额
		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
		projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(entityZX_stzx_cczxdk_cczxdk.getAuxiliaryFee()));
		projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
		//修改项目总金额
		projectLiquiService.updateById(projectLiqui);

		//添加费用明细记录
		ExpenseRecord expenseRecord=new ExpenseRecord();
		expenseRecord.setCostAmount(entityZX_stzx_cczxdk_cczxdk.getAuxiliaryFee());
		expenseRecord.setCostIncurredTime(entityZX_stzx_cczxdk_cczxdk.getFinalPaymentDate());
		expenseRecord.setProjectId(taskNode.getProjectId());
		expenseRecord.setCaseeId(taskNode.getCaseeId());
		expenseRecord.setCaseeNumber(casee.getCaseeNumber());
		expenseRecord.setStatus(0);
		expenseRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		expenseRecord.setCompanyCode(projectLiqui.getCompanyCode());
		expenseRecord.setCostType(10007);
		expenseRecordService.save(expenseRecord);

		//添加到款回款信息
		PaymentRecord paymentRecord=new PaymentRecord();
		paymentRecord.setPaymentType(200);
		paymentRecord.setFundsType(20003);
		paymentRecord.setStatus(0);
		paymentRecord.setPaymentDate(entityZX_stzx_cczxdk_cczxdk.getFinalPaymentDate());
		paymentRecord.setPaymentAmount(entityZX_stzx_cczxdk_cczxdk.getAmountReceived());
		paymentRecord.setCaseeId(taskNode.getCaseeId());
		paymentRecord.setProjectId(taskNode.getProjectId());
		paymentRecord.setCompanyCode(projectLiqui.getCompanyCode());
		paymentRecord.setCaseeNumber(casee.getCaseeNumber());
		paymentRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		paymentRecordService.save(paymentRecord);

		List<ExpenseRecordSubjectRe> expenseRecordSubjectRes = new ArrayList<>();
		List<PaymentRecordSubjectRe> paymentRecordSubjectRes = new ArrayList<>();
		// 遍历财产关联多个债务人
		for (Subject subject:assetsReSubjectDTO.getSubjectList()){
			ExpenseRecordSubjectRe expenseRecordSubjectRe=new ExpenseRecordSubjectRe();
			expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
			expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordSubjectRes.add(expenseRecordSubjectRe);

			PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setSubjectId(subject.getSubjectId());
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			paymentRecordSubjectRes.add(paymentRecordSubjectRe);
		}
		//添加费用产生明细关联主体信息
		expenseRecordSubjectReService.saveBatch(expenseRecordSubjectRes);
		//添加到款信息关联债务人
		paymentRecordSubjectReService.saveBatch(paymentRecordSubjectRes);

	}
}
