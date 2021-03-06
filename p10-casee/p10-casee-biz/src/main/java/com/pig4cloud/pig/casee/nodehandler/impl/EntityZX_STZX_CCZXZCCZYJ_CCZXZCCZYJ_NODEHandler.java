package com.pig4cloud.pig.casee.nodehandler.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.dto.AssetsReDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.project.entityzxprocedure.EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	ProjectCaseeReService projectCaseeReService;
	@Autowired
	AssetsLiquiTransferRecordReService assetsLiquiTransferRecordReService;
	@Autowired
	LiquiTransferRecordService liquiTransferRecordService;
	@Autowired
	ProjectLiquiService projectLiquiService;
	@Autowired
	CaseeHandlingRecordsService caseeHandlingRecordsService;
	@Autowired
	SecurityUtilsService securityUtilsService;
	@Autowired
	TargetService targetService;
	@Autowired
	AssetsReService assetsReService;



	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {

		taskNodeService.setTaskDataSubmission(taskNode);
		//资产处置移交
		EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ entityZX_stzx_cczxzcczyj_cczxzcczyj = JsonUtils.jsonToPojo(taskNode.getFormData(), EntityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.class);

		//查询当前项目最后一个立案案件
		Casee implementCasee = projectCaseeReService.getImplementCaseeByProjectId(taskNode.getProjectId());

		Project project = projectLiquiService.getById(taskNode.getProjectId());

		LiquiTransferRecord liquiTransferRecord=new LiquiTransferRecord();
		liquiTransferRecord.setProjectId(taskNode.getProjectId());
		liquiTransferRecord.setNodeId(taskNode.getNodeId());
		liquiTransferRecord.setCaseeId(implementCasee.getCaseeId());
		liquiTransferRecord.setCaseeNumber(implementCasee.getCaseeNumber());
		liquiTransferRecord.setEntrustInsId(project.getInsId());
		liquiTransferRecord.setEntrustOutlesId(project.getOutlesId());
		liquiTransferRecord.setEntrustedInsId(entityZX_stzx_cczxzcczyj_cczxzcczyj.getEntrustedInsId());
		liquiTransferRecord.setEntrustedOutlesId(entityZX_stzx_cczxzcczyj_cczxzcczyj.getEntrustedOutlesId());
		liquiTransferRecord.setHandoverTime(entityZX_stzx_cczxzcczyj_cczxzcczyj.getHandoverTime());
		liquiTransferRecord.setStatus(0);
		liquiTransferRecord.setRemark(entityZX_stzx_cczxzcczyj_cczxzcczyj.getRemark());
		//添加清收移交记录
		liquiTransferRecordService.save(liquiTransferRecord);

		List<AssetsReDTO> assetsReDTOList = entityZX_stzx_cczxzcczyj_cczxzcczyj.getAssetsReDTOList();
		for (AssetsReDTO assetsReDTO : assetsReDTOList) {//添加财产关联清收移交记录信息
			AssetsLiquiTransferRecordRe assetsLiquiTransferRecordRe=new AssetsLiquiTransferRecordRe();
			assetsLiquiTransferRecordRe.setAssetsReId(assetsReDTO.getAssetsReId());
			assetsLiquiTransferRecordRe.setLiquiTransferRecordId(liquiTransferRecord.getLiquiTransferRecordId());
			assetsLiquiTransferRecordReService.save(assetsLiquiTransferRecordRe);

			AssetsRe assetsRe = assetsReService.getById(assetsReDTO.getAssetsReId());
			assetsRe.setStatus(700);
			//修改财产关联表状态为移送中
			assetsReService.updateById(assetsRe);

			//查询该财产程序信息
			Target target = targetService.getOne(new LambdaQueryWrapper<Target>().eq(Target::getCaseeId, assetsReDTO.getCaseeId()).eq(Target::getGoalId, assetsReDTO.getAssetsId()).eq(Target::getGoalType, 20001).eq(Target::getProcedureNature, 4040));

			TaskNode entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ = taskNodeService.getOne(new LambdaQueryWrapper<TaskNode>().eq(TaskNode::getProjectId, assetsReDTO.getProjectId()).eq(TaskNode::getCaseeId, assetsReDTO.getCaseeId()).eq(TaskNode::getTargetId, target.getTargetId())
					.eq(TaskNode::getNodeKey, "entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ"));

			if (!entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getNodeId().equals(taskNode.getNodeId())){
				//修改节点数据
				entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.setFormData(taskNode.getFormData());
				if (entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.getNeedAudit()==1){//需要审核
					entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.setStatus(101);
				}else {
					entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.setStatus(403);
				}
				entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ.setSubmissionStatus(0);
				taskNodeService.updateById(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ);

				//添加案件任务办理记录
				CaseeHandlingRecords caseeHandlingRecords=new CaseeHandlingRecords();
				BeanUtils.copyProperties(entityZX_STZX_CCZXZCCZYJ_CCZXZCCZYJ,caseeHandlingRecords);
				caseeHandlingRecords.setCreateTime(LocalDateTime.now());
				caseeHandlingRecords.setInsId(securityUtilsService.getCacheUser().getInsId());
				caseeHandlingRecords.setOutlesId(securityUtilsService.getCacheUser().getOutlesId());
				caseeHandlingRecords.setSourceId(assetsReDTO.getAssetsId());
				caseeHandlingRecords.setSourceType(0);
				caseeHandlingRecordsService.save(caseeHandlingRecords);
			}
		}
	}
}
