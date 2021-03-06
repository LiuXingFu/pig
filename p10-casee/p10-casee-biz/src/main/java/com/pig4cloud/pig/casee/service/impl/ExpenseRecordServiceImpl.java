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
 * ?????????????????????
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

		if (record!=null){//??????????????????????????????????????????????????????????????????????????????
			record.setCostAmount(record.getCostAmount().add(expenseRecord.getCostAmount()));
			record.setCostIncurredTime(expenseRecord.getCostIncurredTime());
			this.updateById(record);
		}else {
			//????????????????????????
			this.save(expenseRecord);

			//??????????????????????????????
			List<ProjectSubjectRe> list = projectSubjectReService.list(new LambdaQueryWrapper<ProjectSubjectRe>().eq(ProjectSubjectRe::getProjectId, project.getProjectId()));

			List<ExpenseRecordSubjectRe> expenseRecordSubjectReList=new ArrayList<>();
			ExpenseRecordSubjectRe expenseRecordSubjectRe=new ExpenseRecordSubjectRe();
			for (ProjectSubjectRe projectSubjectRe : list) {
				expenseRecordSubjectRe.setSubjectId(projectSubjectRe.getSubjectId());
				expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
				expenseRecordSubjectReList.add(expenseRecordSubjectRe);
			}
			//??????????????????????????????????????????
			recordSubjectReService.saveBatch(expenseRecordSubjectReList);
		}

		ProjectLiQuiDetail projectLiQuiDetail = project.getProjectLiQuiDetail();
		projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().add(expenseRecord.getCostAmount()));
		project.setProjectLiQuiDetail(projectLiQuiDetail);
		//??????????????????
		return projectLiquiService.updateById(project);
	}

	@Override
	@Transactional
	public boolean updateExpenseRecordUpdateProject(ExpenseRecord expenseRecord) {
		boolean update = this.updateById(expenseRecord);
		// ?????????????????????
		projectPaifuService.updateProjectAmount(expenseRecord.getProjectId());
		return update;
	}

	@Override
	@Transactional
	public boolean updateExpenseRecordStatusAndProjectAmount(ExpenseRecord expenseRecord) {
		ProjectLiqui project = projectLiquiService.getByProjectId(expenseRecord.getProjectId());
		ProjectLiQuiDetail projectLiQuiDetail = project.getProjectLiQuiDetail();
		//?????????????????????
		if (expenseRecord.getStatus()==0){//????????????
			projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().add(expenseRecord.getCostAmount()));
			project.setProjectLiQuiDetail(projectLiQuiDetail);
		}else if (expenseRecord.getStatus()==2){//??????
			projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().subtract(expenseRecord.getCostAmount()));
			project.setProjectLiQuiDetail(projectLiQuiDetail);
		}


//		//???????????????????????????????????????
//		List<PaymentRecord> list = paymentRecordService.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getExpenseRecordId, expenseRecord.getExpenseRecordId()));
//		for (PaymentRecord paymentRecord : list) {
//
//			//???????????????????????????id??????????????????
//			PaymentRecord paymentRecordFather = paymentRecordService.getById(paymentRecord.getFatherId());
//
//			//???????????????????????????
//			projectLiQuiDetail.setRepaymentAmount(projectLiQuiDetail.getRepaymentAmount().subtract(paymentRecordFather.getPaymentAmount()));
//			project.setProjectLiQuiDetail(projectLiQuiDetail);
//
//			//???????????????????????????id??????????????????????????????
//			List<PaymentRecord> paymentRecordFatherList = paymentRecordService.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getFatherId, paymentRecordFather.getPaymentRecordId()));
//			for (PaymentRecord record : paymentRecordFatherList) {
//				//??????????????????????????????????????????????????????
//				paymentRecordService.deletePaymentRecordRe(record.getPaymentRecordId());
//
//				//???????????????????????????????????????
//				ExpenseRecord expenseRecordUpdate = this.getById(record.getExpenseRecordId());
//				if (expenseRecord.getExpenseRecordId()!=expenseRecordUpdate.getExpenseRecordId()){
//					expenseRecordUpdate.setStatus(0);
//					this.updateById(expenseRecordUpdate);
//				}
//			}
//
//			//??????????????????????????????????????????????????????????????????????????????????????????
//			if (paymentRecordFather.getPaymentType()==300||paymentRecordFather.getPaymentType()==400){
//				paymentRecordFather.setStatus(0);
//				paymentRecordService.updateById(paymentRecordFather);
//			}else if (paymentRecordFather.getPaymentType()==100){//???????????????????????????????????????????????????????????????????????????????????????
//				//????????????????????????????????????????????????
//				paymentRecordService.deletePaymentRecordRe(paymentRecordFather.getPaymentRecordId());
//
//				//??????????????????id??????????????????
//				PaymentSourceRe paymentSourceRe = paymentSourceReService.getOne(new LambdaQueryWrapper<PaymentSourceRe>().eq(PaymentSourceRe::getPaymentRecordId, paymentRecordFather.getPaymentRecordId()).eq(PaymentSourceRe::getType,100));
//
//				PaymentRecord paymentRecordDk = paymentRecordService.getById(paymentSourceRe.getSourceId());
//
//				paymentRecordDk.setStatus(0);
//				//????????????????????????
//				paymentRecordService.updateById(paymentRecordDk);
//			}
//		}

		//??????????????????
		projectLiquiService.updateById(project);
		//????????????????????????
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
		//???????????????????????????
		caseeService.updateById(casee);

		//???????????????????????????
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

		//?????????????????????????????????????????????????????????
		ExpenseRecord bjExpenseRecord = this.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId, refereeResultTakeEffectDTO.getProjectId()).eq(ExpenseRecord::getCostType, 10001).eq(ExpenseRecord::getStatus,0));
		if (bjExpenseRecord!=null){
			bjExpenseRecord.setCostAmount(refereeResultTakeEffectDTO.getPrincipal());
			this.updateById(bjExpenseRecord);
		}

		//?????????????????????????????????????????????????????????
		ExpenseRecord lxExpenseRecord = this.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId, refereeResultTakeEffectDTO.getProjectId()).eq(ExpenseRecord::getCostType, 30001).eq(ExpenseRecord::getStatus,0));
		if (lxExpenseRecord!=null){
			lxExpenseRecord.setCostAmount(refereeResultTakeEffectDTO.getInterest());
			this.updateById(lxExpenseRecord);
		}

		//??????????????????????????????????????????
		ExpenseRecord ysExpenseRecord = this.getOne(new LambdaQueryWrapper<ExpenseRecord>().eq(ExpenseRecord::getProjectId, refereeResultTakeEffectDTO.getProjectId()).eq(ExpenseRecord::getCaseeId, refereeResultTakeEffectDTO.getCaseeId()).eq(ExpenseRecord::getCostType, 10003).eq(ExpenseRecord::getStatus,0));

		if (ysExpenseRecord!=null){
			ysExpenseRecord.setCostAmount(refereeResultTakeEffectDTO.getLitigationCosts());
			this.updateById(ysExpenseRecord);
		}else {
			List<ProjectSubjectRe> projectSubjectReList = projectSubjectReService.list(new LambdaQueryWrapper<ProjectSubjectRe>().eq(ProjectSubjectRe::getProjectId, refereeResultTakeEffectDTO.getProjectId()).ne(ProjectSubjectRe::getType,0));

			//????????????????????????????????????
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

			//????????????????????????????????????
			ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
			for (ProjectSubjectRe projectSubjectRe : projectSubjectReList) {
				expenseRecordSubjectRe.setSubjectId(projectSubjectRe.getSubjectId());
				expenseRecordSubjectRe.setExpenseRecordId(expenseRecordPrincipal.getExpenseRecordId());
				expenseRecordSubjectReService.save(expenseRecordSubjectRe);
			}
		}
		projectLiQuiDetail.setProjectAmount(projectLiQuiDetail.getProjectAmount().add(refereeResultTakeEffectDTO.getLitigationCosts()));
		projectLiqui.setProjectLiQuiDetail(projectLiQuiDetail);
		//??????????????????????????????????????????????????????
		projectLiquiService.updateById(projectLiqui);
	}

	@Override
	public List<ExpenseRecordDistributeVO> getAssetsByPaymentType(Integer projectId,Integer caseeId,Integer assetsId) {
		return this.baseMapper.selectByProjectCaseeAssetsId(projectId,caseeId,assetsId);
	}

	@Override
	public boolean deleteExpenseRecordRe(Integer expenseRecordId) {

		//??????????????????????????????????????????
		expenseRecordAssetsReService.remove(new LambdaQueryWrapper<ExpenseRecordAssetsRe>()
				.eq(ExpenseRecordAssetsRe::getExpenseRecordId, expenseRecordId));

		//??????????????????????????????????????????
		expenseRecordSubjectReService.remove(new LambdaQueryWrapper<ExpenseRecordSubjectRe>()
				.eq(ExpenseRecordSubjectRe::getExpenseRecordId, expenseRecordId));

		//????????????????????????
		return this.removeById(expenseRecordId);
	}

	@Override
	public ExpenseRecord addExpenseRecord(BigDecimal amount, LocalDate date, Project project, Casee casee, AssetsReSubjectDTO assetsReSubjectDTO,List<JointAuctionAssetsDTO> jointAuctionAssetsDTOList,Integer costType) {
		//????????????????????????
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

		if (jointAuctionAssetsDTOList!=null){//????????????
			List<ExpenseRecordAssetsRe> expenseRecordAssetsReList = new ArrayList<>();
			//????????????????????????????????????????????????
			for (JointAuctionAssetsDTO jointAuctionAssetsDTO : jointAuctionAssetsDTOList) {
				ExpenseRecordAssetsRe expenseRecordAssetsRe = new ExpenseRecordAssetsRe();
				expenseRecordAssetsRe.setAssetsReId(jointAuctionAssetsDTO.getAssetsReId());
				expenseRecordAssetsRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
				expenseRecordAssetsReList.add(expenseRecordAssetsRe);
			}
			// ??????????????????????????????????????????
			expenseRecordAssetsReService.saveBatch(expenseRecordAssetsReList);
		}else {
			//????????????????????????????????????????????????
			ExpenseRecordAssetsRe expenseRecordAssetsRe = new ExpenseRecordAssetsRe();
			expenseRecordAssetsRe.setAssetsReId(assetsReSubjectDTO.getAssetsReId());
			expenseRecordAssetsRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			// ??????????????????????????????????????????
			expenseRecordAssetsReService.save(expenseRecordAssetsRe);
		}

		List<ExpenseRecordSubjectRe> expenseRecordSubjectReList = new ArrayList<>();
		for (Subject subject : assetsReSubjectDTO.getSubjectList()) {
			ExpenseRecordSubjectRe expenseRecordSubjectRe = new ExpenseRecordSubjectRe();
			expenseRecordSubjectRe.setSubjectId(subject.getSubjectId());
			expenseRecordSubjectRe.setExpenseRecordId(expenseRecord.getExpenseRecordId());
			expenseRecordSubjectReList.add(expenseRecordSubjectRe);
		}
		//??????????????????????????????????????????
		expenseRecordSubjectReService.saveBatch(expenseRecordSubjectReList);
		return expenseRecord;
	}

	@Override
	public ExpenseRecord addCommonExpenseRecord(BigDecimal amount, LocalDate date, Project project, Casee casee,List<Subject> subjectList, String subjectName, Integer costType) {
		//??????????????????????????????
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

		//????????????????????????????????????
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
		//??????????????????????????????????????????????????????
		List<PaymentRecord> paymentRecordList = paymentRecordService.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getExpenseRecordId, ExpenseRecordId).eq(PaymentRecord::getStatus,1));
		if (paymentRecordList.size() > 0) {//?????????
			for (PaymentRecord paymentRecord : paymentRecordList) {
				//???????????????????????????????????????????????????
				paymentRecordService.paymentCancellation(paymentRecord.getPaymentRecordId(),projectType);
			}
		}
		//??????????????????
		ExpenseRecord expenseRecord = this.getById(ExpenseRecordId);
		expenseRecord.setCostAmount(expenseRecord.getCostAmount().subtract(costAmount));
		expenseRecord.setCostAmount(expenseRecord.getCostAmount().add(updateCostAmount));
		expenseRecord.setStatus(0);
		//?????????????????????????????????
		this.updateById(expenseRecord);

		if (projectType==100){//??????
			ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(projectId);
			//??????????????????????????????
			projectLiquiService.subtractProjectAmount(projectLiqui, costAmount);
			//??????????????????????????????
			projectLiquiService.addProjectAmount(projectLiqui, updateCostAmount);
		}else {//??????
			// ?????????????????????
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
		// ????????????????????????????????????
		Casee casee = projectCaseeReService.getImplementCaseeByProjectId(expenseRecordPaifuSaveDTO.getProjectId());
		// ???????????????????????????
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
		// ?????????????????????
		projectPaifuService.updateProjectAmount(expenseRecordPaifuSaveDTO.getProjectId());

		return save;
	}

	@Override
	@Transactional
	public Integer modifyPaifuExpenseRecord(ExpenseRecordPaifuSaveDTO expenseRecordPaifuSaveDTO){
		// ???????????????????????????
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
		// ?????????????????????
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
	 * ?????????id???????????????????????????????????????????????????????????????????????????
	 *
	 * @param projectId        ??????id
	 * @param transferRecordId ????????????id
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

		// ?????????????????????
		projectPaifuService.updateProjectAmount(projectId);

 		if (paymentRecordList.size() > 0){
			this.paymentRecordService.addPaymentRecordByProjectIdAndPaymentRecordList(projectLiQuiAndSubjectListVO, paymentRecordList);
		}
	}

}
