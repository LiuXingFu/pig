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

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.entity.ExpenseRecordAssetsRe;
import com.pig4cloud.pig.casee.mapper.ExpenseRecordAssetsReMapper;
import com.pig4cloud.pig.casee.service.ExpenseRecordAssetsReService;
import com.pig4cloud.pig.casee.vo.paifu.AssetsRePaifuDetailVO;
import com.pig4cloud.pig.casee.vo.paifu.ExpenseRecordPaifuAssetsReListVO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 产生费用财产关联表
 *
 * @author pig code generator
 * @date 2022-05-03 21:15:30
 */
@Service
public class ExpenseRecordAssetsReServiceImpl extends ServiceImpl<ExpenseRecordAssetsReMapper, ExpenseRecordAssetsRe> implements ExpenseRecordAssetsReService {

	@Override
	public List<Integer> queryByExpenseRecordId(Integer expenseRecordId){
		return this.baseMapper.selectByExpenseRecordId(expenseRecordId);
	}

	@Override
	public List<AssetsRePaifuDetailVO> queryAssetsList(Integer expenseRecordId){
		return this.baseMapper.selectAssetsList(expenseRecordId);
	}
}
