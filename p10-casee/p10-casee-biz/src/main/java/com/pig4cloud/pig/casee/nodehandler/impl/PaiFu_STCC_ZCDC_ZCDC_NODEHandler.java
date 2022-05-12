package com.pig4cloud.pig.casee.nodehandler.impl;

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

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍辅资产抵偿
		PaiFu_STCC_ZCDC_ZCDC paiFu_stcc_zcdc_zcdc = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_ZCDC_ZCDC.class);
		//查询当前财产拍卖公告节点信息
		TaskNode taskNodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG", taskNode.getTargetId());
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);

		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		//同步联合拍卖财产资产抵偿节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_zcdc_zcdc.getAssetsId(),taskNode,"paiFu_STCC_ZCDC_ZCDC");
		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), paiFu_stcc_zcdc_zcdc.getAssetsId());

		ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());

		addZcdcRepaymentFee(paiFu_stcc_zcdc_zcdc,paiFu_stcc_pmgg_pmgg,projectPaifu,casee,assetsReSubjectDTO);
		// 更新项目总金额
		projectPaifuService.updateProjectAmount(taskNode.getProjectId());

		// 更新拍辅项目总金额
		projectPaifuService.updateRepaymentAmount(taskNode.getProjectId());

		//通过清收移交记录信息查询清收项目id
		LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_stcc_zcdc_zcdc.getAssetsId());
		if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());

			//添加清收回款、费用明细信息
			addZcdcRepaymentFee(paiFu_stcc_zcdc_zcdc,paiFu_stcc_pmgg_pmgg,projectLiqui,casee,assetsReSubjectDTO);
			// 更新清收项目总金额
			projectLiquiService.modifyProjectAmount(liquiTransferRecord.getProjectId());
		}
	}

	//添加回款、费用明细信息
	public void addZcdcRepaymentFee(PaiFu_STCC_ZCDC_ZCDC paiFu_stcc_zcdc_zcdc, PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO) {
		//查询当前财产程序拍辅费
		ExpenseRecord expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(),project.getProjectId(),10007);
		expenseRecord.setCostAmount(expenseRecord.getCostAmount().add(paiFu_stcc_zcdc_zcdc.getAuxiliaryFee()));
		//修改当前财产程序拍辅费
		expenseRecordService.updateById(expenseRecord);

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
		// 遍历财产关联多个债务人
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
