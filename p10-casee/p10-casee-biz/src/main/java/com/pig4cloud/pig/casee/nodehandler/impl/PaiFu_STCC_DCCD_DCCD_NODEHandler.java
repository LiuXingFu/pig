package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_DCCD_DCCD;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaiFu_STCC_DCCD_DCCD_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;


	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {//抵偿裁定

		PaiFu_STCC_DCCD_DCCD paiFu_stcc_dccd_dccd = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_DCCD_DCCD.class);

		//发送拍辅任务消息
		this.taskNodeService.sendPaifuTaskMessage(taskNode);

		//同步联合拍卖财产抵偿裁定节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_dccd_dccd.getAssetsId(),taskNode,"paiFu_STCC_DCCD_DCCD");
	}
}
