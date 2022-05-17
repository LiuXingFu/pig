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

			setUpdateSubject(customerSubjectDTO, subject);

			this.remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM);

			// 添加或修改客户信息
			addOrUpdateCustomer(customerSubjectDTO, subject);
		} else {
			if (Objects.nonNull(customerSubjectDTO.getUnifiedIdentity())) {
				// 主体id为空 根据身份标识查询主体信息
				SubjectVO subjectVO = remoteSubjectService.getByUnifiedIdentity(customerSubjectDTO.getUnifiedIdentity(), SecurityConstants.FROM).getData();
				// 判断主体信息是否为空
				if (Objects.isNull(subjectVO)) {
//					saveSubjectOrCustomer(customerSubjectDTO);
					subjectVO = this.remoteSubjectService.getByPhone(customerSubjectDTO.getPhone(), SecurityConstants.FROM).getData();
					if (Objects.isNull(subjectVO)) {
						saveSubjectOrCustomer(customerSubjectDTO);
					} else {
						setUpdateSubject(customerSubjectDTO, subjectVO);

						this.remoteSubjectService.saveOrUpdateById(subjectVO, SecurityConstants.FROM);

						//添加或修改客户信息
						addOrUpdateCustomer(customerSubjectDTO, subjectVO);
					}
				} else {
					setUpdateSubject(customerSubjectDTO, subjectVO);

					this.remoteSubjectService.saveOrUpdateById(subjectVO, SecurityConstants.FROM);

					// 添加或修改客户信息
					addOrUpdateCustomer(customerSubjectDTO, subjectVO);
				}
			} else {
				//根据电话查询主体
				SubjectVO subjectVO = this.remoteSubjectService.getByPhone(customerSubjectDTO.getPhone(), SecurityConstants.FROM).getData();
				// 主体信息为空 添加主体信息
				if (Objects.isNull(subjectVO)) {
					saveSubjectOrCustomer(customerSubjectDTO);
					//主体不为空
				} else {
					setUpdateSubject(customerSubjectDTO, subjectVO);

					this.remoteSubjectService.saveOrUpdateById(subjectVO, SecurityConstants.FROM);

					//添加或修改客户信息
					addOrUpdateCustomer(customerSubjectDTO, subjectVO);
				}
			}
		}
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
			//判断如果查询的主体有无身份证并分别修改主体信息
			if (Objects.isNull(subject.getUnifiedIdentity())) {
				saveUpdateSubject(customerSubjectDTO);
			} else {
				customerSubjectDTO.setUnifiedIdentity(subject.getUnifiedIdentity());
				saveUpdateSubject(customerSubjectDTO);
			}
			//如果某些字段为空则排除客户对象
			setUpdateCustomer(customerSubjectDTO, customer);

			//修改主体
			this.updateById(customer);
		}
	}

	/**
	 * 如果某些字段为空则排出客户对象
	 *
	 * @param customerSubjectDTO
	 * @param customer
	 */
	private void setUpdateCustomer(CustomerSubjectDTO customerSubjectDTO, Customer customer) {

		if (Objects.nonNull(customerSubjectDTO.getCaseeId()) && !customerSubjectDTO.getCaseeId().equals("")) {
			customer.setCaseeId(customerSubjectDTO.getCaseeId());
		}

		if (Objects.nonNull(customerSubjectDTO.getProjectId()) && !customerSubjectDTO.getProjectId().equals("")) {
			customer.setProjectId(customerSubjectDTO.getProjectId());
		}

		if (Objects.nonNull(customerSubjectDTO.getCustomerType()) && !customerSubjectDTO.getCustomerType().equals("")) {
			customer.setCustomerType(customerSubjectDTO.getCustomerType());
		}

		if (Objects.nonNull(customerSubjectDTO.getRecommenderId()) && !customerSubjectDTO.getRecommenderId().equals("")) {
			customer.setRecommenderId(customerSubjectDTO.getRecommenderId());
		}

		if (Objects.nonNull(customerSubjectDTO.getInsId()) && !customerSubjectDTO.getInsId().equals("")) {
			customer.setInsId(customerSubjectDTO.getInsId());
		}

		if (Objects.nonNull(customerSubjectDTO.getOutlesId()) && !customerSubjectDTO.getOutlesId().equals("")) {
			customer.setOutlesId(customerSubjectDTO.getOutlesId());
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

		setUpdateSubject(customerSubjectDTO, subject);

		this.remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM);

		// 添加或修改客户信息
		addOrUpdateCustomer(customerSubjectDTO, subject);

		return update += 1;
	}

	/**
	 * 如果某些字段为空则排出客户对象
	 *
	 * @param customerSubjectDTO
	 * @param subject
	 */
	private void setUpdateSubject(CustomerSubjectDTO customerSubjectDTO, Subject subject) {
		if (Objects.nonNull(customerSubjectDTO.getUnifiedIdentity()) && !customerSubjectDTO.getUnifiedIdentity().equals("")) {
			subject.setUnifiedIdentity(customerSubjectDTO.getUnifiedIdentity());
		}

		if (Objects.nonNull(customerSubjectDTO.getName()) && !customerSubjectDTO.equals("")) {
			subject.setName(customerSubjectDTO.getName());
		}

		if (Objects.nonNull(customerSubjectDTO.getPhone()) && !customerSubjectDTO.getPhone().equals("")) {
			subject.setPhone(customerSubjectDTO.getPhone());
		}

		if (Objects.nonNull(customerSubjectDTO.getEmail()) && !customerSubjectDTO.getEmail().equals("")) {
			subject.setEmail(customerSubjectDTO.getEmail());
		}

		if (Objects.nonNull(customerSubjectDTO.getRemark()) && !customerSubjectDTO.getRemark().equals("")) {
			subject.setRemark(customerSubjectDTO.getRemark());
		}

		if (Objects.nonNull(customerSubjectDTO.getEmployer()) && !customerSubjectDTO.getEmployer().equals("")) {
			subject.setEmployer(customerSubjectDTO.getEmployer());
		}

		if (Objects.nonNull(customerSubjectDTO.getGender()) && !customerSubjectDTO.getGender().equals("")) {
			subject.setGender(customerSubjectDTO.getGender());
		}

		if (Objects.nonNull(customerSubjectDTO.getEthnic()) && !customerSubjectDTO.getEthnic().equals("")) {
			subject.setEthnic(customerSubjectDTO.getEthnic());
		}
	}

	/**
	 * 验证身份证与电话客户信息是否存在
	 *
	 * @param unifiedIdentity
	 * @param phone
	 * @return
	 */
	@Override
	public int verifyUnifiedIdentityAndPhone(String unifiedIdentity, String phone, Integer subjectId) {
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
					subjectVO = this.remoteSubjectService.getByPhone(phone, SecurityConstants.FROM).getData();
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
			SubjectVO subjectVO = this.remoteSubjectService.getByPhone(phone, SecurityConstants.FROM).getData();
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
	 * @param customerSubjectDTOList
	 * @return
	 */
	@Override
	@Transactional
	public int saveCustomerList(List<CustomerSubjectDTO> customerSubjectDTOList) {
		int add = 0;

		for (CustomerSubjectDTO customerSubjectDTO : customerSubjectDTOList) {
			saveUpdateCustomerAndSubject(customerSubjectDTO);
		}

		return add + 1;
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
		this.remoteSubjectService.saveOrUpdateById(subject, SecurityConstants.FROM);
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
