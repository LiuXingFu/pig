package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.AssetsReDTO;
import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_DCCD_DCCD;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMGG_PMGG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.AssetsReService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class PaiFu_STCC_DCCD_DCCD_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	AssetsReService assetsReService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {//抵偿裁定

		setPaiFuStccDccdDccd(taskNode);
	}

	private void setPaiFuStccDccdDccd(TaskNode taskNode) {
		PaiFu_STCC_DCCD_DCCD paiFu_stcc_dccd_dccd = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_DCCD_DCCD.class);

		//查询当前财产拍卖公告节点信息
		TaskNode taskNodePmgg = taskNodeService.queryLastTaskNode("paiFu_STCC_PMGG_PMGG", taskNode.getTargetId());
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNodePmgg.getFormData(), PaiFu_STCC_PMGG_PMGG.class);
		for (JointAuctionAssetsDTO jointAuctionAssetsDTO : paiFu_stcc_pmgg_pmgg.getJointAuctionAssetsDTOList()) {
			//修改当前财产关联表状态
			jointAuctionAssetsDTO.setStatus(500);
			assetsReService.updateById(jointAuctionAssetsDTO);
		}

		//发送拍辅任务消息
		this.taskNodeService.sendPaifuTaskMessage(taskNode);

		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

		//同步联合拍卖财产抵偿裁定节点数据
		taskNodeService.synchronizeJointAuctionTaskNode(paiFu_stcc_dccd_dccd.getAssetsId(),taskNode,"paiFu_STCC_DCCD_DCCD");
	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		setPaiFuStccDccdDccd(taskNode);
	}
}
