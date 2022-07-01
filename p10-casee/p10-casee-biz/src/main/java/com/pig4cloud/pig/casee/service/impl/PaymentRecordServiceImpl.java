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
		allotmentRecords(paymentRecordDTO,null,paymentRecordSubjectReList,projectLiquiService);
		return update;
	}

	@Override
	public boolean collection(PaymentRecordDTO paymentRecordDTO) {
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
		allotmentRecords(paymentRecordDTO,subjectIdList,null,projectLiquiService);

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
	public boolean updatePaymentRecord(Integer paymentRecordId, Integer status) {
		PaymentRecord paymentRecord = this.getById(paymentRecordId);
		paymentRecord.setStatus(status);
		return this.updateById(paymentRecord);
	}

	@Override
	public void updateCourtPaymentRecordRe(Integer paymentRecordId) {
		//修改到款信息为未分配
		this.updatePaymentRecord(paymentRecordId,0);

		//查询回款来源信息
		List<PaymentSourceRe> paymentSourceReList = paymentSourceReService.list(new LambdaQueryWrapper<PaymentSourceRe>().eq(PaymentSourceRe::getSourceId, paymentRecordId));
		for (PaymentSourceRe paymentSourceRe : paymentSourceReList) {

			//根据回款来源信息回款id修改回款信息
			List<PaymentRecord> paymentRecordList = this.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getFatherId, paymentSourceRe.getPaymentRecordId()));
			for (PaymentRecord record : paymentRecordList) {
				record.setStatus(2);

				//修改费用明细记录状态
				ExpenseRecord expenseRecord = expenseRecordService.getById(record.getExpenseRecordId());
				if (expenseRecord.getStatus() != 2) {
					expenseRecord.setStatus(0);
					expenseRecordService.updateById(expenseRecord);
				}
			}
			this.updateBatchById(paymentRecordList);
			//修改领款信息为作废
			this.updatePaymentRecord(paymentSourceRe.getPaymentRecordId(),2);
		}
	}

	@Override
	@Transactional
	public void paymentCancellation(Integer paymentRecordId,Integer projectType) {
		PaymentRecord paymentRecord = this.getById(paymentRecordId);
		if (projectType==100){//清收
			//根据该条回款记录父id查询分配类型
			PaymentRecord fatherIPaymentRecord = this.getById(paymentRecord.getFatherId());

			ProjectLiqui project = projectLiquiService.getByProjectId(paymentRecord.getProjectId());

			//修改项目回款总金额
			projectLiquiService.subtractRepaymentAmount(project,fatherIPaymentRecord.getPaymentAmount());

			//如果当前分配类型是履行到申请人或资产抵偿则修改该状态为未分配
			if (fatherIPaymentRecord.getPaymentType()==300||fatherIPaymentRecord.getPaymentType()==400) {
				this.updatePaymentRecord(fatherIPaymentRecord.getPaymentRecordId(),0);
			}else {//如果当前分配类型是领款，修改领款、到款状态为未分配
				if (fatherIPaymentRecord.getPaymentType()==200){
					//修改到款款状态为未领款
					this.updatePaymentRecord(fatherIPaymentRecord.getPaymentRecordId(),0);
				}else {
					//修改领款状态为作废
					this.updatePaymentRecord(fatherIPaymentRecord.getPaymentRecordId(),2);
				}

				//领款时可能领多条到款信息所以这里可能会查出多条到款记录
				List<PaymentSourceRe> paymentSourceReList = paymentSourceReService.list(new LambdaQueryWrapper<PaymentSourceRe>().eq(PaymentSourceRe::getPaymentRecordId, fatherIPaymentRecord.getPaymentRecordId()).eq(PaymentSourceRe::getType, 100));
				for (PaymentSourceRe paymentSourceRe : paymentSourceReList) {
					//查询到款信息
					PaymentRecord dkPaymentRecord = this.getById(paymentSourceRe.getSourceId());
					dkPaymentRecord.setStatus(0);
					this.updateById(dkPaymentRecord);
				}
			}

			List<PaymentRecord> paymentRecordList = this.list(new LambdaQueryWrapper<PaymentRecord>().eq(PaymentRecord::getFatherId, fatherIPaymentRecord.getPaymentRecordId()));
			for (PaymentRecord record : paymentRecordList) {
				record.setStatus(2);

				//修改费用产生记录状态为正常
				ExpenseRecord expenseRecordUpdate = expenseRecordService.getById(record.getExpenseRecordId());
				if (expenseRecordUpdate.getStatus()!=2){
					expenseRecordUpdate.setStatus(0);
					expenseRecordService.updateById(expenseRecordUpdate);
				}
			}
			this.updateBatchById(paymentRecordList);
		}else {//拍辅
			if (paymentRecord.getStatus()==1){
				paymentRecord.setStatus(2);
				this.updateById(paymentRecord);
				//修改项目回款总金额
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
		paymentRecord.setStatus(1);
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

	/**
	 * 将项目id与回款详细记录集合添加到回款详细记录表与相关表中
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

		// 批量保存回款财产关联
		List<PaymentRecordSubjectRe> paymentRecordSubjectRes = new ArrayList<>();
		for (ProjectSubjectVO projectSubjectVO : projectLiQuiAndSubjectListVO.getProjectSubjectVOList()) {
			for (PaymentRecord paymentRecord : paymentRecordList) {
				PaymentRecordSubjectRe paymentRecordSubjectRe = new PaymentRecordSubjectRe();
				paymentRecordSubjectRe.setSubjectId(projectSubjectVO.getSubjectId());
				paymentRecordSubjectRe.setPaymentRecordId(paymentRecord.getPaymentRecordId());
				paymentRecordSubjectRes.add(paymentRecordSubjectRe);
			}
		}

		//添加关联债务人信息
		paymentRecordSubjectReService.saveBatch(paymentRecordSubjectRes);

		projectPaifuService.updateRepaymentAmount(projectLiQuiAndSubjectListVO.getProjectLiqui().getProjectId());
	}


	public void allotmentRecords(PaymentRecordDTO paymentRecordDTO, List<Integer> subjectIdList, List<PaymentRecordSubjectRe> paymentRecordSubjectReList, ProjectLiquiService projectLiquiService) {//分配款项记录
		//修改项目回款总金额
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
