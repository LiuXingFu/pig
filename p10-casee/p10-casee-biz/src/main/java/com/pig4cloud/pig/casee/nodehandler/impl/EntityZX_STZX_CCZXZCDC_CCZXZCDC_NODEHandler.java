package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.PaymentRecordAddDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXZCDC_CCZXZCDC;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.AssetsReVO;
import com.pig4cloud.pig.casee.vo.PaymentRecordVO;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
		getLiQiuStccZcdcZcdc(taskNode);
	}

	private void getLiQiuStccZcdcZcdc(TaskNode taskNode) {
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
			}else {//如果没有拍辅费或者拍辅费已经还完则添加
				//添加费用明细记录以及其它关联信息
				expenseRecordService.addExpenseRecord(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee(),entityZX_stzx_cczxzcdc_cczxzcdc.getSettlementDate(),projectLiqui,casee,assetsReSubjectDTO,null,10007);
			}
		}

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
			record.setCaseeId(taskNode.getCaseeId());
			record.setCaseeNumber(casee.getCaseeNumber());
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

		//添加拍辅金额时需修改项目总金额
		projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee()));
		projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());

		//添加抵偿时需修改项目回款总金额
		projectLiqui.getProjectLiQuiDetail().setRepaymentAmount(projectLiqui.getProjectLiQuiDetail().getRepaymentAmount().add(entityZX_stzx_cczxzcdc_cczxzcdc.getCompensationAmount()));
		projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
		//修改项目总金额以及回款总金额
		projectLiquiService.updateById(projectLiqui);

		String json = JsonUtils.objectToJson(entityZX_stzx_cczxzcdc_cczxzcdc);

		taskNode.setFormData(json);

		//节点信息更新
		this.taskNodeService.updateById(taskNode);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));
		EntityZX_STZX_CCZXZCDC_CCZXZCDC entityZX_stzx_cczxzcdc_cczxzcdc = JsonUtils.jsonToPojo(node.getFormData(), EntityZX_STZX_CCZXZCDC_CCZXZCDC.class);

		if (entityZX_stzx_cczxzcdc_cczxzcdc!=null) {
			Page page=new Page();
			page.setSize(9999);
			IPage<PaymentRecordVO> byPaymentRecordPage = paymentRecordService.getByPaymentRecordPage(page, entityZX_stzx_cczxzcdc_cczxzcdc.getPaymentRecordId());
			for (PaymentRecordVO record : byPaymentRecordPage.getRecords()) {
				ExpenseRecord expenseRecord = expenseRecordService.getById(record.getExpenseRecordId());
				expenseRecord.setStatus(0);
				expenseRecordService.updateById(expenseRecord);

				//删除资产抵偿的分配款项信息
				paymentRecordService.removeById(record.getPaymentRecordId());

				//删除资产抵偿分配款项关联信息
				paymentRecordAssetsReService.remove(new LambdaQueryWrapper<PaymentRecordAssetsRe>()
						.eq(PaymentRecordAssetsRe::getPaymentRecordId, record.getPaymentRecordId()));

				//删除资产抵偿分配款项关联债务人信息
				paymentRecordSubjectReService.remove(new LambdaQueryWrapper<PaymentRecordSubjectRe>()
						.eq(PaymentRecordSubjectRe::getPaymentRecordId, record.getPaymentRecordId()));
			}

			Target target = targetService.getById(taskNode.getTargetId());

			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

			//查询当前财产程序拍辅费
			ExpenseRecord expenseRecord = expenseRecordAssetsReService.queryAssetsReIdExpenseRecord(assetsReSubjectDTO.getAssetsReId(), taskNode.getProjectId(), 10007);
			if (expenseRecord!=null){
				expenseRecord.setCostAmount(expenseRecord.getCostAmount().subtract(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee()));
				//修改当前财产程序拍辅费
				expenseRecordService.updateById(expenseRecord);
			}

			//删除资产抵偿的抵偿信息
			paymentRecordService.removeById(entityZX_stzx_cczxzcdc_cczxzcdc.getPaymentRecordId());

			//删除资产抵偿关联信息
			paymentRecordAssetsReService.remove(new LambdaQueryWrapper<PaymentRecordAssetsRe>()
					.eq(PaymentRecordAssetsRe::getPaymentRecordId, entityZX_stzx_cczxzcdc_cczxzcdc.getPaymentRecordId()));

			//删除资产抵偿关联债务人
			paymentRecordSubjectReService.remove(new LambdaQueryWrapper<PaymentRecordSubjectRe>()
					.eq(PaymentRecordSubjectRe::getPaymentRecordId, entityZX_stzx_cczxzcdc_cczxzcdc.getPaymentRecordId()));

			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());

			//需修改项目总金额
			projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().subtract(entityZX_stzx_cczxzcdc_cczxzcdc.getAuxiliaryFee()));
			projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());

			//修改项目回款总金额
			projectLiqui.getProjectLiQuiDetail().setRepaymentAmount(projectLiqui.getProjectLiQuiDetail().getRepaymentAmount().subtract(entityZX_stzx_cczxzcdc_cczxzcdc.getCompensationAmount()));
			projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
			//修改项目总金额以及回款总金额
			projectLiquiService.updateById(projectLiqui);

		}
		getLiQiuStccZcdcZcdc(taskNode);
	}
}
