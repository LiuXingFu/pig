package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.PaymentRecordAddDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXZCDC_CCZXZCDC;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
	private CaseeLiquiService caseeLiquiService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private PaymentRecordSubjectReService paymentRecordSubjectReService;
	@Autowired
	private PaymentRecordService paymentRecordService;
	@Autowired
	ExpenseRecordAssetsReService expenseRecordAssetsReService;
	@Autowired
	TargetService targetService;
	@Autowired
	PaymentRecordAssetsReService paymentRecordAssetsReService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//资产抵偿
		EntityZX_STZX_CCZXZCDC_CCZXZCDC entityZX_stzx_cczxzcdc_cczxzcdc = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXZCDC_CCZXZCDC.class);
		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		Target target = targetService.getById(taskNode.getTargetId());

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());

		//查询当前财产程序拍辅费
		ExpenseRecord expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(),taskNode.getProjectId(),10007);
		if (expenseRecord!=null){
			expenseRecord.setCostAmount(expenseRecord.getCostAmount().add(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee()));
			expenseRecord.setStatus(0);
			//修改当前财产程序拍辅费
			expenseRecordService.updateById(expenseRecord);
		}

		//添加抵偿回款信息
		PaymentRecord paymentRecord=new PaymentRecord();
		paymentRecord.setPaymentType(400);
		paymentRecord.setPaymentDate(entityZX_stzx_cczxzcdc_cczxzcdc.getSettlementDate());
		paymentRecord.setPaymentAmount(entityZX_stzx_cczxzcdc_cczxzcdc.getCompensationAmount());
		paymentRecord.setCaseeId(taskNode.getCaseeId());
		paymentRecord.setProjectId(taskNode.getProjectId());
		paymentRecord.setCompanyCode(projectLiqui.getCompanyCode());
		paymentRecord.setCaseeNumber(casee.getCaseeNumber());
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

		//添加抵偿回款信息关联财产
		PaymentRecordAssetsRe paymentRecordAssetsRe=new PaymentRecordAssetsRe();
		paymentRecordAssetsRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
		paymentRecordAssetsRe.setAssetsReId(assetsReSubjectDTO.getAssetsReId());
		paymentRecordAssetsReService.save(paymentRecordAssetsRe);

		//抵偿分配记录
		List<PaymentRecordAddDTO> paymentRecordList = entityZX_stzx_cczxzcdc_cczxzcdc.getPaymentRecordList();
		List<PaymentRecordSubjectRe> paymentRecordSubjectRes = new ArrayList<>();
		for (PaymentRecordAddDTO record : paymentRecordList) {
			record.setPaymentType(400);
			record.setPaymentDate(paymentRecord.getPaymentDate());
			record.setFatherId(paymentRecord.getPaymentRecordId());
			record.setCaseeId(taskNode.getCaseeId());
			record.setCaseeNumber(casee.getCaseeNumber());
			//添加抵偿分配记录
			paymentRecordService.save(record);

			// 遍历财产关联多个债务人
			for (Subject subject:assetsReSubjectDTO.getSubjectList()){
				PaymentRecordSubjectRe paymentRecordSubject=new PaymentRecordSubjectRe();
				paymentRecordSubject.setPaymentRecordId(record.getPaymentRecordId());
				paymentRecordSubject.setSubjectId(subject.getSubjectId());
				//添加抵偿分配记录与主体关联信息
				paymentRecordSubjectRes.add(paymentRecordSubject);
			}

			PaymentRecordAssetsRe paymentRecordAssets=new PaymentRecordAssetsRe();
			paymentRecordAssets.setPaymentRecordId(record.getPaymentRecordId());
			paymentRecordAssets.setAssetsReId(assetsReSubjectDTO.getAssetsReId());
			paymentRecordAssetsReService.save(paymentRecordAssets);

			//如果费用已经还完则修改状态
			ExpenseRecord expenseRecordUpdate = expenseRecordService.getById(record.getExpenseRecordId());
			if (expenseRecordUpdate.getCostAmount()==record.getPaymentAmount().add(record.getPaymentSumAmount())){
				expenseRecordUpdate.setExpenseRecordId(record.getExpenseRecordId());
				expenseRecordUpdate.setStatus(1);
				//修改明细记录状态
				expenseRecordService.updateById(expenseRecordUpdate);
			}
		}
		//添加抵偿分配信息关联债务人
		paymentRecordSubjectReService.saveBatch(paymentRecordSubjectRes);

		//添加拍辅金额时需修改项目总金额
		projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee()));
		projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());

		//添加抵偿时需修改项目回款总金额
		projectLiqui.getProjectLiQuiDetail().setRepaymentAmount(projectLiqui.getProjectLiQuiDetail().getRepaymentAmount().add(entityZX_stzx_cczxzcdc_cczxzcdc.getCompensationAmount()));
		projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
		//修改项目总金额以及回款总金额
		projectLiquiService.updateById(projectLiqui);
	}
}
