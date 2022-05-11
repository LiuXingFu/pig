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

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.JointAuctionAssetsDTO;
import com.pig4cloud.pig.casee.dto.SaveAssetsDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.vo.AssetsVO;
import com.pig4cloud.pig.casee.vo.CaseeOrAssetsVO;

import java.util.List;

/**
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
public interface AssetsReService extends IService<AssetsRe> {

	List<AssetsVO> getProjectIdStatusByAssets(Integer projectId, Integer status);

	List<CaseeOrAssetsVO> selectCaseeOrAssets(Integer caseeId);

	void updateAssetsReDetail(SaveAssetsDTO saveAssetsDTO);

	List<JointAuctionAssetsDTO> queryByAssetsReIdList(List<Integer> assetsReIdList);
}
