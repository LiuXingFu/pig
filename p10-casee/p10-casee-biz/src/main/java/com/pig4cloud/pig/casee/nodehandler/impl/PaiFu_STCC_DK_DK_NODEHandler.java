package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
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
		setPaiFuStccDkDk(taskNode);

		PaiFu_STCC_DK_DK paiFu_stcc_dk_dk = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_DK_DK.class);

		//同步联合拍卖财产到款节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_dk_dk.getAssetsId(), taskNode, "paiFu_STCC_DK_DK");
	}

	private void setPaiFuStccDkDk(TaskNode taskNode) {
		PaiFu_STCC_DK_DK paiFu_stcc_dk_dk = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_DK_DK.class);
		//查询当前财产拍卖公告节点信息
		TaskNode taskNodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG", taskNode.getTargetId());
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);

		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), paiFu_stcc_dk_dk.getAssetsId());

		ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());

		//添加拍辅回款、费用明细信息
		addDkRepaymentFee(paiFu_stcc_dk_dk, paiFu_stcc_pmgg_pmgg, projectPaifu, casee, assetsReSubjectDTO, 2);

		// 更新拍辅项目总金额
		projectPaifuService.updateRepaymentAmount(taskNode.getProjectId());

		//通过清收移交记录信息查询清收项目id
		LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_stcc_dk_dk.getAssetsId());
		if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());
			//添加清收回款、费用明细信息
			addDkRepaymentFee(paiFu_stcc_dk_dk, paiFu_stcc_pmgg_pmgg, projectLiqui, casee, assetsReSubjectDTO, 1);
			// 更新清收项目总金额
			projectLiquiService.modifyProjectAmount(liquiTransferRecord.getProjectId());
		}

		String json = JsonUtils.objectToJson(paiFu_stcc_dk_dk);

		taskNode.setFormData(json);

		this.taskNodeService.updateById(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);

		taskNodeService.setTaskDataSubmission(taskNode);
	}


	//添加回款、费用明细信息
	public void addDkRepaymentFee(PaiFu_STCC_DK_DK paiFu_stcc_dk_dk, PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO, Integer type) {
		//查询当前财产程序拍辅费
		ExpenseRecord expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(),project.getProjectId(),10007);
		expenseRecord.setCostAmount(expenseRecord.getCostAmount().add(paiFu_stcc_dk_dk.getAuxiliaryFee()));
		//修改当前财产程序拍辅费
		expenseRecordService.updateById(expenseRecord);

		if (type.equals(2)) {//拍辅
			paiFu_stcc_dk_dk.setPaiFuExpenseRecordId(expenseRecord.getExpenseRecordId());
		} else {//清收
			paiFu_stcc_dk_dk.setLiQuiExpenseRecordId(expenseRecord.getExpenseRecordId());
			//添加清收到款到款信息
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

			paiFu_stcc_dk_dk.setLiQuiPaymentRecordId(paymentRecord.getPaymentRecordId());
			List<PaymentRecordAssetsRe> paymentRecordAssetsReList = new ArrayList<>();

			//循环当前拍卖公告联合拍卖财产信息
			for (JointAuctionAssetsDTO jointAuctionAssetsDTO : paiFu_stcc_pmgg_pmgg.getJointAuctionAssetsDTOList()) {
				PaymentRecordAssetsRe paymentRecordAssetsRe = new PaymentRecordAssetsRe();
				paymentRecordAssetsRe.setAssetsReId(jointAuctionAssetsDTO.getAssetsReId());
				paymentRecordAssetsRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
				paymentRecordAssetsReList.add(paymentRecordAssetsRe);
			}
			// 添加回款记录财产关联信息
			paymentRecordAssetsReService.saveBatch(paymentRecordAssetsReList);

			List<PaymentRecordSubjectRe> paymentRecordSubjectRes = new ArrayList<>();
			for (Subject subject : assetsReSubjectDTO.getSubjectList()) {
				PaymentRecordSubjectRe paymentRecordSubjectRe = new PaymentRecordSubjectRe();
				paymentRecordSubjectRe.setSubjectId(subject.getSubjectId());
				paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
				paymentRecordSubjectRes.add(paymentRecordSubjectRe);
			}
			//添加到款信息关联债务人
			paymentRecordSubjectReService.saveBatch(paymentRecordSubjectRes);
		}
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		PaiFu_STCC_DK_DK paiFu_STCC_DK_DK = JsonUtils.jsonToPojo(node.getFormData(), PaiFu_STCC_DK_DK.class);


		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(node.getProjectId(), node.getCaseeId(), paiFu_STCC_DK_DK.getAssetsId());

		//查询拍辅项目
		ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());

		//将之前的拍辅费减除并修改
		updateExpenseRecord(paiFu_STCC_DK_DK, assetsReSubjectDTO, projectPaifu.getProjectId());

		//通过清收移交记录信息查询清收项目id
		LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_STCC_DK_DK.getAssetsId());

		if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细

			//查询清收项目
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());

			//将之前的拍辅费减除并修改
			updateExpenseRecord(paiFu_STCC_DK_DK, assetsReSubjectDTO, projectLiqui.getProjectId());

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
		setPaiFuStccDkDk(taskNode);

		PaiFu_STCC_DK_DK paiFu_stcc_dk_dk = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_DK_DK.class);

		//同步联合拍卖财产到款节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_dk_dk.getAssetsId(), taskNode, "paiFu_STCC_DK_DK");

	}

	/**
	 * 将查询的拍辅费查出减除，然后修改
	 * @param paiFu_STCC_DK_DK
	 * @param assetsReSubjectDTO
	 * @param projectId
	 */
	private void updateExpenseRecord(PaiFu_STCC_DK_DK paiFu_STCC_DK_DK, AssetsReSubjectDTO assetsReSubjectDTO, Integer projectId) {
		//查询当前财产程序拍辅费
		ExpenseRecord expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(), projectId, 10007);
		expenseRecord.setCostAmount(expenseRecord.getCostAmount().subtract(paiFu_STCC_DK_DK.getAuxiliaryFee()));
		//修改当前财产程序拍辅费
		expenseRecordService.updateById(expenseRecord);
	}
}
