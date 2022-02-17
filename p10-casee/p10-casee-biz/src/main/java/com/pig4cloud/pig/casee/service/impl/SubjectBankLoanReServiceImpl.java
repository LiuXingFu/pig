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
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.entity.SubjectBankLoanRe;
import com.pig4cloud.pig.casee.mapper.SubjectBankLoanReMapper;
import com.pig4cloud.pig.casee.service.SubjectBankLoanReService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
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
	public List<Integer> selectSubjectId(Integer bankLoanId) {
		return this.baseMapper.selectSubjectId(bankLoanId);
	}
}
