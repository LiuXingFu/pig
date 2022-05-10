package com.pig4cloud.pig.casee.nodehandler.impl;
import com.pig4cloud.pig.casee.dto.paifu.AuctionRecordSaveDTO;
import com.pig4cloud.pig.casee.entity.AuctionRecord;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXPMGG_CCZXPMGG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityZX_STZX_CCZXPMGG_CCZXPMGG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	AssetsReLiquiService assetsReLiquiService;
	@Autowired
	AuctionRecordService auctionRecordService;


	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		//拍卖公告
		EntityZX_STZX_CCZXPMGG_CCZXPMGG entityZX_stzx_cczxpmgg_cczxpmgg = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXPMGG_CCZXPMGG.class);
		AuctionRecordSaveDTO auctionRecordSaveDTO=new AuctionRecordSaveDTO();
		BeanUtils.copyProperties(entityZX_stzx_cczxpmgg_cczxpmgg,auctionRecordSaveDTO);
		//添加拍卖记录信息
		AuctionRecord auctionRecord = auctionRecordService.saveAuctionRecord(auctionRecordSaveDTO);

		entityZX_stzx_cczxpmgg_cczxpmgg.setAuctionRecordId(auctionRecord.getAuctionRecordId());

		entityZX_stzx_cczxpmgg_cczxpmgg.setAuctionId(auctionRecord.getAuctionId());

		//修改节点信息
		taskNodeService.updateById(taskNode);

		taskNodeService.setTaskDataSubmission(taskNode);

	}
}
