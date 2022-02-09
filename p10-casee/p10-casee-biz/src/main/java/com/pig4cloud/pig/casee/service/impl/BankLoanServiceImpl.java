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
import com.pig4cloud.pig.admin.api.dto.SubjectAddressDTO;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.AssetsDTO;
import com.pig4cloud.pig.casee.dto.BankLoanDTO;
import com.pig4cloud.pig.casee.dto.BankLoanInformationDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.AssetsBankLoanRe;
import com.pig4cloud.pig.casee.entity.BankLoan;
import com.pig4cloud.pig.casee.entity.SubjectBankLoanRe;
import com.pig4cloud.pig.casee.mapper.BankLoanMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.BankLoanInformationVO;
import com.pig4cloud.pig.casee.vo.BankLoanVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 银行借贷表
 *
 * @author Mjh
 * @date 2022-01-29 10:20:00
 */
@Service
public class BankLoanServiceImpl extends ServiceImpl<BankLoanMapper, BankLoan> implements BankLoanService {
	@Autowired
	RemoteSubjectService remoteSubjectService;

	@Autowired
	RemoteAddressService remoteAddressService;

	@Autowired
	SubjectBankLoanReService subjectBankLoanReService;

	@Autowired
	AssetsBankLoanReService assetsBankLoanReService;

	@Autowired
	AssetsService assetsService;

	@Override
	@Transactional
	public boolean saveBankLoanDebtorPawn(BankLoanDTO bankLoanDTO) {
		//银行借贷信息
		BankLoan bankLoan=new BankLoan();

		//财产关联银行借贷信息
		AssetsBankLoanRe assetsBankLoanRe=new AssetsBankLoanRe();

		//抵押财产信息
		Assets assets=new Assets();
		assets.setType(20200);//默认实体财产类型

		BeanCopyUtil.copyBean(bankLoanDTO,bankLoan);

		BigDecimal principal = bankLoan.getPrincipal();
		BigDecimal interest = bankLoan.getInterest();

		bankLoan.setRental(principal.add(interest));

		//添加银行借贷信息
		boolean save = this.save(bankLoan);

		//添加主体关联银行借贷信息
		if (bankLoanDTO.getSubjectIdList().size()>0){
			List<SubjectBankLoanRe> subjectBankLoanReList = subjectBankLoanReService.list(new LambdaQueryWrapper<SubjectBankLoanRe>().isNull(SubjectBankLoanRe::getBankLoanId).in(SubjectBankLoanRe::getSubjectId, bankLoanDTO.getSubjectIdList()));
			for (SubjectBankLoanRe bankLoanRe : subjectBankLoanReList) {
				bankLoanRe.setBankLoanId(bankLoan.getBankLoanId());
			}
			subjectBankLoanReService.updateBatchById(subjectBankLoanReList);
		}

		//添加抵押财产信息
		List<AssetsDTO> assetsDTOList = bankLoanDTO.getAssetsDTOList();
		if (assetsDTOList!=null){
			for (AssetsDTO assetsDTO : assetsDTOList) {
				BeanCopyUtil.copyBean(assetsDTO,assets);
				assetsService.save(assets);//添加财产信息

				Address address = assetsDTO.getAddress();
				address.setType(4);
				address.setUserId(assets.getAssetsId());
				remoteAddressService.saveAddress(address, SecurityConstants.FROM);//添加财产地址信息

				//添加财产关联银行借贷信息
				assetsBankLoanRe.setAssetsId(assets.getAssetsId());
				assetsBankLoanRe.setBankLoanId(bankLoan.getBankLoanId());
				assetsBankLoanRe.setSubjectId(assetsDTO.getSubjectId());
				assetsBankLoanRe.setMortgageTime(assetsDTO.getMortgageTime());
				assetsBankLoanRe.setMortgageAmount(assetsDTO.getMortgageAmount());
				assetsBankLoanReService.save(assetsBankLoanRe);
			}
		}
		return save;
	}

	@Override
	public IPage<BankLoanVO> bankLoanPage(Page page, BankLoanDTO bankLoanDTO) {
		return this.baseMapper.bankLoanPage(page,bankLoanDTO);
	}

	@Override
	public BankLoanInformationVO getByBankLoanInformation(Integer bankLoanId) {
		return this.baseMapper.getByBankLoanInformation(bankLoanId);
	}

	@Override
	public boolean updateBankLoanInformation(BankLoanInformationDTO bankLoanInformationDTO) {
		BankLoan bankLoan=new BankLoan();
		BeanCopyUtil.copyBean(bankLoanInformationDTO,bankLoan);
		//修改银行借贷表信息
		boolean result = this.updateById(bankLoan);

		//债务人信息
		List<SubjectAddressDTO> subjectAddressDTOList = bankLoanInformationDTO.getSubjectAddressDTOList();
		if (subjectAddressDTOList!=null&&subjectAddressDTOList.size()>0){
			List<Address> addressList=new ArrayList<>();
			//债务人id
			List<Integer> subjectIdList=new ArrayList<>();

			for (SubjectAddressDTO subjectAddressDTO : subjectAddressDTOList) {
				//债务人
				Subject subject=new Subject();
				BeanCopyUtil.copyBean(subjectAddressDTO,subject);
				remoteSubjectService.saveOrUpdateById(subject,SecurityConstants.FROM);//新增或修改主体信息
				subjectIdList.add(subject.getSubjectId());

				addressList = subjectAddressDTO.getAddressList();
				for (Address address : addressList) {
					address.setUserId(subject.getSubjectId());
					address.setType(1);
				}
			}
			remoteAddressService.saveOrUpdateById(addressList,SecurityConstants.FROM);//新增或修改主体关联地址信息

			//添加主体关联银行借贷信息
			List<SubjectBankLoanRe> subjectBankLoanReList = subjectBankLoanReService.list(new LambdaQueryWrapper<SubjectBankLoanRe>().isNull(SubjectBankLoanRe::getBankLoanId).in(SubjectBankLoanRe::getSubjectId, subjectIdList));
			for (SubjectBankLoanRe bankLoanRe : subjectBankLoanReList) {
				bankLoanRe.setBankLoanId(bankLoan.getBankLoanId());
			}
			subjectBankLoanReService.updateBatchById(subjectBankLoanReList);
		}

		//抵押财产信息
		List<AssetsDTO> assetsDTOList = bankLoanInformationDTO.getAssetsDTOList();
		if (assetsDTOList!=null&&assetsDTOList.size()>0){
			List<Address> addressList=new ArrayList<>();
			AssetsBankLoanRe assetsBankLoanRe=new AssetsBankLoanRe();
			Assets assets=new Assets();

			for (AssetsDTO assetsDTO : assetsDTOList) {
				BeanCopyUtil.copyBean(assetsDTO,assets);

				//修改财产关联银行借贷信息
				if (assets.getAssetsId()!=null){
					assetsBankLoanRe.setSubjectId(assetsDTO.getSubjectId());
					assetsBankLoanRe.setMortgageTime(assetsDTO.getMortgageTime());
					assetsBankLoanRe.setMortgageAmount(assetsDTO.getMortgageAmount());
					assetsBankLoanReService.updateById(assetsBankLoanRe);
				}else {
					//添加财产关联银行借贷信息
					assets.setType(20200);//默认实体财产类型
					assetsBankLoanRe.setAssetsId(assets.getAssetsId());
					assetsBankLoanRe.setBankLoanId(bankLoan.getBankLoanId());
					assetsBankLoanRe.setSubjectId(assetsDTO.getSubjectId());
					assetsBankLoanRe.setMortgageTime(assetsDTO.getMortgageTime());
					assetsBankLoanRe.setMortgageAmount(assetsDTO.getMortgageAmount());
					assetsBankLoanReService.save(assetsBankLoanRe);
				}
				assetsService.saveOrUpdate(assets);//添加或修改财产信息

				Address address = assetsDTO.getAddress();
				address.setType(4);
				address.setUserId(assets.getAssetsId());
				addressList.add(address);
			}
			remoteAddressService.saveOrUpdateById(addressList, SecurityConstants.FROM);//添加或修改财产地址信息

		}

		return result;
	}
}
