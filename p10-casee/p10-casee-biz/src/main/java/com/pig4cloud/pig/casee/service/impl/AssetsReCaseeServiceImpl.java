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
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.casee.dto.AssetsAddDTO;
import com.pig4cloud.pig.casee.dto.AssetsDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.entity.assets.AssetsReCasee;
import com.pig4cloud.pig.casee.mapper.AssetsReCaseeMapper;
import com.pig4cloud.pig.casee.mapper.AssetsReMapper;
import com.pig4cloud.pig.casee.service.AssetsReCaseeService;
import com.pig4cloud.pig.casee.service.AssetsReService;
import com.pig4cloud.pig.casee.service.AssetsService;
import com.pig4cloud.pig.casee.vo.CaseeOrAssetsVO;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
@Service
public class AssetsReCaseeServiceImpl extends ServiceImpl<AssetsReCaseeMapper, AssetsRe> implements AssetsReCaseeService {

	@Autowired
	AssetsService assetsService;

	@Override
	@Transactional
	public Integer saveAssetsCasee(AssetsAddDTO assetsAddDTO){
		// 财产不存在保存财产信息及相关联信息
		if(Objects.isNull(assetsAddDTO.getAssetsId())){
			Assets assets = new Assets();
			BeanCopyUtil.copyBean(assetsAddDTO,assets);
			assetsService.save(assets);
			assetsAddDTO.setAssetsId(assets.getAssetsId());
			// 保存财产地址信息
			if(Objects.nonNull(assetsAddDTO.getCode())){
				Address address = new Address();
				// 4=财产地址
				address.setType(4);
				address.setUserId(assets.getAssetsId());
				BeanCopyUtil.copyBean(assetsAddDTO,address);
			}
		}
		AssetsReCasee assetsReCasee = new AssetsReCasee();
		// 财产来源2=案件
		assetsReCasee.setAssetsSource(2);
		assetsReCasee.setCreateCaseeId(assetsAddDTO.getCaseeId());
		BeanCopyUtil.copyBean(assetsAddDTO,assetsReCasee);
		return this.baseMapper.insert(assetsReCasee);
	}

}
