package com.pig4cloud.pig.casee.nodehandler.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pig4cloud.pig.casee.dto.AuditTargetDTO;
import com.pig4cloud.pig.casee.dto.TaskFlowDTO;
import com.pig4cloud.pig.casee.entity.CaseePerformance;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.CaseePerformanceService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class lIQUI_CASEE_REPAYMENTDETAILS_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	private CaseePerformanceService caseePerformanceService;

	@Override
	public void handlerTaskAudit(TaskFlowDTO taskFlowDTO) {
		//1.根据节点id查询案件绩效考核表
		CaseePerformance caseePerformance = this.caseePerformanceService.getOne(new LambdaQueryWrapper<CaseePerformance>()
				.eq(CaseePerformance::getNodeId, taskFlowDTO.getNodeId())
				.eq(CaseePerformance::getDelFlag, 0));
		if (Objects.nonNull(caseePerformance)) {
			//2.修改计费状态和金额
			caseePerformance.setPerfAmount(taskFlowDTO.getPerformanceAmount());
			caseePerformance.setUserId(taskFlowDTO.getUpdateBy());
			caseePerformance.setPerfStatus(taskFlowDTO.getPerfStatus());
			caseePerformance.setRemark(taskFlowDTO.getDescribed());
			this.caseePerformanceService.updateById(caseePerformance);
		}
	}
}
