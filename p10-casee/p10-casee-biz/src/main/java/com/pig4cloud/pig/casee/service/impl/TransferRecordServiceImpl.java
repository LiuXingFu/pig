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
import com.pig4cloud.pig.casee.entity.TransferRecord;
import com.pig4cloud.pig.casee.mapper.TransferRecordMapper;
import com.pig4cloud.pig.casee.service.TransferRecordService;
import org.springframework.stereotype.Service;

/**
 * 移交记录表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:40
 */
@Service
public class TransferRecordServiceImpl extends ServiceImpl<TransferRecordMapper, TransferRecord> implements TransferRecordService {

	@Override
	public IPage<TransferRecord> TransferRecordPage(Page page, TransferRecord transferRecord) {
		return this.baseMapper.TransferRecordPage(page,transferRecord);
	}
}
