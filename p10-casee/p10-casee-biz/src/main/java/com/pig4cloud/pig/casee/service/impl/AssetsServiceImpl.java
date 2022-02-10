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
import com.pig4cloud.pig.casee.dto.AssetsGetByIdDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.mapper.AssetsMapper;
import com.pig4cloud.pig.casee.service.AssetsService;
import org.springframework.stereotype.Service;

/**
 * 财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
@Service
public class AssetsServiceImpl extends ServiceImpl<AssetsMapper, Assets> implements AssetsService {

	@Override
	public AssetsGetByIdDTO getByAssets(Integer assetsId) {
		return this.baseMapper.getByAssets(assetsId);
	}
}
