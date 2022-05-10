package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.AssetsReDTO;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_DK_DK;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_JGYJ_JGYJ;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMGG_PMGG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
			//查询当前财产关联债务人信息
			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), paiFu_stcc_jgyj_jgyj.getAssetsId());
			//查询当前财产拍卖公告节点信息
			TaskNode taskNodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG", taskNode.getTargetId());
			PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);

			//查询案件信息
			Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

			ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());
			projectPaifu.getProjectPaifuDetail().setProjectAmount(projectPaifu.getProjectPaifuDetail().getProjectAmount().add(paiFu_stcc_jgyj_jgyj.getPricingFee()));
			projectPaifu.setProjectPaifuDetail(projectPaifu.getProjectPaifuDetail());
			//修改拍辅项目总金额
			projectPaifuService.updateById(projectPaifu);

			//添加拍辅回款、费用明细信息
			addJgyjRepaymentFee(paiFu_stcc_jgyj_jgyj, paiFu_stcc_pmgg_pmgg, projectPaifu, casee, assetsReSubjectDTO, 1);

			//通过清收移交记录信息查询清收项目id
			LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_stcc_jgyj_jgyj.getAssetsId());
			if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
				ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());

				projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(paiFu_stcc_jgyj_jgyj.getPricingFee()));
				projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
				//修改清收项目总金额
				projectLiquiService.updateById(projectLiqui);
				//添加清收回款、费用明细信息
				addJgyjRepaymentFee(paiFu_stcc_jgyj_jgyj, paiFu_stcc_pmgg_pmgg, projectLiqui, casee, assetsReSubjectDTO, 2);
			}
		}

		String json = JsonUtils.objectToJson(paiFu_stcc_jgyj_jgyj);

		taskNode.setFormData(json);

		this.taskNodeService.updateById(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);

		taskNodeService.setTaskDataSubmission(taskNode);
	}

	//添加回款、费用明细信息
	public void addJgyjRepaymentFee(PaiFu_STCC_JGYJ_JGYJ paiFu_stcc_jgyj_jgyj, PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO, Integer type) {
		//添加定价费用明细记录
		ExpenseRecord expenseRecord = new ExpenseRecord();
		expenseRecord.setCostAmount(paiFu_stcc_jgyj_jgyj.getPricingFee());
		expenseRecord.setCostIncurredTime(paiFu_stcc_jgyj_jgyj.getPricingDate());
		expenseRecord.setProjectId(project.getProjectId());
		expenseRecord.setCaseeId(casee.getCaseeId());
		expenseRecord.setCaseeNumber(casee.getCaseeNumber());
		expenseRecord.setStatus(0);
		expenseRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		expenseRecord.setCompanyCode(project.getCompanyCode());
		expenseRecord.setCostType(10006);
		expenseRecordService.save(expenseRecord);

		if (type.equals(1)) {
			paiFu_stcc_jgyj_jgyj.setLiQuiExpenseRecordId(expenseRecord.getExpenseRecordId());
		} else {
			paiFu_stcc_jgyj_jgyj.setPaiFuExpenseRecordId(expenseRecord.getExpenseRecordId());
		}

		if (paiFu_stcc_pmgg_pmgg != null) {
			List<ExpenseRecordAssetsRe> expenseRecordAssetsReList = new ArrayList<>();
			//循环当前拍卖公告联合拍卖财产信息
			for (AssetsReDTO assetsReDTO : paiFu_stcc_pmgg_pmgg.getAssetsReIdList()) {
				ExpenseRecordAssetsRe expenseRecordAssetsRe = new ExpenseRecordAssetsRe();
				expenseRecordAssetsRe.setAssetsReId(assetsReDTO.getAssetsReId());
				expenseRecordAssetsRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
				expenseRecordAssetsReList.add(expenseRecordAssetsRe);
			}
			// 添加费用产生记录财产关联信息
			expenseRecordAssetsReService.saveBatch(expenseRecordAssetsReList);
		}

		List<ExpenseRecordSubjectRe> expenseRecordSubjectReList = new ArrayList<>();
		for (Subject subject : assetsReSubjectDTO.getSubjectList()) {
			ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
			expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
			expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordSubjectReList.add(expenseRecordSubjectRe);
		}
		//添加费用产生明细关联主体信息
		expenseRecordSubjectReService.saveBatch(expenseRecordSubjectReList);
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {

		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		PaiFu_STCC_JGYJ_JGYJ paiFu_STCC_JGYJ_JGYJ = JsonUtils.jsonToPojo(node.getFormData(), PaiFu_STCC_JGYJ_JGYJ.class);

		ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());
		projectPaifu.getProjectPaifuDetail().setProjectAmount(projectPaifu.getProjectPaifuDetail().getProjectAmount().subtract(paiFu_STCC_JGYJ_JGYJ.getPricingFee()));
		projectPaifu.setProjectPaifuDetail(projectPaifu.getProjectPaifuDetail());
		//修改拍辅项目总金额
		projectPaifuService.updateById(projectPaifu);

		//删除费用明细记录
		expenseRecordService.removeById(paiFu_STCC_JGYJ_JGYJ.getPaiFuExpenseRecordId());

		//删除费用明细记录财产关联信息
		expenseRecordAssetsReService.remove(new LambdaQueryWrapper<ExpenseRecordAssetsRe>()
				.eq(ExpenseRecordAssetsRe::getExpenseRecordId, paiFu_STCC_JGYJ_JGYJ.getPaiFuExpenseRecordId()));

		//删除费用产生明细关联主体信息
		expenseRecordSubjectReService.remove(new LambdaQueryWrapper<ExpenseRecordSubjectRe>()
				.eq(ExpenseRecordSubjectRe::getExpenseRecordId, paiFu_STCC_JGYJ_JGYJ.getPaiFuExpenseRecordId()));

		//通过清收移交记录信息查询清收项目id
		LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_STCC_JGYJ_JGYJ.getAssetsId());
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

		setPaiFuStccJgyjJgyj(taskNode);
	}
}
