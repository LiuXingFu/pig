package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMJGSDQK_PMJGSDQK;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaiFu_STCC_PMJGSDQK_PMJGSDQK_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;


	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		setPMJGSDQK(taskNode);

	}

	private void setPMJGSDQK(TaskNode taskNode) {
		//拍辅拍卖结果送达情况
		PaiFu_STCC_PMJGSDQK_PMJGSDQK paiFu_stcc_pmjgsdqk_pmjgsdqk = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_PMJGSDQK_PMJGSDQK.class);

		//发送拍辅任务消息
		this.taskNodeService.sendPaifuTaskMessage(taskNode);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

		//同步联合拍卖财产拍卖结果送达情况节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_pmjgsdqk_pmjgsdqk.getAssetsId(), taskNode, "paiFu_STCC_PMJGSDQK_PMJGSDQK");
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		setPMJGSDQK(taskNode);
	}
}
