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
package com.pig4cloud.pig.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.mapper.AddressMapper;
import com.pig4cloud.pig.admin.service.AddressService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地址表
 *
 * @author yuanduo
 * @date 2021-09-03 17:14:47
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

	@Override
	public Address saveAddress(Address address) {
		if (address.getArea() == null ||
				address.getCity() == null ||
				address.getCode() == null ||
				address.getProvince() == null) {
			return null;
		}
		this.save(address);
		return address;
	}

	@Override
	public Address getById(Integer addressId, Integer type) {
		return this.baseMapper.getById(addressId, type);
	}

	/**
	 * 根据类型id查询
	 * @param userId
	 * @param type
	 * @return
	 */
	@Override
	public Address getByUserId(Integer userId, Integer type) {
		return this.baseMapper.getByUserId(userId, type);
	}

	/**
	 * 根据地址类型与地址类型id查询地址信息
	 * @param typeId
	 * @param type
	 * @return
	 */
	@Override
	public Address queryAssetsByTypeIdAndType(Integer typeId, Integer type) {
		return this.baseMapper.queryAssetsByTypeIdAndType(typeId, type);
	}

	@Override
	public List<Address> queryAssetsByInformationAddress(String informationAddress) {
		return this.list(new LambdaQueryWrapper<Address>().like(Address::getInformationAddress,informationAddress).eq(Address::getDelFlag,0));
	}
}
