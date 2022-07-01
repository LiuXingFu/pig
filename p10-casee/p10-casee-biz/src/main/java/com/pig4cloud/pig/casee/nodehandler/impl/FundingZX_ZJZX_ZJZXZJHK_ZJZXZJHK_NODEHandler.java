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
		//补录的节点数据
		FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK makeUpFundingZX_zjzx_zjzxzjhk_zjzxzjhk = JsonUtils.jsonToPojo(taskNode.getFormData(), FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK.class);

		//查询补录之前的数据
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK fundingZX_zjzx_zjzxzjhk_zjzxzjhk = JsonUtils.jsonToPojo(node.getFormData(), FundingZX_ZJZX_ZJZXZJHK_ZJZXZJHK.class);
		if (fundingZX_zjzx_zjzxzjhk_zjzxzjhk!=null){
			//如果补录划扣金额发生改变
			if (makeUpFundingZX_zjzx_zjzxzjhk_zjzxzjhk.getDeductionAmount().compareTo(fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getDeductionAmount())!=0) {
				PaymentRecord paymentRecord = paymentRecordService.getById(fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getPaymentRecordId());
				if (paymentRecord.getStatus()==1){//如果当前资金划扣已分配
					//根据回款记录id修改法院到款记录信息、领款记录信息
					paymentRecordService.updateCourtPaymentRecordRe(fundingZX_zjzx_zjzxzjhk_zjzxzjhk.getPaymentRecordId());

					ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
					//修改项目总金额以及回款总金额
					projectLiquiService.subtractRepaymentAmount(projectLiqui,paymentRecord.getPaymentAmount());
				}
				//修改划扣金额
				paymentRecord.setPaymentAmount(makeUpFundingZX_zjzx_zjzxzjhk_zjzxzjhk.getDeductionAmount());
				paymentRecordService.updateById(paymentRecord);
			}
			//更新json数据
			taskNodeService.setTaskDataSubmission(taskNode);
		}else {
			addLiQiuZjhk(taskNode);
		}
	}
}
