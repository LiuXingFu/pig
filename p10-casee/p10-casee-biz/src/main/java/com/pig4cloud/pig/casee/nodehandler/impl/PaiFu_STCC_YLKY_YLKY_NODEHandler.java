package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.LeadTheWay;
import com.pig4cloud.pig.casee.entity.LeadTheWayActualLookSamplerRe;
import com.pig4cloud.pig.casee.entity.LeadTheWayUserRe;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ActualLookSamplerListDetail;
import com.pig4cloud.pig.casee.entity.paifuentity.detail.ListOfSamplers;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_YLKY_YLKY;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.LeadTheWayActualLookSamplerReService;
import com.pig4cloud.pig.casee.service.LeadTheWayService;
import com.pig4cloud.pig.casee.service.LeadTheWayUserReService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PaiFu_STCC_YLKY_YLKY_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	LeadTheWayService leadTheWayService;
	@Autowired
	LeadTheWayActualLookSamplerReService leadTheWayActualLookSamplerReService;
	@Autowired
	LeadTheWayUserReService leadTheWayUserReService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		//拍辅引领看样
		PaiFu_STCC_YLKY_YLKY paiFu_stcc_ylky_ylky = setPaiFuStccYlkyYlky(taskNode);

		//同步联合拍卖财产引领看样节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_ylky_ylky.getAssetsId(),taskNode,"paiFu_STCC_YLKY_YLKY");
	}

	private PaiFu_STCC_YLKY_YLKY setPaiFuStccYlkyYlky(TaskNode taskNode) {
		PaiFu_STCC_YLKY_YLKY paiFu_stcc_ylky_ylky = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_YLKY_YLKY.class);

		List<LeadTheWayActualLookSamplerRe> leadTheWayActualLookSamplerReList=new ArrayList<>();
		List<LeadTheWayUserRe> leadTheWayUserReList=new ArrayList<>();
		for (ActualLookSamplerListDetail actualLookSamplerListDetail : paiFu_stcc_ylky_ylky.getSamplerList()) {
			LeadTheWay leadTheWay=new LeadTheWay();
			BeanUtils.copyProperties(actualLookSamplerListDetail,leadTheWay);
			leadTheWayService.save(leadTheWay);
			actualLookSamplerListDetail.setLeadTheWayId(leadTheWay.getLeadTheWayId());

			//看样人员名单信息
			List<ListOfSamplers> subjectList = actualLookSamplerListDetail.getSubjectList();
			for (ListOfSamplers listOfSamplers : subjectList) {
				Subject subject=new Subject();
				BeanUtils.copyProperties(listOfSamplers,subject);

				//根据手机号添加或修改主体信息
				R<Subject> phoneBySaveOrUpdateById = remoteSubjectService.getPhoneAndUnifiedIdentityBySaveOrUpdateById(subject, SecurityConstants.FROM);
				Integer subjectId = Integer.valueOf(phoneBySaveOrUpdateById.getData().getSubjectId().toString());

				LeadTheWayActualLookSamplerRe leadTheWayActualLookSamplerRe=new LeadTheWayActualLookSamplerRe();
				BeanUtils.copyProperties(listOfSamplers,leadTheWayActualLookSamplerRe);
				leadTheWayActualLookSamplerRe.setLeadTheWayId(leadTheWay.getLeadTheWayId());
				leadTheWayActualLookSamplerRe.setSubjectId(subjectId);
				leadTheWayActualLookSamplerReList.add(leadTheWayActualLookSamplerRe);
			}

			//带看人员信息
			List<Integer> userIdList = actualLookSamplerListDetail.getUserIdList();
			for (Integer integer : userIdList) {
				LeadTheWayUserRe leadTheWayUserRe=new LeadTheWayUserRe();
				leadTheWayUserRe.setLeadTheWayId(leadTheWay.getLeadTheWayId());
				leadTheWayUserRe.setUserId(integer);
				leadTheWayUserReList.add(leadTheWayUserRe);
			}
		}

		taskNodeService.updateById(taskNode);

		taskNodeService.setTaskDataSubmission(taskNode);

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);

		leadTheWayActualLookSamplerReService.saveBatch(leadTheWayActualLookSamplerReList);
		leadTheWayUserReService.saveBatch(leadTheWayUserReList);
		return paiFu_stcc_ylky_ylky;
	}


	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		TaskNode node = this.taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getNodeId, taskNode.getNodeId()));

		PaiFu_STCC_YLKY_YLKY paiFu_STCC_YLKY_YLKY = JsonUtils.jsonToPojo(node.getFormData(), PaiFu_STCC_YLKY_YLKY.class);

		List<Integer> collect = paiFu_STCC_YLKY_YLKY.getSamplerList().stream().map(ActualLookSamplerListDetail::getLeadTheWayId).collect(Collectors.toList());

		leadTheWayService.removeByIds(collect);

		leadTheWayActualLookSamplerReService.remove(new LambdaQueryWrapper<LeadTheWayActualLookSamplerRe>().in(LeadTheWayActualLookSamplerRe::getLeadTheWayId, collect));

		leadTheWayUserReService.remove(new LambdaQueryWrapper<LeadTheWayUserRe>().in(LeadTheWayUserRe::getLeadTheWayId, collect));

		//拍辅引领看样
		PaiFu_STCC_YLKY_YLKY paiFu_stcc_ylky_ylky = setPaiFuStccYlkyYlky(taskNode);

		//同步联合拍卖财产引领看样节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_ylky_ylky.getAssetsId(),taskNode,"paiFu_STCC_YLKY_YLKY");
	}
}
