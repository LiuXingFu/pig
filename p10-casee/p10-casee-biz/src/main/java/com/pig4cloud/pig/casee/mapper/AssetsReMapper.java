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

package com.pig4cloud.pig.casee.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pig4cloud.pig.casee.dto.AssetsDTO;
import com.pig4cloud.pig.casee.dto.SaveAssetsDTO;
import com.pig4cloud.pig.casee.entity.AssetsRe;
import com.pig4cloud.pig.casee.vo.AssetsVO;
import com.pig4cloud.pig.casee.vo.CaseeOrAssetsVO;
import com.pig4cloud.pig.common.core.util.KeyValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 财产关联表
 *
 * @author ligt
 * @date 2022-01-19 15:19:24
 */
@Mapper
public interface AssetsReMapper extends BaseMapper<AssetsRe> {
	List<AssetsVO> getProjectIdStatusByAssets(@Param("projectId")Integer projectId, @Param("status")Integer status);

	List<CaseeOrAssetsVO> selectCaseeOrAssets(Integer caseeId);

	String queryAssetsReDetail(SaveAssetsDTO saveAssetsDTO);

	void updateAssetsReDetail(@Param("assetsId") Integer assetsId, @Param("caseeId") Integer caseeId,@Param("listParams") List<KeyValue> listParams);
}
