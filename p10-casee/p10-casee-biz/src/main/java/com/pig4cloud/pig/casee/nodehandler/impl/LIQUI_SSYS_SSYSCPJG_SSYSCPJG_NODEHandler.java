package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.CaseeLiquiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.ProjectLiQuiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.FirstTrialRefereeResult;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES.LiQui_SSES_SSESCPJG_SSESCPJG;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSYS.LiQui_SSYS_SSYSCPJG_SSYSCPJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class LIQUI_SSYS_SSYSCPJG_SSYSCPJG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Autowired
	CaseeService caseeService;

	@Autowired
	CaseeLiquiService caseeLiquiService;

	@Autowired
	ExpenseRecordService expenseRecordService;

	@Autowired
	private ExpenseRecordSubjectReService expenseRecordSubjectReService;

	@Autowired
	private	ProjectLiquiService projectLiquiService;

	@Autowired
	private ProjectSubjectReService projectSubjectReService;

	@Autowired
	private PaymentRecordService paymentRecordService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);

		LiQui_SSYS_SSYSCPJG_SSYSCPJG liQui_ssys_ssyscpjg_ssyscpjg = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSYS_SSYSCPJG_SSYSCPJG.class);
		Casee casee=new Casee();
		casee.setCaseeId(taskNode.getCaseeId());
		casee.setJudicialExpenses(liQui_ssys_ssyscpjg_ssyscpjg.getLitigationCosts());
		//修改案件案件受理费
		caseeService.updateById(casee);

		//查询案件与案件详情
		Casee queryCasee = new Casee();
		queryCasee.setCaseeId(taskNode.getCaseeId());
		CaseeLiqui caseeLiqui = caseeLiquiService.getCaseeLiqui(queryCasee);

		//判断不需要审核，处理裁判结果节点加入案件
		if(taskNode.getNeedAudit().equals(Integer.valueOf("0"))) {
			CaseeLiquiDetail caseeLiquiDetail = caseeLiqui.getCaseeLiquiDetail();
			//案件详情为空创建
			if (Objects.isNull(caseeLiquiDetail)) {
				caseeLiquiDetail = new CaseeLiquiDetail();
			}
			FirstTrialRefereeResult firstTrialRefereeResult = JsonUtils.jsonToPojo(taskNode.getFormData(), FirstTrialRefereeResult.class);
			caseeLiquiDetail.setFirstTrialRefereeResult(firstTrialRefereeResult);
			caseeLiqui.setCaseeLiquiDetail(caseeLiquiDetail);
			this.caseeLiquiService.updateById(caseeLiqui);
		}

		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
		ProjectLiQuiDetail projectLiQuiDetail = new ProjectLiQuiDetail();
		BeanCopyUtil.copyBean(projectLiqui.getProjectLiQuiDetail(),projectLiQuiDetail);
		projectLiQuiDetail.setPrincipal(liQui_ssys_ssyscpjg_ssyscpjg.getPrincipal());
		projectLiQuiDetail.setInterest(liQui_ssys_ssyscpjg_ssyscpjg.getInterest());
		projectLiQuiDetail.setPrincipalInterestAmount(liQui_ssys_ssyscpjg_ssyscpjg.getRefereeAmount());
		projectLiQuiDetail.setProjectAmount(liQui_ssys_ssyscpjg_ssyscpjg.getRefereeAmount());

		//查询项目本金费用产生记录并修改本金金额
		ExpenseRecord bjExpenseRecord = expenseRecordService.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId, taskNode.getProjectId()).eq(ExpenseRecord::getCaseeId, taskNode.getCaseeId()).eq(ExpenseRecord::getCostType, 10001).eq(ExpenseRecord::getStatus,0));
		bjExpenseRecord.setCostAmount(liQui_ssys_ssyscpjg_ssyscpjg.getPrincipal());
		expenseRecordService.updateById(bjExpenseRecord);

		//查询项目利息费用产生记录并修改利息金额
		ExpenseRecord lxExpenseRecord = expenseRecordService.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId, taskNode.getProjectId()).eq(ExpenseRecord::getCaseeId, taskNode.getCaseeId()).eq(ExpenseRecord::getCostType, 30001).eq(ExpenseRecord::getStatus,0));
		lxExpenseRecord.setCostAmount(liQui_ssys_ssyscpjg_ssyscpjg.getInterest());
		expenseRecordService.updateById(lxExpenseRecord);

		//查询一审诉讼费并修改诉讼金额
		ExpenseRecord ysExpenseRecord = expenseRecordService.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId, taskNode.getProjectId()).eq(ExpenseRecord::getCaseeId, taskNode.getCaseeId()).eq(ExpenseRecord::getCostType, 10003).eq(ExpenseRecord::getStatus,0));

		if (ysExpenseRecord!=null){
			ysExpenseRecord.setCostAmount(liQui_ssys_ssyscpjg_ssyscpjg.getLitigationCosts());
			expenseRecordService.updateById(ysExpenseRecord);
		}else {
			List<ProjectSubjectRe> projectSubjectReList = projectSubjectReService.list(new LambdaQueryWrapper<ProjectSubjectRe>().eq(ProjectSubjectRe::getProjectId, taskNode.getProjectId()).ne(ProjectSubjectRe::getType,0));

			//添加一审诉讼费用产生明显
			ExpenseRecord expenseRecordPrincipal = new ExpenseRecord();
			expenseRecordPrincipal.setProjectId(taskNode.getProjectId());
			expenseRecordPrincipal.setCaseeId(taskNode.getCaseeId());
			expenseRecordPrincipal.setCaseeNumber(caseeLiqui.getCaseeNumber());
			expenseRecordPrincipal.setCostType(10003);
			expenseRecordPrincipal.setCostIncurredTime(liQui_ssys_ssyscpjg_ssyscpjg.getRefereeMediationTime());
			expenseRecordPrincipal.setCostAmount(liQui_ssys_ssyscpjg_ssyscpjg.getLitigationCosts());
			expenseRecordPrincipal.setStatus(0);
			expenseRecordPrincipal.setCompanyCode(projectLiqui.getCompanyCode());
			expenseRecordPrincipal.setSubjectName(projectLiqui.getSubjectPersons());
			expenseRecordService.save(expenseRecordPrincipal);

			//添加费用记录关联主体信息
			ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
			for (ProjectSubjectRe projectSubjectRe : projectSubjectReList) {
				expenseRecordSubjectRe.setSubjectId(projectSubjectRe.getSubjectId());
				expenseRecordSubjectRe.setExpenseRecordId(expenseRecordPrincipal.getExpenseRecordId());
				expenseRecordSubjectReService.save(expenseRecordSubjectRe);
			}
		}
		projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().add(liQui_ssys_ssyscpjg_ssyscpjg.getLitigationCosts()));
		projectLiqui.setProjectLiQuiDetail(projectLiQuiDetail);
		//修改项目本金、利息、以及本金利息总额
		projectLiquiService.updateById(projectLiqui);
	}

	private void addData(TaskNode taskNode){


	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		LiQui_SSES_SSESCPJG_SSESCPJG liQui_sses_ssescpjg_ssescpjg = JsonUtils.jsonToPojo(node.getFormData(), LiQui_SSES_SSESCPJG_SSESCPJG.class);
		if (liQui_sses_ssescpjg_ssescpjg!=null) {


		}
		addData(taskNode);
	}

}
