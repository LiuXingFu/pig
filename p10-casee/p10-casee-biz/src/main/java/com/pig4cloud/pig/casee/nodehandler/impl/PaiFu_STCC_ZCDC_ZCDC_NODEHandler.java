package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.AssetsReDTO;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMGG_PMGG;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_ZCDC_ZCDC;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaiFu_STCC_ZCDC_ZCDC_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	private CaseeLiquiService caseeLiquiService;
	@Autowired
	AssetsReLiquiService assetsReLiquiService;
	@Autowired
	private ProjectPaifuService projectPaifuService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private LiquiTransferRecordService liquiTransferRecordService;
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
	private ExpenseRecordAssetsReService expenseRecordAssetsReService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		//拍辅资产抵偿
		PaiFu_STCC_ZCDC_ZCDC paiFu_stcc_zcdc_zcdc = setPaiFuStccZcdcZcdc(taskNode);

		//同步联合拍卖财产资产抵偿节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_zcdc_zcdc.getAssetsId(),taskNode,"paiFu_STCC_ZCDC_ZCDC");
	}

	private PaiFu_STCC_ZCDC_ZCDC setPaiFuStccZcdcZcdc(TaskNode taskNode) {
		PaiFu_STCC_ZCDC_ZCDC paiFu_stcc_zcdc_zcdc = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_ZCDC_ZCDC.class);
		//查询当前财产拍卖公告节点信息
		TaskNode taskNodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG", taskNode.getTargetId());
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);

		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), paiFu_stcc_zcdc_zcdc.getAssetsId());

		ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());
		projectPaifu.getProjectPaifuDetail().setProjectAmount(projectPaifu.getProjectPaifuDetail().getProjectAmount().add(paiFu_stcc_zcdc_zcdc.getAuxiliaryFee()));
		projectPaifu.setProjectPaifuDetail(projectPaifu.getProjectPaifuDetail());
		//修改拍辅项目总金额
		projectPaifuService.updateById(projectPaifu);

		addZcdcRepaymentFee(paiFu_stcc_zcdc_zcdc,paiFu_stcc_pmgg_pmgg,projectPaifu,casee,assetsReSubjectDTO, 2);


		//通过清收移交记录信息查询清收项目id
		LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_stcc_zcdc_zcdc.getAssetsId());
		if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());
			projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(paiFu_stcc_zcdc_zcdc.getAuxiliaryFee()));
			projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
			//修改清收项目总金额
			projectLiquiService.updateById(projectLiqui);

			//添加清收回款、费用明细信息
			addZcdcRepaymentFee(paiFu_stcc_zcdc_zcdc,paiFu_stcc_pmgg_pmgg,projectLiqui,casee,assetsReSubjectDTO, 1);
		}

		String json = JsonUtils.objectToJson(paiFu_stcc_zcdc_zcdc);

		taskNode.setFormData(json);

		this.taskNodeService.updateById(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);

		taskNodeService.setTaskDataSubmission(taskNode);
		return paiFu_stcc_zcdc_zcdc;
	}

	//添加回款、费用明细信息
	public void addZcdcRepaymentFee(PaiFu_STCC_ZCDC_ZCDC paiFu_stcc_zcdc_zcdc, PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO, Integer type) {
		//添加费用明细记录
		ExpenseRecord expenseRecord = new ExpenseRecord();
		expenseRecord.setCostAmount(paiFu_stcc_zcdc_zcdc.getAuxiliaryFee());
		expenseRecord.setCostIncurredTime(paiFu_stcc_zcdc_zcdc.getSettlementDate());
		expenseRecord.setProjectId(project.getProjectId());
		expenseRecord.setCaseeId(casee.getCaseeId());
		expenseRecord.setCaseeNumber(casee.getCaseeNumber());
		expenseRecord.setStatus(0);
		expenseRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		expenseRecord.setCompanyCode(project.getCompanyCode());
		expenseRecord.setCostType(10007);
		expenseRecordService.save(expenseRecord);

		if (type.equals(1)) {
			paiFu_stcc_zcdc_zcdc.setLiQuiExpenseRecordId(expenseRecord.getExpenseRecordId());
		} else {
			paiFu_stcc_zcdc_zcdc.setPaiFuExpenseRecordId(expenseRecord.getExpenseRecordId());
		}

		//添加资产抵偿回款信息
		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setPaymentType(400);
		paymentRecord.setPaymentDate(paiFu_stcc_zcdc_zcdc.getSettlementDate());
		paymentRecord.setPaymentAmount(paiFu_stcc_zcdc_zcdc.getCompensationAmount());
		paymentRecord.setCaseeId(casee.getCaseeId());
		paymentRecord.setProjectId(project.getProjectId());
		paymentRecord.setCompanyCode(project.getCompanyCode());
		paymentRecord.setCaseeNumber(casee.getCaseeNumber());
		paymentRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		paymentRecordService.save(paymentRecord);

		if (type.equals(1)) {
			paiFu_stcc_zcdc_zcdc.setLiQuiPaymentRecordId(paymentRecord.getPaymentRecordId());
		} else {
			paiFu_stcc_zcdc_zcdc.setPaiFuPaymentRecordId(paymentRecord.getPaymentRecordId());
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

		PaiFu_STCC_ZCDC_ZCDC paiFu_STCC_ZCDC_ZCDC = JsonUtils.jsonToPojo(node.getFormData(), PaiFu_STCC_ZCDC_ZCDC.class);

		ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());
		projectPaifu.getProjectPaifuDetail().setProjectAmount(projectPaifu.getProjectPaifuDetail().getProjectAmount().subtract(paiFu_STCC_ZCDC_ZCDC.getAuxiliaryFee()));
		projectPaifu.setProjectPaifuDetail(projectPaifu.getProjectPaifuDetail());
		//修改拍辅项目总金额
		projectPaifuService.updateById(projectPaifu);

		//删除费用明细记录
		expenseRecordService.removeById(paiFu_STCC_ZCDC_ZCDC.getPaiFuExpenseRecordId());

		//删除费用明细记录财产关联信息
		expenseRecordAssetsReService.remove(new LambdaQueryWrapper<ExpenseRecordAssetsRe>()
				.eq(ExpenseRecordAssetsRe::getExpenseRecordId, paiFu_STCC_ZCDC_ZCDC.getPaiFuExpenseRecordId()));

		//添加费用产生明细关联主体信息
		expenseRecordSubjectReService.remove(new LambdaQueryWrapper<ExpenseRecordSubjectRe>()
				.eq(ExpenseRecordSubjectRe::getExpenseRecordId, paiFu_STCC_ZCDC_ZCDC.getPaiFuExpenseRecordId()));

		//删除到款的到款信息
		paymentRecordService.removeById(paiFu_STCC_ZCDC_ZCDC.getPaiFuPaymentRecordId());

		//删除回款记录财产关联信息
		paymentRecordAssetsReService.remove(new LambdaQueryWrapper<PaymentRecordAssetsRe>()
				.eq(PaymentRecordAssetsRe::getPaymentRecordId, paiFu_STCC_ZCDC_ZCDC.getPaiFuPaymentRecordId()));

		//删除到款信息关联债务人
		paymentRecordSubjectReService.remove(new LambdaQueryWrapper<PaymentRecordSubjectRe>()
				.eq(PaymentRecordSubjectRe::getPaymentRecordId, paiFu_STCC_ZCDC_ZCDC.getPaiFuPaymentRecordId()));

		//通过清收移交记录信息查询清收项目id
		LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_STCC_ZCDC_ZCDC.getAssetsId());
		if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());
			projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().subtract(paiFu_STCC_ZCDC_ZCDC.getAuxiliaryFee()));
			projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
			//修改清收项目总金额
			projectLiquiService.updateById(projectLiqui);

			//删除费用明细记录
			expenseRecordService.removeById(paiFu_STCC_ZCDC_ZCDC.getLiQuiExpenseRecordId());

			//删除费用明细记录财产关联信息
			expenseRecordAssetsReService.remove(new LambdaQueryWrapper<ExpenseRecordAssetsRe>()
					.eq(ExpenseRecordAssetsRe::getExpenseRecordId, paiFu_STCC_ZCDC_ZCDC.getLiQuiExpenseRecordId()));

			//删除费用产生明细关联主体信息
			expenseRecordSubjectReService.remove(new LambdaQueryWrapper<ExpenseRecordSubjectRe>()
					.eq(ExpenseRecordSubjectRe::getExpenseRecordId, paiFu_STCC_ZCDC_ZCDC.getLiQuiExpenseRecordId()));

			//删除到款的到款信息
			paymentRecordService.removeById(paiFu_STCC_ZCDC_ZCDC.getLiQuiPaymentRecordId());

			//删除回款记录财产关联信息
			paymentRecordAssetsReService.remove(new LambdaQueryWrapper<PaymentRecordAssetsRe>()
					.eq(PaymentRecordAssetsRe::getPaymentRecordId, paiFu_STCC_ZCDC_ZCDC.getLiQuiPaymentRecordId()));

			//删除到款信息关联债务人
			paymentRecordSubjectReService.remove(new LambdaQueryWrapper<PaymentRecordSubjectRe>()
					.eq(PaymentRecordSubjectRe::getPaymentRecordId, paiFu_STCC_ZCDC_ZCDC.getLiQuiPaymentRecordId()));
		}

		//拍辅资产抵偿
		PaiFu_STCC_ZCDC_ZCDC paiFu_stcc_zcdc_zcdc = setPaiFuStccZcdcZcdc(taskNode);

		//同步联合拍卖财产资产抵偿节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_zcdc_zcdc.getAssetsId(),taskNode,"paiFu_STCC_ZCDC_ZCDC");
	}
}
