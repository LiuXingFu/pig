package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.PaymentRecordAddDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXZCDC_CCZXZCDC;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class EntityZX_STZX_CCZXZCDC_CCZXZCDC_NODEHandler extends TaskNodeHandler {

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

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//资产抵偿
		EntityZX_STZX_CCZXZCDC_CCZXZCDC entityZX_stzx_cczxzcdc_cczxzcdc = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXZCDC_CCZXZCDC.class);
		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		//查询当前财产关联债务人信息
		Subject subject = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), entityZX_stzx_cczxzcdc_cczxzcdc.getAssetsId());

		//添加拍辅金额时需添加项目总金额
		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
		projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee()));
		projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
		//修改项目总金额
		projectLiquiService.updateById(projectLiqui);

		//添加费用明细记录
		ExpenseRecord expenseRecord=new ExpenseRecord();
		expenseRecord.setCostAmount(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee());
		expenseRecord.setCostIncurredTime(entityZX_stzx_cczxzcdc_cczxzcdc.getSettlementDate());
		expenseRecord.setProjectId(taskNode.getProjectId());
		expenseRecord.setCaseeId(taskNode.getCaseeId());
		expenseRecord.setCaseeNumber(casee.getCaseeNumber());
		expenseRecord.setStatus(0);
		expenseRecord.setSubjectName(subject.getName());
		expenseRecord.setCompanyCode(projectLiqui.getCompanyCode());
		expenseRecord.setCostType(10007);
		expenseRecordService.save(expenseRecord);

		//添加费用产生明细关联主体信息
		ExpenseRecordSubjectRe expenseRecordSubjectRe=new ExpenseRecordSubjectRe();
		expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
		expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
		expenseRecordSubjectReService.save(expenseRecordSubjectRe);

		//添加抵偿回款信息
		PaymentRecord paymentRecord=new PaymentRecord();
		paymentRecord.setPaymentType(400);
		paymentRecord.setPaymentDate(entityZX_stzx_cczxzcdc_cczxzcdc.getSettlementDate());
		paymentRecord.setPaymentAmount(entityZX_stzx_cczxzcdc_cczxzcdc.getCompensationAmount());
		paymentRecord.setCaseeId(taskNode.getCaseeId());
		paymentRecord.setProjectId(taskNode.getProjectId());
		paymentRecord.setCompanyCode(projectLiqui.getCompanyCode());
		paymentRecord.setCaseeNumber(casee.getCaseeNumber());
		paymentRecord.setSubjectName(subject.getName());
		paymentRecordService.save(paymentRecord);

		//添加抵偿回款信息关联债务人
		PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
		paymentRecordSubjectRe.setSubjectId(subject.getSubjectId());
		paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
		paymentRecordSubjectReService.save(paymentRecordSubjectRe);

		List<PaymentRecordAddDTO> paymentRecordList = entityZX_stzx_cczxzcdc_cczxzcdc.getPaymentRecordList();
		for (PaymentRecordAddDTO record : paymentRecordList) {
			record.setPaymentType(400);
			record.setPaymentDate(paymentRecord.getPaymentDate());
			record.setFatherId(paymentRecord.getPaymentRecordId());
			record.setCaseeId(taskNode.getCaseeId());
			record.setCaseeNumber(casee.getCaseeNumber());
			//添加抵偿分配记录
			paymentRecordService.save(record);
			//添加抵偿分配记录与主体关联信息
			PaymentRecordSubjectRe paymentRecordSubject=new PaymentRecordSubjectRe();
			paymentRecordSubject.setPaymentRecordId(record.getPaymentRecordId());
			paymentRecordSubject.setSubjectId(subject.getSubjectId());
			this.paymentRecordSubjectReService.save(paymentRecordSubject);

			ExpenseRecord expenseRecordUpdate = expenseRecordService.getById(record.getExpenseRecordId());
			if (expenseRecordUpdate.getCostAmount()==record.getPaymentAmount().add(record.getPaymentSumAmount())){
				expenseRecordUpdate.setExpenseRecordId(record.getExpenseRecordId());
				expenseRecordUpdate.setStatus(1);
				//修改明细记录状态
				expenseRecordService.updateById(expenseRecordUpdate);
			}
		}

		//添加抵偿时需修改项目回款总金额
		projectLiqui.getProjectLiQuiDetail().setRepaymentAmount(projectLiqui.getProjectLiQuiDetail().getRepaymentAmount().add(entityZX_stzx_cczxzcdc_cczxzcdc.getCompensationAmount()));
		projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
		//修改项目总金额
		projectLiquiService.updateById(projectLiqui);
	}
}
