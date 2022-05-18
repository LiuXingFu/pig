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

package com.pig4cloud.pig.casee.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.CustomerPageDTO;
import com.pig4cloud.pig.casee.dto.CustomerSubjectDTO;
import com.pig4cloud.pig.casee.entity.Customer;
import com.pig4cloud.pig.casee.vo.CustomerOrSubjectVO;
import com.pig4cloud.pig.casee.vo.CustomerSubjectVO;

import java.util.List;

/**
 * 客户表
 *
 * @author yuanduo
 * @date 2022-04-06 10:40:19
 */
public interface CustomerService extends IService<Customer> {

	/**
	 * 新增客户信息
	 * @param customerSubjectDTO
	 * @return
	 */
	int saveCustomer(CustomerSubjectDTO customerSubjectDTO);

	/**
	 * 分页查询客户信息
	 * @param page
	 * @param customerPageDTO
	 * @return
	 */
	IPage<CustomerSubjectVO> queryCustomerPage(Page page, CustomerPageDTO customerPageDTO);

	/**
	 * 通过id查询客户与主体信息
	 * @param customerId id
	 * @return R
	 */
	CustomerOrSubjectVO queryById(Integer customerId);

	/**
	 * 修改客户信息
	 * @param customerSubjectDTO 客户表
	 * @return R
	 */
	int updateCustomerById(CustomerSubjectDTO customerSubjectDTO);

	/**
	 * 验证身份证与电话客户信息是否存在
	 * @param unifiedIdentity
	 * @param phone
	 * @return
	 */
	int verifyUnifiedIdentityAndPhone(String unifiedIdentity, String phone, String name, Integer subjectId);

	/**
	 * 批量添加客户信息
	 * @param customerSubjectDTOList
	 * @return
	 */
	int saveCustomerBatch(List<CustomerSubjectDTO> customerSubjectDTOList);
}
