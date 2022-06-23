package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.Target;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.AssetsReService;
import com.pig4cloud.pig.casee.service.PaymentRecordService;
import com.pig4cloud.pig.casee.service.TargetService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityZX_STZX_CCZXCJCD_CCZXCJCD_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	PaymentRecordService paymentRecordService;
	@Autowired
	AssetsReService assetsReService;
	@Autowired
	TargetService targetService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		addData(taskNode);
	}

	private void addData(TaskNode taskNode){
		Target target = targetService.getById(taskNode.getTargetId());
		taskNodeService.setTaskDataSubmission(taskNode);
		AssetsRe assetsReDTO = assetsReService.getOne(new LambdaQueryWrapper<AssetsRe>().eq(AssetsRe::getProjectId, taskNode.getProjectId()).eq(AssetsRe::getCaseeId, taskNode.getCaseeId()).eq(AssetsRe::getAssetsId, target.getGoalId()));
		//修改当前财产关联表状态
		assetsReDTO.setStatus(500);
		assetsReService.updateById(assetsReDTO);
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		addData(taskNode);
	}
}
