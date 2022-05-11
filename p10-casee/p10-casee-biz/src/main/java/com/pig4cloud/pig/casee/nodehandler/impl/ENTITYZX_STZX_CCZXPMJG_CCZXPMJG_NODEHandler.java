package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionResultsSaveDTO;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.ExpenseRecord;
import com.pig4cloud.pig.casee.entity.ExpenseRecordSubjectRe;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMGG_PMGG;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXPMJG_CCZXPMJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

@Component
public class ENTITYZX_STZX_CCZXPMJG_CCZXPMJG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
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
	private AuctionRecordService auctionRecordService;

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
				//复制拍卖公告节点
				taskNodeService.copyTaskNode(entityZX_stzx_cczxpmgg_cczxpmgg);
			}
			//复制拍卖结果节点
			taskNodeService.copyTaskNode(taskNode);

		}else {//成交
			//查询案件信息
			Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());
			//查询当前财产关联债务人信息
			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), entityZX_stzx_cczxpmjg_cczxpmjg.getAssetsId());


			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(taskNode.getProjectId());
			projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(entityZX_stzx_cczxpmjg_cczxpmjg.getAuxiliaryFee()));
			projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
			//修改项目总金额
			projectLiquiService.updateById(projectLiqui);

			//添加费用明细记录
			ExpenseRecord expenseRecord=new ExpenseRecord();
			expenseRecord.setCostAmount(entityZX_stzx_cczxpmjg_cczxpmjg.getAuxiliaryFee());
			expenseRecord.setCostIncurredTime(entityZX_stzx_cczxpmjg_cczxpmjg.getClosingDate());
			expenseRecord.setProjectId(taskNode.getProjectId());
			expenseRecord.setCaseeId(taskNode.getCaseeId());
			expenseRecord.setCaseeNumber(casee.getCaseeNumber());
			expenseRecord.setStatus(0);
			expenseRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
			expenseRecord.setCompanyCode(projectLiqui.getCompanyCode());
			expenseRecord.setCostType(10007);
			expenseRecordService.save(expenseRecord);

			//添加费用产生明细关联主体信息
			List<ExpenseRecordSubjectRe> expenseRecordSubjectRes = new ArrayList<>();
			for (Subject subject:assetsReSubjectDTO.getSubjectList()){
				ExpenseRecordSubjectRe expenseRecordSubjectRe=new ExpenseRecordSubjectRe();
				expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
				expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
				expenseRecordSubjectRes.add(expenseRecordSubjectRe);
			}
			expenseRecordSubjectReService.saveBatch(expenseRecordSubjectRes);

		}
	}
}
