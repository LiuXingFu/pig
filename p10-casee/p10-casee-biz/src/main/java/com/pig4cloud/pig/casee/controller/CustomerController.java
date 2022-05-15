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

package com.pig4cloud.pig.casee.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.casee.dto.CustomerPageDTO;
import com.pig4cloud.pig.casee.dto.CustomerSubjectDTO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.casee.entity.Customer;
import com.pig4cloud.pig.casee.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


/**
 * 客户表
 *
 * @author yuanduo
 * @date 2022-04-06 10:40:19
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/customer" )
@Api(value = "customer", tags = "客户表管理")
public class CustomerController {

    private final  CustomerService customerService;

	@Autowired
	RemoteSubjectService remoteSubjectService;

    /**
     * 分页查询
     * @param page 分页对象
     * @param customer 客户表
     * @return
     */
    @ApiOperation(value = "分页查询", notes = "分页查询")
    @GetMapping("/page" )
    public R getCustomerPage(Page page, Customer customer) {
        return R.ok(customerService.page(page, Wrappers.query(customer)));
    }


    /**
     * 通过id查询客户表
     * @param customerId id
     * @return R
     */
    @ApiOperation(value = "通过id查询", notes = "通过id查询")
    @GetMapping("/{customerId}" )
    @PreAuthorize("@pms.hasPermission('casee_customer_get')" )
    public R getById(@PathVariable("customerId" ) Integer customerId) {
        return R.ok(customerService.getById(customerId));
    }

    /**
     * 新增客户表
     * @param customer 客户表
     * @return R
     */
    @ApiOperation(value = "新增客户表", notes = "新增客户表")
    @SysLog("新增客户表" )
    @PostMapping
    @PreAuthorize("@pms.hasPermission('casee_customer_add')" )
    public R save(@RequestBody Customer customer) {
        return R.ok(customerService.save(customer));
    }

    /**
     * 修改客户表
     * @param customer 客户表
     * @return R
     */
    @ApiOperation(value = "修改客户表", notes = "修改客户表")
    @SysLog("修改客户表" )
    @PutMapping
    @PreAuthorize("@pms.hasPermission('casee_customer_edit')" )
    public R updateById(@RequestBody Customer customer) {
        return R.ok(customerService.updateById(customer));
    }

    /**
     * 通过id删除客户表
     * @param customerId id
     * @return R
     */
    @ApiOperation(value = "通过id删除客户表", notes = "通过id删除客户表")
    @SysLog("通过id删除客户表" )
    @DeleteMapping("/{customerId}" )
    public R removeById(@PathVariable Integer customerId) {
		Customer customer = this.customerService.getById(customerId);
		Subject subject = this.remoteSubjectService.getById(customer.getSubjectId(), SecurityConstants.FROM).getData();
		if(Objects.isNull(subject.getUnifiedIdentity())) {
			this.remoteSubjectService.removeById(subject.getSubjectId(), SecurityConstants.FROM);
		}
		return R.ok(customerService.removeById(customerId));
    }

	/**
	 * 新增客户信息
	 * @param customerSubjectDTO
	 * @return R
	 */
	@ApiOperation(value = "新增客户信息", notes = "新增客户信息")
	@SysLog("新增客户信息" )
	@PostMapping("/saveCustomer")
	public R saveCustomer(@RequestBody CustomerSubjectDTO customerSubjectDTO) {
		int i = customerService.saveCustomer(customerSubjectDTO);
		if(i > 0){
			return R.ok("添加客户信息成功！");
		} else {
			return R.failed("添加客户信息失败！");
		}
	}

	/**
	 * 分页查询客户信息
	 * @param page 分页对象
	 * @param customerPageDTO
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/queryCustomerPage" )
	public R queryCustomerPage(Page page, CustomerPageDTO customerPageDTO){
		return R.ok(customerService.queryCustomerPage(page, customerPageDTO));
	}

	/**
	 * 通过id查询客户与主体信息
	 * @param customerId id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询客户与主体信息", notes = "通过id查询客户与主体信息")
	@GetMapping("/queryById/{customerId}" )
	public R queryById(@PathVariable("customerId" ) Integer customerId) {
		return R.ok(customerService.queryById(customerId));
	}

	/**
	 * 修改客户信息
	 * @param customerSubjectDTO 客户表
	 * @return R
	 */
	@ApiOperation(value = "修改客户信息", notes = "修改客户信息")
	@SysLog("修改客户信息" )
	@PutMapping("/updateCustomerById")
	public R updateCustomerById(@RequestBody CustomerSubjectDTO customerSubjectDTO) {
		int i = customerService.updateCustomerById(customerSubjectDTO);
		if (i > 0) {
			return R.ok("修改客户信息成功！");
		} else {
			return R.failed("修改客户信息失败！");
		}
	}

	/**
	 * 验证身份证与电话客户信息是否存在
	 * @param unifiedIdentity
	 * @param phone
	 * @return
	 */
	@ApiOperation(value = "通过id查询客户与主体信息", notes = "通过id查询客户与主体信息")
	@GetMapping("/verifyUnifiedIdentityAndPhone" )
	public R verifyUnifiedIdentityAndPhone(String unifiedIdentity, String phone) {
		return R.ok(customerService.verifyUnifiedIdentityAndPhone(unifiedIdentity, phone));
	}

}
