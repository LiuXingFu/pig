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
import com.pig4cloud.pig.casee.entity.AssetsLiquiTransferRecordRe;
import com.pig4cloud.pig.casee.mapper.AssetsLiquiTransferRecordReMapper;
import com.pig4cloud.pig.casee.service.AssetsLiquiTransferRecordReService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 财产关联清收移交记录表
 *
 * @author Mjh
 * @date 2022-04-06 15:22:06
 */
@Service
public class AssetsLiquiTransferRecordReServiceImpl extends ServiceImpl<AssetsLiquiTransferRecordReMapper, AssetsLiquiTransferRecordRe> implements AssetsLiquiTransferRecordReService {

	@Override
	public List<AssetsLiquiTransferRecordRe> getByTransferRecordAssets(Integer projectId,Integer assetsReId) {
		return this.baseMapper.getByTransferRecordAssets(projectId,assetsReId);
	}
}
