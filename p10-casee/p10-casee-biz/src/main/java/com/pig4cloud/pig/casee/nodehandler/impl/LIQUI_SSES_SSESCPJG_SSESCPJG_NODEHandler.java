package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.CaseeLiquiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.ProjectLiQuiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.SecondTrialRefereeResult;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSES.LiQui_SSES_SSESCPJG_SSESCPJG;
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
public class LIQUI_SSES_SSESCPJG_SSESCPJG_NODEHandler extends TaskNodeHandler {

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

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//诉讼阶段二审裁判结果
		LiQui_SSES_SSESCPJG_SSESCPJG liQui_SSES_SSESCPJG_SSESCPJG = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SSES_SSESCPJG_SSESCPJG.class);

		//更改立案录入的诉讼费
		Casee casee=new Casee();
		casee.setCaseeId(taskNode.getCaseeId());
		casee.setJudicialExpenses(liQui_SSES_SSESCPJG_SSESCPJG.getLitigationCosts());
		//修改案件受理费
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
			SecondTrialRefereeResult secondTrialRefereeResult = JsonUtils.jsonToPojo(taskNode.getFormData(), SecondTrialRefereeResult.class);
			caseeLiquiDetail.setSecondTrialRefereeResult(secondTrialRefereeResult);
			caseeLiqui.setCaseeLiquiDetail(caseeLiquiDetail);
			this.caseeLiquiService.updateById(caseeLiqui);
		}

		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
		ProjectLiQuiDetail projectLiQuiDetail = new ProjectLiQuiDetail();
		BeanCopyUtil.copyBean(projectLiqui.getProjectLiQuiDetail(),projectLiQuiDetail);
		projectLiQuiDetail.setPrincipal(liQui_SSES_SSESCPJG_SSESCPJG.getPrincipal());
		projectLiQuiDetail.setInterest(liQui_SSES_SSESCPJG_SSESCPJG.getInterest());
		projectLiQuiDetail.setPrincipalInterestAmount(liQui_SSES_SSESCPJG_SSESCPJG.getRefereeAmount());
		projectLiqui.setProjectLiQuiDetail(projectLiQuiDetail);
		//查询二审诉讼费并修改诉讼金额
		ExpenseRecord esExpenseRecord = expenseRecordService.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId, taskNode.getProjectId()).eq(ExpenseRecord::getCaseeId, taskNode.getCaseeId()).eq(ExpenseRecord::getCostType, 10004));
		esExpenseRecord.setCostAmount(liQui_SSES_SSESCPJG_SSESCPJG.getLitigationCosts());

		//修改项目总金额
		projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().subtract(esExpenseRecord.getCostAmount()));
		projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().add(liQui_SSES_SSESCPJG_SSESCPJG.getLitigationCosts()));

		//修改项目本金、利息、以及本金利息总额
		projectLiquiService.updateById(projectLiqui);
		expenseRecordService.updateById(esExpenseRecord);

		List<ProjectSubjectRe> projectSubjectReList = projectSubjectReService.list(new LambdaQueryWrapper<ProjectSubjectRe>().eq(ProjectSubjectRe::getProjectId, taskNode.getProjectId()).ne(ProjectSubjectRe::getType,0));

		List<ExpenseRecord> expenseRecordList = expenseRecordService.list(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId, taskNode.getProjectId()).eq(ExpenseRecord::getStatus, 0).in(ExpenseRecord::getCostType, 10001, 30001));
		for (ExpenseRecord expenseRecord : expenseRecordList) {
			expenseRecord.setStatus(2);
		}
		//修改项目本金、利息费用为作废
		expenseRecordService.updateBatchById(expenseRecordList);

		//添加项目费用产生记录 本金
		ExpenseRecord expenseRecordPrincipal = new ExpenseRecord();
		expenseRecordPrincipal.setProjectId(taskNode.getProjectId());
		expenseRecordPrincipal.setCaseeId(taskNode.getCaseeId());
		expenseRecordPrincipal.setCaseeNumber(caseeLiqui.getCaseeNumber());
		expenseRecordPrincipal.setCostType(10001);
		expenseRecordPrincipal.setCostIncurredTime(liQui_SSES_SSESCPJG_SSESCPJG.getRefereeMediationTime());
		expenseRecordPrincipal.setCostAmount(liQui_SSES_SSESCPJG_SSESCPJG.getPrincipal());
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

		//添加项目费用产生记录 利息
		ExpenseRecord expenseRecordInterest = new ExpenseRecord();
		expenseRecordInterest.setProjectId(taskNode.getProjectId());
		expenseRecordInterest.setCaseeId(taskNode.getCaseeId());
		expenseRecordInterest.setCaseeNumber(caseeLiqui.getCaseeNumber());
		expenseRecordInterest.setCostType(30001);
		expenseRecordInterest.setCostIncurredTime(liQui_SSES_SSESCPJG_SSESCPJG.getRefereeMediationTime());
		expenseRecordInterest.setCostAmount(liQui_SSES_SSESCPJG_SSESCPJG.getInterest());
		expenseRecordInterest.setStatus(0);
		expenseRecordInterest.setCompanyCode(projectLiqui.getCompanyCode());
		expenseRecordInterest.setSubjectName(projectLiqui.getSubjectPersons());
		expenseRecordService.save(expenseRecordInterest);

		//添加费用记录关联主体信息
		ExpenseRecordSubjectRe expenseRecordSubjectInterestRe = new ExpenseRecordSubjectRe();
		for (ProjectSubjectRe projectSubjectRe : projectSubjectReList) {
			expenseRecordSubjectInterestRe.setSubjectId(projectSubjectRe.getSubjectId());
			expenseRecordSubjectInterestRe.setExpenseRecordId(expenseRecordInterest.getExpenseRecordId());
			expenseRecordSubjectReService.save(expenseRecordSubjectInterestRe);
		}
		//修改项目总金额
		projectLiquiService.modifyProjectAmount(taskNode.getProjectId());
	}
}
