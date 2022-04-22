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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.LiquiTransferPageDTO;
import com.pig4cloud.pig.casee.entity.LiquiTransfer;
import com.pig4cloud.pig.casee.mapper.LiquiTransferMapper;
import com.pig4cloud.pig.casee.service.LiquiTransferService;
import com.pig4cloud.pig.casee.vo.LiquiTransferPageVO;
import org.springframework.stereotype.Service;

/**
 * 清收移交表
 *
 * @author yuanduo
 * @date 2022-04-21 15:39:01
 */
@Service
public class LiquiTransferServiceImpl extends ServiceImpl<LiquiTransferMapper, LiquiTransfer> implements LiquiTransferService {

	@Override
	public IPage<LiquiTransferPageVO> getLiquiTransferPage(Page page, LiquiTransferPageDTO liquiTransferPageDTO) {
		return this.baseMapper.getLiquiTransferPage(page, liquiTransferPageDTO);
	}
}
