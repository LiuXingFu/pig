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
import com.pig4cloud.pig.casee.entity.MortgageAssetsRe;
import com.pig4cloud.pig.casee.entity.MortgageAssetsSubjectRe;
import com.pig4cloud.pig.casee.mapper.MortgageAssetsReMapper;
import com.pig4cloud.pig.casee.service.MortgageAssetsReService;
import com.pig4cloud.pig.casee.service.MortgageAssetsSubjectReService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 抵押财产关联表
 *
 * @author Mjh
 * @date 2022-04-13 11:24:36
 */
@Service
public class MortgageAssetsReServiceImpl extends ServiceImpl<MortgageAssetsReMapper, MortgageAssetsRe> implements MortgageAssetsReService {
	@Autowired
	MortgageAssetsSubjectReService mortgageAssetsSubjectReService;


	@Override
	public boolean removeMortgageAssets(Integer mortgageAssetsReId) {
		mortgageAssetsSubjectReService.remove(new LambdaQueryWrapper<MortgageAssetsSubjectRe>().eq(MortgageAssetsSubjectRe::getMortgageAssetsReId,mortgageAssetsReId));
		return this.removeById(mortgageAssetsReId);
	}
}
