package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_KYZBGZ_KYZBGZ;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.SignUpLookLikeService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaiFu_STCC_KYZBGZ_KYZBGZ_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	SignUpLookLikeService signUpLookLikeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;


	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍辅看样准备工作
		PaiFu_STCC_KYZBGZ_KYZBGZ paiFu_stcc_kyzbgz_kyzbgz = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_KYZBGZ_KYZBGZ.class);

		//同步联合拍卖财产看样准备工作节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_kyzbgz_kyzbgz.getAssetsId(),taskNode,"paiFu_STCC_KYZBGZ_KYZBGZ");



	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		super.handlerTaskMakeUp(taskNode);
	}
}
