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
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.casee.dto.InsOutlesDTO;
import com.pig4cloud.pig.casee.dto.LiquiTransferRecordPageDTO;
import com.pig4cloud.pig.casee.entity.LiquiTransferRecord;
import com.pig4cloud.pig.casee.vo.LiquiTransferRecordDetailsVO;
import com.pig4cloud.pig.casee.vo.LiquiTransferRecordVO;
import com.pig4cloud.pig.casee.vo.QueryLiquiTransferRecordDetailsVO;
import com.pig4cloud.pig.casee.vo.paifu.LiquiTransferRecordAssetsDetailsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 清收移交记录表
 *
 * @author Mjh
 * @date 2022-04-06 15:21:31
 */
@Mapper
public interface LiquiTransferRecordMapper extends BaseMapper<LiquiTransferRecord> {
	IPage<LiquiTransferRecordVO> queryLiquiTransferRecordPage(Page page, @Param("query") LiquiTransferRecordPageDTO liquiTransferRecordPageDTO, @Param("login") InsOutlesDTO insOutlesDTO);

	LiquiTransferRecordDetailsVO getByLiquiTransferRecordId(Integer liquiTransferRecordId);

	List<LiquiTransferRecordVO> queryTransferRecord(Integer caseeId);

	QueryLiquiTransferRecordDetailsVO queryByLiquiTransferRecordId(Integer liquiTransferRecordId);

	List<LiquiTransferRecordAssetsDetailsVO> getTransferRecordAssetsByProjectId(Integer projectId);


	LiquiTransferRecord getByPaifuProjectIdAndAssetsId(@Param("paifuProjectId") Integer paifuProjectId, @Param("assetsId") Integer assetsId);
}
