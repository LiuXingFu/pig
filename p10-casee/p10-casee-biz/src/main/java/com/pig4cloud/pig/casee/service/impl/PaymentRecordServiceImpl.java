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
import com.pig4cloud.pig.casee.mapper.PaymentRecordMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.MoneyBackMonthlyRank;
import com.pig4cloud.pig.casee.vo.PaymentRecordCourtPaymentVO;
import com.pig4cloud.pig.casee.vo.PaymentRecordVO;
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
 * 回款详细记录表
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
				// 添加回款记录财产关联信息
				paymentRecordAssetsReService.save(paymentRecordAssetsRe);
			}
		}
		for (Integer subjectId : paymentRecordDTO.getSubjectIdList()) {
			PaymentRecordSubjectRe paymentRecordSubjectRe = new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setSubjectId(subjectId);
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecordDTO.getPaymentRecordId());
			//添加关联债务人信息
			paymentRecordSubjectReService.save(paymentRecordSubjectRe);
		}
		return save;
	}

	@Override
	@Transactional
	public boolean distribute(PaymentRecordDTO paymentRecordDTO) {
		//查询当前分配款项记录修改状态为已分配
		PaymentRecord record = this.getById(paymentRecordDTO.getPaymentRecordId());
		record.setStatus(1);
		boolean update = this.updateById(record);

		List<PaymentRecordSubjectRe> paymentRecordSubjectReList = paymentRecordSubjectReService.list(new LambdaQueryWrapper<PaymentRecordSubjectRe>().eq(PaymentRecordSubjectRe::getPaymentRecordId, record.getPaymentRecordId()));

		//添加分配款项记录以及关联信息
		allotmentRecords(paymentRecordDTO,null,paymentRecordSubjectReList);
		return update;
	}

	@Override
	public boolean collection(PaymentRecordDTO paymentRecordDTO) {
		if (paymentRecordDTO.getPaymentType().equals(300)){//自动履行到申请人
			paymentRecordDTO.setStatus(1);
		}
		//添加领款信息
		boolean save = this.save(paymentRecordDTO);

		List<Integer> subjectIdList = paymentRecordDTO.getSubjectIdList();
		//添加领款记录与主体关联信息
		for (Integer integer : subjectIdList) {
			PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecordDTO.getPaymentRecordId());
			paymentRecordSubjectRe.setSubjectId(integer);
			this.paymentRecordSubjectReService.save(paymentRecordSubjectRe);
		}

		//添加分配款项记录以及关联信息
		allotmentRecords(paymentRecordDTO,subjectIdList,null);

		//修改到款记录状态
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
			//添加回款来源信息
			paymentSourceReService.saveBatch(paymentSourceReList);
			this.updateBatchById(courtPayment);
		}

		return save;
	}

	@Override
	public boolean deletePaymentRecordRe(Integer paymentRecordId) {

		//删除回款关联信息
		paymentRecordAssetsReService.remove(new LambdaQueryWrapper<PaymentRecordAssetsRe>()
				.eq(PaymentRecordAssetsRe::getPaymentRecordId, paymentRecordId));

		//删除回款关联债务人信息
		paymentRecordSubjectReService.remove(new LambdaQueryWrapper<PaymentRecordSubjectRe>()
				.eq(PaymentRecordSubjectRe::getPaymentRecordId, paymentRecordId));

		//删除回款信息
		return this.removeById(paymentRecordId);
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
			//循环当前拍卖公告联合拍卖财产信息
			for (JointAuctionAssetsDTO jointAuctionAssetsDTO : jointAuctionAssetsDTOList) {
				PaymentRecordAssetsRe paymentRecordAssetsRe = new PaymentRecordAssetsRe();
				paymentRecordAssetsRe.setAssetsReId(jointAuctionAssetsDTO.getAssetsReId());
				paymentRecordAssetsRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
				paymentRecordAssetsReList.add(paymentRecordAssetsRe);
			}
			// 添加回款记录财产关联信息
			paymentRecordAssetsReService.saveBatch(paymentRecordAssetsReList);
		}else {
			//添加关联财产信息
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
		//添加关联债务人信息
		paymentRecordSubjectReService.saveBatch(paymentRecordSubjectRes);
		return paymentRecord;
	}

	/**
	 * 查询较去年回款额
	 * @return
	 */
	@Override
	public BigDecimal queryCompareMoneyBackAmountCount() {
		return this.baseMapper.queryCompareMoneyBackAmountCount(jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * 本月回款额月排名
	 * @return
	 */
	@Override
	public IPage<MoneyBackMonthlyRank> queryMoneyBackMonthlyRankList(Page page, CountMoneyBackMonthlyRankDTO countMoneyBackMonthlyRankDTO) {
		return this.baseMapper.queryMoneyBackMonthlyRankList(page, countMoneyBackMonthlyRankDTO, jurisdictionUtilsService.queryByInsId("PLAT_"), jurisdictionUtilsService.queryByOutlesId("PLAT_"));
	}

	/**
	 * 本月总回款额
	 * @return
	 */
	@Override
	public BigDecimal getTotalRepayments() {
		return this.baseMapper.getTotalRepayments();
	}

	/**
	 * 根据时间集合查询回款额
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
		// 查询费用产生记录详情
		ExpenseRecordPaifuAssetsReListVO expenseRecordPaifuAssetsReListVO = expenseRecordService.queryPaifuExpenseRecordAssetsReList(paymentRecordSaveDTO.getExpenseRecordId());

		if(paymentRecordSaveDTO.getExpenseRecordStatus()==1){
			// 更新费用产生记录状态1=已还款
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
		Integer save = this.baseMapper.insert(paymentRecord);
		// 批量保存回款主体关联
		List<PaymentRecordSubjectRe> paymentRecordSubjectRes = new ArrayList<>();
		for(Integer subjectId:paymentRecordSaveDTO.getSubjectIdList()){
			PaymentRecordSubjectRe paymentRecordSubjectRe = new PaymentRecordSubjectRe();
			paymentRecordSubjectRe.setSubjectId(subjectId);
			paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			paymentRecordSubjectRes.add(paymentRecordSubjectRe);
		}
		// 批量保存回款财产关联
		List<PaymentRecordAssetsRe> paymentRecordAssetsRes = new ArrayList<>();
		for(Integer assetsId : expenseRecordPaifuAssetsReListVO.getAssetsReIdList()){
			PaymentRecordAssetsRe paymentRecordAssetsRe = new PaymentRecordAssetsRe();
			paymentRecordAssetsRe.setAssetsReId(assetsId);
			paymentRecordAssetsRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
			paymentRecordAssetsRes.add(paymentRecordAssetsRe);
		}
		paymentRecordSubjectReService.saveBatch(paymentRecordSubjectRes);
		paymentRecordAssetsReService.saveBatch(paymentRecordAssetsRes);
		// 更新项目回款总金额
		projectPaifuService.updateRepaymentAmount(paymentRecord.getProjectId());
		return save;
	}

	public void allotmentRecords(PaymentRecordDTO paymentRecordDTO,List<Integer> subjectIdList,List<PaymentRecordSubjectRe> paymentRecordSubjectReList){//分配款项记录
		for (PaymentRecordAddDTO paymentRecord : paymentRecordDTO.getPaymentRecordList()) {
			paymentRecord.setProjectId(paymentRecordDTO.getProjectId());
			paymentRecord.setExpenseRecordId(paymentRecord.getExpenseRecordId());
			paymentRecord.setCompanyCode(paymentRecordDTO.getCompanyCode());
			paymentRecord.setFatherId(paymentRecordDTO.getPaymentRecordId());
			paymentRecord.setPaymentDate(LocalDate.now());
			paymentRecord.setPaymentType(paymentRecordDTO.getPaymentType());
			paymentRecord.setStatus(1);
			//添加分配款项信息
			this.save(paymentRecord);

			if (subjectIdList!=null){
				//添加分配款项明细与主体关联信息
				for (Integer integer : subjectIdList) {
					PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
					paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
					paymentRecordSubjectRe.setSubjectId(integer);
					this.paymentRecordSubjectReService.save(paymentRecordSubjectRe);
				}
			}else {
				//添加分配款项明细与主体关联信息
				for (PaymentRecordSubjectRe subjectRe : paymentRecordSubjectReList) {
					PaymentRecordSubjectRe paymentRecordSubjectRe=new PaymentRecordSubjectRe();
					paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
					paymentRecordSubjectRe.setSubjectId(subjectRe.getSubjectId());
					this.paymentRecordSubjectReService.save(paymentRecordSubjectRe);
				}
			}

			List<ExpenseRecordAssetsRe> expenseRecordAssetsReList = expenseRecordAssetsReService.list(new LambdaQueryWrapper<ExpenseRecordAssetsRe>().eq(ExpenseRecordAssetsRe::getExpenseRecordId, paymentRecord.getExpenseRecordId()));

			for (ExpenseRecordAssetsRe expenseRecordAssetsRe : expenseRecordAssetsReList) {
				//添加到款信息关联财产
				PaymentRecordAssetsRe paymentRecordAssetsRe=new PaymentRecordAssetsRe();
				paymentRecordAssetsRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
				paymentRecordAssetsRe.setAssetsReId(expenseRecordAssetsRe.getAssetsReId());
				paymentRecordAssetsReService.save(paymentRecordAssetsRe);
			}

			//修改明细记录状态
			ExpenseRecord expenseRecord = expenseRecordService.getById(paymentRecord.getExpenseRecordId());
			if (expenseRecord.getCostAmount().compareTo(paymentRecord.getPaymentAmount().add(paymentRecord.getPaymentSumAmount()))==0){
				expenseRecord.setExpenseRecordId(paymentRecord.getExpenseRecordId());
				expenseRecord.setStatus(1);
				expenseRecordService.updateById(expenseRecord);
			}
		}
	}
}
