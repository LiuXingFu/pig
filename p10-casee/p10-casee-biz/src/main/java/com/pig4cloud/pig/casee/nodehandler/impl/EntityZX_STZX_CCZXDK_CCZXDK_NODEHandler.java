package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
		setLiQuiStccDkDk(taskNode);
	}

	private void setLiQuiStccDkDk(TaskNode taskNode) {
		//到款
		EntityZX_STZX_CCZXDK_CCZXDK entityZX_stzx_cczxdk_cczxdk = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXDK_CCZXDK.class);

		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		Target target = targetService.getById(taskNode.getTargetId());

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());

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
		PaymentRecord paymentRecord = paymentRecordService.addPaymentRecord(entityZX_stzx_cczxdk_cczxdk.getAmountReceived(), entityZX_stzx_cczxdk_cczxdk.getFinalPaymentDate(),0, projectLiqui, casee, assetsReSubjectDTO, null, 200, 20003);

		entityZX_stzx_cczxdk_cczxdk.setPaymentRecordId(paymentRecord.getPaymentRecordId());

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

		// 更新清收项目总金额
		projectLiquiService.modifyProjectAmount(projectLiqui.getProjectId());

		String json = JsonUtils.objectToJson(entityZX_stzx_cczxdk_cczxdk);

		taskNode.setFormData(json);

		//节点信息更新
		this.taskNodeService.updateById(taskNode);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);
	}


	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		EntityZX_STZX_CCZXDK_CCZXDK entityZX_stzx_cczxdk_cczxdk = JsonUtils.jsonToPojo(node.getFormData(), EntityZX_STZX_CCZXDK_CCZXDK.class);
		if (entityZX_stzx_cczxdk_cczxdk!=null) {
			Target target = targetService.getById(taskNode.getTargetId());

			//查询当前财产关联债务人信息
			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(node.getProjectId(), node.getCaseeId(), target.getGoalId());

			//查询当前财产程序拍辅费
			ExpenseRecord expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(), taskNode.getProjectId(), 10007);
			if (expenseRecord!=null){
				expenseRecord.setCostAmount(expenseRecord.getCostAmount().subtract(entityZX_stzx_cczxdk_cczxdk.getAuxiliaryFee()));
				//修改当前财产程序拍辅费
				expenseRecordService.updateById(expenseRecord);
			}

			////删除到款以及关联信息
			paymentRecordService.deletePaymentRecordRe(entityZX_stzx_cczxdk_cczxdk.getPaymentRecordId());
		}
		setLiQuiStccDkDk(taskNode);
	}
}
