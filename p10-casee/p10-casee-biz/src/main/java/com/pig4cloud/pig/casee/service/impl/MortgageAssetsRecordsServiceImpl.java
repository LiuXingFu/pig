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
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.MortgageAssetsRe;
import com.pig4cloud.pig.casee.entity.MortgageAssetsRecords;
import com.pig4cloud.pig.casee.entity.MortgageAssetsSubjectRe;
import com.pig4cloud.pig.casee.mapper.MortgageAssetsRecordsMapper;
import com.pig4cloud.pig.casee.service.MortgageAssetsReService;
import com.pig4cloud.pig.casee.service.MortgageAssetsRecordsService;
import com.pig4cloud.pig.casee.service.MortgageAssetsSubjectReService;
import com.pig4cloud.pig.casee.vo.AssetsInformationVO;
import com.pig4cloud.pig.casee.vo.MortgageAssetsRecordsVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 抵押记录表
 *
 * @author Mjh
 * @date 2022-04-13 11:24:18
 */
@Service
public class MortgageAssetsRecordsServiceImpl extends ServiceImpl<MortgageAssetsRecordsMapper, MortgageAssetsRecords> implements MortgageAssetsRecordsService {
	@Autowired
	MortgageAssetsSubjectReService mortgageAssetsSubjectReService;
	@Autowired
	MortgageAssetsReService mortgageAssetsReService;
	@Autowired
	RemoteSubjectService remoteSubjectService;

	@Override
	public List<AssetsInformationVO> getMortgageAssetsRecordsDetails(Integer bankLoanId) {
		return this.baseMapper.getMortgageAssetsRecordsDetails(bankLoanId);
	}

	@Override
	public MortgageAssetsRecordsVO getByMortgageAssetsRecordsId(Integer mortgageAssetsRecordsId) {
		MortgageAssetsRecordsVO mortgageAssetsRecordsVO = this.baseMapper.getByMortgageAssetsRecordsId(mortgageAssetsRecordsId);
		List<MortgageAssetsRe> list = mortgageAssetsReService.list(new LambdaQueryWrapper<MortgageAssetsRe>().eq(MortgageAssetsRe::getMortgageAssetsRecordsId, mortgageAssetsRecordsId));
		List<MortgageAssetsSubjectRe> mortgageAssetsSubjectReList=new ArrayList<>();
		for (MortgageAssetsRe mortgageAssetsRe : list) {
			mortgageAssetsSubjectReList = mortgageAssetsSubjectReService.list(new LambdaQueryWrapper<MortgageAssetsSubjectRe>().eq(MortgageAssetsSubjectRe::getMortgageAssetsReId, mortgageAssetsRe.getMortgageAssetsReId()));
		}
		for (MortgageAssetsSubjectRe mortgageAssetsSubjectRe : mortgageAssetsSubjectReList) {
			R<Subject> serviceById = remoteSubjectService.getById(mortgageAssetsSubjectRe.getSubjectId(), SecurityConstants.FROM);
			mortgageAssetsRecordsVO.getSubjectList().add(serviceById.getData().getSubjectId());
		}

		return mortgageAssetsRecordsVO;
	}
}
