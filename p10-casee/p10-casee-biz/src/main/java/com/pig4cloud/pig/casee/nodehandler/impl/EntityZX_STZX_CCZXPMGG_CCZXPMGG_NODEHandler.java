package com.pig4cloud.pig.casee.nodehandler.impl;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
import com.pig4cloud.pig.casee.dto.paifu.AuctionRecordSaveDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.AuctionRecord;
import com.pig4cloud.pig.casee.entity.Target;
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
	@Autowired
	AssetsReService assetsReService;
	@Autowired
	AssetsService assetsService;
	@Autowired
	TargetService targetService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		Target target = targetService.getById(taskNode.getTargetId());

		//拍卖公告
		EntityZX_STZX_CCZXPMGG_CCZXPMGG entityZX_stzx_cczxpmgg_cczxpmgg = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXPMGG_CCZXPMGG.class);
		AssetsRe assetsRe = assetsReService.getOne(new LambdaQueryWrapper<AssetsRe>().eq(AssetsRe::getAssetsId, target.getGoalId()).eq(AssetsRe::getProjectId, taskNode.getProjectId()).eq(AssetsRe::getCaseeId, taskNode.getCaseeId()));
		AuctionRecordSaveDTO auctionRecordSaveDTO=new AuctionRecordSaveDTO();
		JointAuctionAssetsDTO jointAuctionAssetsDTO=new JointAuctionAssetsDTO();
		jointAuctionAssetsDTO.setAssetsReId(assetsRe.getAssetsReId());
		auctionRecordSaveDTO.getJointAuctionAssetsDTOList().add(jointAuctionAssetsDTO);
		auctionRecordSaveDTO.setAuctionTitle(assetsService.getById(assetsRe.getAssetsId()).getAssetsName());
		BeanUtils.copyProperties(entityZX_stzx_cczxpmgg_cczxpmgg,auctionRecordSaveDTO);
		//添加拍卖记录信息
		AuctionRecord auctionRecord = auctionRecordService.saveAuctionRecord(auctionRecordSaveDTO);

		entityZX_stzx_cczxpmgg_cczxpmgg.setAuctionRecordId(auctionRecord.getAuctionRecordId());

		entityZX_stzx_cczxpmgg_cczxpmgg.setAuctionId(auctionRecord.getAuctionId());

		String json = JsonUtils.objectToJson(entityZX_stzx_cczxpmgg_cczxpmgg);

		taskNode.setFormData(json);

		//修改节点信息
		taskNodeService.updateById(taskNode);

		taskNodeService.setTaskDataSubmission(taskNode);

	}

	@Override
	public void handlerTaskMakeUp(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
	}
}
