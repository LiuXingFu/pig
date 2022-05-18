package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.CustomerSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXDK_CCZXDK;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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
	private CaseeLiquiService caseeLiquiService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private PaymentRecordSubjectReService paymentRecordSubjectReService;
	@Autowired
	private PaymentRecordService paymentRecordService;
	@Autowired
	AssetsReService assetsReService;
	@Autowired
	ExpenseRecordAssetsReService expenseRecordAssetsReService;
	@Autowired
	TargetService targetService;
	@Autowired
	PaymentRecordAssetsReService paymentRecordAssetsReService;
	@Autowired
	CustomerService customerService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//到款
		EntityZX_STZX_CCZXDK_CCZXDK entityZX_stzx_cczxdk_cczxdk = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXDK_CCZXDK.class);

		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		Target target = targetService.getById(taskNode.getTargetId());

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

		//添加拍辅金额时需添加项目总金额
		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
		projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(entityZX_stzx_cczxdk_cczxdk.getAuxiliaryFee()));
		projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
		//修改项目总金额
		projectLiquiService.updateById(projectLiqui);
		if (entityZX_stzx_cczxdk_cczxdk.getAuxiliaryFee().compareTo(BigDecimal.ZERO)!=0){//判断拍辅费是否大于0
			//查询当前财产程序拍辅费
			ExpenseRecord expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(), taskNode.getProjectId(), 10007);
			if (expenseRecord!=null){//如果当前拍辅费没有还完则修改拍辅金额
				expenseRecord.setCostAmount(expenseRecord.getCostAmount().add(entityZX_stzx_cczxdk_cczxdk.getAuxiliaryFee()));
				//修改当前财产程序拍辅费
				expenseRecordService.updateById(expenseRecord);
			}else {//如果没有拍辅费或者拍辅费已经还完则添加
				//添加费用明细记录以及其它关联信息
				expenseRecordService.addExpenseRecord(entityZX_stzx_cczxdk_cczxdk.getAuxiliaryFee(),entityZX_stzx_cczxdk_cczxdk.getFinalPaymentDate(),projectLiqui,casee,assetsReSubjectDTO,null,10007);
			}
		}

		//添加到款信息以及其它关联信息
		paymentRecordService.addPaymentRecord(entityZX_stzx_cczxdk_cczxdk.getAmountReceived(), entityZX_stzx_cczxdk_cczxdk.getFinalPaymentDate(), projectLiqui, casee, assetsReSubjectDTO, null, 200, 20003);

		//添加到款客户信息
		CustomerSubjectDTO customerSubjectDTO=new CustomerSubjectDTO();
		customerSubjectDTO.setName(entityZX_stzx_cczxdk_cczxdk.getFinalPayer());
		customerSubjectDTO.setPhone(entityZX_stzx_cczxdk_cczxdk.getPhone());
		customerSubjectDTO.setCustomerType(30000);
		customerSubjectDTO.setProjectId(taskNode.getProjectId());
		customerSubjectDTO.setCaseeId(taskNode.getCaseeId());
		if (entityZX_stzx_cczxdk_cczxdk.getIdentityCard()!=null){
			customerSubjectDTO.setUnifiedIdentity(entityZX_stzx_cczxdk_cczxdk.getIdentityCard());
		}
		customerService.saveCustomer(customerSubjectDTO);
	}
}
