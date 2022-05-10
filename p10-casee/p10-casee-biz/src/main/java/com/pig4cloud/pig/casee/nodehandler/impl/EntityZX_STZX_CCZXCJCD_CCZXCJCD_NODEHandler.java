package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXCJCD_CCZXCJCD;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.AssetsReService;
import com.pig4cloud.pig.casee.service.PaymentRecordService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
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

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//成交裁定
		EntityZX_STZX_CCZXCJCD_CCZXCJCD entityZX_stzx_cczxcjcd_cczxcjcd = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXCJCD_CCZXCJCD.class);

		AssetsRe assetsReDTO = assetsReService.getOne(new LambdaQueryWrapper<AssetsRe>().eq(AssetsRe::getProjectId, taskNode.getProjectId()).eq(AssetsRe::getCaseeId, taskNode.getCaseeId()).eq(AssetsRe::getAssetsId, entityZX_stzx_cczxcjcd_cczxcjcd.getAssetsId()));
		//修改当前财产关联表状态
		assetsReDTO.setStatus(500);
		assetsReService.updateById(assetsReDTO);
	}
}
