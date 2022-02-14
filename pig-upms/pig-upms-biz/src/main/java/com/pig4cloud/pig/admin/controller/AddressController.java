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

package com.pig4cloud.pig.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.AddressDTO;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.service.AddressService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 地址表
 *
 * @author yuanduo
 * @date 2021-09-03 17:14:47
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/address")
@Api(value = "address", tags = "地址表管理")
public class AddressController {

	private final AddressService addressService;

	/**
	 * 分页查询
	 *
	 * @param page    分页对象
	 * @param address 地址表
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	@PreAuthorize("@pms.hasPermission('generator_address_get')")
	public R getAddressPage(Page page, Address address) {
		return R.ok(addressService.page(page, Wrappers.query(address)));
	}


	/**
	 * 通过id查询地址表
	 *
	 * @param addressId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询", notes = "通过id查询")
	@GetMapping("/{addressId}")
	@PreAuthorize("@pms.hasPermission('generator_address_get')")
	public R getById(@PathVariable("addressId") Integer addressId) {
		return R.ok(addressService.getById(addressId));
	}

	/**
	 * 新增地址表
	 *
	 * @param address 地址表
	 * @return R
	 */
	@ApiOperation(value = "新增地址表", notes = "新增地址表")
	@SysLog("新增地址表")
	@PostMapping
	public R save(@RequestBody Address address) {
		return R.ok(addressService.saveAddress(address));
	}

	/**
	 * 修改地址表
	 *
	 * @param addressDTO 地址表
	 * @return R
	 */
	@ApiOperation(value = "修改地址表", notes = "修改地址表")
	@SysLog("修改地址表")
	@PutMapping
	public R updateById(@RequestBody AddressDTO addressDTO) {
		return R.ok(addressService.updateBatchById(addressDTO.getAddressList()));
	}

	/**
	 * 新增或修改地址表
	 *
	 * @param addressDTO 地址表
	 * @return R
	 */
	@ApiOperation(value = "新增或修改地址表", notes = "新增或修改地址表")
	@SysLog("新增或修改地址表")
	@PostMapping("/saveOrUpdateById")
	public R saveOrUpdateById(@RequestBody AddressDTO addressDTO) {
		return R.ok(addressService.saveOrUpdateBatch(addressDTO.getAddressList()));
	}

	/**
	 * 通过id删除地址表
	 *
	 * @param addressId id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除地址表", notes = "通过id删除地址表")
	@SysLog("通过id删除地址表")
	@DeleteMapping("/{addressId}")
	public R removeById(@PathVariable Integer addressId) {
		return R.ok(addressService.removeById(addressId));
	}

	/**
	 * 通过类型id和类型删除地址表
	 *
	 * @param userId 类型id
	 * @param type 类型
	 * @return R
	 */
	@ApiOperation(value = "通过类型id和类型删除地址表", notes = "通过类型id和类型删除地址表")
	@SysLog("通过类型id和类型删除地址表")
	@DeleteMapping("/removeUserIdAndType")
	public R removeUserIdAndType(Integer userId,Integer type) {
		return R.ok(addressService.remove(new LambdaQueryWrapper<Address>().eq(Address::getUserId,userId).eq(Address::getType,type)));
	}
}
