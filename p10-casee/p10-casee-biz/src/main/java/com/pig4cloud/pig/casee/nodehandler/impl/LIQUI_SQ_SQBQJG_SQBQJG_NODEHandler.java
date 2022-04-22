package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.casee.dto.AuditTargetDTO;
import com.pig4cloud.pig.casee.dto.CaseeLiquiModifyStatusDTO;
import com.pig4cloud.pig.casee.dto.CaseeModifyDTO;
import com.pig4cloud.pig.casee.entity.TaskNode;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SQ.LiQui_SQ_SQBQJG_SQBQJG;
import com.pig4cloud.pig.casee.entity.project.liquiprocedure.SSBQ.LiQui_SSBQ_SSBQBQJG_SSBQBQJG;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.CaseeLiquiService;
import com.pig4cloud.pig.casee.service.TaskNodeService;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class LIQUI_SQ_SQBQJG_SQBQJG_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;

	@Autowired
	CaseeLiquiService caseeLiquiService;

	@Override
	public void handlerTaskSubmit(TaskNode taskNode) {
		//任务数据提交 保存程序、财产和行为
		taskNodeService.setTaskDataSubmission(taskNode);

		LiQui_SQ_SQBQJG_SQBQJG liQui_sq_sqbqjg_sqbqjg = JsonUtils.jsonToPojo(taskNode.getFormData(), LiQui_SQ_SQBQJG_SQBQJG.class);

		// 更新案件状态
		CaseeLiquiModifyStatusDTO caseeLiquiModifyStatusDTO = new CaseeLiquiModifyStatusDTO();
		caseeLiquiModifyStatusDTO.setStatus(3);
		caseeLiquiModifyStatusDTO.setProjectId(taskNode.getProjectId());
		caseeLiquiModifyStatusDTO.setCaseeId(taskNode.getCaseeId());
		caseeLiquiModifyStatusDTO.setChangeTime(liQui_sq_sqbqjg_sqbqjg.getCaseClosedTime());
		caseeLiquiService.modifyCaseeStatusById(caseeLiquiModifyStatusDTO);
	}
}
