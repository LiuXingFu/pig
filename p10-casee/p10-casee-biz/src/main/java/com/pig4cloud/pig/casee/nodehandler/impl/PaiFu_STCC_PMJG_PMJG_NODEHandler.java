package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
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

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍辅拍卖结果
		PaiFu_STCC_PMJG_PMJG paiFu_stcc_pmjg_pmjg = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_PMJG_PMJG.class);
		if (paiFu_stcc_pmjg_pmjg.getAuctionResults()==1){//流拍

			//查询最后一条拍卖公告信息
			TaskNode taskNodePmgg = taskNodeService.queryNewTaskNode("paiFu_STCC_PMGG_PMGG",taskNode);
			if (taskNodePmgg!=null){
				//复制拍卖公告节点
				taskNodeService.copyTaskNode(taskNodePmgg);
			}
			//查询最后一条接受咨询信息
			TaskNode taskNodeJszx = taskNodeService.queryNewTaskNode("paiFu_STCC_JSZX_JSZX",taskNode);
			if (taskNodeJszx!=null){
				//复制接受咨询节点
				taskNodeService.copyTaskNode(taskNodeJszx);
			}
			//查询最后一条报名看样信息
			TaskNode taskNodeBmky = taskNodeService.queryNewTaskNode("paiFu_STCC_BMKY_BMKY",taskNode);
			if (taskNodeBmky!=null){
				//复制报名看样节点
				taskNodeService.copyTaskNode(taskNodeBmky);
			}
			//查询最后一条看样准备工作信息
			TaskNode taskNodeKyzbgz = taskNodeService.queryNewTaskNode("paiFu_STCC_KYZBGZ_KYZBGZ",taskNode);
			if (taskNodeKyzbgz!=null){
				//复制看样准备工作节点
				taskNodeService.copyTaskNode(taskNodeKyzbgz);
			}
			//查询最后一条引领看样信息
			TaskNode taskNodeYlky = taskNodeService.queryNewTaskNode("paiFu_STCC_YLKY_YLKY",taskNode);
			if (taskNodeYlky!=null){
				//复制引领看样节点
				taskNodeService.copyTaskNode(taskNodeYlky);
			}
			//复制拍卖结果节点
			taskNodeService.copyTaskNode(taskNode);
		}else {//成交
			//通过清收移交记录信息查询清收项目id
			LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_stcc_pmjg_pmjg.getAssetsId());
			//查询案件信息
			Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());
			//查询当前财产关联债务人信息
			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(liquiTransferRecord.getProjectId(), taskNode.getCaseeId(), paiFu_stcc_pmjg_pmjg.getAssetsId());

			//查询拍辅项目信息
			ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());
			//查询清收项目信息
			projectPaifu.getProjectPaifuDetail().setProjectAmount(projectPaifu.getProjectPaifuDetail().getProjectAmount().add(paiFu_stcc_pmjg_pmjg.getAuxiliaryFee()));
			projectPaifu.setProjectPaifuDetail(projectPaifu.getProjectPaifuDetail());
			//修改拍辅项目总金额
			projectPaifuService.updateById(projectPaifu);

			//添加拍辅费用明细记录
			ExpenseRecord pfExpenseRecord=new ExpenseRecord();
			pfExpenseRecord.setCostAmount(paiFu_stcc_pmjg_pmjg.getAuxiliaryFee());
			pfExpenseRecord.setCostIncurredTime(paiFu_stcc_pmjg_pmjg.getClosingDate());
			pfExpenseRecord.setProjectId(projectPaifu.getProjectId());
			pfExpenseRecord.setCaseeId(taskNode.getCaseeId());
			pfExpenseRecord.setCaseeNumber(casee.getCaseeNumber());
			pfExpenseRecord.setStatus(0);
			pfExpenseRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
			pfExpenseRecord.setCompanyCode(projectPaifu.getCompanyCode());
			pfExpenseRecord.setCostType(10007);
			expenseRecordService.save(pfExpenseRecord);

			//添加拍辅费用产生明细关联主体信息
			List<ExpenseRecordSubjectRe> pfExpenseRecordSubjectRes = new ArrayList<>();
			for (Subject subject:assetsReSubjectDTO.getSubjectList()){
				ExpenseRecordSubjectRe expenseRecordSubjectRe=new ExpenseRecordSubjectRe();
				expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
				expenseRecordSubjectRe.setExpenseRecordId(pfExpenseRecord.getExpenseRecordId());
				pfExpenseRecordSubjectRes.add(expenseRecordSubjectRe);
			}
			expenseRecordSubjectReService.saveBatch(pfExpenseRecordSubjectRes);

			//查询清收项目信息
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());
			projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(paiFu_stcc_pmjg_pmjg.getAuxiliaryFee()));
			projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
			//修改清收项目总金额
			projectLiquiService.updateById(projectLiqui);

			//添加清收费用明细记录
			ExpenseRecord expenseRecord=new ExpenseRecord();
			expenseRecord.setCostAmount(paiFu_stcc_pmjg_pmjg.getAuxiliaryFee());
			expenseRecord.setCostIncurredTime(paiFu_stcc_pmjg_pmjg.getClosingDate());
			expenseRecord.setProjectId(projectLiqui.getProjectId());
			expenseRecord.setCaseeId(taskNode.getCaseeId());
			expenseRecord.setCaseeNumber(casee.getCaseeNumber());
			expenseRecord.setStatus(0);
			expenseRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
			expenseRecord.setCompanyCode(projectLiqui.getCompanyCode());
			expenseRecord.setCostType(10007);
			expenseRecordService.save(expenseRecord);

			//添加清收费用产生明细关联主体信息
			List<ExpenseRecordSubjectRe> expenseRecordSubjectRes = new ArrayList<>();
			for (Subject subject:assetsReSubjectDTO.getSubjectList()){
				ExpenseRecordSubjectRe expenseRecordSubjectRe=new ExpenseRecordSubjectRe();
				expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
				expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
				expenseRecordSubjectRes.add(expenseRecordSubjectRe);
			}
			expenseRecordSubjectReService.saveBatch(expenseRecordSubjectRes);
		}


	}
}
