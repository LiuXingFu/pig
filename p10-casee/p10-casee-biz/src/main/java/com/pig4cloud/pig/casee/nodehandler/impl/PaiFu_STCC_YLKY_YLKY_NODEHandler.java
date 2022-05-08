package com.pig4cloud.pig.casee.nodehandler.impl;

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
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍辅引领看样
		PaiFu_STCC_YLKY_YLKY paiFu_stcc_ylky_ylky = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_YLKY_YLKY.class);

		//同步联合拍卖财产引领看样节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_ylky_ylky.getAssetsId(),taskNode,"paiFu_STCC_YLKY_YLKY");

		List<LeadTheWayActualLookSamplerRe> leadTheWayActualLookSamplerReList=new ArrayList<>();
		List<LeadTheWayUserRe> leadTheWayUserReList=new ArrayList<>();
		for (ActualLookSamplerListDetail actualLookSamplerListDetail : paiFu_stcc_ylky_ylky.getSamplerList()) {
			LeadTheWay leadTheWay=new LeadTheWay();
			BeanUtils.copyProperties(actualLookSamplerListDetail,leadTheWay);
			leadTheWayService.save(leadTheWay);
			//看样人员名单信息
			List<ListOfSamplers> subjectList = actualLookSamplerListDetail.getSubjectList();
			for (ListOfSamplers listOfSamplers : subjectList) {
				Subject subject=new Subject();
				BeanUtils.copyProperties(listOfSamplers,subject);
				subject.setUnifiedIdentity(listOfSamplers.getIdentityCard());

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
		leadTheWayActualLookSamplerReService.saveBatch(leadTheWayActualLookSamplerReList);
		leadTheWayUserReService.saveBatch(leadTheWayUserReList);


	}
}
