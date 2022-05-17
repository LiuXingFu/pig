package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXXK_CCZXXK;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.AssetsService;
import com.pig4cloud.pig.casee.service.PaymentRecordService;
import com.pig4cloud.pig.casee.service.TargetService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityZX_STZX_CCZXXK_CCZXXK_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	PaymentRecordService paymentRecordService;
	@Autowired
	AssetsService assetsService;
	@Autowired
	TargetService targetService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//现勘
		EntityZX_STZX_CCZXXK_CCZXXK entityZX_stzx_cczxxk_cczxxk = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXXK_CCZXXK.class);

		Target target = targetService.getById(taskNode.getTargetId());

		Assets assets = assetsService.getById(target.getGoalId());

		TaskNode taskNodeBdcxkrh = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getTargetId, taskNode.getTargetId()).eq(TaskNode::getNodeAttributes, 400).eq(TaskNode::getNodeName, "不动产现勘入户"));

		if (assets.getAssetsType().equals(20201)){
			if (entityZX_stzx_cczxxk_cczxxk.getWhetherHomeInspection()==1){//如果当前财产已现勘入户修改不动产现勘入户节点状态为已跳过
				taskNodeBdcxkrh.setStatus(301);
				taskNodeService.updateById(taskNodeBdcxkrh);
			}
		}else if (assets.getAssetsType().equals(20202) || assets.getAssetsType().equals(20203) || assets.getAssetsType().equals(20205)) {//如果当前财产是动产则直接修改不动产现勘入户节点状态为已跳过
			taskNodeBdcxkrh.setStatus(301);
			taskNodeService.updateById(taskNodeBdcxkrh);
		}
	}
}
