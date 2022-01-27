package com.pig4cloud.pig.casee.config;


import com.pig4cloud.pig.casee.dto.CaseeOrTargetTaskFlowDTO;
import com.pig4cloud.pig.casee.service.CaseeBizLiquiService;
import com.pig4cloud.pig.casee.service.CaseeOutlesDealReService;
import com.pig4cloud.pig.casee.service.TargetBizLiquiService;
import com.pig4cloud.pig.casee.utils.SpringUtils;
import com.pig4cloud.pig.common.core.constant.CaseeOrTargetTaskFlowConstants;
import com.pig4cloud.pig.common.security.service.PigUser;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.impl.persistence.entity.VariableInstance;

import java.util.Map;

public class CaseeOrTargetExecutionListener implements ExecutionListener {
	@Override
	public void notify(DelegateExecution delegateExecution) {
//		CaseeBizLiquiService caseeBizLiquiService = SpringUtils.getObject(CaseeBizLiquiService.class);
//		TargetBizLiquiService targetBizLiquiService = SpringUtils.getObject(TargetBizLiquiService.class);
//
//		Map<String, VariableInstance> variableInstances = delegateExecution.getParent().getVariableInstances();
//		//获取审核提交的数据
//		CaseeOrTargetTaskFlowDTO caseeOrTargetTaskFlowDTO = (CaseeOrTargetTaskFlowDTO)variableInstances.get(CaseeOrTargetTaskFlowConstants.CASEEORTARGET_OBJECT).getCachedValue();
//
//		//如果审核任务是案件开启、结案、暂缓、中止
//		if (caseeOrTargetTaskFlowDTO.getNodeType()==100||caseeOrTargetTaskFlowDTO.getNodeType()==101||caseeOrTargetTaskFlowDTO.getNodeType()==102||caseeOrTargetTaskFlowDTO.getNodeType()==103){
//			if (caseeOrTargetTaskFlowDTO.getStatus()==111){//驳回
//				caseeBizLiquiService.confirmCaseStatus(caseeOrTargetTaskFlowDTO,3);
//			}else if (caseeOrTargetTaskFlowDTO.getStatus()==403){//审核通过
//				caseeBizLiquiService.confirmCaseStatus(caseeOrTargetTaskFlowDTO,2);
//			}
//		}
//
//		//如果审核任务是标的毁损、灭失
//		if (caseeOrTargetTaskFlowDTO.getNodeType()==110||caseeOrTargetTaskFlowDTO.getNodeType()==111){
//			if (caseeOrTargetTaskFlowDTO.getStatus()==111){//驳回
//				targetBizLiquiService.confirmCaseStatus(caseeOrTargetTaskFlowDTO.getTargetId(),3,caseeOrTargetTaskFlowDTO.getDescribed());
//			}else if (caseeOrTargetTaskFlowDTO.getStatus()==403){//审核通过
//				targetBizLiquiService.confirmCaseStatus(caseeOrTargetTaskFlowDTO.getTargetId(),2,caseeOrTargetTaskFlowDTO.getDescribed());
//			}
//		}
	}
}
