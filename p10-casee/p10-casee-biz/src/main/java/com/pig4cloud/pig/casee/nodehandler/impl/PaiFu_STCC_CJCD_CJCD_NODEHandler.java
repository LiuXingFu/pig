package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_CJCD_CJCD;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaiFu_STCC_CJCD_CJCD_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;


	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {//成交裁定
		setPaiFuStccCjcdCjcd(taskNode);
	}

	private void setPaiFuStccCjcdCjcd(TaskNode taskNode) {
		PaiFu_STCC_CJCD_CJCD paiFu_stcc_cjcd_cjcd = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_CJCD_CJCD.class);

		//发送拍辅任务消息
		this.taskNodeService.sendPaifuTaskMessage(taskNode);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

		//同步联合拍卖财产成交裁定节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_cjcd_cjcd.getAssetsId(),taskNode,"paiFu_STCC_CJCD_CJCD");
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		setPaiFuStccCjcdCjcd(taskNode);
	}
}
