package com.pig4cloud.pig.casee.nodehandler.impl;

import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.paifuentity.ProjectPaifu;
import com.pig4cloud.pig.casee.entity.paifuentity.entityzxprocedure.PaiFu_STCC_JGYJ_JGYJ;
import com.pig4cloud.pig.casee.nodehandler.TaskNodeHandler;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaiFu_STCC_JGYJ_JGYJ_NODEHandler extends TaskNodeHandler {

	@Autowired
	TaskNodeService taskNodeService;
	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	private CaseeLiquiService caseeLiquiService;
	@Autowired
	AssetsReLiquiService assetsReLiquiService;
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private ProjectPaifuService projectPaifuService;
	@Autowired
	private ExpenseRecordService expenseRecordService;
	@Autowired
	private ExpenseRecordSubjectReService expenseRecordSubjectReService;
	@Autowired
	private ExpenseRecordAssetsReService expenseRecordAssetsReService;
	@Autowired
	private LiquiTransferRecordService liquiTransferRecordService;

	@Override
	@Transactional
	public void handlerTaskSubmit(TaskNode taskNode) {
		taskNodeService.setTaskDataSubmission(taskNode);
		//拍辅价格依据
		PaiFu_STCC_JGYJ_JGYJ paiFu_stcc_jgyj_jgyj = JsonUtils.jsonToPojo(taskNode.getFormData(), PaiFu_STCC_JGYJ_JGYJ.class);

		//添加定价费用需修改项目总金额
		if (paiFu_stcc_jgyj_jgyj.getPricingManner() != 0) {
			//查询当前财产关联债务人信息
			AssetsReSubjectDTO assetsReSubjectDTO = assetsReLiquiService.queryAssetsSubject(taskNode.getProjectId(), taskNode.getCaseeId(), paiFu_stcc_jgyj_jgyj.getAssetsId());

			//查询案件信息
			Casee casee = caseeLiquiService.getById(taskNode.getCaseeId());

			ProjectPaifu projectPaifu = projectPaifuService.queryById(taskNode.getProjectId());

			//添加拍辅回款、费用明细信息
			addJgyjRepaymentFee(paiFu_stcc_jgyj_jgyj, projectPaifu, casee, assetsReSubjectDTO);

			// 更新项目总金额
			projectPaifuService.updateProjectAmount(taskNode.getProjectId());

			//通过清收移交记录信息查询清收项目id
			LiquiTransferRecord liquiTransferRecord = liquiTransferRecordService.getByPaifuProjectIdAndAssetsId(taskNode.getProjectId(), paiFu_stcc_jgyj_jgyj.getAssetsId());
			if (liquiTransferRecord != null) {//如果当前财产是清收移交过来的财产那么也要添加清收回款、费用产生记录明细
				ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(liquiTransferRecord.getProjectId());

				projectLiqui.getProjectLiQuiDetail().setProjectAmount(projectLiqui.getProjectLiQuiDetail().getProjectAmount().add(paiFu_stcc_jgyj_jgyj.getPricingFee()));
				projectLiqui.setProjectLiQuiDetail(projectLiqui.getProjectLiQuiDetail());
				//修改清收项目总金额
				projectLiquiService.updateById(projectLiqui);
				//添加清收回款、费用明细信息
				addJgyjRepaymentFee(paiFu_stcc_jgyj_jgyj, projectLiqui, casee, assetsReSubjectDTO);
			}
		}
	}

	//添加回款、费用明细信息
	public void addJgyjRepaymentFee(PaiFu_STCC_JGYJ_JGYJ paiFu_stcc_jgyj_jgyj, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO) {
		//添加定价费用明细记录
		ExpenseRecord expenseRecord = new ExpenseRecord();
		expenseRecord.setCostAmount(paiFu_stcc_jgyj_jgyj.getPricingFee());
		expenseRecord.setCostIncurredTime(paiFu_stcc_jgyj_jgyj.getPricingDate());
		expenseRecord.setProjectId(project.getProjectId());
		expenseRecord.setCaseeId(casee.getCaseeId());
		expenseRecord.setCaseeNumber(casee.getCaseeNumber());
		expenseRecord.setStatus(0);
		expenseRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		expenseRecord.setCompanyCode(project.getCompanyCode());
		expenseRecord.setCostType(10006);
		expenseRecordService.save(expenseRecord);

		//循环当前拍卖公告联合拍卖财产信息
		ExpenseRecordAssetsRe expenseRecordAssetsRe = new ExpenseRecordAssetsRe();
		expenseRecordAssetsRe.setAssetsReId(assetsReSubjectDTO.getAssetsReId());
		expenseRecordAssetsRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
		// 添加费用产生记录财产关联信息
		expenseRecordAssetsReService.save(expenseRecordAssetsRe);

		List<ExpenseRecordSubjectRe> expenseRecordSubjectReList = new ArrayList<>();
		for (Subject subject : assetsReSubjectDTO.getSubjectList()) {
			ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
			expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
			expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordSubjectReList.add(expenseRecordSubjectRe);
		}
		//添加费用产生明细关联主体信息
		expenseRecordSubjectReService.saveBatch(expenseRecordSubjectReList);
	}
}
