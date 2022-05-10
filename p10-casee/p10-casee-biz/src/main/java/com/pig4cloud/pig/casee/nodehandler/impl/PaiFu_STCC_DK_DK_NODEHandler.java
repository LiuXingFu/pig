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
public class PaiFu_STCC_DK_DK_NODEHandler extends TaskNodeHandler {

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
	private ExpenseRecordService expenseRecordService;
	@Autowired
	private PaymentRecordService paymentRecordService;
	@Autowired
	private ExpenseRecordSubjectReService expenseRecordSubjectReService;
	@Autowired
	private PaymentRecordSubjectReService paymentRecordSubjectReService;
	@Autowired
	private PaymentRecordAssetsReService paymentRecordAssetsReService;
	@Autowired
	private ProjectPaifuService projectPaifuService;
	@Autowired
	private LiquiTransferRecordService liquiTransferRecordService;
	@Autowired
	private ExpenseRecordAssetsReService expenseRecordAssetsReService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		//拍辅到款
		PaiFu_STCC_DK_DK paiFu_stcc_dk_dk = setPaiFuStccDkDk(taskNode);

		//同步联合拍卖财产到款节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_dk_dk.getAssetsId(), taskNode, "paiFu_STCC_DK_DK");
	}

	private PaiFu_STCC_DK_DK setPaiFuStccDkDk(TaskNode taskNode) {
		PaiFu_STCC_DK_DK paiFu_stcc_dk_dk = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_DK_DK.class);
		//查询当前财产拍卖公告节点信息
		TaskNode taskNodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG", taskNode.getTargetId());
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);

		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), paiFu_stcc_dk_dk.getAssetsId());

		ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());
		projectPaifu.getProjectPaifuDetail().setProjectAmount(projectPaifu.getProjectPaifuDetail().getProjectAmount().add(paiFu_stcc_dk_dk.getAuxiliaryFee()));
		projectPaifu.setProjectPaifuDetail(projectPaifu.getProjectPaifuDetail());
		//修改拍辅项目总金额
		projectPaifuService.updateById(projectPaifu);

		//添加拍辅回款、费用明细信息
		addDkRepaymentFee(paiFu_stcc_dk_dk, paiFu_stcc_pmgg_pmgg, projectPaifu, casee, assetsReSubjectDTO, 2);

		//通过清收移交记录信息查询清收项目id
		LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_stcc_dk_dk.getAssetsId());
		if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());
			projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(paiFu_stcc_dk_dk.getAuxiliaryFee()));
			projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
			//修改清收项目总金额
			projectLiquiService.updateById(projectLiqui);

			//添加清收回款、费用明细信息
			addDkRepaymentFee(paiFu_stcc_dk_dk, paiFu_stcc_pmgg_pmgg, projectLiqui, casee, assetsReSubjectDTO, 1);
		}

		String json = JsonUtils.objectToJson(paiFu_stcc_dk_dk);

		taskNode.setFormData(json);

		this.taskNodeService.updateById(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);

		taskNodeService.setTaskDataSubmission(taskNode);
		return paiFu_stcc_dk_dk;
	}


	//添加回款、费用明细信息
	public void addDkRepaymentFee(PaiFu_STCC_DK_DK paiFu_stcc_dk_dk, PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO, Integer type) {
		//添加费用明细记录
		ExpenseRecord expenseRecord = new ExpenseRecord();
		expenseRecord.setCostAmount(paiFu_stcc_dk_dk.getAuxiliaryFee());
		expenseRecord.setCostIncurredTime(paiFu_stcc_dk_dk.getFinalPaymentDate());
		expenseRecord.setProjectId(project.getProjectId());
		expenseRecord.setCaseeId(casee.getCaseeId());
		expenseRecord.setCaseeNumber(casee.getCaseeNumber());
		expenseRecord.setStatus(0);
		expenseRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		expenseRecord.setCompanyCode(project.getCompanyCode());
		expenseRecord.setCostType(10007);
		expenseRecordService.save(expenseRecord);

		if (type.equals(1)) {
			paiFu_stcc_dk_dk.setLiQuiExpenseRecordId(expenseRecord.getExpenseRecordId());
		} else {
			paiFu_stcc_dk_dk.setPaiFuExpenseRecordId(expenseRecord.getExpenseRecordId());
		}

		//添加到款到款信息
		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setPaymentType(200);
		paymentRecord.setFundsType(20003);
		paymentRecord.setStatus(0);
		paymentRecord.setPaymentDate(paiFu_stcc_dk_dk.getFinalPaymentDate());
		paymentRecord.setPaymentAmount(paiFu_stcc_dk_dk.getAmountReceived());
		paymentRecord.setCaseeId(casee.getCaseeId());
		paymentRecord.setProjectId(project.getProjectId());
		paymentRecord.setCompanyCode(project.getCompanyCode());
		paymentRecord.setCaseeNumber(casee.getCaseeNumber());
		paymentRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		paymentRecordService.save(paymentRecord);

		if (type.equals(1)) {
			paiFu_stcc_dk_dk.setLiQuiPaymentRecordId(paymentRecord.getPaymentRecordId());
		} else {
			paiFu_stcc_dk_dk.setPaiFuPaymentRecordId(paymentRecord.getPaymentRecordId());
		}

		List<PaymentRecordAssetsRe> paymentRecordAssetsReList = new ArrayList<>();
		List<ExpenseRecordAssetsRe> expenseRecordAssetsReList = new ArrayList<>();

		//循环当前拍卖公告联合拍卖财产信息
		for (AssetsReDTO assetsReDTO : paiFu_stcc_pmgg_pmgg.getAssetsReIdList()) {
			PaymentRecordAssetsRe paymentRecordAssetsRe = new PaymentRecordAssetsRe();
			paymentRecordAssetsRe.setAssetsReId(assetsReDTO.getAssetsReId());
			paymentRecordAssetsRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			paymentRecordAssetsReList.add(paymentRecordAssetsRe);

			ExpenseRecordAssetsRe expenseRecordAssetsRe = new ExpenseRecordAssetsRe();
			expenseRecordAssetsRe.setAssetsReId(assetsReDTO.getAssetsReId());
			expenseRecordAssetsRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
		}
		// 添加回款记录财产关联信息
		paymentRecordAssetsReService.saveBatch(paymentRecordAssetsReList);
		// 添加费用明细记录财产关联信息
		expenseRecordAssetsReService.saveBatch(expenseRecordAssetsReList);

		List<ExpenseRecordSubjectRe> expenseRecordSubjectRes = new ArrayList<>();
		List<PaymentRecordSubjectRe> paymentRecordSubjectRes = new ArrayList<>();
		// 遍历财产关联多个债务人
		for (Subject subject : assetsReSubjectDTO.getSubjectList()) {
			ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
			expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
			expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordSubjectRes.add(expenseRecordSubjectRe);

			PaymentRecordSubjectRe paymentRecordSubjectRe = new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setSubjectId(subject.getSubjectId());
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			paymentRecordSubjectRes.add(paymentRecordSubjectRe);
		}
		//添加费用产生明细关联主体信息
		expenseRecordSubjectReService.saveBatch(expenseRecordSubjectRes);
		//添加到款信息关联债务人
		paymentRecordSubjectReService.saveBatch(paymentRecordSubjectRes);
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		PaiFu_STCC_DK_DK paiFu_STCC_DK_DK = JsonUtils.jsonToPojo(node.getFormData(), PaiFu_STCC_DK_DK.class);

		ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());
		projectPaifu.getProjectPaifuDetail().setProjectAmount(projectPaifu.getProjectPaifuDetail().getProjectAmount().subtract(paiFu_STCC_DK_DK.getAuxiliaryFee()));
		projectPaifu.setProjectPaifuDetail(projectPaifu.getProjectPaifuDetail());
		//修改拍辅项目总金额
		projectPaifuService.updateById(projectPaifu);

		//删除费用明细记录
		expenseRecordService.removeById(paiFu_STCC_DK_DK.getPaiFuExpenseRecordId());

		//删除费用明细记录财产关联信息
		expenseRecordAssetsReService.remove(new LambdaQueryWrapper<ExpenseRecordAssetsRe>()
				.eq(ExpenseRecordAssetsRe::getExpenseRecordId, paiFu_STCC_DK_DK.getPaiFuExpenseRecordId()));

		//删除费用产生明细关联主体信息
		expenseRecordSubjectReService.remove(new LambdaQueryWrapper<ExpenseRecordSubjectRe>()
				.eq(ExpenseRecordSubjectRe::getExpenseRecordId, paiFu_STCC_DK_DK.getPaiFuExpenseRecordId()));

		//删除到款的到款信息
		paymentRecordService.removeById(paiFu_STCC_DK_DK.getPaiFuPaymentRecordId());

		//删除回款记录财产关联信息
		paymentRecordAssetsReService.remove(new LambdaQueryWrapper<PaymentRecordAssetsRe>()
				.eq(PaymentRecordAssetsRe::getPaymentRecordId, paiFu_STCC_DK_DK.getPaiFuPaymentRecordId()));

		//删除到款信息关联债务人
		paymentRecordSubjectReService.remove(new LambdaQueryWrapper<PaymentRecordSubjectRe>()
				.eq(PaymentRecordSubjectRe::getPaymentRecordId, paiFu_STCC_DK_DK.getPaiFuPaymentRecordId()));

		//通过清收移交记录信息查询清收项目id
		LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_STCC_DK_DK.getAssetsId());

		if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());
			projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().subtract(paiFu_STCC_DK_DK.getAuxiliaryFee()));
			projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
			//修改清收项目总金额
			projectLiquiService.updateById(projectLiqui);

			//删除费用明细记录
			expenseRecordService.removeById(paiFu_STCC_DK_DK.getLiQuiExpenseRecordId());

			//删除费用明细记录财产关联信息
			expenseRecordAssetsReService.remove(new LambdaQueryWrapper<ExpenseRecordAssetsRe>()
					.eq(ExpenseRecordAssetsRe::getExpenseRecordId, paiFu_STCC_DK_DK.getLiQuiExpenseRecordId()));

			//删除费用产生明细关联主体信息
			expenseRecordSubjectReService.remove(new LambdaQueryWrapper<ExpenseRecordSubjectRe>()
					.eq(ExpenseRecordSubjectRe::getExpenseRecordId, paiFu_STCC_DK_DK.getLiQuiExpenseRecordId()));

			//删除到款的到款信息
			paymentRecordService.removeById(paiFu_STCC_DK_DK.getLiQuiPaymentRecordId());

			//删除回款记录财产关联信息
			paymentRecordAssetsReService.remove(new LambdaQueryWrapper<PaymentRecordAssetsRe>()
					.eq(PaymentRecordAssetsRe::getPaymentRecordId, paiFu_STCC_DK_DK.getLiQuiPaymentRecordId()));

			//删除到款信息关联债务人
			paymentRecordSubjectReService.remove(new LambdaQueryWrapper<PaymentRecordSubjectRe>()
					.eq(PaymentRecordSubjectRe::getPaymentRecordId, paiFu_STCC_DK_DK.getLiQuiPaymentRecordId()));
		}

		//拍辅到款
		PaiFu_STCC_DK_DK paiFu_stcc_dk_dk = setPaiFuStccDkDk(taskNode);

		//同步联合拍卖财产到款节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_dk_dk.getAssetsId(), taskNode, "paiFu_STCC_DK_DK");

	}
}
