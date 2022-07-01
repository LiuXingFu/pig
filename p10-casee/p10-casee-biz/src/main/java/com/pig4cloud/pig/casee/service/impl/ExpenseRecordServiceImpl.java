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
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.AssetsReSubjectDTO;
import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
import com.pig4cloud.pig.casee.dto.RefereeResultTakeEffectDTO;
import com.pig4cloud.pig.casee.dto.paifu.ExpenseRecordPaifuSaveDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.entity.liquientity.CaseeLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.TransferRecordLiqui;
import com.pig4cloud.pig.casee.entity.liquientity.detail.CaseeLiquiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.ProjectLiQuiDetail;
import com.pig4cloud.pig.casee.entity.liquientity.detail.detailentity.FirstTrialRefereeResult;
import com.pig4cloud.pig.casee.mapper.ExpenseRecordMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.*;
import com.pig4cloud.pig.casee.vo.paifu.ExpenseRecordDetailVO;
import com.pig4cloud.pig.casee.vo.paifu.ExpenseRecordPaifuAssetsReListVO;
import com.pig4cloud.pig.casee.vo.paifu.ExpenseRecordPageVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.JsonUtils;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
	@Autowired
	private ExpenseRecordAssetsReService expenseRecordAssetsReService;
	@Autowired
	private ProjectCaseeReService projectCaseeReService;
	@Autowired
	private AssetsReSubjectService assetsReSubjectService;
	@Autowired
	private RemoteSubjectService remoteSubjectService;
	@Autowired
	private ProjectPaifuService projectPaifuService;
	@Autowired
	private PaymentRecordService paymentRecordService;
	@Autowired
	private PaymentSourceReService paymentSourceReService;
	@Autowired
	private ExpenseRecordSubjectReService expenseRecordSubjectReService;
	@Autowired
	private CaseeService caseeService;
	@Autowired
	private CaseeLiquiService caseeLiquiService;
	@Autowired
	private TransferRecordLiquiService transferRecordLiquiService;

	@Override
	public IPage<ExpenseRecordVO> getExpenseRecordPage(Page page, ExpenseRecord expenseRecord) {
		return this.baseMapper.getExpenseRecordPage(page,expenseRecord);
	}

	@Override
	public boolean saveExpenseRecordUpdateProject(ExpenseRecord expenseRecord) {
		ExpenseRecord record = this.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId,expenseRecord.getProjectId()).eq(ExpenseRecord::getCaseeNumber, expenseRecord.getCaseeNumber()).eq(ExpenseRecord::getCostType, expenseRecord.getCostType()).eq(ExpenseRecord::getStatus,0));
		ProjectLiqui project = projectLiquiService.getByProjectId(expenseRecord.getProjectId());
		expenseRecord.setSubjectName(project.getSubjectPersons());

		if (record!=null){//如果当前费用类型的案件费用信息已经存在则费用金额累加
			record.setCostAmount(record.getCostAmount().add(expenseRecord.getCostAmount()));
			record.setCostIncurredTime(expenseRecord.getCostIncurredTime());
			this.updateById(record);
		}else {
			//添加费用产出记录
			this.save(expenseRecord);

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
			recordSubjectReService.saveBatch(expenseRecordSubjectReList);
		}

		ProjectLiQuiDetail projectLiQuiDetail = project.getProjectLiQuiDetail();
		projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().add(expenseRecord.getCostAmount()));
		project.setProjectLiQuiDetail(projectLiQuiDetail);
		//修改项目金额
		return projectLiquiService.updateById(project);
	}

	@Override
	@Transactional
	public boolean updateExpenseRecordUpdateProject(ExpenseRecord expenseRecord) {
		boolean update = this.updateById(expenseRecord);
		// 更新项目总金额
		projectPaifuService.updateProjectAmount(expenseRecord.getProjectId());
		return update;
	}

	@Override
	@Transactional
	public boolean updateExpenseRecordStatusAndProjectAmount(ExpenseRecord expenseRecord) {
		ProjectLiqui project = projectLiquiService.getByProjectId(expenseRecord.getProjectId());
		ProjectLiQuiDetail projectLiQuiDetail = project.getProjectLiQuiDetail();
		//修改项目总金额
		if (expenseRecord.getStatus()==0){//恢复正常
			projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().add(expenseRecord.getCostAmount()));
			project.setProjectLiQuiDetail(projectLiQuiDetail);
		}else if (expenseRecord.getStatus()==2){//作废
			projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().subtract(expenseRecord.getCostAmount()));
			project.setProjectLiQuiDetail(projectLiQuiDetail);
		}


//		//查询该费用记录回款记录信息
//		List<PaymentRecord> list = paymentRecordService.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getExpenseRecordId, expenseRecord.getExpenseRecordId()));
//		for (PaymentRecord paymentRecord : list) {
//
//			//根据该条回款记录父id查询分配类型
//			PaymentRecord paymentRecordFather = paymentRecordService.getById(paymentRecord.getFatherId());
//
//			//修改项目回款总金额
//			projectLiQuiDetail.setRepaymentAmount(projectLiQuiDetail.getRepaymentAmount().subtract(paymentRecordFather.getPaymentAmount()));
//			project.setProjectLiQuiDetail(projectLiQuiDetail);
//
//			//根据该条回款记录父id查询所有分配款项记录
//			List<PaymentRecord> paymentRecordFatherList = paymentRecordService.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getFatherId, paymentRecordFather.getPaymentRecordId()));
//			for (PaymentRecord record : paymentRecordFatherList) {
//				//删除分配款项记录信息以及相关关联信息
//				paymentRecordService.deletePaymentRecordRe(record.getPaymentRecordId());
//
//				//修改费用产生记录状态为正常
//				ExpenseRecord expenseRecordUpdate = this.getById(record.getExpenseRecordId());
//				if (expenseRecord.getExpenseRecordId()!=expenseRecordUpdate.getExpenseRecordId()){
//					expenseRecordUpdate.setStatus(0);
//					this.updateById(expenseRecordUpdate);
//				}
//			}
//
//			//如果当前分配类型是履行到申请人或资产抵偿则修改该状态为未分配
//			if (paymentRecordFather.getPaymentType()==300||paymentRecordFather.getPaymentType()==400){
//				paymentRecordFather.setStatus(0);
//				paymentRecordService.updateById(paymentRecordFather);
//			}else if (paymentRecordFather.getPaymentType()==100){//如果当前分配类型是领款，删除领款信息并修改到款状态为未分配
//				//删除领款记录信息以及相关关联信息
//				paymentRecordService.deletePaymentRecordRe(paymentRecordFather.getPaymentRecordId());
//
//				//根据领款记录id查询到款信息
//				PaymentSourceRe paymentSourceRe = paymentSourceReService.getOne(new LambdaQueryWrapper<PaymentSourceRe>().eq(PaymentSourceRe::getPaymentRecordId, paymentRecordFather.getPaymentRecordId()).eq(PaymentSourceRe::getType,100));
//
//				PaymentRecord paymentRecordDk = paymentRecordService.getById(paymentSourceRe.getSourceId());
//
//				paymentRecordDk.setStatus(0);
//				//修改到款信息状态
//				paymentRecordService.updateById(paymentRecordDk);
//			}
//		}

		//修改项目金额
		projectLiquiService.updateById(project);
		//修改费用产生信息
		return this.updateById(expenseRecord);
	}

	@Override
	public List<ExpenseRecordDistributeVO> getByPaymentType(ExpenseRecord expenseRecord) {
		return this.baseMapper.getByPaymentType(expenseRecord);
	}

	@Override
	public ExpenseRecordMoneyBackVO getByExpenseRecordMoneyBack(ExpenseRecord expenseRecord) {
		return this.baseMapper.getByExpenseRecordMoneyBack(expenseRecord);
	}

	@Override
	public void refereeResultTakeEffectUpdateExpenseRecord(RefereeResultTakeEffectDTO refereeResultTakeEffectDTO) {
		Casee casee=new Casee();
		casee.setCaseeId(refereeResultTakeEffectDTO.getCaseeId());
		casee.setJudicialExpenses(refereeResultTakeEffectDTO.getLitigationCosts());
		//修改案件案件受理费
		caseeService.updateById(casee);

		//查询案件与案件详情
		Casee queryCasee = new Casee();
		queryCasee.setCaseeId(refereeResultTakeEffectDTO.getCaseeId());
		CaseeLiqui caseeLiqui = caseeLiquiService.getCaseeLiqui(queryCasee);


		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(refereeResultTakeEffectDTO.getProjectId());
		ProjectLiQuiDetail projectLiQuiDetail = new ProjectLiQuiDetail();
		BeanCopyUtil.copyBean(projectLiqui.getProjectLiQuiDetail(),projectLiQuiDetail);
		projectLiQuiDetail.setPrincipal(refereeResultTakeEffectDTO.getPrincipal());
		projectLiQuiDetail.setInterest(refereeResultTakeEffectDTO.getInterest());
		projectLiQuiDetail.setPrincipalInterestAmount(refereeResultTakeEffectDTO.getRefereeAmount());
		projectLiQuiDetail.setProjectAmount(refereeResultTakeEffectDTO.getRefereeAmount());

		//查询项目本金费用产生记录并修改本金金额
		ExpenseRecord bjExpenseRecord = this.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId, refereeResultTakeEffectDTO.getProjectId()).eq(ExpenseRecord::getCostType, 10001).eq(ExpenseRecord::getStatus,0));
		if (bjExpenseRecord!=null){
			bjExpenseRecord.setCostAmount(refereeResultTakeEffectDTO.getPrincipal());
			this.updateById(bjExpenseRecord);
		}

		//查询项目利息费用产生记录并修改利息金额
		ExpenseRecord lxExpenseRecord = this.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId, refereeResultTakeEffectDTO.getProjectId()).eq(ExpenseRecord::getCostType, 30001).eq(ExpenseRecord::getStatus,0));
		if (lxExpenseRecord!=null){
			lxExpenseRecord.setCostAmount(refereeResultTakeEffectDTO.getInterest());
			this.updateById(lxExpenseRecord);
		}

		//查询一审诉讼费并修改诉讼金额
		ExpenseRecord ysExpenseRecord = this.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId, refereeResultTakeEffectDTO.getProjectId()).eq(ExpenseRecord::getCaseeId, refereeResultTakeEffectDTO.getCaseeId()).eq(ExpenseRecord::getCostType, 10003).eq(ExpenseRecord::getStatus,0));

		if (ysExpenseRecord!=null){
			ysExpenseRecord.setCostAmount(refereeResultTakeEffectDTO.getLitigationCosts());
			this.updateById(ysExpenseRecord);
		}else {
			List<ProjectSubjectRe> projectSubjectReList = projectSubjectReService.list(new LambdaQueryWrapper<ProjectSubjectRe>().eq(ProjectSubjectRe::getProjectId, refereeResultTakeEffectDTO.getProjectId()).ne(ProjectSubjectRe::getType,0));

			//添加一审诉讼费用产生明显
			ExpenseRecord expenseRecordPrincipal = new ExpenseRecord();
			expenseRecordPrincipal.setProjectId(refereeResultTakeEffectDTO.getProjectId());
			expenseRecordPrincipal.setCaseeId(refereeResultTakeEffectDTO.getCaseeId());
			expenseRecordPrincipal.setCaseeNumber(caseeLiqui.getCaseeNumber());
			expenseRecordPrincipal.setCostType(10003);
			expenseRecordPrincipal.setCostIncurredTime(refereeResultTakeEffectDTO.getRefereeMediationTime());
			expenseRecordPrincipal.setCostAmount(refereeResultTakeEffectDTO.getLitigationCosts());
			expenseRecordPrincipal.setStatus(0);
			expenseRecordPrincipal.setCompanyCode(projectLiqui.getCompanyCode());
			expenseRecordPrincipal.setSubjectName(projectLiqui.getSubjectPersons());
			this.save(expenseRecordPrincipal);

			//添加费用记录关联主体信息
			ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
			for (ProjectSubjectRe projectSubjectRe : projectSubjectReList) {
				expenseRecordSubjectRe.setSubjectId(projectSubjectRe.getSubjectId());
				expenseRecordSubjectRe.setExpenseRecordId(expenseRecordPrincipal.getExpenseRecordId());
				expenseRecordSubjectReService.save(expenseRecordSubjectRe);
			}
		}
		projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().add(refereeResultTakeEffectDTO.getLitigationCosts()));
		projectLiqui.setProjectLiQuiDetail(projectLiQuiDetail);
		//修改项目本金、利息、以及本金利息总额
		projectLiquiService.updateById(projectLiqui);
	}

	@Override
	public List<ExpenseRecordDistributeVO> getAssetsByPaymentType(Integer projectId,Integer caseeId,Integer assetsId) {
		return this.baseMapper.selectByProjectCaseeAssetsId(projectId,caseeId,assetsId);
	}

	@Override
	public boolean deleteExpenseRecordRe(Integer expenseRecordId) {

		//删除费用明细记录财产关联信息
		expenseRecordAssetsReService.remove(new LambdaQueryWrapper<ExpenseRecordAssetsRe>()
				.eq(ExpenseRecordAssetsRe::getExpenseRecordId, expenseRecordId));

		//删除费用产生明细关联主体信息
		expenseRecordSubjectReService.remove(new LambdaQueryWrapper<ExpenseRecordSubjectRe>()
				.eq(ExpenseRecordSubjectRe::getExpenseRecordId, expenseRecordId));

		//删除费用明细记录
		return this.removeById(expenseRecordId);
	}

	@Override
	public ExpenseRecord addExpenseRecord(BigDecimal amount, LocalDate date, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO,List<JointAuctionAssetsDTO> jointAuctionAssetsDTOList,Integer costType) {
		//添加费用明细记录
		ExpenseRecord expenseRecord = new ExpenseRecord();
		expenseRecord.setCostAmount(amount);
		expenseRecord.setCostIncurredTime(date);
		expenseRecord.setProjectId(project.getProjectId());
		expenseRecord.setCaseeId(casee.getCaseeId());
		expenseRecord.setCaseeNumber(casee.getCaseeNumber());
		expenseRecord.setStatus(0);
		expenseRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		expenseRecord.setCompanyCode(project.getCompanyCode());
		expenseRecord.setCostType(costType);
		this.save(expenseRecord);

		if (jointAuctionAssetsDTOList!=null){//联合拍卖
			List<ExpenseRecordAssetsRe> expenseRecordAssetsReList = new ArrayList<>();
			//循环当前拍卖公告联合拍卖财产信息
			for (JointAuctionAssetsDTO jointAuctionAssetsDTO : jointAuctionAssetsDTOList) {
				ExpenseRecordAssetsRe expenseRecordAssetsRe = new ExpenseRecordAssetsRe();
				expenseRecordAssetsRe.setAssetsReId(jointAuctionAssetsDTO.getAssetsReId());
				expenseRecordAssetsRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
				expenseRecordAssetsReList.add(expenseRecordAssetsRe);
			}
			// 添加费用产生记录财产关联信息
			expenseRecordAssetsReService.saveBatch(expenseRecordAssetsReList);
		}else {
			//循环当前拍卖公告联合拍卖财产信息
			ExpenseRecordAssetsRe expenseRecordAssetsRe = new ExpenseRecordAssetsRe();
			expenseRecordAssetsRe.setAssetsReId(assetsReSubjectDTO.getAssetsReId());
			expenseRecordAssetsRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			// 添加费用产生记录财产关联信息
			expenseRecordAssetsReService.save(expenseRecordAssetsRe);
		}

		List<ExpenseRecordSubjectRe> expenseRecordSubjectReList = new ArrayList<>();
		for (Subject subject : assetsReSubjectDTO.getSubjectList()) {
			ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
			expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
			expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordSubjectReList.add(expenseRecordSubjectRe);
		}
		//添加费用产生明细关联主体信息
		expenseRecordSubjectReService.saveBatch(expenseRecordSubjectReList);
		return expenseRecord;
	}

	@Override
	public ExpenseRecord addCommonExpenseRecord(BigDecimal amount, LocalDate date, Project project, Casee casee,List<Subject> subjectList, String subjectName, Integer costType) {
		//添加项目费用产生记录
		ExpenseRecord expenseRecord = new ExpenseRecord();
		expenseRecord.setProjectId(project.getProjectId());
		expenseRecord.setCostType(costType);
		expenseRecord.setCostIncurredTime(date);
		expenseRecord.setCostAmount(amount);
		expenseRecord.setStatus(0);
		expenseRecord.setCompanyCode(project.getCompanyCode());
		expenseRecord.setSubjectName(subjectName);
		if (casee!=null){
			expenseRecord.setCaseeId(casee.getCaseeId());
			expenseRecord.setCaseeNumber(casee.getCaseeNumber());
		}

		this.save(expenseRecord);

		//添加费用记录关联主体信息
		List<ExpenseRecordSubjectRe> expenseRecordSubjectReList = new ArrayList<>();
		for (Subject subject : subjectList) {
			ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
			expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
			expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordSubjectReList.add(expenseRecordSubjectRe);
		}
		expenseRecordSubjectReService.saveBatch(expenseRecordSubjectReList);
		return expenseRecord;
	}

	@Override
	public void updateExpenseRecordProjectAmount(Integer ExpenseRecordId,BigDecimal costAmount,BigDecimal updateCostAmount,Integer projectId,Integer projectType) {
		//拍辅费要是已分配，修改已回款记录作废
		List<PaymentRecord> paymentRecordList = paymentRecordService.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getExpenseRecordId, ExpenseRecordId).eq(PaymentRecord::getStatus,1));
		if (paymentRecordList.size() > 0) {//已回款
			for (PaymentRecord paymentRecord : paymentRecordList) {
				//修改项目回款总金额、已回款记录作废
				paymentRecordService.paymentCancellation(paymentRecord.getPaymentRecordId(),projectType);
			}
		}
		//修改费用金额
		ExpenseRecord expenseRecord = this.getById(ExpenseRecordId);
		expenseRecord.setCostAmount(expenseRecord.getCostAmount().subtract(costAmount));
		expenseRecord.setCostAmount(expenseRecord.getCostAmount().add(updateCostAmount));
		expenseRecord.setStatus(0);
		//修改当前财产程序拍辅费
		this.updateById(expenseRecord);

		if (projectType==100){//清收
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(projectId);
			//减去补录前的拍辅费用
			projectLiquiService.subtractProjectAmount(projectLiqui, costAmount);
			//加上补录后的拍辅费用
			projectLiquiService.addProjectAmount(projectLiqui, updateCostAmount);
		}else {//拍辅
			// 更新项目总金额
			projectPaifuService.updateProjectAmount(projectId);
		}
	}

	@Override
	public BigDecimal totalAmountByProjectId(Integer projectId){
		return this.baseMapper.totalAmountByProjectId(projectId);
	}

	@Override
	@Transactional
	public Integer savePaifuExpenseRecord(ExpenseRecordPaifuSaveDTO expenseRecordPaifuSaveDTO){
		// 查询项目最后一条案件信息
		Casee casee = projectCaseeReService.getImplementCaseeByProjectId(expenseRecordPaifuSaveDTO.getProjectId());
		// 查询财产债务人名称
		List<Integer> subjectIdList = assetsReSubjectService.queryByAssetsIdList(expenseRecordPaifuSaveDTO.getAssetsReIdList());
		R<String> subjectNames = remoteSubjectService.querySubjectName(subjectIdList, SecurityConstants.FROM);

		ExpenseRecord expenseRecord = new ExpenseRecord();
		BeanCopyUtil.copyBean(expenseRecordPaifuSaveDTO,expenseRecord);
		expenseRecord.setStatus(0);
		expenseRecord.setCaseeId(casee.getCaseeId());
		expenseRecord.setCaseeNumber(casee.getCaseeNumber());
		expenseRecord.setSubjectName(subjectNames.getData());
		Integer save = this.baseMapper.insert(expenseRecord);

		List<ExpenseRecordSubjectRe> expenseRecordSubjectRes = new ArrayList<>();
		for(Integer subjectId:subjectIdList){
			ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
			expenseRecordSubjectRe.setSubjectId(subjectId);
			expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordSubjectRes.add(expenseRecordSubjectRe);
		}
		recordSubjectReService.saveBatch(expenseRecordSubjectRes);

		List<ExpenseRecordAssetsRe> expenseRecordAssetsRes = new ArrayList<>();
		for(Integer assetsReId : expenseRecordPaifuSaveDTO.getAssetsReIdList()){
			ExpenseRecordAssetsRe expenseRecordAssetsRe = new ExpenseRecordAssetsRe();
			expenseRecordAssetsRe.setAssetsReId(assetsReId);
			expenseRecordAssetsRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordAssetsRes.add(expenseRecordAssetsRe);
		}
		expenseRecordAssetsReService.saveBatch(expenseRecordAssetsRes);
		// 更新项目总金额
		projectPaifuService.updateProjectAmount(expenseRecordPaifuSaveDTO.getProjectId());

		return save;
	}

	@Override
	@Transactional
	public Integer modifyPaifuExpenseRecord(ExpenseRecordPaifuSaveDTO expenseRecordPaifuSaveDTO){
		// 查询财产债务人名称
		List<Integer> subjectIdList = assetsReSubjectService.queryByAssetsIdList(expenseRecordPaifuSaveDTO.getAssetsReIdList());
		R<String> subjectNames = remoteSubjectService.querySubjectName(subjectIdList, SecurityConstants.FROM);

		ExpenseRecord expenseRecord = new ExpenseRecord();
		BeanCopyUtil.copyBean(expenseRecordPaifuSaveDTO,expenseRecord);
		expenseRecord.setSubjectName(subjectNames.getData());
		Integer save = this.baseMapper.updateById(expenseRecord);

		QueryWrapper<ExpenseRecordAssetsRe> queryWrapper = new QueryWrapper<>();
		queryWrapper.lambda().eq(ExpenseRecordAssetsRe::getExpenseRecordId,expenseRecordPaifuSaveDTO.getExpenseRecordId());
		expenseRecordAssetsReService.remove(queryWrapper);

		QueryWrapper<ExpenseRecordSubjectRe> expenseRecordSubjectReQueryWrapper = new QueryWrapper<>();
		expenseRecordSubjectReQueryWrapper.lambda().eq(ExpenseRecordSubjectRe::getExpenseRecordId,expenseRecordPaifuSaveDTO.getExpenseRecordId());
		recordSubjectReService.remove(expenseRecordSubjectReQueryWrapper);

		List<ExpenseRecordSubjectRe> expenseRecordSubjectRes = new ArrayList<>();
		for(Integer subjectId:subjectIdList){
			ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
			expenseRecordSubjectRe.setSubjectId(subjectId);
			expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordSubjectRes.add(expenseRecordSubjectRe);
		}
		recordSubjectReService.saveBatch(expenseRecordSubjectRes);

		List<ExpenseRecordAssetsRe> expenseRecordAssetsRes = new ArrayList<>();
		for(Integer assetsReId : expenseRecordPaifuSaveDTO.getAssetsReIdList()){
			ExpenseRecordAssetsRe expenseRecordAssetsRe = new ExpenseRecordAssetsRe();
			expenseRecordAssetsRe.setAssetsReId(assetsReId);
			expenseRecordAssetsRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordAssetsRes.add(expenseRecordAssetsRe);
		}
		expenseRecordAssetsReService.saveBatch(expenseRecordAssetsRes);
		// 更新项目总金额
		projectPaifuService.updateProjectAmount(expenseRecordPaifuSaveDTO.getProjectId());

		return save;
	}

	@Override
	public 	ExpenseRecordPaifuAssetsReListVO queryPaifuExpenseRecordAssetsReList(Integer expenseRecordId){
		ExpenseRecordPaifuAssetsReListVO expenseRecordPaifuAssetsReListVOS = new ExpenseRecordPaifuAssetsReListVO();
		ExpenseRecord expenseRecord = this.baseMapper.selectById(expenseRecordId);
		BeanCopyUtil.copyBean(expenseRecord,expenseRecordPaifuAssetsReListVOS);

		List<Integer> assetsReIdList = expenseRecordAssetsReService.queryByExpenseRecordId(expenseRecordId);
		expenseRecordPaifuAssetsReListVOS.setAssetsReIdList(assetsReIdList);

		return expenseRecordPaifuAssetsReListVOS;
	}

	@Override
	public IPage<ExpenseRecordPageVO> queryPaifuExpenseRecordPage(Page page, Integer projectId){
		return this.baseMapper.queryPaifuExpenseRecordPage(page,projectId);
	}

	@Override
	public ExpenseRecordDetailVO queryDetailById(Integer expenseRecordId){
		ExpenseRecordDetailVO expenseRecordDetailVO = this.baseMapper.queryDetailById(expenseRecordId);
		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setExpenseRecordId(expenseRecordId);
		paymentRecord.setStatus(1);
		BigDecimal paymentAmount = paymentRecordService.sumCourtPayment(paymentRecord);
		BigDecimal outstandingAmount = expenseRecordDetailVO.getCostAmount();
		if(paymentAmount!=null){
			outstandingAmount = outstandingAmount.subtract(paymentAmount);
		}
		expenseRecordDetailVO.setOutstandingAmount(outstandingAmount);
		return expenseRecordDetailVO;
	}

	@Override
	public 	ExpenseRecord queryByAssetsReId(Integer projectId,Integer assetsReId){
		return this.baseMapper.queryByAssetsReId(projectId,assetsReId);
	}

	/**
	 * 将项目id和费用产生记录集合添加费用产生记录以及其它关联信息
	 *
	 * @param projectId        项目id
	 * @param transferRecordId 移送记录id
	 * @return
	 */
	@Override
	@Transactional
	public void addExpenseRecordByProjectIdAndExpenseRecordList(Integer projectId, Integer transferRecordId) {

		TransferRecordLiqui recordLiqui = transferRecordLiquiService.queryTransferRecordLiquiById(transferRecordId);

		ProjectLiQuiAndSubjectListVO projectLiQuiAndSubjectListVO = projectLiquiService.selectProjectLiquiAndSubject(projectId);

		List<ExpenseRecord> expenseRecordList = recordLiqui.getTransferRecordLiquiDetail().getExpenseRecordList();

		List<PaymentRecord> paymentRecordList = new ArrayList<>();

		for (ExpenseRecord expenseRecord : expenseRecordList) {
			expenseRecord.setProjectId(projectId);
			expenseRecord.setCompanyCode(projectLiQuiAndSubjectListVO.getProjectLiqui().getCompanyCode());
			expenseRecord.setSubjectName(projectLiQuiAndSubjectListVO.getProjectLiqui().getSubjectPersons());
		}

		this.saveBatch(expenseRecordList);

		List<ExpenseRecordSubjectRe> expenseRecordSubjectReList = new ArrayList<>();
		for (ProjectSubjectVO projectSubjectVO : projectLiQuiAndSubjectListVO.getProjectSubjectVOList()) {
			for (ExpenseRecord expenseRecord : expenseRecordList) {
				ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
				expenseRecordSubjectRe.setSubjectId(projectSubjectVO.getSubjectId());
				expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
				expenseRecordSubjectReList.add(expenseRecordSubjectRe);
			}
		}

		expenseRecordSubjectReService.saveBatch(expenseRecordSubjectReList);

		for (ExpenseRecord expenseRecord : expenseRecordList) {
			if (expenseRecord.getStatus().equals(1)) {
				PaymentRecord paymentRecord = new PaymentRecord();
				BeanCopyUtil.copyBean(expenseRecord, paymentRecord);
				paymentRecord.setFundsType(expenseRecord.getCostType());
				paymentRecord.setPaymentType(100);
				paymentRecord.setPaymentAmount(expenseRecord.getCostAmount());
				paymentRecord.setPaymentDate(expenseRecord.getCostIncurredTime());
				paymentRecordList.add(paymentRecord);
			}
		}

		// 更新项目总金额
		projectPaifuService.updateProjectAmount(projectId);

 		if (paymentRecordList.size() > 0){
			this.paymentRecordService.addPaymentRecordByProjectIdAndPaymentRecordList(projectLiQuiAndSubjectListVO, paymentRecordList);
		}
	}

}
