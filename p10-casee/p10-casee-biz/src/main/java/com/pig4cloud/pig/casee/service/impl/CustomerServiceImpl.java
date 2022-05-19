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
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import com.pig4cloud.pig.casee.vo.CustomerOrSubjectVO;
import com.pig4cloud.pig.casee.vo.CustomerSubjectVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.security.service.SecurityUtilsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
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
	 *
	 * @param customerSubjectDTO
	 * @return
	 */
	@Override
	@Transactional
	public int saveCustomer(CustomerSubjectDTO customerSubjectDTO) {
		int add = 0;
		// 判断主体id是否为空
		saveUpdateCustomerAndSubject(customerSubjectDTO);

		return add += 1;
	}

	private void saveUpdateCustomerAndSubject(CustomerSubjectDTO customerSubjectDTO) {
		if (customerSubjectDTO.getSubjectId() != null) {

			Subject subject = this.remoteSubjectService.getById(customerSubjectDTO.getSubjectId(), SecurityConstants.FROM).getData();

			SubjectVO subjectVO = new SubjectVO();

			BeanUtil.copyProperties(subject, subjectVO);

			saveSubjectVOAndCustomerSubjectDTO(customerSubjectDTO, subjectVO);
		} else {
			if (Objects.nonNull(customerSubjectDTO.getUnifiedIdentity()) && !customerSubjectDTO.getUnifiedIdentity().equals("")) {
				// 主体id为空 根据身份标识查询主体信息
				SubjectVO subjectVO = remoteSubjectService.getByUnifiedIdentity(customerSubjectDTO.getUnifiedIdentity(), SecurityConstants.FROM).getData();
				// 判断主体信息是否为空
				if (Objects.isNull(subjectVO)) {
					subjectVO = this.remoteSubjectService.getByPhone(customerSubjectDTO.getPhone(), customerSubjectDTO.getName(), SecurityConstants.FROM).getData();
					if (Objects.isNull(subjectVO)) {
						saveSubjectOrCustomer(customerSubjectDTO);
					} else {
						saveSubjectVOAndCustomerSubjectDTO(customerSubjectDTO, subjectVO);
					}
				} else {
					saveSubjectVOAndCustomerSubjectDTO(customerSubjectDTO, subjectVO);
				}
			} else {
				//根据电话查询主体
				SubjectVO subjectVO = this.remoteSubjectService.getByPhone(customerSubjectDTO.getPhone(), customerSubjectDTO.getName(), SecurityConstants.FROM).getData();
				// 主体信息为空 添加主体信息
				if (Objects.isNull(subjectVO)) {
					saveSubjectOrCustomer(customerSubjectDTO);
					//主体不为空
				} else {
					saveSubjectVOAndCustomerSubjectDTO(customerSubjectDTO, subjectVO);
				}
			}
		}
	}

	private void saveSubjectVOAndCustomerSubjectDTO(CustomerSubjectDTO customerSubjectDTO, SubjectVO subjectVO) {
		if ((Objects.nonNull(subjectVO.getPhone()) && !subjectVO.equals("")) && (Objects.nonNull(customerSubjectDTO.getPhone()) && !customerSubjectDTO.getPhone().equals(""))) {
			if (!subjectVO.getPhone().contains(customerSubjectDTO.getPhone())) {
				customerSubjectDTO.setPhone(subjectVO.getPhone() + ',' + customerSubjectDTO.getPhone());
			}
		}

		this.remoteSubjectService.saveOrUpdateById(subjectVO, SecurityConstants.FROM);

		//添加或修改客户信息
		addOrUpdateCustomer(customerSubjectDTO, subjectVO);
	}

	/**
	 * 添加或修改客户信息
	 *
	 * @param customerSubjectDTO
	 * @param subject
	 */
	private void addOrUpdateCustomer(CustomerSubjectDTO customerSubjectDTO, Subject subject) {
		//根据主体id查询客户信息
		Customer customer = this.getOne(new LambdaQueryWrapper<Customer>().eq(Customer::getSubjectId, subject.getSubjectId()));
		//客户信息为空添加客户信息
		if (Objects.isNull(customer)) {
			customer = getCustomer(customerSubjectDTO, subject.getSubjectId());
			this.save(customer);
			//客户信息不为空更新客户信息
		} else {
			customerSubjectDTO.setSubjectId(subject.getSubjectId());
			customerSubjectDTO.setCustomerId(customer.getCustomerId());
			//判断如果查询的主体有无身份证并分别修改主体信息
			if (Objects.isNull(subject.getUnifiedIdentity())) {
				saveUpdateSubject(customerSubjectDTO);
			} else {
				customerSubjectDTO.setUnifiedIdentity(subject.getUnifiedIdentity());
				saveUpdateSubject(customerSubjectDTO);
			}

			BeanUtil.copyProperties(customerSubjectDTO, customer);

			//修改客户
			this.updateById(customer);
		}
	}


	/**
	 * 添加主体与客户信息
	 *
	 * @param customerSubjectDTO
	 */
	private void saveSubjectOrCustomer(CustomerSubjectDTO customerSubjectDTO) {
		// 主体信息为空 添加主体信息
		Subject subject = new Subject();
		BeanUtil.copyProperties(customerSubjectDTO, subject);

		subject = this.remoteSubjectService.saveSubject(subject, SecurityConstants.FROM).getData();

		//主体信息添加完成 添加客户信息
		Customer customer = getCustomer(customerSubjectDTO, subject.getSubjectId());
		this.save(customer);
	}

	/**
	 * 分页查询客户信息
	 *
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
	 * 通过id查询客户与主体信息
	 *
	 * @param customerId id
	 * @return R
	 */
	@Override
	public CustomerOrSubjectVO queryById(Integer customerId) {
		return this.baseMapper.queryById(customerId);
	}

	/**
	 * 修改客户信息
	 *
	 * @param customerSubjectDTO 客户表
	 * @return R
	 */
	@Override
	public int updateCustomerById(CustomerSubjectDTO customerSubjectDTO) {

		int update = 0;
		Subject subject = this.remoteSubjectService.getById(customerSubjectDTO.getSubjectId(), SecurityConstants.FROM).getData();

		this.remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM);

		// 添加或修改客户信息
		addOrUpdateCustomer(customerSubjectDTO, subject);

		return update += 1;
	}

	/**
	 * 验证身份证与电话客户信息是否存在
	 *
	 * @param unifiedIdentity
	 * @param phone
	 * @return
	 */
	@Override
	public int verifyUnifiedIdentityAndPhone(String unifiedIdentity, String phone, String name, Integer subjectId) {
		if ((Objects.nonNull(unifiedIdentity) && !unifiedIdentity.equals("")) && (Objects.nonNull(phone) && !phone.equals(""))) {
			SubjectVO subjectVO = this.remoteSubjectService.getByUnifiedIdentityAndPhone(unifiedIdentity, phone, SecurityConstants.FROM).getData();
			if (Objects.nonNull(subjectVO)) {
				if (subjectVO.getSubjectId().equals(subjectId)) {
					return 0;
				} else {
					return isCustomer(subjectVO.getSubjectId());
				}
			} else {
				subjectVO = this.remoteSubjectService.getByUnifiedIdentity(unifiedIdentity, SecurityConstants.FROM).getData();

				if (Objects.nonNull(subjectVO)) {
					if (subjectVO.getSubjectId().equals(subjectId)) {
						return 0;
					} else {
						return isCustomer(subjectVO.getSubjectId());
					}
				} else {
					subjectVO = this.remoteSubjectService.getByPhone(phone, name, SecurityConstants.FROM).getData();
					if (Objects.nonNull(subjectVO)) {
						if (subjectVO.getSubjectId().equals(subjectId)) {
							return 0;
						} else {
							return isCustomer(subjectVO.getSubjectId());
						}
					} else {
						return 0;
					}
				}
			}
		} else if (Objects.nonNull(unifiedIdentity) && (phone == null || phone.equals(""))) {
			SubjectVO subjectVO = this.remoteSubjectService.getByUnifiedIdentity(unifiedIdentity, SecurityConstants.FROM).getData();
			if (Objects.nonNull(subjectVO)) {
				if (subjectVO.getSubjectId().equals(subjectId)) {
					return 0;
				} else {
					return isCustomer(subjectVO.getSubjectId());
				}
			} else {
				return 0;
			}
		} else if (Objects.nonNull(phone) && (unifiedIdentity == null || unifiedIdentity.equals(""))) {
			SubjectVO subjectVO = this.remoteSubjectService.getByPhone(phone, name, SecurityConstants.FROM).getData();
			if (Objects.nonNull(subjectVO)) {
				if (subjectVO.getSubjectId().equals(subjectId)) {
					return 0;
				} else {
					return isCustomer(subjectVO.getSubjectId());
				}
			} else {
				return 0;
			}
		}
		return 0;
	}


	/**
	 * 批量添加客户信息
	 *
	 * @param customerSubjectDTOList
	 * @return
	 */
	@Override
	@Transactional
	public int saveCustomerBatch(List<CustomerSubjectDTO> customerSubjectDTOList) {
		int add = 0;

		for (CustomerSubjectDTO customerSubjectDTO : customerSubjectDTOList) {
			if (customerSubjectDTO.getSubjectId() != null) {
				saveUpdateSubject(customerSubjectDTO);
			} else if ((customerSubjectDTO.getUnifiedIdentity() != null && !customerSubjectDTO.equals("")) && (customerSubjectDTO.getPhone() != null && !customerSubjectDTO.getPhone().equals(""))) {
				SubjectVO subjectVO = this.remoteSubjectService.getByUnifiedIdentity(customerSubjectDTO.getUnifiedIdentity(), SecurityConstants.FROM).getData();
				if (subjectVO != null) {
					if ((Objects.nonNull(subjectVO.getPhone()) && !subjectVO.equals("")) && (Objects.nonNull(customerSubjectDTO.getPhone()) && !customerSubjectDTO.getPhone().equals(""))) {
						if (!subjectVO.getPhone().contains(customerSubjectDTO.getPhone())) {
							customerSubjectDTO.setPhone(subjectVO.getPhone() + ',' + customerSubjectDTO.getPhone());
						}
					}

					saveUpdateSubject(customerSubjectDTO);
					customerSubjectDTO.setSubjectId(subjectVO.getSubjectId());

					Customer customer = this.getOne(new LambdaQueryWrapper<Customer>().eq(Customer::getSubjectId, subjectVO.getSubjectId()));
					if (customer != null) {
						customerSubjectDTO.setCustomerId(customer.getCustomerId());
					}
				} else {
					subjectVO = this.remoteSubjectService.getByPhone(customerSubjectDTO.getPhone(), customerSubjectDTO.getName(), SecurityConstants.FROM).getData();
					saveUpdateSubjectAndSetCustomerSubjectDTO(customerSubjectDTO, subjectVO);
				}
			} else if (customerSubjectDTO.getUnifiedIdentity() != null && !customerSubjectDTO.getUnifiedIdentity().equals("")) {
				SubjectVO subjectVO = this.remoteSubjectService.getByUnifiedIdentity(customerSubjectDTO.getUnifiedIdentity(), SecurityConstants.FROM).getData();
				saveUpdateSubjectAndSetCustomerSubjectDTO(customerSubjectDTO, subjectVO);
			} else if (customerSubjectDTO.getPhone() != null && !customerSubjectDTO.getPhone().equals("")) {
				SubjectVO subjectVO = this.remoteSubjectService.getByPhone(customerSubjectDTO.getPhone(), customerSubjectDTO.getName(), SecurityConstants.FROM).getData();
				saveUpdateSubjectAndSetCustomerSubjectDTO(customerSubjectDTO, subjectVO);
			} else {
				return 0;
			}
		}

		List<Customer> customers = new ArrayList<>();

		for (CustomerSubjectDTO customerSubjectDTO : customerSubjectDTOList) {
			Customer customer = new Customer();

			BeanUtil.copyProperties(customerSubjectDTO, customer);

			customers.add(customer);
		}

		this.saveOrUpdateBatch(customers);

		return add + 1;
	}

	/**
	 * 保存或修改主体客户信息
	 * @param customerSubjectDTO
	 * @param subjectVO
	 */
	private void saveUpdateSubjectAndSetCustomerSubjectDTO(CustomerSubjectDTO customerSubjectDTO, SubjectVO subjectVO) {
		if (Objects.nonNull(subjectVO)) {
			customerSubjectDTO.setSubjectId(subjectVO.getSubjectId());

			if ((Objects.nonNull(subjectVO.getPhone()) && !subjectVO.equals("")) && (Objects.nonNull(customerSubjectDTO.getPhone()) && !customerSubjectDTO.getPhone().equals(""))) {
				if (!subjectVO.getPhone().contains(customerSubjectDTO.getPhone())) {
					customerSubjectDTO.setPhone(subjectVO.getPhone() + ',' + customerSubjectDTO.getPhone());
				}
			}

			saveUpdateSubject(customerSubjectDTO);
			Customer customer = this.getOne(new LambdaQueryWrapper<Customer>().eq(Customer::getSubjectId, subjectVO.getSubjectId()));
			if (customer != null) {
				customerSubjectDTO.setCustomerId(customer.getCustomerId());
			}
		} else {
			saveUpdateSubject(customerSubjectDTO);
		}
	}

	public int isCustomer(Integer subjectId) {
		Customer customer = this.getOne(new LambdaQueryWrapper<Customer>().eq(Customer::getSubjectId, subjectId));
		if (Objects.nonNull(customer)) {
			return 1;
		} else {
			return 0;
		}
	}

	/**
	 * 更新或添加主体信息
	 *
	 * @param customerSubjectDTO
	 */
	private void saveUpdateSubject(CustomerSubjectDTO customerSubjectDTO) {
		Subject subject = new Subject();
		BeanUtil.copyProperties(customerSubjectDTO, subject);
		Integer integer = this.remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM).getData();
		customerSubjectDTO.setSubjectId(integer);
	}

	/**
	 * 存储客户信息
	 *
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
