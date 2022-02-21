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
import com.pig4cloud.pig.casee.entity.ExpenseRecord;
import com.pig4cloud.pig.casee.entity.ExpenseRecordSubjectRe;
import com.pig4cloud.pig.casee.entity.ProjectSubjectRe;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.ProjectLiQuiDetail;
import com.pig4cloud.pig.casee.mapper.ExpenseRecordMapper;
import com.pig4cloud.pig.casee.service.ExpenseRecordService;
import com.pig4cloud.pig.casee.service.ExpenseRecordSubjectReService;
import com.pig4cloud.pig.casee.service.ProjectLiquiService;
import com.pig4cloud.pig.casee.service.ProjectSubjectReService;
import com.pig4cloud.pig.casee.vo.ExpenseRecordVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 费用产生记录表
 *
 * @author Mjh
 * @date 2022-02-17 17:53:07
 */
@Service
public class ExpenseRecordServiceImpl extends ServiceImpl<ExpenseRecordMapper, ExpenseRecord> implements ExpenseRecordService {
	@Autowired
	private ProjectLiquiService projectLiquiService;
	@Autowired
	private ProjectSubjectReService projectSubjectReService;
	@Autowired
	private ExpenseRecordSubjectReService recordSubjectReService;

	@Override
	public IPage<ExpenseRecordVO> getExpenseRecordPage(Page page, ExpenseRecord expenseRecord) {
		return this.baseMapper.getExpenseRecordPage(page,expenseRecord);
	}

	@Override
	public boolean saveExpenseRecordUpdateProject(ExpenseRecord expenseRecord) {
		ExpenseRecord record = this.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getCaseeNumber, expenseRecord.getCaseeNumber()).eq(ExpenseRecord::getCostType, expenseRecord.getCostType()).eq(ExpenseRecord::getStatus,0));
		ProjectLiqui project = projectLiquiService.getByProjectId(expenseRecord.getProjectId());
		expenseRecord.setSubjectName(project.getSubjectPersons());

		if (record!=null){
			record.setCostAmount(expenseRecord.getCostAmount());
			record.setCostIncurredTime(expenseRecord.getCostIncurredTime());
			this.updateById(record);
		}else {
			this.save(expenseRecord);
		}

		ProjectLiQuiDetail projectLiQuiDetail = project.getProjectLiQuiDetail();
		projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().add(expenseRecord.getCostAmount()));
		project.setProjectLiQuiDetail(projectLiQuiDetail);
		//修改项目金额
		projectLiquiService.updateById(project);
		//查询项目关联主体信息
		List<ProjectSubjectRe> list = projectSubjectReService.list(new LambdaQueryWrapper<ProjectSubjectRe>().eq(ProjectSubjectRe::getProjectId, project.getProjectId()));

		List<ExpenseRecordSubjectRe> expenseRecordSubjectReList=new ArrayList<>();
		ExpenseRecordSubjectRe expenseRecordSubjectRe=new ExpenseRecordSubjectRe();
		for (ProjectSubjectRe projectSubjectRe : list) {
			expenseRecordSubjectRe.setSubjectId(projectSubjectRe.getSubjectId());
			expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordSubjectReList.add(expenseRecordSubjectRe);
		}

		//添加费用产出记录主体关联信息
		return recordSubjectReService.saveBatch(expenseRecordSubjectReList);
	}
}
