package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXJGYJ_CCZXJGYJ;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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
		taskNodeService.setTaskDataSubmission(taskNode);
		//价格依据
		EntityZX_STZX_CCZXJGYJ_CCZXJGYJ entityZX_stzx_cczxjgyj_cczxjgyj = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXJGYJ_CCZXJGYJ.class);

		//添加定价费用需修改项目总金额
		if (entityZX_stzx_cczxjgyj_cczxjgyj.getPricingManner()!=0){
			Target target = targetService.getById(taskNode.getTargetId());

			//查询当前财产关联债务人信息
			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

			//查询案件信息
			Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());

			//添加定价费用明细记录
			ExpenseRecord expenseRecord=new ExpenseRecord();
			expenseRecord.setCostAmount(entityZX_stzx_cczxjgyj_cczxjgyj.getPricingFee());
			expenseRecord.setCostIncurredTime(entityZX_stzx_cczxjgyj_cczxjgyj.getPricingDate());
			expenseRecord.setProjectId(taskNode.getProjectId());
			expenseRecord.setCaseeId(taskNode.getCaseeId());
			expenseRecord.setCaseeNumber(casee.getCaseeNumber());
			expenseRecord.setStatus(0);
			expenseRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
			expenseRecord.setCompanyCode(projectLiqui.getCompanyCode());
			expenseRecord.setCostType(10006);
			expenseRecordService.save(expenseRecord);

			//添加费用产生明细关联财产关联信息
			ExpenseRecordAssetsRe expenseRecordAssetsRe=new ExpenseRecordAssetsRe();
			expenseRecordAssetsRe.setAssetsReId(assetsReSubjectDTO.getAssetsReId());
			expenseRecordAssetsRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordAssetsReService.save(expenseRecordAssetsRe);

			//添加费用产生明细关联主体信息
			List<ExpenseRecordSubjectRe> expenseRecordSubjectRes = new ArrayList<>();
			for (Subject subject:assetsReSubjectDTO.getSubjectList()){
				ExpenseRecordSubjectRe expenseRecordSubjectRe=new ExpenseRecordSubjectRe();
				expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
				expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
				expenseRecordSubjectRes.add(expenseRecordSubjectRe);
			}
			expenseRecordSubjectReService.saveBatch(expenseRecordSubjectRes);

			//修改项目总金额
			projectLiquiService.modifyProjectAmount(projectLiqui.getProjectId());
		}
	}
}
