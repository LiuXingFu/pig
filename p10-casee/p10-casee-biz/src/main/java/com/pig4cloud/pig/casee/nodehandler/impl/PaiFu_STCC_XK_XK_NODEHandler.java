package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_XK_XK;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.AssetsService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PaiFu_STCC_XK_XK_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	AssetsService assetsService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {//现勘

		PaiFu_STCC_XK_XK paiFu_stcc_xk_xk = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_XK_XK.class);
		Assets assets = assetsService.getById(paiFu_stcc_xk_xk.getAssetsId());

		TaskNode taskNodeBdcxkrh = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getTargetId, taskNode.getTargetId()).eq(TaskNode::getNodeAttributes, 400).eq(TaskNode::getNodeName, "不动产现勘入户"));

		if (assets.getAssetsType().equals(20201)){
			if (paiFu_stcc_xk_xk.getWhetherHomeInspection()==1){//如果当前财产已现勘入户修改不动产现勘入户节点状态为已跳过
				taskNodeBdcxkrh.setStatus(301);
				taskNodeService.updateById(taskNodeBdcxkrh);
			}
		}else if (assets.getAssetsType().equals(20202) || assets.getAssetsType().equals(20203) || assets.getAssetsType().equals(20205)) {//如果当前财产是动产则直接修改不动产现勘入户节点状态为已跳过
			taskNodeBdcxkrh.setStatus(301);
			taskNodeService.updateById(taskNodeBdcxkrh);
		}

		//发送拍辅任务消息
		taskNodeService.sendPaifuTaskMessage(taskNode);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);
	}

}
