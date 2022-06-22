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
	ExpenseRecordAssetsReService expenseRecordAssetsReService;
	@Autowired
	TargetService targetService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		addJgyjRepaymentFee(taskNode);
	}

	public void addJgyjRepaymentFee(TaskNode taskNode){
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
		//查询补录之前的数据
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		EntityZX_STZX_CCZXJGYJ_CCZXJGYJ entityZX_stzx_cczxjgyj_cczxjgyj = JsonUtils.jsonToPojo(node.getFormData(), EntityZX_STZX_CCZXJGYJ_CCZXJGYJ.class);

		if (entityZX_stzx_cczxjgyj_cczxjgyj!=null) {

			if (entityZX_stzx_cczxjgyj_cczxjgyj.getPricingManner()!=0){
				//删除费用明细记录
				expenseRecordService.removeById(entityZX_stzx_cczxjgyj_cczxjgyj.getExpenseRecordId());

				//删除费用明细记录财产关联信息
				expenseRecordAssetsReService.remove(new LambdaQueryWrapper<ExpenseRecordAssetsRe>()
						.eq(ExpenseRecordAssetsRe::getExpenseRecordId, entityZX_stzx_cczxjgyj_cczxjgyj.getExpenseRecordId()));

				//删除费用产生明细关联主体信息
				expenseRecordSubjectReService.remove(new LambdaQueryWrapper<ExpenseRecordSubjectRe>()
						.eq(ExpenseRecordSubjectRe::getExpenseRecordId, entityZX_stzx_cczxjgyj_cczxjgyj.getExpenseRecordId()));

				ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());

				projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().subtract(entityZX_stzx_cczxjgyj_cczxjgyj.getPricingFee()));
				projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
				//修改清收项目总金额
				projectLiquiService.updateById(projectLiqui);
			}
		}
		addJgyjRepaymentFee(taskNode);
	}
}
