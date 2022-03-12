package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.entity.Casee;
import com.pig4cloud.pig.casee.entity.ExpenseRecord;
import com.pig4cloud.pig.casee.entity.ExpenseRecordSubjectRe;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXPMJG_CCZXPMJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
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

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍卖结果
		EntityZX_STZX_CCZXPMJG_CCZXPMJG entityZX_stzx_cczxpmjg_cczxpmjg = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXPMJG_CCZXPMJG.class);
		if (entityZX_stzx_cczxpmjg_cczxpmjg.getAuctionResults()==1){//流拍
			List<TaskNode> entityZX_stzx_cczxpmgg_cczxpmgg = taskNodeService.list(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXPMGG_CCZXPMGG").eq(TaskNode::getCaseeId, taskNode.getCaseeId()).eq(TaskNode::getProjectId, taskNode.getProjectId()).eq(TaskNode::getTargetId,taskNode.getTargetId()));
			if (entityZX_stzx_cczxpmgg_cczxpmgg.size()>0){
				//查询最后一条拍卖公告信息
				TaskNode taskNodePmgg = entityZX_stzx_cczxpmgg_cczxpmgg.get(entityZX_stzx_cczxpmgg_cczxpmgg.size()-1);
				//复制拍卖公告节点
				TaskNode auctionAnnouncement=new TaskNode();
				BeanUtils.copyProperties(taskNodePmgg,auctionAnnouncement);
				auctionAnnouncement.setFormData(null);
				auctionAnnouncement.setStatus(0);
				auctionAnnouncement.setSubmissionStatus(0);
				auctionAnnouncement.setNodeType(0);
				auctionAnnouncement.setActReProcdefId(null);
				auctionAnnouncement.setTrusteeStatus(null);
				auctionAnnouncement.setTrusteeType(null);
				auctionAnnouncement.setCreateTime(LocalDateTime.now());
				//拿到拍卖公告id最后一位数+1
				Integer nodeId = Integer.valueOf(taskNodePmgg.getNodeId().substring(taskNodePmgg.getNodeId().lastIndexOf("_") + 1, taskNodePmgg.getNodeId().length()))+1;
				StringBuilder stringBuilderPmgg=new StringBuilder(taskNodePmgg.getNodeId());
				auctionAnnouncement.setNodeId(stringBuilderPmgg.replace(taskNodePmgg.getNodeId().lastIndexOf("_") + 1, taskNodePmgg.getNodeId().length(), nodeId.toString()).toString());
				taskNodeService.save(auctionAnnouncement);
			}

			//复制拍卖结果节点
			TaskNode auctionResults=new TaskNode();
			BeanUtils.copyProperties(taskNode,auctionResults);
			auctionResults.setFormData(null);
			auctionResults.setStatus(0);
			auctionResults.setSubmissionStatus(0);
			auctionResults.setNodeType(0);
			auctionResults.setActReProcdefId(null);
			auctionResults.setTrusteeStatus(null);
			auctionResults.setTrusteeType(null);
			auctionResults.setCreateTime(LocalDateTime.now());
			//拿到拍卖结果id最后一位数+1
			Integer auctionResultsId = Integer.valueOf(auctionResults.getNodeId().substring(auctionResults.getNodeId().lastIndexOf("_") + 1, auctionResults.getNodeId().length()))+1;
			StringBuilder stringBuilderPmjg=new StringBuilder(auctionResults.getNodeId());
			auctionResults.setNodeId(stringBuilderPmjg.replace(auctionResults.getNodeId().lastIndexOf("_") + 1, auctionResults.getNodeId().length(), auctionResultsId.toString()).toString());
			taskNodeService.save(auctionResults);
		}else {//成交
			//查询案件信息
			Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());
			//查询当前财产关联债务人信息
			List<Subject> subjects = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), entityZX_stzx_cczxpmjg_cczxpmjg.getAssetsId());
			String name=null;
			for (Subject subject : subjects) {
				if (name!=null){
					name+=","+subject.getName();
				}else {
					name=subject.getName();
				}
			}

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
			expenseRecord.setSubjectName(name);
			expenseRecord.setCompanyCode(projectLiqui.getCompanyCode());
			expenseRecord.setCostType(10007);
			expenseRecordService.save(expenseRecord);

			//添加费用产生明细关联主体信息
			ExpenseRecordSubjectRe expenseRecordSubjectRe=new ExpenseRecordSubjectRe();
			for (Subject subject : subjects) {
				expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
				expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
				expenseRecordSubjectReService.save(expenseRecordSubjectRe);
			}
		}
	}
}
