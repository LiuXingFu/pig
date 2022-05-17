package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.CustomerSubjectDTO;
import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionResultsSaveDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMGG_PMGG;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMJG_PMJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.BeanUtils;
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
	@Autowired
	private AuctionRecordService auctionRecordService;
	@Autowired
	CustomerService customerService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		//拍辅拍卖结果
		PaiFu_STCC_PMJG_PMJG paiFu_stcc_pmjg_pmjg = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_PMJG_PMJG.class);

		//查询当前财产拍卖公告节点信息
		TaskNode nodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG", taskNode.getTargetId());
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(nodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);

		//拍卖记录信息
		AuctionResultsSaveDTO auctionResultsSaveDTO=new AuctionResultsSaveDTO();
		BeanUtils.copyProperties(paiFu_stcc_pmjg_pmjg,auctionResultsSaveDTO);
		auctionResultsSaveDTO.setAuctionId(paiFu_stcc_pmgg_pmgg.getAuctionId());
		auctionResultsSaveDTO.setAuctionRecordId(paiFu_stcc_pmgg_pmgg.getAuctionRecordId());
		auctionResultsSaveDTO.setResultsTime(paiFu_stcc_pmjg_pmjg.getClosingDate());
		auctionResultsSaveDTO.setResultsType(paiFu_stcc_pmjg_pmjg.getAuctionResults());
		auctionResultsSaveDTO.setAuctionPeopleNumber(paiFu_stcc_pmjg_pmjg.getNumberOfParticipants());
		auctionResultsSaveDTO.setAppendix(paiFu_stcc_pmjg_pmjg.getAppendixFile());
		//修改拍卖记录信息
		auctionRecordService.saveAuctionResults(auctionResultsSaveDTO);

		//同步联合拍卖财产拍卖结果节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode, "paiFu_STCC_PMJG_PMJG");

		if (paiFu_stcc_pmjg_pmjg.getAuctionResults() == 20) {//流拍
			//如果拍卖结果流拍则复制新的拍卖公告到拍卖结果这一段环节节点并删除之前的节点
			taskNodeService.auctionResultsCopyTaskNode(paiFu_stcc_pmgg_pmgg.getJointAuctionAssetsDTOList());
		} else {//成交
			//查询案件信息
			Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

			//查询拍辅项目信息
			ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());
			projectPaifu.getProjectPaifuDetail().setProjectAmount(projectPaifu.getProjectPaifuDetail().getProjectAmount().add(paiFu_stcc_pmjg_pmjg.getAuxiliaryFee()));
			projectPaifu.setProjectPaifuDetail(projectPaifu.getProjectPaifuDetail());
			//修改拍辅项目总金额
			projectPaifuService.updateById(projectPaifu);

			Target target = targetService.getById(taskNode.getTargetId());

			//查询拍辅财产关联债务人信息
			AssetsReSubjectDTO pfAssetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

			//添加拍辅回款、费用明细信息
			addPmjgRepaymentFee(paiFu_stcc_pmjg_pmjg,paiFu_stcc_pmgg_pmgg,projectPaifu,casee,pfAssetsReSubjectDTO);

			//通过清收移交记录信息查询清收项目id
			LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), target.getGoalId());
			if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
				//查询清收财产关联债务人信息
				AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(liquiTransferRecord.getProjectId(), taskNode.getCaseeId(), target.getGoalId());
				//查询清收项目信息
				ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());
				projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(paiFu_stcc_pmjg_pmjg.getAuxiliaryFee()));
				projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
				//修改清收项目总金额
				projectLiquiService.updateById(projectLiqui);

				//添加清收回款、费用明细信息
				addPmjgRepaymentFee(paiFu_stcc_pmjg_pmjg,paiFu_stcc_pmgg_pmgg,projectLiqui,casee,assetsReSubjectDTO);
			}
			CustomerSubjectDTO customerSubjectDTO=new CustomerSubjectDTO();
			if (paiFu_stcc_pmjg_pmjg.getIdentityCard()!=null){
				customerSubjectDTO.setUnifiedIdentity(paiFu_stcc_pmjg_pmjg.getIdentityCard());
			}
			customerSubjectDTO.setNatureType(0);
			customerSubjectDTO.setPhone(paiFu_stcc_pmjg_pmjg.getPhone());
			customerSubjectDTO.setName(paiFu_stcc_pmjg_pmjg.getBuyer());
			customerSubjectDTO.setProjectId(taskNode.getProjectId());
			customerSubjectDTO.setCaseeId(taskNode.getCaseeId());
			customerSubjectDTO.setCustomerType(20000);
			//添加客户信息
			customerService.saveCustomer(customerSubjectDTO);
		}

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);
	}

	public void addPmjgRepaymentFee(PaiFu_STCC_PMJG_PMJG paiFu_stcc_pmjg_pmjg, PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO) {
		ExpenseRecord pfExpenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(),project.getProjectId(),10007);
		if (pfExpenseRecord!=null){
			pfExpenseRecord.setCostAmount(pfExpenseRecord.getCostAmount().add(paiFu_stcc_pmjg_pmjg.getAuxiliaryFee()));
			pfExpenseRecord.setStatus(0);
			//修改当前财产程序拍辅费
			expenseRecordService.updateById(pfExpenseRecord);
		}else {
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
			for (JointAuctionAssetsDTO jointAuctionAssetsDTO : paiFu_stcc_pmgg_pmgg.getJointAuctionAssetsDTOList()) {
				ExpenseRecordAssetsRe expenseRecordAssetsRe = new ExpenseRecordAssetsRe();
				expenseRecordAssetsRe.setAssetsReId(jointAuctionAssetsDTO.getAssetsReId());
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
}
