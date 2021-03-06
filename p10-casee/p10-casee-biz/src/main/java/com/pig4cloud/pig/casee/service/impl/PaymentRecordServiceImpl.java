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
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.casee.dto.*;
import com.pig4cloud.pig.casee.dto.paifu.PaymentRecordSaveDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.dto.count.CountMoneyBackMonthlyRankDTO;
import com.pig4cloud.pig.casee.entity.liquientity.ProjectLiqui;
import com.pig4cloud.pig.casee.mapper.PaymentRecordMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.*;
import com.pig4cloud.pig.casee.vo.paifu.ExpenseRecordPaifuAssetsReListVO;
import com.pig4cloud.pig.casee.vo.paifu.PaymentRecordListVO;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * ?????????????????????
 *
 * @author Mjh
 * @date 2022-02-17 17:52:51
 */
@Service
public class PaymentRecordServiceImpl extends ServiceImpl<PaymentRecordMapper, PaymentRecord> implements PaymentRecordService {
	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;
	@Autowired
	private PaymentRecordSubjectReService paymentRecordSubjectReService;
	@Autowired
	private ExpenseRecordService expenseRecordService;
	@Autowired
	private PaymentRecordAssetsReService paymentRecordAssetsReService;
	@Autowired
	private ProjectPaifuService projectPaifuService;
	@Autowired
	private ExpenseRecordAssetsReService expenseRecordAssetsReService;
	@Autowired
	private PaymentSourceReService paymentSourceReService;
	@Autowired
	private ProjectLiquiService projectLiquiService;

	@Override
	public IPage<PaymentRecordVO> getPaymentRecordPage(Page page, PaymentRecordPageDTO paymentRecordPageDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.getPaymentRecordPage(page,paymentRecordPageDTO,insOutlesDTO);
	}

	@Override
	public IPage<PaymentRecordVO> getProjectIdByPaymentRecordPage(Page page, String projectId) {
		return this.baseMapper.getProjectIdByPaymentRecordPage(page,projectId);
	}

	@Override
	public IPage<PaymentRecordVO> getByUnassignedPaymentRecordPage(Page page, PaymentRecordPageDTO paymentRecordPageDTO) {
		return this.baseMapper.getByUnassignedPaymentRecordPage(page,paymentRecordPageDTO);
	}

	@Override
	public IPage<PaymentRecordVO> getByPaymentRecordPage(Page page, Integer paymentRecordId) {
		return this.baseMapper.getByPaymentRecordPage(page,paymentRecordId);
	}

	@Override
	public BigDecimal sumCourtPayment(PaymentRecord paymentRecord){
		return this.baseMapper.sumCourtPayment(paymentRecord);
	}

	@Override
	public IPage<PaymentRecordCourtPaymentVO> getCourtPaymentPage(Page page, String projectId) {
		return this.baseMapper.getCourtPaymentPage(page,projectId);
	}

	@Override
	public List<PaymentRecordVO> getCourtPaymentUnpaid(Integer projectId) {
		return this.baseMapper.getCourtPaymentUnpaid(projectId);
	}

	@Override
	@Transactional
	public boolean saveCourtPayment(PaymentRecordDTO paymentRecordDTO) {
		boolean save = this.save(paymentRecordDTO);
		if (paymentRecordDTO.getFundsType().equals(20003)){
			for (Integer assetsReId : paymentRecordDTO.getAssetsReIdList()) {
				PaymentRecordAssetsRe paymentRecordAssetsRe = new PaymentRecordAssetsRe();
				paymentRecordAssetsRe.setAssetsReId(assetsReId);
				paymentRecordAssetsRe.setPaymentRecordId(paymentRecordDTO.getPaymentRecordId());
				// ????????????????????????????????????
				paymentRecordAssetsReService.save(paymentRecordAssetsRe);
			}
		}
		for (Integer subjectId : paymentRecordDTO.getSubjectIdList()) {
			PaymentRecordSubjectRe paymentRecordSubjectRe = new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setSubjectId(subjectId);
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecordDTO.getPaymentRecordId());
			//???????????????????????????
			paymentRecordSubjectReService.save(paymentRecordSubjectRe);
		}
		return save;
	}

	@Override
	@Transactional
	public boolean distribute(PaymentRecordDTO paymentRecordDTO) {
		//??????????????????????????????????????????????????????
		PaymentRecord record = this.getById(paymentRecordDTO.getPaymentRecordId());
		record.setStatus(1);
		boolean update = this.updateById(record);

		List<PaymentRecordSubjectRe> paymentRecordSubjectReList = paymentRecordSubjectReService.list(new LambdaQueryWrapper<PaymentRecordSubjectRe>().eq(PaymentRecordSubjectRe::getPaymentRecordId, record.getPaymentRecordId()));

		//??????????????????????????????????????????
		allotmentRecords(paymentRecordDTO,null,paymentRecordSubjectReList,projectLiquiService);
		return update;
	}

	@Override
	public boolean collection(PaymentRecordDTO paymentRecordDTO) {
		//??????????????????
		boolean save = this.save(paymentRecordDTO);

		List<Integer> subjectIdList = paymentRecordDTO.getSubjectIdList();
		//???????????????????????????????????????
		for (Integer integer : subjectIdList) {
			PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecordDTO.getPaymentRecordId());
			paymentRecordSubjectRe.setSubjectId(integer);
			this.paymentRecordSubjectReService.save(paymentRecordSubjectRe);
		}

		//??????????????????????????????????????????
		allotmentRecords(paymentRecordDTO,subjectIdList,null,projectLiquiService);

		//????????????????????????
		List<PaymentRecord> courtPayment = paymentRecordDTO.getCourtPayment();
		List<PaymentSourceRe> paymentSourceReList=new ArrayList<>();
		if (courtPayment!=null&&courtPayment.size()>0){
			for (PaymentRecord paymentRecord : courtPayment) {
				PaymentSourceRe paymentSourceRe=new PaymentSourceRe();
				paymentSourceRe.setSourceId(paymentRecord.getPaymentRecordId());
				paymentSourceRe.setPaymentRecordId(paymentRecordDTO.getPaymentRecordId());
				paymentSourceRe.setType(100);
				paymentSourceReList.add(paymentSourceRe);

				paymentRecord.setStatus(1);
			}
			//????????????????????????
			paymentSourceReService.saveBatch(paymentSourceReList);
			this.updateBatchById(courtPayment);
		}

		return save;
	}

	@Override
	public boolean deletePaymentRecordRe(Integer paymentRecordId) {

		//????????????????????????
		paymentRecordAssetsReService.remove(new LambdaQueryWrapper<PaymentRecordAssetsRe>()
				.eq(PaymentRecordAssetsRe::getPaymentRecordId, paymentRecordId));

		//?????????????????????????????????
		paymentRecordSubjectReService.remove(new LambdaQueryWrapper<PaymentRecordSubjectRe>()
				.eq(PaymentRecordSubjectRe::getPaymentRecordId, paymentRecordId));

		//??????????????????
		return this.removeById(paymentRecordId);
	}

	@Override
	public boolean updatePaymentRecord(Integer paymentRecordId, Integer status) {
		PaymentRecord paymentRecord = this.getById(paymentRecordId);
		paymentRecord.setStatus(status);
		return this.updateById(paymentRecord);
	}

	@Override
	public void updateCourtPaymentRecordRe(Integer paymentRecordId) {
		//??????????????????????????????
		this.updatePaymentRecord(paymentRecordId,0);

		//????????????????????????
		List<PaymentSourceRe> paymentSourceReList = paymentSourceReService.list(new LambdaQueryWrapper<PaymentSourceRe>().eq(PaymentSourceRe::getSourceId, paymentRecordId));
		for (PaymentSourceRe paymentSourceRe : paymentSourceReList) {

			//??????????????????????????????id??????????????????
			List<PaymentRecord> paymentRecordList = this.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getFatherId, paymentSourceRe.getPaymentRecordId()));
			for (PaymentRecord record : paymentRecordList) {
				record.setStatus(2);

				//??????????????????????????????
				ExpenseRecord expenseRecord = expenseRecordService.getById(record.getExpenseRecordId());
				if (expenseRecord.getStatus() != 2) {
					expenseRecord.setStatus(0);
					expenseRecordService.updateById(expenseRecord);
				}
			}
			this.updateBatchById(paymentRecordList);
			//???????????????????????????
			this.updatePaymentRecord(paymentSourceRe.getPaymentRecordId(),2);
		}
	}

	@Override
	@Transactional
	public void paymentCancellation(Integer paymentRecordId,Integer projectType) {
		PaymentRecord paymentRecord = this.getById(paymentRecordId);
		if (projectType==100){//??????
			//???????????????????????????id??????????????????
			PaymentRecord fatherIPaymentRecord = this.getById(paymentRecord.getFatherId());

			ProjectLiqui project = projectLiquiService.getByProjectId(paymentRecord.getProjectId());

			//???????????????????????????
			projectLiquiService.subtractRepaymentAmount(project,fatherIPaymentRecord.getPaymentAmount());

			//??????????????????????????????????????????????????????????????????????????????????????????
			if (fatherIPaymentRecord.getPaymentType()==300||fatherIPaymentRecord.getPaymentType()==400) {
				this.updatePaymentRecord(fatherIPaymentRecord.getPaymentRecordId(),0);
			}else {//???????????????????????????????????????????????????????????????????????????
				if (fatherIPaymentRecord.getPaymentType()==200){
					//?????????????????????????????????
					this.updatePaymentRecord(fatherIPaymentRecord.getPaymentRecordId(),0);
				}else {
					//???????????????????????????
					this.updatePaymentRecord(fatherIPaymentRecord.getPaymentRecordId(),2);
				}

				//?????????????????????????????????????????????????????????????????????????????????
				List<PaymentSourceRe> paymentSourceReList = paymentSourceReService.list(new LambdaQueryWrapper<PaymentSourceRe>().eq(PaymentSourceRe::getPaymentRecordId, fatherIPaymentRecord.getPaymentRecordId()).eq(PaymentSourceRe::getType, 100));
				for (PaymentSourceRe paymentSourceRe : paymentSourceReList) {
					//??????????????????
					PaymentRecord dkPaymentRecord = this.getById(paymentSourceRe.getSourceId());
					dkPaymentRecord.setStatus(0);
					this.updateById(dkPaymentRecord);
				}
			}

			List<PaymentRecord> paymentRecordList = this.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getFatherId, fatherIPaymentRecord.getPaymentRecordId()));
			for (PaymentRecord record : paymentRecordList) {
				record.setStatus(2);

				//???????????????????????????????????????
				ExpenseRecord expenseRecordUpdate = expenseRecordService.getById(record.getExpenseRecordId());
				if (expenseRecordUpdate.getStatus()!=2){
					expenseRecordUpdate.setStatus(0);
					expenseRecordService.updateById(expenseRecordUpdate);
				}
			}
			this.updateBatchById(paymentRecordList);
		}else {//??????
			if (paymentRecord.getStatus()==1){
				paymentRecord.setStatus(2);
				this.updateById(paymentRecord);
				//???????????????????????????
				projectPaifuService.updateRepaymentAmount(paymentRecord.getProjectId());
			}
		}
	}

	@Override
	public PaymentRecord addPaymentRecord(BigDecimal amount,LocalDate paymentDate,Integer status,Project project,Casee casee,AssetsReSubjectDTO assetsReSubjectDTO,List<JointAuctionAssetsDTO> jointAuctionAssetsDTOList,Integer paymentType,Integer fundsType) {
		PaymentRecord paymentRecord = new PaymentRecord();
		paymentRecord.setPaymentType(paymentType);
		paymentRecord.setFundsType(fundsType);
		paymentRecord.setStatus(status);
		paymentRecord.setPaymentDate(paymentDate);
		paymentRecord.setPaymentAmount(amount);
		paymentRecord.setCaseeId(casee.getCaseeId());
		paymentRecord.setProjectId(project.getProjectId());
		paymentRecord.setCompanyCode(project.getCompanyCode());
		paymentRecord.setCaseeNumber(casee.getCaseeNumber());
		paymentRecord.setSubjectName(assetsReSubjectDTO.getSubjectName());
		this.save(paymentRecord);

		List<PaymentRecordAssetsRe> paymentRecordAssetsReList = new ArrayList<>();

		if (jointAuctionAssetsDTOList!=null){
			//????????????????????????????????????????????????
			for (JointAuctionAssetsDTO jointAuctionAssetsDTO : jointAuctionAssetsDTOList) {
				PaymentRecordAssetsRe paymentRecordAssetsRe = new PaymentRecordAssetsRe();
				paymentRecordAssetsRe.setAssetsReId(jointAuctionAssetsDTO.getAssetsReId());
				paymentRecordAssetsRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
				paymentRecordAssetsReList.add(paymentRecordAssetsRe);
			}
			// ????????????????????????????????????
			paymentRecordAssetsReService.saveBatch(paymentRecordAssetsReList);
		}else {
			//????????????????????????
			PaymentRecordAssetsRe paymentRecordAssetsRe=new PaymentRecordAssetsRe();
			paymentRecordAssetsRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			paymentRecordAssetsRe.setAssetsReId(assetsReSubjectDTO.getAssetsReId());
			paymentRecordAssetsReService.save(paymentRecordAssetsRe);
		}

		List<PaymentRecordSubjectRe> paymentRecordSubjectRes = new ArrayList<>();
		for (Subject subject : assetsReSubjectDTO.getSubjectList()) {
			PaymentRecordSubjectRe paymentRecordSubjectRe = new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setSubjectId(subject.getSubjectId());
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			paymentRecordSubjectRes.add(paymentRecordSubjectRe);
		}
		//???????????????????????????
		paymentRecordSubjectReService.saveBatch(paymentRecordSubjectRes);
		return paymentRecord;
	}

	/**
	 * ????????????????????????
	 * @return
	 */
	@Override
	public BigDecimal queryCompareMoneyBackAmountCount() {
		return this.baseMapper.queryCompareMoneyBackAmountCount(jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * ????????????????????????
	 * @return
	 */
	@Override
	public IPage<MoneyBackMonthlyRank> queryMoneyBackMonthlyRankList(Page page, CountMoneyBackMonthlyRankDTO countMoneyBackMonthlyRankDTO) {
		return this.baseMapper.queryMoneyBackMonthlyRankList(page, countMoneyBackMonthlyRankDTO, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * ??????????????????
	 * @return
	 */
	@Override
	public BigDecimal getTotalRepayments() {
		return this.baseMapper.getTotalRepayments();
	}

	/**
	 * ?????????????????????????????????
	 * @param polylineColumnActive
	 * @param difference
	 * @return
	 */
	@Override
	public Map<String, BigDecimal> getPaymentRecordMap(Integer polylineColumnActive, List<String> difference) {
		return this.baseMapper.getPaymentRecordMap(polylineColumnActive, difference, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	@Override
	public List<PaymentRecordListVO> queryPaifuPaymentRecordList(Integer projectId){
		return this.baseMapper.queryPaifuPaymentRecordList(projectId);
	}

	@Override
	@Transactional
	public Integer savePaifuPaymentRecord(PaymentRecordSaveDTO paymentRecordSaveDTO){
		// ??????????????????????????????
		ExpenseRecordPaifuAssetsReListVO expenseRecordPaifuAssetsReListVO = expenseRecordService.queryPaifuExpenseRecordAssetsReList(paymentRecordSaveDTO.getExpenseRecordId());

		if(paymentRecordSaveDTO.getExpenseRecordStatus()==1){
			// ??????????????????????????????1=?????????
			ExpenseRecord expenseRecord = new ExpenseRecord();
			expenseRecord.setExpenseRecordId(paymentRecordSaveDTO.getExpenseRecordId());
			expenseRecord.setStatus(paymentRecordSaveDTO.getExpenseRecordStatus());
			expenseRecordService.updateById(expenseRecord);
		}

		PaymentRecord paymentRecord = new PaymentRecord();
		BeanCopyUtil.copyBean(paymentRecordSaveDTO,paymentRecord);
		paymentRecord.setProjectId(expenseRecordPaifuAssetsReListVO.getProjectId());
		paymentRecord.setCompanyCode(expenseRecordPaifuAssetsReListVO.getCompanyCode());
		paymentRecord.setCaseeId(expenseRecordPaifuAssetsReListVO.getCaseeId());
		paymentRecord.setCaseeNumber(expenseRecordPaifuAssetsReListVO.getCaseeNumber());
		paymentRecord.setFundsType(expenseRecordPaifuAssetsReListVO.getCostType());
		paymentRecord.setPaymentType(100);
		paymentRecord.setStatus(1);
		Integer save = this.baseMapper.insert(paymentRecord);
		// ??????????????????????????????
		List<PaymentRecordSubjectRe> paymentRecordSubjectRes = new ArrayList<>();
		for(Integer subjectId:paymentRecordSaveDTO.getSubjectIdList()){
			PaymentRecordSubjectRe paymentRecordSubjectRe = new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setSubjectId(subjectId);
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			paymentRecordSubjectRes.add(paymentRecordSubjectRe);
		}
		// ??????????????????????????????
		List<PaymentRecordAssetsRe> paymentRecordAssetsRes = new ArrayList<>();
		for(Integer assetsId : expenseRecordPaifuAssetsReListVO.getAssetsReIdList()){
			PaymentRecordAssetsRe paymentRecordAssetsRe = new PaymentRecordAssetsRe();
			paymentRecordAssetsRe.setAssetsReId(assetsId);
			paymentRecordAssetsRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			paymentRecordAssetsRes.add(paymentRecordAssetsRe);
		}
		paymentRecordSubjectReService.saveBatch(paymentRecordSubjectRes);
		paymentRecordAssetsReService.saveBatch(paymentRecordAssetsRes);
		// ???????????????????????????
		projectPaifuService.updateRepaymentAmount(paymentRecord.getProjectId());
		return save;
	}

	/**
	 * ?????????id????????????????????????????????????????????????????????????????????????
	 *
	 * @param projectLiQuiAndSubjectListVO
	 * @param paymentRecordList
	 * @return
	 */
	@Override
	@Transactional
	public void addPaymentRecordByProjectIdAndPaymentRecordList(ProjectLiQuiAndSubjectListVO projectLiQuiAndSubjectListVO, List<PaymentRecord> paymentRecordList) {
		int add = 0;

		this.saveBatch(paymentRecordList);

		// ??????????????????????????????
		List<PaymentRecordSubjectRe> paymentRecordSubjectRes = new ArrayList<>();
		for (ProjectSubjectVO projectSubjectVO : projectLiQuiAndSubjectListVO.getProjectSubjectVOList()) {
			for (PaymentRecord paymentRecord : paymentRecordList) {
				PaymentRecordSubjectRe paymentRecordSubjectRe = new PaymentRecordSubjectRe();
				paymentRecordSubjectRe.setSubjectId(projectSubjectVO.getSubjectId());
				paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
				paymentRecordSubjectRes.add(paymentRecordSubjectRe);
			}
		}

		//???????????????????????????
		paymentRecordSubjectReService.saveBatch(paymentRecordSubjectRes);

		projectPaifuService.updateRepaymentAmount(projectLiQuiAndSubjectListVO.getProjectLiqui().getProjectId());
	}


	public void allotmentRecords(PaymentRecordDTO paymentRecordDTO, List<Integer> subjectIdList, List<PaymentRecordSubjectRe> paymentRecordSubjectReList, ProjectLiquiService projectLiquiService) {//??????????????????
		//???????????????????????????
		ProjectLiqui projectLiqui = projectLiquiService.getByProjectId(paymentRecordDTO.getProjectId());
		projectLiquiService.addRepaymentAmount(projectLiqui,paymentRecordDTO.getPaymentAmount());

		for (PaymentRecordAddDTO paymentRecord : paymentRecordDTO.getPaymentRecordList()) {
			paymentRecord.setProjectId(paymentRecordDTO.getProjectId());
			paymentRecord.setExpenseRecordId(paymentRecord.getExpenseRecordId());
			paymentRecord.setCompanyCode(paymentRecordDTO.getCompanyCode());
			paymentRecord.setFatherId(paymentRecordDTO.getPaymentRecordId());
			paymentRecord.setPaymentDate(LocalDate.now());
			paymentRecord.setPaymentType(paymentRecordDTO.getPaymentType());
			paymentRecord.setStatus(1);
			//????????????????????????
			this.save(paymentRecord);

			if (subjectIdList!=null){
				//?????????????????????????????????????????????
				for (Integer integer : subjectIdList) {
					PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
					paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
					paymentRecordSubjectRe.setSubjectId(integer);
					this.paymentRecordSubjectReService.save(paymentRecordSubjectRe);
				}
			}else {
				//?????????????????????????????????????????????
				for (PaymentRecordSubjectRe subjectRe : paymentRecordSubjectReList) {
					PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
					paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
					paymentRecordSubjectRe.setSubjectId(subjectRe.getSubjectId());
					this.paymentRecordSubjectReService.save(paymentRecordSubjectRe);
				}
			}

			List<ExpenseRecordAssetsRe> expenseRecordAssetsReList = expenseRecordAssetsReService.list(new LambdaQueryWrapper<ExpenseRecordAssetsRe>().eq(ExpenseRecordAssetsRe::getExpenseRecordId, paymentRecord.getExpenseRecordId()));

			for (ExpenseRecordAssetsRe expenseRecordAssetsRe : expenseRecordAssetsReList) {
				//??????????????????????????????
				PaymentRecordAssetsRe paymentRecordAssetsRe=new PaymentRecordAssetsRe();
				paymentRecordAssetsRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
				paymentRecordAssetsRe.setAssetsReId(expenseRecordAssetsRe.getAssetsReId());
				paymentRecordAssetsReService.save(paymentRecordAssetsRe);
			}

			//????????????????????????
			ExpenseRecord expenseRecord = expenseRecordService.getById(paymentRecord.getExpenseRecordId());
			if (expenseRecord.getCostAmount().compareTo(paymentRecord.getPaymentAmount().add(paymentRecord.getPaymentSumAmount()))==0){
				expenseRecord.setExpenseRecordId(paymentRecord.getExpenseRecordId());
				expenseRecord.setStatus(1);
				expenseRecordService.updateById(expenseRecord);
			}
		}
	}
}
