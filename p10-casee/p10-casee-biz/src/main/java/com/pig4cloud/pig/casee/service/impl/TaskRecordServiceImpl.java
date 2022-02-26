/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.casee.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.CaseeOrTargetTaskFlowDTO;
import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.entity.TaskRecord;
import com.pig4cloud.pig.casee.mapper.TaskRecordMapper;
import com.pig4cloud.pig.casee.service.TaskRecordService;
import com.pig4cloud.pig.casee.vo.AgentMatterVO;
import com.pig4cloud.pig.common.core.constant.CaseeOrTargetTaskFlowConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.ObjectTransitionEntityUtils;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 标的表
 *
 * @author yy
 * @date 2021-09-15 10:03:23
 */
@Service
public class TaskRecordServiceImpl extends ServiceImpl<TaskRecordMapper, TaskRecord> implements TaskRecordService {
	@Autowired
	private SecurityUtilsService securityUtilsService;

	@Override
	public IPage<AgentMatterVO> taskRecordList(Page page, TaskFlowDTO taskFlowDTO) {
		return this.baseMapper.taskRecordList(page,SecurityUtils.getUser().getId(),taskFlowDTO);
	}

	@Override
	@Transactional
	public Boolean addTaskRecord(Object objectDTO, String taskFlowName) {
		//获取当前机构网点信息
		PigUser cacheUser = securityUtilsService.getCacheUser();

		ObjectTransitionEntityUtils objectTransitionEntityUtils=new ObjectTransitionEntityUtils();
		//办理任务数据DTO
		TaskFlowDTO taskFlowDTO= null;
		//案件或标的任务DTO
		CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO=null;
		//任务记录实体
		TaskRecord taskRecord = new TaskRecord();
		//提交或委托数据说明
		String described=null;

		try {
			if (taskFlowName.equals(CaseeOrTargetTaskFlowConstants.CASEEORTARGET_OBJECT)){
				caseeOrTargetTaskFlowDTO = objectTransitionEntityUtils.readValueMap(objectDTO, CaseeOrTargetTaskFlowDTO.class);
				if (caseeOrTargetTaskFlowDTO.getDescribed()!=null){
					described=caseeOrTargetTaskFlowDTO.getDescribed();
				}
			}else if (taskFlowName.equals(CaseeOrTargetTaskFlowConstants.TASK_OBJECT)){
				taskFlowDTO = objectTransitionEntityUtils.readValueMap(objectDTO, TaskFlowDTO.class);
				if (taskFlowDTO.getDescribed()!=null){
					described=taskFlowDTO.getDescribed();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		BeanCopyUtil.copyBean(objectDTO,taskRecord);
		taskRecord.setCreateBy(null);
		taskRecord.setCreateTime(null);
		taskRecord.setUpdateTime(null);
		taskRecord.setUpdateBy(null);
		taskRecord.setSubmitInsId(cacheUser.getInsId());
		taskRecord.setSubmitOutlesId(cacheUser.getOutlesId());
		taskRecord.setSubmitId(cacheUser.getId());

		if (taskFlowName.equals(CaseeOrTargetTaskFlowConstants.TASK_OBJECT)){
			if (taskRecord.getStatus()==600){//修改委托列表数据
				//添加委托任务时，查询任务记录有没有未委托数据(审核数据)
				List<TaskRecord> list = this.list(new LambdaQueryWrapper<TaskRecord>().eq(TaskRecord::getNodeId, taskFlowDTO.getNodeId()).eq(TaskRecord::getCanEntrust, 0));
				if (list.size()<=0){
					//如果没有任务记录则添加一条审核任务
					taskRecord.setCanEntrust(0);
					this.save(taskRecord);
				}

				// 新增一条委托任务记录
				if (taskFlowDTO!=null){
					taskRecord.setTrusteeId(taskFlowDTO.getCommissionUserId());
					taskRecord.setTrusteeInsId(taskFlowDTO.getCommissionInsId());
					taskRecord.setTrusteeOutlesId(taskFlowDTO.getCommissionOutlesId());
				}
				taskRecord.setCanEntrust(1);
				return this.save(taskRecord);

			}else if (taskRecord.getStatus()==101){//提交任务时 修改委托列表任务状态
				//查询当前所有委托任务并且委托状态为委托中
				List<TaskRecord> taskRecordList = this.list(new LambdaQueryWrapper<TaskRecord>().eq(TaskRecord::getNodeId,taskFlowDTO.getNodeId()).eq(TaskRecord::getStatus,600).eq(TaskRecord::getCanEntrust,1).eq(TaskRecord::getTrusteeStatus,0));
				if (taskRecordList.size()>0){
					for (TaskRecord record : taskRecordList) {
						record.setStatus(taskRecord.getStatus());
						//如果当前任务提交人是委托人则把处理人设置成当前用户
						if (record.getTrusteeId().equals(cacheUser.getId())){
							record.setHandlerId(cacheUser.getId());
						}
					}
					this.updateBatchById(taskRecordList);
				}

				//修改审核任务记录数据
				TaskRecord record = this.getOne(new LambdaQueryWrapper<TaskRecord>().eq(TaskRecord::getNodeId, taskFlowDTO.getNodeId()).eq(TaskRecord::getStatus, 600).eq(TaskRecord::getCanEntrust, 0));
				if (record!=null){//添加任务记录审核人
					record.setAuditorId(taskFlowDTO.getAuditorIdList().get(0));
					record.setAuditorInsId(cacheUser.getInsId());
					record.setAuditorOutlesId(cacheUser.getOutlesId());
					record.setSubmitInsId(cacheUser.getInsId());
					record.setSubmitOutlesId(cacheUser.getOutlesId());
					record.setSubmitId(cacheUser.getId());
					record.setStatus(taskFlowDTO.getStatus());
					record.setTrusteeStatus(taskFlowDTO.getTrusteeStatus());
					this.updateById(record);
				}

				if (taskRecordList.size()>0||record!=null){
					return true;
				}
			}
		}

		//任务审核成功或者驳回,查询当前任务所有记录，修改之前任务状态
		List<TaskRecord> taskRecordList = this.list(new LambdaQueryWrapper<TaskRecord>().eq(TaskRecord::getNodeId,taskRecord.getNodeId()).eq(TaskRecord::getTrusteeStatus,0).and(i -> i.eq(TaskRecord::getStatus, 101).or().eq(TaskRecord::getStatus, 600)));
		if (taskRecordList.size()>0){
			for (TaskRecord record : taskRecordList) {
				//如果当前任务不是委托任务并且当前审核任务没有委托 添加处理人为当前审核人
				if (record.getCanEntrust()==0&&record.getTrusteeType()==0){
					record.setHandlerId(cacheUser.getId());
				}
				//如果当前任务是委托任务并且委托人是当前审核人时 添加处理人为当前审核人
				if (record.getCanEntrust()==1&&record.getTrusteeId().equals(cacheUser.getId())){
					record.setHandlerId(cacheUser.getId());
				}
				record.setStatus(taskRecord.getStatus());
				//设置审核人信息
				record.setAuditorId(cacheUser.getId());
				record.setAuditorOutlesId(cacheUser.getOutlesId());
				record.setAuditorInsId(cacheUser.getInsId());
				record.setDescribed(described);
			}
			return this.updateBatchById(taskRecordList);
		}else {//当任务记录表没有当前任务数据时 添加任务记录数据
			if (taskFlowName.equals(CaseeOrTargetTaskFlowConstants.CASEEORTARGET_OBJECT)){//如果是案件或标的任务 添加任务记录
				//案件任务审核人集合（可能存在多个）
				List<Integer> caseeOrTargetAuditorList = caseeOrTargetTaskFlowDTO.getCaseeOrTargetAuditorList();
				List<TaskRecord> taskRecords=new ArrayList<>();
				for (Integer integer : caseeOrTargetAuditorList) {
					taskRecord.setDescribed(described);
					taskRecord.setAuditorId(integer);
					taskRecords.add(taskRecord);
				}
				return this.saveBatch(taskRecords);
			}else {//如果是任务办理任务 添加任务记录
				return this.save(taskRecord);
			}
		}
	}
}
