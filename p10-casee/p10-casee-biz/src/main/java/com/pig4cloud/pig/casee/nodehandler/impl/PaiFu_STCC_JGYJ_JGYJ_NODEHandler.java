package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_JGYJ_JGYJ;
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
public class PaiFu_STCC_JGYJ_JGYJ_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	private CaseeLiquiService caseeLiquiService;
	@Autowired
	AssetsReLiquiService assetsReLiquiService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private ProjectPaifuService projectPaifuService;
	@Autowired
	private ExpenseRecordService expenseRecordService;
	@Autowired
	private ExpenseRecordSubjectReService expenseRecordSubjectReService;
	@Autowired
	private ExpenseRecordAssetsReService expenseRecordAssetsReService;
	@Autowired
	private LiquiTransferRecordService liquiTransferRecordService;
	@Autowired
	TargetService targetService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		//拍辅价格依据
		setPaiFuStccJgyjJgyj(taskNode);
	}

	private void setPaiFuStccJgyjJgyj(TaskNode taskNode) {
		PaiFu_STCC_JGYJ_JGYJ paiFu_stcc_jgyj_jgyj = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_JGYJ_JGYJ.class);

		//添加定价费用需修改项目总金额
		if (paiFu_stcc_jgyj_jgyj.getPricingManner() != 0) {
			Target target = targetService.getById(taskNode.getTargetId());

			//查询当前财产关联债务人信息
			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

			//查询案件信息
			Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

			ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());

			//添加拍辅回款、费用明细信息
			addJgyjRepaymentFee(paiFu_stcc_jgyj_jgyj, projectPaifu, casee, assetsReSubjectDTO, 2);

			// 更新项目总金额
			projectPaifuService.updateProjectAmount(taskNode.getProjectId());

			//通过清收移交记录信息查询清收项目id
			LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), target.getGoalId());
			if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
				ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());

				projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(paiFu_stcc_jgyj_jgyj.getPricingFee()));
				projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
				//修改清收项目总金额
				projectLiquiService.updateById(projectLiqui);
				//添加清收回款、费用明细信息
				addJgyjRepaymentFee(paiFu_stcc_jgyj_jgyj, projectLiqui, casee, assetsReSubjectDTO, 1);
			}

			String json = JsonUtils.objectToJson(paiFu_stcc_jgyj_jgyj);

			taskNode.setFormData(json);

			//节点信息更新
			this.taskNodeService.updateById(taskNode);

			//发送拍辅任务消息
			taskNodeService.sendPaifuTaskMessage(taskNode, null);

			//任务数据提交 保存程序、财产和行为
			taskNodeService.setTaskDataSubmission(taskNode);
		}
	}

	//添加回款、费用明细信息
	public void addJgyjRepaymentFee(PaiFu_STCC_JGYJ_JGYJ paiFu_stcc_jgyj_jgyj, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO, Integer type) {
		ExpenseRecord jgyjExpenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(),project.getProjectId(),10006);
		BigDecimal pricingFee = paiFu_stcc_jgyj_jgyj.getPricingFee();
		if (jgyjExpenseRecord!=null){//如果存在定价费则修改定价金额
			jgyjExpenseRecord.setCostAmount(jgyjExpenseRecord.getCostAmount().add(paiFu_stcc_jgyj_jgyj.getPricingFee()));
			//修改当前财产程序定价费
			expenseRecordService.updateById(jgyjExpenseRecord);
		}else {
			//如果当前询价费不为0
			if(pricingFee.compareTo(BigDecimal.ZERO)!=0){
				//添加定价费用明细记录以及其它关联信息
				ExpenseRecord expenseRecord = expenseRecordService.addExpenseRecord(paiFu_stcc_jgyj_jgyj.getPricingFee(), paiFu_stcc_jgyj_jgyj.getPricingDate(), project, casee, assetsReSubjectDTO, null, 10006);
				if (type.equals(1)) {
					paiFu_stcc_jgyj_jgyj.setLiQuiExpenseRecordId(expenseRecord.getExpenseRecordId());
				} else {
					paiFu_stcc_jgyj_jgyj.setPaiFuExpenseRecordId(expenseRecord.getExpenseRecordId());
				}
			}
		}
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		PaiFu_STCC_JGYJ_JGYJ paiFu_STCC_JGYJ_JGYJ = JsonUtils.jsonToPojo(node.getFormData(), PaiFu_STCC_JGYJ_JGYJ.class);

		if (paiFu_STCC_JGYJ_JGYJ != null) {

			//添加定价费用需修改项目总金额
			if (paiFu_STCC_JGYJ_JGYJ.getPricingManner() != 0) {

				Target target = targetService.getById(taskNode.getTargetId());

				//删除费用明细记录
				expenseRecordService.removeById(paiFu_STCC_JGYJ_JGYJ.getPaiFuExpenseRecordId());

				//删除费用明细记录财产关联信息
				expenseRecordAssetsReService.remove(new LambdaQueryWrapper<ExpenseRecordAssetsRe>()
						.eq(ExpenseRecordAssetsRe::getExpenseRecordId, paiFu_STCC_JGYJ_JGYJ.getPaiFuExpenseRecordId()));

				//删除费用产生明细关联主体信息
				expenseRecordSubjectReService.remove(new LambdaQueryWrapper<ExpenseRecordSubjectRe>()
						.eq(ExpenseRecordSubjectRe::getExpenseRecordId, paiFu_STCC_JGYJ_JGYJ.getPaiFuExpenseRecordId()));

				//通过清收移交记录信息查询清收项目id
				LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), target.getGoalId());
				if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
					ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());

					projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().subtract(paiFu_STCC_JGYJ_JGYJ.getPricingFee()));
					projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
					//修改清收项目总金额
					projectLiquiService.updateById(projectLiqui);

					//删除费用明细记录
					expenseRecordService.removeById(paiFu_STCC_JGYJ_JGYJ.getLiQuiExpenseRecordId());

					//删除费用明细记录财产关联信息
					expenseRecordAssetsReService.remove(new LambdaQueryWrapper<ExpenseRecordAssetsRe>()
							.eq(ExpenseRecordAssetsRe::getExpenseRecordId, paiFu_STCC_JGYJ_JGYJ.getLiQuiExpenseRecordId()));

					//删除费用产生明细关联主体信息
					expenseRecordSubjectReService.remove(new LambdaQueryWrapper<ExpenseRecordSubjectRe>()
							.eq(ExpenseRecordSubjectRe::getExpenseRecordId, paiFu_STCC_JGYJ_JGYJ.getLiQuiExpenseRecordId()));
				}
			}
		}

		//拍辅价格依据
		setPaiFuStccJgyjJgyj(taskNode);
	}
}
