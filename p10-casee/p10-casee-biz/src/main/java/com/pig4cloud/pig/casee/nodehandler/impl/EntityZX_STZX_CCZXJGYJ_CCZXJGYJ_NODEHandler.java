package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXJGYJ_CCZXJGYJ;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class EntityZX_STZX_CCZXJGYJ_CCZXJGYJ_NODEHandler extends TaskNodeHandler {

	@Autowired
	private TaskNodeService taskNodeService;
	@Autowired
	private ExpenseRecordService expenseRecordService;
	@Autowired
	private AssetsReLiquiService assetsReLiquiService;
	@Autowired
	private CaseeLiquiService caseeLiquiService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private TargetService targetService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		addJgyjRepaymentFee(taskNode);
	}

	public void addJgyjRepaymentFee(TaskNode taskNode) {
		//价格依据
		EntityZX_STZX_CCZXJGYJ_CCZXJGYJ entityZX_stzx_cczxjgyj_cczxjgyj = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXJGYJ_CCZXJGYJ.class);
		if (entityZX_stzx_cczxjgyj_cczxjgyj.getPricingManner() != 0 && entityZX_stzx_cczxjgyj_cczxjgyj.getPricingFee().compareTo(BigDecimal.ZERO) != 0) {

			Target target = targetService.getById(taskNode.getTargetId());

			//查询当前财产关联债务人信息
			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

			//查询案件信息
			Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());

			//添加定价费用明细记录
			ExpenseRecord expenseRecord = expenseRecordService.addExpenseRecord(entityZX_stzx_cczxjgyj_cczxjgyj.getPricingFee(), entityZX_stzx_cczxjgyj_cczxjgyj.getPricingDate(), projectLiqui, casee, assetsReSubjectDTO, null, 10006);

			entityZX_stzx_cczxjgyj_cczxjgyj.setExpenseRecordId(expenseRecord.getExpenseRecordId());

			String json = JsonUtils.objectToJson(entityZX_stzx_cczxjgyj_cczxjgyj);

			taskNode.setFormData(json);

			//节点信息更新
			this.taskNodeService.updateById(taskNode);

			//修改项目总金额
			projectLiquiService.modifyProjectAmount(projectLiqui.getProjectId());

		}
		taskNodeService.setTaskDataSubmission(taskNode);
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		//补录的节点数据
		EntityZX_STZX_CCZXJGYJ_CCZXJGYJ makeUpEntityZX_stzx_cczxjgyj_cczxjgyj = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXJGYJ_CCZXJGYJ.class);

		//查询补录之前的数据
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		EntityZX_STZX_CCZXJGYJ_CCZXJGYJ entityZX_stzx_cczxjgyj_cczxjgyj = JsonUtils.jsonToPojo(node.getFormData(), EntityZX_STZX_CCZXJGYJ_CCZXJGYJ.class);

		if (entityZX_stzx_cczxjgyj_cczxjgyj != null) {
			//如果补录定价费用发生改变
			if (entityZX_stzx_cczxjgyj_cczxjgyj.getPricingManner() != 0 && makeUpEntityZX_stzx_cczxjgyj_cczxjgyj.getPricingFee().compareTo(entityZX_stzx_cczxjgyj_cczxjgyj.getPricingFee()) != 0) {
				//定价费要是已分配，修改已回款记录作废，修改费用金额，修改项目回款总金额以及项目总金额
				expenseRecordService.updateExpenseRecordProjectAmount(entityZX_stzx_cczxjgyj_cczxjgyj.getExpenseRecordId(),entityZX_stzx_cczxjgyj_cczxjgyj.getPricingFee(),makeUpEntityZX_stzx_cczxjgyj_cczxjgyj.getPricingFee(),taskNode.getProjectId(),100);
			}
			//更新json数据
			taskNodeService.setTaskDataSubmission(taskNode);
		} else {
			addJgyjRepaymentFee(taskNode);
		}
	}
}
