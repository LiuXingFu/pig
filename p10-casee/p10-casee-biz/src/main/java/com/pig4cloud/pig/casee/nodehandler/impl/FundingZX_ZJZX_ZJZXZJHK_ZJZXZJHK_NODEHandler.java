package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
	TargetService targetService;
	@Autowired
	PaymentRecordAssetsReService paymentRecordAssetsReService;
	@Autowired
	PaymentRecordSubjectReService paymentRecordSubjectReService;
	@Autowired
	ExpenseRecordService expenseRecordService;
	@Autowired
	PaymentSourceReService paymentSourceReService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		addLiQiuZjhk(taskNode);
	}

	private void addLiQiuZjhk(TaskNode taskNode){

		//资金划扣
		FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK fundingZX_zjzx_zjzxzjhk_zjzxzjhk = JsonUtils.jsonToPojo(taskNode.getFormData(), FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK.class);

		Target target = targetService.getById(taskNode.getTargetId());

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());
		//添加资金划扣信息
		PaymentRecord paymentRecord = paymentRecordService.addPaymentRecord(fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getDeductionAmount(), fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getDeductionTime(), 0, projectLiqui, casee, assetsReSubjectDTO, null, 200, 20004);

		fundingZX_zjzx_zjzxzjhk_zjzxzjhk.setPaymentRecordId(paymentRecord.getPaymentRecordId());

		String json = JsonUtils.objectToJson(fundingZX_zjzx_zjzxzjhk_zjzxzjhk);

		taskNode.setFormData(json);

		//节点信息更新
		this.taskNodeService.updateById(taskNode);

		taskNodeService.setTaskDataSubmission(taskNode);
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {

		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK fundingZX_zjzx_zjzxzjhk_zjzxzjhk = JsonUtils.jsonToPojo(node.getFormData(), FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK.class);
		if (fundingZX_zjzx_zjzxzjhk_zjzxzjhk!=null){
			PaymentRecord paymentRecord = paymentRecordService.getById(fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getPaymentRecordId());

			//删除资金划扣回款信息以及关联信息
			paymentRecordService.deletePaymentRecordRe(paymentRecord.getPaymentRecordId());

			if (paymentRecord.getStatus()==1){//如果当前资金划扣已分配
				//查询资金划扣回款来源信息
				PaymentSourceRe paymentSourceRe = paymentSourceReService.getOne(new LambdaQueryWrapper<PaymentSourceRe>().eq(PaymentSourceRe::getSourceId, paymentRecord.getPaymentRecordId()));

				//删除资金划扣领款信息以及关联信息
				paymentRecordService.deletePaymentRecordRe(paymentSourceRe.getPaymentRecordId());

				//根据划扣回款来源信息回款id删除回款信息
				List<PaymentRecord> paymentRecordList = paymentRecordService.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getFatherId, paymentSourceRe.getPaymentRecordId()));
				for (PaymentRecord record : paymentRecordList) {

					//删除资金划扣领款分配信息以及关联信息
					paymentRecordService.deletePaymentRecordRe(record.getPaymentRecordId());

					//修改费用明细记录状态
					ExpenseRecord expenseRecord = expenseRecordService.getById(record.getExpenseRecordId());
					if (expenseRecord.getStatus()!=2){
						expenseRecord.setStatus(0);
						expenseRecordService.updateById(expenseRecord);
					}
				}
				ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());

				//添加修改项目回款总金额
				projectLiqui.getProjectLiQuiDetail().setRepaymentAmount(projectLiqui.getProjectLiQuiDetail().getRepaymentAmount().subtract(fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getDeductionAmount()));
				projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
				//修改项目回款总金额
				projectLiquiService.updateById(projectLiqui);
			}
		}

		addLiQiuZjhk(taskNode);
	}
}
