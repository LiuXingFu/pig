package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
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

import java.math.BigDecimal;
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
	private PaymentRecordSubjectReService paymentRecordSubjectReService;
	@Autowired
	private PaymentRecordAssetsReService paymentRecordAssetsReService;
	@Autowired
	private ExpenseRecordAssetsReService expenseRecordAssetsReService;
	@Autowired
	TargetService targetService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		//拍辅资产抵偿
		getPaiFuStccZcdcZcdc(taskNode);

		//同步联合拍卖财产资产抵偿节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode, "paiFu_STCC_ZCDC_ZCDC");
	}

	private void getPaiFuStccZcdcZcdc(TaskNode taskNode) {
		PaiFu_STCC_ZCDC_ZCDC paiFu_stcc_zcdc_zcdc = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_ZCDC_ZCDC.class);
		//查询当前财产拍卖公告节点信息
		TaskNode taskNodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG", taskNode.getTargetId());

		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);

		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		Target target = targetService.getById(taskNode.getTargetId());

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

		ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());

		addZcdcRepaymentFee(paiFu_stcc_zcdc_zcdc, paiFu_stcc_pmgg_pmgg, projectPaifu, casee, assetsReSubjectDTO, 2);
		// 更新拍辅项目总金额
		projectPaifuService.updateProjectAmount(taskNode.getProjectId());

		// 更新拍辅项目回款总金额
		projectPaifuService.updateRepaymentAmount(taskNode.getProjectId());

		//通过清收移交记录信息查询清收项目id
		LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), target.getGoalId());
		if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());

			//添加清收回款、费用明细信息
			addZcdcRepaymentFee(paiFu_stcc_zcdc_zcdc, paiFu_stcc_pmgg_pmgg, projectLiqui, casee, assetsReSubjectDTO, 1);
			// 更新清收项目总金额
			projectLiquiService.modifyProjectAmount(liquiTransferRecord.getProjectId());
		}

		String json = JsonUtils.objectToJson(paiFu_stcc_zcdc_zcdc);

		taskNode.setFormData(json);

		//节点信息更新
		this.taskNodeService.updateById(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode, null);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);
	}

	//添加回款、费用明细信息
	public void addZcdcRepaymentFee(PaiFu_STCC_ZCDC_ZCDC paiFu_stcc_zcdc_zcdc, PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO, Integer type) {
		if (paiFu_stcc_zcdc_zcdc.getAuxiliaryFee().compareTo(BigDecimal.ZERO)!=0){//判断拍辅费是否大于0
			//查询当前财产程序拍辅费
			ExpenseRecord expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(), project.getProjectId(), 10007);
			if (expenseRecord!=null) {//如果当前拍辅费没有还完则修改拍辅金额
				expenseRecord.setCostAmount(expenseRecord.getCostAmount().add(paiFu_stcc_zcdc_zcdc.getAuxiliaryFee()));
				//修改当前财产程序拍辅费
				expenseRecordService.updateById(expenseRecord);
			}else {//如果没有拍辅费或者拍辅费已经还完则添加
				//添加费用产生记录以及其它关联信息
				expenseRecordService.addExpenseRecord(paiFu_stcc_zcdc_zcdc.getAuxiliaryFee(), paiFu_stcc_zcdc_zcdc.getSettlementDate(), project, casee, assetsReSubjectDTO, paiFu_stcc_pmgg_pmgg.getJointAuctionAssetsDTOList(),10007);
			}
		}

		//添加资产抵偿回款信息以及其它关联信息
		PaymentRecord paymentRecord = paymentRecordService.addPaymentRecord(paiFu_stcc_zcdc_zcdc.getCompensationAmount(), paiFu_stcc_zcdc_zcdc.getSettlementDate(),0, project, casee, assetsReSubjectDTO, paiFu_stcc_pmgg_pmgg.getJointAuctionAssetsDTOList(), 400, 40001);

		if (type.equals(1)) {
			paiFu_stcc_zcdc_zcdc.setLiQuiPaymentRecordId(paymentRecord.getPaymentRecordId());
		} else {
			paiFu_stcc_zcdc_zcdc.setPaiFuPaymentRecordId(paymentRecord.getPaymentRecordId());
		}
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		PaiFu_STCC_ZCDC_ZCDC paiFu_STCC_ZCDC_ZCDC = JsonUtils.jsonToPojo(node.getFormData(), PaiFu_STCC_ZCDC_ZCDC.class);

		if (paiFu_STCC_ZCDC_ZCDC!=null){
			Target target = targetService.getById(taskNode.getTargetId());

			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

			ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());

			updateExpenseRecord(paiFu_STCC_ZCDC_ZCDC, assetsReSubjectDTO.getAssetsReId(), projectPaifu.getProjectId());

			//删除到款的到款信息
			paymentRecordService.removeById(paiFu_STCC_ZCDC_ZCDC.getPaiFuPaymentRecordId());

			//删除回款记录财产关联信息
			paymentRecordAssetsReService.remove(new LambdaQueryWrapper<PaymentRecordAssetsRe>()
					.eq(PaymentRecordAssetsRe::getPaymentRecordId, paiFu_STCC_ZCDC_ZCDC.getPaiFuPaymentRecordId()));

			//删除到款信息关联债务人
			paymentRecordSubjectReService.remove(new LambdaQueryWrapper<PaymentRecordSubjectRe>()
					.eq(PaymentRecordSubjectRe::getPaymentRecordId, paiFu_STCC_ZCDC_ZCDC.getPaiFuPaymentRecordId()));

			//通过清收移交记录信息查询清收项目id
			LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), target.getGoalId());
			if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
				ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());

				updateExpenseRecord(paiFu_STCC_ZCDC_ZCDC, assetsReSubjectDTO.getAssetsReId(), projectLiqui.getProjectId());

				//删除到款的到款信息
				paymentRecordService.removeById(paiFu_STCC_ZCDC_ZCDC.getLiQuiPaymentRecordId());

				//删除回款记录财产关联信息
				paymentRecordAssetsReService.remove(new LambdaQueryWrapper<PaymentRecordAssetsRe>()
						.eq(PaymentRecordAssetsRe::getPaymentRecordId, paiFu_STCC_ZCDC_ZCDC.getLiQuiPaymentRecordId()));

				//删除到款信息关联债务人
				paymentRecordSubjectReService.remove(new LambdaQueryWrapper<PaymentRecordSubjectRe>()
						.eq(PaymentRecordSubjectRe::getPaymentRecordId, paiFu_STCC_ZCDC_ZCDC.getLiQuiPaymentRecordId()));

			}
		}

		//拍辅资产抵偿
		getPaiFuStccZcdcZcdc(taskNode);

		//同步联合拍卖财产资产抵偿节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode, "paiFu_STCC_ZCDC_ZCDC");
	}

	/**
	 * 将查询的拍辅费查出减除，然后修改
	 *
	 * @param paiFu_STCC_ZCDC_ZCDC
	 * @param assetsReId
	 * @param projectId
	 */
	private void updateExpenseRecord(PaiFu_STCC_ZCDC_ZCDC paiFu_STCC_ZCDC_ZCDC, Integer assetsReId, Integer projectId) {
		//查询当前财产程序拍辅费
		ExpenseRecord expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReId, projectId, 10007);
		if (expenseRecord!=null){
			expenseRecord.setCostAmount(expenseRecord.getCostAmount().subtract(paiFu_STCC_ZCDC_ZCDC.getAuxiliaryFee()));
			//修改当前财产程序拍辅费
			expenseRecordService.updateById(expenseRecord);
		}
	}
}
