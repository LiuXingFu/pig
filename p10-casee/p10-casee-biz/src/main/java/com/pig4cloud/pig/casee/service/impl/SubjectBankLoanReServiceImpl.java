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
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.SubjectBankLoanReDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.mapper.SubjectBankLoanReMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 主体关联银行借贷表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:44
 */
@Service
public class SubjectBankLoanReServiceImpl extends ServiceImpl<SubjectBankLoanReMapper, SubjectBankLoanRe> implements SubjectBankLoanReService {
	@Autowired
	private RemoteSubjectService remoteSubjectService;
	@Autowired
	private RemoteAddressService remoteAddressService;
	@Autowired
	private BankLoanService bankLoanService;
	@Autowired
	private MortgageAssetsReService mortgageAssetsReService;
	@Autowired
	private MortgageAssetsSubjectReService mortgageAssetsSubjectReService;
	@Autowired
	private MortgageAssetsRecordsService mortgageAssetsRecordsService;


	@Override
	public boolean removeSubjectAndBankLoan(Integer bankLoanId, List<Integer> subjectIds) {
		for (Integer subjectId : subjectIds) {
			//删除主体信息
			remoteSubjectService.removeById(subjectId, SecurityConstants.FROM);
			//删除主体关联地址信息
			remoteAddressService.removeUserIdAndType(subjectId,1, SecurityConstants.FROM);
			//删除主体关联银行借贷信息
			this.remove(new LambdaQueryWrapper<SubjectBankLoanRe>().eq(SubjectBankLoanRe::getBankLoanId, bankLoanId).eq(SubjectBankLoanRe::getSubjectId, subjectId));
		}
		return true;
	}

	@Override
	public boolean removeSubjectBankLoanRe(Integer subjectBankLoanId,Integer bankLoanId,Integer subjectId, String name) {
		BankLoan bankLoan = bankLoanService.getById(bankLoanId);
		String subjectName = bankLoan.getSubjectName();
		String[] split = subjectName.split(",");
		if (split.length>1){
			if (subjectName.contains(name)){
				subjectName=subjectName.replaceAll(name+",","");
			}
		}else {
			if (subjectName.contains(name)){
				subjectName=subjectName.replaceAll(name,"");
			}
		}
		bankLoan.setSubjectName(subjectName);

//		只有一个债务人时删除债务人，银行债务人名称没有变动，上面那个方法要改
		//修改债务人名称
		bankLoanService.updateById(bankLoan);
		//查询抵押信息
		List<MortgageAssetsRecords> mortgageAssetsRecordsList = mortgageAssetsRecordsService.list(new LambdaQueryWrapper<MortgageAssetsRecords>().eq(MortgageAssetsRecords::getBankLoanId, bankLoanId));
		for (MortgageAssetsRecords mortgageAssetsRecords : mortgageAssetsRecordsList) {
			String mortgageAssetsRecordsSubjectName = mortgageAssetsRecords.getSubjectName();
			if (mortgageAssetsRecordsSubjectName.contains(name)){
				mortgageAssetsRecordsSubjectName=mortgageAssetsRecordsSubjectName.replaceAll(name+",","");
			}
			mortgageAssetsRecords.setSubjectName(mortgageAssetsRecordsSubjectName);
			//修改抵押信息
			mortgageAssetsRecordsService.updateById(mortgageAssetsRecords);

			//查询抵押关联财产信息
			List<MortgageAssetsRe> mortgageAssetsReList = mortgageAssetsReService.list(new LambdaQueryWrapper<MortgageAssetsRe>().eq(MortgageAssetsRe::getMortgageAssetsRecordsId, mortgageAssetsRecords.getMortgageAssetsRecordsId()));

			for (MortgageAssetsRe mortgageAssetsRe : mortgageAssetsReList) {
				//查询财产关联债务人信息
				List<MortgageAssetsSubjectRe> mortgageAssetsSubjectReList = mortgageAssetsSubjectReService.list(new LambdaQueryWrapper<MortgageAssetsSubjectRe>().eq(MortgageAssetsSubjectRe::getMortgageAssetsReId, mortgageAssetsRe.getMortgageAssetsReId()));
				if (mortgageAssetsSubjectReList.size()==1){//如果当前财产只关联了一个债务人，删除债务人时需把抵押信息和财产关联信息删除
					mortgageAssetsRecordsService.removeById(mortgageAssetsRecords);
					mortgageAssetsReService.remove(new LambdaQueryWrapper<MortgageAssetsRe>().eq(MortgageAssetsRe::getMortgageAssetsReId,mortgageAssetsRe.getMortgageAssetsReId()));
				}
				for (MortgageAssetsSubjectRe mortgageAssetsSubjectRe : mortgageAssetsSubjectReList) {//删除财产关联债务人信息
					mortgageAssetsSubjectReService.remove(new LambdaQueryWrapper<MortgageAssetsSubjectRe>().eq(MortgageAssetsSubjectRe::getSubjectId,subjectId).eq(MortgageAssetsSubjectRe::getMortgageAssetsReId,mortgageAssetsRe.getMortgageAssetsReId()));
				}
			}
		}
		return this.removeById(subjectBankLoanId);
	}

	@Override
	public Integer modifySubjectBySubjectBankLoanId(SubjectBankLoanReDTO subjectBankLoanReDTO) {
		SubjectBankLoanRe subjectBankLoanRe=new SubjectBankLoanRe();
		subjectBankLoanRe.setSubjectBankLoanId(subjectBankLoanReDTO.getSubjectBankLoanId());
		subjectBankLoanRe.setDebtType(subjectBankLoanReDTO.getDebtType());

		Subject subject = new Subject();
		BeanCopyUtil.copyBean(subjectBankLoanReDTO,subject);
		remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM);

		return this.baseMapper.updateById(subjectBankLoanRe);
	}

	@Override
	public List<Integer> selectSubjectId(Integer bankLoanId) {
		return this.baseMapper.selectSubjectId(bankLoanId);
	}
}
