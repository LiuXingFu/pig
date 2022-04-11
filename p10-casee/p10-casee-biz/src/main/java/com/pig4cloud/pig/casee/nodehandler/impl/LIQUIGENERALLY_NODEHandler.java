package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.entity.CaseeHandlingRecords;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.CaseeHandlingRecordsService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

@Component
public class LIQUIGENERALLY_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	SecurityUtilsService securityUtilsService;
	@Autowired
	CaseeHandlingRecordsService caseeHandlingRecordsService;

	/**
	 * 更新程序
	 * @param taskNode
	 */
	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		//添加案件任务办理记录
		CaseeHandlingRecords caseeHandlingRecords=new CaseeHandlingRecords();
		BeanUtils.copyProperties(taskNode,caseeHandlingRecords);
		caseeHandlingRecords.setCreateTime(LocalDateTime.now());
		caseeHandlingRecords.setInsId(securityUtilsService.getCacheUser().getInsId());
		caseeHandlingRecords.setOutlesId(securityUtilsService.getCacheUser().getOutlesId());
		caseeHandlingRecordsService.save(caseeHandlingRecords);

		taskNodeService.setTaskDataSubmission(taskNode);
	}

}
