package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.PaymentRecordAddDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXZCDC_CCZXZCDC;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.AssetsReVO;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class EntityZX_STZX_CCZXZCDC_CCZXZCDC_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	private ExpenseRecordService expenseRecordService;
	@Autowired
	AssetsReLiquiService assetsReLiquiService;
	@Autowired
	private CaseeLiquiService caseeLiquiService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private PaymentRecordSubjectReService paymentRecordSubjectReService;
	@Autowired
	private PaymentRecordService paymentRecordService;
	@Autowired
	ExpenseRecordAssetsReService expenseRecordAssetsReService;
	@Autowired
	TargetService targetService;
	@Autowired
	PaymentRecordAssetsReService paymentRecordAssetsReService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		addLiQiuStccZcdcZcdc(taskNode);
	}

	private void addLiQiuStccZcdcZcdc(TaskNode taskNode) {
		//资产抵偿
		EntityZX_STZX_CCZXZCDC_CCZXZCDC entityZX_stzx_cczxzcdc_cczxzcdc = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXZCDC_CCZXZCDC.class);
		//查询案件信息
		Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

		Target target = targetService.getById(taskNode.getTargetId());

		//查询当前财产关联债务人信息
		AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
		if (entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee().compareTo(BigDecimal.ZERO)!=0){//判断拍辅费是否大于0
			//查询当前财产程序拍辅费
			ExpenseRecord expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(), taskNode.getProjectId(), 10007);
			if (expenseRecord!=null){//如果当前拍辅费没有还完则修改拍辅金额
				expenseRecord.setCostAmount(expenseRecord.getCostAmount().add(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee()));
				//修改当前财产程序拍辅费
				expenseRecordService.updateById(expenseRecord);
				entityZX_stzx_cczxzcdc_cczxzcdc.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			}else {//如果没有拍辅费或者拍辅费已经还完则添加
				//添加费用明细记录以及其它关联信息
				ExpenseRecord addExpenseRecord = expenseRecordService.addExpenseRecord(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee(), entityZX_stzx_cczxzcdc_cczxzcdc.getSettlementDate(), projectLiqui, casee, assetsReSubjectDTO, null, 10007);

				entityZX_stzx_cczxzcdc_cczxzcdc.setExpenseRecordId(addExpenseRecord.getExpenseRecordId());
			}
		}

		//添加拍辅金额时需修改项目总金额
		projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee()));
		projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());

		//添加抵偿回款信息以及分配款项记录
		addCompensateDistribute(entityZX_stzx_cczxzcdc_cczxzcdc,taskNode,projectLiqui,casee,assetsReSubjectDTO);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

	}

	//添加抵偿回款信息以及分配款项记录
	public void addCompensateDistribute(EntityZX_STZX_CCZXZCDC_CCZXZCDC entityZX_stzx_cczxzcdc_cczxzcdc,TaskNode taskNode,ProjectLiqui projectLiqui,Casee casee,AssetsReSubjectDTO assetsReSubjectDTO){
		//添加抵偿回款信息
		PaymentRecord paymentRecord = paymentRecordService.addPaymentRecord(entityZX_stzx_cczxzcdc_cczxzcdc.getCompensationAmount(), entityZX_stzx_cczxzcdc_cczxzcdc.getSettlementDate(),1,projectLiqui, casee, assetsReSubjectDTO, null, 400, 40001);

		entityZX_stzx_cczxzcdc_cczxzcdc.setPaymentRecordId(paymentRecord.getPaymentRecordId());

		//抵偿分配记录
		List<PaymentRecordAddDTO> paymentRecordList = entityZX_stzx_cczxzcdc_cczxzcdc.getPaymentRecordList();
		List<PaymentRecordSubjectRe> paymentRecordSubjectRes = new ArrayList<>();
		for (PaymentRecordAddDTO record : paymentRecordList) {
			record.setPaymentType(400);
			record.setPaymentDate(paymentRecord.getPaymentDate());
			record.setFatherId(paymentRecord.getPaymentRecordId());
			record.setCaseeId(casee.getCaseeId());
			record.setCaseeNumber(casee.getCaseeNumber());
			record.setStatus(1);
			//添加抵偿分配记录
			paymentRecordService.save(record);
			List<AssetsReVO> assetsReList = record.getAssetsReList();

			// 遍历财产关联多个债务人
			for (Subject subject:assetsReSubjectDTO.getSubjectList()){
				PaymentRecordSubjectRe paymentRecordSubject=new PaymentRecordSubjectRe();
				paymentRecordSubject.setPaymentRecordId(record.getPaymentRecordId());
				paymentRecordSubject.setSubjectId(subject.getSubjectId());
				//添加抵偿分配记录与主体关联信息
				paymentRecordSubjectRes.add(paymentRecordSubject);
			}

			for (AssetsReVO assetsReVO : assetsReList) {
				if (assetsReVO.getAssetsReId()!=null){
					//添加到款信息关联财产
					PaymentRecordAssetsRe PaymentRecordAssets=new PaymentRecordAssetsRe();
					PaymentRecordAssets.setPaymentRecordId(record.getPaymentRecordId());
					PaymentRecordAssets.setAssetsReId(assetsReVO.getAssetsReId());
					paymentRecordAssetsReService.save(PaymentRecordAssets);
				}
			}

			//如果费用已经还完则修改状态
			ExpenseRecord expenseRecordUpdate = expenseRecordService.getById(record.getExpenseRecordId());
			if (expenseRecordUpdate.getCostAmount().compareTo(record.getPaymentAmount().add(record.getPaymentSumAmount()))==0){
				expenseRecordUpdate.setExpenseRecordId(record.getExpenseRecordId());
				expenseRecordUpdate.setStatus(1);
				//修改明细记录状态
				expenseRecordService.updateById(expenseRecordUpdate);
			}
		}
		//添加抵偿分配信息关联债务人
		paymentRecordSubjectReService.saveBatch(paymentRecordSubjectRes);

		projectLiqui.getProjectLiQuiDetail().setRepaymentAmount(projectLiqui.getProjectLiQuiDetail().getRepaymentAmount().add(entityZX_stzx_cczxzcdc_cczxzcdc.getCompensationAmount()));
		projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
		//修改项目总金额以及回款总金额
		projectLiquiService.updateById(projectLiqui);

		String json = JsonUtils.objectToJson(entityZX_stzx_cczxzcdc_cczxzcdc);

		taskNode.setFormData(json);

		//节点信息更新
		this.taskNodeService.updateById(taskNode);
	}


	@Override
	@Transactional
	public void handlerTaskMakeUp(TaskNode taskNode) {
		//补录的节点数据
		EntityZX_STZX_CCZXZCDC_CCZXZCDC makeUpEntityZX_stzx_cczxzcdc_cczxzcdc = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXZCDC_CCZXZCDC.class);

		//查询补录之前的数据
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		EntityZX_STZX_CCZXZCDC_CCZXZCDC entityZX_stzx_cczxzcdc_cczxzcdc = JsonUtils.jsonToPojo(node.getFormData(), EntityZX_STZX_CCZXZCDC_CCZXZCDC.class);

		if (entityZX_stzx_cczxzcdc_cczxzcdc!=null) {
			//如果补录到款拍辅费用发生改变
			if (makeUpEntityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee().compareTo(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee())!=0) {
				//拍辅费要是已分配，修改已回款记录作废，修改拍辅费、修改项目回款总金额以及项目总金额
				expenseRecordService.updateExpenseRecordProjectAmount(entityZX_stzx_cczxzcdc_cczxzcdc.getExpenseRecordId(),entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee(),makeUpEntityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee(),taskNode.getProjectId(),100);
			}

			//如果补录抵偿金额发生改变
			if (makeUpEntityZX_stzx_cczxzcdc_cczxzcdc.getCompensationAmount().compareTo(entityZX_stzx_cczxzcdc_cczxzcdc.getCompensationAmount())!=0) {

				//查询资产抵偿回款记录
				PaymentRecord paymentRecord = paymentRecordService.getById(entityZX_stzx_cczxzcdc_cczxzcdc.getPaymentRecordId());

				//查询资产抵偿分配记录
				List<PaymentRecord> paymentRecordList = paymentRecordService.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getFatherId, paymentRecord.getPaymentRecordId()).eq(PaymentRecord::getStatus, 1));
				for (PaymentRecord record : paymentRecordList) {
					record.setStatus(2);
					//修改费用明细记录状态
					ExpenseRecord expenseRecord = expenseRecordService.getById(record.getExpenseRecordId());
					if (expenseRecord.getStatus() != 2) {
						expenseRecord.setStatus(0);
						expenseRecordService.updateById(expenseRecord);
					}
				}
				paymentRecordService.updateBatchById(paymentRecordList);

				ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
				if (paymentRecord.getStatus()==1){
					projectLiqui.getProjectLiQuiDetail().setRepaymentAmount(projectLiqui.getProjectLiQuiDetail().getRepaymentAmount().subtract(paymentRecord.getPaymentAmount()));
					projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
					//修改项目回款总金额
					projectLiquiService.updateById(projectLiqui);
				}

				//修改资产抵偿状态为作废
				paymentRecord.setStatus(2);
				paymentRecordService.updateById(paymentRecord);

				//查询案件信息
				Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

				Target target = targetService.getById(taskNode.getTargetId());

				//查询当前财产关联债务人信息
				AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

				//添加抵偿回款信息以及分配款项记录
				addCompensateDistribute(makeUpEntityZX_stzx_cczxzcdc_cczxzcdc,taskNode,projectLiqui,casee,assetsReSubjectDTO);
			}
			//更新json数据
			taskNodeService.setTaskDataSubmission(taskNode);
		}else {
			addLiQiuStccZcdcZcdc(taskNode);
		}
	}
}
