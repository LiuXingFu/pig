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
import com.pig4cloud.pig.admin.api.dto.SubjectAddressDTO;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.SubjectBankLoanReDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.mapper.SubjectBankLoanReMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.SubjectIdsOrSubjectBankLoanReIdsVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
	@Transactional
	public boolean removeSubjectBankLoanRe(Integer subjectBankLoanId,Integer bankLoanId,Integer subjectId, String name) {
		BankLoan bankLoan = bankLoanService.getById(bankLoanId);
		String subjectName = bankLoan.getSubjectName();
		String[] split = subjectName.split(",");
		if (split.length>1){
			if (subjectName.contains(name)){
				int index = subjectName.indexOf(name);
				if (index!=0){
					if (subjectName.substring(index-1,index).equals(",")){
						subjectName=subjectName.replaceAll(","+name,"");
					}else {
						subjectName=subjectName.replaceAll(name+",","");
					}
				}else {
					subjectName=subjectName.replaceAll(name+",","");
				}
			}
		}else {
			if (subjectName.contains(name)){
				subjectName=subjectName.replaceAll(name,"");
			}
		}
		bankLoan.setSubjectName(subjectName);

		//修改债务人名称
		bankLoanService.updateById(bankLoan);
		//查询抵押信息
		List<MortgageAssetsRecords> mortgageAssetsRecordsList = mortgageAssetsRecordsService.list(new LambdaQueryWrapper<MortgageAssetsRecords>().eq(MortgageAssetsRecords::getBankLoanId, bankLoanId));
		for (MortgageAssetsRecords mortgageAssetsRecords : mortgageAssetsRecordsList) {

			//查询抵押关联财产信息
			List<MortgageAssetsRe> mortgageAssetsReList = mortgageAssetsReService.list(new LambdaQueryWrapper<MortgageAssetsRe>().eq(MortgageAssetsRe::getMortgageAssetsRecordsId, mortgageAssetsRecords.getMortgageAssetsRecordsId()));

			for (MortgageAssetsRe mortgageAssetsRe : mortgageAssetsReList) {
//				String mortgageAssetsRecordsSubjectName = mortgageAssetsRe.getSubjectName();
//				if (mortgageAssetsRecordsSubjectName.contains(name)){
//					mortgageAssetsRecordsSubjectName=mortgageAssetsRecordsSubjectName.replaceAll(name+",","");
//				}

				String mortgageAssetsRecordsSubjectName = mortgageAssetsRe.getSubjectName();
				String[] mortgageAssetsRecordsSubjectNameSplit = mortgageAssetsRecordsSubjectName.split(",");
				if (mortgageAssetsRecordsSubjectNameSplit.length>1){
					if (mortgageAssetsRecordsSubjectName.contains(name)){
						int index = mortgageAssetsRecordsSubjectName.indexOf(name);
						if (index!=0){
							if (mortgageAssetsRecordsSubjectName.substring(index-1,index).equals(",")){
								mortgageAssetsRecordsSubjectName=mortgageAssetsRecordsSubjectName.replaceAll(","+name,"");
							}else {
								mortgageAssetsRecordsSubjectName=mortgageAssetsRecordsSubjectName.replaceAll(name+",","");
							}
						}else {
							mortgageAssetsRecordsSubjectName=mortgageAssetsRecordsSubjectName.replaceAll(name+",","");
						}
					}
				}else {
					if (mortgageAssetsRecordsSubjectName.contains(name)){
						mortgageAssetsRecordsSubjectName=mortgageAssetsRecordsSubjectName.replaceAll(name,"");
					}
				}

				mortgageAssetsRe.setSubjectName(mortgageAssetsRecordsSubjectName);
				//修改财产关联信息
				mortgageAssetsReService.updateById(mortgageAssetsRe);

				//查询财产关联债务人信息
				List<MortgageAssetsSubjectRe> mortgageAssetsSubjectReList = mortgageAssetsSubjectReService.list(new LambdaQueryWrapper<MortgageAssetsSubjectRe>().eq(MortgageAssetsSubjectRe::getMortgageAssetsReId, mortgageAssetsRe.getMortgageAssetsReId()));

				if (mortgageAssetsSubjectReList.size()==1 && mortgageAssetsSubjectReList.get(0).getSubjectId() == subjectId){//如果当前财产只关联了一个债务人，删除债务人时需把抵押信息和财产关联信息删除
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
		subjectBankLoanRe.setDescribes(subjectBankLoanReDTO.getDescribes());

		Subject subject = new Subject();
		BeanCopyUtil.copyBean(subjectBankLoanReDTO,subject);
		remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM);

		return this.baseMapper.updateById(subjectBankLoanRe);
	}

	@Override
	@Transactional
	public SubjectIdsOrSubjectBankLoanReIdsVO saveSubjectOrSubjectBankLoanRe(List<SubjectAddressDTO> subjectAddressDTOList) {

		SubjectIdsOrSubjectBankLoanReIdsVO subjectIdsOrSubjectBankLoanReIdsVO = new SubjectIdsOrSubjectBankLoanReIdsVO();

		List<Integer> subjectIds = new ArrayList();
		BankLoan bankLoan = new BankLoan();
		List<Integer> subjectBankLoanReListIds = new ArrayList<>();

		String subjectNames = "";//银行借贷所有债务人名称
		for (SubjectAddressDTO subjectAddressDTO : subjectAddressDTOList) {
			//添加债务人主体信息
			Subject subject = new Subject();
			BeanCopyUtil.copyBean(subjectAddressDTO, subject);
			Subject subjectData = remoteSubjectService.saveSubject(subject, SecurityConstants.FROM).getData();
			bankLoan = bankLoanService.getById(subjectAddressDTO.getBankLoanId());
			if (bankLoan != null) {
				if (bankLoan.getSubjectName() == null) {
					if (subjectNames == "") {
						subjectNames = subject.getName();
					} else {
						subjectNames += "," + subject.getName();
					}
				} else {
					subjectNames = bankLoan.getSubjectName();
					if (!bankLoan.getSubjectName().contains(subject.getName())) {
						subjectNames += "," + subject.getName();
					}
				}
			}
			List<Address> addressList = subjectAddressDTO.getAddressList();
			for (Address address : addressList) {
				address.setUserId(subjectData.getSubjectId());
				address.setType(1);
				//添加债务人地址信息
				remoteAddressService.saveOrUpdate(address,SecurityConstants.FROM);
			}
			//添加主体关联银行借贷信息
			SubjectBankLoanRe subjectBankLoanRe = new SubjectBankLoanRe();
			subjectBankLoanRe.setSubjectId(subjectData.getSubjectId());
			subjectBankLoanRe.setDebtType(subjectAddressDTO.getDebtType());
			subjectBankLoanRe.setBankLoanId(subjectAddressDTO.getBankLoanId());
			subjectBankLoanRe.setDescribes(subjectAddressDTO.getDescribes());
			this.save(subjectBankLoanRe);
			subjectBankLoanReListIds.add(subjectBankLoanRe.getSubjectBankLoanId());
			subjectIds.add(subjectData.getSubjectId());
		}
		bankLoan.setSubjectName(subjectNames);
		bankLoanService.updateById(bankLoan);

		subjectIdsOrSubjectBankLoanReIdsVO.setSubjectIds(subjectIds);
		subjectIdsOrSubjectBankLoanReIdsVO.setSubjectBankLoanReListIds(subjectBankLoanReListIds);
		return subjectIdsOrSubjectBankLoanReIdsVO;
	}

	@Override
	public List<Integer> selectSubjectId(Integer bankLoanId) {
		return this.baseMapper.selectSubjectId(bankLoanId);
	}
}
