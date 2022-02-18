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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.casee.dto.AssetsDTO;
import com.pig4cloud.pig.casee.dto.AssetsGetByIdDTO;
import com.pig4cloud.pig.casee.dto.AssetsOrProjectPageDTO;
import com.pig4cloud.pig.casee.dto.BankLoanDTO;
import com.pig4cloud.pig.casee.entity.Assets;
import com.pig4cloud.pig.casee.vo.AssetsDeailsVO;
import com.pig4cloud.pig.casee.vo.AssetsOrProjectPageVO;

import java.util.List;

/**
 * 财产表
 *
 * @author ligt
 * @date 2022-01-11 10:29:44
 */
public interface AssetsService extends IService<Assets> {

	AssetsGetByIdDTO getByAssets(Integer assetsId);

	boolean	saveAssets(BankLoanDTO bankLoanDTO);

	IPage<AssetsOrProjectPageVO> queryPageAssetsOrProject(Page page, Integer subjectId);

	AssetsDeailsVO queryById(Integer assetsId);

	Object getPageAssetsManage(Page page, AssetsOrProjectPageDTO assetsOrProjectPageDTO);
}
