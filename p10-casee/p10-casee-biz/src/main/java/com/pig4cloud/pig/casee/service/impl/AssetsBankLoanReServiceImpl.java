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
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.casee.dto.AssetsDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.AssetsBankLoanRe;
import com.pig4cloud.pig.casee.mapper.AssetsBankLoanReMapper;
import com.pig4cloud.pig.casee.service.AssetsBankLoanReService;
import com.pig4cloud.pig.casee.service.AssetsService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 财产关联银行借贷表
 *
 * @author Mjh
 * @date 2022-01-28 18:52:30
 */
@Service
public class AssetsBankLoanReServiceImpl extends ServiceImpl<AssetsBankLoanReMapper, AssetsBankLoanRe> implements AssetsBankLoanReService {
	@Autowired
	private AssetsService assetsService;
	@Autowired
	private RemoteAddressService remoteAddressService;

	@Override
	public Integer modifAssetsByAssetsBankLoanReId(AssetsDTO assetsDTO) {
		Assets assets=new Assets();
		BeanCopyUtil.copyBean(assetsDTO,assets);
		assetsService.updateById(assets);

		AssetsBankLoanRe assetsBankLoanRe=new AssetsBankLoanRe();
		BeanCopyUtil.copyBean(assetsDTO,assetsBankLoanRe);

		Address address=new Address();
		BeanUtils.copyProperties(assetsDTO, address);
		if (assetsDTO.getAddressAsId()!=null){
			address.setAddressId(assetsDTO.getAddressAsId());
			address.setType(4);
			remoteAddressService.updateByAddressId(address,SecurityConstants.FROM);
		}else {
			address.setType(4);
			address.setUserId(assets.getAssetsId());
			remoteAddressService.saveAddress(address,SecurityConstants.FROM);
		}

		return this.baseMapper.updateById(assetsBankLoanRe);
	}
}
