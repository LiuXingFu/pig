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
import com.pig4cloud.pig.casee.entity.DeadlineConfigure;
import com.pig4cloud.pig.casee.mapper.DeadlineConfigureMapper;
import com.pig4cloud.pig.casee.service.DeadlineConfigureService;
import org.springframework.stereotype.Service;

/**
 * 期限配置表
 *
 * @author yuanduo
 * @date 2022-02-11 21:13:38
 */
@Service
public class DeadlineConfigureServiceImpl extends ServiceImpl<DeadlineConfigureMapper, DeadlineConfigure> implements DeadlineConfigureService {

	@Override
	public IPage<DeadlineConfigure> getDeadlineConfigurePage(Page page, DeadlineConfigure deadlineConfigure) {
		return this.baseMapper.getDeadlineConfigurePage(page, deadlineConfigure);
	}
}
