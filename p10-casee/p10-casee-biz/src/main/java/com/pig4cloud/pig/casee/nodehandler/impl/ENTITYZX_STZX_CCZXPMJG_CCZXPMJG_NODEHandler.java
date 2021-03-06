package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.CustomerSubjectDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionResultsSaveDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMGG_PMGG;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXPMJG_CCZXPMJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ENTITYZX_STZX_CCZXPMJG_CCZXPMJG_NODEHandler extends TaskNodeHandler {

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
	private AuctionRecordService auctionRecordService;
	@Autowired
	ExpenseRecordAssetsReService expenseRecordAssetsReService;
	@Autowired
	TargetService targetService;
	@Autowired
	CustomerService customerService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍卖结果
		EntityZX_STZX_CCZXPMJG_CCZXPMJG entityZX_stzx_cczxpmjg_cczxpmjg = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXPMJG_CCZXPMJG.class);
		//查询当前财产拍卖公告节点信息
		TaskNode nodePmgg = taskNodeService.queryLastTaskNode("entityZX_STZX_CCZXPMGG_CCZXPMGG", taskNode.getTargetId());
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(nodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);

		//拍卖记录信息
		AuctionResultsSaveDTO auctionResultsSaveDTO=new AuctionResultsSaveDTO();
		BeanUtils.copyProperties(entityZX_stzx_cczxpmjg_cczxpmjg,auctionResultsSaveDTO);
		auctionResultsSaveDTO.setAuctionId(paiFu_stcc_pmgg_pmgg.getAuctionId());
		auctionResultsSaveDTO.setAuctionRecordId(paiFu_stcc_pmgg_pmgg.getAuctionRecordId());
		auctionResultsSaveDTO.setResultsTime(entityZX_stzx_cczxpmjg_cczxpmjg.getClosingDate());
		auctionResultsSaveDTO.setResultsType(entityZX_stzx_cczxpmjg_cczxpmjg.getAuctionResults());
		auctionResultsSaveDTO.setAuctionPeopleNumber(entityZX_stzx_cczxpmjg_cczxpmjg.getNumberOfParticipants());
		auctionResultsSaveDTO.setAppendix(entityZX_stzx_cczxpmjg_cczxpmjg.getAppendixFile());
		//修改拍卖记录信息
		auctionRecordService.saveAuctionResults(auctionResultsSaveDTO);

		if (entityZX_stzx_cczxpmjg_cczxpmjg.getAuctionResults()==20){//流拍
			//查询最后一条拍卖公告信息
			TaskNode entityZX_stzx_cczxpmgg_cczxpmgg = taskNodeService.queryLastTaskNode("entityZX_STZX_CCZXPMGG_CCZXPMGG",taskNode.getTargetId());
			if (entityZX_stzx_cczxpmgg_cczxpmgg!=null){
				//修改当前节点状态为删除
				entityZX_stzx_cczxpmgg_cczxpmgg.setDelFlag("1");
				taskNodeService.updateById(entityZX_stzx_cczxpmgg_cczxpmgg);
				//复制拍卖公告节点
				taskNodeService.copyTaskNode(entityZX_stzx_cczxpmgg_cczxpmgg);
			}
			//修改当前节点状态为删除
			taskNode.setDelFlag("1");
			taskNodeService.updateById(taskNode);
			//复制拍卖结果节点
			taskNodeService.copyTaskNode(taskNode);

		}else {//成交
			if (entityZX_stzx_cczxpmjg_cczxpmjg.getAuxiliaryFee().compareTo(BigDecimal.ZERO) != 0) {//判断拍辅费是否大于0
				//查询案件信息
				Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

				Target target = targetService.getById(taskNode.getTargetId());

				//查询当前财产关联债务人信息
				AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), target.getGoalId());

				ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());

				//添加费用明细记录以及其它关联信息
				expenseRecordService.addExpenseRecord(entityZX_stzx_cczxpmjg_cczxpmjg.getAuxiliaryFee(),entityZX_stzx_cczxpmjg_cczxpmjg.getClosingDate(),projectLiqui,casee,assetsReSubjectDTO,null,10007);

				//修改项目总金额
				projectLiquiService.modifyProjectAmount(projectLiqui.getProjectId());
			}

			//添加成交客户信息
			CustomerSubjectDTO customerSubjectDTO=new CustomerSubjectDTO();
			customerSubjectDTO.setName(entityZX_stzx_cczxpmjg_cczxpmjg.getBuyer());
			customerSubjectDTO.setPhone(entityZX_stzx_cczxpmjg_cczxpmjg.getPhone());
			customerSubjectDTO.setCustomerType(20000);
			customerSubjectDTO.setProjectId(taskNode.getProjectId());
			customerSubjectDTO.setCaseeId(taskNode.getCaseeId());
			if (entityZX_stzx_cczxpmjg_cczxpmjg.getIdentityCard()!=null){
				customerSubjectDTO.setUnifiedIdentity(entityZX_stzx_cczxpmjg_cczxpmjg.getIdentityCard());
			}
			customerService.saveCustomer(customerSubjectDTO);
		}
	}
}
