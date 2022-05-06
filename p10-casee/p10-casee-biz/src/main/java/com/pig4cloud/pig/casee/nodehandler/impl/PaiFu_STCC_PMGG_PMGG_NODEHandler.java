package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.dto.AssetsReDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionRecordSaveDTO;
import com.pig4cloud.pig.casee.entity.AuctionRecord;
import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMGG_PMGG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.AuctionRecordService;
import com.pig4cloud.pig.casee.service.CaseeHandlingRecordsService;
import com.pig4cloud.pig.casee.service.TargetService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class PaiFu_STCC_PMGG_PMGG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	AuctionRecordService auctionRecordService;
	@Autowired
	TargetService targetService;
	@Autowired
	CaseeHandlingRecordsService caseeHandlingRecordsService;
	@Autowired
	SecurityUtilsService securityUtilsService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍辅拍卖公告
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_PMGG_PMGG.class);
		AuctionRecordSaveDTO auctionRecordSaveDTO=new AuctionRecordSaveDTO();
		BeanUtils.copyProperties(paiFu_stcc_pmgg_pmgg,auctionRecordSaveDTO);
		//添加拍卖记录信息
		Integer auctionRecordId = auctionRecordService.saveAuctionRecord(auctionRecordSaveDTO);

		paiFu_stcc_pmgg_pmgg.setAuctionRecordId(auctionRecordId);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);

		List<AssetsReDTO> assetsReIdList = paiFu_stcc_pmgg_pmgg.getAssetsReIdList();
		setJointAuctionRelatedOperations(taskNode, paiFu_stcc_pmgg_pmgg, assetsReIdList);
		String json = JsonUtils.objectToJson(paiFu_stcc_pmgg_pmgg);

		taskNode.setFormData(json);

		//修改节点信息
		taskNodeService.updateById(taskNode);

	}

	private void setJointAuctionRelatedOperations(TaskNode taskNode, PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg, List<AssetsReDTO> assetsReIdList) {
		for (AssetsReDTO assetsReDTO : assetsReIdList) {
			//根据项目、案件、财产id查询程序信息
			Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getProjectId, taskNode.getProjectId()).eq(Target::getCaseeId, taskNode.getCaseeId()).eq(Target::getGoalId, assetsReDTO.getAssetsId()).eq(Target::getGoalType, 20001).eq(Target::getProcedureNature,6060));

			//根据程序id、节点key查询节点信息
			TaskNode taskNodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG",target.getTargetId());

			if (!taskNodePmgg.getNodeId().equals(taskNode.getNodeId())) {
				taskNodePmgg.setFormData(taskNode.getFormData());
				if (taskNodePmgg.getNeedAudit()==1){//需要审核
					taskNodePmgg.setStatus(101);
				}else {
					taskNodePmgg.setStatus(403);
				}
				taskNodePmgg.setSubmissionStatus(taskNode.getSubmissionStatus());

				String json = JsonUtils.objectToJson(paiFu_stcc_pmgg_pmgg);

				taskNodePmgg.setFormData(json);

				//修改节点信息
				taskNodeService.updateById(taskNodePmgg);

				//发送拍辅任务消息
				taskNodeService.sendPaifuTaskMessage(taskNodePmgg);

				taskNodeService.setTaskDataSubmission(taskNodePmgg);

				//添加案件任务办理记录
				caseeHandlingRecordsService.addCaseeHandlingRecords(assetsReDTO.getAssetsId(),taskNodePmgg,paiFu_stcc_pmgg_pmgg.getAuctionType());
			}
		}
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {

		//拍辅拍卖公告
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_PMGG_PMGG.class);
		AuctionRecordSaveDTO auctionRecordSaveDTO=new AuctionRecordSaveDTO();
		BeanUtils.copyProperties(paiFu_stcc_pmgg_pmgg,auctionRecordSaveDTO);

		AuctionRecord auctionRecord = auctionRecordService.getById(paiFu_stcc_pmgg_pmgg.getAuctionRecordId());

		//拍卖开始时间
		auctionRecord.setAuctionStartTime(paiFu_stcc_pmgg_pmgg.getAuctionStartTime());

		//拍卖结束时间
		auctionRecord.setAuctionEndTime(paiFu_stcc_pmgg_pmgg.getAuctionEndTime());

		//起拍价
		auctionRecord.setStartingPrice(paiFu_stcc_pmgg_pmgg.getStartingPrice());

		//公告发布时间
		auctionRecord.setAnnouncementStartTime(paiFu_stcc_pmgg_pmgg.getAnnouncementStartTime());

		//拍卖链接
		auctionRecord.setAuctionLink(paiFu_stcc_pmgg_pmgg.getAuctionLink());

		//拍卖平台
		auctionRecord.setAuctionPlatform(paiFu_stcc_pmgg_pmgg.getAuctionPlatform());

		//备注
		auctionRecord.setRemark(paiFu_stcc_pmgg_pmgg.getRemark());

		//附件
		auctionRecord.setAppendix(paiFu_stcc_pmgg_pmgg.getAppendix());

		auctionRecordService.updateById(auctionRecord);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);

		taskNodeService.setTaskDataSubmission(taskNode);

		List<AssetsReDTO> assetsReIdList = paiFu_stcc_pmgg_pmgg.getAssetsReIdList();

		setJointAuctionRelatedOperations(taskNode, paiFu_stcc_pmgg_pmgg, assetsReIdList);

	}
}
