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

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
import com.pig4cloud.pig.casee.dto.SaveAssetsDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.mapper.AssetsReMapper;
import com.pig4cloud.pig.casee.service.AssetsReService;
import com.pig4cloud.pig.casee.vo.AssetsVO;
import com.pig4cloud.pig.casee.vo.CaseeOrAssetsVO;
import com.pig4cloud.pig.common.core.util.BeanCopyUtil;
import com.pig4cloud.pig.common.core.util.KeyValue;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
@Service
public class AssetsReServiceImpl extends ServiceImpl<AssetsReMapper, AssetsRe> implements AssetsReService {


	@Override
	public List<AssetsVO> getProjectIdStatusByAssets(Integer projectId, Integer status) {
		return this.baseMapper.getProjectIdStatusByAssets(projectId,status);
	}
	@Override
	public List<CaseeOrAssetsVO> selectCaseeOrAssets(Integer caseeId) {
		return this.baseMapper.selectCaseeOrAssets(caseeId);
	}

	@Override
	public void updateAssetsReDetail(SaveAssetsDTO saveAssetsDTO) {
		JSONObject formData = JSONObject.parseObject(saveAssetsDTO.getFormData());
		List<KeyValue> listParams = new ArrayList<KeyValue>();

		for (String o : formData.keySet()) {
			listParams.add(new KeyValue(saveAssetsDTO.getKey() + "." + o, formData.get(o)));
		}

		String businessData = this.baseMapper.queryAssetsReDetail(saveAssetsDTO);

		if (businessData == null) {
			String[] pathVec = saveAssetsDTO.getKey().substring(2).split("\\.");
			AssetsRe assetsRe = this.getOne(new LambdaQueryWrapper<AssetsRe>().eq(AssetsRe::getDelFlag, 0).eq(AssetsRe::getAssetsId, saveAssetsDTO.getAssetsId()).eq(AssetsRe::getCaseeId, saveAssetsDTO.getCaseeId()));
			JSONObject bussDataJson = JSONObject.parseObject(assetsRe.getAssetsReDetail());

			int index = pathVec.length;
			do {
				index--;
				String varName = pathVec[index];
				JSONObject tmpJson = new JSONObject();
				tmpJson.put(varName, formData);
				formData = tmpJson;
				if (index <= 0) {
					break;
				}
			} while (true);

			BeanCopyUtil.mergeJSONObject(bussDataJson, formData);
			AssetsRe needUpdate = new AssetsRe();
			needUpdate.setAssetsReId(assetsRe.getAssetsReId());
			needUpdate.setAssetsReDetail(bussDataJson.toJSONString());
			this.updateById(needUpdate);
		} else {
			this.baseMapper.updateAssetsReDetail(saveAssetsDTO.getAssetsId(), saveAssetsDTO.getCaseeId(), listParams);
		}

	}

	@Override
	public List<JointAuctionAssetsDTO> queryByAssetsReIdList(List<Integer> assetsReIdList){
		return this.baseMapper.selectByAssetsReIdList(assetsReIdList);
	}
}
