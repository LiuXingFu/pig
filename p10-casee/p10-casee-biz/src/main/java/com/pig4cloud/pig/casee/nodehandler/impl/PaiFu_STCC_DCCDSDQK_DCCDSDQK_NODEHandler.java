package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaiFu_STCC_DCCDSDQK_DCCDSDQK_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;


	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {//抵偿裁定送达情况
		setPaiFuStccDccdsdqkDccdsdqk(taskNode);

	}

	private void setPaiFuStccDccdsdqkDccdsdqk(TaskNode taskNode) {
		//发送拍辅任务消息
		this.taskNodeService.sendPaifuTaskMessage(taskNode, null);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

		//同步联合拍卖财产抵偿裁定送达情况节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode,"paiFu_STCC_DCCDSDQK_DCCDSDQK");
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		setPaiFuStccDccdsdqkDccdsdqk(taskNode);
	}
}
