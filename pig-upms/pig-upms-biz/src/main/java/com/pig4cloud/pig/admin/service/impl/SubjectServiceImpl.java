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

import com.alibaba.csp.sentinel.util.StringUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.dto.AddSubjectOrAddressDTO;
import com.pig4cloud.pig.admin.api.dto.SubjectAddressDTO;
import com.pig4cloud.pig.admin.api.dto.SubjectPageDTO;
import com.pig4cloud.pig.admin.api.dto.SubjectProjectCaseeDTO;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.vo.*;
import com.pig4cloud.pig.admin.mapper.SubjectMapper;
import com.pig4cloud.pig.admin.service.AddressService;
import com.pig4cloud.pig.admin.service.SubjectService;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.entity.BankLoan;
import com.pig4cloud.pig.casee.entity.SubjectBankLoanRe;
import com.pig4cloud.pig.casee.feign.RemoteBankLoanService;
import com.pig4cloud.pig.casee.feign.RemoteSubjectBankLoanReService;
import com.pig4cloud.pig.casee.vo.SubjectIdsOrSubjectBankLoanReIdsVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.security.service.JurisdictionUtilsService;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 主体
 *
 * @author yy
 * @date 2021-09-17 16:55:57
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {
	@Autowired
	private AddressService addressService;

	@Autowired
	private RemoteSubjectBankLoanReService remoteSubjectBankLoanReService;

	@Autowired
	private RemoteBankLoanService remoteBankLoanService;
	@Autowired
	private JurisdictionUtilsService jurisdictionUtilsService;

	@Override
	public boolean saveBatchSubject(List<Subject> subjectList) {
		for (Subject subject : subjectList) {
			if (subject.getUnifiedIdentity() != null && subject.getUnifiedIdentity() != "") {
				//判断是不是身份证
				if (isIdNum(subject.getUnifiedIdentity())) {
					subject.setNatureType(0);
				} else {
					subject.setNatureType(1);
				}
				LambdaQueryWrapper<Subject> subjectLambdaQueryWrapper = new LambdaQueryWrapper<>();
				subjectLambdaQueryWrapper.eq(Subject::getDelFlag, "0").eq(Subject::getUnifiedIdentity, subject.getUnifiedIdentity());
				Subject subjectQuery = this.getOne(subjectLambdaQueryWrapper);
				//判断是否存在 存在并且未认证就进行修改 不存在就新增
				if (subjectQuery != null && subjectQuery.getIsAuthentication() == 0) {
					subject.setSubjectId(subjectQuery.getSubjectId());
					subject.setName(subject.getName());
					subject.setPhone(subject.getPhone());
					subject.setLegalPerson(subject.getLegalPerson());
					subject.setEmail(subject.getEmail());
					subject.setRemark(subject.getRemark());
				}
			}
		}
		return this.saveOrUpdateBatch(subjectList);
	}

	/**
	 * 保存单条主体
	 *
	 * @param subject
	 * @return
	 */
	@Override
	public Subject saveSubject(Subject subject) {
		if (StringUtil.isNotBlank(subject.getUnifiedIdentity())) {
			if (subject.getNatureType() == null) {
				//判断是不是身份证
				if (isIdNum(subject.getUnifiedIdentity())) {
					subject.setNatureType(0);
				} else {
					subject.setNatureType(1);
				}
			}
			LambdaQueryWrapper<Subject> subjectLambdaQueryWrapper = new LambdaQueryWrapper<>();
			subjectLambdaQueryWrapper.eq(Subject::getDelFlag, "0").eq(Subject::getUnifiedIdentity, subject.getUnifiedIdentity());
			Subject subjectQuery = this.getOne(subjectLambdaQueryWrapper);
			//判断是否存在 存在并且未认证就进行修改 不存在就新增
			if (subjectQuery != null && subjectQuery.getIsAuthentication() == 0) {
				subject.setSubjectId(subjectQuery.getSubjectId());
				subject.setName(subject.getName());
				subject.setPhone(subject.getPhone());
				subject.setLegalPerson(subject.getLegalPerson());
				subject.setEmail(subject.getEmail());
				subject.setRemark(subject.getRemark());
			}
		}
		this.saveOrUpdate(subject);
		return subject;
	}

	@Override
	@Transactional
	public SubjectIdsOrSubjectBankLoanReIdsVO saveSubjectAddress(List<SubjectAddressDTO> subjectAddressDTOList) {

		SubjectIdsOrSubjectBankLoanReIdsVO subjectIdsOrSubjectBankLoanReIdsVO = new SubjectIdsOrSubjectBankLoanReIdsVO();

		List<Integer> subjectIds = new ArrayList();
		BankLoan bankLoan = new BankLoan();
		List<Integer> subjectBankLoanReListIds = new ArrayList<>();

		String subjectNames = "";//银行借贷所有债务人名称
		for (SubjectAddressDTO subjectAddressDTO : subjectAddressDTOList) {
			//添加债务人主体信息
			Subject subject = new Subject();
			BeanCopyUtil.copyBean(subjectAddressDTO, subject);
			this.saveSubject(subject);
			bankLoan = remoteBankLoanService.queryBankLoan(subjectAddressDTO.getBankLoanId(), SecurityConstants.FROM).getData();
			if (bankLoan!=null){
				if (bankLoan.getSubjectName()==null) {
					if (subjectNames==""){
						subjectNames = subject.getName();
					}else {
						subjectNames +=","+ subject.getName();
					}
				} else {
					if (!bankLoan.getSubjectName().contains(subject.getName())) {
						subjectNames = bankLoan.getSubjectName() + "," + subject.getName();
					}
				}
			}
			List<Address> addressList = subjectAddressDTO.getAddressList();
			for (Address address : addressList) {
				address.setUserId(subject.getSubjectId());
				address.setType(1);
				//添加债务人地址信息
				addressService.saveOrUpdate(address);
			}
			//添加主体关联银行借贷信息
			SubjectBankLoanRe subjectBankLoanRe = new SubjectBankLoanRe();
			subjectBankLoanRe.setSubjectId(subject.getSubjectId());
			subjectBankLoanRe.setDebtType(subjectAddressDTO.getDebtType());
			subjectBankLoanRe.setBankLoanId(subjectAddressDTO.getBankLoanId());
			Integer subjectBankLoanReId = remoteSubjectBankLoanReService.saveSubjectBankLoanRe(subjectBankLoanRe, SecurityConstants.FROM).getData();
			subjectBankLoanReListIds.add(subjectBankLoanReId);
			subjectIds.add(subject.getSubjectId());
		}
		bankLoan.setSubjectName(subjectNames);
		remoteBankLoanService.updateBankLoan(bankLoan, SecurityConstants.FROM);

		subjectIdsOrSubjectBankLoanReIdsVO.setSubjectIds(subjectIds);
		subjectIdsOrSubjectBankLoanReIdsVO.setSubjectBankLoanReListIds(subjectBankLoanReListIds);

		return subjectIdsOrSubjectBankLoanReIdsVO;
	}


	@Override
	public SubjectVO getByUnifiedIdentity(String unifiedIdentity) {
		SubjectVO subjectVO = this.baseMapper.getByUnifiedIdentity(unifiedIdentity);
		if (subjectVO != null) {
			List<AddressVO> addressList = subjectVO.getAddressList();
			for (AddressVO addressVO : addressList) {
				addressVO.setAddressACPName(addressVO.getProvince() + addressVO.getCity() + addressVO.getArea());
			}
		}
		return subjectVO;
	}

	@Override
	public SubjectGetByIdVO getBySubjectId(Integer subjectId) {
		SubjectGetByIdVO subjectGetByIdVO = this.baseMapper.getBySubjectId(subjectId);
		if (subjectGetByIdVO != null) {
			List<AddressVO> addressList = subjectGetByIdVO.getAddressList();
			for (AddressVO addressVO : addressList) {
				addressVO.setAddressACPName(addressVO.getProvince() + addressVO.getCity() + addressVO.getArea());
			}
		}
		return subjectGetByIdVO;
	}

	/**
	 * 根据特定条件分页查询债务人
	 *
	 * @param page
	 * @param subjectPageDTO
	 * @return
	 */
	@Override
	public IPage<Subject> pageSubject(Page page, SubjectPageDTO subjectPageDTO) {
		return this.baseMapper.pageSubject(page, subjectPageDTO);
	}

	/**
	 * 根据id查询债务人信息
	 *
	 * @param subjectId
	 * @return
	 */
	@Override
	public SubjectVO selectSubjectById(Integer subjectId) {
		return this.baseMapper.selectSubjectById(subjectId);
	}

	/**
	 * 判断身份证格式
	 *
	 * @param idNum
	 * @return
	 */
	public static boolean isIdNum(String idNum) {

		// 中国公民身份证格式：长度为15或18位，最后一位可以为字母
		Pattern idNumPattern = Pattern.compile("(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])");

		// 格式验证
		if (!idNumPattern.matcher(idNum).matches())
			return false;

		// 合法性验证

		int year = 0;
		int month = 0;
		int day = 0;

		if (idNum.length() == 15) {

			// 一代身份证

			System.out.println("一代身份证：" + idNum);

			// 提取身份证上的前6位以及出生年月日
			Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{2})(\\d{2})(\\d{2}).*");

			Matcher birthDateMather = birthDatePattern.matcher(idNum);

			if (birthDateMather.find()) {

				year = Integer.valueOf("19" + birthDateMather.group(1));
				month = Integer.valueOf(birthDateMather.group(2));
				day = Integer.valueOf(birthDateMather.group(3));

			}

		} else if (idNum.length() == 18) {

			// 二代身份证

			System.out.println("二代身份证：" + idNum);

			// 提取身份证上的前6位以及出生年月日
			Pattern birthDatePattern = Pattern.compile("\\d{6}(\\d{4})(\\d{2})(\\d{2}).*");

			Matcher birthDateMather = birthDatePattern.matcher(idNum);

			if (birthDateMather.find()) {

				year = Integer.valueOf(birthDateMather.group(1));
				month = Integer.valueOf(birthDateMather.group(2));
				day = Integer.valueOf(birthDateMather.group(3));
			}

		}

		// 年份判断，100年前至今

		Calendar cal = Calendar.getInstance();

		// 当前年份
		int currentYear = cal.get(Calendar.YEAR);

		if (year <= currentYear - 100 || year > currentYear)
			return false;

		// 月份判断
		if (month < 1 || month > 12)
			return false;

		// 日期判断

		// 计算月份天数

		int dayCount = 31;

		switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				dayCount = 31;
				break;
			case 2:
				// 2月份判断是否为闰年
				if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
					dayCount = 29;
					break;
				} else {
					dayCount = 28;
					break;
				}
			case 4:
			case 6:
			case 9:
			case 11:
				dayCount = 30;
				break;
		}

		System.out.println(String.format("生日：%d年%d月%d日", year, month, day));

		System.out.println(month + "月份有：" + dayCount + "天");

		if (day < 1 || day > dayCount)
			return false;

		return true;
	}

	@Override
	public List<Subject> queryBySubjectIdList(List<Integer> subjectIdList) {
		return this.baseMapper.selectBatchIds(subjectIdList);
	}

	@Override
	public IPage<SubjectProjectCaseeVO> queryPageByProjectId(Page page, SubjectProjectCaseeDTO subjectProjectCaseeDTO) {
		return this.baseMapper.selectPageByProjectId(page, subjectProjectCaseeDTO);
	}

	@Override
	public IPage<SubjectProjectCaseeVO> queryPageByCaseeId(Page page, SubjectProjectCaseeDTO subjectProjectCaseeDTO) {
		return this.baseMapper.selectPageByCaseeId(page, subjectProjectCaseeDTO);
	}

	/**
	 * 添加主体信息与主体地址
	 *
	 * @param addSubjectOrAddressDTO
	 * @return
	 */
	@Override
	public int addSubjectOrAddress(AddSubjectOrAddressDTO addSubjectOrAddressDTO) {
		this.save(addSubjectOrAddressDTO);

		if (Objects.nonNull(addSubjectOrAddressDTO.getInformationAddress()) || Objects.nonNull(addSubjectOrAddressDTO.getCode())) {

			Address address = new Address();

			BeanUtils.copyProperties(addSubjectOrAddressDTO, address);

			address.setUserId(addSubjectOrAddressDTO.getSubjectId());
			address.setType(1);

			this.addressService.save(address);
		}

		return addSubjectOrAddressDTO.getSubjectId();
	}

	@Override
	public SubjectDetailsVO getSubjectDetailBySubjectId(Integer subjectId) {
		return this.baseMapper.getSubjectDetailBySubjectId(subjectId);
	}

	@Override
	public int getIsThereASubjectByUnifiedIdentity(String unifiedIdentity, Integer insId) {
		return this.baseMapper.getIsThereASubjectByUnifiedIdentity(unifiedIdentity, insId);
	}

	@Override
	public Subject getByInsId(Integer insId) {
		return this.baseMapper.getByInsId(insId);
	}

	@Override
	public List<Subject> getSubjectByBankLoanId(Integer bankLoanId) {
		return this.baseMapper.getSubjectByBankLoanId(bankLoanId);
	}

	@Override
	public IPage<Subject> queryPageList(Page page, SubjectPageDTO subjectPageDTO){
		InsOutlesDTO insOutlesDTO = new InsOutlesDTO();
		insOutlesDTO.setInsId(jurisdictionUtilsService.queryByInsId("PLAT_"));
		insOutlesDTO.setOutlesId(jurisdictionUtilsService.queryByOutlesId("PLAT_"));
		return this.baseMapper.selectPageList(page,subjectPageDTO,insOutlesDTO);
	}
}
