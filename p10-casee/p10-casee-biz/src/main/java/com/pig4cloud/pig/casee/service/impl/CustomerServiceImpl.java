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

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.admin.api.vo.SubjectVO;
import com.pig4cloud.pig.casee.dto.CustomerPageDTO;
import com.pig4cloud.pig.casee.dto.CustomerSubjectDTO;
import com.pig4cloud.pig.casee.entity.Customer;
import com.pig4cloud.pig.casee.mapper.CustomerMapper;
import com.pig4cloud.pig.casee.service.CustomerService;
import com.pig4cloud.pig.casee.vo.CustomerSubjectVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Objects;

/**
 * 客户表
 *
 * @author yuanduo
 * @date 2022-04-06 10:40:19
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Autowired
	RemoteSubjectService remoteSubjectService;

	@Autowired
	private SecurityUtilsService securityUtilsService;

	/**
	 * 新增客户信息
	 * @param customerSubjectDTO
	 * @return
	 */
	@Override
	@Transactional
	public int saveCustomer(CustomerSubjectDTO customerSubjectDTO) {
		// 判断主体id是否为空
		if(customerSubjectDTO.getSubjectId() != null) {
			// 主体id不为空 添加客户信息
			Customer customer = new Customer();
			BeanUtils.copyProperties(customerSubjectDTO, customer);
			customer.setInsId(securityUtilsService.getCacheUser().getInsId());
			customer.setOutlesId(securityUtilsService.getCacheUser().getOutlesId());
			customer.setRecommenderId(securityUtilsService.getCacheUser().getId());
			this.save(customer);
		} else {
			// 主体id为空 根据身份标识查询主体信息
			SubjectVO subjectVO = remoteSubjectService.getByUnifiedIdentity(customerSubjectDTO.getUnifiedIdentity(), SecurityConstants.FROM).getData();
			// 判断主体信息是否为空
			if (Objects.isNull(subjectVO)) {
				// 主体信息为空 添加主体信息
				Subject subject = new Subject();
				BeanUtil.copyProperties(customerSubjectDTO, subject);

				subject = this.remoteSubjectService.saveSubject(subject, SecurityConstants.FROM).getData();

				//主体信息添加完成 添加客户信息
				Customer customer = getCustomer(customerSubjectDTO, subject.getSubjectId());
				this.save(customer);

			} else {
				// 主体信息不为空 添加客户信息
				Customer customer = getCustomer(customerSubjectDTO, subjectVO.getSubjectId());
				this.save(customer);
			}
		}
		return 1;
	}

	/**
	 * 分页查询客户信息
	 * @param page
	 * @param customerPageDTO
	 * @return
	 */
	@Override
	public IPage<CustomerSubjectVO> queryCustomerPage(Page page, CustomerPageDTO customerPageDTO) {
		customerPageDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		customerPageDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.queryCustomerPage(page, customerPageDTO);
	}

	/**
	 * 存储客户信息
	 * @param customerSubjectDTO
	 * @param subjectId
	 * @return
	 */
	private Customer getCustomer(CustomerSubjectDTO customerSubjectDTO, Integer subjectId) {
		Customer customer = new Customer();
		BeanUtils.copyProperties(customerSubjectDTO, customer);
		customer.setSubjectId(subjectId);
		customer.setInsId(securityUtilsService.getCacheUser().getInsId());
		customer.setOutlesId(securityUtilsService.getCacheUser().getOutlesId());
		customer.setRecommenderId(securityUtilsService.getCacheUser().getId());
		return customer;
	}
}
