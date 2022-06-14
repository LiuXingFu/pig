package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMGG_PMGG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.AssetsReService;
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
	@Autowired
	AssetsReService assetsReService;


	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {//成交裁定
		//修改抵偿裁定环节为跳过
		TaskNode paiFu_stcc_dccd_dccd = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getTargetId, taskNode.getTargetId()).eq(TaskNode::getNodeKey, "paiFu_STCC_DCCD_DCCD").eq(TaskNode::getNodeAttributes, 400));
		paiFu_stcc_dccd_dccd.setStatus(301);
		taskNodeService.updateById(paiFu_stcc_dccd_dccd);

		//修改抵偿裁定送达情况环节为跳过
		TaskNode paiFu_stcc_dccdsdqk_dccdsdqk = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getTargetId, taskNode.getTargetId()).eq(TaskNode::getNodeKey, "paiFu_STCC_DCCDSDQK_DCCDSDQK").eq(TaskNode::getNodeAttributes, 400));
		paiFu_stcc_dccdsdqk_dccdsdqk.setStatus(301);
		taskNodeService.updateById(paiFu_stcc_dccdsdqk_dccdsdqk);

		setPaiFuStccCjcdCjcd(taskNode);
	}

	private void setPaiFuStccCjcdCjcd(TaskNode taskNode) {
		//查询当前财产拍卖公告节点信息
		TaskNode taskNodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG", taskNode.getTargetId());
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);
		AssetsRe assetsRe = new AssetsRe();
		for (JointAuctionAssetsDTO jointAuctionAssetsDTO : paiFu_stcc_pmgg_pmgg.getJointAuctionAssetsDTOList()) {
			//修改当前财产关联表状态
			assetsRe.setAssetsReId(jointAuctionAssetsDTO.getAssetsReId());
			assetsRe.setStatus(500);
			assetsReService.updateById(assetsRe);
		}

		//发送拍辅任务消息
		this.taskNodeService.sendPaifuTaskMessage(taskNode, null);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

		//同步联合拍卖财产成交裁定节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(taskNode, "paiFu_STCC_CJCD_CJCD");
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		setPaiFuStccCjcdCjcd(taskNode);
	}
}
