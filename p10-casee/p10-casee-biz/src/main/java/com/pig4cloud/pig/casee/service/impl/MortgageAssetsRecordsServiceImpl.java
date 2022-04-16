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
import com.pig4cloud.pig.casee.dto.AssetsDTO;
import com.pig4cloud.pig.casee.dto.MortgageAssetsDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.entity.MortgageAssetsRe;
import com.pig4cloud.pig.casee.entity.MortgageAssetsRecords;
import com.pig4cloud.pig.casee.entity.MortgageAssetsSubjectRe;
import com.pig4cloud.pig.casee.mapper.MortgageAssetsRecordsMapper;
import com.pig4cloud.pig.casee.service.AssetsService;
import com.pig4cloud.pig.casee.service.MortgageAssetsReService;
import com.pig4cloud.pig.casee.service.MortgageAssetsRecordsService;
import com.pig4cloud.pig.casee.service.MortgageAssetsSubjectReService;
import com.pig4cloud.pig.casee.vo.AssetsInformationVO;
import com.pig4cloud.pig.casee.vo.MortgageAssetsRecordsVO;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


	@Override
	public List<AssetsInformationVO> getMortgageAssetsRecordsDetails(Integer bankLoanId) {
		return this.baseMapper.getMortgageAssetsRecordsDetails(bankLoanId);
	}

	@Override
	public MortgageAssetsRecordsVO getByMortgageAssetsRecordsId(Integer mortgageAssetsRecordsId) {
		MortgageAssetsRecordsVO mortgageAssetsRecordsVO = this.baseMapper.getByMortgageAssetsRecordsId(mortgageAssetsRecordsId);
		List<MortgageAssetsRe> list = mortgageAssetsReService.list(new LambdaQueryWrapper<MortgageAssetsRe>().eq(MortgageAssetsRe::getMortgageAssetsRecordsId, mortgageAssetsRecordsId));
		List<MortgageAssetsSubjectRe> mortgageAssetsSubjectReList=new ArrayList<>();
		for (MortgageAssetsRe mortgageAssetsRe : list) {
			mortgageAssetsSubjectReList = mortgageAssetsSubjectReService.list(new LambdaQueryWrapper<MortgageAssetsSubjectRe>().eq(MortgageAssetsSubjectRe::getMortgageAssetsReId, mortgageAssetsRe.getMortgageAssetsReId()));
		}
		for (MortgageAssetsSubjectRe mortgageAssetsSubjectRe : mortgageAssetsSubjectReList) {
			R<Subject> serviceById = remoteSubjectService.getById(mortgageAssetsSubjectRe.getSubjectId(), SecurityConstants.FROM);
			mortgageAssetsRecordsVO.getSubjectId().add(serviceById.getData().getSubjectId());
		}

		return mortgageAssetsRecordsVO;
	}

	@Override
	public boolean updateByMortgageAssets(MortgageAssetsDTO mortgageAssetsDTO) {
		MortgageAssetsRecords mortgageAssetsRecords=new MortgageAssetsRecords();
		BeanCopyUtil.copyBean(mortgageAssetsDTO, mortgageAssetsRecords);

		List<AssetsDTO> assetsList = mortgageAssetsDTO.getAssetsList();
		for (AssetsDTO assetsDTO : assetsList) {
			Integer assetsId = assetsDTO.getAssetsId();
			if (assetsId!=null){//财产已存在
				//查询该财产是否关联抵押信息
				MortgageAssetsRe mortgageAssetsRe = mortgageAssetsReService.getOne(new LambdaQueryWrapper<MortgageAssetsRe>().eq(MortgageAssetsRe::getAssetsId, assetsId).eq(MortgageAssetsRe::getMortgageAssetsRecordsId, mortgageAssetsRecords.getMortgageAssetsRecordsId()));
				if (mortgageAssetsRe!=null){//有关联则修改
					//清除该财产关联债务人信息
					mortgageAssetsSubjectReService.remove(new LambdaQueryWrapper<MortgageAssetsSubjectRe>().eq(MortgageAssetsSubjectRe::getMortgageAssetsReId,mortgageAssetsRe.getMortgageAssetsReId()));
					List<Integer> subjectId = mortgageAssetsDTO.getSubjectId();
					for (Integer id : subjectId) {
						MortgageAssetsSubjectRe mortgageAssetsSubjectRe=new MortgageAssetsSubjectRe();
						mortgageAssetsSubjectRe.setSubjectId(id);
						mortgageAssetsSubjectRe.setMortgageAssetsReId(mortgageAssetsRe.getMortgageAssetsReId());
						mortgageAssetsSubjectReService.save(mortgageAssetsSubjectRe);//添加财产关联债务人信息
					}
				}else {//无关联则添加
					mortgageAssetsRe.setAssetsId(assetsId);
					mortgageAssetsRe.setMortgageAssetsRecordsId(mortgageAssetsRecords.getMortgageAssetsRecordsId());
					mortgageAssetsReService.save(mortgageAssetsRe);//添加财产关联抵押信息
					List<Integer> subjectId = mortgageAssetsDTO.getSubjectId();
					for (Integer id : subjectId) {
						MortgageAssetsSubjectRe mortgageAssetsSubjectRe=new MortgageAssetsSubjectRe();
						mortgageAssetsSubjectRe.setSubjectId(id);
						mortgageAssetsSubjectRe.setMortgageAssetsReId(mortgageAssetsRe.getMortgageAssetsReId());
						mortgageAssetsSubjectReService.save(mortgageAssetsSubjectRe);//添加财产关联债务人信息
					}
				}
			}else {
				//抵押财产信息
				Assets assets = new Assets();
				BeanCopyUtil.copyBean(assetsDTO, assets);
				assetsService.save(assets);//添加财产信息
				//财产地址信息
				Address address = new Address();
				BeanUtils.copyProperties(assetsDTO, address);
				address.setType(4);
				address.setUserId(assets.getAssetsId());
				remoteAddressService.saveAddress(address, SecurityConstants.FROM);//添加财产地址信息
				//财产关联抵押信息
				MortgageAssetsRe mortgageAssetsRe=new MortgageAssetsRe();
				mortgageAssetsRe.setAssetsId(assets.getAssetsId());
				mortgageAssetsRe.setMortgageAssetsRecordsId(mortgageAssetsRecords.getMortgageAssetsRecordsId());
				mortgageAssetsReService.save(mortgageAssetsRe);//添加财产关联抵押信息

				List<Integer> subjectId = mortgageAssetsDTO.getSubjectId();
				for (Integer id : subjectId) {
					MortgageAssetsSubjectRe mortgageAssetsSubjectRe=new MortgageAssetsSubjectRe();
					mortgageAssetsSubjectRe.setSubjectId(id);
					mortgageAssetsSubjectRe.setMortgageAssetsReId(mortgageAssetsRe.getMortgageAssetsReId());
					mortgageAssetsSubjectReService.save(mortgageAssetsSubjectRe);//添加财产关联债务人信息
				}
			}
		}
		return this.updateById(mortgageAssetsRecords);//修改抵押信息
	}
}
