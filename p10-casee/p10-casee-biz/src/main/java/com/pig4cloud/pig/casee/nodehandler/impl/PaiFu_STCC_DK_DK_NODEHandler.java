package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.CustomerSubjectDTO;
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
import java.math.BigDecimal;

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
	private PaymentRecordSubjectReService paymentRecordSubjectReService;
	@Autowired
	private PaymentRecordAssetsReService paymentRecordAssetsReService;
	@Autowired
	private ProjectPaifuService projectPaifuService;
	@Autowired
	private LiquiTransferRecordService liquiTransferRecordService;
	@Autowired
	private ExpenseRecordAssetsReService expenseRecordAssetsReService;
	@Autowired
	CustomerService customerService;
	@Autowired
	TargetService targetService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		//修改资产抵偿环节为跳过
		TaskNode paiFu_stcc_zcdc_zcdc = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getTargetId, taskNode.getTargetId()).eq(TaskNode::getNodeKey, "paiFu_STCC_ZCDC_ZCDC").eq(TaskNode::getNodeAttributes, 400));
		paiFu_stcc_zcdc_zcdc.setStatus(301);
		taskNodeService.updateById(paiFu_stcc_zcdc_zcdc);

		//拍辅到款
		setPaiFuStccDkDk(taskNode);

		//同步联合拍卖财产到款节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode, "paiFu_STCC_DK_DK");
	}

	private void setPaiFuStccDkDk(TaskNode taskNode) {
		PaiFu_STCC_DK_DK paiFu_stcc_dk_dk = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_DK_DK.class);
		//查询当前财产拍卖公告节点信息
		TaskNode taskNodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG", taskNode.getTargetId());
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);

		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		Target target = targetService.getById(taskNode.getTargetId());

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

		ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());

		//添加拍辅回款、费用明细信息
		addDkRepaymentFee(taskNode,paiFu_stcc_dk_dk, paiFu_stcc_pmgg_pmgg, projectPaifu, casee, assetsReSubjectDTO, 2);

		// 更新拍辅项目总金额
		projectPaifuService.updateProjectAmount(taskNode.getProjectId());

		//通过清收移交记录信息查询清收项目id
		LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), target.getGoalId());
		if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());
			//添加清收回款、费用明细信息
			addDkRepaymentFee(taskNode,paiFu_stcc_dk_dk, paiFu_stcc_pmgg_pmgg, projectLiqui, casee, assetsReSubjectDTO, 1);
			// 更新清收项目总金额
			projectLiquiService.modifyProjectAmount(liquiTransferRecord.getProjectId());
		}

		String json = JsonUtils.objectToJson(paiFu_stcc_dk_dk);

		taskNode.setFormData(json);

		//节点信息更新
		this.taskNodeService.updateById(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode, null);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);
	}


	//添加回款、费用明细信息
	public void addDkRepaymentFee(TaskNode taskNode,PaiFu_STCC_DK_DK paiFu_stcc_dk_dk, PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO, Integer type) {
		CustomerSubjectDTO customerSubjectDTO=new CustomerSubjectDTO();
		if (paiFu_stcc_dk_dk.getIdentityCard()!=null){
			customerSubjectDTO.setUnifiedIdentity(paiFu_stcc_dk_dk.getIdentityCard());
		}
		customerSubjectDTO.setNatureType(0);
		customerSubjectDTO.setPhone(paiFu_stcc_dk_dk.getPhone());
		customerSubjectDTO.setName(paiFu_stcc_dk_dk.getFinalPayer());
		customerSubjectDTO.setProjectId(taskNode.getProjectId());
		customerSubjectDTO.setCaseeId(taskNode.getCaseeId());
		customerSubjectDTO.setCustomerType(30000);
		//添加客户信息
		customerService.saveCustomer(customerSubjectDTO);

		ExpenseRecord expenseRecord=null;
		if (paiFu_stcc_dk_dk.getAuxiliaryFee().compareTo(BigDecimal.ZERO)!=0){//判断拍辅费是否大于0
			//查询当前财产程序拍辅费
			expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(), project.getProjectId(), 10007);
			if (expenseRecord!=null){//如果当前拍辅费没有还完则修改拍辅金额
				expenseRecord.setCostAmount(expenseRecord.getCostAmount().add(paiFu_stcc_dk_dk.getAuxiliaryFee()));
				//修改当前财产程序拍辅费
				expenseRecordService.updateById(expenseRecord);
			}else {//如果没有拍辅费或者拍辅费已经还完则添加
				//添加费用产生记录以及其它关联信息
				 expenseRecord = expenseRecordService.addExpenseRecord(paiFu_stcc_dk_dk.getAuxiliaryFee(), paiFu_stcc_dk_dk.getFinalPaymentDate(), project, casee, assetsReSubjectDTO, paiFu_stcc_pmgg_pmgg.getJointAuctionAssetsDTOList(),10007);
			}
			if (type.equals(2)) {//拍辅
				paiFu_stcc_dk_dk.setPaiFuExpenseRecordId(expenseRecord.getExpenseRecordId());
			} else {//清收
				paiFu_stcc_dk_dk.setLiQuiExpenseRecordId(expenseRecord.getExpenseRecordId());

				//添加到款记录以及其它关联信息
				PaymentRecord paymentRecord = paymentRecordService.addPaymentRecord(paiFu_stcc_dk_dk.getAmountReceived(), paiFu_stcc_dk_dk.getFinalPaymentDate(), project, casee, assetsReSubjectDTO, paiFu_stcc_pmgg_pmgg.getJointAuctionAssetsDTOList(), 200, 20003);

				paiFu_stcc_dk_dk.setLiQuiPaymentRecordId(paymentRecord.getPaymentRecordId());
			}
		}
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		PaiFu_STCC_DK_DK paiFu_STCC_DK_DK = JsonUtils.jsonToPojo(node.getFormData(), PaiFu_STCC_DK_DK.class);

		if (paiFu_STCC_DK_DK!=null){
			Target target = targetService.getById(taskNode.getTargetId());

			//查询当前财产关联债务人信息
			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(node.getProjectId(), node.getCaseeId(), target.getGoalId());

			//查询拍辅项目
			ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());

			//将之前的拍辅费减除并修改
			updateExpenseRecord(paiFu_STCC_DK_DK, assetsReSubjectDTO.getAssetsReId(), projectPaifu.getProjectId());

			//通过清收移交记录信息查询清收项目id
			LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), target.getGoalId());

			if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细

				//查询清收项目
				ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());

				//将之前的拍辅费减除并修改
				updateExpenseRecord(paiFu_STCC_DK_DK, assetsReSubjectDTO.getAssetsReId(), projectLiqui.getProjectId());

				//删除到款的到款信息
				paymentRecordService.removeById(paiFu_STCC_DK_DK.getLiQuiPaymentRecordId());

				//删除回款记录财产关联信息
				paymentRecordAssetsReService.remove(new LambdaQueryWrapper<PaymentRecordAssetsRe>()
						.eq(PaymentRecordAssetsRe::getPaymentRecordId, paiFu_STCC_DK_DK.getLiQuiPaymentRecordId()));

				//删除到款信息关联债务人
				paymentRecordSubjectReService.remove(new LambdaQueryWrapper<PaymentRecordSubjectRe>()
						.eq(PaymentRecordSubjectRe::getPaymentRecordId, paiFu_STCC_DK_DK.getLiQuiPaymentRecordId()));
			}
		}

		//拍辅到款
		setPaiFuStccDkDk(taskNode);

		//同步联合拍卖财产到款节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode, "paiFu_STCC_DK_DK");

	}

	/**
	 * 将查询的拍辅费查出减除，然后修改
	 *
	 * @param paiFu_STCC_DK_DK
	 * @param assetsReId
	 * @param projectId
	 */
	private void updateExpenseRecord(PaiFu_STCC_DK_DK paiFu_STCC_DK_DK, Integer assetsReId, Integer projectId) {
		//查询当前财产程序拍辅费
		ExpenseRecord expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReId, projectId, 10007);
		if (expenseRecord!=null){
			expenseRecord.setCostAmount(expenseRecord.getCostAmount().subtract(paiFu_STCC_DK_DK.getAuxiliaryFee()));
			//修改当前财产程序拍辅费
			expenseRecordService.updateById(expenseRecord);
		}
	}
}
