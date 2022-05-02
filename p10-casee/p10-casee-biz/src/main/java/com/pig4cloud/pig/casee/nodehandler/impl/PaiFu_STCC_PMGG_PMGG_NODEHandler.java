package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.dto.paifu.AuctionRecordSaveDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionResultsSaveDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_PMGG_PMGG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaiFu_STCC_PMGG_PMGG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	AuctionRecordService auctionRecordService;


	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍辅拍卖公告
		PaiFu_STCC_PMGG_PMGG paiFu_stcc_pmgg_pmgg = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_PMGG_PMGG.class);
		AuctionRecordSaveDTO auctionRecordSaveDTO=new AuctionRecordSaveDTO();
		BeanUtils.copyProperties(paiFu_stcc_pmgg_pmgg,auctionRecordSaveDTO);
		//添加拍卖记录信息
		auctionRecordService.saveAuctionRecord(auctionRecordSaveDTO);
	}
}
