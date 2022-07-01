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

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.admin.api.entity.Address;
import com.pig4cloud.pig.admin.api.entity.Subject;
import com.pig4cloud.pig.admin.api.feign.RemoteAddressService;
import com.pig4cloud.pig.admin.api.feign.RemoteSubjectService;
import com.pig4cloud.pig.admin.api.vo.SubjectVO;
import com.pig4cloud.pig.casee.dto.AssetsDTO;
import com.pig4cloud.pig.casee.dto.MortgageAssetsDTO;
import com.pig4cloud.pig.casee.entity.*;
import com.pig4cloud.pig.casee.mapper.MortgageAssetsRecordsMapper;
import com.pig4cloud.pig.casee.service.*;
import com.pig4cloud.pig.casee.vo.AssetsInformationVO;
import com.pig4cloud.pig.casee.vo.MortgageAssetsRecordsVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 抵押记录表
 *
 * @author Mjh
 * @date 2022-04-13 11:24:18
 */
@Service
public class MortgageAssetsRecordsServiceImpl extends ServiceImpl<MortgageAssetsRecordsMapper, MortgageAssetsRecords> implements MortgageAssetsRecordsService {
	@Autowired
	MortgageAssetsSubjectReService mortgageAssetsSubjectReService;
	@Autowired
	MortgageAssetsReService mortgageAssetsReService;
	@Autowired
	RemoteSubjectService remoteSubjectService;
	@Autowired
	AssetsService assetsService;
	@Autowired
	RemoteAddressService remoteAddressService;
	@Autowired
	AssetsReService assetsReService;
	@Autowired
	AssetsBankLoanReService assetsBankLoanReService;
	@Autowired
	AssetsReSubjectService assetsReSubjectService;
	@Autowired
	MortgageAssetsRecordsService mortgageAssetsRecordsService;
	@Autowired
	TargetService targetService;
	@Autowired
	ProjectLiquiService projectLiquiService;
	@Autowired
	ProjectCaseeReService projectCaseeReService;
	@Autowired
	CaseeHandlingRecordsReService caseeHandlingRecordsReService;
	@Autowired
	CaseeHandlingRecordsService caseeHandlingRecordsService;
	@Autowired
	PaymentRecordAssetsReService paymentRecordAssetsReService;
	@Autowired
	ExpenseRecordAssetsReService expenseRecordAssetsReService;

	@Override
	public List<AssetsInformationVO> getMortgageAssetsRecordsDetails(Integer bankLoanId) {
		return this.baseMapper.getMortgageAssetsRecordsDetails(bankLoanId);
	}

	@Override
	public MortgageAssetsRecordsVO getByMortgageAssetsRecordsId(Integer mortgageAssetsRecordsId) {
		MortgageAssetsRecordsVO mortgageAssetsRecordsVO = this.baseMapper.getByMortgageAssetsRecordsId(mortgageAssetsRecordsId);

		return mortgageAssetsRecordsVO;
	}

	@Override
	@Transactional
	public boolean updateByMortgageAssets(MortgageAssetsDTO mortgageAssetsDTO) {
		MortgageAssetsRecords mortgageAssetsRecords = new MortgageAssetsRecords();
		BeanCopyUtil.copyBean(mortgageAssetsDTO, mortgageAssetsRecords);

		List<AssetsDTO> assetsList = mortgageAssetsDTO.getAssetsDTOList();
		Assets assets = new Assets();

		for (AssetsDTO assetsDTO : assetsList) {
			//如果抵押财产发生改变则删除关联关系
			if (assetsDTO.getMortgageAssetsReId() != null) {
				//删除抵押财产关联信息
				mortgageAssetsReService.removeById(assetsDTO.getMortgageAssetsReId());
				//删除抵押财产关联债务人信息
				mortgageAssetsSubjectReService.remove(new LambdaQueryWrapper<MortgageAssetsSubjectRe>().eq(MortgageAssetsSubjectRe::getMortgageAssetsReId, assetsDTO.getMortgageAssetsReId()));
			}

			Integer assetsId = assetsDTO.getAssetsId();
			if (assetsId != null) {//财产已存在
				//修改财产信息
				BeanCopyUtil.copyBean(assetsDTO, assets);
				assetsService.updateById(assets);

				Address address = new Address();

				Address data = this.remoteAddressService.queryAssetsByTypeIdAndType(assetsId, 4, SecurityConstants.FROM).getData();

				BeanUtils.copyProperties(assetsDTO, address);


				//如果当前财产地址不为空则修改地址信息否则添加地址信息
				if (data != null) {
					address.setAddressId(data.getAddressId());
					address.setType(4);
					remoteAddressService.updateByAddressId(address, SecurityConstants.FROM);
				} else {
					address.setType(4);
					address.setUserId(assets.getAssetsId());
					remoteAddressService.saveAddress(address, SecurityConstants.FROM);
				}

				//查询该财产是否关联抵押信息
				MortgageAssetsRe mortgageAssetsRe = mortgageAssetsReService.getOne(new LambdaQueryWrapper<MortgageAssetsRe>().eq(MortgageAssetsRe::getAssetsId, assetsId).eq(MortgageAssetsRe::getMortgageAssetsRecordsId, mortgageAssetsRecords.getMortgageAssetsRecordsId()));
				if (mortgageAssetsRe != null) {//有关联则修改
					//修改抵押财产关联信息
					mortgageAssetsRe.setSubjectName(assetsDTO.getSubjectName());
					mortgageAssetsReService.updateById(mortgageAssetsRe);
					//清除该财产关联债务人信息
					mortgageAssetsSubjectReService.remove(new LambdaQueryWrapper<MortgageAssetsSubjectRe>().eq(MortgageAssetsSubjectRe::getMortgageAssetsReId, mortgageAssetsRe.getMortgageAssetsReId()));
					List<Integer> subjectId = assetsDTO.getSubjectId();
					for (Integer id : subjectId) {
						MortgageAssetsSubjectRe mortgageAssetsSubjectRe = new MortgageAssetsSubjectRe();
						mortgageAssetsSubjectRe.setSubjectId(id);
						mortgageAssetsSubjectRe.setMortgageAssetsReId(mortgageAssetsRe.getMortgageAssetsReId());
						mortgageAssetsSubjectReService.save(mortgageAssetsSubjectRe);//添加财产关联债务人信息
					}
				} else {//无关联则添加
					mortgageAssetsRe = new MortgageAssetsRe();
					mortgageAssetsRe.setAssetsId(assetsId);
					mortgageAssetsRe.setMortgageAssetsRecordsId(mortgageAssetsRecords.getMortgageAssetsRecordsId());
					mortgageAssetsRe.setSubjectName(assetsDTO.getSubjectName());
					mortgageAssetsReService.save(mortgageAssetsRe);//添加财产关联抵押信息
					List<Integer> subjectId = assetsDTO.getSubjectId();
					for (Integer id : subjectId) {
						MortgageAssetsSubjectRe mortgageAssetsSubjectRe = new MortgageAssetsSubjectRe();
						mortgageAssetsSubjectRe.setSubjectId(id);
						mortgageAssetsSubjectRe.setMortgageAssetsReId(mortgageAssetsRe.getMortgageAssetsReId());
						mortgageAssetsSubjectReService.save(mortgageAssetsSubjectRe);//添加财产关联债务人信息
					}
				}
			} else {
				//抵押财产信息
				BeanCopyUtil.copyBean(assetsDTO, assets);
				assetsService.save(assets);//添加财产信息
				//财产地址信息
				Address address = new Address();
				BeanUtils.copyProperties(assetsDTO, address);
				address.setType(4);
				address.setUserId(assets.getAssetsId());
				remoteAddressService.saveAddress(address, SecurityConstants.FROM);//添加财产地址信息
				//财产关联抵押信息
				MortgageAssetsRe mortgageAssetsRe = new MortgageAssetsRe();
				mortgageAssetsRe.setAssetsId(assets.getAssetsId());
				mortgageAssetsRe.setMortgageAssetsRecordsId(mortgageAssetsRecords.getMortgageAssetsRecordsId());
				mortgageAssetsRe.setSubjectName(assetsDTO.getSubjectName());
				mortgageAssetsReService.save(mortgageAssetsRe);//添加财产关联抵押信息

				List<Integer> subjectId = assetsDTO.getSubjectId();
				List<MortgageAssetsSubjectRe> mortgageAssetsSubjectRes = new ArrayList<>();
				if (subjectId != null && subjectId.size() > 0) {
					for (Integer id : subjectId) {
						MortgageAssetsSubjectRe mortgageAssetsSubjectRe = new MortgageAssetsSubjectRe();
						mortgageAssetsSubjectRe.setSubjectId(id);
						mortgageAssetsSubjectRe.setMortgageAssetsReId(mortgageAssetsRe.getMortgageAssetsReId());
						mortgageAssetsSubjectRes.add(mortgageAssetsSubjectRe);
					}
				} else if (assetsDTO.getUnifiedIdentityList() != null && assetsDTO.getUnifiedIdentityList().size() > 0) {
					for (String unifiedIdentity : assetsDTO.getUnifiedIdentityList()) {
						MortgageAssetsSubjectRe mortgageAssetsSubjectRe = new MortgageAssetsSubjectRe();
						mortgageAssetsSubjectRe.setMortgageAssetsReId(mortgageAssetsRe.getMortgageAssetsReId());
						R<SubjectVO> subjectVOR = remoteSubjectService.getByUnifiedIdentity(unifiedIdentity, SecurityConstants.FROM);
						mortgageAssetsSubjectRe.setSubjectId(subjectVOR.getData().getSubjectId());
						mortgageAssetsSubjectRes.add(mortgageAssetsSubjectRe);
					}
				}
				mortgageAssetsSubjectReService.saveBatch(mortgageAssetsSubjectRes);//添加财产关联债务人信息
			}
		}
		return this.updateById(mortgageAssetsRecords);//修改抵押信息
	}

	@Override
	@Transactional
	public void synchronize() {
		List<AssetsBankLoanRe> AssetsBankLoanReList = assetsBankLoanReService.list();

		List<MortgageAssetsSubjectRe> mortgageAssetsSubjectReList = new ArrayList<>();
		for (AssetsBankLoanRe assetsBankLoanRe : AssetsBankLoanReList) {
			MortgageAssetsRecords mortgageAssetsRecords = new MortgageAssetsRecords();
			BeanCopyUtil.copyBean(assetsBankLoanRe, mortgageAssetsRecords);
			mortgageAssetsRecords.setMortgageStartTime(assetsBankLoanRe.getMortgageTime());
			mortgageAssetsRecords.setJointMortgage(0);
			R<Subject> serviceById = remoteSubjectService.getById(assetsBankLoanRe.getSubjectId(), SecurityConstants.FROM);
//			mortgageAssetsRecords.setSubjectName(serviceById.getData().getName());
			mortgageAssetsRecordsService.save(mortgageAssetsRecords);

			MortgageAssetsRe mortgageAssetsRe = new MortgageAssetsRe();
			mortgageAssetsRe.setAssetsId(assetsBankLoanRe.getAssetsId());
			mortgageAssetsRe.setMortgageAssetsRecordsId(mortgageAssetsRecords.getMortgageAssetsRecordsId());
			mortgageAssetsReService.save(mortgageAssetsRe);

			MortgageAssetsSubjectRe mortgageAssetsSubjectRe = new MortgageAssetsSubjectRe();
			mortgageAssetsSubjectRe.setMortgageAssetsReId(mortgageAssetsRe.getMortgageAssetsReId());
			mortgageAssetsSubjectRe.setSubjectId(assetsBankLoanRe.getSubjectId());
			mortgageAssetsSubjectReList.add(mortgageAssetsSubjectRe);
		}
		mortgageAssetsSubjectReService.saveBatch(mortgageAssetsSubjectReList);


		List<AssetsRe> list = assetsReService.list();
		List<AssetsReSubject> assetsReSubjectList = new ArrayList<>();

		for (AssetsRe assetsRe : list) {
			AssetsReSubject assetsReSubject = new AssetsReSubject();
			assetsReSubject.setSubjectId(assetsRe.getSubjectId());
			assetsReSubject.setAssetsReId(assetsRe.getAssetsReId());
			assetsReSubjectList.add(assetsReSubject);
		}

		assetsReSubjectService.saveBatch(assetsReSubjectList);


		List<Target> targetList = targetService.list();
		List<Target> targets = new ArrayList();

		for (Target target : targetList) {
			ProjectCaseeRe projectCaseeRe = projectCaseeReService.getOne(new LambdaQueryWrapper<ProjectCaseeRe>().eq(ProjectCaseeRe::getCaseeId, target.getCaseeId()));
			target.setProjectId(projectCaseeRe.getProjectId());
			targets.add(target);
		}
		targetService.updateBatchById(targets);
	}
}
