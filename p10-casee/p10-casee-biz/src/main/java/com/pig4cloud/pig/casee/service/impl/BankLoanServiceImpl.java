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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.BankLoanDTO;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.entity.BankLoan;
import com.pig4cloud.pig.casee.mapper.BankLoanMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.BankLoanInformationVO;
import com.pig4cloud.pig.casee.vo.BankLoanVO;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;


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

	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Override
	public IPage<BankLoanVO> bankLoanPage(Page page, BankLoanDTO bankLoanDTO) {
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.bankLoanPage(page, bankLoanDTO, insOutlesDTO);
	}

	@Override
	public BankLoanInformationVO getByBankLoanInformation(Integer bankLoanId) {
		BankLoanInformationVO bankLoanInformation = this.baseMapper.getByBankLoanInformation(bankLoanId);
		return bankLoanInformation;
	}

	@Override
	public int updateBankLoan(BankLoan bankLoan) {
		BigDecimal principal = bankLoan.getPrincipal();
		BigDecimal interest = bankLoan.getInterest();
		if(Objects.nonNull(principal) && Objects.nonNull(interest)){
			bankLoan.setRental(principal.add(interest));
		}
		return this.baseMapper.updateById(bankLoan);
	}
}

