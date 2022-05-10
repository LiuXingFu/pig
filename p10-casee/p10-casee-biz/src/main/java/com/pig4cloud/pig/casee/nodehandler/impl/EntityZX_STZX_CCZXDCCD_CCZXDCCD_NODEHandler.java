package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXDCCD_CCZXDCCD;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.AssetsReService;
import com.pig4cloud.pig.casee.service.PaymentRecordService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityZX_STZX_CCZXDCCD_CCZXDCCD_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	PaymentRecordService paymentRecordService;
	@Autowired
	AssetsReService assetsReService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//抵偿裁定
		EntityZX_STZX_CCZXDCCD_CCZXDCCD entityZX_stzx_cczxdccd_cczxdccd = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXDCCD_CCZXDCCD.class);

		AssetsRe assetsReDTO = assetsReService.getOne(new LambdaQueryWrapper<AssetsRe>().eq(AssetsRe::getProjectId, taskNode.getProjectId()).eq(AssetsRe::getCaseeId, taskNode.getCaseeId()).eq(AssetsRe::getAssetsId, entityZX_stzx_cczxdccd_cczxdccd.getAssetsId()));
		//修改当前财产关联表状态
		assetsReDTO.setStatus(500);
		assetsReService.updateById(assetsReDTO);
	}
}
