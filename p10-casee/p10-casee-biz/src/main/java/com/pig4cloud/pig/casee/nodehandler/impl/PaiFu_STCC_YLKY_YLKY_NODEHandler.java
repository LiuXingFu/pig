package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_YLKY_YLKY;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaiFu_STCC_YLKY_YLKY_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;


	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍辅引领看样
		PaiFu_STCC_YLKY_YLKY paiFu_stcc_ylky_ylky = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_YLKY_YLKY.class);

		//同步联合拍卖财产引领看样节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_ylky_ylky.getAssetsId(),taskNode,"paiFu_STCC_YLKY_YLKY");



	}
}
