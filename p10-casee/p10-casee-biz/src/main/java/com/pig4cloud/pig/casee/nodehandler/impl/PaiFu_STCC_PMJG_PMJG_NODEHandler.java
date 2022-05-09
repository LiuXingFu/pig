package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.AssetsReDTO;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMGG_PMGG;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMJG_PMJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaiFu_STCC_PMJG_PMJG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	TargetService targetService;
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
	private LiquiTransferRecordService liquiTransferRecordService;
	@Autowired
	private ProjectPaifuService projectPaifuService;
	@Autowired
	private ExpenseRecordAssetsReService expenseRecordAssetsReService;


	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍辅拍卖结果
		PaiFu_STCC_PMJG_PMJG paiFu_stcc_pmjg_pmjg = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_PMJG_PMJG.class);

		//查询当前财产拍卖公告节点信息
		TaskNode nodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG", taskNode.getTargetId());
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(nodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);

		//同步联合拍卖财产拍卖结果节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_pmjg_pmjg.getAssetsId(), taskNode, "paiFu_STCC_PMJG_PMJG");

		if (paiFu_stcc_pmjg_pmjg.getAuctionResults() == 1) {//流拍

			//查询最后一条拍卖公告信息
			TaskNode taskNodePmgg = taskNodeService.queryNewTaskNode("paiFu_STCC_PMGG_PMGG", taskNode);
			if (taskNodePmgg != null) {
				//复制拍卖公告节点
				taskNodeService.copyTaskNode(taskNodePmgg);
			}
			//查询最后一条接受咨询信息
			TaskNode taskNodeJszx = taskNodeService.queryNewTaskNode("paiFu_STCC_JSZX_JSZX", taskNode);
			if (taskNodeJszx != null) {
				//复制接受咨询节点
				taskNodeService.copyTaskNode(taskNodeJszx);
			}
			//查询最后一条报名看样信息
			TaskNode taskNodeBmky = taskNodeService.queryNewTaskNode("paiFu_STCC_BMKY_BMKY", taskNode);
			if (taskNodeBmky != null) {
				//复制报名看样节点
				taskNodeService.copyTaskNode(taskNodeBmky);
			}
			//查询最后一条看样准备工作信息
			TaskNode taskNodeKyzbgz = taskNodeService.queryNewTaskNode("paiFu_STCC_KYZBGZ_KYZBGZ", taskNode);
			if (taskNodeKyzbgz != null) {
				//复制看样准备工作节点
				taskNodeService.copyTaskNode(taskNodeKyzbgz);
			}
			//查询最后一条引领看样信息
			TaskNode taskNodeYlky = taskNodeService.queryNewTaskNode("paiFu_STCC_YLKY_YLKY", taskNode);
			if (taskNodeYlky != null) {
				//复制引领看样节点
				taskNodeService.copyTaskNode(taskNodeYlky);
			}
			//复制拍卖结果节点
			taskNodeService.copyTaskNode(taskNode);
		} else {//成交
			//通过清收移交记录信息查询清收项目id
			LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_stcc_pmjg_pmjg.getAssetsId());
			//查询案件信息
			Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());
			//查询当前财产关联债务人信息
			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(liquiTransferRecord.getProjectId(), taskNode.getCaseeId(), paiFu_stcc_pmjg_pmjg.getAssetsId());

			//查询拍辅项目信息
			ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());
			projectPaifu.getProjectPaifuDetail().setProjectAmount(projectPaifu.getProjectPaifuDetail().getProjectAmount().add(paiFu_stcc_pmjg_pmjg.getAuxiliaryFee()));
			projectPaifu.setProjectPaifuDetail(projectPaifu.getProjectPaifuDetail());
			//修改拍辅项目总金额
			projectPaifuService.updateById(projectPaifu);

			//添加拍辅回款、费用明细信息
			addPmjgRepaymentFee(paiFu_stcc_pmjg_pmjg,paiFu_stcc_pmgg_pmgg,projectPaifu,casee,assetsReSubjectDTO);


			if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
				//查询清收项目信息
				ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());
				projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(paiFu_stcc_pmjg_pmjg.getAuxiliaryFee()));
				projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
				//修改清收项目总金额
				projectLiquiService.updateById(projectLiqui);

				//添加清收回款、费用明细信息
				addPmjgRepaymentFee(paiFu_stcc_pmjg_pmjg,paiFu_stcc_pmgg_pmgg,projectLiqui,casee,assetsReSubjectDTO);
			}
		}
	}

	public void addPmjgRepaymentFee(PaiFu_STCC_PMJG_PMJG paiFu_stcc_pmjg_pmjg, PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO) {
		//添加费用明细记录
		ExpenseRecord expenseRecord = new ExpenseRecord();
		expenseRecord.setCostAmount(paiFu_stcc_pmjg_pmjg.getAuxiliaryFee());
		expenseRecord.setCostIncurredTime(paiFu_stcc_pmjg_pmjg.getClosingDate());
		expenseRecord.setProjectId(project.getProjectId());
		expenseRecord.setCaseeId(casee.getCaseeId());
		expenseRecord.setCaseeNumber(casee.getCaseeNumber());
		expenseRecord.setStatus(0);
		expenseRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		expenseRecord.setCompanyCode(project.getCompanyCode());
		expenseRecord.setCostType(10007);
		expenseRecordService.save(expenseRecord);

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
}
